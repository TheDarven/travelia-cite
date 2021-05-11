package fr.thedarven.traveliacite.player.model;

import fr.thedarven.traveliacite.Cite;
import fr.thedarven.traveliacite.hologram.IHologramEntityValue;
import fr.thedarven.traveliacite.team.model.TeamCite;

import java.io.Serializable;
import java.util.UUID;

public class PlayerCite implements IHologramEntityValue, Serializable {

    private static final long serialVersionUID = 1L;

    private transient Cite main;
    private final UUID uuid;
    private String name;
    private int emeralds;
    private transient TeamCite team;

    public PlayerCite(UUID uuid, String name, Cite main) {
        this.main = main;
        this.uuid = uuid;
        this.name = name;
        this.emeralds = 0;
    }

    public void addEmeralds(int amount) {
        this.emeralds += amount;
        if (this.emeralds < 0) {
            this.emeralds = 0;
        }
        this.main.getHologramManager().updateHolograms();
    }

    public void setEmeralds(int amount) {
        this.emeralds = amount;
        if (this.emeralds < 0) {
            this.emeralds = 0;
        }
        this.main.getHologramManager().updateHolograms();
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
}
