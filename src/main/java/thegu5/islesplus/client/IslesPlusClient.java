package thegu5.islesplus.client;

import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.entities.pipe.PipeStatus;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MagmaCubeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.ScoreboardPlayerScore;
import net.minecraft.scoreboard.Team;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import thegu5.islesplus.DiscordUtils;
import thegu5.islesplus.ModConfig;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minecraft.util.Formatting;

@net.fabricmc.api.Environment(net.fabricmc.api.EnvType.CLIENT)
public class IslesPlusClient implements ClientModInitializer {
    public static final MinecraftClient client = MinecraftClient.getInstance();
    public static ModConfig config() {
        return AutoConfig.getConfigHolder(ModConfig.class).getConfig();
    }
    public static InGameHud hud() {
        return MinecraftClient.getInstance().inGameHud;
    }
    public static final IPCClient ipcClient = new IPCClient(930912540135915610L);
    public static int clientTick = 1;
    public static int discordAppCount = 0;
    public static String islesLocation = "";
    public static String previousIP = "";
    public static boolean isFishing = false;
    public static Entity fishingEntity = null;
    public static Entity fishingHoloEntity = null;
    public static boolean justStartedFishing = false;
    public static boolean titlelengthset = false;
    private final String skullSignature = "ngBEheIaXuWnZaiWkxNB8XPN8Nbuo08mDHPZWNEVs82GnKfsC6lLU/nED3VGeHUT/8pxWxwS1Zjfuh/ty0Yzd7jovVrI8qYNIrHidHoct4twJ1Nch8+NmeIY7aE9yy6EuI81x1MK90vhMmyNHYnalMYMMbZE7TizwvzKKKdpvvrK8xspzNednbyXpTbHsAUV90SjdNH5TQlaI61XT+TCPYjX7nBDBcqPLMWWzO/SVskQfPoufphgdw7uOugZPiULtoQy6TEYGIXOjvFmBcF0HlHUbhHKuxUSSr5wLhb5kMZQaUTkWAJIfH3V/1wU/vSG5T1IU4kcw3LOlFr3uUZHzzU6w+a3mAE+P7aBBsgtB0Qrw8sB/miqArNjEAz4p52Mqly1o+PTFhPvczTNzStWNHg6oDsYlzZ+xtqD/5XAr32YUHwUgFld22b4bOsYWLPd1dvT0GxMVEFDadXVYD5Omf2Qr+6dAbFbIcVN8qe+/Wo+AsYmr49VQxifCxZ3kg6RnomPSwNsIN+xGZzr42bPA4iHSMJ19uvhX1pvrw19tTJ6zvfCKgutQYx/hse5BDOADDc0ci4Og9U/aQGX33Q76SsW61Clg0a5g9rpqxTuTgcLUSMoaPvOp0goW8CetHR0DqqwzqHXIAZJNdD9bL1q3hEbzW7VwTduD5R98ELNb/Q=";
    public static MinecraftClient client() {
        try {
            return MinecraftClient.getInstance();
        }
        catch(NullPointerException e) {
            return null;
        }
    }
    public static String getFormattingFromEnum(ModConfig.Color clr) {
        switch(clr) {
            case DARK_RED -> {
                return "4";
            }
            case RED -> {
                return "c";
            }
            case GOLD -> {
                return "6";
            }
            case YELLOW -> {
                return "e";
            }
            case DARK_GREEN -> {
                return "2";
            }
            case GREEN -> {
                return "a";
            }
            case AQUA -> {
                return "b";
            }
            case DARK_AQUA -> {
                return "3";
            }
            case DARK_BLUE -> {
                return "1";
            }
            case BLUE -> {
                return "9";
            }
            case LIGHT_PURPLE ->  {
                return "d";
            }
            case DARK_PURPLE -> {
                return "5";
            }
            case WHITE -> {
                return "f";
            }
            case GRAY -> {
                return "7";
            }
            case DARK_GRAY -> {
                return "8";
            }
            case BLACK -> {
                return "0";
            }
        }
        return "something went wrong";
    }
    public static boolean onIsles() {
        try {
            return client() != null && !client().isInSingleplayer() && client().getCurrentServerEntry().address != null && client().getCurrentServerEntry().address.contains("skyblockisles.com");
        } catch (NullPointerException exception) {
            return false;
        }
    }
    public static Entity getFishingHoloEntity(Entity e) {
        List<Entity> nearbyArmorstands = client.world.getOtherEntities(client.player, e.getBoundingBox().expand(0, 1, 0), entity -> entity.getType() == EntityType.ARMOR_STAND);
        if (nearbyArmorstands.isEmpty()) return null;
        for (Entity armorStand : nearbyArmorstands) {
            if (armorStand.getDisplayName().toString().contains("Fishing")) {
                return armorStand;
            }
        }
        return null;
    }
    private boolean isFishingEntityAlive() {
        return fishingEntity != null && fishingEntity.isAlive();
    }

    private boolean isFishingArmorstandNearby() {
        return onIsles() && getFishingHoloEntity(fishingEntity) != null && fishingHoloEntity.getId() != getFishingHoloEntity(fishingEntity).getId();
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
    public static Text getMessage(String message, char color) {
        return new LiteralText(message).formatted(Formatting.byCode(color));
    }
    public static List<String> getScoreboard() {
        try {
            assert MinecraftClient.getInstance().player != null;
            Scoreboard board = MinecraftClient.getInstance().player.getScoreboard();
            ScoreboardObjective objective = board.getObjectiveForSlot(1);
            List<String> lines = new ArrayList<>();
            for (ScoreboardPlayerScore score : board.getAllPlayerScores(objective)) {
                Team team = board.getPlayerTeam(score.getPlayerName());
                if (team != null) {
                    String line = team.getPrefix().getString() + team.getSuffix().getString();
                    if (line.trim().length() > 0) {
                        String formatted = Formatting.strip(line);
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
        if (config().firstload) {
            config().lootnotifs.lootnotifslist.add("Key");
            config().lootnotifs.lootnotifslist.add("Rough Amethyst");
            config().lootnotifs.lootnotifslist.add("Rough Ruby");
            config().lootnotifs.lootnotifslist.add("Rough Celestial");
            config().lootnotifs.lootnotifslist.add("Rough Emerald");
            config().lootnotifs.lootnotifslist.add("Rough Diamond");
            config().firstload = false;
        }

        ClientCommandManager.DISPATCHER.register(
                ClientCommandManager.literal("ia").executes(context -> {
                    System.out.println("ran /ia");
                    PlayerEntity player = MinecraftClient.getInstance().player;
                    assert player != null;
                    player.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BIT, 1, 1);
                    return 0;
                })
        );
        ClientCommandManager.DISPATCHER.register(
                ClientCommandManager.literal("printscoreboard").executes((context -> {
                    for (String msg : Objects.requireNonNull(getScoreboard())) {
                        System.out.println(msg);
                    }
                    return 0;
                }))
        );
        ClientTickEvents.START_CLIENT_TICK.register((listener) ->
        {
            try {
                clientTick++;
                if (onIsles()) {
                    if (config().fishingnotifier) {
                        if (isFishing && (!isFishingEntityAlive() || isFishingArmorstandNearby() || client.player.isSneaking() || client.player.getInventory().getEmptySlot() == -1) && !justStartedFishing) {
                            isFishing = false;
                            fishingEntity = null;
                            fishingHoloEntity = null;
                            client.player.playSound(SoundEvents.ENTITY_ELDER_GUARDIAN_CURSE, 1F, 0.8F);
                            client.player.sendMessage(getMessage("You have stopped fishing...", 'e'), false);
                        } else if (justStartedFishing) {
                            justStartedFishing = false;
                        }
                    }
                    if (clientTick == 20) {
                        if (onIsles() && !titlelengthset) {
                            hud().setTitleTicks(5, 20, 5);
                            titlelengthset = true;
                        }
                        List<String> scoreboard = IslesPlusClient.getScoreboard();
                        if (scoreboard == null) {
                            System.out.println("Scoreboard null :(");
                        } else {
                            discordAppCount++;
                            // Night notifier
                            if (config().nightdropdown.nightnotifs) {
                                Pattern currtime = Pattern.compile("^.*" + config().nightdropdown.nightnotiftime + ".*");
                                for (String line : scoreboard) {
                                    Matcher lineMatch = currtime.matcher(line);
                                    if (lineMatch.find()) {
                                        client().player.playSound(SoundEvents.BLOCK_NOTE_BLOCK_PLING, 1, 0.5F);
                                        InGameHud hud = MinecraftClient.getInstance().inGameHud;
                                        hud.setTitle(Text.of("§" + getFormattingFromEnum(config().nightdropdown.nightnotifcolor) + "Night time is soon!"));
                                    }
                                }
                            }
                            // RPC
                            if (discordAppCount == 5) {
                                if (onIsles()) {
                                    if (scoreboard.size() > 1) {
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
                    if (config().glowingparkourskulls) {
                        try {
                            List<Entity> nearbyArmorStands = client.world.getOtherEntities(client.player, client.player.getBoundingBox().expand(client.gameRenderer.getViewDistance(), client.gameRenderer.getViewDistance(), client.gameRenderer.getViewDistance()), (entity -> entity.getType() == EntityType.ARMOR_STAND));
                            for (Entity en : nearbyArmorStands) {
                                if (!en.isGlowing()) {
                                    for (ItemStack stack : en.getArmorItems()) {
                                        if (stack != null && !stack.toString().toUpperCase().contains("AIR") && stack.getNbt() != null && stack.getNbt().get("SkullOwner") != null && stack.getNbt().get("SkullOwner").toString().contains(skullSignature)) {
                                            en.setGlowing(true);
                                        }
                                    }
                                }
                            }
                        } catch (NullPointerException exception) {
                            // ignore exception
                        }
                    }
                }

            }
            catch (NullPointerException e) {
                e.printStackTrace();
            }
            UseEntityCallback.EVENT.register((player, world, hand, entity, ear) -> {
                try {
                    if (config().fishingnotifier) {
                        if (onIsles() && !isFishing && entity.getType() == EntityType.MAGMA_CUBE && !justStartedFishing) {
                            if (client.player.getInventory().getEmptySlot() > -1 && ((MagmaCubeEntity) entity).getSize() > 1 && getFishingHoloEntity(entity) != null) {
                                isFishing = true;
                                justStartedFishing = true;
                                fishingEntity = entity;
                                fishingHoloEntity = getFishingHoloEntity(entity);
                            }
                        }
                    }
                }
                catch(NullPointerException e) {
                    e.printStackTrace();
                }
                return ActionResult.PASS;
            });
            ClientPlayConnectionEvents.JOIN.register((a, b, c) ->
                    setupIPC(MinecraftClient.getInstance()));

            ClientPlayConnectionEvents.DISCONNECT.register((a, b) ->
                    closeIPC());
        });
        }
    }


