package fr.thedarven.traveliacite.team;

import fr.thedarven.traveliacite.Cite;
import fr.thedarven.traveliacite.team.model.TeamCite;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import javax.annotation.Nullable;
import java.util.*;

public class TeamManager {

    private final Cite main;
    private final Map<String, TeamCite> teams = new HashMap<>();
    private Scoreboard scoreboardTeam;

    public TeamManager(Cite main) {
        this.main = main;

        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        if (Objects.nonNull(scoreboardManager)) {
            this.scoreboardTeam = scoreboardManager.getNewScoreboard();
        }
    }

    public void createTeam(String name, String color, Location hologramLocation, String hologramTitle, List<UUID> players) {
        if (!this.teams.containsKey(name)) {
            this.teams.put(name, new TeamCite(name, color, hologramLocation, hologramTitle, players, this));
        }
    }

    public TeamCite getTeam(String name) {
        return this.teams.get(name);
    }

    public @Nullable TeamCite getTeamOfUuid(UUID uuid) {
        return this.teams.values().stream()
                .filter(team -> team.hasPlayer(uuid))
                .findFirst()
                .orElse(null);
    }

    public List<TeamCite> getTeamsList() {
        return new ArrayList<>(this.teams.values());
    }

    public Cite getMain() {
        return this.main;
    }

    public Scoreboard getScoreboardTeam() {
        return this.scoreboardTeam;
    }

}
