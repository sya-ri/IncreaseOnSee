package com.github.syari.yululi.increaseonsee

import org.bukkit.block.Block
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import kotlin.math.abs

fun Player.getNearestEntityInSight(range: Int): Entity? {
    val entities = getNearbyEntities(range.toDouble(), range.toDouble(), range.toDouble())
    val sights = getLineOfSight(null, range).map(Block::getLocation)
    for (s in sights) {
        for (e in entities) {
            if (abs(e.location.x - s.x) < 1.3 && abs(e.location.y - s.y) < 1.5 && abs(e.location.z - s.z) < 1.3) {
                return e
            }
        }
    }
    return null
}
