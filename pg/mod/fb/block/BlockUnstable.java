package pg.mod.fb.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.World;
import pg.mod.fb.block.entity.FallingBlock;
import pg.mod.fb.core.Constants;

public class BlockUnstable extends Block {
	
	public BlockUnstable(int id, int texture) {
		super(id, texture, Material.sand);
		setBlockName(Constants.UNSTABLE_BLOCK_NAME)
		.setStepSound(soundSandFootstep);
        this.setCreativeTab(CreativeTabs.tabBlock);
	}
	
	@Override
	public int getBlockTextureFromSideAndMetadata(int par1, int par2) {
		return this.blockIndexInTexture;
	}

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World par1World, int par2, int par3, int par4)
    {
        par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, this.tickRate());
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
        par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, this.tickRate());
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (!par1World.isRemote)
        {
            this.tryToFall(par1World, par2, par3, par4);
        }
    }

    /**
     * If there is space to fall below will start this block falling
     */
    private void tryToFall(World world, int x, int y, int z)
    {
        if (canFallBelow(world, x, y - 1, z) && y >= 0)
        {
            byte var8 = 32;

            if (world.checkChunksExist(x - var8, y - var8, z - var8, x + var8, y + var8, z + var8))
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


    /**
     * Called when the falling block entity for this block is created
     */
    protected void onStartFalling(FallingBlock entity) {}

    /**
     * How many world ticks before ticking
     */
    public int tickRate()
    {
        return 5;
    }

    /**
     * Checks to see if the sand can fall into the block below it
     */
    public static boolean canFallBelow(World world, int x, int y, int z)
    {
        int var4 = world.getBlockId(x, y, z);

        if (var4 == 0)
        {
            return true;
        }
        else if (var4 == Block.fire.blockID)
        {
            return true;
        }
        else
        {
            Material var5 = Block.blocksList[var4].blockMaterial;
            return (var5 == Material.water || var5 == Material.lava);
        }
    }

	public void onFinishFalling(World world, int x, int y, int z, int metadata) { }

}
