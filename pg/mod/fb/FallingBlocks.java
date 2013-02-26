package pg.mod.fb;

import pg.mod.fb.block.ModBlocks;
import pg.mod.fb.core.Info;
import pg.mod.fb.core.proxy.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid=Info.MOD_ID, name=Info.MOD_NAME, version=Info.VERSION)
@NetworkMod(clientSideRequired=true, serverSideRequired=false)

public class FallingBlocks {

	@Instance(Info.MOD_ID)
	public static FallingBlocks instance;
    
    @SidedProxy(clientSide = Info.CLIENT_PROXY_CLASS, serverSide = Info.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;

	public static int FALLING_OILSAND;

    @PreInit
    public void preInit(FMLPreInitializationEvent event) {
    }
    
    @Init
    public void init(FMLInitializationEvent event) {
    	
        ModBlocks.init();
        
        proxy.registerEntities();
        
        proxy.initRenderersAndTextures();
    	  	
    }
    
    @PostInit
    public void postInit(FMLPostInitializationEvent event) {
    
    }

}