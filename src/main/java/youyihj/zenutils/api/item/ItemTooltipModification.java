package youyihj.zenutils.api.item;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.lwjgl.input.Keyboard;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;
import youyihj.zenutils.api.reload.ScriptReloadEvent;
import youyihj.zenutils.api.util.StringList;
import youyihj.zenutils.impl.util.TotallyImmutableItemStack;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author youyihj
 */
@ZenRegister
@ZenExpansion("crafttweaker.item.IIngredient")
@Mod.EventBusSubscriber
public class ItemTooltipModification {
    private static final Map<IIngredient, ITooltipModifier> MODIFIERS = new LinkedHashMap<>();

    @ZenMethod
    public static void modifyTooltip(IIngredient ingredient, ITooltipModifier modifier) {
        MODIFIERS.put(ingredient, modifier);
    }

    @SubscribeEvent
    public static void onReloadPre(ScriptReloadEvent.Pre event) {
        MODIFIERS.clear();
    }

    @Mod.EventBusSubscriber(Side.CLIENT)
    public static class TooltipEventHandler {
        @SubscribeEvent(priority = EventPriority.LOWEST)
        public static void onItemTooltip(ItemTooltipEvent event) {
            IItemStack item = null;
            StringList tooltip = null;
            boolean pressed = false;
            for (Map.Entry<IIngredient, ITooltipModifier> entry : MODIFIERS.entrySet()) {
                if (!event.getItemStack().isEmpty() && entry.getKey().matches(CraftTweakerMC.getIItemStackForMatching(event.getItemStack()))) {
                    if (tooltip == null) {
                        tooltip = StringList.create(event.getToolTip());
                        item = new TotallyImmutableItemStack(event.getItemStack());
                        pressed = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
                    }
                    entry.getValue().apply(item, tooltip, pressed, event.getFlags().isAdvanced());
                }
            }
            if (tooltip != null) {
                event.getToolTip().clear();
                tooltip.forEach(event.getToolTip()::add);
            }
        }
    }

}
