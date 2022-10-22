package singulariteam.eternalsingularity.proxy;

import net.minecraftforge.client.MinecraftForgeClient;
import singulariteam.eternalsingularity.item.EternalSingularityItem;
import singulariteam.eternalsingularity.render.EternalItemRenderer;
import singulariteam.eternalsingularity.render.ShaderHelper;

public final class ClientProxy extends CommonProxy {
    EternalItemRenderer fancies = new EternalItemRenderer();

    @Override
    public void init() {
        MinecraftForgeClient.registerItemRenderer(EternalSingularityItem.instance, fancies);
        ShaderHelper.initShaders();
    }

    @Override
    public void postInit() {
        super.postInit();
        if (compoundSingularityItem != null)
            MinecraftForgeClient.registerItemRenderer(compoundSingularityItem, fancies);
    }
}
