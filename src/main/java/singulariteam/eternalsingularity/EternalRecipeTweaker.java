package singulariteam.eternalsingularity;

import java.util.Iterator;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import singulariteam.eternalsingularity.proxy.CommonProxy;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import wanion.lib.common.MineTweakerHelper;

@ZenClass("mods.eternalsingularity")
public final class EternalRecipeTweaker {

    private EternalRecipeTweaker() {}

    public static void init() {
        MineTweakerAPI.registerClass(EternalRecipeTweaker.class);
    }

    @ZenMethod
    public static void add(@Nonnull final IItemStack input) {
        final ItemStack itemStack = MineTweakerHelper.toStack(input);
        if (itemStack != null && itemStack.getItem() != null) MineTweakerAPI.apply(new Add(itemStack));
    }

    @ZenMethod
    public static void remove(final IItemStack input) {
        final ItemStack itemStack = MineTweakerHelper.toStack(input);
        if (itemStack != null && itemStack.getItem() != null) MineTweakerAPI.apply(new Remove(itemStack));
    }

    private static class Add implements IUndoableAction {

        private final ItemStack itemStack;

        public Add(@Nonnull final ItemStack itemStack) {
            this.itemStack = itemStack;
        }

        @Override
        public void apply() {
            CommonProxy.getEternalSingularityRecipe().getInput().add(itemStack);
        }

        @Override
        public boolean canUndo() {
            return true;
        }

        @Override
        public void undo() {
            CommonProxy.getEternalSingularityRecipe().getInput().remove(itemStack);
        }

        @Override
        public String describe() {
            return "Adding " + itemStack.getDisplayName() + " into Eternal Singularity Recipe.";
        }

        @Override
        public String describeUndo() {
            return "Removing " + itemStack.getDisplayName() + " from Eternal Singularity Recipe.";
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }
    }

    private static class Remove implements IUndoableAction {

        private final ItemStack itemStack;

        private Remove(@Nonnull final ItemStack itemStackToRemove) {
            ItemStack itemStack = null;
            for (final Iterator<Object> eternalSingularityRecipeIterator = CommonProxy.getEternalSingularityRecipe()
                    .getInput().iterator(); eternalSingularityRecipeIterator.hasNext() && itemStack == null;) {
                final Object input = eternalSingularityRecipeIterator.next();
                if (input instanceof ItemStack && ((ItemStack) input).isItemEqual(itemStackToRemove))
                    itemStack = (ItemStack) input;
            }
            this.itemStack = itemStack;
        }

        @Override
        public void apply() {
            CommonProxy.getEternalSingularityRecipe().getInput().remove(itemStack);
        }

        @Override
        public boolean canUndo() {
            return itemStack != null;
        }

        @Override
        public void undo() {
            CommonProxy.getEternalSingularityRecipe().getInput().add(itemStack);
        }

        @Override
        public String describe() {
            return "Removing " + itemStack.getDisplayName() + " from Eternal Singularity Recipe.";
        }

        @Override
        public String describeUndo() {
            return "Adding " + itemStack.getDisplayName() + " into Eternal Singularity Recipe.";
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }
    }
}
