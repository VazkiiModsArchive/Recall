package vazkii.recall.common;

import java.util.Random;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class ItemReturnScroll extends ItemRecallBase {

	protected ItemReturnScroll(int par1) {
		super(par1, "Return Scroll");
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		if (!hasTag(par3EntityPlayer, RecallConfig.sharedRecalls, par1ItemStack.getItemDamage(), BindType.SCROLL, "posX")) {
			RecallPacketHandler.sendMessagePacket(par3EntityPlayer, RecallReference.UNBOUND_MESSAGE, par1ItemStack.getItemDamage());
			return par1ItemStack;
		}

		int dim = getInt(par3EntityPlayer, RecallConfig.sharedRecalls, par1ItemStack.getItemDamage(), BindType.SCROLL, "DIM");

		if (dim != par3EntityPlayer.dimension) {
			RecallPacketHandler.sendMessagePacket(par3EntityPlayer, RecallReference.RETURN_FAIL_MESSAGE, par1ItemStack.getItemDamage());
			return par1ItemStack;
		}

		Random rand = new Random();

		double posX = getDouble(par3EntityPlayer, RecallConfig.sharedRecalls, par1ItemStack.getItemDamage(), BindType.SCROLL, "posX");
		double posY = getDouble(par3EntityPlayer, RecallConfig.sharedRecalls, par1ItemStack.getItemDamage(), BindType.SCROLL, "posY");
		double posZ = getDouble(par3EntityPlayer, RecallConfig.sharedRecalls, par1ItemStack.getItemDamage(), BindType.SCROLL, "posZ");
		double motionX = getDouble(par3EntityPlayer, RecallConfig.sharedRecalls, par1ItemStack.getItemDamage(), BindType.SCROLL, "motionX");
		double motionY = getDouble(par3EntityPlayer, RecallConfig.sharedRecalls, par1ItemStack.getItemDamage(), BindType.SCROLL, "motionY");
		double motionZ = getDouble(par3EntityPlayer, RecallConfig.sharedRecalls, par1ItemStack.getItemDamage(), BindType.SCROLL, "motionZ");

		par2World.playSoundAtEntity(par3EntityPlayer, "mob.endermen.portal", 1.0F, rand.nextFloat());
		par3EntityPlayer.setPosition(posX, posY, posZ);
		par3EntityPlayer.motionX = motionX;
		par3EntityPlayer.motionY = motionY;
		par3EntityPlayer.motionZ = motionZ;

		RecallPacketHandler.sendMessagePacket(par3EntityPlayer, RecallReference.RETURN_SUCCESS_MESSAGE, par1ItemStack.getItemDamage());
		for (int i = 0; i < RecallReference.PARTICLE_COUNT; i++)
			RecallPacketHandler.sendParticlePacket(par1ItemStack.getItemDamage(), par3EntityPlayer.posX + (rand.nextDouble() - 0.5D) * par3EntityPlayer.width, par3EntityPlayer.posY + rand.nextDouble() * par3EntityPlayer.height - 0.25D, par3EntityPlayer.posZ + (rand.nextDouble() - 0.5D) * par3EntityPlayer.width, par3EntityPlayer.dimension);
		par2World.playSoundAtEntity(par3EntityPlayer, "mob.endermen.portal", 1.0F, rand.nextFloat());

		return par1ItemStack;
	}

	@Override
	public int getColorFromDamage(int par1, int par2) {
		return par2 == 0 ? 0xCCCCCC : super.getColorFromDamage(par1, par2);
	}

}
