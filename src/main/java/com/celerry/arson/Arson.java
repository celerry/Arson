package com.celerry.arson;

import com.celerry.arson.commands.ArsonCommand;
import com.celerry.arson.listeners.DouseBlocks;
import com.celerry.arson.listeners.StartFire;
import com.celerry.arson.utilities.Utils;
import org.bukkit.Bukkit;
import org.bukkit.CoalType;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.material.Coal;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Arson extends JavaPlugin {

    static Arson plugin;

    public static Plugin getPlugin() {
        return plugin;
    }

    public void onEnable() {

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        plugin = this;
        getLogger().info("Enabled Arson");

        getServer().getPluginManager().registerEvents(new StartFire(), this);
        getServer().getPluginManager().registerEvents(new DouseBlocks(), this);

        getCommand("arson").setExecutor(new ArsonCommand());

        // Stop fire tick
        getServer().getWorlds().forEach(world-> { world.setGameRuleValue("doFireTick", "false"); });

        // Stop spigot from whining
        NamespacedKey key;

        // Cooking fuel
        Material mat = Material.BLAZE_POWDER;
        FurnaceRecipe fuelRecipe = new FurnaceRecipe(new ItemStack(Material.MAGMA_CREAM), mat);
        Bukkit.addRecipe(fuelRecipe);

        // Crafting liquid fuel
        key = new NamespacedKey(plugin, "liquid_fuel");
        ShapelessRecipe liquidRecipe = new ShapelessRecipe(key, Utils.getLiquidFuel());
        liquidRecipe.addIngredient(Material.MAGMA_CREAM);
        liquidRecipe.addIngredient(Material.SULPHUR);
        liquidRecipe.addIngredient(Material.POTION);
        liquidRecipe.addIngredient(2, new Coal(CoalType.CHARCOAL));
        Bukkit.addRecipe(liquidRecipe);
        // Crafting flint and steel
        key = new NamespacedKey(plugin, "arsonists_lighter");
        ShapelessRecipe flintAndSteelRecipe = new ShapelessRecipe(key, Utils.getFlintAndSteel());
        flintAndSteelRecipe.addIngredient(Material.FLINT_AND_STEEL);
        flintAndSteelRecipe.addIngredient(8, new Coal(CoalType.COAL));
        Bukkit.addRecipe(flintAndSteelRecipe);
    }

    @Override
    public void onDisable() {

        getLogger().info("Disabled Arson");
    }

}
