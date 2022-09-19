package xyz.bobkinn_.blockportals;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import javax.annotation.Nonnull;

public class ReloadCmd implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, @Nonnull Command command, @Nonnull String label,@Nonnull String[] args) {
        BlockPortals.reload();
        String msg = BlockPortals.configuration.getString("messages.reloaded","&aConfig reloaded!");
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
        return true;
    }
}
