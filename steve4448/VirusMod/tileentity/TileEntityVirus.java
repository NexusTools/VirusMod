package steve4448.VirusMod.tileentity;

import java.util.ArrayList;

import steve4448.VirusMod.VirusMod;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.FMLLog;

/**
 ** The actual controller of the viruses, how it interacts with the world.
 **/
public class TileEntityVirus extends TileEntity {
	public ArrayList<int[]> blockPositions = new ArrayList<int[]>();
	public int virusType = 0;
	public int replaceWith = 0;
	public int replaceWithMeta = 0;
	public float degradationLeft = 0;
	public boolean dropsResource = false;
	public boolean hasHiddenHost = false;
	private int curTicks = 0;

	public void init(int virusType, int replaceWith, int replaceWithMeta, float degradationLeft, int x, int y, int z, boolean dropsResource) {
		this.virusType = virusType;
		this.replaceWith = replaceWith;
		this.replaceWithMeta = replaceWithMeta;
		this.degradationLeft = degradationLeft;
		this.dropsResource = dropsResource;
		blockPositions.add(new int[]{x, y, z});
	}

	@Override
	public void updateEntity() {
		if(!worldObj.isRemote)
			if(curTicks++ >= tickRate()) {
				if(degradationLeft <= 0 && blockPositions.size() == 1 || blockPositions.size() < 1) // If the size is below 1 at this point, it's probably corrupt upon loading.
					invalidate();
				else {
					for(int i = 0; i < VirusMod.virusIterationsPerTick; i++)
						handleBlock(worldObj.rand.nextInt(blockPositions.size()));
				}
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

			if(degradationLeft <= 0 && slot > 0) {
				if(dropsResource)
					dropResource(xyz[0], xyz[1], xyz[2]);
				worldObj.setBlockAndMetadataWithNotify(xyz[0], xyz[1], xyz[2], replaceWith, replaceWithMeta);
				blockPositions.remove(xyz);
				return;
			}

			if(worldObj.blockExists(xyz[0] - 1, xyz[1], xyz[2]) && worldObj.rand.nextBoolean()) {
				boolean flag = false;
				for(int i = 0; i < VirusMod.untouchable.length; i++)
					if(VirusMod.untouchable[i] == worldObj.getBlockId(xyz[0] - 1, xyz[1], xyz[2]) || replaceWith == worldObj.getBlockId(xyz[0] - 1, xyz[1], xyz[2]))
						flag = true;

				if(!flag) {
					degradeBasedOffBlock(xyz[0], xyz[1], xyz[2]);
					worldObj.removeBlockTileEntity(xyz[0] - 1, xyz[1], xyz[2]);
					worldObj.setBlockAndMetadata(xyz[0] - 1, xyz[1], xyz[2], VirusMod.blockVirusStub.blockID, virusType);
					blockPositions.add(new int[]{xyz[0] - 1, xyz[1], xyz[2]});
					degradationLeft -= VirusMod.VIRUS_DEGRADATION;
				}
			}

			if(worldObj.blockExists(xyz[0] + 1, xyz[1], xyz[2]) && worldObj.rand.nextBoolean()) {
				boolean flag = false;
				for(int i = 0; i < VirusMod.untouchable.length; i++)
					if(VirusMod.untouchable[i] == worldObj.getBlockId(xyz[0] + 1, xyz[1], xyz[2]) || replaceWith == worldObj.getBlockId(xyz[0] + 1, xyz[1], xyz[2]))
						flag = true;

				if(!flag) {
					degradeBasedOffBlock(xyz[0], xyz[1], xyz[2]);
					worldObj.removeBlockTileEntity(xyz[0] + 1, xyz[1], xyz[2]);
					worldObj.setBlockAndMetadata(xyz[0] + 1, xyz[1], xyz[2], VirusMod.blockVirusStub.blockID, virusType);
					blockPositions.add(new int[]{xyz[0] + 1, xyz[1], xyz[2]});
					degradationLeft -= VirusMod.VIRUS_DEGRADATION;
				}
			}

			if(worldObj.blockExists(xyz[0], xyz[1] - 1, xyz[2]) && worldObj.rand.nextBoolean()) {
				boolean flag = false;
				for(int i = 0; i < VirusMod.untouchable.length; i++)
					if(VirusMod.untouchable[i] == worldObj.getBlockId(xyz[0], xyz[1] - 1, xyz[2]) || replaceWith == worldObj.getBlockId(xyz[0], xyz[1] - 1, xyz[2]))
						flag = true;

				if(!flag) {
					degradeBasedOffBlock(xyz[0], xyz[1], xyz[2]);
					worldObj.removeBlockTileEntity(xyz[0], xyz[1] - 1, xyz[2]);
					worldObj.setBlockAndMetadata(xyz[0], xyz[1] - 1, xyz[2], VirusMod.blockVirusStub.blockID, virusType);
					blockPositions.add(new int[]{xyz[0], xyz[1] - 1, xyz[2]});
					degradationLeft -= VirusMod.VIRUS_DEGRADATION;
				}
			}

			if(worldObj.blockExists(xyz[0], xyz[1] + 1, xyz[2]) && worldObj.rand.nextBoolean() || replaceWith == worldObj.getBlockId(xyz[0], xyz[1] + 1, xyz[2])) {
				boolean flag = false;
				for(int i = 0; i < VirusMod.untouchable.length; i++)
					if(VirusMod.untouchable[i] == worldObj.getBlockId(xyz[0], xyz[1] + 1, xyz[2]))
						flag = true;

				if(!flag) {
					degradeBasedOffBlock(xyz[0], xyz[1], xyz[2]);
					worldObj.removeBlockTileEntity(xyz[0], xyz[1] + 1, xyz[2]);
					worldObj.setBlockAndMetadata(xyz[0], xyz[1] + 1, xyz[2], VirusMod.blockVirusStub.blockID, virusType);
					blockPositions.add(new int[]{xyz[0], xyz[1] + 1, xyz[2]});
					degradationLeft -= VirusMod.VIRUS_DEGRADATION;
				}
			}

			if(worldObj.blockExists(xyz[0], xyz[1], xyz[2] - 1) && worldObj.rand.nextBoolean()) {
				boolean flag = false;
				for(int i = 0; i < VirusMod.untouchable.length; i++)
					if(VirusMod.untouchable[i] == worldObj.getBlockId(xyz[0], xyz[1], xyz[2] - 1) || replaceWith == worldObj.getBlockId(xyz[0], xyz[1], xyz[2] - 1))
						flag = true;

				if(!flag) {
					degradeBasedOffBlock(xyz[0], xyz[1], xyz[2]);
					worldObj.removeBlockTileEntity(xyz[0], xyz[1], xyz[2] - 1);
					worldObj.setBlockAndMetadata(xyz[0], xyz[1], xyz[2] - 1, VirusMod.blockVirusStub.blockID, virusType);
					blockPositions.add(new int[]{xyz[0], xyz[1], xyz[2] - 1});
					degradationLeft -= VirusMod.VIRUS_DEGRADATION;
				}
			}

			if(worldObj.blockExists(xyz[0], xyz[1], xyz[2] + 1) && worldObj.rand.nextBoolean() || replaceWith == worldObj.getBlockId(xyz[0], xyz[1], xyz[2] + 1)) {
				boolean flag = false;
				for(int i = 0; i < VirusMod.untouchable.length; i++)
					if(VirusMod.untouchable[i] == worldObj.getBlockId(xyz[0], xyz[1], xyz[2] + 1))
						flag = true;

				if(!flag) {
					degradeBasedOffBlock(xyz[0], xyz[1], xyz[2]);
					worldObj.removeBlockTileEntity(xyz[0], xyz[1], xyz[2] + 1);
					worldObj.setBlockAndMetadata(xyz[0], xyz[1], xyz[2] + 1, VirusMod.blockVirusStub.blockID, virusType);
					blockPositions.add(new int[]{xyz[0], xyz[1], xyz[2] + 1});
					degradationLeft -= VirusMod.VIRUS_DEGRADATION;
				}
			}
			if(slot != 0) {
				if(dropsResource)
					dropResource(xyz[0], xyz[1], xyz[2]);
				worldObj.setBlockAndMetadataWithNotify(xyz[0], xyz[1], xyz[2], replaceWith, replaceWithMeta);
				blockPositions.remove(xyz);
			} else if(!hasHiddenHost && worldObj.rand.nextBoolean()) {
				worldObj.setBlockMetadataWithNotify(xyz[0], xyz[1], xyz[2], 1);
				worldObj.markBlockForUpdate(xyz[0], xyz[1], xyz[2]); // Apparently required in order to get it to re-render with the new texture.
				hasHiddenHost = true;
			}
			degradationLeft -= VirusMod.VIRUS_DEGRADATION; // Eventual degradation.
		}
	}

	/**
	 * Degrade the virus based on the block it's destroyed, if enabled. It's not the way I want to do it but seems to be the only easy way right now. (getBlockHardness(x, y, z) returns whatever block it was called from, not the location's block)
	 */
	public void degradeBasedOffBlock(int x, int y, int z) {
		if(VirusMod.useBlockResistance) {
			Block b = Block.blocksList[worldObj.getBlockId(x, y, z)];
			if(b != null)
				degradationLeft -= b.getBlockHardness(worldObj, x, y, z);
		}
	}
	
	public void dropResource(int x, int y, int z) {
		Block b = Block.blocksList[worldObj.getBlockId(x, y, z)];
		if(b != null)
			b.dropBlockAsItem(worldObj, x, y, z, worldObj.getBlockMetadata(x, y, z), 0);
	}

	/**
	 ** Every * server ticks, actually tick.
	 **/
	public int tickRate() {
		return VirusMod.virusTickRate;
	}

	/**
	 * Cleanup!
	 */
	@Override
	public void invalidate() {
		super.invalidate();
		for(int i = 0; i < blockPositions.size(); i++) {
			int[] xyz = blockPositions.get(i);
			worldObj.setBlockAndMetadataWithNotify(xyz[0], xyz[1], xyz[2], 0, 0);
		}
		blockPositions.clear();
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("VirusType", virusType);
		compound.setInteger("ReplaceWith", replaceWith);
		compound.setInteger("ReplaceWithMeta", replaceWithMeta);
		compound.setBoolean("DropsResource", dropsResource);
		compound.setFloat("DegradationLeft", degradationLeft);
		compound.setBoolean("HasHiddenHost", hasHiddenHost);

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
		virusType = compound.getInteger("VirusType");
		replaceWith = compound.getInteger("ReplaceWith");
		replaceWithMeta = compound.getInteger("ReplaceWithMeta");
		dropsResource = compound.getBoolean("DropsResource");
		degradationLeft = compound.getFloat("DegradationLeft");
		hasHiddenHost = compound.getBoolean("HasHiddenHost");

		NBTTagList coordList = compound.getTagList("BlockPositions");
		for(int i = 0; i < coordList.tagCount(); i++) {
			NBTTagCompound values = (NBTTagCompound) coordList.tagAt(i);
			blockPositions.add(new int[]{values.getInteger("X"), values.getInteger("Y"), values.getInteger("Z")});
		}
	}
}
