package qu.fixedVillagerTrades.mixin;

import net.minecraft.item.ItemStack;
import net.minecraft.village.TradeOffer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import qu.fixedVillagerTrades.FixedVillagerTrades;

@Mixin(TradeOffer.class)
public class TradeOfferMixin {

    @Final
    @Shadow
    private ItemStack firstBuyItem;

    @ModifyArg(method = "getAdjustedFirstBuyItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;clamp(III)I"), index = 1)
    private int changeMinPrice(int value) {
        return Math.max(value, (int)(firstBuyItem.getCount() * (1.0 - FixedVillagerTrades.getMaxDiscount())));
    }
}
