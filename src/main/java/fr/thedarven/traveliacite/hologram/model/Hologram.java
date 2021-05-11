package fr.thedarven.traveliacite.hologram.model;

import fr.thedarven.traveliacite.Cite;
import fr.thedarven.traveliacite.hologram.HologramEntity;
import fr.thedarven.traveliacite.hologram.HologramEntityValue;
import fr.thedarven.traveliacite.hologram.IHologramEntityValue;
import fr.thedarven.traveliacite.hologram.HologramManager;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class Hologram<T extends IHologramEntityValue> {

    public static final double SIZE_OF_LINE = 0.5;

    protected final Cite main;
    protected final HologramManager hologramManager;
    protected final Location location;

    protected final Map<T, HologramEntityValue<T>> entities = new HashMap<>();
    protected HologramEntity title;

    public Hologram(Cite cite, HologramManager hologramManager, Location location, String title) {
        this.main = cite;
        this.hologramManager = hologramManager;
        this.location = location;
        initTitle(title);
    }

    private void initTitle(String titleValue) {
        this.title = new HologramEntity(titleValue, this.location.clone());
        this.title.show();
    }

    public abstract void reload();

    public final void remove() {
        this.entities.values().forEach(HologramEntity::remove);
        this.entities.clear();

        if (Objects.nonNull(this.title)) {
            this.title.remove();
            this.title = null;
        }
    }

    protected void setPosition(int position, HologramEntityValue<T> entity) {
        Location newLocation = this.location.clone().add(0, -Hologram.SIZE_OF_LINE * position, 0);
        entity.setLocation(newLocation, this.main);
    }

    protected void hideAllHolograms() {
        this.entities.values().forEach(HologramEntity::hide);
    }

}
