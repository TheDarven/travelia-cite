package fr.thedarven.traveliacite.hologram.model;

import fr.thedarven.traveliacite.Cite;
import fr.thedarven.traveliacite.hologram.HologramEntityValue;
import fr.thedarven.traveliacite.hologram.HologramManager;
import fr.thedarven.traveliacite.player.PlayerManager;
import fr.thedarven.traveliacite.player.model.PlayerCite;
import fr.thedarven.traveliacite.player.model.PlayerCiteComparator;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import java.util.List;
import java.util.Objects;

public class PlayerHologram extends Hologram<PlayerCite> {

    private final static String HOLOGRAM_FORMAT = "{name}: " + ChatColor.GOLD + "{amount}" + ChatColor.DARK_GREEN + " Points";
    private final int nbPlayers;

    public PlayerHologram(Cite cite, HologramManager hologramManager, Location location, int nbPlayers, String title) {
        super(cite, hologramManager, location, title);
        this.nbPlayers = nbPlayers;
        reload();
    }

    public void reload() {
        PlayerManager playerManager = this.main.getPlayerManager();
        List<PlayerCite> playersList = playerManager.getPlayersList();
        playersList.sort(new PlayerCiteComparator());

        hideAllHolograms();

        int nbDisplayPlayers = Math.min(this.nbPlayers, playersList.size());
        for (int i = 0; i < nbDisplayPlayers; i++) {
            PlayerCite playerCite = playersList.get(i);
            if(!playerCite.isDirty()) continue;
            HologramEntityValue<PlayerCite> entity = this.entities.get(playerCite);
            if (Objects.isNull(entity)) {
                entity = new HologramEntityValue<>(HOLOGRAM_FORMAT, playerCite, this.location.clone());
                this.entities.put(playerCite, entity);
            }
            entity.updateName();
            setPosition(i + 1, entity);
            entity.show();
            playerCite.setDirty(false);
        }
    }

}
