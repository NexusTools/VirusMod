package nexustools.virusmod.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import nexustools.virusmod.VirusMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 ** Mostly just a 'stub' of the virus block, doesn't have any tile entity attached.
 **/
public class BlockVirusStub extends Block {

	public BlockVirusStub(int id) {
		super(id, Material.air);
		setBlockName("Virus Stub");
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int par1, int meta) {
		return meta; // Meta has direct correlation to the texture id.
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
		return "/nexustools/virusmod/images/virussheet.png";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random random) {
		world.spawnParticle("tilecrack_" + blockID + "_" + world.getBlockMetadata(x, y, z), x + random.nextDouble(), y + random.nextDouble(), z + random.nextDouble(), random.nextDouble() - random.nextDouble(), random.nextDouble() - random.nextDouble(), random.nextDouble() - random.nextDouble());
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int unknown, CreativeTabs tab, List subItems) {
		for(int i = 0; i < VirusMod.VIRUS_BLOCK_NAMES.length; i++)
			subItems.add(new ItemStack(this, 1, i));
	}
}
