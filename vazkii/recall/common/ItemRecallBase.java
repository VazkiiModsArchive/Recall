package vazkii.recall.common;

import java.io.File;
import java.util.List;

import vazkii.codebase.common.CommonUtils;
import vazkii.codebase.common.EnumVazkiiMods;
import vazkii.codebase.common.IOUtils;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;

public class ItemRecallBase extends Item {

	public static final String[] PREFIXES = new String[] { "White", "Orange", "Magenta", "Light Blue", "Yellow", "Lime", "Pink", "Gray", "Light Gray", "Cyan", "Purple", "Blue", "Brown", "Green", "Red", "Black" };

	String name;

	protected ItemRecallBase(int par1, String name) {
		super(par1);
		setCreativeTab(CreativeTabs.tabTransport);
		setMaxStackSize(1);
		setHasSubtypes(true);
		this.name = name;
	}

	@Override
	public String getItemDisplayName(ItemStack par1ItemStack) {
		return String.format("%s %s", PREFIXES[par1ItemStack.getItemDamage()], name);
	}

	@Override
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List) {
		for (int var4 = 0; var4 < 16; ++var4)
			par3List.add(new ItemStack(par1, 1, var4));
	}

	@Override
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	public int getIconFromDamageForRenderPass(int par1, int par2) {
		return par2 > 0 ? super.getIconFromDamageForRenderPass(par1, par2) + 16 : super.getIconFromDamageForRenderPass(par1, par2);
	}

	@Override
	public int getColorFromItemStack(ItemStack par1, int par2) {
		return par2 == 0 ? 0xFFFFFF : CommonUtils.parseHexString(RecallReference.COLORS[par1.getItemDamage()].toString());
	}

	@Override
	public String getTextureFile() {
		return "/vazkii/recall/client/resources/sprites.png";
	}

	void saveInt(EntityPlayer player, boolean global, int index, BindType type, int intToSave, String tagName) {
		File cacheFile = IOUtils.getWorldCacheFile(EnumVazkiiMods.RECALL, player.worldObj);
		NBTTagCompound cmp = IOUtils.getTagCompoundInFile(cacheFile);
		String name = global ? "" : player.username + "_";
		cmp.setInteger(name + type + index + "_" + tagName, intToSave);
		IOUtils.injectNBTToFile(cmp, cacheFile);
	}

	int getInt(EntityPlayer player, boolean global, int index, BindType type, String tagName) {
		File cacheFile = IOUtils.getWorldCacheFile(EnumVazkiiMods.RECALL, player.worldObj);
		NBTTagCompound cmp = IOUtils.getTagCompoundInFile(cacheFile);
		String name = global ? "" : player.username + "_";
		return cmp.getInteger(name + type + index + "_" + tagName);
	}

	double getDouble(EntityPlayer player, boolean global, int index, BindType type, String tagName) {
		File cacheFile = IOUtils.getWorldCacheFile(EnumVazkiiMods.RECALL, player.worldObj);
		NBTTagCompound cmp = IOUtils.getTagCompoundInFile(cacheFile);
		String name = global ? "" : player.username + "_";
		return cmp.getDouble(name + type + index + "_" + tagName);
	}

	void saveDouble(EntityPlayer player, boolean global, int index, BindType type, double doubleToSave, String tagName) {
		File cacheFile = IOUtils.getWorldCacheFile(EnumVazkiiMods.RECALL, player.worldObj);
		NBTTagCompound cmp = IOUtils.getTagCompoundInFile(cacheFile);
		String name = global ? "" : player.username + "_";
		cmp.setDouble(name + type + index + "_" + tagName, doubleToSave);
		IOUtils.injectNBTToFile(cmp, cacheFile);
	}

	boolean hasTag(EntityPlayer player, boolean global, int index, BindType type, String tagName) {
		File cacheFile = IOUtils.getWorldCacheFile(EnumVazkiiMods.RECALL, player.worldObj);
		NBTTagCompound cmp = IOUtils.getTagCompoundInFile(cacheFile);
		String name = global ? "" : player.username + "_";
		return cmp.hasKey(name + type + index + "_" + tagName);
	}

	public enum BindType
	{
		BINDSTONE("recallPoint"), SCROLL("returnPoint");

		private BindType(String s) {
			name = s;
		}

		private String name;

		@Override
		public String toString() {
			return name;
		}
	}

}
