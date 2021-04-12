package com.github.syari.yululi.increaseonsee

import com.github.syari.spigot.api.scheduler.runTaskTimer
import com.github.syari.yululi.increaseonsee.Main.Companion.plugin
import org.bukkit.entity.LivingEntity
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitTask

object IncreaseTask {
    private var task: BukkitTask? = null

    var mobAmount = 1

    fun start() = if (task != null) {
        false
    } else {
        task = plugin.runTaskTimer(10) {
            plugin.server.onlinePlayers.forEach {
                val entity = it.getNearestEntityInSight(30)
                if (entity is LivingEntity) {
                    entity.addPotionEffect(PotionEffect(PotionEffectType.GLOWING, 10, 0, false, false, false))
                    if (CoolTime.contains(it).not()) {
                        CoolTime.add(it, 5 * 20)
                        repeat(mobAmount) {
                            entity.world.spawnEntity(entity.location, entity.type)
                        }
                    }
                }
            }
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
