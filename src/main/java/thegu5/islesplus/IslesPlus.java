package thegu5.islesplus;

import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.entities.pipe.PipeStatus;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.ScoreboardPlayerScore;
import net.minecraft.scoreboard.Team;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import thegu5.islesplus.client.IslesPlusClient;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static thegu5.islesplus.client.IslesPlusClient.*;

public class IslesPlus implements ModInitializer {
    public static final IPCClient ipcClient = new IPCClient(930912540135915610L);
    public static int clientTick = 1;
    public static int discordAppCount = 0;
    public static String islesLocation = "";
    public static String previousIP = "";
    public static MinecraftClient client() {
        try {
            return MinecraftClient.getInstance();
        }
        catch(NullPointerException e) {
            return null;
        }
    }
    public static boolean onIsles() {
        try {
            return client() != null && !client().isInSingleplayer() && client().getCurrentServerEntry().address != null && client().getCurrentServerEntry().address.contains("skyblockisles.com");
        } catch (NullPointerException exception) {
            return false;
        }
    }
    public void setupIPC(MinecraftClient client) {
        clientTick = 1;
        if (onIsles()) {
            if (previousIP.equals("")) {
                previousIP = client.getCurrentServerEntry().address;
                try {
                    ipcClient.connect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                DiscordUtils.lastTimestamp = OffsetDateTime.now();
            }
            DiscordUtils.updateRPC("", "");
        }
    }

    public void closeIPC() {
        if (onIsles() && ipcClient.getStatus() == PipeStatus.CONNECTED) {
            previousIP = "";
            ipcClient.close();
        }
    }

    @Override
    public void onInitialize() {

        ClientTickEvents.START_CLIENT_TICK.register((listener) ->
        {
            try {
                clientTick++;
                if (clientTick == 20) {
                    List<String> scoreboard = IslesPlusClient.getScoreboard();
                    if (scoreboard == null) {
                        System.out.println("Scoreboard null :(");
                    } else {
                        discordAppCount++;
                        // Night notifier
                        Pattern currtime = Pattern.compile(".+" + config().nightnotiftime + ".+");
                        for (String line : scoreboard) { // this crashes bc null
                            Matcher lineMatch = currtime.matcher(line);
                            if (lineMatch.find()) {
                                client().player.playSound(SoundEvents.BLOCK_NOTE_BLOCK_PLING, 1, 0.5F);
                                hud().setSubtitle(Text.of("§0Night time is soon!"));
                            }
                        }
                        // RPC
                        if (discordAppCount == 5) {
                            if (onIsles()) {
                                if (scoreboard != null && scoreboard.size() > 1) {
                                    if (scoreboard.get(1).startsWith("Rank: "))
                                        DiscordUtils.updateRPC(scoreboard.get(4), "In Hub");
                                    else {
                                        DiscordUtils.updateRPC(scoreboard.get(2), "In Game");
                                        islesLocation = scoreboard.get(2);
                                    }
                                }
                            } else {
                                if (!client.isInSingleplayer()) {
                                    DiscordUtils.updateRPC("In Game Menu", "");
                                }
                            }
                            discordAppCount = 0;
                        }
                    }
                    clientTick = 0;
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

        });
        ClientPlayConnectionEvents.JOIN.register((a, b, c) ->
                setupIPC(MinecraftClient.getInstance()));

        ClientPlayConnectionEvents.DISCONNECT.register((a, b) ->
                closeIPC());
    }
}
