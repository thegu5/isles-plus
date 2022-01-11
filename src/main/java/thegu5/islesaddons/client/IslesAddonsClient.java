package thegu5.islesaddons.client;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import thegu5.islesaddons.ModConfig;

@net.fabricmc.api.Environment(net.fabricmc.api.EnvType.CLIENT)
public class IslesAddonsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        AutoConfig.register(ModConfig.class, GsonConfigSerializer::new);
        ClientCommandManager.DISPATCHER.register(
                ClientCommandManager.literal("ia").executes(context -> {
                    System.out.println("ran /ia");

                    return 0;
                })
        );


    }

}
