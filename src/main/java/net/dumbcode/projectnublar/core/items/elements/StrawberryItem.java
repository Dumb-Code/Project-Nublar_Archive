package net.dumbcode.projectnublar.core.items.elements;

import net.dumbcode.projectnublar.core.items.DumbFoodItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Rarity;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class StrawberryItem extends DumbFoodItem {

    private static final Supplier<Properties> DEFAULT_PROPERTIES = () -> new Properties()
        .stacksTo(32)
        .rarity(Rarity.RARE);

    public StrawberryItem() {
        super(DEFAULT_PROPERTIES.get()
            .food(DEFAULT_FOOD_PROPERTIES
                .get()
                .nutrition(2)
                .saturationMod(0.3f)
                .fast()
                .alwaysEat()
                .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 30), 0.25f)
                .build()
            )
        );
    }

    public StrawberryItem(Properties properties) {
        super(properties);
    }

    public StrawberryItem(FoodProperties foodProperties) {
        super(DEFAULT_PROPERTIES.get().food(foodProperties));
    }

    public StrawberryItem(@NotNull Properties properties, FoodProperties foodProperties) {
        super(properties.food(foodProperties));
    }

    public StrawberryItem(@NotNull UnaryOperator<Properties> properties, FoodProperties foodProperties) {
        super(properties.apply(DEFAULT_PROPERTIES.get()).food(foodProperties));
    }

    public StrawberryItem(@NotNull UnaryOperator<Properties> properties, @NotNull Function<FoodProperties.Builder, FoodProperties> foodProperties) {
        super(properties.apply(DEFAULT_PROPERTIES.get()).food(foodProperties.apply(DEFAULT_FOOD_PROPERTIES.get())));
    }
}
