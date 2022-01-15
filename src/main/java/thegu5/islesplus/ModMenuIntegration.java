package thegu5.islesplus;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

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