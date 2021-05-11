package fr.thedarven.traveliacite.listener.commands;

import fr.thedarven.traveliacite.Cite;
import fr.thedarven.traveliacite.player.model.PlayerCite;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PointsCommand implements CommandExecutor, TabCompleter {

    private static final String NOT_OPERATOR = "§cTu n'es pas autorisé à utiliser cette commande (mais bien essayé), petit filou va !";
    private static final String POINTS_COMMAND_USAGE = "§cUtilisation : /points [add|remove|set] <name> <amount>.";
    private static final String ACTION_NOT_FOUND = "§cL'action spécifiée n'a pas été trouvé.";
    private static final String PLAYER_NOT_FOUND = "§cL'utilisateur spécifié n'a pas été trouvé.";
    private static final String INVALID_AMOUNT = "§cLe montant fourni n'est pas valide (nombre entier).";

    private final Cite main;

    public PointsCommand(Cite main) {
        this.main = main;
    }

    public boolean onCommand(CommandSender sender, Command command, String msg, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage(NOT_OPERATOR);
        }

        if (args.length < 3) {
            sender.sendMessage(POINTS_COMMAND_USAGE);
            return false;
        }

        Optional<PointsCommandActionEnum> oAction = PointsCommandActionEnum.getActionByName(args[0]);
        if (!oAction.isPresent()) {
            sender.sendMessage(ACTION_NOT_FOUND);
            sender.sendMessage(POINTS_COMMAND_USAGE);
            return false;
        }

        Optional<PlayerCite> oTarget = this.main.getPlayerManager().getPlayerCiteByName(args[1]);
        if (!oTarget.isPresent()) {
            sender.sendMessage(PLAYER_NOT_FOUND);
            sender.sendMessage(POINTS_COMMAND_USAGE);
            return false;
        }

        try {
            int amount = Integer.parseInt(args[2]);
            PlayerCite target = oTarget.get();

            switch (oAction.get()) {
                case ADD:
                    System.out.println("[Cite] " + amount + " emeraudes ajoutees a " + target.getName() + " par " + sender.getName());
                    target.addEmeralds(amount);
                    sender.sendMessage("§2" + amount + "§a émeraudes on été ajoutées au compte de §2" + target.getName() + "§a avec succès.");
                    break;
                case SET:
                    System.out.println("[Cite] Nouveau montant d'emeraudes pour " + target.getName() + " par " + sender.getName() + " : " + amount + " émeraudes.");
                    target.setEmeralds(amount);
                    sender.sendMessage("§2" + target.getName() + "§a possède désormais §2" + amount + "§a émeraudes.");
                    break;
                case REMOVE:
                    System.out.println("[Cite] " + amount + " emeraudes retirees a " + target.getName() + " par " + sender.getName());
                    target.addEmeralds(-amount);
                    sender.sendMessage("§2" + amount + "§a émeraudes on été enlevées au compte de §2" + target.getName() + "§a avec succès.");
                    break;
            }
            return true;
        } catch (NumberFormatException exception) {
            sender.sendMessage(INVALID_AMOUNT);
            sender.sendMessage(POINTS_COMMAND_USAGE);
            return false;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String msg, String[] args) {
        int nbArgs = args.length;
        if (nbArgs == 1) {
            return Arrays.stream(PointsCommandActionEnum
                    .values())
                    .map(PointsCommandActionEnum::getName)
                    .filter(name -> name.toLowerCase().contains(args[0].toLowerCase()))
                    .collect(Collectors.toList());
        } else if (nbArgs == 2) {
            return this.main.getPlayerManager().getPlayersList()
                    .stream()
                    .map(PlayerCite::getName)
                    .filter(name -> name.toLowerCase().contains(args[1].toLowerCase()))
                    .collect(Collectors.toList());
        }
        return null;
    }

    public enum PointsCommandActionEnum {
        ADD("add"),
        REMOVE("remove"),
        SET("set");

        private String name;

        PointsCommandActionEnum(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public static Optional<PointsCommandActionEnum> getActionByName(String name) {
            return Arrays.stream(values())
                    .filter(action -> action.name.equalsIgnoreCase(name))
                    .findFirst();
        }
    }
}
