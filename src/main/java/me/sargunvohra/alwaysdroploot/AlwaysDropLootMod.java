/* Licensed under Apache-2.0 */
package me.sargunvohra.alwaysdroploot;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.WorldServer;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = AlwaysDropLootMod.MOD_ID, useMetadata = true, acceptableRemoteVersions = "*")
public class AlwaysDropLootMod {
  static final String MOD_ID = "@MOD_ID@";

  @EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    MinecraftForge.EVENT_BUS.register(this);
  }

  @SubscribeEvent
  void onLivingDeath(LivingDeathEvent event) {
    Entity entity = event.getEntity();
    if (entity.world.isRemote) return;
    if (!(entity instanceof EntityLivingBase)) return;

    EntityLivingBase living = (EntityLivingBase) entity;
    if (living.recentlyHit <= 0) living.recentlyHit = 100;
    if (living.attackingPlayer == null)
      living.attackingPlayer = FakePlayerFactory.getMinecraft(((WorldServer) entity.world));
  }
}
