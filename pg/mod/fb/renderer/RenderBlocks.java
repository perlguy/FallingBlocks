package pg.mod.fb.renderer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class RenderBlocks extends net.minecraft.client.renderer.RenderBlocks {
	
	public RenderBlocks() { super(); }
	
	public void renderFallingOilSand(Block block, World world, int x, int y, int z, int metadata)
    {
        Tessellator tessellator = Tessellator.instance;
        
        tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
        
        this.renderMinX = 0.0;
        this.renderMaxX = 1.0;
        this.renderMinY = 0.0;
        this.renderMaxY = 1.0;
        this.renderMinZ = 0.0;
        this.renderMaxZ = 1.0;
        
        tessellator.startDrawingQuads();
        
        tessellator.setColorOpaque_F(0.5F, 0.5F, 0.5F);
        this.renderBottomFace(block, -0.50D, -0.50D, -0.50D, block.blockIndexInTexture);

        tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
        this.renderTopFace(block, -0.50D, -0.50D, -0.50D, block.blockIndexInTexture);

        tessellator.setColorOpaque_F(0.8F,0.8F,0.8F);
        this.renderEastFace(block, -0.50D, -0.50D, -0.50D, block.blockIndexInTexture);
        this.renderWestFace(block, -0.50D, -0.50D, -0.50D, block.blockIndexInTexture);

        tessellator.setColorOpaque_F(0.6F,0.6F,0.6F);
        this.renderNorthFace(block, -0.50D, -0.50D, -0.50D, block.blockIndexInTexture);
        this.renderSouthFace(block, -0.50D, -0.50D, -0.50D, block.blockIndexInTexture);
        
        tessellator.draw();
    }
    
}
