package net.dumbcode.projectnublar.core.creativetab;

import net.dumbcode.projectnublar.core.creativetab.elements.AllItemsCreativeTab;
import net.dumbcode.projectnublar.core.exceptions.UtilityClassException;
import net.dumbcode.projectnublar.core.registry.Registrar;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static net.dumbcode.projectnublar.ProjectNublar.MOD_ID;

public final class DumbCreativeTabs {

    public enum CreativeTabs {
        ALL_ITEMS(AllItemsCreativeTab::new);

        private final Supplier<DumbCreativeTab> creativeTabConstructor;
        private final RegistryObject<CreativeModeTab> registryCreativeTab = RegistryObject.create(new ResourceLocation(MOD_ID, getRegisterName()), Registrar.CREATIVE_MODE_TABS.getRegistryKey(), MOD_ID);

        public static @Unmodifiable @NotNull List<RegistryObject<CreativeModeTab>> registryCreativeTabs() {
            return Arrays.stream(CreativeTabs.values()).map(x -> x.registryCreativeTab).toList();
        }

        CreativeTabs(Supplier<DumbCreativeTab> creativeTabConstructor) {
            this.creativeTabConstructor = creativeTabConstructor;
        }

        public @NotNull CreativeModeTab get() {
            return registryCreativeTab.get();
        }

        public Supplier<DumbCreativeTab> getCreativeTabConstructor() {
            return this.creativeTabConstructor;
        }

        public @NotNull String getRegisterName() {
            return this.name().toLowerCase();
        }
    }

    private DumbCreativeTabs() {
        throw new UtilityClassException();
    }
}
