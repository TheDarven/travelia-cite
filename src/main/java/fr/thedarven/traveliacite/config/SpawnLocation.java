package fr.thedarven.traveliacite.config;

import org.bukkit.Location;

import java.util.Objects;

public class SpawnLocation {

    private Location location1;
    private Location location2;

    public SpawnLocation(Location location1, Location location2) {
        this.location1 = location1;
        this.location2 = location2;
    }

    public SpawnLocation() { }

    public boolean isBetween(Location compare) {
        return Objects.nonNull(this.location1) && Objects.nonNull(this.location2) && location1.getWorld() == compare.getWorld()
                && Math.min(location1.getBlockX(), location2.getBlockX()) <= compare.getBlockX()
                && Math.min(location1.getBlockY(), location2.getBlockY()) <= compare.getBlockY()
                && Math.min(location1.getBlockZ(), location2.getBlockZ()) <= compare.getBlockZ()
                && Math.max(location1.getBlockX(), location2.getBlockX()) >= compare.getBlockX()
                && Math.max(location1.getBlockY(), location2.getBlockY()) >= compare.getBlockY()
                && Math.max(location1.getBlockZ(), location2.getBlockZ()) >= compare.getBlockZ();
    }

    public void setLocation1(Location location1) {
        this.location1 = location1;
    }

    public void setLocation2(Location location2) {
        this.location2 = location2;
    }
}
