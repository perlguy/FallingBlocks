package pg.mod.fb.core.proxy;

import pg.mod.fb.block.entity.FallingBlock;
import pg.mod.fb.block.entity.RenderFallingBlock;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {

	@Override
	public void initRenderersAndTextures() {
		RenderingRegistry.registerEntityRenderingHandler(FallingBlock.class, new RenderFallingBlock());
	}
	
}