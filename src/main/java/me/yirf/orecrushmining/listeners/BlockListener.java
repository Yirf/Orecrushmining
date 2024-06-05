package me.yirf.orecrushmining.listeners;

import io.github.itzispyder.pdk.events.CustomListener;
import me.yirf.orecrushmining.Orecrushmining;
import me.yirf.orecrushmining.managers.BlockManager;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockDamageAbortEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BlockListener implements CustomListener {

    private final JavaPlugin plugin;
    private final Map<UUID, BlockHealthInfo> blockHealthMap;

    public BlockListener(JavaPlugin plugin) {
        this.plugin = plugin;
        this.blockHealthMap = new HashMap<>();
    }

    @EventHandler
    public void onBlockDamageAbort(BlockDamageAbortEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();

        if (blockHealthMap.containsKey(playerId)) {
            blockHealthMap.remove(playerId);
        }
    }

    @EventHandler
    public void onBlockDamage(BlockDamageEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        UUID playerId = player.getUniqueId();

        if (blockHealthMap.containsKey(playerId)) {
            error(player, "&4[!] &cThats weird! Contact an admin for help...");
            return;
        }


        int maxHealth = BlockManager.getMaxBlockHealth(block);
        if (maxHealth <= 0) {
            return;
        }

        int stages = 9;
        int stage = Math.max(1, stages / maxHealth); // Calculate stage health

        blockHealthMap.put(playerId, new BlockHealthInfo(block, maxHealth));

        Thread loopThread = new Thread(() -> {
            synchronized (BlockListener.class) {
                BlockHealthInfo healthInfo = blockHealthMap.get(playerId);
                if (healthInfo == null) return;

                int currentHealth = healthInfo.getCurrentHealth();
                for (int i = 0; i < stages && currentHealth > 0; i++) {
                    if (!blockHealthMap.containsKey(playerId)) break;

                    currentHealth -= stage;
                    healthInfo.setCurrentHealth(currentHealth);

                    BlockManager.sendProtocolBlockBreak(block.getLocation(), player, (stage * (i+1)));

                    try {
                        long delay = calculateStageDelay(i, maxHealth);
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                block.getLocation().getBlock().setType(block.getType());
                blockHealthMap.remove(playerId);
            }
        });

        loopThread.start();
    }

    private long calculateStageDelay(int stageIndex, int maxHealth) {
        long totalTime = 5000;

        double stageProgress = (double) (stageIndex + 1) / 9.0;
        double stageTime = stageProgress * totalTime;

        long delay = (long) (stageTime * (maxHealth / 22.0));

        return delay;
    }


    private static class BlockHealthInfo {
        private final Block block;
        private int currentHealth;

        public BlockHealthInfo(Block block, int maxHealth) {
            this.block = block;
            this.currentHealth = maxHealth;
        }

        public int getCurrentHealth() {
            return currentHealth;
        }

        public void setCurrentHealth(int currentHealth) {
            this.currentHealth = currentHealth;
        }
    }
}
