package com.cosmicreach.redcorp.utils

import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.chat.hover.content.Item
import net.md_5.bungee.api.chat.hover.content.Text
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

object ChatUtil {

    private const val PREFIX = "§cCR §8|§r "

    // ---------- COLOR TAGS ----------

    private val COLOR_TAGS: Map<String, String> = mapOf(
        // Colors
        "<black>" to ChatColor.BLACK.toString(),
        "<dark_blue>" to ChatColor.DARK_BLUE.toString(),
        "<dark_green>" to ChatColor.DARK_GREEN.toString(),
        "<dark_aqua>" to ChatColor.DARK_AQUA.toString(),
        "<dark_red>" to ChatColor.DARK_RED.toString(),
        "<dark_purple>" to ChatColor.DARK_PURPLE.toString(),
        "<gold>" to ChatColor.GOLD.toString(),
        "<gray>" to ChatColor.GRAY.toString(),
        "<dark_gray>" to ChatColor.DARK_GRAY.toString(),
        "<blue>" to ChatColor.BLUE.toString(),
        "<green>" to ChatColor.GREEN.toString(),
        "<aqua>" to ChatColor.AQUA.toString(),
        "<red>" to ChatColor.RED.toString(),
        "<light_purple>" to ChatColor.LIGHT_PURPLE.toString(),
        "<yellow>" to ChatColor.YELLOW.toString(),
        "<white>" to ChatColor.WHITE.toString(),

        // Formats
        "<bold>" to ChatColor.BOLD.toString(),
        "<italic>" to ChatColor.ITALIC.toString(),
        "<underlined>" to ChatColor.UNDERLINE.toString(),
        "<strikethrough>" to ChatColor.STRIKETHROUGH.toString(),
        "<obfuscated>" to ChatColor.MAGIC.toString(),

        // Reset / closers
        "</bold>" to ChatColor.RESET.toString(),
        "</italic>" to ChatColor.RESET.toString(),
        "</underlined>" to ChatColor.RESET.toString(),
        "</strikethrough>" to ChatColor.RESET.toString(),
        "</obfuscated>" to ChatColor.RESET.toString(),
        "</red>" to ChatColor.RESET.toString(),
        "</green>" to ChatColor.RESET.toString(),
        "</yellow>" to ChatColor.RESET.toString(),
        "</gold>" to ChatColor.RESET.toString(),
        "</gray>" to ChatColor.RESET.toString(),
        "</blue>" to ChatColor.RESET.toString(),
        "</light_purple>" to ChatColor.RESET.toString(),
        "</dark_red>" to ChatColor.RESET.toString(),
        "<reset>" to ChatColor.RESET.toString()
    )

    private fun applyColorTags(input: String): String {
        var result = input
        COLOR_TAGS.forEach { (tag, code) ->
            result = result.replace(tag, code, ignoreCase = true)
        }
        return result
    }

    // ---------- PREFIX HELPERS ----------

    private fun applyPrefix(component: TextComponent): TextComponent {
        val pref = TextComponent(PREFIX)
        val root = TextComponent()
        root.addExtra(pref)
        root.addExtra(component)
        return root
    }

    // ---------- PUBLIC SEND METHODS ----------

    fun sendComponents(player: Player, vararg components: TextComponent) {
        val base = TextComponent()
        base.addExtra(TextComponent(PREFIX))
        components.forEach { base.addExtra(it) }
        player.spigot().sendMessage(base)
    }

    fun send(player: Player, component: TextComponent) {
        player.spigot().sendMessage(applyPrefix(component))
    }

    // ---------- COMPONENT BUILDERS ----------

    fun button(
        label: String,
        color: ChatColor = ChatColor.WHITE,
        command: String,
        hoverText: String? = null,
        hoverItem: ItemStack? = null
    ): TextComponent {

        val parsedLabel = applyColorTags(label)

        val component = TextComponent(parsedLabel).apply {
            this.color = color
            clickEvent = ClickEvent(ClickEvent.Action.RUN_COMMAND, command)
        }

        when {
            hoverItem != null -> {
                component.hoverEvent = HoverEvent(
                    HoverEvent.Action.SHOW_ITEM,
                    Item(
                        hoverItem.type.key.toString(),
                        hoverItem.amount,
                        null
                    )
                )
            }

            hoverText != null -> {
                component.hoverEvent = HoverEvent(
                    HoverEvent.Action.SHOW_TEXT,
                    Text(applyColorTags(hoverText))
                )
            }
        }

        return component
    }

    fun text(
        content: String,
        color: ChatColor = ChatColor.WHITE,
        hoverText: String? = null
    ): TextComponent {

        val parsed = applyColorTags(content)

        return TextComponent(parsed).apply {
            this.color = color

            if (hoverText != null) {
                hoverEvent = HoverEvent(
                    HoverEvent.Action.SHOW_TEXT,
                    Text(applyColorTags(hoverText))
                )
            }
        }
    }

    // ---------- JSON / DSL BUILDER ----------

    class JsonBuilder {
        private val base = TextComponent()

        fun text(
            content: String,
            color: ChatColor = ChatColor.WHITE,
            hoverText: String? = null
        ) {
            base.addExtra(ChatUtil.text(content, color, hoverText))
        }

        fun button(
            label: String,
            color: ChatColor = ChatColor.WHITE,
            command: String,
            hoverText: String? = null,
            hoverItem: ItemStack? = null
        ) {
            base.addExtra(ChatUtil.button(label, color, command, hoverText, hoverItem))
        }

        fun space() {
            base.addExtra(TextComponent(" "))
        }

        internal fun build(): TextComponent = base
    }

    fun json(init: JsonBuilder.() -> Unit): TextComponent {
        val builder = JsonBuilder()
        builder.init()
        return builder.build()
    }
}
