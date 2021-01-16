package com.celerry.arson.listeners;

import com.celerry.arson.Arson;
import com.celerry.arson.utilities.Utils;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

import static com.sk89q.worldguard.bukkit.BukkitUtil.toVector;
public class DouseBlocks implements Listener {

    @EventHandler
    public void potionSplash(PotionSplashEvent event) {
        String name = event.getPotion().getItem().getItemMeta().getDisplayName();
        ProjectileSource source = event.getPotion().getShooter();
        if (!(source instanceof Player)) {
            return; // not thrown by player, ignore
        }

        Player player = (Player) source;
        Location location = event.getPotion().getLocation();
        WorldGuardPlugin worldGuard = WGBukkit.getPlugin();
        LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(player);

        RegionManager regionManager = worldGuard.getRegionManager(location.getWorld());
        ApplicableRegionSet set = regionManager.getApplicableRegions(toVector(location));

        boolean mycelium_spread = set.testState(localPlayer, DefaultFlag.MYCELIUM_SPREAD);
        if (!mycelium_spread) {
            player.sendMessage(ChatColor.RED+""+ChatColor.BOLD+"Hey! "+ChatColor.GRAY+"Sorry, but arson is not allowed here.");
            return;
        }

        if(name.equalsIgnoreCase(ChatColor.GOLD + "Liquid Fuel")) {
            List<Block> doused = Utils.getNearbyBlocks(event.getPotion().getLocation(), Arson.getPlugin().getConfig().getInt("radius"));
            doused.forEach(block -> {
                if(block.getType() == Material.AIR) {
                    if(block.getRelative(BlockFace.DOWN).getType() != Material.AIR && block.getRelative(BlockFace.DOWN).getType().isSolid()) { // Should stop "ghost platforms" from appearing (splash potions are weird.)
                        if(!block.getMetadata("doused").isEmpty()) {
                            return;
                        }
                        // Check for mycelium-spread flag (stops arson)
                        RegionManager worldGuardRegionManager = worldGuard.getRegionManager(block.getWorld());
                        ApplicableRegionSet setRegion = worldGuardRegionManager.getApplicableRegions(toVector(block.getLocation()));

                        boolean mycelium_spread_sep = setRegion.testState(localPlayer, DefaultFlag.MYCELIUM_SPREAD);
                        if (!mycelium_spread_sep) {
                            return;
                        }

                        // Marks the block as doused
                        block.setMetadata("doused", new FixedMetadataValue(Arson.getPlugin(), true));

                        int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(Arson.getPlugin(), new BukkitRunnable() {
                            @Override
                            public void run() {
                                if(block.getMetadata("doused").isEmpty()) { // If block is no longer doused (by being lit on fire), stop particles
                                    return;
                                }
                                player.getWorld().spawnParticle(Particle.FLAME, new Location(block.getWorld(), block.getX()+0.5, block.getY(), block.getZ()+0.5), 25, 0.1, 0.1, 0.1, 0);
                            }
                        }, 0, 20);

                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                block.removeMetadata("doused", Arson.getPlugin());
                                Bukkit.getScheduler().cancelTask(id);
                            }
                        }.runTaskLater(Arson.getPlugin(), 600);

                    }

                }
            });

        }
    }
}
