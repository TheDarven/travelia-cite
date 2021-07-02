package fr.thedarven.traveliacite.listener.listeners;

import java.util.Objects;

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
import org.bukkit.inventory.meta.ItemMeta;

import fr.thedarven.traveliacite.Cite;
import fr.thedarven.traveliacite.player.model.PlayerCite;

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
                playerInventory.forEach(i -> tryConsumeStack(i, player, clickedPnjLocation));  
            }   
        }
    }
    
    public void tryConsumeStack(ItemStack stack, Player player, Location clickedPnjLocation) {
    	
    	if(!isStackValid(stack)) return;
    	int amount = stack.getAmount();
    	
    	if (amount > 0) {
            PlayerCite playerCite = this.main.getPlayerManager().getAndCreatePlayerCiteByPlayer(player);
            System.out.println("[Cite] " + player.getName() + " a déposé " + amount + " points. (" + playerCite.getEmeralds() + "P)");
            playerCite.addEmeralds(amount);
            player.sendMessage("§2" + amount + "§a points ont été déposés sur votre compte.");
            Objects.requireNonNull(clickedPnjLocation.getWorld()).spawnParticle(Particle.VILLAGER_HAPPY, clickedPnjLocation.add(0, 2, 0), 10, 0.5, 0.25, 0.5);
        } else player.sendMessage("§eVous n'avez aucun point a déposer sur vous.");
    	
    	// Remove stack, but with more steps, just in case, to hopefully prevent unintentional duplication glitch
    	player.getInventory().remove(stack);
    	stack.setAmount(0);
    	stack.setType(Material.AIR);
    	
    }
    
    public boolean isStackValid(ItemStack stack) {
    	if(stack.getType() == Material.HAY_BLOCK) return false;
    	if(stack.getAmount() <= 0) return false;
    	ItemMeta meta = stack.getItemMeta();
    	if(meta.getCustomModelData() != 789) return false;
    	// ADD SOME CHECKS HERE if needed
    	return true;
    }

}
