package fr.thedarven.traveliacite.hologram;

import fr.thedarven.traveliacite.Cite;
import fr.thedarven.traveliacite.hologram.model.Hologram;
import fr.thedarven.traveliacite.hologram.model.PlayerHologram;
import fr.thedarven.traveliacite.hologram.model.PlayerTeamHologram;
import fr.thedarven.traveliacite.hologram.model.TeamHologram;
import fr.thedarven.traveliacite.team.model.TeamCite;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class HologramManager {

    public final static int NB_PLAYERS = 10;

    private final Cite main;
    private final List<Hologram<?>> holograms;

    public HologramManager(Cite main) {
        this.main = main;
        this.holograms = new ArrayList<>();
    }

    public void initHolograms(Location teamsHologram, String teamsTitle, Location playersHologram, String playersTitle, int nbPlayers) {
        this.main.getTeamManager().getTeamsList().forEach(TeamCite::countAmountOfEmeralds);
        this.holograms.add(new PlayerHologram(this.main, this, playersHologram, nbPlayers, playersTitle));
        this.holograms.add(new TeamHologram(this.main, this, teamsHologram, teamsTitle));
        this.main.getTeamManager().getTeamsList().forEach(teamCite ->
                this.holograms.add(new PlayerTeamHologram(this.main, this, teamCite.getHologramLocation(), teamCite))
        );
    }

    public void updateHolograms() {
        this.main.getTeamManager().getTeamsList().forEach(TeamCite::countAmountOfEmeralds);
        this.holograms.forEach(Hologram::reload);
    }

    public void removeAll() {
        this.holograms.forEach(Hologram::remove);
        this.holograms.clear();
    }

}
