package com.celerry.arson.utilities;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static ItemStack getLiquidFuel() {
        ItemStack fuel = new ItemStack(Material.SPLASH_POTION);
        PotionMeta meta = (PotionMeta)fuel.getItemMeta();
        meta.setColor(Color.ORANGE);
        meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setDisplayName(ChatColor.GOLD+"Liquid Fuel");
        fuel.setItemMeta(meta);

        return fuel;
    }

    public static ItemStack getFlintAndSteel() {
        ItemStack fas = new ItemStack(Material.FLINT_AND_STEEL);
        ItemMeta fasm = fas.getItemMeta();
        fasm.setUnbreakable(true);
        fasm.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        fasm.setDisplayName(ChatColor.GOLD+"Arsonist's Lighter");
        fas.setItemMeta(fasm);

        return fas;
    }

    public static List<Block> getNearbyBlocks(Location location, int radius) {
        List<Block> blocks = new ArrayList<Block>();
        for(int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
                for(int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
                    blocks.add(location.getWorld().getBlockAt(x, location.getBlockY(), z));
                }
            }
        return blocks;
    }

}
