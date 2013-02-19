package steve4448.VirusMod;

import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityVirusController extends TileEntity {
	private int spread = 0;
	public TileEntityVirusController() {}
	
	@Override
    public void updateEntity() {
    	super.updateEntity();
    	
    }
	
	public void setRemainingSpread(int amt) {
		spread = amt;
	}
	
	public int getRemainingSpread() {
		return spread;
	}
}
