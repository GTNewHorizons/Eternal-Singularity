package singulariteam.eternalsingularity.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fox.spiteful.avaritia.items.ItemResource;
import fox.spiteful.avaritia.items.LudicrousItems;
import fox.spiteful.avaritia.render.ICosmicRenderItem;
import fox.spiteful.avaritia.render.IHaloRenderItem;
import singulariteam.eternalsingularity.EternalSingularityMod;

public class CompoundSingularityItem extends Item implements IHaloRenderItem, ICosmicRenderItem {

    private final int max;

    @SideOnly(Side.CLIENT)
    private IIcon cosmicMask;

    @SideOnly(Side.CLIENT)
    private IIcon[] foregroundIcons, backgroundIcons;

    public CompoundSingularityItem(final int max) {
        super();
        setHasSubtypes(true);
        this.max = max;
        setCreativeTab(EternalSingularityMod.creativeTabs);
    }

    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.uncommon;
    }

    @Override
    public String getUnlocalizedName(final ItemStack stack) {
        return "item.combined.singularity." + MathHelper.clamp_int(getDamage(stack), 0, max);
    }

    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (int j = 0; j < max; j++) list.add(new ItemStack(item, 1, j));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getMaskTexture(ItemStack stack, EntityPlayer player) {
        return cosmicMask;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public float getMaskMultiplier(ItemStack stack, EntityPlayer player) {
        return 1.0f;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister ir) {
        super.registerIcons(ir);
        foregroundIcons = new IIcon[max];
        backgroundIcons = new IIcon[max];

        for (int x = 0; x < max; x++) {
            foregroundIcons[x] = ir.registerIcon("eternalsingularity:combined_singularity_" + x + "_overlay");
            backgroundIcons[x] = ir.registerIcon("eternalsingularity:combined_singularity_" + x);
        }
        this.cosmicMask = ir.registerIcon("eternalsingularity:combined_singularity_mask");
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass) {
        return pass == 1 ? this.foregroundIcons[stack.getItemDamage() % foregroundIcons.length]
                : this.backgroundIcons[stack.getItemDamage() % backgroundIcons.length];
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public boolean drawHalo(ItemStack paramItemStack) {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public IIcon getHaloTexture(ItemStack paramItemStack) {
        return ((ItemResource) LudicrousItems.resource).halo[0];
    }

    @SideOnly(Side.CLIENT)
    public int getHaloSize(ItemStack paramItemStack) {
        return 4;
    }

    @SideOnly(Side.CLIENT)
    public boolean drawPulseEffect(ItemStack paramItemStack) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public int getHaloColour(ItemStack paramItemStack) {
        return -16777216;
    }
}
