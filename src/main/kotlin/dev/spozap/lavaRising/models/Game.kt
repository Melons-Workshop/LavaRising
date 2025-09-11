package dev.spozap.lavaRising.models

import net.kyori.adventure.audience.Audience
import org.bukkit.entity.Player
import java.util.*

data class Game(
    var state: GameState = GameState.WAITING,
    val arena: Arena,
    val participants: Set<Player> = emptySet(),
    val deaths: MutableSet<UUID> = mutableSetOf()
) {
    val audience: Audience = Audience.audience(participants)

    var lavaY: Int = minOf(arena.pos1.y, arena.pos2.y).toInt()

    val lavaReachedTop: Boolean
        get() = lavaY > maxOf(arena.pos1.blockY, arena.pos2.blockY)
}
