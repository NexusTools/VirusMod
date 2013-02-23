package steve4448.VirusMod;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;
/**
** Mostly just a 'stub' of the virus block, doesn't have any tile entity attached.
**/
public class BlockEaterVirus extends Block {

	public BlockEaterVirus(int id) {
		super(id, Material.air);
		this.setBlockName("Eater Virus");
	}

	@Override
	public int getBlockTextureFromSide(int par1) {
		return VirusMod.TEXTURE_VIRUS_EATER;
	}
	
	@Override
	public int quantityDropped(Random rand) {
		return 0;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public String getTextureFile() {
		return "/steve4448/images/virussheet.png";
	}
}
