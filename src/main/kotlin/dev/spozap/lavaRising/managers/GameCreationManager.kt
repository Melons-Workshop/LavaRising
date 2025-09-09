package dev.spozap.lavaRising.managers

import dev.spozap.lavaRising.models.GameSelection
import dev.spozap.lavaRising.utils.format
import dev.spozap.lavaRising.utils.mm
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.event.block.Action
import java.util.*

object GameCreationManager {

    private val gameSelections: MutableMap<UUID, GameSelection> = mutableMapOf()

    fun checkArenaCreation(player: Player) {
        val selection = gameSelections[player.uniqueId]

        if (selection == null) {
            player.sendMessage(mm("<bold><red>You don't have created a selection</red></bold>"))
            return
        }

        if (!selection.isComplete()) {
            player.sendMessage(mm("<bold><red>Your selection is not completed!</red></bold>"))
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
                player.sendMessage(mm("<green>Set pos2 to: ${blockLocation.format()}"))
            }

            Action.RIGHT_CLICK_BLOCK -> {
                gameSelection.pos1 = blockLocation
                player.sendMessage(mm("<green>Set pos1 to: ${blockLocation.format()}"))
            }

            else -> null
        }
    }


}