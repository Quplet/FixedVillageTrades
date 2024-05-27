package qu.fixedVillagerTrades.mixin;

import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.registry.Registries;
import net.minecraft.util.Util;
import net.minecraft.village.TradeOfferList;
import net.minecraft.village.VillagerProfession;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import qu.fixedVillagerTrades.FixedVillagerTrades;
import qu.fixedVillagerTrades.OfferNbtAccessor;

@Mixin(MerchantEntity.class)
public abstract class MerchantEntityMixin implements OfferNbtAccessor {

    @Shadow @Final
    private static Logger LOGGER;

    @Shadow @Nullable protected TradeOfferList offers;

    @Shadow public abstract TradeOfferList getOffers();

    @Unique
    protected NbtCompound offersNbt = new NbtCompound();

    @Inject(method = "getOffers", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/MerchantEntity;fillRecipes()V", shift = At.Shift.AFTER))
    private void fixedVillagerTrades$rememberTrades(CallbackInfoReturnable<TradeOfferList> cir) {
        if (FixedVillagerTrades.areTradesFixed() && (MerchantEntity)(Object)this instanceof VillagerEntity villager) {
            VillagerProfession profession1 = villager.getVillagerData().getProfession();
            if (profession1 != VillagerProfession.NONE && profession1 != VillagerProfession.NITWIT) {
                for (VillagerProfession profession2 : Registries.VILLAGER_PROFESSION) {
                    if (profession1 == profession2) {
                        String key = profession2.id() + "Offers";
                        TradeOfferList.CODEC.parse(((MerchantEntity)(Object)this).getRegistryManager().getOps(NbtOps.INSTANCE), offersNbt.get(key)).resultOrPartial(Util.addPrefix("Failed to load offers: ", LOGGER::warn)).ifPresentOrElse(
                                offers -> this.offers = offers,
                                () -> offersNbt.put(key, TradeOfferList.CODEC.encodeStart(((MerchantEntity)(Object)this).getRegistryManager().getOps(NbtOps.INSTANCE), offers).getOrThrow())
                        );
                        break;
                    }
                }
            }
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
