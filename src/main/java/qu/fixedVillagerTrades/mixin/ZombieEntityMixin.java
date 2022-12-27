package qu.fixedVillagerTrades.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.mob.ZombieVillagerEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import qu.fixedVillagerTrades.FixedVillagerTrades;
import qu.fixedVillagerTrades.OfferNbtAccessor;

@Mixin(ZombieEntity.class)
public class ZombieEntityMixin {

    @Inject(method = "onKilledOther", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/ZombieVillagerEntity;setOfferData(Lnet/minecraft/nbt/NbtCompound;)V"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void fixedVillagerTrades$setOffersNbtOnConversion(ServerWorld world, LivingEntity other, CallbackInfoReturnable<Boolean> cir, boolean bl, VillagerEntity villagerEntity, ZombieVillagerEntity zombieVillagerEntity) {
        if (FixedVillagerTrades.areTradesFixed()) {
            ((OfferNbtAccessor) zombieVillagerEntity).setOffersNbt(((OfferNbtAccessor) villagerEntity).getOffersNbt());
        }
    }
}
