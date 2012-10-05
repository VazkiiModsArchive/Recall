package vazkii.recall.common;

import net.minecraft.src.Block;
import net.minecraft.src.BlockCloth;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import vazkii.codebase.common.EnumVazkiiMods;
import vazkii.codebase.common.IOUtils;
import vazkii.recall.client.RecallTickHandler;
import vazkii.um.common.ModConverter;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.TickRegistry;

@Mod(modid = "recall_Vz", name = "Recall", version = "by Vazkii. Version [2.0.1] for 1.3.2")
@NetworkMod(channels = { "recall_Vz", "recall1_Vz" }, packetHandler = RecallPacketHandler.class, clientSideRequired = true)
public class mod_Recall {

	@Init
	public void onInit(FMLInitializationEvent event) {
		new RecallConfig(IOUtils.getConfigFile(EnumVazkiiMods.RECALL));
		new RecallUpdateHandler(ModConverter.getMod(getClass()));

		TickRegistry.registerTickHandler(new RecallTickHandler(), Side.CLIENT);

		if (!RecallConfig.oldschoolMode) for (int i = 0; i < 16; i++) {
			ModLoader.addRecipe(new ItemStack(recallScroll, 1, i), "ESE", "DPD", "ESE", Character.valueOf('E'), Item.enderPearl, Character.valueOf('S'), Item.stick, Character.valueOf('P'), Item.paper, Character.valueOf('D'), new ItemStack(Item.dyePowder, 1, BlockCloth.getBlockFromDye(i)));
			ModLoader.addRecipe(new ItemStack(recallBindstone, 1, i), "ESE", "DSD", "ESE", Character.valueOf('E'), Item.enderPearl, Character.valueOf('S'), Block.stone, Character.valueOf('D'), new ItemStack(Item.dyePowder, 1, BlockCloth.getBlockFromDye(i)));
			ModLoader.addRecipe(new ItemStack(returnScroll, 1, i), new Object[] { "E", "S", "E", Character.valueOf('E'), Item.eyeOfEnder, Character.valueOf('S'), new ItemStack(recallScroll, 1, i) });
		}
		else for (int i = 0; i < 16; i++) {
			ModLoader.addRecipe(new ItemStack(recallScroll, 1, i), new Object[] { " S ", "DP ", " S ", Character.valueOf('S'), Item.stick, Character.valueOf('P'), Item.paper, Character.valueOf('D'), new ItemStack(Item.dyePowder, 1, BlockCloth.getBlockFromDye(i)) });
			ModLoader.addRecipe(new ItemStack(recallScroll, 1, i), new Object[] { " S ", " PD", " S ", Character.valueOf('S'), Item.stick, Character.valueOf('P'), Item.paper, Character.valueOf('D'), new ItemStack(Item.dyePowder, 1, BlockCloth.getBlockFromDye(i)) });
			ModLoader.addRecipe(new ItemStack(recallBindstone, 1, i), new Object[] { "SSS", "SDS", "SSS", Character.valueOf('S'), Block.cobblestone, Character.valueOf('D'), new ItemStack(Item.dyePowder, 1, BlockCloth.getBlockFromDye(i)) });

		}

	}

	public static Item recallScroll;
	public static Item recallBindstone;
	public static Item returnScroll;

}
