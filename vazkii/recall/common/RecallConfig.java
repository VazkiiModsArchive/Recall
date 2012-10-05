package vazkii.recall.common;

import java.io.File;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

public class RecallConfig extends Configuration {

	// Props Start
	// ============================================================================
	static boolean oldschoolMode = false;
	static boolean sharedRecalls = false;

	// Props End
	// ==============================================================================

	public RecallConfig(File file) {
		super(file);
		categories.remove(CATEGORY_BLOCK);

		load();

		mod_Recall.recallScroll = new ItemRecallScroll(getOrCreateIntProperty("recallScrollID", CATEGORY_ITEM, 4452).getInt(4452)).setIconIndex(1);
		mod_Recall.recallBindstone = new ItemRecallBindstone(getOrCreateIntProperty("recallBindstoneID", CATEGORY_ITEM, 4453).getInt(4453)).setIconIndex(0);
		mod_Recall.returnScroll = new ItemReturnScroll(getOrCreateIntProperty("returnScrollID", CATEGORY_ITEM, 4454).getInt(4454)).setIconIndex(1);

		Property propOldSchoolMode = getOrCreateBooleanProperty("oldSchoolMode", CATEGORY_GENERAL, false);
		propOldSchoolMode.comment = "Set to true to enable oldschool mode, where the recipes are similar to the ones of the original mod.";
		oldschoolMode = propOldSchoolMode.getBoolean(false);

		Property propSharedRecalls = getOrCreateBooleanProperty("sharedRecalls", CATEGORY_GENERAL, false);
		propSharedRecalls.comment = "Set to true to make the recall points shared between the players.";
		sharedRecalls = propSharedRecalls.getBoolean(false);

		save();
	}

}
