package dev.spozap.lavaRising.models

import net.kyori.adventure.audience.Audience
import org.bukkit.entity.Player

data class Game(
    var state: GameState = GameState.WAITING,
    val arena: Arena,
    val participants: List<Player> = emptyList(),
) {
    val audience: Audience
        get() = Audience.audience(participants)

    var lavaY: Double = minOf(arena.pos1.y, arena.pos2.y)
}