package net.minecraft.src;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import net.minecraft.src.forge.ForgeHooks;

public class ItemRecallBindstone extends ItemRecallBase {

	public ItemRecallBindstone(int par1) {
		super(par1, "Bindstone");
	}
	
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7)
    {
    	File file = mod_Recall.getWorldRecallFile(par3World, par1ItemStack.getItemDamage());
		if(!file.exists())
			try {
				file.createNewFile();
			} catch (IOException e) {
		}
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger(new StringBuilder().append(prefixes[par1ItemStack.getItemDamage()]).append("_posX").toString(), par4);
		nbt.setInteger(new StringBuilder().append(prefixes[par1ItemStack.getItemDamage()]).append("_posY").toString() , par5+2);
		nbt.setInteger(new StringBuilder().append(prefixes[par1ItemStack.getItemDamage()]).append("_posZ").toString(), par6);
		nbt.setInteger(new StringBuilder().append(prefixes[par1ItemStack.getItemDamage()]).append("_DIM").toString(), par2EntityPlayer.dimension);
		try {
			CompressedStreamTools.writeCompressed(nbt, new FileOutputStream(file));
			 mod_Recall.setMessage("Bound!", mod_Recall.colors[par1ItemStack.getItemDamage()]);
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
		return true;
    }	

}
