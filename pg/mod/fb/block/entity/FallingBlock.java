package pg.mod.fb.block.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import pg.mod.fb.block.UnstableBlock;
import pg.mod.fb.block.ModBlocks;
import pg.mod.fb.core.BlockIDs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class FallingBlock extends Entity {

	public int blockID;
	public int metadata;
	public int fallTime;
	public double fallRate;
	public boolean shouldDropItem;
	
	public FallingBlock(World world) 
	{
		super(world);
	}
	
	public FallingBlock(World world, float x, float y, float z) {
		super(world);
		setPosition(x, x, z);
	}
	
	protected void entityInit() {
		
		this.blockID = BlockIDs.UNSTABLE_BLOCK;
		this.metadata = 0;
		this.fallTime = 0;
		this.fallRate = 0.04D;
		this.shouldDropItem = true;
		
		this.setSize(1.0F, 1.0F);
		this.yOffset = 0.5F; 
        this.boundingBox.setBounds(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
	}
	
    /**
     * Sets the location and Yaw/Pitch of an entity in the world
     */
	@Override
    public void setLocationAndAngles(double x, double y, double z, float yaw, float pitch)
    {
        this.lastTickPosX = this.prevPosX = this.posX = x;
        this.lastTickPosY = this.prevPosY = this.posY = y;
        this.lastTickPosZ = this.prevPosZ = this.posZ = z;
        this.rotationYaw = yaw;
        this.rotationPitch = pitch;
        this.setPosition(this.posX, this.posY, this.posZ);
    }

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate()
	{
		if (this.blockID == 0)
		{
			this.setDead();
		}
		else
		{
			this.prevPosX = this.posX;
			this.prevPosY = this.posY;
			this.prevPosZ = this.posZ;
			++this.fallTime;
			this.motionY -= this.fallRate;
			this.moveEntity(this.motionX, this.motionY, this.motionZ);
			this.motionX *= 0.98D;
			this.motionY *= 0.98D;
			this.motionZ *= 0.98D;

			if (!this.worldObj.isRemote)
			{
				int xi = MathHelper.floor_double(this.posX);
				int yi = MathHelper.floor_double(this.posY);
				int zi = MathHelper.floor_double(this.posZ);

				if (this.fallTime == 1)
				{
					if ( this.fallTime != 1 || this.worldObj.getBlockId(xi,yi,zi) != this.blockID)
					{
						this.setDead();
						return;
					}
					this.worldObj.setBlockWithNotify(xi, yi, zi, 0);
				}

				if (this.onGround)
				{
					this.motionX *= 0.7D;
					this.motionZ *= 0.7D;
					this.motionY *= -0.5D;

					if (this.worldObj.getBlockId(xi, yi, zi) != Block.pistonMoving.blockID)
					{
						this.setDead();
						if (this.worldObj.canPlaceEntityOnSide(this.blockID, xi, yi, zi, true, 1, (Entity)null) && 
								!UnstableBlock.canFallBelow(this.worldObj, xi, yi - 1, zi) && 
								this.worldObj.setBlockAndMetadataWithNotify(xi, yi, zi, this.blockID, this.metadata))
						{
							((UnstableBlock)ModBlocks.unstableBlock).onFinishFalling(this.worldObj, xi, yi, zi, metadata);
						}
						else if (this.shouldDropItem)
						{
							this.entityDropItem(new ItemStack(this.blockID, 1, ModBlocks.unstableBlock.damageDropped(this.metadata)), 0.0F);
						}
					}

				}
				else if (this.fallTime > 100 && !this.worldObj.isRemote && (yi < 1 || yi > 256) || this.fallTime > 600)
				{
					this.setDead();
					if (this.shouldDropItem)
					{
						this.entityDropItem(new ItemStack(this.blockID, 1, ModBlocks.unstableBlock.damageDropped(this.metadata)), 0.0F);
					}
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public World getWorld()
	{
		return this.worldObj;
	}

	protected void writeEntityToNBT(NBTTagCompound var1) {}

	protected void readEntityFromNBT(NBTTagCompound var1) {}



}

