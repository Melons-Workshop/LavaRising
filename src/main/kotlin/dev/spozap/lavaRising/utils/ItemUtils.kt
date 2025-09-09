package dev.spozap.lavaRising.utils
import dev.spozap.lavaRising.LavaRising
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

object ItemUtils {

    val WAND_NAMESPACED_KEY = NamespacedKey(LavaRising.INSTANCE, "lavarising_wand")

    fun createWand(): ItemStack {
        val wand = ItemStack(Material.WOODEN_AXE)
        val wandMeta = wand.itemMeta

        wandMeta.displayName(MiniMessage.miniMessage().deserialize("<red>Wand</red>"))

        val pdc = wandMeta.persistentDataContainer
        pdc.set(WAND_NAMESPACED_KEY, PersistentDataType.STRING, "")

        wand.itemMeta = wandMeta

        return  wand
    }

    fun isWand(item: ItemStack): Boolean {

        if (item.type != Material.WOODEN_AXE) {
            return  false
        }

        val itemMeta = item.itemMeta
        val pdc = itemMeta.persistentDataContainer

        return pdc.get(WAND_NAMESPACED_KEY, PersistentDataType.STRING) != null

    }

}