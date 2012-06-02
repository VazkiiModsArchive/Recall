package net.minecraft.src;

import net.minecraft.src.forge.ITextureProvider;

public abstract class ItemRecallBase extends Item{

	public static String[] prefixes = new String[]{
			"White", "Orange", "Magenta", "Light Blue", "Yellow", "Lime", "Pink", "Gray", "Light Gray", "Cyan", "Purple", "Blue", "Brown", "Green", "Red", "Black"
	};
	private String name;
	
	public ItemRecallBase(int par1, String name) {
		super(par1);
		this.name = name;
		setMaxStackSize(1);
		setHasSubtypes(true);
	}
	
    public String getItemDisplayName(ItemStack par1ItemStack)
    {
    	return prefixes[par1ItemStack.getItemDamage()] + " " + name;
    }
	
    public int getColorFromDamage(int par1, int par2)
    {
        return par2 == 0 ? 0xFFFFFF : mod_Recall.colors[par1];
    }
    
    public boolean func_46058_c()
    {
        return true;
    }
	
    public int func_46057_a(int par1, int par2)
    {
        return par2 > 0 ? mod_Recall.overlayTex : super.func_46057_a(par1, par2);
    }
}
