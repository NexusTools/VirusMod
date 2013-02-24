package steve4448.ExpandedTNT.TNTTNT;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityTNTTNTPrimed extends Entity {
	public int fuse;

	public EntityTNTTNTPrimed(World par1World) {
		super(par1World);
		preventEntitySpawning = true;
		setSize(0.98F, 0.98F);
		yOffset = height / 2.0F;
		float var8 = (float) (Math.random() * Math.PI * 2.0D);
		motionX = -((float) Math.sin(var8)) * 0.02F;
		motionY = 0.20000000298023224D;
		motionZ = -((float) Math.cos(var8)) * 0.02F;
		fuse = 80;
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
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
				worldObj.createExplosion((Entity) null, posX, posY, posZ, 1f, true);
				for(int i = 0; i < 8; i++) { // 8 logically make sense based on
												// recipe.
					EntityTNTPrimed entityTNTPrimed = new EntityTNTPrimed(worldObj, posX, posY, posZ);
					entityTNTPrimed.setVelocity(rand.nextDouble() * 1.0D - rand.nextDouble() * 1.0D, 0.1D + rand.nextDouble() * 0.9D, rand.nextDouble() * 1.0D - rand.nextDouble() * 1.0D);
					worldObj.spawnEntityInWorld(entityTNTPrimed);
					worldObj.playSoundAtEntity(entityTNTPrimed, "random.bow", 1.0F, 1.0F);
				}
			}
		} else
			worldObj.spawnParticle("smoke", posX, posY + 0.5D, posZ, 0.0D, 0.0D, 0.0D);
	}

	@Override
	protected void entityInit() {}

	@Override
	protected boolean canTriggerWalking() {
		return false;
	}

	@Override
	public boolean canBeCollidedWith() {
		return !isDead;
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
		par1NBTTagCompound.setByte("Fuse", (byte) fuse);
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
		fuse = par1NBTTagCompound.getByte("Fuse");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getShadowSize() {
		return 0.0F;
	}
}
