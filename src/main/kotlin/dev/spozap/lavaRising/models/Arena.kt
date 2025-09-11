package dev.spozap.lavaRising.models

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.World

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

fun Arena.fill(y: Int, material: Material) {
    val world: World = pos1.world ?: return

    val minX = minOf(pos1.blockX, pos2.blockX)
    val maxX = maxOf(pos1.blockX, pos2.blockX)
    val minZ = minOf(pos1.blockZ, pos2.blockZ)
    val maxZ = maxOf(pos1.blockZ, pos2.blockZ)

    for (x in minX..maxX) {
        for (z in minZ..maxZ) {
            val block = world.getBlockAt(x, y, z)
            if (block.type == Material.AIR) {
                block.type = material
            }
        }
    }
}

fun Arena.clearLava() {
    val world: World = pos1.world ?: return

    val minX = minOf(pos1.blockX, pos2.blockX)
    val maxX = maxOf(pos1.blockX, pos2.blockX)
    val minY = minOf(pos1.blockY, pos2.blockY)
    val maxY = maxOf(pos1.blockY, pos2.blockY)
    val minZ = minOf(pos1.blockZ, pos2.blockZ)
    val maxZ = maxOf(pos1.blockZ, pos2.blockZ)

    for (x in minX..maxX) {
        for (y in minY..maxY) {
            for (z in minZ..maxZ) {
                val block = world.getBlockAt(x, y, z)
                if (block.type == Material.LAVA) {
                    block.type = Material.AIR
                }
            }
        }
    }
}

