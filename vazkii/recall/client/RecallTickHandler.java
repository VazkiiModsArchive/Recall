package vazkii.recall.client;

import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.ScaledResolution;
import vazkii.codebase.common.CommonUtils;
import vazkii.recall.common.RecallReference;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class RecallTickHandler implements ITickHandler {

	static String renderingString = "";
	static int ticksRemainderForRender = 0;
	static int colorIndex;

	public static void set(String string, int color) {
		renderingString = string;
		colorIndex = color;
		ticksRemainderForRender = RecallReference.MESSAGE_TIME;
	}

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		if (type.equals(EnumSet.of(TickType.CLIENT))) clientTick(tickData);
		else if (type.equals(EnumSet.of(TickType.RENDER))) renderTick(tickData);
	}

	public void clientTick(Object... tickData) {
		if (ticksRemainderForRender > 0) --ticksRemainderForRender;
		else renderingString = "";
	}

	public void renderTick(Object... tickData) {
		if (renderingString == "") return;

		Minecraft mc = CommonUtils.getMc();
		if (mc.currentScreen != null) return;

		FontRenderer font = mc.fontRenderer;
		ScaledResolution resolution = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
		int x = resolution.getScaledWidth() / 2 - font.getStringWidth(renderingString) / 2;
		int y = resolution.getScaledHeight() / 2 - RecallReference.MESSAGE_RENDER_OFFSET;
		font.drawStringWithShadow(renderingString, x, y, CommonUtils.parseHexString(RecallReference.COLORS[colorIndex].toString()));
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.CLIENT, TickType.RENDER);
	}

	@Override
	public String getLabel() {
		return "Recall";
	}

}
