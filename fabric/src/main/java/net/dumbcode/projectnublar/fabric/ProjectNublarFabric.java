package net.dumbcode.projectnublar.fabric;

import net.dumbcode.projectnublar.ProjectNublar;
import net.fabricmc.api.ModInitializer;

public class ProjectNublarFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        ProjectNublar.init();
    }
}