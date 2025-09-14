package dev.spozap.lavaRising.managers

import dev.spozap.lavaRising.models.GameSelection
import dev.spozap.lavaRising.utils.configMessage
import dev.spozap.lavaRising.utils.format
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.event.block.Action
import java.util.*

object GameCreationManager {

    private val gameSelections: MutableMap<UUID, GameSelection> = mutableMapOf()

    fun checkArenaCreation(player: Player) {
        val selection = gameSelections[player.uniqueId]

        if (selection == null) {
            configMessage(player, "no-selection")
            return
        }

        if (!selection.isComplete()) {
            configMessage(player, "selection-not-complete")
            return
        }

        gameSelections.remove(player.uniqueId)
        ArenaManager.createArena(player, selection)

    }

    fun handleCreationByAction(player: Player, action: Action, blockLocation: Location) {

        val gameSelection = gameSelections.getOrPut(player.uniqueId) {
            GameSelection()
        }

        when (action) {
            Action.LEFT_CLICK_BLOCK -> {
                gameSelection.pos2 = blockLocation
                configMessage(player, "pos1-set", mapOf(Pair("pos", blockLocation.format())))
            }

            Action.RIGHT_CLICK_BLOCK -> {
                gameSelection.pos1 = blockLocation
                configMessage(player, "pos2-set", mapOf(Pair("pos", blockLocation.format())))
            }

            else -> null
        }
    }


}