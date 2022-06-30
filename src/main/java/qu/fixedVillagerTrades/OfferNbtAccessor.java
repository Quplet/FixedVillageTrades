package qu.fixedVillagerTrades;

import net.minecraft.nbt.NbtCompound;

public interface OfferNbtAccessor {

    void setOffersNbt(NbtCompound nbt);

    NbtCompound getOffersNbt();
}
