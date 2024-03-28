package net.dumbcode.projectnublar.core.data;

import net.dumbcode.projectnublar.core.items.DumbItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.function.UnaryOperator;

import static net.dumbcode.projectnublar.ProjectNublar.MOD_ID;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MOD_ID, existingFileHelper);
    }

    public final class Generator {
        private final ModItemModelProvider provider;
        private final RegistryObject<Item> item;

        public Generator(ModItemModelProvider provider, RegistryObject<Item> item) {
            this.provider = provider;
            this.item = item;
        }

        private ResourceLocation location() {
            return ForgeRegistries.ITEMS.getKey(item.get());
        }

        public Generator basic() {
            provider.basicItem(item.get());
            return this;
        }

        public Generator basic(@Range(from=1, to=5) int layers) {
            ResourceLocation location = location();
            ItemModelBuilder builder = getBuilder(location.toString())
                .parent(new ModelFile.UncheckedModelFile("item/generated"));
            for (int i = 0; i < layers; i++) {
                builder.texture("layer" + i, "item/" + location.getPath() + "_layer" + i);
            }
            return this;
        }

        @Contract("_ -> this")
        public Generator builder(@NotNull UnaryOperator<ItemModelBuilder> builder) {
            ItemModelBuilder localBuilder = provider.getBuilder(location().toString());
            builder.apply(localBuilder);
            return this;
        }
    }

    @Override
    protected void registerModels() {
        for (DumbItems.Items item : DumbItems.Items.values()) {
            UnaryOperator<Generator> modelGeneratorOperator = item.getModelGeneratorOperator();
            modelGeneratorOperator.apply(new Generator(this, item.getRegistry().item()));
        }
    }
}
