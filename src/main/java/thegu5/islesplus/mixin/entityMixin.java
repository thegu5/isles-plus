package thegu5.islesplus.mixin;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class entityMixin {

    @Shadow
    private boolean glowing;

    @Shadow
    protected abstract void setFlag(int index, boolean value);

    @Inject(method = "setGlowing", at = @At("HEAD"))
    public void setGlowing(boolean glowing, CallbackInfo ci) {
        this.glowing = glowing;
        this.setFlag(6, glowing);
    }
}