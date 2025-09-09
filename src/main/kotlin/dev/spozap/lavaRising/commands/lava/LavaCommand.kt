package dev.spozap.lavaRising.commands.lava

import dev.spozap.lavaRising.managers.GameCreationManager
import dev.spozap.lavaRising.utils.ItemUtils
import org.bukkit.entity.Player
import revxrsal.commands.annotation.Command
import revxrsal.commands.annotation.Subcommand
import revxrsal.commands.bukkit.annotation.CommandPermission


@Command("lava")
class LavaCommand {

    @CommandPermission("lavarising.wand")
    @Subcommand("wand")
    fun wand(p: Player) {
        val wand = ItemUtils.createWand()
        p.give(wand)
    }

    @CommandPermission("lavarising.arena.create")
    @Subcommand("arena create")
    fun createArena(p: Player) {
        GameCreationManager.checkArenaCreation(p)
    }

}