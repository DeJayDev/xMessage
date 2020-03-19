package dev.dejay.xmessage.commands;

import dev.dejay.xmessage.Main;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class CmdReply extends Command {

  public CmdReply() {
    super("reply", "", new String[]{"r", "res"});
  }

  @Override
  public void execute(CommandSender commandSender, String[] args) {
    ProxiedPlayer sender;
    ProxyServer proxy = Main.INSTANCE.getProxy();

    if (commandSender instanceof ProxiedPlayer) {
      sender = (ProxiedPlayer) commandSender;

      ProxiedPlayer receiver = proxy.getPlayer(Main.INSTANCE.messageHistory.get(sender.getUniqueId()));
      List<String> msgList = new LinkedList<>(Arrays.asList(args));
      String message = String.join(", ", msgList.remove(0));// Build the message by removing the username (args[0])

      String senderMsg = Main.INSTANCE.config.getString("sender-msg");
      String receiverMsg = Main.INSTANCE.config.getString("receiver-msg");

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
    }
  }

}
