package thegu5.islesplus;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

import java.util.ArrayList;

@Config(name = "assets")
public class ModConfig implements ConfigData {
    public boolean debugloot = false;
    public ArrayList<String> customlootnotifs = new ArrayList<>();
    public boolean nightnotifs = false;
    public String nightnotiftime = "6:00";
    public boolean stackchat = true;
    public boolean fishingnotifier = true;
    public boolean glowingparkourskulls = true;
}