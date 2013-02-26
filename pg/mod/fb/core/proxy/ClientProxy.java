package pg.mod.fb.core.proxy;

import pg.mod.fb.block.entity.FallingOilSand;
import pg.mod.fb.block.entity.RenderFallingOilSand;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {


	@Override
	public void initRenderersAndTextures() {

		RenderingRegistry.registerEntityRenderingHandler(FallingOilSand.class, new RenderFallingOilSand());
	}
	
}