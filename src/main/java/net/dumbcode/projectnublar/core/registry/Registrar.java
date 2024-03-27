package net.dumbcode.projectnublar.core.registry;

import net.dumbcode.projectnublar.ProjectNublar;
import net.dumbcode.projectnublar.core.blocks.IDumbBlock;
import net.dumbcode.projectnublar.core.blocks.DumbBlocks;
import net.dumbcode.projectnublar.core.blocks.entity.DumbBlockEntities;
import net.dumbcode.projectnublar.core.blocks.entity.DumbBlockEntity;
import net.dumbcode.projectnublar.core.creativetab.DumbCreativeTab;
import net.dumbcode.projectnublar.core.creativetab.DumbCreativeTabs;
import net.dumbcode.projectnublar.core.mobs.DumbMobs;
import net.dumbcode.projectnublar.core.exceptions.UtilityClassException;
import net.dumbcode.projectnublar.core.items.DumbItem;
import net.dumbcode.projectnublar.core.items.DumbItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static net.dumbcode.projectnublar.ProjectNublar.MOD_ID;

@Mod.EventBusSubscriber(modid = ProjectNublar.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Registrar {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);

    // the difference between the two is that FLUID registry defines the behaviour of a fluid (projectnublar:examplefluid)
    // FLUIDS registry defines how projectnublar:examplefluid ticks, animates, creates a bucket etc.
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, MOD_ID);

    // FLUID_TYPES defines minecraft constraints of the projectnublar:examplefluid. More like fluid's config. It defines properties like
    // viscosity, can fluid drown, can it be passed on a boat, extinguish fire etc.
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.FLUID_TYPES, MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MOD_ID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

    public static void register(@NotNull IEventBus bus) {
        BLOCKS.register(bus);
        ITEMS.register(bus);
        CREATIVE_MODE_TABS.register(bus);
        ENTITY_TYPES.register(bus);
        BLOCK_ENTITY_TYPES.register(bus);
        FLUID_TYPES.register(bus);
        FLUIDS.register(bus);
    }

    @SubscribeEvent
    public static void register(@NotNull RegisterEvent event) {
        event.register(Registrar.BLOCKS.getRegistryKey(), helper -> {
            for (DumbBlocks.Blocks block : DumbBlocks.Blocks.values()) {
                Supplier<IDumbBlock> blockConstructor = block.getBlockConstructor();
                Block instance = (Block) blockConstructor.get();
                helper.register(new ResourceLocation(ProjectNublar.MOD_ID, block.getRegisterName()), instance);
            }
        });
        event.register(Registrar.ITEMS.getRegistryKey(), helper -> {
            for (DumbBlocks.Blocks block : DumbBlocks.Blocks.values()) {
                Block instance = block.getRegistry().block().get();
                IDumbBlock dumbBlock = (IDumbBlock) instance;
                Item item = dumbBlock.createItem();
                helper.register(new ResourceLocation(ProjectNublar.MOD_ID, block.getRegisterName()), item);
            }
            for (DumbItems.Items item : DumbItems.Items.values()) {
                Supplier<DumbItem> itemClass = item.getItemConstructor();
                Item instance = itemClass.get();
                helper.register(new ResourceLocation(ProjectNublar.MOD_ID, item.getRegisterName()), instance);
            }
        });
        event.register(Registrar.ENTITY_TYPES.getRegistryKey(), helper -> {
            for(DumbMobs.Mobs entity : DumbMobs.Mobs.values()) {
                helper.register(ProjectNublar.resourceLocation(entity.name().toLowerCase()), entity.getNativeType());
            }
        });
        event.register(Registrar.BLOCK_ENTITY_TYPES.getRegistryKey(), helper -> {
            for (DumbBlockEntities.Entities entity : DumbBlockEntities.Entities.values()) {
                List<Block> validBlocks = new ArrayList<>();
                for (DumbBlocks.Blocks block : DumbBlocks.Blocks.values()) {
                    DumbBlockEntities.Entities associatedEntity = block.getMetadata().associatedEntity();
                    if (associatedEntity == null) continue;
                    if (!associatedEntity.equals(entity)) continue;
                    validBlocks.add(block.getRegistry().block().get());
                }
                BlockEntityType.Builder<DumbBlockEntity> builder = BlockEntityType.Builder.of(entity.getBlockEntityConstructor(), validBlocks.toArray(new Block[0]));
                BlockEntityType<DumbBlockEntity> build = builder.build(null);
                helper.register(ProjectNublar.resourceLocation(entity.getRegisterName()), build);
            }
        });
        event.register(Registrar.CREATIVE_MODE_TABS.getRegistryKey(), helper -> {
                for (DumbCreativeTabs.CreativeTabs creativeTab : DumbCreativeTabs.CreativeTabs.values()) {
                    Supplier<DumbCreativeTab> creativeTabConstructor = creativeTab.getCreativeTabConstructor();
                    DumbCreativeTab instance = creativeTabConstructor.get();
                    CreativeModeTab creativeModeTab = instance.get();
                    helper.register(new ResourceLocation(ProjectNublar.MOD_ID, creativeTab.getRegisterName()), creativeModeTab);
                }
            });
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        for (DumbBlockEntities.Entities blockEntity : DumbBlockEntities.Entities.values()) {
            event.registerBlockEntityRenderer(blockEntity.getRegistry().blockEntityType().get(), blockEntity.getRenderer());
        }
    }

    private Registrar() {
        throw new UtilityClassException();
    }
}
