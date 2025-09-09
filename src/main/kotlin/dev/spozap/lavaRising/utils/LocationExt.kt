package dev.spozap.lavaRising.utils

import org.bukkit.Location

fun Location.format(): String = "(X: ${this.x} Y: ${this.y} Z: ${this.z})"