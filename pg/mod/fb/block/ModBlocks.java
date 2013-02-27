package pg.mod.fb.block;

import net.minecraft.block.Block;
import pg.mod.fb.core.BlockIDs;
import pg.mod.fb.core.Constants;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class ModBlocks {

	public static Block unstableBlock;
	
	public static void init() {
		unstableBlock          = new BlockUnstable(BlockIDs.UNSTABLE_BLOCK, 18);
		
		GameRegistry.registerBlock(unstableBlock, Constants.UNSTABLE_BLOCK_NAME);
		LanguageRegistry.addName(unstableBlock, "Oil Sand");
	
	}

}
