package com.celerry.arson.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class ArsonCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command");
            return true;
        }
        Player player = (Player) sender;
        if(args.length == 0 || args[0].equalsIgnoreCase("help")) {
            player.sendMessage(ChatColor.GRAY+"Arson by "+ChatColor.GREEN+"celerry");
            player.sendMessage(ChatColor.GREEN+"/arson recipe [item]");
            player.sendMessage(ChatColor.GREEN+"Items:");
            player.sendMessage(ChatColor.GRAY+"- "+ChatColor.GREEN+"MagmaCream");
            player.sendMessage(ChatColor.GRAY+"- "+ChatColor.GREEN+"Lighter");
            player.sendMessage(ChatColor.GRAY+"- "+ChatColor.GREEN+"LiquidFuel");
            return true;
        }
        if(args[0].equalsIgnoreCase("recipe")) {
            if(args.length < 2) {
                player.sendMessage(ChatColor.RED+"An error occurred, "+ChatColor.GREEN+"invalid recipe");
                return true;
            }
            if(args.length > 1) {
                switch (args[1].toLowerCase()) {
                    case "magmacream":
                        player.sendMessage(ChatColor.GRAY+"Cook blaze powder in a furnace");
                        return true;
                    case "lighter":
                        player.sendMessage(ChatColor.GRAY+"1 flint and steel, 8 coal in a crafting table");
                        return true;
                    case "liquidfuel":
                        player.sendMessage(ChatColor.GRAY+"1 water bottle, 1 gunpowder, 1 magma cream, and 2 charcoal in a crafting table");
                        return true;
                    default:
                        player.sendMessage(ChatColor.RED+"An error occurred, "+ChatColor.GREEN+"invalid recipe");
                        return true;
                }
            }
            } else {
                player.sendMessage(ChatColor.RED+"An error occurred, try "+ChatColor.GREEN+"writing an item");
                return true;
            }
        player.sendMessage(ChatColor.RED+"An error occurred, try "+ChatColor.GREEN+"/arson help");
        return true;
    }
}
