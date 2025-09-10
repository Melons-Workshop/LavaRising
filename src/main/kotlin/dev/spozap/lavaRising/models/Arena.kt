package dev.spozap.lavaRising.models

import org.bukkit.Location

data class Arena(
    val pos1: Location,
    val pos2: Location
) {
    fun isInside(location: Location): Boolean {
        val minX = minOf(this.pos1.blockX, this.pos2.blockX)
        val maxX = maxOf(this.pos1.blockX, this.pos2.blockX)
        val minY = minOf(this.pos1.blockY, this.pos2.blockY)
        val maxY = maxOf(this.pos1.blockY, this.pos2.blockY)
        val minZ = minOf(this.pos1.blockZ, this.pos2.blockZ)
        val maxZ = maxOf(this.pos1.blockZ, this.pos2.blockZ)

        val x = location.blockX
        val y = location.blockY
        val z = location.blockZ

        return x in minX..maxX && y in minY..maxY && z in minZ..maxZ
    }
}