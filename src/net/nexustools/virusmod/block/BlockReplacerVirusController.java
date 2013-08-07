package net.nexustools.virusmod.block;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.nexustools.virusmod.VirusMod;
import net.nexustools.virusmod.tileentity.TileEntityVirus;

/**
 ** This block is essentially the 'host' of the virus, it creates the tile entity.
 **/
public class BlockReplacerVirusController extends BlockContainer {

	public BlockReplacerVirusController(int id) {
		super(id, Material.air);
		setBlockName("Replacer Virus Stub");
		setCreativeTab(CreativeTabs.tabBlock);
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int par1, int meta) {
		return meta == 0 ? VirusMod.TEXTURE_REPLACER_VIRUS : VirusMod.TEXTURE_HACKY_STUB;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityVirus();
	}

	@Override
	public void onPostBlockPlaced(World world, int x, int y, int z, int meta) {
		if(!(world.getBlockTileEntity(x, y, z) instanceof TileEntityVirus))
			return;

		TileEntityVirus replacerEntity = (TileEntityVirus) world.getBlockTileEntity(x, y, z);
		replacerEntity.init(VirusMod.REPLACER_VIRUS, world.getBlockId(x, y + 1, z), world.getBlockMetadata(x, y + 1, z), VirusMod.replacerVirusStrengthMin + world.rand.nextInt(VirusMod.replacerVirusStrengthMax - VirusMod.replacerVirusStrengthMin), x, y, z, false);
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
}
