package pg.mod.fb.block;

import net.minecraft.block.Block;
import pg.mod.fb.core.BlockIDs;
import pg.mod.fb.core.Constants;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class ModBlocks {

	public static Block oilSand;
	
	public static void init() {
		oilSand          = new BlockOilSand(BlockIDs.OILSAND, 18);
		
		GameRegistry.registerBlock(oilSand, Constants.OILSAND_NAME);
		LanguageRegistry.addName(oilSand, "Oil Sand");
	
	}

}
