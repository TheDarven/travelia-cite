package fr.thedarven.traveliacite;

import fr.thedarven.traveliacite.config.ConfigurationManager;
import fr.thedarven.traveliacite.hologram.HologramManager;
import fr.thedarven.traveliacite.listener.ListenerManager;
import fr.thedarven.traveliacite.listener.listeners.JoinQuitListener;
import fr.thedarven.traveliacite.player.PlayerManager;
import fr.thedarven.traveliacite.team.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Cite extends JavaPlugin {

    private PlayerManager playerManager;
    private TeamManager teamManager;
    private HologramManager hologramManager;
    private ConfigurationManager configurationManager;
    private ListenerManager listenerManager;

    public void onEnable() {
        this.saveDefaultConfig();

        this.playerManager = new PlayerManager(this);
        this.teamManager = new TeamManager(this);
        this.hologramManager = new HologramManager(this);

        this.configurationManager = new ConfigurationManager(this);
        this.configurationManager.loadConfig();

        this.listenerManager = new ListenerManager(this);

        JoinQuitListener joinQuitListener = this.listenerManager.getJoinQuitListener();
        Bukkit.getOnlinePlayers().forEach(joinQuitListener::playerJoin);

        // Log changements : action

        /* this.playerManager.createPlayerCite(UUID.fromString("b5c58e19-ad15-4b95-be3d-054da2e706f9"), "GaaZeR");
        this.playerManager.createPlayerCite(UUID.fromString("7260c4f6-2a1c-4145-9079-dc4f42985fee"), "luto45");
        this.playerManager.createPlayerCite(UUID.fromString("b634ae35-96fa-4d27-818f-e83dc224ac70"), "drmanyx");
        this.playerManager.createPlayerCite(UUID.fromString("d00bbcf3-cc19-420c-945b-0596565dd3f1"), "Feezox");
        this.playerManager.createPlayerCite(UUID.fromString("9ef91a20-cc78-46ed-be1a-cb3c4d994847"), "Flush_");
        this.playerManager.createPlayerCite(UUID.fromString("2e3d0e6a-4685-45b2-8f7e-daee46868cb9"), "Grimbergame");
        this.playerManager.createPlayerCite(UUID.fromString("1f9977bb-fb30-4c42-a7d2-6b5f333c2d06"), "Mcspayne");
        this.playerManager.createPlayerCite(UUID.fromString("4ed83f1b-1184-448d-8c24-fbc524eff2d7"), "MrCalin");
        this.playerManager.createPlayerCite(UUID.fromString("cf8d8e87-853e-435e-b68f-920a3b7ea0e1"), "Mus");
        this.playerManager.createPlayerCite(UUID.fromString("df00d23f-a0c5-4c2f-8e56-04159e76d414"), "Nowdek");
        this.playerManager.createPlayerCite(UUID.fromString("e9e8e0dc-6edc-4698-9aa6-a466384eee9a"), "Tanks");
        this.playerManager.createPlayerCite(UUID.fromString("06345827-c4cd-4f5d-b0ad-a4ce1ecb4576"), "Raphael_0");
        this.playerManager.createPlayerCite(UUID.fromString("8e7c4e34-db7d-472b-bbe5-a3c4765909fd"), "Dragon3099"); */
    }

    public void onDisable() {
        this.playerManager.savePlayers();
        this.hologramManager.removeAll();
    }

    public ConfigurationManager getConfigurationManager() {
        return this.configurationManager;
    }

    public PlayerManager getPlayerManager() {
        return this.playerManager;
    }

    public TeamManager getTeamManager() {
        return this.teamManager;
    }

    public HologramManager getHologramManager() {
        return this.hologramManager;
    }

}
