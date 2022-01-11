package thegu5.islesaddons;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.Text;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;

@Environment(EnvType.CLIENT)
public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
            /* Screen parent = MinecraftClient.getInstance().currentScreen;
            ConfigBuilder builder = ConfigBuilder.create()
                    .setParentScreen(parent)
                    .setTitle(Text.of("Isles Addons Config"));
            ConfigEntryBuilder entryBuilder = builder.entryBuilder();
            boolean keydrops = true;
            boolean casketdrops = true;
            builder.entryBuilder().startBooleanToggle(Text.of("Key Drop Notifications"), keydrops);
            builder.entryBuilder().startBooleanToggle(Text.of("Fishing Casket Drop Notifications"), casketdrops);
            return builder.build(); */
            return parent -> AutoConfig.getConfigScreen(ModConfig.class, parent).get();

    }
}