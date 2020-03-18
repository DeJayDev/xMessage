package dev.dejay.xmessage.commands;

import dev.dejay.xmessage.Main;
import java.util.Arrays;
import net.md_5.bungee.api.CommandSender;
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
    ProxiedPlayer receiver = Main.INSTANCE.getProxy().getPlayer(args[0]);
    String message = String.join(", ", Arrays.asList(args).remove(0)); // Build the message by removing the username (args[0])

    if (commandSender instanceof ProxiedPlayer) {
      sender = (ProxiedPlayer) commandSender;
      sender.sendMessage(new ComponentBuilder("later").create());
    }
  }

}
