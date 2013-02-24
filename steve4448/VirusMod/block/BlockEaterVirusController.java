package steve4448.VirusMod.block;

import java.util.Random;

import steve4448.VirusMod.VirusMod;
import steve4448.VirusMod.tileentity.TileEntityEaterVirus;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

/**
 ** This block is essentially the 'host' of the virus, it creates the tile entity.
 **/
public class BlockEaterVirusController extends BlockContainer {

	public BlockEaterVirusController(int id) {
		super(id, Material.air);
		setBlockName("Eater Virus");
		setCreativeTab(CreativeTabs.tabBlock);
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int par1, int meta) {
		return meta == 0 ? VirusMod.TEXTURE_VIRUS_EATER : VirusMod.TEXTURE_VIRUS_STUB;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityEaterVirus();
	}

	@Override
	public void onPostBlockPlaced(World world, int x, int y, int z, int meta) {
		if(!(world.getBlockTileEntity(x, y, z) instanceof TileEntityEaterVirus))
			return;

		TileEntityEaterVirus eaterEntity = (TileEntityEaterVirus) world.getBlockTileEntity(x, y, z);
		eaterEntity.init(VirusMod.eaterStrengthMin + world.rand.nextInt(VirusMod.eaterStrengthMax - VirusMod.eaterStrengthMin), x, y, z);
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
