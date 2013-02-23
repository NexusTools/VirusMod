package steve4448.VirusMod;

import java.util.ArrayList;

import cpw.mods.fml.common.FMLLog;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
/**
 **  The actual controller of the virus, how it interacts with the world.
 **/
public class TileEntityEaterVirus extends TileEntity {

	public ArrayList<int[]> blockPositions = new ArrayList<int[]>();
	public float toEat = 0;
	private int curTicks = 0;
	
	public void init(float toEat, int x, int y, int z) {
		this.toEat = toEat;
		blockPositions.add(new int[]{x, y, z});
	}
	
	@Override
	public void updateEntity() {
		if(curTicks++ >= tickRate()) {
			if((toEat <= 0 && blockPositions.size() == 1) || blockPositions.size() < 1) //If the size is below 1 at this point, probably corrupted upon loading.
				this.invalidate();
			else
				for(int i = 0; i < VirusMod.eaterIterationsPerTick; i++)
					handleBlock((blockPositions.size() > i ? worldObj.rand.nextInt(blockPositions.size() + i) : 0)); //Increments i so that the potential of calling the same exact one again doesn't occur.
			curTicks = 0;
		}
	}
	
	public void handleBlock(int slot) {
		if(!worldObj.isRemote) {
			System.out.println(blockPositions.size() + ", " + toEat);
			if(slot < 0 || slot >= blockPositions.size()) {
				FMLLog.severe("Incorrect handleBlock slot?");
				return;
			}
			int[] xyz = blockPositions.get(slot);
			
			if(toEat <= 0 && slot > 0) {
				worldObj.setBlockAndMetadataWithNotify(xyz[0], xyz[1], xyz[2], 0, 0);
				blockPositions.remove(xyz);
				return;
			}
			
			if(worldObj.blockExists(xyz[0] - 1, xyz[1], xyz[2]) && worldObj.rand.nextBoolean()) {
				boolean flag = false;
				for(int i = 0; i < VirusMod.uneatable.length; i++) {
					if(VirusMod.uneatable[i] == worldObj.getBlockId(xyz[0] - 1, xyz[1], xyz[2])) {
						flag = true;
					}
				}
				
				if(!flag) {
					degradeBasedOffBlock(xyz[0], xyz[1], xyz[2]);
					worldObj.removeBlockTileEntity(xyz[0] - 1, xyz[1], xyz[2]);
					worldObj.setBlockAndMetadata(xyz[0] - 1, xyz[1], xyz[2], VirusMod.blockEaterVirus.blockID, 0);
					blockPositions.add(new int[]{xyz[0] - 1, xyz[1], xyz[2]});
					toEat -= VirusMod.eaterDegradation;
				}
			}
			
			if(worldObj.blockExists(xyz[0] + 1, xyz[1], xyz[2]) && worldObj.rand.nextBoolean()) {
				boolean flag = false;
				for(int i = 0; i < VirusMod.uneatable.length; i++) {
					if(VirusMod.uneatable[i] == worldObj.getBlockId(xyz[0] + 1, xyz[1], xyz[2])) {
						flag = true;
					}
				}
				
				if(!flag) {
					degradeBasedOffBlock(xyz[0], xyz[1], xyz[2]);
					worldObj.removeBlockTileEntity(xyz[0] + 1, xyz[1], xyz[2]);
					worldObj.setBlockAndMetadata(xyz[0] + 1, xyz[1], xyz[2], VirusMod.blockEaterVirus.blockID, 0);
					blockPositions.add(new int[]{xyz[0] + 1, xyz[1], xyz[2]});
					toEat -= VirusMod.eaterDegradation;
				}
			}
			
			if(worldObj.blockExists(xyz[0], xyz[1] - 1, xyz[2]) && worldObj.rand.nextBoolean()) {
				boolean flag = false;
				for(int i = 0; i < VirusMod.uneatable.length; i++) {
					if(VirusMod.uneatable[i] == worldObj.getBlockId(xyz[0], xyz[1] - 1, xyz[2])) {
						flag = true;
					}
				}
				
				if(!flag) {
					degradeBasedOffBlock(xyz[0], xyz[1], xyz[2]);
					worldObj.removeBlockTileEntity(xyz[0], xyz[1] - 1, xyz[2]);
					worldObj.setBlockAndMetadata(xyz[0], xyz[1] - 1, xyz[2], VirusMod.blockEaterVirus.blockID, 0);
					blockPositions.add(new int[]{xyz[0], xyz[1] - 1, xyz[2]});
					toEat -= VirusMod.eaterDegradation;
				}
			}
			
			if(worldObj.blockExists(xyz[0], xyz[1] + 1, xyz[2]) && worldObj.rand.nextBoolean()) {
				boolean flag = false;
				for(int i = 0; i < VirusMod.uneatable.length; i++) {
					if(VirusMod.uneatable[i] == worldObj.getBlockId(xyz[0], xyz[1] + 1, xyz[2])) {
						flag = true;
					}
				}
				
				if(!flag) {
					degradeBasedOffBlock(xyz[0], xyz[1], xyz[2]);
					worldObj.removeBlockTileEntity(xyz[0], xyz[1] + 1, xyz[2]);
					worldObj.setBlockAndMetadata(xyz[0], xyz[1] + 1, xyz[2], VirusMod.blockEaterVirus.blockID, 0);
					blockPositions.add(new int[]{xyz[0], xyz[1] + 1, xyz[2]});
					toEat -= VirusMod.eaterDegradation;
				}
			}
			
			if(worldObj.blockExists(xyz[0], xyz[1], xyz[2] - 1) && worldObj.rand.nextBoolean()) {
				boolean flag = false;
				for(int i = 0; i < VirusMod.uneatable.length; i++) {
					if(VirusMod.uneatable[i] == worldObj.getBlockId(xyz[0], xyz[1], xyz[2] - 1)) {
						flag = true;
					}
				}
				
				if(!flag) {
					degradeBasedOffBlock(xyz[0], xyz[1], xyz[2]);
					worldObj.removeBlockTileEntity(xyz[0], xyz[1], xyz[2] - 1);
					worldObj.setBlockAndMetadata(xyz[0], xyz[1], xyz[2] - 1, VirusMod.blockEaterVirus.blockID, 0);
					blockPositions.add(new int[]{xyz[0], xyz[1], xyz[2] - 1});
					toEat -= VirusMod.eaterDegradation;
				}
			}
			
			if(worldObj.blockExists(xyz[0], xyz[1], xyz[2] + 1) && worldObj.rand.nextBoolean()) {
				boolean flag = false;
				for(int i = 0; i < VirusMod.uneatable.length; i++) {
					if(VirusMod.uneatable[i] == worldObj.getBlockId(xyz[0], xyz[1], xyz[2] + 1)) {
						flag = true;
					}
				}
				
				if(!flag) {
					degradeBasedOffBlock(xyz[0], xyz[1], xyz[2]);
					worldObj.removeBlockTileEntity(xyz[0], xyz[1], xyz[2] + 1);
					worldObj.setBlockAndMetadata(xyz[0], xyz[1], xyz[2] + 1, VirusMod.blockEaterVirus.blockID, 0);
					blockPositions.add(new int[]{xyz[0], xyz[1], xyz[2] + 1});
					toEat -= VirusMod.eaterDegradation;
				}
			}
			if(slot != 0) {
				worldObj.setBlockAndMetadataWithNotify(xyz[0], xyz[1], xyz[2], 0, 0);
				blockPositions.remove(xyz);
			}
			toEat -= VirusMod.eaterDegradation; //Eventual degradation.
		}
	}
	
	/**
	 * Degrade the virus based on the block it's destroyed, if enabled.
	 * It's not the way I want to do it but seems to be the only easy way right now. (getBlockHardness(x, y, z) returns whatever block it was called from, not the location's block)
	 */
	public void degradeBasedOffBlock(int x, int y, int z) {
		if(VirusMod.useBlockResistance) {
			int type = worldObj.getBlockId(x, y, z);
			for(int i = 0; i < Block.blocksList.length; i++)
				if(Block.blocksList[i] != null)
					if(Block.blocksList[i].idPicked(worldObj, x, y, z) == type) {
						toEat -= Block.blocksList[i].getBlockHardness(worldObj, x, y, z);
						break;
					}
		}
	}
	
	/**
	 ** Every * server ticks, actually tick.
	 **/
	public int tickRate() {
		return VirusMod.eaterTickRate;
	}
}
