package singulariteam.eternalsingularity;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import org.apache.logging.log4j.Logger;
import singulariteam.eternalsingularity.item.EternalSingularityItem;
import singulariteam.eternalsingularity.proxy.CommonProxy;

import static singulariteam.eternalsingularity.Reference.*;

@Mod(
        modid = MOD_ID,
        name = MOD_NAME,
        version = MOD_VERSION,
        acceptedMinecraftVersions = MC_VERSION,
        dependencies =
                "required-after:Avaritia;required-after:wanionlib@[1.7.10-1.3,);after:thermsingul;after:universalsingularities;after:aobdsingularities")
public class EternalSingularityMod {
    @Mod.Instance(MOD_ID)
    public static EternalSingularityMod instance;

    public static Logger logger;

    @SidedProxy(serverSide = COMMON_PROXY, clientSide = CLIENT_PROXY)
    public static CommonProxy proxy;

    public static final CreativeTabs creativeTabs = new CreativeTabs(MOD_ID) {
        @SideOnly(Side.CLIENT)
        @Override
        public Item getTabIconItem() {
            return EternalSingularityItem.instance;
        }
    };

    @Mod.EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit(event.getSuggestedConfigurationFile());
    }

    @Mod.EventHandler
    public void init(final FMLInitializationEvent event) {
        proxy.init();
    }

    @Mod.EventHandler
    public void postInit(final FMLPostInitializationEvent event) {
        proxy.postInit();
    }
}
