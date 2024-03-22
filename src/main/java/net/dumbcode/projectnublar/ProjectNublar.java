package net.dumbcode.projectnublar;

import com.mojang.logging.LogUtils;
import net.dumbcode.projectnublar.config.ClientConfig;
import net.dumbcode.projectnublar.config.ServerConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import static net.dumbcode.projectnublar.Registries.*;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ProjectNublar.MODID)
public class ProjectNublar {

    public static final String MODID = "projectnublar";
    public static final Logger LOGGER = LogUtils.getLogger();

    // Create a Deferred Register to hold Blocks which will all be registered under the "projectnublar" namespace
    public ProjectNublar() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        // Register the Deferred Register to the mod event bus so blocks get registered
        BLOCKS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        ITEMS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        CREATIVE_MODE_TABS.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ServerConfig.SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);
    }

    @Contract("_ -> new")
    public static @NotNull ResourceLocation resourceLocation(String path) {
        return new ResourceLocation(MODID, path);
    }
}
