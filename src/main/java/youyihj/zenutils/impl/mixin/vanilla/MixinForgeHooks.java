package youyihj.zenutils.impl.mixin.vanilla;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.AnvilUpdateEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import youyihj.zenutils.impl.mixin.itf.IAnvilUpdateEventExtension;

/**
 * @author youyihj
 */
@Mixin(value = ForgeHooks.class, remap = false)
public abstract class MixinForgeHooks {
    @Inject(
            method = "onAnvilChange",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraftforge/event/AnvilUpdateEvent;<init>(Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;Ljava/lang/String;I)V",
                    // shift to after two opcodes (INVOKESPECIAL, ASTORE)
                    shift = At.Shift.BY,
                    by = 2
            )
    )
    private static void passPlayer(ContainerRepair container, ItemStack left, ItemStack right, IInventory outputSlot, String name, int baseCost, CallbackInfoReturnable<Boolean> cir, @Local AnvilUpdateEvent event) {
        ((IAnvilUpdateEventExtension) event).zu$setPlayer(((ContainerRepairAccessor) container).getPlayer());
    }
}
