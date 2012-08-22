package vazkii.recall.common;

import java.util.Random;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class ItemRecallScroll extends ItemRecallBase {

	protected ItemRecallScroll(int par1) {
		super(par1, "Recall Scroll");
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		if (!hasTag(par3EntityPlayer, RecallConfig.sharedRecalls, par1ItemStack.getItemDamage(), BindType.BINDSTONE, "posX")) {
			RecallPacketHandler.sendMessagePacket(par3EntityPlayer, RecallReference.UNBOUND_MESSAGE, par1ItemStack.getItemDamage());
			return par1ItemStack;
		}

		int dim = getInt(par3EntityPlayer, RecallConfig.sharedRecalls, par1ItemStack.getItemDamage(), BindType.BINDSTONE, "DIM");

		if (dim != par3EntityPlayer.dimension) {
			RecallPacketHandler.sendMessagePacket(par3EntityPlayer, RecallReference.RECALL_FAIL_MESSAGE, par1ItemStack.getItemDamage());
			return par1ItemStack;
		}

		Random rand = new Random();

		saveDouble(par3EntityPlayer, RecallConfig.sharedRecalls, par1ItemStack.getItemDamage(), BindType.SCROLL, par3EntityPlayer.posX, "posX");
		saveDouble(par3EntityPlayer, RecallConfig.sharedRecalls, par1ItemStack.getItemDamage(), BindType.SCROLL, par3EntityPlayer.posY, "posY");
		saveDouble(par3EntityPlayer, RecallConfig.sharedRecalls, par1ItemStack.getItemDamage(), BindType.SCROLL, par3EntityPlayer.posZ, "posZ");
		saveDouble(par3EntityPlayer, RecallConfig.sharedRecalls, par1ItemStack.getItemDamage(), BindType.SCROLL, par3EntityPlayer.motionX, "motionX");
		saveDouble(par3EntityPlayer, RecallConfig.sharedRecalls, par1ItemStack.getItemDamage(), BindType.SCROLL, par3EntityPlayer.motionY, "motionY");
		saveDouble(par3EntityPlayer, RecallConfig.sharedRecalls, par1ItemStack.getItemDamage(), BindType.SCROLL, par3EntityPlayer.motionZ, "motionZ");
		saveInt(par3EntityPlayer, RecallConfig.sharedRecalls, par1ItemStack.getItemDamage(), BindType.SCROLL, par3EntityPlayer.dimension, "DIM");

		int posX = getInt(par3EntityPlayer, RecallConfig.sharedRecalls, par1ItemStack.getItemDamage(), BindType.BINDSTONE, "posX");
		int posY = getInt(par3EntityPlayer, RecallConfig.sharedRecalls, par1ItemStack.getItemDamage(), BindType.BINDSTONE, "posY");
		int posZ = getInt(par3EntityPlayer, RecallConfig.sharedRecalls, par1ItemStack.getItemDamage(), BindType.BINDSTONE, "posZ");

		par2World.playSoundAtEntity(par3EntityPlayer, "mob.endermen.portal", 1.0F, rand.nextFloat());
		par3EntityPlayer.setPosition(posX + 0.5, posY + 1.6000000001D, posZ + 0.5);
		par3EntityPlayer.motionY = 0;
		par3EntityPlayer.motionX = 0;
		par3EntityPlayer.motionZ = 0;

		RecallPacketHandler.sendMessagePacket(par3EntityPlayer, RecallReference.RECALL_SUCCESS_MESSAGE, par1ItemStack.getItemDamage());
		for (int i = 0; i < RecallReference.PARTICLE_COUNT; i++)
			RecallPacketHandler.sendParticlePacket(par1ItemStack.getItemDamage(), par3EntityPlayer.posX + (rand.nextDouble() - 0.5D) * par3EntityPlayer.width, par3EntityPlayer.posY + rand.nextDouble() * par3EntityPlayer.height - 0.25D, par3EntityPlayer.posZ + (rand.nextDouble() - 0.5D) * par3EntityPlayer.width, par3EntityPlayer.dimension);
		par2World.playSoundAtEntity(par3EntityPlayer, "mob.endermen.portal", 1.0F, rand.nextFloat());

		return par1ItemStack;
	}

}
