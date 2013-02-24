package steve4448.VirusMod;

import java.util.Random;

import cpw.mods.fml.common.FMLLog;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
/**
 ** This block is essentially the 'host' of the virus, it creates the tile entity.
 **/
public class BlockEaterVirusController extends BlockContainer {

	public BlockEaterVirusController(int id) {
		super(id, Material.air);
		this.setBlockName("Eater Virus");
		this.setCreativeTab(CreativeTabs.tabBlock);
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
		if(!(world.getBlockTileEntity(x, y, z) instanceof TileEntityEaterVirus)) {
			return;
		}
		
		TileEntityEaterVirus eaterEntity = (TileEntityEaterVirus)world.getBlockTileEntity(x, y, z);
		eaterEntity.init((VirusMod.eaterStrengthMin + world.rand.nextInt(VirusMod.eaterStrengthMax - VirusMod.eaterStrengthMin)), x, y, z);
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
	public String getTextureFile() {
		return "/steve4448/images/virussheet.png";
	}
}
