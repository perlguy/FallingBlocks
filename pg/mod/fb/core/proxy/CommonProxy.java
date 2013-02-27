package pg.mod.fb.core.proxy;

import pg.mod.fb.block.entity.FallingOilSand;
import pg.mod.fb.core.Constants;
import cpw.mods.fml.common.registry.EntityRegistry;

public class CommonProxy {

    // Client stuff
    public void initRenderersAndTextures() {
            // Nothing here as the server doesn't render graphics!
    }
    
    public void registerEntities(Object mod) {
    	EntityRegistry.registerModEntity(FallingOilSand.class, Constants.FALLING_OILSAND_NAME, 0, mod, 80, 5, true);
    }
    
}
