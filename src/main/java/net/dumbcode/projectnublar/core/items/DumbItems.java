package net.dumbcode.projectnublar.core.items;

import net.dumbcode.projectnublar.core.items.elements.TestItem;
import net.dumbcode.projectnublar.core.registry.Registrar;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static net.dumbcode.projectnublar.ProjectNublar.MOD_ID;

public class DumbItems {
    public enum Items {
        TEST_ITEM(TestItem.class);

        private final Class<? extends DumbItem> itemClass;
        private final RegistryObject<Item> registryItem = RegistryObject.create(new ResourceLocation(MOD_ID, getRegisterName()), Registrar.ITEMS.getRegistryKey(), MOD_ID);

        public static @Unmodifiable @NotNull List<Class<? extends DumbItem>> classes() {
            return Arrays.stream(Items.values()).map(Items::getItemClass).collect(Collectors.toUnmodifiableList());
        }

        public static @Unmodifiable @NotNull List<RegistryObject<Item>> registryItems() {
            return Arrays.stream(Items.values()).map(x -> x.registryItem).toList();
        }

        Items(Class<? extends DumbItem> itemClass) {
            this.itemClass = itemClass;
        }

        public Item get() {
            return registryItem.get();
        }

        public Class<? extends DumbItem> getItemClass() {
            return this.itemClass;
        }

        public String getRegisterName() {
            return this.name().toLowerCase();
        }
    }
}
