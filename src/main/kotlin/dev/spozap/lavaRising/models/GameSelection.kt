package dev.spozap.lavaRising.models

import org.bukkit.Location

data class GameSelection(
    var pos1: Location? = null,
    var pos2: Location? = null
) {
    fun isComplete(): Boolean = pos1 != null && pos2 != null
}