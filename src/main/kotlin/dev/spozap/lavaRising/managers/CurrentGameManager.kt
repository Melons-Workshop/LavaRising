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
import java.util.*

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
                    val game = currentGame ?: return
                    val arena = game.arena

                    val topY = maxOf(arena.pos1.blockY, arena.pos2.blockY)

                    if (game.lavaReachedTop) {
                        cancel()
                        sendConfigTitle(game.audience, "ended")
                        reset()
                        return
                    }

                    arena.fill(game.lavaY, Material.LAVA)
                    game.lavaY++

                    val toRemove = mutableListOf<UUID>()
                    for ((uuid, loc) in game.deaths) {
                        if (loc.blockY > topY) {
                            toRemove.add(uuid)
                            continue
                        }

                        val block = loc.world.getBlockAt(loc.blockX, loc.blockY, loc.blockZ)
                        if (block.type == Material.AIR) {
                            block.type = Material.LAVA
                        }

                        loc.y = loc.y + 1 // subimos un bloque en Y para la próxima vez
                    }

                    toRemove.forEach { game.deaths.remove(it) }
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
        val game = currentGame ?: return
        if (game.deaths.containsKey(player.uniqueId)) return

        game.deaths[player.uniqueId] = player.location.clone()

        configMessage(player, "player-death", mapOf("player" to player.name))
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