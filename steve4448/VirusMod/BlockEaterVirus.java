package steve4448.VirusMod;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;

public class BlockEaterVirus extends Block {
	
	public BlockEaterVirus(int id) {
		super(id, Material.air);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setBlockName("Eater Virus");
	}
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		int toEat = world.getBlockMetadata(x, y, z);
		if(toEat <= 0) {
			world.setBlockAndMetadata(x, y, z, 0, 0);
			return;
		}
		if(world.blockExists(x - 1, y, z)) {
			boolean flag = false;
			for(int i = 0; i < VirusMod.uneatable.length; i++) {
				if(VirusMod.uneatable[i] == world.getBlockId(x - 1, y, z)) {
					flag = true;
				}
			}
			if(!flag) {
				world.setBlockAndMetadata(x - 1, y, z, VirusMod.blockEaterVirusID, toEat - 1);
				world.scheduleBlockUpdate(x - 1, y, z, blockID, (tickRate() / 2) + world.rand.nextInt(tickRate() / 2));
			}
		}
		if(world.blockExists(x + 1, y, z)) {
			boolean flag = false;
			for(int i = 0; i < VirusMod.uneatable.length; i++) {
				if(VirusMod.uneatable[i] == world.getBlockId(x + 1, y, z)) {
					flag = true;
				}
			}
			if(!flag) {
				world.setBlockAndMetadata(x + 1, y, z, VirusMod.blockEaterVirusID, toEat - 1);
				world.scheduleBlockUpdate(x + 1, y, z, blockID, (tickRate() / 2) + world.rand.nextInt(tickRate() / 2));
			}
		}
		
		if(world.blockExists(x, y - 1, z)) {
			boolean flag = false;
			for(int i = 0; i < VirusMod.uneatable.length; i++) {
				if(VirusMod.uneatable[i] == world.getBlockId(x, y - 1, z)) {
					flag = true;
				}
			}
			if(!flag) {
				world.setBlockAndMetadata(x, y - 1, z, VirusMod.blockEaterVirusID, toEat - 1);
				world.scheduleBlockUpdate(x, y - 1, z, blockID, (tickRate() / 2) + world.rand.nextInt(tickRate() / 2));
			}
		}
		if(world.blockExists(x, y + 1, z)) {
			boolean flag = false;
			for(int i = 0; i < VirusMod.uneatable.length; i++) {
				if(VirusMod.uneatable[i] == world.getBlockId(x, y + 1, z)) {
					flag = true;
				}
			}
			if(!flag) {
				world.setBlockAndMetadata(x, y + 1, z, VirusMod.blockEaterVirusID, toEat - 1);
				world.scheduleBlockUpdate(x, y + 1, z, blockID, (tickRate() / 2) + world.rand.nextInt(tickRate() / 2));
			}
		}
		
		if(world.blockExists(x, y, z - 1)) {
			boolean flag = false;
			for(int i = 0; i < VirusMod.uneatable.length; i++) {
				if(VirusMod.uneatable[i] == world.getBlockId(x, y, z - 1)) {
					flag = true;
				}
			}
			if(!flag) {
				world.setBlockAndMetadata(x, y, z - 1, VirusMod.blockEaterVirusID, toEat - 1);
				world.scheduleBlockUpdate(x, y, z - 1, blockID, (tickRate() / 2) + world.rand.nextInt(tickRate() / 2));
			}
		}
		if(world.blockExists(x, y, z + 1)) {
			boolean flag = false;
			for(int i = 0; i < VirusMod.uneatable.length; i++) {
				if(VirusMod.uneatable[i] == world.getBlockId(x, y, z + 1)) {
					flag = true;
				}
			}
			if(!flag) {
				world.setBlockAndMetadata(x, y, z + 1, VirusMod.blockEaterVirusID, toEat - 1);
				world.scheduleBlockUpdate(x, y, z + 1, blockID, (tickRate() / 2) + world.rand.nextInt(tickRate() / 2));
			}
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
		world.scheduleBlockUpdate(x, y, z, blockID, (tickRate() / 2) + world.rand.nextInt(tickRate() / 2));
		return 4 + world.rand.nextInt(6);
	}
	
	@Override
    public int getBlockTextureFromSide(int par1) {
        return VirusMod.TEXTURE_VIRUS_EATER;
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
