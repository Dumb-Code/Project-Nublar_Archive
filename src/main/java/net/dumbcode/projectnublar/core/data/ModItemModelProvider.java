package net.dumbcode.projectnublar.core.data;

import net.dumbcode.projectnublar.core.items.DumbItems;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import static net.dumbcode.projectnublar.ProjectNublar.MOD_ID;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        for (DumbItems.Items item : DumbItems.Items.values()) {
            basicItem(item.getRegistry().item().get());
        }
    }
}
