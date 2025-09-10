package dev.spozap.lavaRising.listeners

import dev.spozap.lavaRising.managers.CurrentGameManager
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockFromToEvent

class LavaFlowEvent : Listener {

    @EventHandler
    fun onLavaFlow(event: BlockFromToEvent) {
        val arena = CurrentGameManager.currentGame?.arena

        if (arena == null) {
            return
        }

        if (event.block.type != Material.LAVA) {
            return
        }

        if (!arena.isInside(event.block.location)) {
            return
        }

        event.isCancelled = true
    }

}