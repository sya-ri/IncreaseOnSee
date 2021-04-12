package com.github.syari.yululi.increaseonsee

import com.github.syari.spigot.api.command.command
import com.github.syari.spigot.api.command.tab.CommandTabArgument.Companion.argument
import com.github.syari.spigot.api.message.sendChatMessage
import com.github.syari.yululi.increaseonsee.IncreaseTask.mobAmount
import com.github.syari.yululi.increaseonsee.Main.Companion.plugin
import org.bukkit.entity.EntityType

object CommandCreator {
    private const val MessagePrefix = "&b[Increase]"

    fun register() {
        plugin.command("ios") {
            tab {
                argument { addAll("start", "stop", "set", "ignore") }
                argument("ignore **") { addAll(IncreaseTask.allLivingEntityType.map(EntityType::name)) }
            }
            execute {
                when (args.lowerOrNull(0)) {
                    "start" -> {
                        IncreaseTask.start()
                        sender.sendChatMessage("$MessagePrefix &fモブ増殖を開始しました")
                    }
                    "stop" -> {
                        IncreaseTask.stop()
                        sender.sendChatMessage("$MessagePrefix &fモブ増殖を停止しました")
                    }
                    "set" -> {
                        mobAmount = args.getOrNull(1)?.toIntOrNull() ?: 1
                        sender.sendChatMessage("$MessagePrefix &f増殖するモブの量を &a$mobAmount &fにした")
                    }
                    "ignore" -> {
                        IncreaseTask.ignoreType = args.subList(1, args.size).mapNotNull { name ->
                            IncreaseTask.allLivingEntityType.firstOrNull { it.name.equals(name, true) }
                        }.toSet()
                        sender.sendChatMessage("$MessagePrefix &a${IncreaseTask.ignoreType.joinToString { it.name }} &fは増殖させないようにしました")
                    }
                    else -> {
                        sender.sendChatMessage(
                            """
                                $MessagePrefix &fコマンド一覧
                                &a/$label start &7モブ増殖を開始する
                                &a/$label stop &7モブ増殖を停止する
                                &a/$label set <量> &7モブ増殖の量を変更する
                                &a/$label ignore <モブタイプ> &7モブ増殖させないタイプを変更する
                            """.trimIndent()
                        )
                    }
                }
            }
        }
    }
}
