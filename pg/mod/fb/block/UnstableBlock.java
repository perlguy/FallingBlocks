package pg.mod.fb.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.World;
import pg.mod.fb.block.entity.FallingBlock;
import pg.mod.fb.core.Constants;

public class UnstableBlock extends Block {
	
	public UnstableBlock(int id, int texture) {
		super(id, texture, Material.sand);
		setBlockName(Constants.UNSTABLE_BLOCK_NAME)
		.setStepSound(soundSandFootstep);
        this.setCreativeTab(CreativeTabs.tabBlock);
	}
	
	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int metadata) {
		return this.blockIndexInTexture;
	}

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World world, int x, int y, int z)
    {
        world.scheduleBlockUpdate(x, y, z, this.blockID, this.tickRate());
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World world, int x, int y, int z, int neighbor)
    {
    	world.scheduleBlockUpdate(x, y, z, this.blockID, this.tickRate());
    }

    public void updateTick(World world, int x, int y, int z, Random rnd)
    {
        if (!world.isRemote)
        {
            this.tryToFall(world, x, y, z);
        }
    }

    private void tryToFall(World world, int x, int y, int z)
    {
        if (canFallBelow(world, x, y - 1, z) && y >= 0)
        {
            byte range = 32;

            if (world.checkChunksExist(x - range, y - range, z - range, x + range, y + range, z + range))
            {
                if (!world.isRemote)
                {
                	double off = 0.5D;
                	FallingBlock entity = new FallingBlock(world);
                	entity.setPosition((double)x+off, (double)y+off, (double)z+off);
                    this.onStartFalling(entity);
                    world.spawnEntityInWorld(entity);
                }
            }
            else
            {
                world.setBlockWithNotify(x, y, z, 0);

                while (canFallBelow(world, x, y - 1, z) && y > 0)
                {
                    --y;
                }

                if (y > 0)
                {
                    world.setBlockWithNotify(x, y, z, this.blockID);
                }
            }
        }
    }


    protected void onStartFalling(FallingBlock entity) {}

    public int tickRate()
    {
        return 5;
    }

    public static boolean canFallBelow(World world, int x, int y, int z)
    {
        int block_id = world.getBlockId(x, y, z);

        if (block_id == 0 || block_id == Block.fire.blockID)
        {
            return true;
        }
        
        Material material = Block.blocksList[block_id].blockMaterial;
        return (material == Material.water || material == Material.lava);
    }

	public void onFinishFalling(World world, int x, int y, int z, int metadata) { }

}
