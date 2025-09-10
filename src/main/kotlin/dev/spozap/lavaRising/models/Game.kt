package dev.spozap.lavaRising.models

import net.kyori.adventure.audience.Audience
import org.bukkit.entity.Player

data class Game(
    val state: GameState = GameState.WAITING,
    val arena: Arena,
    val participants: List<Player> = emptyList()
) {
    val audience: Audience
        get() = Audience.audience(participants)
}