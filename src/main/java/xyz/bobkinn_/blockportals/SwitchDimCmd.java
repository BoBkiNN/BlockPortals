package xyz.bobkinn_.blockportals;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import javax.annotation.Nonnull;
import java.io.IOException;

public class SwitchDimCmd implements CommandExecutor {
    public static String color(String text){
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender sender,@Nonnull Command command,@Nonnull String label, String[] args) {
        String incorrect = BlockPortals.configuration.getString("messages.incorrect-switch", "&cIncorrect world or state!");
        if (args.length == 0){
            sender.sendMessage(color(incorrect));
            return true;
        }
        String world = args[0];
        String opt = "allow-"+world;
        String msgA = BlockPortals.configuration.getString("messages.allowed", "&aPortals to &e%dim%&a allowed");
        msgA = msgA.replace("%dim%", world);
        String msgB = BlockPortals.configuration.getString("messages.blocked", "&cPortals to &e%dim%&c blocked");
        msgB = msgB.replace("%dim%", world);
        boolean correctWorld = world.equals("overworld") || world.equals("nether") || world.equals("end");
        if (args.length == 1){
            if (correctWorld){
                boolean allow = !BlockPortals.configuration.getBoolean(opt, true);
                if (allow){
                    sender.sendMessage(color(msgA));
                } else {
                    sender.sendMessage(color(msgB));
                }
                BlockPortals.configuration.set(opt, allow);
            } else {
                sender.sendMessage(color(incorrect));
                return true;
            }
        } else {
            if (!correctWorld){
                sender.sendMessage(color(incorrect));
                return true;
            }
            String state = args[1];

            if (state.equalsIgnoreCase("on")){
                boolean allow = BlockPortals.configuration.getBoolean(opt, true);
                if (allow){
                    String alreadyA = BlockPortals.configuration.getString("messages.allowed-already", "&aPortals to &e%dim%&d already&a allowed");
                    sender.sendMessage(color(alreadyA.replace("%dim%", world)));
                } else {
                    BlockPortals.configuration.set(opt, true);
                    sender.sendMessage(color(msgA));
                }
            } else if (state.equalsIgnoreCase("off")){
                boolean allow = BlockPortals.configuration.getBoolean(opt, true);
                if (!allow){
                    String alreadyB = BlockPortals.configuration.getString("messages.blocked-already", "&cPortals to &e%dim%&d already&c blocked");
                    sender.sendMessage(color(alreadyB.replace("%dim%", world)));
                } else {
                    BlockPortals.configuration.set(opt, false);
                    sender.sendMessage(color(msgB));
                }
            } else {
                sender.sendMessage(color(incorrect));
                return true;
            }
        }
        try {
            BlockPortals.configuration.save(BlockPortals.configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
