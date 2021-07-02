package fr.thedarven.traveliacite.player.model;

import fr.thedarven.traveliacite.Cite;
import fr.thedarven.traveliacite.hologram.IHologramEntityValue;
import fr.thedarven.traveliacite.team.model.TeamCite;

import java.io.Serializable;
import java.util.UUID;

public class PlayerCite implements IHologramEntityValue, Serializable {

    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unused")
	private transient Cite main;
    private transient TeamCite team;
    private transient boolean dirty = false;
    
    private final UUID uuid;
    private String name;
    private int emeralds;

    public PlayerCite(UUID uuid, String name, Cite main) {
        this.main = main;
        this.uuid = uuid;
        this.name = name;
        this.emeralds = 0;
        this.dirty = true;
    }

    public void addEmeralds(int amount) {
        setEmeralds(this.emeralds + amount);
    }

    public void setEmeralds(int amount) {
        this.emeralds = Math.max(amount, 0);
        this.setDirty(true);
    }

    public int getEmeralds() {
        return this.emeralds;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public TeamCite getTeam() {
        return team;
    }

    public void setTeam(TeamCite team) {
        this.team = team;
    }

    @Override
    public int getValue() {
        return this.emeralds;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void setMain(Cite main) {
        this.main = main;
    }

    public void setDirty(boolean d) {
    	this.dirty = d;
    }
    
	@Override
	public boolean isDirty() {
		return this.dirty;
	}
}
