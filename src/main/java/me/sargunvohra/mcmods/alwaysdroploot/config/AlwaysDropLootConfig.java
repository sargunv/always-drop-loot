package me.sargunvohra.mcmods.alwaysdroploot.config;

@SuppressWarnings("WeakerAccess")
public class AlwaysDropLootConfig {
    public final boolean alwaysDropXp;
    public final LootDropMode lootDropMode;

    AlwaysDropLootConfig(boolean alwaysDropXp, LootDropMode lootDropMode) {
        this.alwaysDropXp = alwaysDropXp;
        this.lootDropMode = lootDropMode;
    }

    AlwaysDropLootConfig() {
        this(false, LootDropMode.VANILLA);
    }

    @Override
    public String toString() {
        return "AlwaysDropLootConfig{" +
            "alwaysDropXp=" + alwaysDropXp +
            ", lootDropMode=" + lootDropMode +
            '}';
    }

    void validate() {
    }

    public enum LootDropMode {
        VANILLA,
        VANILLA_INVERSE,
        ALWAYS_AS_PLAYER,
        NEVER_AS_PLAYER,
    }
}
