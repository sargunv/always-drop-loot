package me.sargunvohra.mcmods.alwaysdroploot.config;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@SuppressWarnings("WeakerAccess")
@Data
@RequiredArgsConstructor
public class AlwaysDropLootConfig {
  private final boolean alwaysDropXp;
  private final LootDropMode lootDropMode;

  AlwaysDropLootConfig() {
    this(false, LootDropMode.VANILLA);
  }

  void validate() {}

  public enum LootDropMode {
    VANILLA,
    VANILLA_INVERSE,
    ALWAYS_AS_PLAYER,
    NEVER_AS_PLAYER,
  }
}
