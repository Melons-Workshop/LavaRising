package dev.spozap.lavaRising.managers

import dev.spozap.lavaRising.LavaRising
import dev.spozap.lavaRising.models.Arena
import dev.spozap.lavaRising.models.Game
import dev.spozap.lavaRising.models.GameState
import dev.spozap.lavaRising.utils.mm
import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.title.Title
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask

object CurrentGameManager {
    var currentGame: Game? = null
    var lavaRisingTask: BukkitTask? = null

    fun create(arena: Arena) {
        val players = Bukkit.getOnlinePlayers().toList()

        currentGame = Game(arena = arena, participants = players)
        startCountdown()
    }

    fun start() {
        currentGame?.state = GameState.RUNNING

        lavaRisingTask = object : BukkitRunnable() {

            override fun run() {
                val world = currentGame?.arena?.pos1?.world!!
                val arena = currentGame?.arena!!

                val lavaY = currentGame?.lavaY!!

                val minX = minOf(arena.pos1.blockX, arena.pos2.blockX)
                val maxX = maxOf(arena.pos1.blockX, arena.pos2.blockX)
                val minZ = minOf(arena.pos1.blockZ, arena.pos2.blockZ)
                val maxZ = maxOf(arena.pos1.blockZ, arena.pos2.blockZ)

                if (lavaY > maxOf(arena.pos1.blockY, arena.pos2.blockY)) {
                    cancel()
                    return
                }

                for (x in minX..maxX) {
                    for (z in minZ..maxZ) {
                        world.getBlockAt(x, lavaY.toInt(), z).type = Material.LAVA
                    }
                }

                currentGame?.lavaY++
            }
        }.runTaskTimer(LavaRising.INSTANCE, 0L, 100L)
    }

    fun stop(p: Player) {
        currentGame?.state = GameState.STOPPED
        lavaRisingTask?.cancel()
        p.sendMessage(mm("<green>Game stopped successfully</green>"))
    }


    private fun startCountdown() {
        object : BukkitRunnable() {
            var time = 10
            val tickSound = Sound.sound(Key.key("minecraft:block.bell.resonate"), Sound.Source.BLOCK, 1f, 1f)

            override fun run() {
                if (time <= 0) {
                    currentGame?.audience?.showTitle(
                        Title.title(
                            mm("<red>LavaRising</red>"),
                            mm("<red>Lava will start raising! Be careful</red>")
                        )
                    )
                    currentGame?.audience?.stopSound(tickSound)

                    currentGame?.audience?.playSound(
                        Sound.sound(Key.key("minecraft:entity.ender_dragon.growl"), Sound.Source.MASTER, 1f, 1f)
                    )

                    cancel()
                    start()

                    return
                }

                currentGame?.audience?.showTitle(
                    Title.title(
                        mm("<red>LavaRising</red>"),
                        mm("<red>Game will start in $time seconds</red>")
                    )
                )

                currentGame?.audience?.playSound(tickSound)

                time--
            }
        }.runTaskTimer(LavaRising.INSTANCE, 0L, 20L)
    }

}