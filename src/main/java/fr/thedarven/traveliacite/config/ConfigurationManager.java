package fr.thedarven.traveliacite.config;

import fr.thedarven.traveliacite.Cite;
import fr.thedarven.traveliacite.hologram.HologramManager;
import fr.thedarven.traveliacite.team.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class ConfigurationManager {

    private final String DEFAULT_HOLOGRAM_TEAMS_TITLE = "§6Classement des équipes";
    private final String DEFAULT_HOLOGRAM_PLAYERS_TITLE = "§6Classement des joueurs";
    private final String DEFAULT_HOLOGRAM_PLAYERS_TEAM_TITLE = "§6Classement de l'équipe {name}";

    private final Cite main;

    public ConfigurationManager(Cite main) {
        this.main = main;
    }

    public void loadConfig() {
        loadTeams(this.main.getConfig());
        loadHolograms(this.main.getConfig());
    }

    private void loadTeams(FileConfiguration fileConfiguration) {
        ConfigurationSection teamsSection = fileConfiguration.getConfigurationSection("teams");
        if (Objects.isNull(teamsSection)) {
            return;
        }

        TeamManager teamManager = this.main.getTeamManager();
        for (String teamTag: teamsSection.getKeys(false)) {
            ConfigurationSection teamSection = fileConfiguration.getConfigurationSection("teams." + teamTag);
            if (Objects.isNull(teamSection)) {
                continue;
            }

            String name = teamSection.getString("name");
            String color = teamSection.getString("color");

            ConfigurationSection hologramSection = teamSection.getConfigurationSection("hologram");
            Location location = getLocationOfSection(hologramSection);
            String hologramTitle = DEFAULT_HOLOGRAM_PLAYERS_TEAM_TITLE;
            if (Objects.nonNull(hologramSection)) {
                hologramTitle = hologramSection.getString("title", DEFAULT_HOLOGRAM_PLAYERS_TEAM_TITLE);
            }

            List<String> playersUUID = teamSection.getStringList("players");

            if (Objects.isNull(name) || Objects.isNull(color) || Objects.isNull(location) || Objects.isNull(hologramTitle)) {
                continue;
            }

            List<UUID> players = playersUUID.stream().map(UUID::fromString).collect(Collectors.toList());

            teamManager.createTeam(name, color, location, hologramTitle, players);
        }
    }

    private void loadHolograms(FileConfiguration fileConfiguration) {
        ConfigurationSection teamsHologramSection = fileConfiguration.getConfigurationSection("hologram_teams");
        String teamsHologramTitle = DEFAULT_HOLOGRAM_TEAMS_TITLE;
        if (Objects.nonNull(teamsHologramSection)) {
            teamsHologramTitle = teamsHologramSection.getString("title", DEFAULT_HOLOGRAM_TEAMS_TITLE);
        }

        ConfigurationSection playersHologramSection = fileConfiguration.getConfigurationSection("hologram_players");
        String playersHologramTitle = DEFAULT_HOLOGRAM_PLAYERS_TITLE;
        if (Objects.nonNull(playersHologramSection)) {
            playersHologramTitle = playersHologramSection.getString("title", DEFAULT_HOLOGRAM_PLAYERS_TITLE);
        }

        int nbPlayers = HologramManager.NB_PLAYERS;
        if (Objects.nonNull(playersHologramSection)) {
            nbPlayers = playersHologramSection.getInt("nb_players", HologramManager.NB_PLAYERS);
        }
        this.main.getHologramManager().initHolograms(getLocationOfSection(teamsHologramSection), teamsHologramTitle,
                getLocationOfSection(playersHologramSection), playersHologramTitle, nbPlayers);
    }

    private Location getLocationOfSection(@Nullable ConfigurationSection section) {
        if (Objects.isNull(section)) {
            return null;
        }

        double x = section.getDouble("x");
        double y = section.getDouble("y");
        double z = section.getDouble("z");
        String worldName = section.getString("world_name");

        if (Objects.isNull(worldName)) {
            return null;
        }

        World world = Bukkit.getWorld(worldName);
        if (Objects.isNull(world)) {
            return null;
        }

        return new Location(world, x, y, z);
    }

}
