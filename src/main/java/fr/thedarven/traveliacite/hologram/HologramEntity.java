package fr.thedarven.traveliacite.hologram;

import fr.thedarven.traveliacite.Cite;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.Objects;

public class HologramEntity {

    protected final String format;
    protected Location location;
    protected ArmorStand armorStand;

    public HologramEntity(String format, Location location) {
        this.format = format;
        this.location = location;
        createHologram();
    }

    private void createHologram() {
        this.armorStand = (ArmorStand) Objects.requireNonNull(this.location.getWorld()).spawnEntity(this.location, EntityType.ARMOR_STAND);
        this.armorStand.setGravity(false);
        this.armorStand.setBasePlate(false);
        this.armorStand.setInvulnerable(true);
        this.armorStand.setCustomNameVisible(false);
        this.armorStand.setVisible(false);
        this.armorStand.setSmall(true);
        this.armorStand.setCollidable(false);
        this.armorStand.setCanPickupItems(false);
        updateName();
    }

    public void show() {
        this.armorStand.setCustomNameVisible(true);
    }

    public void hide() {
        this.armorStand.setCustomNameVisible(false);
    }

    public void remove() {
        this.armorStand.remove();
        this.armorStand = null;
    }

    public void updateName() {
        this.armorStand.setCustomName(format);
    }

    public void setLocation(Location location, Cite main) {
        this.location = location;
        main.getServer().getScheduler().scheduleSyncDelayedTask(main, () -> armorStand.teleport(location, PlayerTeleportEvent.TeleportCause.PLUGIN), 5L);
    }

}
