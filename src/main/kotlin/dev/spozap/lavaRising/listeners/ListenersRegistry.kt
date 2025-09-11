package dev.spozap.lavaRising.listeners

import dev.spozap.lavaRising.LavaRising
import org.bukkit.Bukkit

object ListenersRegistry {

    fun registerListeners() {
        Bukkit.getPluginManager().registerEvents(WandEvents(), LavaRising.INSTANCE)
        Bukkit.getPluginManager().registerEvents(LavaFlowEvent(), LavaRising.INSTANCE)
        Bukkit.getPluginManager().registerEvents(PlayerEvents(), LavaRising.INSTANCE)
    }

}