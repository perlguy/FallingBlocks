package pg.mod.fb.block.entity;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

import pg.mod.fb.block.ModBlocks;
import pg.mod.fb.renderer.RenderBlocks;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderFallingBlock extends Render
{
	private RenderBlocks blockRenderer = new RenderBlocks();

	public RenderFallingBlock() {}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
	 * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
	 * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
	 * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
	 */
	public void doRender(Entity entity, double x, double y, double z, float par8, float par9)
	{
		FallingBlock fallingBlock = (FallingBlock)entity;
		
		int xi = MathHelper.floor_double(x);
		int yi = MathHelper.floor_double(y);
		int zi = MathHelper.floor_double(z);
		
		this.loadTexture("/terrain.png");
		GL11.glPushMatrix();
		GL11.glTranslatef((float)x, (float)y, (float)z);
        GL11.glDisable(GL11.GL_LIGHTING);

		blockRenderer.renderFallingBlock(ModBlocks.unstableBlock, fallingBlock.getWorld(), xi, yi, zi, 0);

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
		
	}
}
