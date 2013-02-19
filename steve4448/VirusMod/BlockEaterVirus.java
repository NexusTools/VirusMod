package steve4448.VirusMod;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;

public class BlockEaterVirus extends BlockContainer {
	
	public BlockEaterVirus(int id) {
		super(id, Material.air);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setBlockName("Eater Virus");
	}
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		//world.blockExists(par1, par2, par3);
		TileEntityVirusController virusController = (TileEntityVirusController)world.getBlockTileEntity(x, y, z);
		System.out.println("Remaining: " + virusController.getRemainingSpread());
		virusController.setRemainingSpread(virusController.getRemainingSpread() - 1);
		System.out.println("Remaining now: " + virusController.getRemainingSpread());
		if(virusController.getRemainingSpread() <= 0) {
			world.setBlockAndMetadataWithNotify(x, y, z, 0, 0);
			return;
		}
		if(world.getBlockId(x - 1, y, z) != 0) {
			if(world.getBlockTileEntity(x - 1, y, z) != null) world.setBlockTileEntity(x - 1, y, z, null);
			world.setBlockAndMetadata(x - 1, y, z, VirusMod.blockEaterVirusID, 0);
			world.scheduleBlockUpdate(x - 1, y, z, blockID, (tickRate() / 2) + world.rand.nextInt(tickRate()));
		}
		if(world.getBlockId(x + 1, y, z) != 0) {
			if(world.getBlockTileEntity(x + 1, y, z) != null) world.setBlockTileEntity(x + 1, y, z, null);
			world.setBlockAndMetadata(x + 1, y, z, VirusMod.blockEaterVirusID, 0);
			world.scheduleBlockUpdate(x + 1, y, z, blockID, (tickRate() / 2) + world.rand.nextInt(tickRate()));
		}
		
		if(world.getBlockId(x, y - 1, z) != 0) {
			if(world.getBlockTileEntity(x, y - 1, z) != null) world.setBlockTileEntity(x, y - 1, z, null);
			world.setBlockAndMetadata(x, y - 1, z, VirusMod.blockEaterVirusID, 0);
			world.scheduleBlockUpdate(x, y - 1, z, blockID, (tickRate() / 2) + world.rand.nextInt(tickRate()));
		}
		if(world.getBlockId(x, y + 1, z) != 0) {
			if(world.getBlockTileEntity(x, y + 1, z) != null) world.setBlockTileEntity(x, y + 1, z, null);
			world.setBlockAndMetadata(x, y + 1, z, VirusMod.blockEaterVirusID, 0);
			world.scheduleBlockUpdate(x, y + 1, z, blockID, (tickRate() / 2) + world.rand.nextInt(tickRate()));
		}
		
		if(world.getBlockId(x, y, z - 1) != 0) {
			if(world.getBlockTileEntity(x, y, z - 1) != null) world.setBlockTileEntity(x, y, z - 1, null);
			world.setBlockAndMetadata(x, y, z - 1, VirusMod.blockEaterVirusID, 0);
			world.scheduleBlockUpdate(x, y, z - 1, blockID, (tickRate() / 2) + world.rand.nextInt(tickRate()));
		}
		if(world.getBlockId(x, y, z + 1) != 0) {
			if(world.getBlockTileEntity(x, y, z + 1) != null) world.setBlockTileEntity(x, y, z + 1, null);
			world.setBlockAndMetadata(x, y, z + 1, VirusMod.blockEaterVirusID, 0);
			world.scheduleBlockUpdate(x, y, z + 1, blockID, (tickRate() / 2) + world.rand.nextInt(tickRate()));
		}
		
		world.setBlockAndMetadataWithNotify(x, y, z, 0, 0);
	}
	
	@Override
	public int tickRate() {
		return 120;
	}
	
	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta) {
		super.onBlockPlaced(world, x, y, z, side, hitX, hitY, hitZ, meta);
		world.scheduleBlockUpdate(x, y, z, blockID, tickRate());
		return meta;
	}
	
	@Override
    public int getBlockTextureFromSide(int par1) {
        return VirusMod.TEXTURE_VIRUS_EATER;
    }
	
	@Override
    public TileEntity createNewTileEntity(World par1World) {
		TileEntityVirusController ent = new TileEntityVirusController();
		ent.setRemainingSpread(par1World.rand.nextInt(100));
        return ent;
    }
	
    @Override
    public int quantityDropped(Random par1Random) {
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
