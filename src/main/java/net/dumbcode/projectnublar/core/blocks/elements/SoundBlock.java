package net.dumbcode.projectnublar.core.blocks.elements;

import net.dumbcode.projectnublar.core.blocks.DumbBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class SoundBlock extends DumbBlock {
    public SoundBlock() {
            super(Properties.ofFullCopy(Blocks.GOLD_BLOCK));
        }
    @Override
    public @NotNull InteractionResult use(@NotNull BlockState pState, @NotNull Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        pLevel.playSound(pPlayer, pPos, SoundEvents.NOTE_BLOCK_BASS.get(), SoundSource.BLOCKS, 1f, 1f);
        return InteractionResult.SUCCESS;
    }
}
