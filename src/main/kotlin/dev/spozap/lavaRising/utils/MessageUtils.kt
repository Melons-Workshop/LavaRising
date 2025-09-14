package dev.spozap.lavaRising.utils

import dev.spozap.lavaRising.LavaRising
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.title.Title
import org.bukkit.entity.Player

fun mm(miniMessage: String): Component {
    return MiniMessage.miniMessage().deserialize(miniMessage)
}

fun configMessage(audience: Audience, path: String, placeholders: Map<String, String> = emptyMap()) {
    val parsed = processConfigMessage(path, placeholders)
    parsed?.let {
        audience.sendMessage(mm(it))
    }
}

fun configMessage(player: Player, path: String, placeholders: Map<String, String> = emptyMap()) {
    val parsed = processConfigMessage(path, placeholders)
    parsed?.let {
        player.sendMessage(mm(it))
    }
}

private fun processConfigMessage(path: String, placeholders: Map<String, String> = emptyMap()): String? {
    val rawMsg = LavaRising.INSTANCE.config.getString("messages.$path") ?: return null
    var parsedMsg = rawMsg

    placeholders.forEach { key, value ->
        parsedMsg = parsedMsg.replace("{$key}", value)
    }

    return parsedMsg
}

fun sendConfigTitle(audience: Audience, path: String, placeholders: Map<String, String> = emptyMap()) {
    val section = LavaRising.INSTANCE.config.getConfigurationSection("titles.$path") ?: return

    var title = section.getString("title") ?: ""
    var subtitle = section.getString("subtitle") ?: ""

    placeholders.forEach { (key, value) ->
        title = title.replace("{$key}", value)
        subtitle = subtitle.replace("{$key}", value)
    }

    audience.showTitle(
        Title.title(
            mm(title),
            mm(subtitle),
        )
    )
}