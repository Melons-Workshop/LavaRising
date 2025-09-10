package dev.spozap.lavaRising.listeners

import dev.spozap.lavaRising.managers.GameCreationManager
import dev.spozap.lavaRising.utils.ItemUtils
import dev.spozap.lavaRising.utils.mm
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot

class WandEvents : Listener {

    @EventHandler
    fun onInteract(event: PlayerInteractEvent) {
        val player = event.player
        val itemInHand = player.inventory.itemInMainHand

        if (event.hand != EquipmentSlot.HAND) {
            return
        }

        if (!ItemUtils.isWand(itemInHand)) {
            return
        }

        if (!player.hasPermission("lavarising.select")) {
            player.sendMessage(mm("<red>You cannot use LavaRising wand!</red>"))
            return
        }

        event.clickedBlock?.let {
            GameCreationManager.handleCreationByAction(player, event.action, it.location)
            event.isCancelled = true
        }

    }

}