package dev.spozap.lavaRising.managers

import dev.spozap.lavaRising.LavaRising
import dev.spozap.lavaRising.models.Arena
import dev.spozap.lavaRising.models.GameSelection
import dev.spozap.lavaRising.utils.configMessage
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import java.io.File

object ArenaManager {

    var arena: Arena? = null
        private set

    private var dataFile: File = File(LavaRising.INSTANCE.dataFolder, "data.yml")
    private var config: YamlConfiguration

    init {
        if (!dataFile.exists()) {
            dataFile.parentFile.mkdirs()
            dataFile.createNewFile()
        }
        config = YamlConfiguration.loadConfiguration(dataFile)
        loadArena()
    }

    fun createArena(player: Player, selection: GameSelection) {
        arena = Arena(
            pos1 = selection.pos1!!,
            pos2 = selection.pos2!!
        )

        saveArena()
        configMessage(player, "arena-created")
    }

    private fun saveArena() {
        val a = arena ?: return
        config.set("arena.pos1.world", a.pos1.world.name)
        config.set("arena.pos1.x", a.pos1.x)
        config.set("arena.pos1.y", a.pos1.y)
        config.set("arena.pos1.z", a.pos1.z)

        config.set("arena.pos2.world", a.pos2.world.name)
        config.set("arena.pos2.x", a.pos2.x)
        config.set("arena.pos2.y", a.pos2.y)
        config.set("arena.pos2.z", a.pos2.z)

        config.save(dataFile)
    }

    private fun loadArena() {
        if (!config.contains("arena")) return

        val pos1World = Bukkit.getWorld(config.getString("arena.pos1.world") ?: return) ?: return
        val pos1 = Location(
            pos1World,
            config.getDouble("arena.pos1.x"),
            config.getDouble("arena.pos1.y"),
            config.getDouble("arena.pos1.z")
        )

        val pos2World = Bukkit.getWorld(config.getString("arena.pos2.world") ?: return) ?: return
        val pos2 = Location(
            pos2World,
            config.getDouble("arena.pos2.x"),
            config.getDouble("arena.pos2.y"),
            config.getDouble("arena.pos2.z")
        )

        arena = Arena(pos1, pos2)
    }
}
