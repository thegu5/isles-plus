package thegu5.islesplus.mixin;

import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import thegu5.islesplus.ModConfig;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static thegu5.islesplus.IslesPlus.client;

@Mixin(ClientPlayNetworkHandler.class)
public class ChatMixin {
    @Inject(at = @At("HEAD"), method = "onGameMessage(Lnet/minecraft/network/packet/s2c/play/GameMessageS2CPacket;)V")
    private void init(GameMessageS2CPacket packet, CallbackInfo info) {
        ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
        InGameHud hud = MinecraftClient.getInstance().inGameHud;
        String message = packet.getMessage().getString();
        for (String item : config.customlootnotifs) {
                Pattern test = Pattern.compile("^\\[(LOOT|ITEM)].*" + item);
                Matcher currmatcher = test.matcher(message);
                if (currmatcher.find()) {
                    hud.setTitle(Text.of("§c" + item + "!"));
                    client().player.playSound(SoundEvents.BLOCK_NOTE_BLOCK_PLING, 1.5F, 1);
                    break;
                }
        }

        if (config.debugloot) {
            hud.setTitle(Text.of("§cLoot found! - Debug"));
            client().player.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BIT, 1.5F, 1);
        }


    }
}
