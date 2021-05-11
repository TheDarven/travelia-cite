package fr.thedarven.traveliacite.listener;

import fr.thedarven.traveliacite.Cite;
import fr.thedarven.traveliacite.listener.commands.PointsCommand;
import fr.thedarven.traveliacite.listener.listeners.ArmorStandInteractListener;
import fr.thedarven.traveliacite.listener.listeners.JoinQuitListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import java.util.Objects;

public class ListenerManager {

    private final Cite main;
    private JoinQuitListener joinQuitListener;

    public ListenerManager(Cite main) {
        this.main = main;
        initEvents();
        initCommands();
    }

    private void initEvents() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        this.joinQuitListener = new JoinQuitListener(this.main);
        pluginManager.registerEvents(this.joinQuitListener, this.main);
        pluginManager.registerEvents(new ArmorStandInteractListener(this.main), this.main);
    }

    private void initCommands() {
        PointsCommand pointsCommand = new PointsCommand(this.main);
        Objects.requireNonNull(this.main.getCommand("points")).setExecutor(pointsCommand);
        Objects.requireNonNull(this.main.getCommand("points")).setTabCompleter(pointsCommand);
    }

    public JoinQuitListener getJoinQuitListener() {
        return this.joinQuitListener;
    }

}
