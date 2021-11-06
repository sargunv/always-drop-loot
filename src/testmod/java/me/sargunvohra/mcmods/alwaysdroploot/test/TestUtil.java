package me.sargunvohra.mcmods.alwaysdroploot.test;

import net.minecraft.gametest.framework.GameTestHelper;

public class TestUtil {

  public static void runCommand(GameTestHelper helper, String command) {
    var server = helper.getLevel().getServer();
    server
      .getCommands()
      .performCommand(server.createCommandSourceStack(), command);
  }
}
