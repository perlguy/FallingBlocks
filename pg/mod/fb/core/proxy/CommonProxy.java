package pg.mod.fb.core.proxy;

import pg.mod.fb.block.entity.FallingBlock;
import pg.mod.fb.core.Constants;
import cpw.mods.fml.common.registry.EntityRegistry;

public class CommonProxy {

    // Client stuff
    public void initRenderersAndTextures() {
            // Nothing here as the server doesn't render graphics!
    }
    
    public void registerEntities(Object mod) {
    	EntityRegistry.registerModEntity(FallingBlock.class, Constants.FALLING_BLOCK_NAME, 0, mod, 80, 5, true);
    }
    
}
