package me.sargunvohra.mcmods.alwaysdroploot.mixin;

import me.sargunvohra.mcmods.alwaysdroploot.config.ReloadListener;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(at = @At("RETURN"), method = "shouldAlwaysDropXp", cancellable = true)
    private void alwaysDropXp(CallbackInfoReturnable<Boolean> cir) {
        if (ReloadListener.INSTANCE.getConfig().isAlwaysDropXp()) {
            cir.setReturnValue(true);
        }
    }
}
