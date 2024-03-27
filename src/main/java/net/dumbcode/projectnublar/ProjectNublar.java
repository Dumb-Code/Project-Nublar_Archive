package net.dumbcode.projectnublar;

import net.dumbcode.projectnublar.config.ClientConfig;
import com.mojang.logging.LogUtils;
import net.dumbcode.projectnublar.config.ServerConfig;
import net.dumbcode.projectnublar.core.data.DataGenerator;
import net.dumbcode.projectnublar.core.registry.Registrar;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ProjectNublar.MOD_ID)
public class ProjectNublar {

    public static final String MOD_ID = "projectnublar";
    public static final Logger LOGGER = LogUtils.getLogger();

    // Create a Deferred Register to hold Blocks which will all be registered under the "projectnublar" namespace
    public ProjectNublar() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        Registrar.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ServerConfig.SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);

        LOGGER.info("Hello from Project Nublar!");
    }

    @Contract("_ -> new")
    public static @NotNull ResourceLocation resourceLocation(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
