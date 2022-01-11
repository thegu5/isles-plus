package thegu5.islesaddons;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "assets")
public class ModConfig implements ConfigData {
    public boolean keydropnotifs = true;
    public boolean debugloot = false;
    public boolean casketnotifs = false;
    public boolean nightnotifs = false;
}