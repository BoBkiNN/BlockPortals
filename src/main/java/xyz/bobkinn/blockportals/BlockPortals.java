package xyz.bobkinn.blockportals;

import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class BlockPortals extends JavaPlugin implements Listener {
    public static FileConfiguration configuration;
    public static BlockPortals plugin;
    public static File configFile;

    public static void reload() {
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
        configuration = plugin.getConfig();
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        configFile = new File(plugin.getDataFolder(), "config.yml");
        reload();
        getServer().getPluginManager().registerEvents(this, this);
        PluginCommand reloadCmd = getCommand("bpreload");
        if (reloadCmd != null) reloadCmd.setExecutor(new ReloadCmd());
        PluginCommand switchCmd = getCommand("switchdim");
        if (switchCmd != null) {
            switchCmd.setExecutor(new SwitchDimCmd());
            switchCmd.setTabCompleter(new SwitchTabCompleter());
        }

    }

    @EventHandler
    public void onPortal(PlayerPortalEvent e) {
        if (e.getTo() == null) return;
        if (e.getTo().getWorld() == null) return;
        boolean creativeBypass = configuration.getBoolean("creative-bypass", true);
        if (creativeBypass && e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) return;
        if (e.getTo().getWorld().getEnvironment() == World.Environment.THE_END) {
            boolean allowEnd = configuration.getBoolean("allow-end", true);
            if (!allowEnd) e.setCancelled(true);
            return;
        }
        if (e.getTo().getWorld().getEnvironment() == World.Environment.NETHER) {
            boolean allowNether = configuration.getBoolean("allow-nether", true);
            if (!allowNether) e.setCancelled(true);
            return;
        }
        if (e.getTo().getWorld().getEnvironment() == World.Environment.NORMAL) {
            boolean allowNormal = configuration.getBoolean("allow-overworld", true);
            if (!allowNormal) e.setCancelled(true);
        }
    }
}
