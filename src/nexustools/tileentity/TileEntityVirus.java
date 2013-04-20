package nexustools.tileentity;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import nexustools.VirusMod;
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

	/**
	 * Initialization, essentially sets the <i>virusType</i> specific settings to process with. All of this information is saved.
	 */
	public void init(int virusType, int replaceWith, int replaceWithMeta, float degradationLeft, int x, int y, int z, boolean dropsResource) {
		this.virusType = virusType;
		this.replaceWith = replaceWith;
		this.replaceWithMeta = replaceWithMeta;
		this.degradationLeft = degradationLeft;
		this.dropsResource = dropsResource;
		blockPositions.add(new int[] { x, y, z });
	}

	/**
	 * Le logic
	 */
	@Override
	public void updateEntity() {
		if(!worldObj.isRemote)
			if(curTicks++ >= VirusMod.virusTickRate) {
				if(degradationLeft <= 0 && blockPositions.size() == 1 || blockPositions.size() < 1)
					invalidate();
				else
					for(int i = 0; i < VirusMod.virusIterationsPerTick; i++)
						handleBlock(worldObj.rand.nextInt(blockPositions.size()));
				curTicks = 0;
			}
	}

	/**
	 * Handle the VirusBlock at index <i>slot</i>
	 */
	public void handleBlock(int slot) {
		if(!worldObj.isRemote) {
			if(slot < 0 || slot >= blockPositions.size()) {
				FMLLog.severe("Incorrect handleBlock slot. Slot " + slot + ", List Size " + blockPositions.size() + ".");
				return;
			}
			int[] xyz = blockPositions.get(slot);

			if(degradationLeft <= 0 && slot > 0) {
				worldObj.setBlockAndMetadataWithNotify(xyz[0], xyz[1], xyz[2], replaceWith, replaceWithMeta);
				blockPositions.remove(xyz);
				return;
			}
			processBlock(xyz[0] + 1, xyz[1], xyz[2]);
			processBlock(xyz[0] - 1, xyz[1], xyz[2]);
			processBlock(xyz[0], xyz[1] + 1, xyz[2]);
			processBlock(xyz[0], xyz[1] - 1, xyz[2]);
			processBlock(xyz[0], xyz[1], xyz[2] + 1);
			processBlock(xyz[0], xyz[1], xyz[2] - 1);
			if(slot != 0) {
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
	 * Actually process/spread to this block at <i>x</i> <i>y</i> <i>z</i>.
	 */
	public void processBlock(int x, int y, int z) {
		if(worldObj.blockExists(x, y, z) && worldObj.rand.nextBoolean()) {
			Block b = Block.blocksList[worldObj.getBlockId(x, y, z)];
			boolean flag = (replaceWith == worldObj.getBlockId(x, y, z) && replaceWithMeta == worldObj.getBlockMetadata(x, y, z));
			if(!flag)
				for(int i = 0; i < VirusMod.untouchable.length; i++)
					if(VirusMod.untouchable[i] == worldObj.getBlockId(x, y, z))
						flag = true;

			if(!flag) {
				if(dropsResource)
					b.dropBlockAsItem(worldObj, x, y, z, worldObj.getBlockMetadata(x, y, z), 0);
				if(VirusMod.useBlockResistance)
					degradationLeft -= b.getBlockHardness(worldObj, x, y, z);
				worldObj.removeBlockTileEntity(x, y, z);
				worldObj.setBlockAndMetadata(x, y, z, VirusMod.blockVirusStub.blockID, virusType);
				blockPositions.add(new int[] { x, y, z });
				degradationLeft -= VirusMod.VIRUS_DEGRADATION;
			}
		}
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
			blockPositions.add(new int[] { values.getInteger("X"), values.getInteger("Y"), values.getInteger("Z") });
		}
	}
}
