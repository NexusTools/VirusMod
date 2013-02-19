package steve4448.ExpandedTNT.EggTNT;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityTNTPrimed;
import org.lwjgl.opengl.GL11;

import steve4448.ExpandedTNT.ExpandedTNT;

@SideOnly(Side.CLIENT) public class RenderEggTNTPrimed extends Render {
	private RenderBlocks blockRenderer = new RenderBlocks();
	
	public RenderEggTNTPrimed() {
		this.shadowSize = 0.5F;
	}
	
	@Override
	public void doRender(Entity e, double x, double y, double z, float par8, float par9) {
		EntityEggTNTPrimed entityEggTNT = (EntityEggTNTPrimed)e;
		GL11.glPushMatrix();
		GL11.glTranslatef((float)x, (float)y, (float)z);
		float var10;
		
		if((float)entityEggTNT.fuse - par9 + 1.0F < 10.0F) {
			var10 = 1.0F - ((float)entityEggTNT.fuse - par9 + 1.0F) / 10.0F;
			
			if(var10 < 0.0F) {
				var10 = 0.0F;
			}
			
			if(var10 > 1.0F) {
				var10 = 1.0F;
			}
			
			var10 *= var10;
			var10 *= var10;
			float var11 = 1.0F + var10 * 0.3F;
			GL11.glScalef(var11, var11, var11);
		}
		
		var10 = (1.0F - ((float)entityEggTNT.fuse - par9 + 1.0F) / 100.0F) * 0.8F;
		this.loadTexture("/steve4448/images/tntsheet.png");
		this.blockRenderer.renderBlockAsItem(ExpandedTNT.blockEggTNT, 0, entityEggTNT.getBrightness(par9));
		
		if(entityEggTNT.fuse / 5 % 2 == 0) {
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_DST_ALPHA);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, var10);
			this.blockRenderer.renderBlockAsItem(ExpandedTNT.blockEggTNT, 0, 1.0F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
		}
		
		GL11.glPopMatrix();
	}
}
