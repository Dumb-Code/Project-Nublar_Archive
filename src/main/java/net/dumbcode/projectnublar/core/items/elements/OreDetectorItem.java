package net.dumbcode.projectnublar.core.items.elements;

import net.dumbcode.projectnublar.core.items.DumbItem;
import net.dumbcode.projectnublar.core.tags.DumbTags;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class OreDetectorItem extends DumbItem {
    private static final Supplier<Properties> DEFAULT_PROPERTIES = () -> new Item.Properties()
        .durability(100);
    public OreDetectorItem() {
        super(DEFAULT_PROPERTIES.get());
    }

    public OreDetectorItem(Properties properties) {
        super(properties);
    }

    public OreDetectorItem(@NotNull UnaryOperator<Properties> properties) {
        super(properties.apply(DEFAULT_PROPERTIES.get()));
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext pContext) {
        Player player = pContext.getPlayer();
        assert player != null;
        if (!pContext.getLevel().isClientSide()) {
            BlockPos posClicked = pContext.getClickedPos();
            boolean foundBlock = false;
            for (int i = 0; i <= posClicked.getY() + 64; i++) {
                BlockPos posBelow = posClicked.below(i);
                BlockState state = pContext.getLevel().getBlockState(posBelow);
                if (isOre(state)) {
                    player.sendSystemMessage(Component.literal("Found " + state.getBlock() + " at " + posBelow.getX() + ", " + posBelow.getY() + ", " + posBelow.getZ()));
                    foundBlock = true;
                    break;
                }
            }

            if (!foundBlock) {
                player.sendSystemMessage(Component.literal("No ore found!"));
            }
        }

        pContext.getItemInHand().hurtAndBreak(1, player, p -> p.broadcastBreakEvent(p.getUsedItemHand()));

        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("[RIGHT CLICK] to detect ores below the block you click!"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    private boolean isOre(@NotNull BlockState state) {
        return state.is(DumbTags.Blocks.OVERWORLD_ORES.getTagKey());
    }
}
