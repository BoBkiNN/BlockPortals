package xyz.bobkinn_.blockportals;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SwitchTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(@Nonnull CommandSender sender,@Nonnull Command command,@Nonnull String label, String[] args) {
        if (args.length < 2){
            ArrayList<String> ret = new ArrayList<>();
            ret.add("end");
            ret.add("nether");
            ret.add("overworld");
            return ret;
        }
        if (args.length == 2){
            ArrayList<String> ret = new ArrayList<>();
            ret.add("on");
            ret.add("off");
            return ret;
        }
        return new ArrayList<>(Collections.emptyList());
    }
}
