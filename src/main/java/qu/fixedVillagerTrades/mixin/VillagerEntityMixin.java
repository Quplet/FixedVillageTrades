package qu.fixedVillagerTrades.mixin;

import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.MathHelper;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.VillagerProfession;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import qu.fixedVillagerTrades.FixedVillagerTrades;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin extends MerchantEntityMixin {

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void fixedVillagerTrades$writeOffersNbtToNbt(NbtCompound nbt, CallbackInfo ci) {
        if (FixedVillagerTrades.areTradesFixed()) {
            nbt.copyFrom(this.offersNbt);
        }
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void fixedVillagerTrades$readOffersNbtFromNbt(NbtCompound nbt, CallbackInfo ci) {
        if (FixedVillagerTrades.areTradesFixed()) {
            for (VillagerProfession profession1 : Registries.VILLAGER_PROFESSION) {
                String key = profession1.id() + "Offers";
                if (nbt.contains(key)) {
                    this.offersNbt.put(key, nbt.get(key));
                }
            }
        }
    }

    @Inject(method = "prepareOffersFor", at = @At(value = "TAIL"))
    private void fixedVillagerTrades$adjustForMaxDiscount(PlayerEntity player, CallbackInfo ci) {
        for (TradeOffer offer : this.getOffers()) {
            int minPrice = Math.max(1, (int)(offer.getOriginalFirstBuyItem().getCount() * (1.0 - FixedVillagerTrades.getMaxDiscount())));
            if (offer.getDisplayedFirstBuyItem().getCount() < minPrice) {
                // basically reverses the price calculation to result in it being the min price
                offer.setSpecialPrice(minPrice - offer.getOriginalFirstBuyItem().getCount() - Math.max(0, MathHelper.floor((float)(offer.getOriginalFirstBuyItem().getCount() * offer.getDemandBonus()) * offer.getPriceMultiplier())));
            }
        }
    }
}
