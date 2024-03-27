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

/**
 * This class represents a {@link SoundBlock} which is a type of {@link DumbBlock}.
 * When interacted with, it plays a sound.
 */
public class SoundBlock extends DumbBlock {
    /**
     * Constructor for {@link SoundBlock}.
     * It copies the properties of {@link Blocks#GOLD_BLOCK}.
     */
    public SoundBlock() {
        super(Properties.ofFullCopy(Blocks.GOLD_BLOCK));
    }

    /**
     * This method is called when a player interacts with the block.
     * It plays a sound and returns a success interaction result.
     *
     * @param pState The current state of the block.
     * @param pLevel The level in which the block is located.
     * @param pPos The position of the block.
     * @param pPlayer The player who interacted with the block.
     * @param pHand The hand the player used to interact with the block.
     * @param pHit The result of the player's interaction with the block.
     * @return {@link InteractionResult#SUCCESS} after playing the sound.
     */
    @Override
    @SuppressWarnings("deprecation")
    public @NotNull InteractionResult use(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        pLevel.playSound(pPlayer, pPos, SoundEvents.NOTE_BLOCK_BASS.get(), SoundSource.BLOCKS, 1f, 1f);
        return InteractionResult.SUCCESS;
    }
}