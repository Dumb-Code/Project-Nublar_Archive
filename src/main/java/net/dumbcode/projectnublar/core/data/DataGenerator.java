package net.dumbcode.projectnublar.core.data;

import net.dumbcode.projectnublar.ProjectNublar;
import net.dumbcode.projectnublar.core.exceptions.UtilityClassException;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = ProjectNublar.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerator {
    private DataGenerator() {
        throw new UtilityClassException();
    }

    @SubscribeEvent
    public static void gatherData(@NotNull GatherDataEvent event) {
        ProjectNublar.LOGGER.debug("Gathering data for Project Nublar");
        net.minecraft.data.DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeServer(), new ModRecipeProvider(output));
        generator.addProvider(event.includeServer(), ModLootTableProvider.create(output));
        generator.addProvider(event.includeClient(), new ModBlockStateProvider(output, existingFileHelper));
        generator.addProvider(event.includeClient(), new ModItemModelProvider(output, existingFileHelper));

        ModBlockTagGenerator blockTagGenerator = generator.addProvider(event.includeServer(), new ModBlockTagGenerator(output, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModItemTagGenerator(output, lookupProvider, blockTagGenerator.contentsGetter(), existingFileHelper));
    }
}
