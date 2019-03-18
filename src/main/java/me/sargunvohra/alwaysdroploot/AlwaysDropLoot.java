package me.sargunvohra.alwaysdroploot;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(BuildConfig.NAME)
public class AlwaysDropLoot {
  public AlwaysDropLoot() {
    MinecraftForge.EVENT_BUS.register(this);
  }

  @SubscribeEvent
  public void onLivingDeath(LivingDeathEvent event) {
    Entity entity = event.getEntity();
    if (entity.world.isRemote) return;
    if (!(entity instanceof EntityLivingBase)) return;

    EntityLivingBase living = (EntityLivingBase) entity;
    if (living.recentlyHit <= 0) living.recentlyHit = 60;
    if (living.attackingPlayer == null)
      living.attackingPlayer = FakePlayerFactory.getMinecraft(((WorldServer) entity.world));
  }
}
