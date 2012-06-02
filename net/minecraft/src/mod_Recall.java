package net.minecraft.src;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.src.forge.MinecraftForge;
import net.minecraft.src.forge.MinecraftForgeClient;
import net.minecraft.src.vazkii.updatemanager.IUMAdvanced;
import net.minecraft.src.vazkii.updatemanager.IUpdateManager;
import net.minecraft.src.vazkii.updatemanager.ModType;
import net.minecraft.src.vazkii.updatemanager.UMCore;

public class mod_Recall extends BaseMod implements IUpdateManager, IUMAdvanced{

	public mod_Recall() {
		UMCore.addMod(this);
		ModLoader.setInGUIHook(this, true, true);
		ModLoader.setInGameHook(this, true, false);
	}

	public String getVersion() {
		return "by Vazkii. Version [1.0] for 1.2.5";
	}

	public void load() {
	}
	
	public boolean onTickInGUI(float f, Minecraft minecraft, GuiScreen guiscreen)
    {
        if((guiscreen instanceof GuiContainerCreative) && !(lastGuiOpen instanceof GuiContainerCreative) && !minecraft.theWorld.isRemote)
        {
            Container container = ((GuiContainer)guiscreen).inventorySlots;
            List itemList = ((ContainerCreative)container).itemList;
            	for(int i=0; i<16; i++)
            		itemList.add(new ItemStack(rScroll, 1, i));
            	for(int i=0; i<16; i++)        	
            		itemList.add(new ItemStack(rBindstone, 1, i));
        }
        
        lastGuiOpen = guiscreen;
        return true;
    }
	
	protected static File getWorldRecallFile(World world, int color){
		return new File(Minecraft.getAppDir((new StringBuilder()).append("minecraft/saves/").append(world.worldInfo.getWorldName()).append("/recall").toString()), "info" + color + ".dat");
	}
	
    public boolean onTickInGame(float f, Minecraft minecraft)
    {
    	if(!hasTicked){
    		hasTicked = true;
    		if(!oldschoolMode){
    			for(int i=0; i<16; i++){
    				if(ModLoader.isModLoaded("mod_WirelessRedstoneCore")){
    		        ModLoader.addRecipe(new ItemStack(rScroll, 1, i), new Object[] {
    		            "ESE", "DPD", "ESE", Character.valueOf('E'), WirelessRedstoneCore.retherPearl, Character.valueOf('S'), Item.stick, Character.valueOf('P'), Item.paper, Character.valueOf('D'), new ItemStack(Item.dyePowder, 1, BlockCloth.getBlockFromDye(i))
    		        });
    		        ModLoader.addRecipe(new ItemStack(rBindstone, 1, i), new Object[] {
    		            "ESE", "DSD", "ESE", Character.valueOf('E'), WirelessRedstoneCore.retherPearl, Character.valueOf('S'), Block.stone, Character.valueOf('D'), new ItemStack(Item.dyePowder, 1, BlockCloth.getBlockFromDye(i))
    		        });
    				}else{
    			        ModLoader.addRecipe(new ItemStack(rScroll, 1, i), new Object[] {
    			            "ESE", "DPD", "ESE", Character.valueOf('E'), Item.enderPearl, Character.valueOf('S'), Item.stick, Character.valueOf('P'), Item.paper, Character.valueOf('D'), new ItemStack(Item.dyePowder, 1, BlockCloth.getBlockFromDye(i))
    			        });
    			        ModLoader.addRecipe(new ItemStack(rBindstone, 1, i), new Object[] {
    			            "ESE", "DSD", "ESE", Character.valueOf('E'), Item.enderPearl, Character.valueOf('S'), Block.stone, Character.valueOf('D'), new ItemStack(Item.dyePowder, 1, BlockCloth.getBlockFromDye(i))
    			        });
    				}
    			}
    			for(int i=0; i<16; i++)
		        ModLoader.addRecipe(new ItemStack(rReturnScroll, 1, i), new Object[] {
		            "E", "S", "E", Character.valueOf('E'), Item.eyeOfEnder, Character.valueOf('S'), new ItemStack(rScroll, 1, i)
		        });
    			} else {
    				for(int i=0; i<16; i++){
    			        ModLoader.addRecipe(new ItemStack(rScroll, 1, i), new Object[] {
    			            " S ", "DP ", " S ", Character.valueOf('S'), Item.stick, Character.valueOf('P'), Item.paper, Character.valueOf('D'), new ItemStack(Item.dyePowder, 1, BlockCloth.getBlockFromDye(i))
    			        });
    			        ModLoader.addRecipe(new ItemStack(rScroll, 1, i), new Object[] {
    			            " S ", " PD", " S ", Character.valueOf('S'), Item.stick, Character.valueOf('P'), Item.paper, Character.valueOf('D'), new ItemStack(Item.dyePowder, 1, BlockCloth.getBlockFromDye(i))
    			        });
    			        ModLoader.addRecipe(new ItemStack(rBindstone, 1, i), new Object[] {
    			            "SSS", "SDS", "SSS", Character.valueOf('S'), Block.cobblestone, Character.valueOf('D'), new ItemStack(Item.dyePowder, 1, BlockCloth.getBlockFromDye(i))
    			        });
    				}
    			}
    	}
    	
        if(time-- > 0 && minecraft.currentScreen == null)
        {
            ScaledResolution scaledresolution = new ScaledResolution(minecraft.gameSettings, minecraft.displayWidth, minecraft.displayHeight);
            double d = (double)scaledresolution.getScaledWidth() / 2D;
            int i = (int)(d - (double)(minecraft.fontRenderer.getStringWidth(display) / 2));
            minecraft.fontRenderer.drawStringWithShadow(display, i, scaledresolution.getScaledHeight()/2 - 25, color);
        }
        return true;
    }
    
    public static void setMessage(String message, int textColor){
    	display = message;
    	color = textColor;
    	time = 60;
    }
    
	public static final int[] colors = new int[]{
		0xffffff, 0xf4b33f, 0xcb69c5, 0x82ace7, 0xe7e72a, 0x83d41c, 0x979797,
		0x979797, 0xd9d9d9, 0x3c8eb0, 0xb064d8, 0x0a2b7a, 0x4d2b15, 0x223505,
		0x790000, 0x000000
	};
	
	private static boolean hasTicked = false;
	
	private static GuiScreen lastGuiOpen;

	private static int time;
	private static String display;
	private static  int color;
	
	@MLProp public static int rScrollID = 19732;
	@MLProp public static int rBindstoneID = 19733;
	@MLProp public static int rReturnScrollID = 19734;
	@MLProp public static int rRecallHexagramItemID = 19735;
	@MLProp public static int recallHexagramID = 181;
	@MLProp public static boolean oldschoolMode = false;
	
	public static int rScrollTex = ModLoader.addOverride("/gui/items.png", "/vazkii/recall/scroll.png");
	public static int rBindstoneTex = ModLoader.addOverride("/gui/items.png", "/vazkii/recall/bindstone.png");
	public static int overlayTex = ModLoader.addOverride("/gui/items.png", "/vazkii/recall/overlay.png");
	
	public static final Item rScroll = new ItemRecallScroll(rScrollID).setIconIndex(rScrollTex).setItemName("rScroll");
	public static final Item rBindstone = new ItemRecallBindstone(rBindstoneID).setIconIndex(rBindstoneTex).setItemName("rBindstone");
	public static final Item rReturnScroll = new ItemRecallReturnScroll(rReturnScrollID).setIconIndex(rScrollTex).setItemName("rReturnScroll");
	
	public String getModName() {
		return "Recall";
	}

	public String getChangelogURL() {
		return "https://dl.dropbox.com/u/34938401/Mods/On%20Topic/Mods/Recall/Changelog.txt";
	}

	public String getUpdateURL() {
		return "https://dl.dropbox.com/u/34938401/Mods/On%20Topic/Mods/Recall/Version.txt";
	}

	public String getModURL() {
		return "http://www.minecraftforum.net/topic/528166-123-mlforge-vazkiis-mods-ebonapi-last-updated-12512/";
	}

	public ModType getModType() {
		return ModType.UNDEFINED;
	}
	


}
