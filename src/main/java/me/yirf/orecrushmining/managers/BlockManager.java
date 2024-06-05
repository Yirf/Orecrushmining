package me.yirf.orecrushmining.managers;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.util.Vector3i;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerBlockBreakAnimation;
import me.yirf.orecrushmining.Orecrushmining;
import me.yirf.orecrushmining.configs.Blocks;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.security.SecureRandom;
import java.util.Random;

public class BlockManager {

    public static void sendProtocolBlockBreak(Location loc, Player player, int stage) {
        int id = new SecureRandom().nextInt(2100000);
        WrapperPlayServerBlockBreakAnimation animWrapper = new WrapperPlayServerBlockBreakAnimation(id, new Vector3i((int)loc.getX(), (int)loc.getY(), (int)loc.getZ()), (byte)stage);
        PacketEvents.getAPI().getPlayerManager().sendPacket(player, animWrapper);
    }

//    public static void sendProtocolBlockBreak(Location loc, Player player, int stage)
//    {
//        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
//
//        PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.BLOCK_BREAK_ANIMATION);
//
//        BlockPosition pos = new BlockPosition(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
//
//        int i = new Random().nextInt(2100000);
//
//        packet.getBlockPositionModifier().write(0, pos);
//        packet.getIntegers().write(0, i);
//        packet.getIntegers().write(1, stage);
//        protocolManager.sendServerPacket(player, packet);
//    }

    public static int getMaxBlockHealth(Block block) {
        return Blocks.Config.blockHealth(block.getType().name().toString());
    }


    public static int getBlockId(ItemStack block) {
        return 1;
    }
}
