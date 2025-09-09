package dev.spozap.lavaRising.utils

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage

fun mm(miniMessage: String): Component {
    return MiniMessage.miniMessage().deserialize(miniMessage);
}