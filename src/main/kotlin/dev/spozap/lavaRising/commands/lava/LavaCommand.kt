package dev.spozap.lavaRising.commands.lava

import dev.spozap.lavaRising.managers.ArenaManager
import dev.spozap.lavaRising.managers.CurrentGameManager
import dev.spozap.lavaRising.managers.GameCreationManager
import dev.spozap.lavaRising.utils.ItemUtils
import dev.spozap.lavaRising.utils.mm
import org.bukkit.entity.Player
import revxrsal.commands.annotation.Command
import revxrsal.commands.annotation.Subcommand
import revxrsal.commands.bukkit.annotation.CommandPermission


@Command("lavarising")
class LavaCommand {

    @CommandPermission("lavarising.select")
    @Subcommand("select")
    fun wand(p: Player) {
        val wand = ItemUtils.createWand()
        p.give(wand)
    }

    @CommandPermission("lavarising.arena.create")
    @Subcommand("arena create")
    fun createArena(p: Player) {
        GameCreationManager.checkArenaCreation(p)
    }

    @CommandPermission("lavarising.start")
    @Subcommand("start")
    fun startGame(p: Player) {
        val arena = ArenaManager.arena
        if (arena == null) {
            p.sendMessage(mm("<bold><red>No arena was created!</red></bold>"))
            return
        }

        CurrentGameManager.create(arena)
    }

    @CommandPermission("lavarising.stop")
    @Subcommand("stop")
    fun stopGame(p: Player) {
        val arena = ArenaManager.arena
        if (arena == null) {
            p.sendMessage(mm("<bold><red>No arena was created!</red></bold>"))
            return
        }

        CurrentGameManager.stop(p)
    }

}