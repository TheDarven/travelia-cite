package fr.thedarven.traveliacite.hologram.model;

import fr.thedarven.traveliacite.Cite;
import fr.thedarven.traveliacite.hologram.HologramEntityValue;
import fr.thedarven.traveliacite.hologram.HologramManager;
import fr.thedarven.traveliacite.player.model.PlayerCite;
import fr.thedarven.traveliacite.player.model.PlayerCiteComparator;
import fr.thedarven.traveliacite.team.model.TeamCite;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import java.util.List;
import java.util.Objects;

public class PlayerTeamHologram extends Hologram<PlayerCite> {

    private final static String HOLOGRAM_FORMAT = "{name}: " + ChatColor.GOLD + "{amount}" + ChatColor.DARK_GREEN + " Emeraudes";
    private final TeamCite teamCite;


    public PlayerTeamHologram(Cite cite, HologramManager hologramManager, Location location, TeamCite teamCite) {
        super(cite, hologramManager, location, teamCite.getFormattedHologramTitle());
        this.teamCite = teamCite;
        reload();
    }

    @Override
    public void reload() {
        List<PlayerCite> playersList = this.teamCite.getPlayers();
        playersList.sort(new PlayerCiteComparator());

        hideAllHolograms();

        int nbDisplayPlayers = playersList.size();
        for (int i = 0; i < nbDisplayPlayers; i++) {
            PlayerCite playerCite = playersList.get(i);
            HologramEntityValue<PlayerCite> entity = this.entities.get(playerCite);
            if (Objects.isNull(entity)) {
                entity = new HologramEntityValue<>(HOLOGRAM_FORMAT, playerCite, this.location.clone());
                this.entities.put(playerCite, entity);
            }
            entity.updateName();
            setPosition(i + 1, entity);
            entity.show();
        }
    }
}
