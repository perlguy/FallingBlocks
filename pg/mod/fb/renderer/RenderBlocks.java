package pg.mod.fb.renderer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class RenderBlocks extends net.minecraft.client.renderer.RenderBlocks {
	
	public RenderBlocks() { super(); }
	
	public void renderFallingBlock(Block block, World world, int x, int y, int z, int metadata)
    {
        Tessellator tessellator = Tessellator.instance;
        
        tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
        
        this.renderMinX = 0.0;
        this.renderMaxX = 1.0;
        this.renderMinY = 0.0;
        this.renderMaxY = 1.0;
        this.renderMinZ = 0.0;
        this.renderMaxZ = 1.0;
        double offset = -0.5D;
        
        tessellator.startDrawingQuads();

        tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
        this.renderTopFace(block, offset, offset, offset, block.blockIndexInTexture);

        tessellator.setColorOpaque_F(0.8F,0.8F,0.8F);
        this.renderEastFace(block, offset, offset, offset, block.blockIndexInTexture);
        this.renderWestFace(block, offset, offset, offset, block.blockIndexInTexture);

        tessellator.setColorOpaque_F(0.6F,0.6F,0.6F);
        this.renderNorthFace(block, offset, offset, offset, block.blockIndexInTexture);
        this.renderSouthFace(block, offset, offset, offset, block.blockIndexInTexture);

        tessellator.setColorOpaque_F(0.5F, 0.5F, 0.5F);
        this.renderBottomFace(block, offset, offset, offset, block.blockIndexInTexture);
        
        tessellator.draw();
    }
    
}
