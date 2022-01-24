package thegu5.islesplus.mixin;

import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.ChatHudLine;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

import static thegu5.islesplus.client.IslesPlusClient.config;
import static thegu5.islesplus.client.IslesPlusClient.getMessage;

@Mixin(ChatHud.class)
public abstract class chatHudMixin {

    @Shadow
    protected abstract void addMessage(Text chatComponent, int chatLineId);

    @Shadow
    protected abstract void removeMessage(int id);

    @Shadow @Final
    private List<ChatHudLine<Text>> messages;
    @Shadow @Final
    private List<ChatHudLine<OrderedText>> visibleMessages;

    private int amount;
    private boolean sentStackMessage = false;
    private Text lastMessage = null;
    private Text stackMessage = null;

    @Inject(method = "addMessage(Lnet/minecraft/text/Text;I)V", at = @At("TAIL"))
    private void addMessage(Text chatComponent, int chatLineId, CallbackInfo ci) {
        if (config().stackchat) {
            if (!sentStackMessage) {
                Text stackText = null;
                if (stackMessage != null && chatComponent.getString().equals(stackMessage.getString())) {
                    if (amount == 1) removeLastSimilarMessage(stackMessage);
                    if (stackMessage != null) removeLastSimilarMessage(stackMessage);
                    if (lastMessage != null) removeLastSimilarMessage(lastMessage);
                    amount++;

                    Text amountString =
                            getMessage((" (%amount%)").replace(
                                    "%amount%",
                                    String.valueOf(amount)), '7');
                    if (chatComponent.getSiblings().isEmpty()) {
                        stackText =
                                chatComponent.copy().setStyle(chatComponent.getStyle()).append(amountString);
                    } else {
                        chatComponent.getSiblings().add(amountString);
                        stackText = chatComponent;
                    }
                } else {
                    amount = 1;
                    stackMessage = chatComponent;
                    lastMessage = null;
                }
                if (amount > 1 && stackText != null && !ci.isCancelled()) {
                    sentStackMessage = true;
                    addMessage(stackText, 0);
                    lastMessage = stackText;
                }
            } else {
                sentStackMessage = false;
            }
        }
    }

    @Inject(method = "clear", at = @At("HEAD"))
    public void clear(boolean clearHistory, CallbackInfo ci) {
        this.messages.clear();
    }

    private void removeLastSimilarMessage(Text similar) {
        int line = -1;
        for (int i = messages.size() - 1; i >= 0; i--) {
            if (messages.get(i).getText().getString().equals(similar.getString())) {
                line = i;
            }
        }
        if (line >= 0) removeChatLine(line);
    }

    private void removeChatLine(int line) {
        if (messages.size() > line) messages.remove(line);
        if (visibleMessages.size() > line) visibleMessages.remove(line);
    }

}