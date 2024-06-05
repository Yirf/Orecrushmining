package me.yirf.orecrushmining.managers;

import me.yirf.orecrushmining.Orecrushmining;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ToolManager {

    public static boolean isTool(ItemStack item) {
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta.getCustomModelData() == 1111) {
            return true;
        }
        return false;
    }
}

