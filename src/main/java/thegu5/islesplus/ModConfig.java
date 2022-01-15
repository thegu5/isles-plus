package thegu5.islesplus;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

import java.util.ArrayList;

@Config(name = "assets")
public class ModConfig implements ConfigData {
    public boolean debugloot = false;
    public ArrayList<String> customlootnotifs = new ArrayList<String>();
    public String nightnotiftime = "16:30";
    public boolean nightnotifs = false;
}