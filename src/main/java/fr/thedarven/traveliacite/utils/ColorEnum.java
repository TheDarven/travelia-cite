package fr.thedarven.traveliacite.utils;

import org.bukkit.ChatColor;

public enum ColorEnum {

    BLACK("§0", 15, ChatColor.BLACK),
    DARK_BLUE("§1", 9, ChatColor.DARK_BLUE),
    DARK_GREEN("§2", 13, ChatColor.DARK_GREEN),
    DARK_AQUA("§3", 9, ChatColor.DARK_AQUA),
    DARK_RED("§4", 14, ChatColor.DARK_RED),
    DARK_PURPLE("§5", 10, ChatColor.DARK_PURPLE),
    ORANGE("§6", 1, ChatColor.GOLD),
    GRAY("§7", 8, ChatColor.GRAY),
    DARK_GRAY("§8", 7, ChatColor.DARK_GRAY),
    BLUE("§9", 11, ChatColor.BLUE),
    GREEN("§a", 5, ChatColor.GREEN),
    AQUA("§b", 3, ChatColor.AQUA),
    RED("§c", 14, ChatColor.RED),
    LIGHT_PURPLE("§d", 2, ChatColor.LIGHT_PURPLE),
    YELLOW("§e", 4, ChatColor.YELLOW),
    WHITE("§f", 0, ChatColor.WHITE);

    private final String color;
    private final int id;
    private final ChatColor chatColor;


    ColorEnum(String color, int id, ChatColor chatColor) {
        this.color = color;
        this.id = id;
        this.chatColor = chatColor;
    }

    public String getColor() {
        return color;
    }

    public int getId() {
        return id;
    }

    public ChatColor getChatColor() {
        return chatColor;
    }

    public static ColorEnum getByColor(String color) {
        for (ColorEnum colorEnum: ColorEnum.values()) {
            if (colorEnum.color.equals(color)) {
                return colorEnum;
            }
        }
        return ColorEnum.WHITE;
    }

    public static ColorEnum getById(int id) {
        for (ColorEnum colorEnum: ColorEnum.values()) {
            if (colorEnum.id == id) {
                return colorEnum;
            }
        }
        return ColorEnum.WHITE;
    }

}

