package dev.spozap.lavaRising.managers

import dev.spozap.lavaRising.LavaRising
import dev.spozap.lavaRising.models.Arena
import dev.spozap.lavaRising.models.Game
import dev.spozap.lavaRising.utils.mm
import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.title.Title
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable

object CurrentGameManager {
    var currentGame: Game? = null

    fun create(arena: Arena) {
        val players = Bukkit.getOnlinePlayers().toList()

        currentGame = Game(arena = arena, participants = players)
        startCountdown()
    }

    private fun startCountdown() {
        object : BukkitRunnable() {
            var time = 10
            val tickSound = Sound.sound(Key.key("minecraft:block.bell.resonate"), Sound.Source.BLOCK, 1f, 1f)

            override fun run() {
                if (time <= 0) {
                    cancel()
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