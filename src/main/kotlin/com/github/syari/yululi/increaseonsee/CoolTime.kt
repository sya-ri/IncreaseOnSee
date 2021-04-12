package com.github.syari.yululi.increaseonsee

import com.github.syari.spigot.api.scheduler.runTaskLater
import com.github.syari.spigot.api.uuid.UUIDPlayer
import com.github.syari.yululi.increaseonsee.Main.Companion.plugin
import org.bukkit.entity.Player
import java.util.concurrent.ConcurrentSkipListSet

object CoolTime {
    private val inCoolTime = ConcurrentSkipListSet<UUIDPlayer>()

    fun add(player: Player, refreshTime: Long) {
        val uuidPlayer = UUIDPlayer.from(player)
        inCoolTime.add(uuidPlayer)
        plugin.runTaskLater(refreshTime) {
            inCoolTime.remove(uuidPlayer)
        }
    }

    fun contains(player: Player) = inCoolTime.contains(UUIDPlayer.from(player))
}
