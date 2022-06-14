package qu.fixedVillagerTrades.mixin;

import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.registry.Registry;
import net.minecraft.village.TradeOfferList;
import net.minecraft.village.VillagerProfession;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MerchantEntity.class)
public abstract class MerchantEntityMixin {

    @Shadow @Nullable protected TradeOfferList offers;
    @Unique
    protected NbtCompound offersNbt = new NbtCompound();

    @Inject(method = "getOffers", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/MerchantEntity;fillRecipes()V", shift = At.Shift.AFTER))
    private void rememberTrades(CallbackInfoReturnable<TradeOfferList> cir) {
        if ((MerchantEntity)(Object)this instanceof VillagerEntity villager) {
            VillagerProfession profession = villager.getVillagerData().getProfession();
            if (!profession.equals(VillagerProfession.NONE) && !profession.equals(VillagerProfession.NITWIT)) {
                for (VillagerProfession profession1 : Registry.VILLAGER_PROFESSION) {
                    if (profession.equals(profession1)) {
                        String key = profession1.id() + "Offers";
                        if (offersNbt.contains(key)) {
                            offers = new TradeOfferList(offersNbt.getCompound(key));
                        } else {
                            offersNbt.put(key, offers.toNbt());
                        }
                    }
                }
            }
        }
    }
}
