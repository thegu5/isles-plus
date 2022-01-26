package thegu5.islesplus.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static thegu5.islesplus.client.IslesPlusClient.*;

@Mixin(ClientPlayNetworkHandler.class)
public class chatPacketMixin {
    @Inject(at = @At("HEAD"), method = "onGameMessage(Lnet/minecraft/network/packet/s2c/play/GameMessageS2CPacket;)V")
    private void init(GameMessageS2CPacket packet, CallbackInfo info) {
        InGameHud hud = MinecraftClient.getInstance().inGameHud;
        String message = packet.getMessage().getString();
        if (config().lootnotifs.togglelootnotifs) {
            for (String item : config().lootnotifs.lootnotifslist) {
                Pattern test = Pattern.compile("^\\[(LOOT|ITEM)].*" + item);
                Matcher currmatcher = test.matcher(message);
                if (currmatcher.find()) {
                    hud.setTitle(Text.of("§" + getFormattingFromEnum(config().lootnotifs.lootnotifscolor) + item + "!"));
                    assert client() != null;
                    assert client().player != null;
                    client().player.playSound(SoundEvents.BLOCK_NOTE_BLOCK_PLING, 1.5F, 1);
                    break;
                }
            }
        }

        if (config().lootnotifs.debugloot && Pattern.compile("^\\[(LOOT|ITEM)].*").matcher(message).find()) {
            hud.setTitle(Text.of("§cLoot found! - Debug"));
            assert client() != null;
            assert client().player != null;
            client().player.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BIT, 1.5F, 1);
        }


    }
}
