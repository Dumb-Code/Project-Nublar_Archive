package net.dumbcode.projectnublar.core.items;

import net.minecraft.world.food.FoodProperties;

import java.util.function.Supplier;

public abstract class DumbFoodItem extends DumbItem {
    public static final Supplier<FoodProperties.Builder> DEFAULT_FOOD_PROPERTIES = FoodProperties.Builder::new;

    protected DumbFoodItem() {
        super(new Properties());
    }

    protected DumbFoodItem(Properties properties) {
        super(properties);
    }
}
