package thegu5.islesplus.client;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.ScoreboardPlayerScore;
import net.minecraft.scoreboard.Team;
import net.minecraft.sound.SoundEvents;
import thegu5.islesplus.ModConfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static thegu5.islesplus.IslesPlus.client;

@net.fabricmc.api.Environment(net.fabricmc.api.EnvType.CLIENT)
public class IslesPlusClient implements ClientModInitializer {
    public static MinecraftClient client = MinecraftClient.getInstance();

    public static long time() {
        return MinecraftClient.getInstance().world.getTime();
    }
    public static ModConfig config() {
        return AutoConfig.getConfigHolder(ModConfig.class).getConfig();
    }
    public static InGameHud hud() {
        return MinecraftClient.getInstance().inGameHud;
    }

    public static List<String> getScoreboard() {
        try {
            Scoreboard board = MinecraftClient.getInstance().player.getScoreboard();
            ScoreboardObjective objective = board.getObjectiveForSlot(1);
            List<String> lines = new ArrayList<>();
            for (ScoreboardPlayerScore score : board.getAllPlayerScores(objective)) {
                Team team = board.getPlayerTeam(score.getPlayerName());
                if (team != null) {
                    String line = team.getPrefix().getString() + team.getSuffix().getString();
                    if (line.trim().length() > 0) {
                        String formatted = line.replaceAll("/\\u00A7[0-9A-FK-OR]/ig", "");
                        lines.add(formatted);
                    }
                }
            }

            if (objective != null) {
                lines.add(objective.getDisplayName().getString());
                Collections.reverse(lines);
            }
            return lines;
        } catch (NullPointerException e) {
            return null;
        }
    }
    @Override
    public void onInitializeClient() {
        AutoConfig.register(ModConfig.class, GsonConfigSerializer::new);

        ClientCommandManager.DISPATCHER.register(
                ClientCommandManager.literal("ia").executes(context -> {
                    System.out.println("ran /ia");
                    PlayerEntity player = MinecraftClient.getInstance().player;
                    player.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BIT, 1, 1);
                    return 0;
                })
        );
        ClientCommandManager.DISPATCHER.register(
                ClientCommandManager.literal("printscoreboard").executes((context -> {
                    for (String msg : getScoreboard()) {
                        System.out.println(msg);
                    }
                    return 0;
                }))
        );
        }
    }


