package singulariteam.eternalsingularity.proxy;

import net.minecraftforge.client.MinecraftForgeClient;

import singulariteam.eternalsingularity.item.EternalSingularityItem;
import singulariteam.eternalsingularity.render.EternalItemRenderer;

public final class ClientProxy extends CommonProxy {

    @Override
    public void postInit() {
        super.postInit();
        EternalItemRenderer fancies = new EternalItemRenderer();
        MinecraftForgeClient.registerItemRenderer(EternalSingularityItem.instance, fancies);
        if (compoundSingularityItem != null)
            MinecraftForgeClient.registerItemRenderer(compoundSingularityItem, fancies);
    }
}
