package fr.thedarven.traveliacite.player.runnable;

import fr.thedarven.traveliacite.Cite;
import fr.thedarven.traveliacite.player.PlayerManager;
import org.bukkit.scheduler.BukkitRunnable;

public class SavePlayerRunnable extends BukkitRunnable {

    private final Cite main;
    private final PlayerManager playerManager;

    public SavePlayerRunnable(Cite main, PlayerManager playerManager) {
        this.main = main;
        this.playerManager = playerManager;
    }

    @Override
    public void run() {
        this.playerManager.savePlayers();
    }

}
