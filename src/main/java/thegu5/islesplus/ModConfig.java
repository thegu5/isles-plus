package thegu5.islesplus;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

import java.util.ArrayList;

@Config(name = "assets")
public class ModConfig implements ConfigData {
    public enum Color {
        DARK_RED,
        RED,
        GOLD,
        YELLOW,
        DARK_GREEN,
        GREEN,
        AQUA,
        DARK_AQUA,
        DARK_BLUE,
        BLUE,
        LIGHT_PURPLE,
        DARK_PURPLE,
        WHITE,
        GRAY,
        DARK_GRAY,
        BLACK
    }
    @Comment("Duplicate messages are stacked with (2)")
    public boolean stackchat = true;
    @Comment("Whenever you stop fishing, a sound plays")
    public boolean fishingnotifier = true;
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

    @ConfigEntry.Gui.Excluded
    @Comment("Wait, that's illegal")
    public boolean firstload = true;
}