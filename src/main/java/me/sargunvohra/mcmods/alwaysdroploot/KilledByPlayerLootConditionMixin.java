package me.sargunvohra.mcmods.alwaysdroploot;

import net.minecraft.world.loot.condition.KilledByPlayerLootCondition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(KilledByPlayerLootCondition.class)
public class KilledByPlayerLootConditionMixin {

  @Inject(at = @At("RETURN"), method = "method_938", cancellable = true)
  private void alwaysKilledByPlayer(CallbackInfoReturnable<Boolean> cir) {
    cir.setReturnValue(true);
  }
}
