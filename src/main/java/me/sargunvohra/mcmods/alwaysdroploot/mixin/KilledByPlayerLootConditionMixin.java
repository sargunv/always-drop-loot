package me.sargunvohra.mcmods.alwaysdroploot.mixin;

import me.sargunvohra.mcmods.alwaysdroploot.config.ReloadListener;
import net.minecraft.world.loot.condition.KilledByPlayerLootCondition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(KilledByPlayerLootCondition.class)
public class KilledByPlayerLootConditionMixin {

  @Inject(at = @At("RETURN"), method = "method_938", cancellable = true)
  private void alwaysKilledByPlayer(CallbackInfoReturnable<Boolean> cir) {
    switch (ReloadListener.INSTANCE.getConfig().getLootDropMode()) {
      case VANILLA:
        break;
      case VANILLA_INVERSE:
        cir.setReturnValue(!cir.getReturnValue());
        break;
      case ALWAYS_AS_PLAYER:
        cir.setReturnValue(true);
        break;
      case NEVER_AS_PLAYER:
        cir.setReturnValue(false);
        break;
    }
  }
}
