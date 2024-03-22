package net.dumbcode.projectnublar.data;

import net.dumbcode.projectnublar.ProjectNublar;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ProjectNublar.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGen {
    @SubscribeEvent
    public void onDataGather(GatherDataEvent event) {
        ProjectNublar.LOGGER.info("Gathering data: " + event.getGenerator());
    }
}
