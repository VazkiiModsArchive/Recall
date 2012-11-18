package vazkii.recall.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import vazkii.codebase.client.ClientUtils;
import vazkii.codebase.common.CommonUtils;
import vazkii.recall.client.EntityRecallFX;
import vazkii.recall.client.RecallTickHandler;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.INetworkManager;
import net.minecraft.src.Packet250CustomPayload;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class RecallPacketHandler implements IPacketHandler {

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		if (!CommonUtils.getSide().isClient()) return;

		if (packet.channel == "recall_Vz") onMessagePacket(manager, packet, player);
		else onParticlePacket(manager, packet, player);
	}

	public void onMessagePacket(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		DataInputStream dataStream = new DataInputStream(new ByteArrayInputStream(packet.data));
		try {
			int color = dataStream.readInt();
			String message = dataStream.readUTF();
			RecallTickHandler.set(message, color);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void onParticlePacket(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		DataInputStream dataStream = new DataInputStream(new ByteArrayInputStream(packet.data));
		try {
			int dim = dataStream.readInt();
			EntityPlayer entityPlayer = ClientUtils.getClientPlayer();
			if (dim != entityPlayer.dimension) return;

			int color = dataStream.readInt();
			double x = dataStream.readDouble();
			double y = dataStream.readDouble();
			double z = dataStream.readDouble();

			new EntityRecallFX(entityPlayer.worldObj, x, y, z, RecallReference.COLORS[color]);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void sendMessagePacket(EntityPlayer player, String message, int color) {
		Packet250CustomPayload packet = new Packet250CustomPayload();
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		DataOutputStream data = new DataOutputStream(byteStream);

		EntityPlayerMP mpPlayer = CommonUtils.getServer().getConfigurationManager().getPlayerForUsername(player.username);

		try {
			data.writeInt(color);
			data.writeUTF(message);
		} catch (IOException e) {
			e.printStackTrace();
		}

		packet.channel = "recall_Vz";
		packet.data = byteStream.toByteArray();
		packet.length = packet.data.length;

		mpPlayer.playerNetServerHandler.sendPacketToPlayer(packet);
	}

	public static void sendParticlePacket(int color, double x, double y, double z, int dim) {
		Packet250CustomPayload packet = new Packet250CustomPayload();
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		DataOutputStream data = new DataOutputStream(byteStream);

		try {
			data.writeInt(dim);
			data.writeInt(color);
			data.writeDouble(x);
			data.writeDouble(y);
			data.writeDouble(z);
		} catch (IOException e) {
			e.printStackTrace();
		}

		packet.channel = "recall1_Vz";
		packet.data = byteStream.toByteArray();
		packet.length = packet.data.length;

		CommonUtils.getServer().getConfigurationManager().sendPacketToAllPlayers(packet);
	}
}
