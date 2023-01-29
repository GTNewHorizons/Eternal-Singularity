package singulariteam.eternalsingularity.proxy;

import static fox.spiteful.avaritia.Config.craftingOnly;

import java.io.File;
import java.util.*;

import javax.annotation.Nonnull;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import singulariteam.eternalsingularity.EternalRecipeTweaker;
import singulariteam.eternalsingularity.EternalSingularityMod;
import singulariteam.eternalsingularity.item.CompoundSingularityItem;
import singulariteam.eternalsingularity.item.EternalSingularityItem;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import fox.spiteful.avaritia.crafting.ExtremeCraftingManager;
import fox.spiteful.avaritia.crafting.Grinder;

public class CommonProxy {

    private static ShapelessOreRecipe eternalSingularityRecipe = null;
    private static final Set<Class> classSet = new HashSet<>();
    protected CompoundSingularityItem compoundSingularityItem = null;
    private File configFile;

    public CommonProxy() {}

    public final void preInit(final File file) {
        final Configuration config = new Configuration(configFile = file);
        final String[] classNameList = config.getStringList(
                "classNameList",
                Configuration.CATEGORY_GENERAL,
                new String[] { "com.rcx.aobdsingularities.item.AOBDItemSingularity",
                        "fox.spiteful.avaritia.items.ItemSingularity", "wanion.thermsingul.ThermalSingularityItem",
                        "wealthyturtle.uiesingularities.UniversalSingularityItem" },
                "here is the absolute class name of the Item Classes that must be removed from Infinity Catalyst recipe and inserted into Eternal Singularity.");
        if (config.hasChanged()) config.save();
        for (final String className : classNameList) {
            try {
                classSet.add(Class.forName(className));
            } catch (ClassNotFoundException e) {
                EternalSingularityMod.logger.warn("Couldn't find " + className);
            }
        }
        GameRegistry.registerItem(EternalSingularityItem.instance, "eternal_singularity");
        if (Loader.isModLoaded("MineTweaker3")) EternalRecipeTweaker.init();
    }

    public void init() {}

    @Nonnull
    public static ShapelessOreRecipe getEternalSingularityRecipe() {
        return eternalSingularityRecipe;
    }

    @SuppressWarnings("unchecked")
    public void postInit() {
        if (classSet.isEmpty() || craftingOnly) return;
        final List<ItemStack> singularities = new ArrayList<>();
        for (final Iterator<Object> catalystRecipeIterator = Grinder.catalyst.getInput()
                .iterator(); catalystRecipeIterator.hasNext();) {
            final Object input = catalystRecipeIterator.next();
            if (!(input instanceof ItemStack)) continue;
            final Item item = ((ItemStack) input).getItem();
            if (item != null && classSet.contains(item.getClass())) {
                catalystRecipeIterator.remove();
                singularities.add(((ItemStack) input).copy());
            }
        }
        final Configuration config = new Configuration(configFile);
        final int singularityCount = singularities.size();
        final boolean aboveTheLimit = singularityCount > 81;
        final boolean useCompoundSingularities = config.getBoolean(
                "useCompoundSingularities",
                Configuration.CATEGORY_GENERAL,
                aboveTheLimit,
                "When useCompoundSingularities is Enabled, Basic Singularities will Need to be Crafted into Compound Singularities First.\n[If there are > 81 Basic Singularities, this Config Option will be Set to True Automatically]")
                || aboveTheLimit;
        final boolean easyMode = config.getBoolean(
                "easyMode",
                Configuration.CATEGORY_GENERAL,
                false,
                "If this Config Option is Enabled, for Every 9 Singularities Used in the Eternal Singularity Recipe, You will Receive an Additional Eternal Singularity for the Recipe Output.");
        if (config.hasChanged()) config.save();
        final int compoundMax = 16;
        (eternalSingularityRecipe = new ShapelessOreRecipe(
                new ItemStack(
                        EternalSingularityItem.instance,
                        easyMode ? MathHelper.clamp_int(compoundMax, 1, 64) : 1))).getInput().addAll(singularities);
        if (useCompoundSingularities) {
            GameRegistry.registerItem(
                    compoundSingularityItem = new CompoundSingularityItem(compoundMax),
                    "combined_singularity");
            final List<Object> eternalSingularityRecipeInputs = eternalSingularityRecipe.getInput();
            for (int i = 0; i < compoundMax; i++) {
                final ShapelessOreRecipe compoundRecipe = new ShapelessOreRecipe(
                        new ItemStack(compoundSingularityItem, 1, MathHelper.clamp_int(i, 0, 64)));
                for (int s = 0; s < 9; s++) {
                    final int pos = 9 * i + s;
                    if (pos > singularityCount - 1) break;
                    final Object input = eternalSingularityRecipeInputs.get(pos);
                    if (!(input instanceof ItemStack)) continue;
                    compoundRecipe.getInput().add(((ItemStack) input).copy());
                }
                if (compoundRecipe.getInput().size() > 0) GameRegistry.addRecipe(compoundRecipe);
            }
            eternalSingularityRecipeInputs.clear();
            for (int i = 0; i < compoundMax; i++)
                eternalSingularityRecipeInputs.add(new ItemStack(compoundSingularityItem, 1, i));
        }
        ExtremeCraftingManager.getInstance().getRecipeList().add(eternalSingularityRecipe);
        Grinder.catalyst.getInput().add(new ItemStack(EternalSingularityItem.instance));
    }
}
