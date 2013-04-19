package steve4448.block;

import java.util.Random;

import steve4448.VirusMod;
import steve4448.tileentity.TileEntityVirus;

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
		setBlockName("Eater Virus Stub");
		setCreativeTab(CreativeTabs.tabBlock);
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int par1, int meta) {
		return meta == 0 ? VirusMod.TEXTURE_EATER_VIRUS : VirusMod.TEXTURE_HACKY_STUB;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityVirus();
	}

	@Override
	public void onPostBlockPlaced(World world, int x, int y, int z, int meta) {
		if(!(world.getBlockTileEntity(x, y, z) instanceof TileEntityVirus))
			return;

		TileEntityVirus eaterEntity = (TileEntityVirus) world.getBlockTileEntity(x, y, z);
		eaterEntity.init(VirusMod.EATER_VIRUS, 0, 0, VirusMod.eaterVirusStrengthMin + world.rand.nextInt(VirusMod.eaterVirusStrengthMax - VirusMod.eaterVirusStrengthMin), x, y, z, false);
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
}
