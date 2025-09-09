package dev.spozap.lavaRising

import dev.spozap.lavaRising.commands.CommandRegistry
import dev.spozap.lavaRising.listeners.ListenersRegistry
import org.bukkit.plugin.java.JavaPlugin

class LavaRising : JavaPlugin() {

    companion object {
        lateinit var INSTANCE: LavaRising
    }

    override fun onEnable() {
        INSTANCE = this

        ListenersRegistry.registerListeners()
        CommandRegistry.registerCommands()

    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
