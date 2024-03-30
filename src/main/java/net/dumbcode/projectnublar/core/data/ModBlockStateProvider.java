package net.dumbcode.projectnublar.core.data;

import net.dumbcode.projectnublar.core.blocks.DumbBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import static net.dumbcode.projectnublar.ProjectNublar.MOD_ID;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, MOD_ID, exFileHelper);
    }

    public final class Generator {
        private final BlockStateProvider provider;
        private final RegistryObject<Block> block;
        private final String registerName;

        public Generator(BlockStateProvider provider, String registerName, RegistryObject<Block> block) {
            this.provider = provider;
            this.registerName = registerName;
            this.block = block;
        }

        @Contract("_ -> this")
        public Generator variantBuilder(@NotNull UnaryOperator<VariantBlockStateBuilder> variantBuilder) {
            VariantBlockStateBuilder localVariantBuilder = provider.getVariantBuilder(block.get());
            variantBuilder.apply(localVariantBuilder);
            return this;
        }

        public Generator multipartBuilder(@NotNull UnaryOperator<MultiPartBlockStateBuilder> multipartBuilder) {
            MultiPartBlockStateBuilder localMultipartBuilder = provider.getMultipartBuilder(block.get());
            multipartBuilder.apply(localMultipartBuilder);
            return this;
        }

        public ResourceLocation key() {
            return ForgeRegistries.BLOCKS.getKey(block.get());
        }

        public @NotNull String name() {
            return key().getPath();
        }

        public ResourceLocation blockTexture() {
            return provider.blockTexture(block.get());
        }

        public ResourceLocation blockTexture(DumbBlocks.@NotNull Blocks block) {
            return provider.blockTexture(block.getRegistry().block().get());
        }

        @Contract("_, _ -> new")
        public @NotNull ResourceLocation extend(@NotNull ResourceLocation rl, String suffix) {
            return new ResourceLocation(rl.getNamespace(), rl.getPath() + suffix);
        }

        public ModelFile cubeAll() {
            return provider.cubeAll(block.get());
        }

        public Generator simple() {
            provider.simpleBlock(block.get());
            return this;
        }

        public Generator simple(Function<ModelFile, ConfiguredModel[]> expander) {
            provider.simpleBlock(block.get(), expander);
            return this;
        }

        public Generator simple(ModelFile model) {
            provider.simpleBlock(block.get(), model);
            return this;
        }

        public Generator simpleItem(ModelFile model) {
            provider.simpleBlockItem(block.get(), model);
            return this;
        }

        public Generator simpleWithItem(ModelFile model) {
            provider.simpleBlockWithItem(block.get(), model);
            return this;
        }

        public Generator simpleWithItem() {
            Block localBlock = block.get();
            provider.simpleBlockWithItem(localBlock, cubeAll());
            return this;
        }

        public Generator simple(ConfiguredModel ... models) {
            provider.simpleBlock(block.get(), models);
            return this;
        }

        public Generator axis() {
            Block localBlock = block.get();
            if (!(localBlock instanceof RotatedPillarBlock rotatedBlock)) throw new IllegalArgumentException("Block must be a RotatedPillarBlock");
            provider.axisBlock(rotatedBlock);
            return this;
        }

        public Generator log() {
            Block localBlock = block.get();
            if (!(localBlock instanceof RotatedPillarBlock rotatedBlock)) throw new IllegalArgumentException("Block must be a RotatedPillarBlock");
            provider.logBlock(rotatedBlock);
            return this;
        }

        public Generator axis(ResourceLocation baseName) {
            Block localBlock = block.get();
            if (!(localBlock instanceof RotatedPillarBlock rotatedBlock)) throw new IllegalArgumentException("Block must be a RotatedPillarBlock");
            provider.axisBlock(rotatedBlock, baseName);
            return this;
        }

        public Generator axis(ResourceLocation side, ResourceLocation end) {
            Block localBlock = block.get();
            if (!(localBlock instanceof RotatedPillarBlock rotatedBlock)) throw new IllegalArgumentException("Block must be a RotatedPillarBlock");
            provider.axisBlock(rotatedBlock, side, end);
            return this;
        }

        public Generator axisWithRenderType(String renderType) {
            Block localBlock = block.get();
            if (!(localBlock instanceof RotatedPillarBlock rotatedBlock)) throw new IllegalArgumentException("Block must be a RotatedPillarBlock");
            provider.axisBlockWithRenderType(rotatedBlock, renderType);
            return this;
        }

        public Generator logWithRenderType(String renderType) {
            Block localBlock = block.get();
            if (!(localBlock instanceof RotatedPillarBlock rotatedBlock)) throw new IllegalArgumentException("Block must be a RotatedPillarBlock");
            provider.logBlockWithRenderType(rotatedBlock, renderType);
            return this;
        }

        public Generator axisWithRenderType(ResourceLocation baseName, String renderType) {
            Block localBlock = block.get();
            if (!(localBlock instanceof RotatedPillarBlock rotatedBlock)) throw new IllegalArgumentException("Block must be a RotatedPillarBlock");
            provider.axisBlockWithRenderType(rotatedBlock, baseName, renderType);
            return this;
        }

        public Generator axisWithRenderType(ResourceLocation side, ResourceLocation end, String renderType) {
            Block localBlock = block.get();
            if (!(localBlock instanceof RotatedPillarBlock rotatedBlock)) throw new IllegalArgumentException("Block must be a RotatedPillarBlock");
            provider.axisBlockWithRenderType(rotatedBlock, side, end, renderType);
            return this;
        }

        public Generator axisWithRenderType(ResourceLocation renderType) {
            Block localBlock = block.get();
            if (!(localBlock instanceof RotatedPillarBlock rotatedBlock)) throw new IllegalArgumentException("Block must be a RotatedPillarBlock");
            provider.axisBlockWithRenderType(rotatedBlock, renderType);
            return this;
        }

        public Generator logWithRenderType(ResourceLocation renderType) {
            Block localBlock = block.get();
            if (!(localBlock instanceof RotatedPillarBlock rotatedBlock)) throw new IllegalArgumentException("Block must be a RotatedPillarBlock");
            provider.logBlockWithRenderType(rotatedBlock, renderType);
            return this;
        }

        public Generator axisWithRenderType(ResourceLocation baseName, ResourceLocation renderType) {
            Block localBlock = block.get();
            if (!(localBlock instanceof RotatedPillarBlock rotatedBlock)) throw new IllegalArgumentException("Block must be a RotatedPillarBlock");
            provider.axisBlockWithRenderType(rotatedBlock, baseName, renderType);
            return this;
        }

        public Generator axisWithRenderType(ResourceLocation side, ResourceLocation end, ResourceLocation renderType) {
            Block localBlock = block.get();
            if (!(localBlock instanceof RotatedPillarBlock rotatedBlock)) throw new IllegalArgumentException("Block must be a RotatedPillarBlock");
            provider.axisBlockWithRenderType(rotatedBlock, side, end, renderType);
            return this;
        }

        public Generator axis(ModelFile vertical, ModelFile horizontal) {
            Block localBlock = block.get();
            if (!(localBlock instanceof RotatedPillarBlock rotatedBlock)) throw new IllegalArgumentException("Block must be a RotatedPillarBlock");
            provider.axisBlock(rotatedBlock, vertical, horizontal);
            return this;
        }

        public Generator horizontal(ResourceLocation side, ResourceLocation front, ResourceLocation top) {
            provider.horizontalBlock(block.get(), side, front, top);
            return this;
        }

        public Generator horizontal(ModelFile model) {
            provider.horizontalBlock(block.get(), model);
            return this;
        }

        public Generator horizontal(ModelFile model, int angleOffset) {
            provider.horizontalBlock(block.get(), model, angleOffset);
            return this;
        }

        public Generator horizontal(Function<BlockState, ModelFile> modelFunc) {
            provider.horizontalBlock(block.get(), modelFunc);
            return this;
        }

        public Generator horizontal(Function<BlockState, ModelFile> modelFunc, int angleOffset) {
            provider.horizontalBlock(block.get(), modelFunc, angleOffset);
            return this;
        }

        public Generator horizontalFace(ModelFile model) {
            provider.horizontalFaceBlock(block.get(), model);
            return this;
        }

        public Generator horizontalFace(ModelFile model, int angleOffset) {
            provider.horizontalFaceBlock(block.get(), model, angleOffset);
            return this;
        }

        public Generator horizontalFace(Function<BlockState, ModelFile> modelFunc) {
            provider.horizontalFaceBlock(block.get(), modelFunc);
            return this;
        }

        public Generator horizontalFace(Function<BlockState, ModelFile> modelFunc, int angleOffset) {
            provider.horizontalFaceBlock(block.get(), modelFunc, angleOffset);
            return this;
        }

        public Generator directional(ModelFile model) {
            provider.directionalBlock(block.get(), model);
            return this;
        }

        public Generator directional(ModelFile model, int angleOffset) {
            provider.directionalBlock(block.get(), model, angleOffset);
            return this;
        }

        public Generator directional(Function<BlockState, ModelFile> modelFunc) {
            provider.directionalBlock(block.get(), modelFunc);
            return this;
        }

        public Generator directional(Function<BlockState, ModelFile> modelFunc, int angleOffset) {
            provider.directionalBlock(block.get(), modelFunc, angleOffset);
            return this;
        }

        public Generator stairs(ResourceLocation texture) {
            if (!(block.get() instanceof StairBlock stairBlock)) throw new IllegalArgumentException("Block must be a StairBlock");
            provider.stairsBlock(stairBlock, texture);
            return this;
        }

        public Generator stairs(String name, ResourceLocation texture) {
            if (!(block.get() instanceof StairBlock stairBlock)) throw new IllegalArgumentException("Block must be a StairBlock");
            provider.stairsBlock(stairBlock, name, texture);
            return this;
        }

        public Generator stairs(ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
            if (!(block.get() instanceof StairBlock stairBlock)) throw new IllegalArgumentException("Block must be a StairBlock");
            provider.stairsBlock(stairBlock, side, bottom, top);
            return this;
        }

        public Generator stairs(String name, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
            if (!(block.get() instanceof StairBlock stairBlock)) throw new IllegalArgumentException("Block must be a StairBlock");
            provider.stairsBlock(stairBlock, name, side, bottom, top);
            return this;
        }

        public Generator stairsWithRenderType(ResourceLocation texture, String renderType) {
            if (!(block.get() instanceof StairBlock stairBlock)) throw new IllegalArgumentException("Block must be a StairBlock");
            provider.stairsBlockWithRenderType(stairBlock, texture, renderType);
            return this;
        }

        public Generator stairsWithRenderType(String name, ResourceLocation texture, String renderType) {
            if (!(block.get() instanceof StairBlock stairBlock)) throw new IllegalArgumentException("Block must be a StairBlock");
            provider.stairsBlockWithRenderType(stairBlock, name, texture, renderType);
            return this;
        }

        public Generator stairsWithRenderType(ResourceLocation side, ResourceLocation bottom, ResourceLocation top, String renderType) {
            if (!(block.get() instanceof StairBlock stairBlock)) throw new IllegalArgumentException("Block must be a StairBlock");
            provider.stairsBlockWithRenderType(stairBlock, side, bottom, top, renderType);
            return this;
        }

        public Generator stairsWithRenderType(String name, ResourceLocation side, ResourceLocation bottom, ResourceLocation top, String renderType) {
            if (!(block.get() instanceof StairBlock stairBlock)) throw new IllegalArgumentException("Block must be a StairBlock");
            provider.stairsBlockWithRenderType(stairBlock, name, side, bottom, top, renderType);
            return this;
        }

        public Generator stairsWithRenderType(ResourceLocation texture, ResourceLocation renderType) {
            if (!(block.get() instanceof StairBlock stairBlock)) throw new IllegalArgumentException("Block must be a StairBlock");
            provider.stairsBlockWithRenderType(stairBlock, texture, renderType);
            return this;
        }

        public Generator stairsWithRenderType(String name, ResourceLocation texture, ResourceLocation renderType) {
            if (!(block.get() instanceof StairBlock stairBlock)) throw new IllegalArgumentException("Block must be a StairBlock");
            provider.stairsBlockWithRenderType(stairBlock, name, texture, renderType);
            return this;
        }

        public Generator stairsWithRenderType(ResourceLocation side, ResourceLocation bottom, ResourceLocation top, ResourceLocation renderType) {
            if (!(block.get() instanceof StairBlock stairBlock)) throw new IllegalArgumentException("Block must be a StairBlock");
            provider.stairsBlockWithRenderType(stairBlock, side, bottom, top, renderType);
            return this;
        }

        public Generator stairsWithRenderType(String name, ResourceLocation side, ResourceLocation bottom, ResourceLocation top, ResourceLocation renderType) {
            if (!(block.get() instanceof StairBlock stairBlock)) throw new IllegalArgumentException("Block must be a StairBlock");
            provider.stairsBlockWithRenderType(stairBlock, name, side, bottom, top, renderType);
            return this;
        }

        public Generator stairs(ModelFile stairs, ModelFile stairsInner, ModelFile stairsOuter) {
            if (!(block.get() instanceof StairBlock stairBlock)) throw new IllegalArgumentException("Block must be a StairBlock");
            provider.stairsBlock(stairBlock, stairs, stairsInner, stairsOuter);
            return this;
        }

        public Generator slab(ResourceLocation doubleslab, ResourceLocation texture) {
            if (!(block.get() instanceof SlabBlock slabBlock)) throw new IllegalArgumentException("Block must be a SlabBlock");
            provider.slabBlock(slabBlock, doubleslab, texture);
            return this;
        }

        public Generator slab(ResourceLocation doubleslab, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
            if (!(block.get() instanceof SlabBlock slabBlock)) throw new IllegalArgumentException("Block must be a SlabBlock");
            provider.slabBlock(slabBlock, doubleslab, side, bottom, top);
            return this;
        }

        public Generator slab(ModelFile bottom, ModelFile top, ModelFile doubleslab) {
            if (!(block.get() instanceof SlabBlock slabBlock)) throw new IllegalArgumentException("Block must be a SlabBlock");
            provider.slabBlock(slabBlock, bottom, top, doubleslab);
            return this;
        }

        public Generator button(ResourceLocation texture) {
            if (!(block.get() instanceof ButtonBlock buttonBlock)) throw new IllegalArgumentException("Block must be a ButtonBlock");
            provider.buttonBlock(buttonBlock, texture);
            return this;
        }

        public Generator button(ModelFile button, ModelFile buttonPressed) {
            if (!(block.get() instanceof ButtonBlock buttonBlock)) throw new IllegalArgumentException("Block must be a ButtonBlock");
            provider.buttonBlock(buttonBlock, button, buttonPressed);
            return this;
        }

        public Generator pressurePlate(ResourceLocation texture) {
            if (!(block.get() instanceof PressurePlateBlock pressurePlateBlock)) throw new IllegalArgumentException("Block must be a PressurePlateBlock");
            provider.pressurePlateBlock(pressurePlateBlock, texture);
            return this;
        }

        public Generator pressurePlate(ModelFile pressurePlate, ModelFile pressurePlateDown) {
            if (!(block.get() instanceof PressurePlateBlock pressurePlateBlock)) throw new IllegalArgumentException("Block must be a PressurePlateBlock");
            provider.pressurePlateBlock(pressurePlateBlock, pressurePlate, pressurePlateDown);
            return this;
        }

        public Generator sign(ResourceLocation texture) {
            if (!(block.get() instanceof SignBlock signBlock)) throw new IllegalArgumentException("Block must be a SignBlock");
            ModelFile sign = models().sign(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(signBlock)).getPath(), texture);
            return this.simple(sign);
        }

        public Generator sign(ModelFile sign) {
            if (!(block.get() instanceof SignBlock)) throw new IllegalArgumentException("Block must be a SignBlock");
            return this.simple(sign);
        }

        public Generator fourWay(ModelFile post, ModelFile side) {
            if (!(block.get() instanceof CrossCollisionBlock crossCollisionBlock)) throw new IllegalArgumentException("Block must be a CrossCollisionBlock");
            provider.fourWayBlock(crossCollisionBlock, post, side);
            return this;
        }

        public Generator fourWayMultipart(UnaryOperator<MultiPartBlockStateBuilder> builder, ModelFile side) {
            if (!(block.get() instanceof CrossCollisionBlock crossCollisionBlock)) throw new IllegalArgumentException("Block must be a CrossCollisionBlock");
            MultiPartBlockStateBuilder localBuilder = builder.apply(provider.getMultipartBuilder(crossCollisionBlock));
            provider.fourWayMultipart(localBuilder, side);
            return this;
        }

        public Generator fence(ResourceLocation texture) {
            if (!(block.get() instanceof FenceBlock fenceBlock)) throw new IllegalArgumentException("Block must be a FenceBlock");
            provider.fenceBlock(fenceBlock, texture);
            return this;
        }

        public Generator fence(String name, ResourceLocation texture) {
            if (!(block.get() instanceof FenceBlock fenceBlock)) throw new IllegalArgumentException("Block must be a FenceBlock");
            provider.fenceBlock(fenceBlock, name, texture);
            return this;
        }

        public Generator fenceWithRenderType(ResourceLocation texture, String renderType) {
            if (!(block.get() instanceof FenceBlock fenceBlock)) throw new IllegalArgumentException("Block must be a FenceBlock");
            provider.fenceBlockWithRenderType(fenceBlock, texture, renderType);
            return this;
        }

        public Generator fenceWithRenderType(String name, ResourceLocation texture, String renderType) {
            if (!(block.get() instanceof FenceBlock fenceBlock)) throw new IllegalArgumentException("Block must be a FenceBlock");
            provider.fenceBlockWithRenderType(fenceBlock, name, texture, renderType);
            return this;
        }

        public Generator fenceWithRenderType(ResourceLocation texture, ResourceLocation renderType) {
            if (!(block.get() instanceof FenceBlock fenceBlock)) throw new IllegalArgumentException("Block must be a FenceBlock");
            provider.fenceBlockWithRenderType(fenceBlock, texture, renderType);
            return this;
        }

        public Generator fenceWithRenderType(String name, ResourceLocation texture, ResourceLocation renderType) {
            if (!(block.get() instanceof FenceBlock fenceBlock)) throw new IllegalArgumentException("Block must be a FenceBlock");
            provider.fenceBlockWithRenderType(fenceBlock, name, texture, renderType);
            return this;
        }

        public Generator fenceGate(ResourceLocation texture) {
            if (!(block.get() instanceof FenceGateBlock fenceGateBlock)) throw new IllegalArgumentException("Block must be a FenceGateBlock");
            provider.fenceGateBlock(fenceGateBlock, texture);
            return this;
        }

        public Generator fenceGate(String name, ResourceLocation texture) {
            if (!(block.get() instanceof FenceGateBlock fenceGateBlock)) throw new IllegalArgumentException("Block must be a FenceGateBlock");
            provider.fenceGateBlock(fenceGateBlock, name, texture);
            return this;
        }

        public Generator fenceGateWithRenderType(ResourceLocation texture, String renderType) {
            if (!(block.get() instanceof FenceGateBlock fenceGateBlock)) throw new IllegalArgumentException("Block must be a FenceGateBlock");
            provider.fenceGateBlockWithRenderType(fenceGateBlock, texture, renderType);
            return this;
        }

        public Generator fenceGateWithRenderType(String name, ResourceLocation texture, String renderType) {
            if (!(block.get() instanceof FenceGateBlock fenceGateBlock)) throw new IllegalArgumentException("Block must be a FenceGateBlock");
            provider.fenceGateBlockWithRenderType(fenceGateBlock, name, texture, renderType);
            return this;
        }

        public Generator fenceGateWithRenderType(ResourceLocation texture, ResourceLocation renderType) {
            if (!(block.get() instanceof FenceGateBlock fenceGateBlock)) throw new IllegalArgumentException("Block must be a FenceGateBlock");
            provider.fenceGateBlockWithRenderType(fenceGateBlock, texture, renderType);
            return this;
        }

        public Generator fenceGateWithRenderType(String name, ResourceLocation texture, ResourceLocation renderType) {
            if (!(block.get() instanceof FenceGateBlock fenceGateBlock)) throw new IllegalArgumentException("Block must be a FenceGateBlock");
            provider.fenceGateBlockWithRenderType(fenceGateBlock, name, texture, renderType);
            return this;
        }

        public Generator fenceGate(ModelFile gate, ModelFile gateOpen, ModelFile gateWall, ModelFile gateWallOpen) {
            if (!(block.get() instanceof FenceGateBlock fenceGateBlock)) throw new IllegalArgumentException("Block must be a FenceGateBlock");
            provider.fenceGateBlock(fenceGateBlock, gate, gateOpen, gateWall, gateWallOpen);
            return this;
        }

        public Generator wall(ResourceLocation texture) {
            if (!(block.get() instanceof WallBlock wallBlock)) throw new IllegalArgumentException("Block must be a WallBlock");
            provider.wallBlock(wallBlock, texture);
            return this;
        }

        public Generator wall(String name, ResourceLocation texture) {
            if (!(block.get() instanceof WallBlock wallBlock)) throw new IllegalArgumentException("Block must be a WallBlock");
            provider.wallBlock(wallBlock, name, texture);
            return this;
        }

        public Generator wallWithRenderType(ResourceLocation texture, String renderType) {
            if (!(block.get() instanceof WallBlock wallBlock)) throw new IllegalArgumentException("Block must be a WallBlock");
            provider.wallBlockWithRenderType(wallBlock, texture, renderType);
            return this;
        }

        public Generator wallWithRenderType(String name, ResourceLocation texture, String renderType) {
            if (!(block.get() instanceof WallBlock wallBlock)) throw new IllegalArgumentException("Block must be a WallBlock");
            provider.wallBlockWithRenderType(wallBlock, name, texture, renderType);
            return this;
        }

        public Generator wallWithRenderType(ResourceLocation texture, ResourceLocation renderType) {
            if (!(block.get() instanceof WallBlock wallBlock)) throw new IllegalArgumentException("Block must be a WallBlock");
            provider.wallBlockWithRenderType(wallBlock, texture, renderType);
            return this;
        }

        public Generator wallWithRenderType(String name, ResourceLocation texture, ResourceLocation renderType) {
            if (!(block.get() instanceof WallBlock wallBlock)) throw new IllegalArgumentException("Block must be a WallBlock");
            provider.wallBlockWithRenderType(wallBlock, name, texture, renderType);
            return this;
        }

        public Generator wall(ModelFile post, ModelFile side, ModelFile sideTall) {
            if (!(block.get() instanceof WallBlock wallBlock)) throw new IllegalArgumentException("Block must be a WallBlock");
            provider.wallBlock(wallBlock, post, side, sideTall);
            return this;
        }

        public Generator pane(ResourceLocation pane, ResourceLocation edge) {
            if (!(block.get() instanceof IronBarsBlock paneBlock)) throw new IllegalArgumentException("Block must be a PaneBlock");
            provider.paneBlock(paneBlock, pane, edge);
            return this;
        }

        public Generator pane(String name, ResourceLocation pane, ResourceLocation edge) {
            if (!(block.get() instanceof IronBarsBlock paneBlock)) throw new IllegalArgumentException("Block must be a PaneBlock");
            provider.paneBlock(paneBlock, name, pane, edge);
            return this;
        }

        public Generator paneWithRenderType(ResourceLocation pane, ResourceLocation edge, String renderType) {
            if (!(block.get() instanceof IronBarsBlock paneBlock)) throw new IllegalArgumentException("Block must be a PaneBlock");
            provider.paneBlockWithRenderType(paneBlock, pane, edge, renderType);
            return this;
        }

        public Generator paneWithRenderType(String name, ResourceLocation pane, ResourceLocation edge, String renderType) {
            if (!(block.get() instanceof IronBarsBlock paneBlock)) throw new IllegalArgumentException("Block must be a PaneBlock");
            provider.paneBlockWithRenderType(paneBlock, name, pane, edge, renderType);
            return this;
        }

        public Generator paneWithRenderType(ResourceLocation pane, ResourceLocation edge, ResourceLocation renderType) {
            if (!(block.get() instanceof IronBarsBlock paneBlock)) throw new IllegalArgumentException("Block must be a PaneBlock");
            provider.paneBlockWithRenderType(paneBlock, pane, edge, renderType);
            return this;
        }

        public Generator paneWithRenderType(String name, ResourceLocation pane, ResourceLocation edge, ResourceLocation renderType) {
            if (!(block.get() instanceof IronBarsBlock paneBlock)) throw new IllegalArgumentException("Block must be a PaneBlock");
            provider.paneBlockWithRenderType(paneBlock, name, pane, edge, renderType);
            return this;
        }

        public Generator pane(ModelFile post, ModelFile side, ModelFile sideAlt, ModelFile noSide, ModelFile noSideAlt) {
            if (!(block.get() instanceof IronBarsBlock paneBlock)) throw new IllegalArgumentException("Block must be a PaneBlock");
            provider.paneBlock(paneBlock, post, side, sideAlt, noSide, noSideAlt);
            return this;
        }

        public Generator door(ResourceLocation bottom, ResourceLocation top) {
            if (!(block.get() instanceof DoorBlock doorBlock)) throw new IllegalArgumentException("Block must be a DoorBlock");
            provider.doorBlock(doorBlock, bottom, top);
            return this;
        }

        public Generator door(String name, ResourceLocation bottom, ResourceLocation top) {
            if (!(block.get() instanceof DoorBlock doorBlock)) throw new IllegalArgumentException("Block must be a DoorBlock");
            provider.doorBlock(doorBlock, name, bottom, top);
            return this;
        }

        public Generator doorWithRenderType(ResourceLocation bottom, ResourceLocation top, String renderType) {
            if (!(block.get() instanceof DoorBlock doorBlock)) throw new IllegalArgumentException("Block must be a DoorBlock");
            provider.doorBlockWithRenderType(doorBlock, bottom, top, renderType);
            return this;
        }

        public Generator doorWithRenderType(String renderType) {
            if (!(block.get() instanceof DoorBlock doorBlock)) throw new IllegalArgumentException("Block must be a DoorBlock");
            provider.doorBlockWithRenderType(doorBlock, modLoc("blocks/" + registerName + "_bottom"), modLoc("blocks/" + registerName + "_top"), renderType);
            return this;
        }

        public Generator doorWithRenderType() {
            if (!(block.get() instanceof DoorBlock doorBlock)) throw new IllegalArgumentException("Block must be a DoorBlock");
            provider.doorBlockWithRenderType(doorBlock, modLoc("blocks/" + registerName + "_bottom"), modLoc("blocks/" + registerName + "_top"), "cutout");
            return this;
        }

        public Generator doorWithRenderType(String name, ResourceLocation bottom, ResourceLocation top, String renderType) {
            if (!(block.get() instanceof DoorBlock doorBlock)) throw new IllegalArgumentException("Block must be a DoorBlock");
            provider.doorBlockWithRenderType(doorBlock, name, bottom, top, renderType);
            return this;
        }

        public Generator doorWithRenderType(ResourceLocation bottom, ResourceLocation top, ResourceLocation renderType) {
            if (!(block.get() instanceof DoorBlock doorBlock)) throw new IllegalArgumentException("Block must be a DoorBlock");
            provider.doorBlockWithRenderType(doorBlock, bottom, top, renderType);
            return this;
        }

        public Generator doorWithRenderType(String name, ResourceLocation bottom, ResourceLocation top, ResourceLocation renderType) {
            if (!(block.get() instanceof DoorBlock doorBlock)) throw new IllegalArgumentException("Block must be a DoorBlock");
            provider.doorBlockWithRenderType(doorBlock, name, bottom, top, renderType);
            return this;
        }

        public Generator door(ModelFile bottomLeft, ModelFile bottomLeftOpen, ModelFile bottomRight, ModelFile bottomRightOpen, ModelFile topLeft, ModelFile topLeftOpen, ModelFile topRight, ModelFile topRightOpen) {
            if (!(block.get() instanceof DoorBlock doorBlock)) throw new IllegalArgumentException("Block must be a DoorBlock");
            provider.doorBlock(doorBlock, bottomLeft, bottomLeftOpen, bottomRight, bottomRightOpen, topLeft, topLeftOpen, topRight, topRightOpen);
            return this;
        }

        public Generator trapdoor(ResourceLocation texture, boolean orientable) {
            if (!(block.get() instanceof TrapDoorBlock trapDoorBlock)) throw new IllegalArgumentException("Block must be a TrapDoorBlock");
            provider.trapdoorBlock(trapDoorBlock, texture, orientable);
            return this;
        }

        public Generator trapdoor(String name, ResourceLocation texture, boolean orientable) {
            if (!(block.get() instanceof TrapDoorBlock trapDoorBlock)) throw new IllegalArgumentException("Block must be a TrapDoorBlock");
            provider.trapdoorBlock(trapDoorBlock, name, texture, orientable);
            return this;
        }

        public Generator trapdoorWithRenderType(ResourceLocation texture, boolean orientable, String renderType) {
            if (!(block.get() instanceof TrapDoorBlock trapDoorBlock)) throw new IllegalArgumentException("Block must be a TrapDoorBlock");
            provider.trapdoorBlockWithRenderType(trapDoorBlock, texture, orientable, renderType);
            return this;
        }

        public Generator trapdoorWithRenderType(boolean orientable, String renderType) {
            if (!(block.get() instanceof TrapDoorBlock trapDoorBlock)) throw new IllegalArgumentException("Block must be a TrapDoorBlock");
            provider.trapdoorBlockWithRenderType(trapDoorBlock, modLoc("blocks/" + registerName), orientable, renderType);
            return this;
        }

        public Generator trapdoorWithRenderType(String renderType) {
            if (!(block.get() instanceof TrapDoorBlock trapDoorBlock)) throw new IllegalArgumentException("Block must be a TrapDoorBlock");
            provider.trapdoorBlockWithRenderType(trapDoorBlock, modLoc("blocks/" + registerName), true, renderType);
            return this;
        }

        public Generator trapdoorWithRenderType(boolean orientable) {
            if (!(block.get() instanceof TrapDoorBlock trapDoorBlock)) throw new IllegalArgumentException("Block must be a TrapDoorBlock");
            provider.trapdoorBlockWithRenderType(trapDoorBlock, modLoc("blocks/" + registerName), orientable, "cutout");
            return this;
        }

        public Generator trapdoorWithRenderType() {
            if (!(block.get() instanceof TrapDoorBlock trapDoorBlock)) throw new IllegalArgumentException("Block must be a TrapDoorBlock");
            provider.trapdoorBlockWithRenderType(trapDoorBlock, modLoc("blocks/" + registerName), true, "cutout");
            return this;
        }

        public Generator trapdoorWithRenderType(String name, ResourceLocation texture, boolean orientable, String renderType) {
            if (!(block.get() instanceof TrapDoorBlock trapDoorBlock)) throw new IllegalArgumentException("Block must be a TrapDoorBlock");
            provider.trapdoorBlockWithRenderType(trapDoorBlock, name, texture, orientable, renderType);
            return this;
        }

        public Generator trapdoorWithRenderType(ResourceLocation texture, boolean orientable, ResourceLocation renderType) {
            if (!(block.get() instanceof TrapDoorBlock trapDoorBlock)) throw new IllegalArgumentException("Block must be a TrapDoorBlock");
            provider.trapdoorBlockWithRenderType(trapDoorBlock, texture, orientable, renderType);
            return this;
        }

        public Generator trapdoorWithRenderType(String name, ResourceLocation texture, boolean orientable, ResourceLocation renderType) {
            if (!(block.get() instanceof TrapDoorBlock trapDoorBlock)) throw new IllegalArgumentException("Block must be a TrapDoorBlock");
            provider.trapdoorBlockWithRenderType(trapDoorBlock, name, texture, orientable, renderType);
            return this;
        }

        public Generator trapdoor(ModelFile bottom, ModelFile top, ModelFile open, boolean orientable) {
            if (!(block.get() instanceof TrapDoorBlock trapDoorBlock)) throw new IllegalArgumentException("Block must be a TrapDoorBlock");
            provider.trapdoorBlock(trapDoorBlock, bottom, top, open, orientable);
            return this;
        }
    }

    @Override
    protected void registerStatesAndModels() {
        List<DumbBlocks.Blocks> blocks = Arrays.stream(DumbBlocks.Blocks.values()).toList();
        for (DumbBlocks.Blocks block : blocks) {
            UnaryOperator<Generator> stateBuilderOperator = block.getStateGeneratorOperator();
            Generator generator = new Generator(this, block.getRegisterName(), block.getRegistry().block());
            stateBuilderOperator.apply(generator);
        }
    }
}
