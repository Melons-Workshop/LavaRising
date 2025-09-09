package dev.spozap.lavaRising.commands

import dev.spozap.lavaRising.LavaRising
import dev.spozap.lavaRising.commands.lava.LavaCommand
import revxrsal.commands.bukkit.BukkitLamp

object CommandRegistry {

    fun registerCommands() {
        val lamp = BukkitLamp.builder(LavaRising.INSTANCE)
            .build()

        lamp.register(LavaCommand())
    }
}