package dev.spozap.lavaRising.managers

import dev.spozap.lavaRising.LavaRising
import dev.spozap.lavaRising.models.*
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
        val players = Bukkit.getOnlinePlayers().toSet()

        currentGame = Game(arena = arena, participants = players)
        startCountdown()
    }

    fun start() {
        currentGame?.let {
            it.state = GameState.RUNNING

            lavaRisingTask = object : BukkitRunnable() {

                override fun run() {
                    val arena = it.arena
                    val lavaY = currentGame?.lavaY!!

                    if (currentGame!!.lavaReachedTop) {
                        cancel()
                        it.audience.showTitle(
                            Title.title(
                                mm("<bold><red>LavaRising</red></bold>"),
                                mm("<bold><red>Game has finished</red></bold>")
                            )
                        )

                        reset()
                        return
                    }

                    arena.fill(lavaY, Material.LAVA)

                    currentGame?.lavaY++
                }
            }.runTaskTimer(LavaRising.INSTANCE, 0L, 40L)
        }

    }

    fun stop(p: Player) {
        currentGame?.state = GameState.STOPPED
        lavaRisingTask?.cancel()
        p.sendMessage(mm("<green>Game stopped successfully</green>"))
    }

    fun reset() {
        currentGame?.arena?.clearLava()
        lavaRisingTask = null
        currentGame = null
    }

    fun registerDeath(player: Player) {
        if (currentGame!!.deaths.contains(player.uniqueId)) {
            return
        }

        currentGame?.audience?.sendMessage(mm("<bold><gray>LavaRising >> </gray><red>${player.name} is now eliminated</red></bold>"))
        currentGame?.deaths?.add(player.uniqueId)
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