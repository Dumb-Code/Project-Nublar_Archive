package net.dumbcode.projectnublar.core.data;

import net.dumbcode.projectnublar.ProjectNublar;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(modid = ProjectNublar.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGen {
    @SubscribeEvent
    public void onDataGather(@NotNull GatherDataEvent event) {
        ProjectNublar.LOGGER.info("Gathering data: " + event.getGenerator());
    }
}
