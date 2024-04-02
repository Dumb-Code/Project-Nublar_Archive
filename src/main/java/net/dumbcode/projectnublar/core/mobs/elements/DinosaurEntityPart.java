package net.dumbcode.projectnublar.core.mobs.elements;

import net.dumbcode.projectnublar.core.mobs.DumbEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.entity.PartEntity;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;

public abstract class DinosaurEntityPart extends PartEntity<DinosaurEntity> implements DumbEntity, GeoEntity {
    public final DinosaurEntity parentMob;
    public final String name;
    private final EntityDimensions size;
    protected AnimatableInstanceCache animationCache = GeckoLibUtil.createInstanceCache(this);
    public DinosaurEntityPart(DinosaurEntity parent, String name, float width, float height) {
        super(parent);
        this.size = EntityDimensions.scalable(width, height);
        this.name = name;
        this.parentMob = parent;
    }

    @Override
    protected void defineSynchedData() {}

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {}

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {}
    @Override
    public boolean isPickable() {
        return true;
    }

    @Nullable
    public ItemStack getPickResult() {
        return this.parentMob.getPickResult();
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        return !this.isInvulnerableTo(source) && this.parentMob.hurt(this, source, amount);
    }

    @Override
    public boolean is(Entity pEntity) {
        return this == pEntity || this.parentMob == pEntity;
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        throw new UnsupportedOperationException();
    }

    @Override
    public EntityDimensions getDimensions(Pose pPose) {
        return this.size;
    }

    @Override
    public boolean shouldBeSaved() {
        return false;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return animationCache;
    }
}
