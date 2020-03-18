package dev.dejay.xmessage.commands;

import dev.dejay.xmessage.Main;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class CmdMessage extends Command {

  public CmdMessage() {
    super("message", "", new String[]{"msg", "tell", "whisper", "pm"});
  }

  @Override
  public void execute(CommandSender commandSender, String[] args) {
    ProxiedPlayer sender;
    ProxiedPlayer receiver = Main.INSTANCE.getProxy().getPlayer(args[0]);
    List<String> msgList = new LinkedList<>(Arrays.asList(args));
    String message = String.join(", ", msgList.remove(0)); // Build the message by removing the username (args[0])

    if (commandSender instanceof ProxiedPlayer) {
      sender = (ProxiedPlayer) commandSender;

      String senderMsg = Main.INSTANCE.config.getString("sender-msg");
      String receiverMsg = Main.INSTANCE.config.getString("receiver-msg");

      Main.INSTANCE.messageHistory.putIfAbsent(sender.getUniqueId(), receiver.getUniqueId());

      sender.sendMessage(new ComponentBuilder(
          ChatColor.translateAlternateColorCodes('&', senderMsg.replace("{sender}", sender.getName())
              .replace("{receiver}", receiver.getName())
              .replace("{server-name}", receiver.getServer().getInfo().getName())
              .replace("{message}", message))
      ).create());

      receiver.sendMessage(new ComponentBuilder(
          ChatColor.translateAlternateColorCodes('&', receiverMsg.replace("{sender}", sender.getName())
              .replace("{receiver}", receiver.getName())
              .replace("{server-name}", receiver.getServer().getInfo().getName())
              .replace("{message}", message))
      ).create());
    } else {
      String senderName = "@console"; // Old reference, keeping it as a "valid" player.
      commandSender.sendMessage(new ComponentBuilder("Sent message to player.").create());

      String receiverMsg = Main.INSTANCE.config.getString("receiver-msg");

      receiver.sendMessage(new ComponentBuilder(
          ChatColor.translateAlternateColorCodes('&', receiverMsg.replace("{sender}", senderName)
              .replace("{receiver}", receiver.getName())
              .replace("{server-name}", receiver.getServer().getInfo().getName())
              .replace("{message}", message))
      ).create());
    }

  }

}
