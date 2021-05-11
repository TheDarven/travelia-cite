package fr.thedarven.traveliacite.listener.listeners;

import fr.thedarven.traveliacite.Cite;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class ArmorStandInteractListener implements Listener {

    private final Cite main;

    public ArmorStandInteractListener(Cite main) {
        this.main = main;
    }

    @EventHandler
    public void onInteractWithArmorStand(PlayerInteractAtEntityEvent event) {
        if (event.getRightClicked() instanceof ArmorStand) {
            ArmorStand armorStand = (ArmorStand) event.getRightClicked();
            if (armorStand.isInvulnerable()) {
                event.setCancelled(true);
            }
        }
    }

}
