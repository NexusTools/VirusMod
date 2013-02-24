package steve4448.VirusMod;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

/**
 ** Mostly just a 'stub' of the virus block, doesn't have any tile entity attached.
 **/
public class BlockEaterVirus extends Block {

	public BlockEaterVirus(int id) {
		super(id, Material.air);
		setBlockName("Eater Virus");
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
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
		return null;
	}

	@Override
	public boolean isCollidable() {
		return false;
	}

	@Override
	public String getTextureFile() {
		return "/steve4448/images/virussheet.png";
	}

	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random random) {
		world.spawnParticle("tilecrack_" + blockID + "_0", x + random.nextDouble(), y + random.nextDouble(), z + random.nextDouble(), random.nextDouble() - random.nextDouble(), random.nextDouble() - random.nextDouble(), random.nextDouble() - random.nextDouble());
	}
}
