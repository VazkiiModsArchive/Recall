package net.minecraft.src;

import codechicken.nei.API;
import codechicken.nei.IConfigureNEI;
import codechicken.nei.MultiItemRange;

public class NEIRecallConfig implements IConfigureNEI{

	public void loadConfig() {
		MultiItemRange r = new MultiItemRange();
		r.add(mod_Recall.rBindstone);
		API.addSetRange("Vazkii Mods.Recall.Bindstones", r);
		
		MultiItemRange r1 = new MultiItemRange();
		r1.add(mod_Recall.rScroll);
		API.addSetRange("Vazkii Mods.Recall.Recall Scrolls", r1);
		
		MultiItemRange r2 = new MultiItemRange();
		r2.add(mod_Recall.rReturnScroll);
		API.addSetRange("Vazkii Mods.Recall.Return Scrolls", r2);
	}

}
