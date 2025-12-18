package com.cosmicreach.redcorp.utils

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.event.HoverEvent
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import net.kyori.adventure.text.ComponentLike
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

object ChatUtil {
    private const val PREFIX_LEGACY = "§cCR §8|§r "

    private fun parse(input: String): Component {
        return LegacyComponentSerializer.legacySection().deserialize(input)
    }

    private fun applyPrefix(component: Component): Component {
        val prefix = LegacyComponentSerializer.legacySection().deserialize(PREFIX_LEGACY)
        return prefix.append(component)
    }

    fun sendComponents(player: Player, vararg components: ComponentLike) {
        val base = Component.text()
        base.append(LegacyComponentSerializer.legacySection().deserialize(PREFIX_LEGACY))
        components.forEach { base.append(it.asComponent()) }
        player.sendMessage(base.build())
    }

    fun send(player: Player, component: ComponentLike) {
        player.sendMessage(applyPrefix(component.asComponent()))
    }

    fun send(player: Player, message: String) {
        player.sendMessage(applyPrefix(parse(message)))
    }

    fun button(
        label: String,
        command: String,
        hoverText: String? = null,
        hoverItem: ItemStack? = null
    ): Component {
        var c: Component = parse(label)
            .clickEvent(ClickEvent.runCommand(command))

        if (hoverItem != null) {
            c = c.hoverEvent(hoverItem.asHoverEvent())
        } else if (hoverText != null) {
            c = c.hoverEvent(HoverEvent.showText(parse(hoverText)))
        }

        return c
    }

    fun text(
        content: String,
        hoverText: String? = null
    ): Component {
        var c: Component = parse(content)
        if (hoverText != null) {
            c = c.hoverEvent(HoverEvent.showText(parse(hoverText)))
        }
        return c
    }

    class JsonBuilder {
        private val base = Component.text()

        fun text(
            content: String,
            hoverText: String? = null
        ) {
            base.append(ChatUtil.text(content, hoverText))
        }

        fun button(
            label: String,
            command: String,
            hoverText: String? = null,
            hoverItem: ItemStack? = null
        ) {
            base.append(ChatUtil.button(label, command, hoverText, hoverItem))
        }

        fun space() {
            base.append(Component.space())
        }

        internal fun build(): Component = base.build()
    }

    fun json(init: JsonBuilder.() -> Unit): Component {
        val b = JsonBuilder()
        b.init()
        return b.build()
    }
}