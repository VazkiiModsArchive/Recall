package vazkii.recall.common;

import java.util.Random;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class ItemRecallBindstone extends ItemRecallBase {

	protected ItemRecallBindstone(int par1) {
		super(par1, "Bindstone");
	}

	@Override
	public boolean tryPlaceIntoWorld(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
		Random r = new Random();
		saveInt(par2EntityPlayer, RecallConfig.sharedRecalls, par1ItemStack.getItemDamage(), BindType.BINDSTONE, par4, "posX");
		saveInt(par2EntityPlayer, RecallConfig.sharedRecalls, par1ItemStack.getItemDamage(), BindType.BINDSTONE, par5, "posY");
		saveInt(par2EntityPlayer, RecallConfig.sharedRecalls, par1ItemStack.getItemDamage(), BindType.BINDSTONE, par6, "posZ");
		saveInt(par2EntityPlayer, RecallConfig.sharedRecalls, par1ItemStack.getItemDamage(), BindType.BINDSTONE, par2EntityPlayer.dimension, "DIM");

		RecallPacketHandler.sendMessagePacket(par2EntityPlayer, RecallReference.BINDSTONE_MESSAGE, par1ItemStack.getItemDamage());
		for (int i = 0; i < RecallReference.PARTICLE_COUNT; i++)
			RecallPacketHandler.sendParticlePacket(par1ItemStack.getItemDamage(), par4 + r.nextDouble(), par5 + r.nextDouble() + 0.5, par6 + r.nextDouble(), par2EntityPlayer.dimension);
		par3World.playSoundEffect(par4, par5, par6, "mob.endermen.portal", 1.0F, r.nextFloat());

		return true;
	}

}
