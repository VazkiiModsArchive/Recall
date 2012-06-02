package net.minecraft.src;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class ItemRecallScroll extends ItemRecallBase {

	public ItemRecallScroll(int par1) {
		super(par1, "Recall Scroll");
	}
	
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
		NBTTagCompound nbt = new NBTTagCompound();
		File file = mod_Recall.getWorldRecallFile(par2World, par1ItemStack.getItemDamage());
		try {
			nbt = CompressedStreamTools.readCompressed(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			mod_Recall.setMessage("Unbound!", mod_Recall.colors[par1ItemStack.getItemDamage()]);
			return par1ItemStack;
			} catch (IOException e) {
				mod_Recall.setMessage("Unbound!", mod_Recall.colors[par1ItemStack.getItemDamage()]);
				return par1ItemStack;
				}
		
		if(!nbt.hasKey(new StringBuilder().append(prefixes[par1ItemStack.getItemDamage()]).append("_posX").toString())){
			mod_Recall.setMessage("Unbound!", mod_Recall.colors[par1ItemStack.getItemDamage()]);
			return par1ItemStack;
		}
		
		nbt.setDouble(new StringBuilder().append(prefixes[par1ItemStack.getItemDamage()]).append("_oldPosX").toString(), par3EntityPlayer.posX);
		nbt.setDouble(new StringBuilder().append(prefixes[par1ItemStack.getItemDamage()]).append("_oldPosY").toString() , par3EntityPlayer.posY);
		nbt.setDouble(new StringBuilder().append(prefixes[par1ItemStack.getItemDamage()]).append("_oldPosZ").toString(), par3EntityPlayer.posZ);
		nbt.setInteger(new StringBuilder().append(prefixes[par1ItemStack.getItemDamage()]).append("_oldDIM").toString(), par3EntityPlayer.dimension);
		try {
			CompressedStreamTools.writeCompressed(nbt, new FileOutputStream(file));
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
		int xPos = nbt.getInteger(new StringBuilder().append(prefixes[par1ItemStack.getItemDamage()]).append("_posX").toString());
		int yPos = nbt.getInteger(new StringBuilder().append(prefixes[par1ItemStack.getItemDamage()]).append("_posY").toString());
		int zPos = nbt.getInteger(new StringBuilder().append(prefixes[par1ItemStack.getItemDamage()]).append("_posZ").toString());
		int dim = nbt.getInteger(new StringBuilder().append(prefixes[par1ItemStack.getItemDamage()]).append("_DIM").toString());
		
		if(dim != ModLoader.getMinecraftInstance().thePlayer.dimension){
			mod_Recall.setMessage("Recall Failed!", mod_Recall.colors[par1ItemStack.getItemDamage()]);
			return par1ItemStack;
		}
		
		ModLoader.getMinecraftInstance().thePlayer.setPosition(xPos+0.5, yPos+1.6000000001D, zPos+0.5); //The main reason I use this player getter insted of the parameter is so it's compatible with RedPower deployers and the like.
		Random rand = new Random();
	      for(int z=0;z<1000;z++)
          	par3EntityPlayer.worldObj.spawnParticle("portal", par3EntityPlayer.posX + (rand.nextDouble() - 0.5D) * (double)par3EntityPlayer.width, (par3EntityPlayer.posY - 2.2000000001D + rand.nextDouble() * (double)par3EntityPlayer.height) - 0.25D, par3EntityPlayer.posZ + (rand.nextDouble() - 0.5D) * (double)par3EntityPlayer.width, (rand.nextDouble() - 0.5D) * 2D, -rand.nextDouble(), (rand.nextDouble() - 0.5D) * 2D);
        par2World.playSoundEffect(par3EntityPlayer.posX, par3EntityPlayer.posY, par3EntityPlayer.posZ, "mob.endermen.portal", 1.0F, 1.0F);
		mod_Recall.setMessage("Recall Sucessful!", mod_Recall.colors[par1ItemStack.getItemDamage()]);
		
		return par1ItemStack;
    }
}
