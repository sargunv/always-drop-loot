package me.sargunvohra.mcmods.alwaysdroploot.test;

import me.sargunvohra.mcmods.alwaysdroploot.LootDropMode;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;

public class LootDropModeTests {

  private static final String TEMPLATE =
    "always-drop-loot-testmod:1x2x1_chamber";

  private void testCase(
    GameTestHelper helper,
    LootDropMode mode,
    boolean asPlayer,
    boolean expectDrop
  ) {
    TestUtil.runCommand(
      helper,
      "gamerule always-drop-loot:lootDropMode " + mode
    );
    var entity = helper.spawn(EntityType.BLAZE, 1, 2, 1);
    if (asPlayer) {
      entity.hurt(DamageSource.playerAttack(helper.makeMockPlayer()), 1000f);
    } else {
      entity.kill();
    }
    helper.succeedIf(() ->
      helper.assertItemEntityCountIs(
        Items.DIAMOND,
        new BlockPos(1, 2, 1),
        0,
        expectDrop ? 1 : 0
      )
    );
  }

  @GameTest(template = TEMPLATE, batch = "lootDropMode=VANILLA")
  public void vanillaModeWithPlayer(GameTestHelper helper) {
    testCase(helper, LootDropMode.VANILLA, true, true);
  }

  @GameTest(template = TEMPLATE, batch = "lootDropMode=VANILLA")
  public void vanillaModeWithoutPlayer(GameTestHelper helper) {
    testCase(helper, LootDropMode.VANILLA, false, false);
  }

  @GameTest(template = TEMPLATE, batch = "lootDropMode=VANILLA_INVERSE")
  public void inverseModeWithPlayer(GameTestHelper helper) {
    testCase(helper, LootDropMode.VANILLA_INVERSE, true, false);
  }

  @GameTest(template = TEMPLATE, batch = "lootDropMode=VANILLA_INVERSE")
  public void inverseModeWithoutPlayer(GameTestHelper helper) {
    testCase(helper, LootDropMode.VANILLA_INVERSE, false, true);
  }

  @GameTest(template = TEMPLATE, batch = "lootDropMode=ALWAYS_AS_PLAYER")
  public void alwaysModeWithPlayer(GameTestHelper helper) {
    testCase(helper, LootDropMode.ALWAYS_AS_PLAYER, true, true);
  }

  @GameTest(template = TEMPLATE, batch = "lootDropMode=ALWAYS_AS_PLAYER")
  public void alwaysModeWithoutPlayer(GameTestHelper helper) {
    testCase(helper, LootDropMode.ALWAYS_AS_PLAYER, false, true);
  }

  @GameTest(template = TEMPLATE, batch = "lootDropMode=NEVER_AS_PLAYER")
  public void neverModeWithPlayer(GameTestHelper helper) {
    testCase(helper, LootDropMode.NEVER_AS_PLAYER, true, false);
  }

  @GameTest(template = TEMPLATE, batch = "lootDropMode=NEVER_AS_PLAYER")
  public void neverModeWithoutPlayer(GameTestHelper helper) {
    testCase(helper, LootDropMode.NEVER_AS_PLAYER, false, false);
  }
}
