package pg.mod.fb.core.proxy;

import pg.mod.fb.FallingBlocks;
import pg.mod.fb.block.entity.FallingOilSand;
import pg.mod.fb.core.Constants;
import cpw.mods.fml.common.registry.EntityRegistry;

public class CommonProxy {

    // Client stuff
    public void initRenderersAndTextures() {
            // Nothing here as the server doesn't render graphics!
    }
    
    public void registerEntities() {
        FallingBlocks.FALLING_OILSAND = EntityRegistry.findGlobalUniqueEntityId();
    	EntityRegistry.registerGlobalEntityID(FallingOilSand.class, Constants.FALLING_OILSAND_NAME, FallingBlocks.FALLING_OILSAND);
    	EntityRegistry.registerModEntity(FallingOilSand.class, Constants.FALLING_OILSAND_NAME, 0, FallingBlocks.instance, 160, 5, true);
    }
    
}
