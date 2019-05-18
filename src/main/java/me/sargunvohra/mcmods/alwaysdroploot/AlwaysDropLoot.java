package me.sargunvohra.mcmods.alwaysdroploot;

import me.sargunvohra.mcmods.alwaysdroploot.config.ReloadListener;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;

public class AlwaysDropLoot implements ModInitializer {
    @Override
    public void onInitialize() {
        ResourceManagerHelper.get(ResourceType.SERVER_DATA)
            .registerReloadListener(ReloadListener.INSTANCE);
    }
}
