package pg.mod.fb.block.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import pg.mod.fb.block.BlockOilSand;
import pg.mod.fb.block.ModBlocks;
import pg.mod.fb.core.BlockIDs;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class FallingOilSand extends Entity {

	public int blockID;
	public int metadata;
	public int fallTime;
	public double fallRate;
	public boolean shouldDropItem;
	
	private String side;

	public FallingOilSand(World world) 
	{
		super(world);
		side = this.worldObj.isRemote ? "CLIENT" : "SERVER";
		FMLLog.info("[" + side + "] "+this.getClass().getName()+"(world)::start");
		this.fallTime = 0;
		this.fallRate = 0.03999999910593033D;
		this.shouldDropItem = true;
		this.blockID = BlockIDs.OILSAND;
		this.metadata = 0;
		this.setSize(0.98F, 0.98F);
		this.yOffset = this.height / 2.0F; 
		this.ySize = 0;
		this.motionX = 0.0D;
		this.motionY = 0.0D;
		this.motionZ = 0.0D;
		FMLLog.info("[" + side + "] "+this.getClass().getName()+"(world)::exit");
	}
	
	public FallingOilSand(World world, double x, double y, double z, int blockID) {
		this(world,x,y,z,blockID,0);
		FMLLog.info("[" + side + "] "+this.getClass().getName()+"(world,x,y,z,id)::(start/end)");
	}

	public FallingOilSand(World world, double x, double y, double z, int blockID, int metadata) 
	{
		this(world);
		FMLLog.info("[" + side + "] "+this.getClass().getName()+"(world,x,y,z,id,md)::start");
		this.blockID = blockID;
		this.metadata = metadata;
		this.setPosition(x, y, z);
		FMLLog.info("[" + side + "] "+this.getClass().getName()+"(world,x,y,z,id,md)::end");
	}
	
	protected void entityInit() {

	}
	
    /**
     * Sets the x,y,z of the entity from the given parameters. Also seems to set up a bounding box.
     */
    public void setPosition(double x, double y, double z)
    {
    	// MYSTERY... WHY IS THIS NEEDED ????  GLITCHES WITHOUT IT...
    	if (this.worldObj.isRemote) { y-=this.yOffset; }
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        float hw = this.width / 2.0F;
        float h = this.height;
        this.boundingBox.setBounds(x - (double)hw, y - (double)this.yOffset + (double)this.ySize, z - (double)hw, x + (double)hw, y - (double)this.yOffset + (double)this.ySize + (double)h, z + (double)hw);
    }

	/**
	 * Returns true if other Entities should be prevented from moving through this Entity.
	 */
	public boolean canBeCollidedWith()
	{
		return !this.isDead;
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	public void onUpdate()
	{
		FMLLog.info("[" + side + "] onUpdate::entry ["+this.posY+"]");

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
			this.motionX *= 0.9800000190734863D;
			this.motionY *= 0.9800000190734863D;
			this.motionZ *= 0.9800000190734863D;

			FMLLog.info("[" + side + "] onUpdate::postMove ["+this.posY+"]");

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
					this.motionX *= 0.699999988079071D;
					this.motionZ *= 0.699999988079071D;
					this.motionY *= -0.5D;

					if (this.worldObj.getBlockId(xi, yi, zi) != Block.pistonMoving.blockID)
					{
						this.setDead();
						if (this.worldObj.canPlaceEntityOnSide(this.blockID, xi, yi, zi, true, 1, (Entity)null) && 
								!BlockOilSand.canFallBelow(this.worldObj, xi, yi - 1, zi) && 
								this.worldObj.setBlockAndMetadataWithNotify(xi, yi, zi, this.blockID, this.metadata))
						{
							((BlockOilSand)ModBlocks.oilSand).onFinishFalling(this.worldObj, xi, yi, zi, metadata);
						}
						else if (this.shouldDropItem)
						{
							this.entityDropItem(new ItemStack(this.blockID, 1, ModBlocks.oilSand.damageDropped(this.metadata)), 0.0F);
						}
					}

				}
				else if (this.fallTime > 100 && !this.worldObj.isRemote && (yi < 1 || yi > 256) || this.fallTime > 600)
				{
					this.setDead();
					if (this.shouldDropItem)
					{
						this.entityDropItem(new ItemStack(this.blockID, 1, ModBlocks.oilSand.damageDropped(this.metadata)), 0.0F);
					}
				}
			}
		}
	}


	@SideOnly(Side.CLIENT)
	public float getShadowSize()
	{
		return 0.0F;
	}

	@SideOnly(Side.CLIENT)
	public World getWorld()
	{
		return this.worldObj;
	}

	@SideOnly(Side.CLIENT)
	public boolean canRenderOnFire()
	{
		return false;
	}

	protected void readEntityFromNBT(NBTTagCompound var1) {}

	protected void writeEntityToNBT(NBTTagCompound var1) {}



}

