package net.dumbcode.projectnublar.core.data;

import net.dumbcode.projectnublar.core.blocks.DumbBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

import static net.dumbcode.projectnublar.ProjectNublar.MOD_ID;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        List<DumbBlocks.Blocks> blocks = Arrays.stream(DumbBlocks.Blocks.values()).toList();
        for (DumbBlocks.Blocks block : blocks) {
            blockWithItem(block.getRegistry().block());
        }
    }

    private void blockWithItem(@NotNull RegistryObject<Block> registryObject) {
        simpleBlockWithItem(registryObject.get(), cubeAll(registryObject.get()));
    }
}
