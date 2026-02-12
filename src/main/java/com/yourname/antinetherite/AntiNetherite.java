package com.yourname.antinetherite;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class AntiNetherite extends JavaPlugin implements Listener {

    private final String BYPASS_PERM = "antinetherite.bypass";

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("AntiNetherite enabled - No more Netherite!");
    }

    // 1. Blocks the Smithing Table from producing Netherite items
    @EventHandler
    public void onSmith(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (player.hasPermission(BYPASS_PERM)) return;

        if (event.getView().getType() == InventoryType.SMITHING) {
            ItemStack result = event.getCurrentItem();
            if (result != null && result.getType().name().contains("NETHERITE")) {
                event.setCancelled(true);
                player.sendMessage(ChatColor.RED + "Netherite gear is banned on this server!");
            }
        }
    }

    // 2. Vaporizes Netherite items if a player tries to click/move them
    @EventHandler
    public void onInventoryInteract(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (player.hasPermission(BYPASS_PERM)) return;

        ItemStack item = event.getCurrentItem();
        if (item != null && item.getType().name().contains("NETHERITE")) {
            event.setCurrentItem(new ItemStack(Material.AIR));
            player.sendMessage(ChatColor.DARK_RED + "The forbidden metal turned to ash!");
        }
    }

    // 3. Wipes Netherite from inventory when a player joins
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission(BYPASS_PERM)) return;

        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.getType().name().contains("NETHERITE")) {
                item.setAmount(0);
            }
        }
    }
            }
