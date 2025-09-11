package dev.spozap.lavaRising.listeners

import dev.spozap.lavaRising.managers.CurrentGameManager
import dev.spozap.lavaRising.models.GameState
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent

class PlayerEvents : Listener {

    @EventHandler
    fun onPlayerDeath(e: PlayerDeathEvent) {
        val player = e.player

        val currentGame = CurrentGameManager.currentGame ?: return
        if (currentGame.state != GameState.RUNNING) return

        val deathLocation = player.location

        if (currentGame.arena.isInside(deathLocation)) {
            CurrentGameManager.registerDeath(player)
        }
    }
}
