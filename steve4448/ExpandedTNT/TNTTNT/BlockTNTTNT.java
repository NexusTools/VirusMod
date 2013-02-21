package steve4448.ExpandedTNT.TNTTNT;

import steve4448.ExpandedTNT.ExpandedTNT;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTNT;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class BlockTNTTNT extends BlockTNT {
	public BlockTNTTNT(int par1) {
		super(par1, 0);
		setBlockName("TNT TNT");
		setCreativeTab(CreativeTabs.tabRedstone);
	}
	
	@Override
	public int getBlockTextureFromSide(int par1) {
		if(par1 == 0)
			return ExpandedTNT.TNT_TEXTURE_BOTTOM;
		else if(par1 == 1)
			return ExpandedTNT.TNT_TEXTURE_TOP;
		else
			return ExpandedTNT.TNT_TNT_SIDES;
	}
	
	@Override
	public void onBlockDestroyedByExplosion(World par1World, int par2, int par3, int par4) {
		if(!par1World.isRemote) {
			EntityTNTTNTPrimed var5 = new EntityTNTTNTPrimed(par1World);
			var5.setPosition((double)((float)par2 + 0.5F), (double)((float)par3 + 0.5F), (double)((float)par4 + 0.5F));
			var5.fuse = par1World.rand.nextInt(var5.fuse / 8);
			par1World.spawnEntityInWorld(var5);
		}
	}
	
	@Override
	public void onBlockDestroyedByPlayer(World par1World, int par2, int par3, int par4, int par5) {
		if(!par1World.isRemote) {
			if((par5 & 1) == 1) {
				EntityTNTTNTPrimed var6 = new EntityTNTTNTPrimed(par1World);
				var6.setPosition((double)((float)par2 + 0.5F), (double)((float)par3 + 0.5F), (double)((float)par4 + 0.5F));
				par1World.spawnEntityInWorld(var6);
				par1World.playSoundAtEntity(var6, "random.fuse", 1.0F, 1.0F);
			}
		}
	}
	
	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
		if(par5EntityPlayer.getCurrentEquippedItem() != null && par5EntityPlayer.getCurrentEquippedItem().itemID == Item.flintAndSteel.itemID) {
			this.onBlockDestroyedByPlayer(par1World, par2, par3, par4, 1);
			par1World.setBlockWithNotify(par2, par3, par4, 0);
			return true;
		} else {
			return super.onBlockActivated(par1World, par2, par3, par4, par5EntityPlayer, par6, par7, par8, par9);
		}
	}
	
	@Override
	public boolean canDropFromExplosion(Explosion par1Explosion) {
		return false;
	}
	
	@Override
	public String getTextureFile() {
		return "/steve4448/images/tntsheet.png";
	}
}
