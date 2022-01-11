package thegu5.islesaddons.mixin;

import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import thegu5.islesaddons.ModConfig;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Mixin(ClientPlayNetworkHandler.class)
public class ChatMixin {
    @Inject(at = @At("HEAD"), method = "onGameMessage(Lnet/minecraft/network/packet/s2c/play/GameMessageS2CPacket;)V")
    private void init(GameMessageS2CPacket packet, CallbackInfo info) {
        ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
        if (config.keydropnotifs) {
            String message = packet.getMessage().getString();
            Matcher lootMatcher = Pattern.compile("^\\[LOOT].+").matcher(message);
                if (lootMatcher.find()) {
                    InGameHud hud = MinecraftClient.getInstance().inGameHud;
                    PlayerEntity player = MinecraftClient.getInstance().player;
                    // SoundEvent sound = new SoundEvent(new Identifier("block.note.chime"));
                    if (config.debugloot) {
                        hud.setTitle(Text.of("§cLoot found! - Debug"));
                    }
                    // player.playSound(sound, 1, 1);
                    Pattern stemPattern = Pattern.compile("^\\[LOOT].+ Key Stem");
                    Matcher stemMatcher = stemPattern.matcher(message);
                    Pattern loopPattern = Pattern.compile("^\\[LOOT].+ Key Loop");
                    Matcher loopMatcher = loopPattern.matcher(message);
                    Pattern keyPattern = Pattern.compile("^\\[LOOT].+ Key");
                    Matcher keyMatcher = keyPattern.matcher(message);
                    if (stemMatcher.find()) {
                        hud.setTitle(Text.of("§cStem!"));
                        // player.playSound(sound, 1, 1);
                    } else if (loopMatcher.find()) {
                        hud.setTitle(Text.of("§cLoop!"));
                        // player.playSound(sound, 1, 1);
                    } else if (keyMatcher.find()) {
                        hud.setTitle(Text.of("§cKey!"));
                        // player.playSound(sound, 1, 1);
                    }
                // ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
            }
        }

    }
}
