package net.dumbcode.projectnublar.core.entities;

import net.dumbcode.projectnublar.core.utils.MathsUtil;
import net.minecraft.core.BlockPos;

import java.util.ArrayList;
import java.util.List;

public interface AIEntity {
    /**
     * Ported from DumbLibrary utils/AIUtils.java .traverseXZ(int, int, int, int)
     * <p>
     * Find all block positions within a radius of a center point at the cost of accuracy.
     * Use {@link AIEntity#findBlockPositionsXZ(int, int, int, int)} for accurate results.
     * @param centerX
     * @param ecsY
     * @param centerZ
     * @param radius
     * @return
     */
    default List<BlockPos> fastFindBlockPositionsXZ(int centerX, int ecsY, int centerZ, int radius) {
        List<BlockPos> blockPos = new ArrayList<>();
        for (int x = centerX - radius; x <= centerX + radius; x++) {
            for (int z = centerZ - radius; z <= centerZ + radius; z++) {
                if (MathsUtil.fastPythagorean(x - centerX, z - centerZ) <= radius) {
                    blockPos.add(new BlockPos(x, ecsY, z));
                }
            }
        }
        return blockPos;
    }

    /**
     * Ported from DumbLibrary utils/AIUtils.java .traverseXZ(int, int, int, int)
     * <p>
     * Find all block positions within a radius of a center point.
     * If you do not need accurate results, use {@link AIEntity#fastFindBlockPositionsXZ(int, int, int, int)}.
     * @param centerX
     * @param ecsY
     * @param centerZ
     * @param radius
     * @return
     */
    default List<BlockPos> findBlockPositionsXZ(int centerX, int ecsY, int centerZ, int radius) {
        List<BlockPos> blockPos = new ArrayList<>();
        for (int x = centerX - radius; x <= centerX + radius; x++) {
            for (int z = centerZ - radius; z <= centerZ + radius; z++) {
                if (MathsUtil.pythagorean(x - centerX, z - centerZ) <= radius) {
                    blockPos.add(new BlockPos(x, ecsY, z));
                }
            }
        }
        return blockPos;
    }
}
