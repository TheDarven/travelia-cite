package fr.thedarven.traveliacite.hologram.model;

import fr.thedarven.traveliacite.Cite;
import fr.thedarven.traveliacite.hologram.HologramEntityValue;
import fr.thedarven.traveliacite.hologram.HologramManager;
import fr.thedarven.traveliacite.team.TeamManager;
import fr.thedarven.traveliacite.team.model.ModelCiteComparator;
import fr.thedarven.traveliacite.team.model.TeamCite;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import java.util.List;
import java.util.Objects;

public class TeamHologram extends Hologram<TeamCite> {

    private final static String HOLOGRAM_FORMAT = "{name}: " + ChatColor.GOLD + "{amount}" + ChatColor.DARK_GREEN + " Emeraudes";

    public TeamHologram(Cite cite, HologramManager hologramManager, Location location, String title) {
        super(cite, hologramManager, location, title);
        reload();
    }

    public void reload() {
        TeamManager teamManager = this.main.getTeamManager();
        List<TeamCite> teamsList = teamManager.getTeamsList();
        teamsList.sort(new ModelCiteComparator());

        hideAllHolograms();

        int nbDisplayPlayers = teamsList.size();
        for (int i = 0; i < nbDisplayPlayers; i++) {
            TeamCite teamCite = teamsList.get(i);
            HologramEntityValue<TeamCite> entity = this.entities.get(teamCite);
            if (Objects.isNull(entity)) {
                entity = new HologramEntityValue<>(HOLOGRAM_FORMAT, teamCite, this.location.clone());
                this.entities.put(teamCite, entity);
            }
            entity.updateName();
            setPosition(i + 1, entity);
            entity.show();
        }
    }
}
