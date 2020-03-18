package dev.dejay.xmessage.listeners;

import dev.dejay.xmessage.Main;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class EventLeave implements Listener {

  @EventHandler
  public void playerLeaveEvent(PlayerDisconnectEvent event) {
    ProxiedPlayer player = event.getPlayer();

    Main.INSTANCE.messageHistory.remove(player.getUniqueId()); // Make player unmessagable on leave
  }

}
