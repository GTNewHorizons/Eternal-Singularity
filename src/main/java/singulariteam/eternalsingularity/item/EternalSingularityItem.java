package singulariteam.eternalsingularity.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fox.spiteful.avaritia.entity.EntityImmortalItem;
import fox.spiteful.avaritia.items.ItemResource;
import fox.spiteful.avaritia.items.LudicrousItems;
import fox.spiteful.avaritia.render.ICosmicRenderItem;
import fox.spiteful.avaritia.render.IHaloRenderItem;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import singulariteam.eternalsingularity.EternalSingularityMod;

public class EternalSingularityItem extends Item implements IHaloRenderItem, ICosmicRenderItem {
    public static final EternalSingularityItem instance = new EternalSingularityItem();

    private IIcon cosmicMask;
    private IIcon foregroundIcon;

    private EternalSingularityItem() {
        super();
        setCreativeTab(EternalSingularityMod.creativeTabs);
        setTextureName("eternalsingularity:eternal_singularity2");
    }

    public EnumRarity getRarity(ItemStack stack) {
        return LudicrousItems.cosmic;
    }

    @Override
    public String getUnlocalizedName(final ItemStack stack) {
        return "item.eternal.singularity";
    }

    @Override
    public boolean hasCustomEntity(ItemStack stack) {
        return true;
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
        this.cosmicMask = ir.registerIcon("eternalsingularity:eternal_singularity_mask");
        this.foregroundIcon = ir.registerIcon("eternalsingularity:eternal_singularity");
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass) {
        return pass == 1 ? foregroundIcon : super.getIcon(stack, pass);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @Override
    public Entity createEntity(World world, Entity location, ItemStack itemstack) {
        return new EntityImmortalItem(world, location, itemstack);
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
