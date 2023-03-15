package qu.fixedVillagerTrades.mixin;

import net.minecraft.entity.ai.brain.task.LoseJobOnSiteLossTask;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import qu.fixedVillagerTrades.FixedVillagerTrades;

@Mixin(LoseJobOnSiteLossTask.class)
public class LoseJobOnSiteLossTaskMixin {

    @Inject(method = "method_47038", at = @At(value = "INVOKE", target = "Lnet/minecraft/village/VillagerData;withProfession(Lnet/minecraft/village/VillagerProfession;)Lnet/minecraft/village/VillagerData;"), cancellable = true)
    private static void fixedVillagerTrades$lockProfession(ServerWorld world, VillagerEntity entity, long time, CallbackInfoReturnable<Boolean> cir) {
        if (FixedVillagerTrades.areProfessionsLocked()) cir.setReturnValue(false);
    }
}
