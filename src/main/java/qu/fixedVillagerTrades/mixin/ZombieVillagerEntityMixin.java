package qu.fixedVillagerTrades.mixin;

import net.minecraft.entity.mob.ZombieVillagerEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import qu.fixedVillagerTrades.FixedVillagerTrades;
import qu.fixedVillagerTrades.OfferNbtAccessor;

@Mixin(ZombieVillagerEntity.class)
public class ZombieVillagerEntityMixin implements OfferNbtAccessor {
    @Unique
    private NbtCompound offersNbt = new NbtCompound();

    @Inject(method = "finishConversion", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/VillagerEntity;setExperience(I)V", shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void setOffersOnConversion(ServerWorld world, CallbackInfo ci, VillagerEntity villagerEntity) {
        if (FixedVillagerTrades.areTradesFixed()) {
            ((OfferNbtAccessor) villagerEntity).setOffersNbt(offersNbt);
        }
    }

    @Unique
    @Override
    public void setOffersNbt(NbtCompound nbt) {
        this.offersNbt = nbt;
    }

    @Unique
    @Override
    public NbtCompound getOffersNbt() {
        return this.offersNbt;
    }
}
