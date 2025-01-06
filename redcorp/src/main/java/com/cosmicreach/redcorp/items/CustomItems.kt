package com.cosmicreach.redcorp.items

import de.tr7zw.nbtapi.NBT
import org.bukkit.Bukkit
import org.bukkit.JukeboxSong
import org.bukkit.Material
import org.bukkit.entity.Item
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.SkullMeta
import org.bukkit.inventory.meta.components.EquippableComponent
import org.bukkit.inventory.meta.components.JukeboxPlayableComponent
import org.bukkit.profile.PlayerProfile
import java.net.URL
import java.util.UUID

class CustomItems {
    fun IvansBeats (a : Int): ItemStack {
        val item = ItemStack(Material.BEETROOT, a)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 1)
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("beets")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.setCustomModelDataComponent(cm)
        meta.setDisplayName("§3§oɪᴠᴀɴ's ᴘʀᴇᴍɪᴜᴍ ʙᴇᴇᴛs")
        item.setItemMeta(meta)

        return item
    }

    fun Gavel (a : Int): ItemStack {
        val item = ItemStack(Material.WOODEN_AXE, a)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 2)
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("gavel")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.setCustomModelDataComponent(cm)
        meta.setDisplayName("§6§lᴊᴜᴅɢᴇs ʜᴀᴍᴍᴇʀ")

        val lore = listOf("§fWhen found, return to Judge")
        meta.lore = lore
        item.setItemMeta(meta)

        return item
    }

    fun Scroll (a : Int, type: String): ItemStack {
        val item = ItemStack(Material.PAPER, a)

        NBT.modify(item) { nbt ->
            when (type) {
                "teleport" -> nbt.setInteger("item-id", 3)
                "home" -> nbt.setInteger("item-id", 5)
                "death" -> nbt.setInteger("item-id", 6)
            }
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        NBT.modify(item) { nbt ->
            when (type) {
                "teleport" -> {
                    cm.strings = mutableListOf("scroll-teleport")
                    meta.setDisplayName("§3§lᴛᴇʟᴇᴘᴏʀᴛ sᴄʀᴏʟʟ")
                    val lore = listOf("§fAllows teleport to player")
                    meta.lore = lore
                }
                "home" -> {
                    cm.strings = mutableListOf("scroll-home")
                    meta.setDisplayName("§3§lʜᴏᴍᴇ sᴄʀᴏʟʟ")
                    val lore = listOf("§fAllows teleport to their bed")
                    meta.lore = lore
                }
                "death" -> {
                    cm.strings = mutableListOf("scroll-death")
                    meta.setDisplayName("§3§lᴅᴇᴀᴛʜ sᴄʀᴏʟʟ")
                    val lore = listOf("§fAllows teleport to death location")
                    meta.lore = lore
                }
            }
        }

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.setCustomModelDataComponent(cm)

        item.setItemMeta(meta)

        return item
    }

    fun TeleportAnchor (a : Int): ItemStack {
        val item = ItemStack(Material.RECOVERY_COMPASS, a)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 4)
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("anchor")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.setCustomModelDataComponent(cm)
        meta.setDisplayName("§3§lᴛᴇʟᴇᴘᴏʀᴛ ᴀɴᴄʜᴏʀ")

        val lore = listOf("§fAllows players to teleport to you")
        meta.lore = lore
        item.setItemMeta(meta)

        return item
    }

    fun Penis (a : Int): ItemStack {
        val item = ItemStack(Material.ROTTEN_FLESH, a)
        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("penis")

        meta.setCustomModelDataComponent(cm)
        meta.setDisplayName("§3§lᴘᴇɴᴜs")

        val lore = listOf("§fLol its kinda funny")
        meta.lore = lore
        item.setItemMeta(meta)

        return item
    }

    fun Unit (a : Int, type: String): ItemStack {
        val item = ItemStack(Material.SUNFLOWER, a)
        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("${type}-unit")

        meta.setCustomModelDataComponent(cm)
        meta.setDisplayName("§c§lᴜɴɪᴛs")

        val lore = listOf("§fCurrency of Insomnis")
        meta.lore = lore
        item.setItemMeta(meta)

        return item
    }

    fun DebugStick (a : Int): ItemStack {
        val item = ItemStack(Material.STICK, a)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 2886)
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("tag")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.setCustomModelDataComponent(cm)

        meta.setDisplayName("§c§lᴅᴇʙᴜɢ sᴛɪᴄᴋ")

        item.setItemMeta(meta)
        return item
    }

    fun TestWing (a : Int): ItemStack {
        val item = ItemStack(Material.PAPER, a)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 666)
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent
        val equip = meta.equippable

        cm.strings = mutableListOf("wing")
        equip.slot = EquipmentSlot.CHEST
        meta.isGlider = true

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.setCustomModelDataComponent(cm)
        meta.setEquippable(equip)

        meta.setDisplayName("§c§lTest Wing")

        item.setItemMeta(meta)
        return item
    }

    fun Fairy (a: Int, id: String): ItemStack {
        val item = ItemStack(Material.PLAYER_HEAD, a)
        val profile = Bukkit.createPlayerProfile(UUID.randomUUID())
        val textures = profile.textures
        val url = URL("https://textures.minecraft.net/texture/8241989eac173a87489233b11239e54643609de929189b9e10e598b5cc896021")
        textures.skin = url
        profile.setTextures(textures)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 700+id.toInt())
        }

        val meta = item.itemMeta as SkullMeta
        meta.ownerProfile = profile

        item.setItemMeta(meta)
        return item
    }

    fun Card(a: Int, suit: String, number: String): ItemStack {
        val item = ItemStack(Material.PAPER, a)
        val itemMeta = item.itemMeta as ItemMeta

        val cardInfo = getCardInfo(suit, number)
        if (cardInfo != null) {
            val (itemId, displayName, modelData) = cardInfo
            NBT.modify(item) { nbt -> nbt.setInteger("item-id", itemId) }

            val cm = itemMeta.customModelDataComponent
            cm.strings = mutableListOf(modelData)

            itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
            itemMeta.setCustomModelDataComponent(cm)
            itemMeta.setDisplayName(displayName)

            item.setItemMeta(itemMeta)
        }

        return item
    }

    private fun getCardInfo(suit: String, number: String): Triple<Int, String, String>? {
        val displayNamePrefix = when (suit) {
            "clubs" -> "§9§l"
            "diamonds" -> "§b§l"
            "hearts" -> "§c§l"
            "spades" -> "§2§l"
            "joker" -> "§4§l"
            "back" -> "§f§l"
            else -> return null
        }

        val cardName = when (number) {
            "2" -> "2 Of ${suit.capitalize()}"
            "3" -> "3 Of ${suit.capitalize()}"
            "4" -> "4 Of ${suit.capitalize()}"
            "5" -> "5 Of ${suit.capitalize()}"
            "6" -> "6 Of ${suit.capitalize()}"
            "7" -> "7 Of ${suit.capitalize()}"
            "8" -> "8 Of ${suit.capitalize()}"
            "9" -> "9 Of ${suit.capitalize()}"
            "10" -> "10 Of ${suit.capitalize()}"
            "jack" -> "Jack Of ${suit.capitalize()}"
            "queen" -> "Queen Of ${suit.capitalize()}"
            "king" -> "King Of ${suit.capitalize()}"
            "ace" -> "Ace Of ${suit.capitalize()}"
            "red" -> "Red Joker"
            "black" -> "Black Joker"
            "back" -> "Unknown Card"
            else -> return null
        }

        val itemBaseId = when (suit) {
            "clubs" -> 800
            "diamonds" -> 820  // Start at 820 for diamonds
            "hearts" -> 840    // Start at 840 for hearts
            "spades" -> 860    // Start at 860 for spades
            "joker" -> 880     // Start at 880 for jokers
            "back" -> 883     // Start at 880 for jokers
            else -> return null
        }

        val numberIdOffset = when (number) {
            "2" -> 0
            "3" -> 1
            "4" -> 2
            "5" -> 3
            "6" -> 4
            "7" -> 5
            "8" -> 6
            "9" -> 7
            "10" -> 8
            "jack" -> 9
            "queen" -> 10
            "king" -> 11
            "ace" -> 12
            "red" -> 0
            "black" -> 1
            "back" -> 0
            else -> return null
        }

        val itemId = itemBaseId + numberIdOffset

        return Triple(itemId, "$displayNamePrefix$cardName", "${suit.first()}$number")
    }
}