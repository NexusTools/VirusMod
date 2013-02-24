package steve4448.VirusMod;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.FMLLog;

/**
 ** The actual controller of the virus, how it interacts with the world.
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
		if(!worldObj.isRemote)
			if(curTicks++ >= tickRate()) {
				if(toEat <= 0 && blockPositions.size() == 1 || blockPositions.size() < 1) // If the size is below 1 at this point, it's probably corrupt upon loading.
					invalidate();
				else
					for(int i = 0; i < VirusMod.eaterIterationsPerTick; i++)
						handleBlock(worldObj.rand.nextInt(blockPositions.size()));
				curTicks = 0;
			}
	}

	public void handleBlock(int slot) {
		if(!worldObj.isRemote) {
			if(slot < 0 || slot >= blockPositions.size()) {
				FMLLog.severe("Incorrect handleBlock slot. Slot " + slot + ", List Size " + blockPositions.size() + ".");
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
				for(int i = 0; i < VirusMod.uneatable.length; i++)
					if(VirusMod.uneatable[i] == worldObj.getBlockId(xyz[0] - 1, xyz[1], xyz[2]))
						flag = true;

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
				for(int i = 0; i < VirusMod.uneatable.length; i++)
					if(VirusMod.uneatable[i] == worldObj.getBlockId(xyz[0] + 1, xyz[1], xyz[2]))
						flag = true;

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
				for(int i = 0; i < VirusMod.uneatable.length; i++)
					if(VirusMod.uneatable[i] == worldObj.getBlockId(xyz[0], xyz[1] - 1, xyz[2]))
						flag = true;

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
				for(int i = 0; i < VirusMod.uneatable.length; i++)
					if(VirusMod.uneatable[i] == worldObj.getBlockId(xyz[0], xyz[1] + 1, xyz[2]))
						flag = true;

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
				for(int i = 0; i < VirusMod.uneatable.length; i++)
					if(VirusMod.uneatable[i] == worldObj.getBlockId(xyz[0], xyz[1], xyz[2] - 1))
						flag = true;

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
				for(int i = 0; i < VirusMod.uneatable.length; i++)
					if(VirusMod.uneatable[i] == worldObj.getBlockId(xyz[0], xyz[1], xyz[2] + 1))
						flag = true;

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
			} else {
				worldObj.setBlockMetadataWithNotify(xyz[0], xyz[1], xyz[2], 1);
				worldObj.markBlockForUpdate(xyz[0], xyz[1], xyz[2]); // Apparently required in order to get it to re-render with the new texture.
			}
			toEat -= VirusMod.eaterDegradation; // Eventual degradation.
		}
	}

	/**
	 * Degrade the virus based on the block it's destroyed, if enabled. It's not the way I want to do it but seems to be the only easy way right now. (getBlockHardness(x, y, z) returns whatever block it was called from, not the location's block)
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

	/**
	 * Cleanup!
	 */
	@Override
	public void invalidate() {
		super.invalidate();
		toEat = 0;
		for(int i = 0; i < blockPositions.size(); i++) {
			int[] xyz = blockPositions.get(i);
			worldObj.setBlockAndMetadataWithNotify(xyz[0], xyz[1], xyz[2], 0, 0);
		}
		blockPositions.clear();
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setFloat("ToEat", toEat);

		NBTTagList coordList = new NBTTagList();
		for(int i = 0; i < blockPositions.size(); i++) {
			int xyz[] = blockPositions.get(i);
			NBTTagCompound values = new NBTTagCompound();
			values.setInteger("X", xyz[0]);
			values.setInteger("Y", xyz[1]);
			values.setInteger("Z", xyz[2]);
			coordList.appendTag(values);
		}
		compound.setTag("BlockPositions", coordList);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		toEat = compound.getFloat("ToEat");

		NBTTagList coordList = compound.getTagList("BlockPositions");
		for(int i = 0; i < coordList.tagCount(); i++) {
			NBTTagCompound values = (NBTTagCompound) coordList.tagAt(i);
			blockPositions.add(new int[]{values.getInteger("X"), values.getInteger("Y"), values.getInteger("Z")});
		}
	}
}
