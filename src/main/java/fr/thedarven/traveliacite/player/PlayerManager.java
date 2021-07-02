package fr.thedarven.traveliacite.player;

import fr.thedarven.traveliacite.Cite;
import fr.thedarven.traveliacite.player.model.PlayerCite;
import fr.thedarven.traveliacite.player.runnable.SavePlayerRunnable;
import fr.thedarven.traveliacite.utils.ReadFile;
import org.bukkit.entity.Player;

import java.util.*;

public class PlayerManager {

	private static final String SERIALIZABLE_FILE_NAME = "players.ser";
	private static final int TICKS_PER_SECOND = 20;
	private static final int SECONDS_BETWEEN_SAVE = 5 * 60;

	private final Cite main;
	private Map<UUID, PlayerCite> players = new HashMap<>();

	public PlayerManager(Cite main) {
		this.main = main;
		loadPlayers();
		initSavePlayerRunnable();
	}

	private void loadPlayers() {
		ReadFile<Map<UUID, PlayerCite>> readFile = new ReadFile<>(this.main, SERIALIZABLE_FILE_NAME);
		this.players = readFile.readFile();
		if (Objects.isNull(this.players)) {
			this.players = new HashMap<>();
		}
		this.players.values().forEach(playerCite -> playerCite.setMain(this.main));
	}

	public void initSavePlayerRunnable() {
		new SavePlayerRunnable(this.main, this).runTaskTimer(this.main, TICKS_PER_SECOND * SECONDS_BETWEEN_SAVE,
				TICKS_PER_SECOND * SECONDS_BETWEEN_SAVE);
	}

	public void savePlayers() {
		System.out.println("[Cite] Donnees sauvegardees");
		ReadFile<Map<UUID, PlayerCite>> readFile = new ReadFile<>(this.main, SERIALIZABLE_FILE_NAME);
		readFile.writeFile(this.players);
	}

	public PlayerCite getAndCreatePlayerCiteByPlayer(Player player) {
		Optional<PlayerCite> oPlayerCite = getPlayerCiteByUUID(player.getUniqueId());
		PlayerCite playerCite = oPlayerCite.orElseGet(() -> createPlayerCite(player.getUniqueId(), player.getName()));
		playerCite.setName(player.getName());
		return playerCite;
	}

	public PlayerCite createPlayerCite(UUID uuid, String name) {
		PlayerCite newPlayerCite = new PlayerCite(uuid, name, this.main);
		this.players.put(uuid, newPlayerCite);
		// this.main.getHologramManager().updateHolograms();
		return newPlayerCite;
	}

	public Optional<PlayerCite> getPlayerCiteByUUID(UUID uuid) {
		return Optional.ofNullable(this.players.get(uuid));
	}

	public Optional<PlayerCite> getPlayerCiteByName(String name) {
		return this.players.values().stream().filter(playerCite -> playerCite.getName().equalsIgnoreCase(name))
				.findFirst();
	}

	public List<PlayerCite> getPlayersList() {
		return new ArrayList<>(this.players.values());
	}

}
