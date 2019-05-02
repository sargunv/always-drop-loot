package me.sargunvohra.mcmods.alwaysdroploot.config;

import com.google.gson.Gson;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;
import java.io.InputStreamReader;

public class ReloadListener implements SimpleSynchronousResourceReloadListener {

    public static ReloadListener INSTANCE = new ReloadListener();

    private AlwaysDropLootConfig config = new AlwaysDropLootConfig();

    private ReloadListener() {
    }

    @Override
    public Identifier getFabricId() {
        return new Identifier("alwaysdroploot", "reload_listener");
    }

    @Override
    public void apply(ResourceManager resourceManager) {
        try {
            Resource res = resourceManager
                .getResource(new Identifier("alwaysdroploot", "alwaysdroploot/config.json"));
            AlwaysDropLootConfig config = new Gson()
                .fromJson(new InputStreamReader(res.getInputStream()), AlwaysDropLootConfig.class);
            config.validate();
            LogManager.getLogger().info("Loaded {}", config);
            this.config = config;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public AlwaysDropLootConfig getConfig() {
        return config;
    }
}
