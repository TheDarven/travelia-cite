package fr.thedarven.traveliacite.listener;

import fr.thedarven.traveliacite.Cite;
import fr.thedarven.traveliacite.listener.commands.DebugCommand;
import fr.thedarven.traveliacite.listener.commands.PointsCommand;
import fr.thedarven.traveliacite.listener.listeners.ArmorStandInteractListener;
import fr.thedarven.traveliacite.listener.listeners.DeposePointListener;
import fr.thedarven.traveliacite.listener.listeners.JoinQuitListener;
import fr.thedarven.traveliacite.listener.listeners.SpawnProtectionListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import java.util.Objects;

public class ListenerManager {

    private final Cite main;
    private JoinQuitListener joinQuitListener;
    private DebugCommand debugCommand;

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
        pluginManager.registerEvents(new SpawnProtectionListener(this.main), this.main);
        pluginManager.registerEvents(new DeposePointListener(this.main), this.main);
    }

    private void initCommands() {
        PointsCommand pointsCommand = new PointsCommand(this.main);
        Objects.requireNonNull(this.main.getCommand("points")).setExecutor(pointsCommand);
        Objects.requireNonNull(this.main.getCommand("points")).setTabCompleter(pointsCommand);

        this.debugCommand = new DebugCommand();
        Objects.requireNonNull(this.main.getCommand("debug")).setExecutor(debugCommand);
        Objects.requireNonNull(this.main.getCommand("debug")).setTabCompleter(debugCommand);
    }

    public JoinQuitListener getJoinQuitListener() {
        return this.joinQuitListener;
    }

    public DebugCommand getDebugCommand() {
        return debugCommand;
    }

    // BlockBreakEvent
    // BlockPlaceEvent
    // BlockFromToEvent
    // FluidLevelChangeEvent
    // SignChangeEvent
    // StructureGrowEvent
    // PortalCreateEvent
    // HangingPlaceEvent
    // HangingBreakEvent

    // BlockBurnEvent
    // BlockCanBuildEvent
    // BlockDamageEvent
    // BlockExplodeEvent
    // BlockFertilizeEvent
    // BlockFormEvent
    // BlockGrowEvent
    // BlockPhysicsEvent
    // BlockPistonRetractEvent
    // BlockPistonExtendEvent
    // BlockRedstoneEvent
    // BlockShearEntityEvent
    // BlockSpreadEvent
    // CauldronLevelChangeEvent
    // EntityBlockFormEvent
    // LeavesDecayEvent
    // MoistureChangeEvent
    // SpongeAbsorbEvent
    // RaidTriggerEvent
    // BrewEvent
    // BrewingStandFuelEvent
    // FurnaceBurnEvent
    // InventoryPickupItemEvent
    // BlockDispenseArmorEvent
    // BlockDispenseEvent
    // BlockFadeEvent         -    Called when a block fades, melts or disappears based on world conditions

    // PlayerBedEnterEvent
    // PlayerBucketEvent
    // PlayerEggThrowEvent
    // PlayerHarvestBlockEvent
    // PlayerShearEntityEvent
    // PlayerTakeLecternBookEvent

    // CreatureSpawnEvent
    // CreeperPowerEvent
    // EntityBreakDoorEvent
    // EntityChangeBlockEvent
    // EntityExplodeEvent
    // EntityPlaceEvent
    // EntityTransformEvent
    // ExplosionPrimeEvent
    // LingeringPotionSplashEvent
    // PotionSplashEvent
    // SheepDyeWoolEvent
    // PlayerInteractAtEntityEvent
    // PlayerInteractEntityEvent (+ PlayerArmorStandManipulateEvent) --> anvil, enchantment table, cuire, open inv ?
    // PlayerInteractEvent


    // https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/event/vehicle/VehicleEvent.html
}
