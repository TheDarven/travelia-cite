package fr.thedarven.traveliacite.listener.listeners;

import fr.thedarven.traveliacite.Cite;
import fr.thedarven.traveliacite.player.model.PlayerCite;
import fr.thedarven.traveliacite.team.model.TeamCite;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Objects;

public class JoinQuitListener implements Listener {

    private final Cite main;

    public JoinQuitListener(Cite main) {
        this.main = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        playerJoin(event.getPlayer());
    }

    public void playerJoin(Player player) {
        PlayerCite playerCite = this.main.getPlayerManager().getAndCreatePlayerCiteByPlayer(player);
        player.setScoreboard(this.main.getTeamManager().getScoreboardTeam());

        TeamCite teamCite = this.main.getTeamManager().getTeamOfUuid(player.getUniqueId());
        if (Objects.nonNull(teamCite)) {
            teamCite.joinScoreboardTeam(player);
            teamCite.joinTeam(playerCite);
        }
    }

}
