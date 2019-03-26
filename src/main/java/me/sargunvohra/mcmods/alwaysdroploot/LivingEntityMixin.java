package me.sargunvohra.mcmods.alwaysdroploot;

import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

  @Inject(at = @At("RETURN"), method = "method_6071", cancellable = true)
  private void alwaysDropXp(CallbackInfoReturnable<Boolean> cir) {
    cir.setReturnValue(true);
  }
}
