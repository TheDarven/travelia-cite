package fr.thedarven.traveliacite.listener.listeners;

import fr.thedarven.traveliacite.Cite;
import fr.thedarven.traveliacite.player.model.PlayerCite;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Objects;

public class DeposePointListener implements Listener {

    private final Cite main;

    public DeposePointListener(Cite main) {
        this.main = main;
    }

    @EventHandler
    public void onDeposePoint(PlayerInteractAtEntityEvent e) {
        Player player = e.getPlayer();
        Entity entity = e.getRightClicked();

        if (entity instanceof Villager) {
            Location pnjDeposeLocation = this.main.getConfigurationManager().getPnjDeposeLocation();
            Location clickedPnjLocation = entity.getLocation();
            if (pnjDeposeLocation.getWorld() == clickedPnjLocation.getWorld() && pnjDeposeLocation.getBlockX() == clickedPnjLocation.getBlockX()
                    && Math.abs(pnjDeposeLocation.getBlockY() - clickedPnjLocation.getBlockY()) < 2
                    && pnjDeposeLocation.getBlockZ() == clickedPnjLocation.getBlockZ()) {
                e.setCancelled(true);

                PlayerInventory playerInventory = player.getInventory();
                int totalPoints = 0;

                for (int i = 0; i < playerInventory.getSize(); i++) {
                    ItemStack item = playerInventory.getItem(i);
                    if (Objects.isNull(item) || item.getType() == Material.AIR) {
                        continue;
                    }

                    int points = 0;

                    if (item.getType() == Material.EMERALD) {
                        points = 1;
                    } else if (item.getType() == Material.EMERALD_BLOCK) {
                        points = 9;
                    } else {
                        continue;
                    }

                    totalPoints += item.getAmount() * points;
                    playerInventory.setItem(i, null);
                }

                if (totalPoints > 0) {
                    PlayerCite playerCite = this.main.getPlayerManager().getAndCreatePlayerCiteByPlayer(player);
                    System.out.println("[Cite] " + player.getName() + " a déposé " + totalPoints + "emeraudes. (" + playerCite.getEmeralds() + "E)");
                    playerCite.addEmeralds(totalPoints);
                    player.sendMessage("§2" + totalPoints + "§a émeraudes on été déposées sur votre compte.");
                    Objects.requireNonNull(clickedPnjLocation.getWorld())
                            .spawnParticle(Particle.VILLAGER_HAPPY, clickedPnjLocation.add(0, 2, 0),10, 0.5, 0.25, 0.5);
                } else {
                    player.sendMessage("§eVous n'avez aucune émeraudes on déposer sur vous.");
                }
            }
        }
    }

}
