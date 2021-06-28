package fr.thedarven.traveliacite.team.model;

import fr.thedarven.traveliacite.hologram.IHologramEntityValue;
import fr.thedarven.traveliacite.player.PlayerManager;
import fr.thedarven.traveliacite.player.model.PlayerCite;
import fr.thedarven.traveliacite.team.TeamManager;
import fr.thedarven.traveliacite.utils.ColorEnum;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.*;

public class TeamCite implements IHologramEntityValue {

    private final TeamManager teamManager;

    private Team team;
    private final String name;
    private final String color;
    private final Location hologramLocation;
    private final String hologramTitle;
    private final List<UUID> playersUUID;
    private final List<PlayerCite> players;
    private int lastCountOfEmeralds;

    public TeamCite(String name, String color, Location hologramLocation, String hologramTitle, List<UUID> players, TeamManager teamManager) {
        this.name = name;
        this.color = color;
        this.hologramLocation = hologramLocation;
        this.hologramTitle = hologramTitle;
        this.playersUUID = players;
        this.players = new ArrayList<>();
        this.teamManager = teamManager;
        this.lastCountOfEmeralds = 0;

        initPlayerCite();

        createScoreboardTeam();
    }

    public int getAmountOfEmeralds() {
        return this.lastCountOfEmeralds;
    }

    public Location getHologramLocation() {
        return this.hologramLocation;
    }

    public String getFormattedHologramTitle() {
        return this.hologramTitle.replace("{name}", this.name);
    }

    private void initPlayerCite() {
        PlayerManager playerManager = this.teamManager.getMain().getPlayerManager();
        for (UUID uuid: this.playersUUID) {
            Optional<PlayerCite> oPlayerCite = playerManager.getPlayerCiteByUUID(uuid);
            oPlayerCite.ifPresent(this::joinTeam);
        }
    }

    private void createScoreboardTeam() {
        Scoreboard scoreboard = this.teamManager.getScoreboardTeam();
        if (Objects.nonNull(scoreboard)) {
            this.team = scoreboard.registerNewTeam(this.name);
            this.team.setPrefix(this.color);
            this.team.setSuffix("§f");
            this.team.setColor(ColorEnum.getByColor(this.color).getChatColor());
        }
    }

    public boolean hasPlayer(UUID uuid) {
        return this.playersUUID.stream().anyMatch(player -> player.equals(uuid));
    }

    public void joinTeam(PlayerCite playerCite) {
        if (!this.players.contains(playerCite)) {
            this.players.add(playerCite);
        }
        playerCite.setTeam(this);
    }

    public void joinScoreboardTeam(Player player) {
        this.team.addEntry(player.getName());
    }

    public void countAmountOfEmeralds() {
        this.lastCountOfEmeralds = 0;
        this.players.forEach(playerCite -> this.lastCountOfEmeralds += playerCite.getEmeralds());
    }

    public List<PlayerCite> getPlayers() {
        return new ArrayList<>(this.players);
    }

    @Override
    public int getValue() {
        return this.lastCountOfEmeralds;
    }

    @Override
    public String getName() {
        return this.color + this.name + "§r§f";
    }
}
