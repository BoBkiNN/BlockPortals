package xyz.bobkinn_.blockportals;

import org.apache.commons.io.FileUtils;
import org.bukkit.World;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.player.PlayerPortalEvent;
import java.io.*;

public final class BlockPortals extends JavaPlugin implements Listener {
    public static FileConfiguration configuration;
    public static BlockPortals plugin;
    public static File configFile;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        configFile = new File(plugin.getDataFolder(), "config.yml");
        reload();
        getServer().getPluginManager().registerEvents(this,this);
        PluginCommand reloadCmd = getCommand("bpreload");
        if (reloadCmd != null) reloadCmd.setExecutor(new ReloadCmd());
        PluginCommand switchCmd = getCommand("switchdim");
        if (switchCmd != null) {
            switchCmd.setExecutor(new SwitchDimCmd());
            switchCmd.setTabCompleter(new SwitchTabCompleter());
        }

    }

    public static void reload(){
        File dataFolder = plugin.getDataFolder();
        if (!dataFolder.exists()){
            if (!dataFolder.mkdir()){
                plugin.getLogger().severe("Failed to create data folder, disabling..");
                plugin.getServer().getPluginManager().disablePlugin(plugin);
                return;
            }
        } else if (dataFolder.isFile()){
            plugin.getLogger().severe("Failed to create data folder due to existing file with same name, disabling..");
            plugin.getServer().getPluginManager().disablePlugin(plugin);
            return;
        }
        if (!configFile.exists()){
            try {
                if (!configFile.createNewFile()){
                    plugin.getLogger().severe("Failed to create config.yml, disabling..");
                    plugin.getServer().getPluginManager().disablePlugin(plugin);
                }
                InputStream from = plugin.getResource("config.yml");
                if (from == null) {
                    plugin.getLogger().severe("Failed to create config.yml, disabling..");
                    plugin.getServer().getPluginManager().disablePlugin(plugin);
                    return;
                }
                FileUtils.copyInputStreamToFile(from, configFile);

            } catch (IOException e) {
                e.printStackTrace();
                plugin.getLogger().severe("Failed to create config.yml, disabling..");
                plugin.getServer().getPluginManager().disablePlugin(plugin);
            }
        } else if (configFile.isDirectory()){
            plugin.getLogger().severe("Failed to create config.yml due to existing directory with same name, disabling..");
            plugin.getServer().getPluginManager().disablePlugin(plugin);
        }

        configuration = YamlConfiguration.loadConfiguration(configFile);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void onPortal(PlayerPortalEvent e){
        if (e.getTo() == null) return;
        if (e.getTo().getWorld() == null) return;
        if (e.getTo().getWorld().getEnvironment() == World.Environment.THE_END){
            boolean allowEnd = configuration.getBoolean("allow-end", true);
            if (!allowEnd) e.setCancelled(true);
            return;
        }
        if (e.getTo().getWorld().getEnvironment() == World.Environment.NETHER){
            boolean allowNether = configuration.getBoolean("allow-nether", true);
            if (!allowNether) e.setCancelled(true);
            return;
        }
        if (e.getTo().getWorld().getEnvironment() == World.Environment.NORMAL){
            boolean allowNormal = configuration.getBoolean("allow-overworld", true);
            if (!allowNormal) e.setCancelled(true);
        }
    }
}
