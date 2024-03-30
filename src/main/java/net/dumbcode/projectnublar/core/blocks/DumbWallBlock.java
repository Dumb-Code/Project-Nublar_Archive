package net.dumbcode.projectnublar.core.blocks;
import net.minecraft.world.level.block.WallBlock;

public abstract class DumbWallBlock extends WallBlock implements IDumbBlock {
    protected DumbWallBlock(Properties properties) {
        super(properties);
    }
}
