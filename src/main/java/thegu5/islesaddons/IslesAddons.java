package thegu5.islesaddons;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.minecraft.util.ActionResult;
public class IslesAddons implements ModInitializer {
    @Override
    public void onInitialize() {
        ClientTickEvents.START_CLIENT_TICK.register((listener) ->
        {

        });
    }
}
