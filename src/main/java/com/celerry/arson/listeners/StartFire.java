package com.celerry.arson.listeners;

import com.celerry.arson.Arson;
import com.celerry.arson.utilities.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class StartFire implements Listener {

    @EventHandler
    public void startFire(PlayerInteractEvent event) {

        if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = event.getPlayer();

            if(event.getHand().equals(EquipmentSlot.OFF_HAND)) {
                return;
            }

            if(player.getInventory().getItemInMainHand().isSimilar(Utils.getFlintAndSteel())) {
                event.setCancelled(true);
                Block block = event.getClickedBlock().getRelative(BlockFace.UP);
                if(block.getMetadata("doused").isEmpty()) { // If it has no data (so its false), exit that mf
                    player.sendMessage(ChatColor.RED+"That block is not doused with fuel");
                    return;
                }
                if(block.getType() != Material.AIR) { // If block is no longer meant to be doused, undouse that mf
                    block.removeMetadata("doused", Arson.getPlugin());
                    return;
                }

                block.removeMetadata("doused", Arson.getPlugin());
                block.setType(Material.FIRE);
            }

        }
    };
}