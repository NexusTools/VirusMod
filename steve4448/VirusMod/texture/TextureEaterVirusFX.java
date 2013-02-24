package steve4448.VirusMod.texture;

import org.lwjgl.opengl.GL11;

import steve4448.VirusMod.VirusMod;

import cpw.mods.fml.client.FMLTextureFX;

import net.minecraft.client.renderer.RenderEngine;

public class TextureEaterVirusFX extends FMLTextureFX {
	public TextureEaterVirusFX() {
		super(VirusMod.blockEaterVirus.blockIndexInTexture);
		this.tileSize = 2;
		this.tileSizeBase = 2;
	}
	
	@Override
	public void bindImage(RenderEngine renderEngine) {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, renderEngine.getTexture("/steve4448/anim/eatervirusanim.png"));
	}
}
