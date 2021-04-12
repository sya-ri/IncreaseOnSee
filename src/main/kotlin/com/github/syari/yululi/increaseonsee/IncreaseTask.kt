package com.github.syari.yululi.increaseonsee

import com.github.syari.spigot.api.scheduler.runTaskTimer
import com.github.syari.yululi.increaseonsee.Main.Companion.plugin
import org.bukkit.scheduler.BukkitTask

object IncreaseTask {
    private var task: BukkitTask? = null

    fun start() = if (task != null) {
        false
    } else {
        task = plugin.runTaskTimer(10) {
        }
        true
    }

    fun stop() = if (task != null) {
        task!!.cancel()
        true
    } else {
        false
    }
}
