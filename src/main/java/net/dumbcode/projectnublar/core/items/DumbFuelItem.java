package net.dumbcode.projectnublar.core.items;

import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public abstract class DumbFuelItem extends DumbItem {

    public static class FuelProperties extends Properties {
        int burnTime = 0;

        public FuelProperties burnTime(int burnTime) {
            this.burnTime = burnTime;
            return this;
        }

        @Override
        public @NotNull FuelProperties stacksTo(int maxStackSize) {
            return (FuelProperties) super.stacksTo(maxStackSize);
        }

        @Override
        public @NotNull FuelProperties food(FoodProperties food) {
            return (FuelProperties) super.food(food);
        }

        @Override
        public @NotNull FuelProperties defaultDurability(int maxDamage) {
            return (FuelProperties) super.defaultDurability(maxDamage);
        }

        @Override
        public @NotNull FuelProperties durability(int maxDamage) {
            return (FuelProperties) super.durability(maxDamage);
        }

        @Override
        public @NotNull FuelProperties craftRemainder(Item craftingRemainingItem) {
            return (FuelProperties) super.craftRemainder(craftingRemainingItem);
        }

        @Override
        public @NotNull FuelProperties rarity(Rarity rarity) {
            return (FuelProperties) super.rarity(rarity);
        }

        @Override
        public @NotNull FuelProperties fireResistant() {
            return (FuelProperties) super.fireResistant();
        }

        @Override
        public @NotNull FuelProperties setNoRepair() {
            return (FuelProperties) super.setNoRepair();
        }

        @Override
        public @NotNull FuelProperties requiredFeatures(FeatureFlag @NotNull ... requiredFeatures) {
            return (FuelProperties) super.requiredFeatures(requiredFeatures);
        }
    }

    private static final Supplier<FuelProperties> DEFAULT_PROPERTIES = () -> new FuelProperties()
        .stacksTo(64);

    private final FuelProperties properties;

    protected DumbFuelItem(FuelProperties properties) {
        super(properties);
        this.properties = properties;
    }

    protected DumbFuelItem() {
        this(DEFAULT_PROPERTIES.get());
    }

    protected DumbFuelItem(@NotNull UnaryOperator<FuelProperties> properties) {
        this(properties.apply(DEFAULT_PROPERTIES.get()));
    }

    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return properties.burnTime;
    }
}
