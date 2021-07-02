package fr.thedarven.traveliacite.hologram;

import org.bukkit.Location;

import java.util.Objects;

public class HologramEntityValue<T extends IHologramEntityValue> extends HologramEntity {

    protected final T entity;

    public HologramEntityValue(String format, T entity, Location location) {
        super(format, location);
        this.entity = entity;
        updateName();
    }

    public void updateName() {
        String customName = this.format;
        if (Objects.nonNull(this.entity)) {
            customName = this.format.replace("{amount}", String.valueOf(this.entity.getValue()));
            customName = customName.replace("{name}", this.entity.getName());
        }
        this.armorStand.setCustomName(customName);
    }
    
    

}
