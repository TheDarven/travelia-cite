package fr.thedarven.traveliacite.listener.listeners;

import fr.thedarven.traveliacite.Cite;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.BrewingStandFuelEvent;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.raid.RaidTriggerEvent;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.event.world.StructureGrowEvent;

import java.util.List;
import java.util.Objects;

public class SpawnProtectionListener implements Listener {

    private final Cite main;

    public SpawnProtectionListener(Cite main) {
        this.main = main;
    }

    // HangingPlaceEvent
    // HangingBreakEvent
    // BlockPhysicsEvent
    // BlockRedstoneEvent
    // EntityUnleashEvent
    // PlayerUnleashEntityEvent
    // PlayerLeashEntityEvent

    private boolean cancelInteraction(Location location, Player player, String name) {
        if (this.main.getListenerManager().getDebugCommand().isDebugMessage()) {
            System.out.println("[TRAV-DEBUG] " + name);
        }

        return this.main.getConfigurationManager().getSpawnLocation().isBetween(location)
                && (Objects.isNull(player) || !player.isOp() || player.getGameMode() != GameMode.CREATIVE);
    }

    private boolean cancelInteraction(List<BlockState> blocks, Player player, String name) {
        for (BlockState block: blocks) {
            if (cancelInteraction(block.getLocation(), player, name)) {
                return true;
            }
        }
        return false;
    }

    private boolean cancelInteractionBlocks(List<Block> blocks, Player player, String name) {
        for (Block block: blocks) {
            if (cancelInteraction(block.getLocation(), player, name)) {
                return true;
            }
        }
        return false;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        e.setCancelled(cancelInteraction(e.getBlock().getLocation(), e.getPlayer(), e.getClass().getSimpleName()));
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        e.setCancelled(cancelInteraction(e.getBlock().getLocation(), e.getPlayer(), e.getClass().getSimpleName()));
    }

    @EventHandler
    public void onBlockMove(BlockFromToEvent e) {
        e.setCancelled(cancelInteraction(e.getToBlock().getLocation(), null, e.getClass().getSimpleName())
                || cancelInteraction(e.getBlock().getLocation(), null, e.getClass().getSimpleName()));
    }

    @EventHandler
    public void onFluidLevelChange(FluidLevelChangeEvent e) {
        e.setCancelled(cancelInteraction(e.getBlock().getLocation(), null, e.getClass().getSimpleName()));
    }

    @EventHandler
    public void onSignChange(SignChangeEvent e) {
        e.setCancelled(cancelInteraction(e.getBlock().getLocation(), e.getPlayer(), e.getClass().getSimpleName()));
    }

    @EventHandler
    public void onStructureGrowEvent(StructureGrowEvent e) {
        e.setCancelled(cancelInteraction(e.getLocation(), e.getPlayer(), e.getClass().getSimpleName())
                || cancelInteraction(e.getBlocks(), e.getPlayer(), e.getClass().getSimpleName()));
    }

    @EventHandler
    public void onPortalCreation(PortalCreateEvent e) {
        if (e.getReason() == PortalCreateEvent.CreateReason.NETHER_PAIR) {
            e.setCancelled(cancelInteraction(e.getBlocks(), null, e.getClass().getSimpleName()));
            return;
        }

        if (e.getReason() == PortalCreateEvent.CreateReason.FIRE) {
            Player player = e.getEntity() instanceof Player ? (Player) e.getEntity() : null;
            e.setCancelled(cancelInteraction(e.getBlocks(), player, e.getClass().getSimpleName()));
        }
    }

    @EventHandler
    public void onBlockBurn(BlockBurnEvent e) {
        e.setCancelled(cancelInteraction(e.getBlock().getLocation(), null, e.getClass().getSimpleName()));
    }

    @EventHandler
    public void onBlockBuild(BlockCanBuildEvent e) {
        e.setBuildable(!cancelInteraction(e.getBlock().getLocation(), e.getPlayer(), e.getClass().getSimpleName()));
    }

    @EventHandler
    public void onBlockDamage(BlockDamageEvent e) {
        e.setCancelled(cancelInteraction(e.getBlock().getLocation(), e.getPlayer(), e.getClass().getSimpleName()));
    }

    @EventHandler
    public void onBlockExplode(BlockExplodeEvent e) {
        e.setCancelled(cancelInteraction(e.getBlock().getLocation(), null, e.getClass().getSimpleName())
                || cancelInteractionBlocks(e.blockList(), null, e.getClass().getSimpleName()));
    }

    @EventHandler
    public void onBlockFertilize(BlockFertilizeEvent e) {
        e.setCancelled(cancelInteraction(e.getBlock().getLocation(), e.getPlayer(), e.getClass().getSimpleName())
                || cancelInteraction(e.getBlocks(), e.getPlayer(), e.getClass().getSimpleName()));
    }

    @EventHandler
    public void onBlockForm(BlockFormEvent e) {
        e.setCancelled(cancelInteraction(e.getBlock().getLocation(), null, e.getClass().getSimpleName()));
    }

    @EventHandler
    public void onBlockGrow(BlockGrowEvent e) {
        e.setCancelled(cancelInteraction(e.getBlock().getLocation(), null, e.getClass().getSimpleName()));
    }

    @EventHandler
    public void onBlockPistonExtend(BlockPistonExtendEvent e) {
        e.setCancelled(cancelInteraction(e.getBlock().getLocation(), null, e.getClass().getSimpleName())
                || cancelInteractionBlocks(e.getBlocks(), null, e.getClass().getSimpleName()));
    }

    @EventHandler
    public void onBlockPistonRetract(BlockPistonRetractEvent e) {
        e.setCancelled(cancelInteraction(e.getBlock().getLocation(), null, e.getClass().getSimpleName())
                || cancelInteractionBlocks(e.getBlocks(), null, e.getClass().getSimpleName()));
    }

    @EventHandler
    public void onBlockShear(BlockShearEntityEvent e) {
        e.setCancelled(cancelInteraction(e.getBlock().getLocation(), null, e.getClass().getSimpleName())
                || cancelInteraction(e.getEntity().getLocation(), null, e.getClass().getSimpleName()));
    }

    @EventHandler
    public void onBlockSpread(BlockSpreadEvent e) {
        e.setCancelled(cancelInteraction(e.getBlock().getLocation(), null, e.getClass().getSimpleName())
                || cancelInteraction(e.getSource().getLocation(), null, e.getClass().getSimpleName()));
    }

    @EventHandler
    public void onCauldronLevelChange(CauldronLevelChangeEvent e) {
        Player player = e.getEntity() instanceof Player ? (Player) e.getEntity() : null;
        e.setCancelled(cancelInteraction(e.getBlock().getLocation(), player, e.getClass().getSimpleName()));
    }

    @EventHandler
    public void onBlockForm(EntityBlockFormEvent e) {
        Player player = e.getEntity() instanceof Player ? (Player) e.getEntity() : null;
        e.setCancelled(cancelInteraction(e.getBlock().getLocation(), player, e.getClass().getSimpleName()));
    }

    @EventHandler
    public void onLeaveDecay(LeavesDecayEvent e) {
        e.setCancelled(cancelInteraction(e.getBlock().getLocation(), null, e.getClass().getSimpleName()));
    }

    @EventHandler
    public void onMoistureChange(MoistureChangeEvent e) {
        e.setCancelled(cancelInteraction(e.getBlock().getLocation(), null, e.getClass().getSimpleName()));
    }

    @EventHandler
    public void onSpongeAbsorb(SpongeAbsorbEvent e) {
        e.setCancelled(cancelInteraction(e.getBlock().getLocation(), null, e.getClass().getSimpleName())
                || cancelInteraction(e.getBlocks(), null, e.getClass().getSimpleName()));
    }

    @EventHandler
    public void onRaidTrigger(RaidTriggerEvent e) {
        e.setCancelled(cancelInteraction(e.getPlayer().getLocation(), e.getPlayer(), e.getClass().getSimpleName()));
    }


    @EventHandler
    public void onBrew(BrewEvent e) {
        e.setCancelled(cancelInteraction(e.getBlock().getLocation(), null, e.getClass().getSimpleName()));
    }

    @EventHandler
    public void onBrewStandFuel(BrewingStandFuelEvent e) {
        boolean cancel = cancelInteraction(e.getBlock().getLocation(), null, e.getClass().getSimpleName());
        e.setCancelled(cancel);
        e.setConsuming(cancel);
    }

    @EventHandler
    public void onFurnaceBurn(FurnaceBurnEvent e) {
        boolean cancel = cancelInteraction(e.getBlock().getLocation(), null, e.getClass().getSimpleName());
        e.setCancelled(cancel);
        e.setBurning(cancel);
    }

    @EventHandler
    public void onInventoryPickupItem(InventoryPickupItemEvent e) {
        e.setCancelled(cancelInteraction(e.getItem().getLocation(), null, e.getClass().getSimpleName()));
    }

    @EventHandler
    public void onDispenseArmor(BlockDispenseArmorEvent e) {
        e.setCancelled(cancelInteraction(e.getTargetEntity().getLocation(), null, e.getClass().getSimpleName())
                || cancelInteraction(e.getBlock().getLocation(), null, e.getClass().getSimpleName()));
    }

    @EventHandler
    public void onDispense(BlockDispenseEvent e) {
        e.setCancelled(cancelInteraction(e.getBlock().getLocation(), null, e.getClass().getSimpleName()));
    }

    @EventHandler
    public void onBlockFade(BlockFadeEvent e) {
        e.setCancelled(cancelInteraction(e.getBlock().getLocation(), null, e.getClass().getSimpleName()));
    }

    @EventHandler
    public void onPlayerBedEnter(PlayerBedEnterEvent e) {
        e.setCancelled(cancelInteraction(e.getBed().getLocation(), e.getPlayer(), e.getClass().getSimpleName()));
    }

    @EventHandler
    public void onPlayerBucketFill(PlayerBucketFillEvent e) {
        e.setCancelled(cancelInteraction(e.getBlock().getLocation(), e.getPlayer(), e.getClass().getSimpleName())
                || cancelInteraction(e.getBlockClicked().getLocation(), e.getPlayer(), e.getClass().getSimpleName()));
    }

    @EventHandler
    public void onPlayerBucketEmpty(PlayerBucketEmptyEvent e) {
        e.setCancelled(cancelInteraction(e.getBlock().getLocation(), e.getPlayer(), e.getClass().getSimpleName())
                || cancelInteraction(e.getBlockClicked().getLocation(), e.getPlayer(), e.getClass().getSimpleName()));
    }

    @EventHandler
    public void onPlayerThrowEgg(PlayerEggThrowEvent e) {
        if (e.isHatching() && cancelInteraction(e.getEgg().getLocation(), null, e.getClass().getSimpleName())) {
            e.setHatching(false);
        }
    }

    @EventHandler
    public void onPlayerHarvest(PlayerHarvestBlockEvent e) {
        e.setCancelled(cancelInteraction(e.getHarvestedBlock().getLocation(), e.getPlayer(), e.getClass().getSimpleName()));
    }

    @EventHandler
    public void onPlayerShear(PlayerShearEntityEvent e) {
        e.setCancelled(cancelInteraction(e.getEntity().getLocation(), e.getPlayer(), e.getClass().getSimpleName()));
    }

    @EventHandler
    public void onPlayerTakeLectern(PlayerTakeLecternBookEvent e) {
        e.setCancelled(cancelInteraction(e.getLectern().getLocation(), e.getPlayer(), e.getClass().getSimpleName()));
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent e) {
        if (cancelInteraction(e.getLocation(), null, e.getClass().getSimpleName())) {
            CreatureSpawnEvent.SpawnReason spawnReason = e.getSpawnReason();
            if (spawnReason != CreatureSpawnEvent.SpawnReason.EGG && spawnReason != CreatureSpawnEvent.SpawnReason.SPAWNER_EGG
                    && spawnReason != CreatureSpawnEvent.SpawnReason.CUSTOM) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onCreeperPower(CreeperPowerEvent e) {
        e.setCancelled(cancelInteraction(e.getEntity().getLocation(), null, e.getClass().getSimpleName()));
    }

    @EventHandler
    public void onEntityBreakDoor(EntityBreakDoorEvent e) {
        e.setCancelled(cancelInteraction(e.getEntity().getLocation(), null, e.getClass().getSimpleName()));
    }

    @EventHandler
    public void onEntityChangeBlock(EntityChangeBlockEvent e) {
        e.setCancelled(cancelInteraction(e.getBlock().getLocation(), null, e.getClass().getSimpleName()));
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent e) {
        e.setCancelled(cancelInteraction(e.getLocation(), null, e.getClass().getSimpleName())
                || cancelInteraction(e.getEntity().getLocation(), null, e.getClass().getSimpleName()));
    }

    @EventHandler
    public void onEntityPlaceBlock(EntityPlaceEvent e) {
        e.setCancelled(cancelInteraction(e.getBlock().getLocation(), e.getPlayer(), e.getClass().getSimpleName()));
    }

    @EventHandler
    public void onEntityTransform(EntityTransformEvent e) {
        e.setCancelled(cancelInteraction(e.getTransformedEntity().getLocation(), null, e.getClass().getSimpleName()));
    }

    @EventHandler
    public void onExplosionPrime(ExplosionPrimeEvent e) {
        e.setCancelled(cancelInteraction(e.getEntity().getLocation(), null, e.getClass().getSimpleName()));
    }

    @EventHandler
    public void onLingeringPotionSplash(LingeringPotionSplashEvent e) {
        e.setCancelled(cancelInteraction(e.getEntity().getLocation(), null, e.getClass().getSimpleName()));
    }

    @EventHandler
    public void onPotionSplash(PotionSplashEvent e) {
        e.setCancelled(cancelInteraction(e.getEntity().getLocation(), null, e.getClass().getSimpleName()));
    }

    @EventHandler
    public void onSheepDyeWool(SheepDyeWoolEvent e) {
        e.setCancelled(cancelInteraction(e.getEntity().getLocation(), null, e.getClass().getSimpleName()));
    }



    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        e.setCancelled(cancelInteraction(e.getEntity().getLocation(), null, e.getClass().getSimpleName()));
    }

    @EventHandler
    public void onPlayerRightClick(PlayerInteractAtEntityEvent e) {
        e.setCancelled(cancelInteraction(e.getRightClicked().getLocation(), e.getPlayer(), e.getClass().getSimpleName()));
    }

    @EventHandler
    public void onPlayerArmorStandManipulate(PlayerArmorStandManipulateEvent e) {
        e.setCancelled(cancelInteraction(e.getRightClicked().getLocation(), e.getPlayer(), e.getClass().getSimpleName()));
    }

    @EventHandler
    public void onPlayerInteractBlock(PlayerInteractEvent e) {
        if (Objects.nonNull(e.getClickedBlock()) && cancelInteraction(e.getClickedBlock().getLocation(), e.getPlayer(), e.getClass().getSimpleName())) {
            Material clickedMateriel = e.getClickedBlock().getType();
            if (clickedMateriel == Material.ANVIL || clickedMateriel == Material.ENCHANTING_TABLE || clickedMateriel == Material.FURNACE) {
                e.setCancelled(true);
            }
        }
    }

}
