package fr.thedarven.traveliacite.listener.commands;

import fr.thedarven.traveliacite.player.model.PlayerCite;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DebugCommand implements CommandExecutor, TabCompleter {

    private static final String[] values = { "true", "false" };
    private static final String DEBUG_COMMAND_USAGE = "§cUtilisation : /debug [true|false].";

    private boolean debugMessage = false;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String msg, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage(PointsCommand.NOT_OPERATOR);
        }

        if (args.length < 1) {
            sender.sendMessage(DEBUG_COMMAND_USAGE);
            return false;
        }

        this.debugMessage = args[0].equalsIgnoreCase("true");

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String msg, String[] args) {
        int nbArgs = args.length;
        if (nbArgs == 1) {
            return Arrays.stream(values)
                    .filter(value -> value.toLowerCase().contains(args[0].toLowerCase()))
                    .collect(Collectors.toList());
        }
        return null;
    }

    public boolean isDebugMessage() {
        return debugMessage;
    }

    public void setDebugMessage(boolean debugMessage) {
        this.debugMessage = debugMessage;
    }
}
