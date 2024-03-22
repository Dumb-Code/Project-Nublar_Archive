package net.dumbcode.projectnublar.core.items;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class DumbItemStack {
    private final ItemStack itemStack;

    @Contract(value = "_ -> new", pure = true)
    public static @NotNull DumbItemStack of(@NotNull ItemStack itemStack) {
        return new DumbItemStack(itemStack);
    }

    @Contract("_ -> new")
    public static @NotNull DumbItemStack of(@NotNull ItemLike item) {
        return new DumbItemStack(item);
    }

    @Contract("_, _ -> new")
    public static @NotNull DumbItemStack of(@NotNull ItemLike item, int count) {
        return new DumbItemStack(item, count);
    }

    public DumbItemStack(@NotNull ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public DumbItemStack(@NotNull ItemLike item) {
        this(new ItemStack(item));
    }

    public DumbItemStack(@NotNull ItemLike item, int count) {
        this(new ItemStack(item, count));
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}
