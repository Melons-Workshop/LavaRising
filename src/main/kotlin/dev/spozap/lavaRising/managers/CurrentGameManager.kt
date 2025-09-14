package dev.spozap.lavaRising.managers

import dev.spozap.lavaRising.LavaRising
import dev.spozap.lavaRising.models.*
import dev.spozap.lavaRising.utils.configMessage
import dev.spozap.lavaRising.utils.sendConfigTitle
import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound
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

                        sendConfigTitle(it.audience, "ended")

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
        configMessage(p, "game-stopped")
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

        currentGame?.deaths?.add(player.uniqueId)
        configMessage(player, "player-death", mapOf(Pair("player", player.name)))
    }


    private fun startCountdown() {
        object : BukkitRunnable() {
            var time = 10
            val tickSound = Sound.sound(Key.key("minecraft:block.bell.resonate"), Sound.Source.BLOCK, 1f, 1f)

            override fun run() {
                if (time <= 0) {
                    currentGame?.audience?.let {
                        it.stopSound(tickSound)
                        it.playSound(
                            Sound.sound(Key.key("minecraft:entity.ender_dragon.growl"), Sound.Source.MASTER, 1f, 1f)
                        )

                        sendConfigTitle(it, "started")
                    }

                    cancel()
                    start()

                    return
                }

                currentGame?.audience?.let {
                    sendConfigTitle(it, "starting", mapOf(Pair("seconds", time.toString())))
                    it.playSound(tickSound)
                }

                time--
            }
        }.runTaskTimer(LavaRising.INSTANCE, 0L, 20L)
    }

}