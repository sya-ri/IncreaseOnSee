package com.github.syari.yululi.increaseonsee

import com.github.syari.spigot.api.message.sendTitleMessage
import com.github.syari.spigot.api.scheduler.runTaskTimer
import com.github.syari.yululi.increaseonsee.Main.Companion.plugin
import org.bukkit.entity.EntityType
import org.bukkit.entity.LivingEntity
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitTask

object IncreaseTask {
    private var task: BukkitTask? = null

    var mobAmount = 0

    var ignoreType = setOf(
        EntityType.ENDER_DRAGON
    )

    val allLivingEntityType = EntityType.values().filter(EntityType::isAlive)

    fun start() = if (task != null) {
        false
    } else {
        var mobAmountAutoIncrement = 0
        task = plugin.runTaskTimer(10) {
            if (mobAmountAutoIncrement % 120 == 0) { // per 1 minute
                mobAmount ++
                plugin.server.onlinePlayers.forEach {
                    it.sendTitleMessage("&0", "&f&lモブが &a&l${mobAmount}体 &f&l増えます")
                }
            }
            mobAmountAutoIncrement ++
            plugin.server.onlinePlayers.forEach {
                val entity = it.getNearestEntityInSight(30)
                if (entity is LivingEntity && entity.type.isSpawnable && ignoreType.contains(entity.type).not()) {
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
        task = null
        true
    } else {
        false
    }
}
