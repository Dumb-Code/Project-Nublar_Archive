package net.dumbcode.projectnublar.forge;

import dev.architectury.platform.forge.EventBuses;
import net.dumbcode.projectnublar.ProjectNublar;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ProjectNublar.MOD_ID)
public class ProjectNublarForge {
    public ProjectNublarForge() {
		// Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(ProjectNublar.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        ProjectNublar.init();
    }
}