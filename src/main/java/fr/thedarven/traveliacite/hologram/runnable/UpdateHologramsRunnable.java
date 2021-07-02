package fr.thedarven.traveliacite.hologram.runnable;

import org.bukkit.scheduler.BukkitRunnable;

import fr.thedarven.traveliacite.Cite;

public class UpdateHologramsRunnable extends BukkitRunnable {

	private Cite main;
	
	public UpdateHologramsRunnable(Cite cite) {
		this.main = cite;
	}
	
	@Override
	public void run() {
		this.main.getHologramManager().updateHolograms();
	}

}
