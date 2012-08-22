package vazkii.recall.common;

import net.minecraft.src.ItemStack;
import vazkii.codebase.common.VazkiiUpdateHandler;
import cpw.mods.fml.common.Mod;

public class RecallUpdateHandler extends VazkiiUpdateHandler {

	public RecallUpdateHandler(Mod m) {
		super(m);
	}

	@Override
	public String getModName() {
		return "Recall";
	}

	@Override
	public String getUMVersion() {
		return RecallReference.VERSION;
	}

	@Override
	public String getUpdateURL() {
		return RecallReference.UPDATE_URL;
	}

	@Override
	public String getChangelogURL() {
		return RecallReference.CHANGELOG_URL;
	}

	int elapsedChecks = 0;
	int damage = 0;

	@Override
	public ItemStack getIconStack() {
		if (++elapsedChecks % 80 == 0) damage = damage == 15 ? 0 : ++damage;
		return new ItemStack(mod_Recall.recallScroll, 1, damage);
	}

}
