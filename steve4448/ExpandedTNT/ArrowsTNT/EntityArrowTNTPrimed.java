package steve4448.ExpandedTNT.ArrowsTNT;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityArrowTNTPrimed extends Entity {
	public int fuse;
	
	public EntityArrowTNTPrimed(World par1World) {
		super(par1World);
		this.preventEntitySpawning = true;
		this.setSize(0.98F, 0.98F);
		this.yOffset = this.height / 2.0F;
		float var8 = (float)(Math.random() * Math.PI * 2.0D);
		this.motionX = (double)(-((float)Math.sin((double)var8)) * 0.02F);
		this.motionY = 0.20000000298023224D;
		this.motionZ = (double)(-((float)Math.cos((double)var8)) * 0.02F);
		this.fuse = 80;
		this.prevPosX = posX;
		this.prevPosY = posY;
		this.prevPosZ = posZ;
	}
	
	@Override
	public void onUpdate() {
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		motionY -= 0.03999999910593033D;
		moveEntity(motionX, motionY, motionZ);
		motionX *= 0.9800000190734863D;
		motionY *= 0.9800000190734863D;
		motionZ *= 0.9800000190734863D;
		
		if(onGround) {
			motionX *= 0.699999988079071D;
			motionZ *= 0.699999988079071D;
			motionY *= -0.5D;
		}
		
		if(fuse-- <= 0) {
			setDead();
			
			if(!worldObj.isRemote) {
				this.worldObj.createExplosion((Entity)null, this.posX, this.posY, this.posZ, 1f, true);
				for(int i = 0; i < 8; i++) { // 8 logically make sense based on
												// recipe.
					EntityArrow entityarrow = new EntityArrow(worldObj, posX, posY, posZ);
					entityarrow.setVelocity(rand.nextDouble() * 1.0D - rand.nextDouble() * 1.0D, 0.1D + rand.nextDouble() * 0.9D, rand.nextDouble() * 1.0D - rand.nextDouble() * 1.0D);
					worldObj.spawnEntityInWorld(entityarrow);
					worldObj.playSoundAtEntity(entityarrow, "random.bow", 1.0F, 1.0F);
				}
			}
		} else {
			worldObj.spawnParticle("smoke", posX, posY + 0.5D, posZ, 0.0D, 0.0D, 0.0D);
		}
	}
	
	protected void entityInit() {}
	
	protected boolean canTriggerWalking() {
		return false;
	}
	
	public boolean canBeCollidedWith() {
		return !this.isDead;
	}
	
	protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
		par1NBTTagCompound.setByte("Fuse", (byte)this.fuse);
	}
	
	protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
		this.fuse = par1NBTTagCompound.getByte("Fuse");
	}
	
	@SideOnly(Side.CLIENT)
	public float getShadowSize() {
		return 0.0F;
	}
}
