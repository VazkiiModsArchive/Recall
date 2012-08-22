package vazkii.recall.client;

import net.minecraft.src.EntityPortalFX;
import net.minecraft.src.World;
import vazkii.codebase.client.ColorRGB;
import vazkii.codebase.common.CommonUtils;

public class EntityRecallFX extends EntityPortalFX {

	public EntityRecallFX(World par1World, double par2, double par4, double par6, ColorRGB color) {
		super(par1World, par2, par4, par6, 0F, 0F, 0F);
		particleRed = color.getRedF();
		particleGreen = color.getGreenF();
		particleBlue = color.getBlueF();
		CommonUtils.getMc().effectRenderer.addEffect(this);
	}

}
