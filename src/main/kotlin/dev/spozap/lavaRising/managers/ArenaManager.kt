package dev.spozap.lavaRising.managers

import dev.spozap.lavaRising.models.Arena
import dev.spozap.lavaRising.models.GameSelection
import dev.spozap.lavaRising.utils.mm
import org.bukkit.entity.Player

object ArenaManager {

    var arena: Arena? = null

    fun createArena(player: Player, selection: GameSelection) {
        arena = Arena(
            pos1 = selection.pos1!!,
            pos2 = selection.pos2!!
        )

        player.sendMessage(mm("<bold><green>Arena created successfully</green></bold>"))
    }

}