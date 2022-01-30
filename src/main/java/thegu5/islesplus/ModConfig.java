package thegu5.islesplus;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

import java.util.ArrayList;

@Config(name = "assets")
public class ModConfig implements ConfigData {
    public enum Color {
        BLACK("BLACK", '0', 0, 0),
        DARK_BLUE("DARK_BLUE", '1', 1, 170),
        DARK_GREEN("DARK_GREEN", '2', 2, 43520),
        DARK_AQUA("DARK_AQUA", '3', 3, 43690),
        DARK_RED("DARK_RED", '4', 4, 11141120),
        DARK_PURPLE("DARK_PURPLE", '5', 5, 11141290),
        GOLD("GOLD", '6', 6, 16755200),
        GRAY("GRAY", '7', 7, 11184810),
        DARK_GRAY("DARK_GRAY", '8', 8, 5592405),
        BLUE("BLUE", '9', 9, 5592575),
        GREEN("GREEN", 'a', 10, 5635925),
        AQUA("AQUA", 'b', 11, 5636095),
        RED("RED", 'c', 12, 16733525),
        LIGHT_PURPLE("LIGHT_PURPLE", 'd', 13, 16733695),
        YELLOW("YELLOW", 'e', 14, 16777045),
        WHITE("WHITE", 'f', 15, 16777215);
        public final String name;
        public final char code;
        public final int index;
        public final int value;
        Color(String name, char code, int index, int value) {
            this.name = name;
            this.code = code;
            this.index = index;
            this.value = value;
        }
    }
    @Comment("Duplicate messages are stacked with (2)")
    public boolean stackchat = true;
    @Comment("Messages in chat, and a sound when you stop")
    public boolean fishingnotifier = false;
    @ConfigEntry.Gui.PrefixText
    @Comment("Make the giant course skulls glow for maximum laziness!")
    public boolean glowingparkourskulls = true;

    @ConfigEntry.Gui.CollapsibleObject
    public NightClass nightdropdown = new NightClass();

    public static class NightClass {
        @Comment("Toggle this off to disable notifications but keep settings")
        public boolean nightnotifs = false;
        @Comment("This is in-game time!")
        public String nightnotiftime = "6:00";
        @Comment("Supports all built-in mc colors")
        public Color nightnotifcolor = Color.RED;
    }

    @ConfigEntry.Gui.CollapsibleObject
    public LootNotifsClass lootnotifs = new LootNotifsClass();

    @ConfigEntry.Gui.CollapsibleObject
    public gjallartimerclass gjtimer = new gjallartimerclass();

    @ConfigEntry.Gui.CollapsibleObject
    public realmtimerclass realmtimer = new realmtimerclass();

    @ConfigEntry.Gui.CollapsibleObject
    @Comment("Use this to test X, Y, and color values")
    public testtextclass testtext = new testtextclass();

    public static class LootNotifsClass {
        @Comment("Toggle this off to disable notifications but keep settings")
        public boolean togglelootnotifs = true;
        @Comment("This fuzzy searches; 'Key' matches all keys/parts")
        public ArrayList<String> lootnotifslist = new ArrayList<>();
        @Comment("Supports all built-in mc colors")
        public Color lootnotifscolor = Color.RED;
        @ConfigEntry.Gui.Excluded
        @Comment("You shouldn't be seeing this... lol")
        public boolean debugloot = false;
    }
    public static class gjallartimerclass {
        @Comment("Toggle the timer")
        public boolean toggle = true;
        @Comment("The timer's x position on the screen (left-right)")
        public int x = 25;
        @Comment("The timer's y position on the screen (up-down)")
        public int y = 100;
        @Comment("Supports all built-in mc colors")
        public Color color = Color.GOLD;
    }
    public static class realmtimerclass {
        @Comment("Toggle the timer")
        public boolean toggle = true;
        @Comment("The timer's x position on the screen (left-right)")
        public int x = 25;
        @Comment("The timer's y position on the screen (up-down)")
        public int y = 125;
        @Comment("Supports all built-in mc colors")
        public Color color = Color.LIGHT_PURPLE;
    }
    public static class testtextclass {
        @Comment("Toggle the test text")
        public boolean toggle = false;
        @Comment("The text's x position on the screen (left-right)")
        public int x = 25;
        @Comment("The timer's y position on the screen (up-down)")
        public int y = 150;
        @Comment("Supports all built-in mc colors")
        public Color color = Color.GOLD;
    }
    @ConfigEntry.Gui.Excluded
    @Comment("Wait, that's illegal")
    public boolean firstload = true;
}