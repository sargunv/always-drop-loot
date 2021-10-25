package me.sargunvohra.mcmods.alwaysdroploot.mixin;

import me.sargunvohra.mcmods.alwaysdroploot.AlwaysDropLoot;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

  @Inject(
    at = @At("RETURN"),
    method = "isAlwaysExperienceDropper",
    cancellable = true
  )
  private void alwaysDropXp(CallbackInfoReturnable<Boolean> cir) {
    var entity = (LivingEntity) (Object) this;
    var enabled = entity
      .getCommandSenderWorld()
      .getGameRules()
      .getRule(AlwaysDropLoot.ALWAYS_DROP_XP)
      .get();
    if (enabled) {
      cir.setReturnValue(true);
    }
  }
}
