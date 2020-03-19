package dev.dejay.xmessage;

import dev.dejay.xmessage.commands.CmdMessage;
import dev.dejay.xmessage.commands.CmdReply;
import dev.dejay.xmessage.listeners.EventLeave;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public final class Main extends Plugin {

  public static Main INSTANCE;
  public Map<UUID, UUID> messageHistory = new HashMap<>();

  public Configuration config;

  @Override
  public void onEnable() {
    INSTANCE = this;
    PluginManager pluginManager = getProxy().getPluginManager();
    pluginManager.registerCommand(this, new CmdMessage());
    pluginManager.registerCommand(this, new CmdReply());
    pluginManager.registerListener(this, new EventLeave());

    if (!getDataFolder().exists()) {
      getDataFolder().mkdir();
    } else {
      try {
        File configFile = new File(getDataFolder(), "config.yml");
        config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    File configFile = new File(getDataFolder(), "config.yml");

    if (!configFile.exists()) { //If the config doesn't exist, let's try to make it.
      try(InputStream stream = getResourceAsStream("config.yml")) {
        Files.copy(stream, configFile.toPath());
        config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
      } catch (IOException rock) {
        rock.printStackTrace();
      }
    }

  }

  @Override
  public void onDisable() {
    // Plugin shutdown logic
  }

}
