package com.cosmicreach.redcorp.commands

import com.cosmicreach.redcorp.menus.*
import com.cosmicreach.redcorp.npcs.Cops
import com.cosmicreach.redcorp.npcs.Merlin
import com.cosmicreach.redcorp.npcs.Zarek
import com.cosmicreach.redcorp.utils.Utils
import com.cosmicreach.redcorp.utils.DecideLoot
import com.cosmicreach.redcorp.utils.DrugTest
import net.milkbowl.vault.economy.Economy
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import xyz.xenondevs.invui.window.Window
import java.util.concurrent.ThreadLocalRandom

class NPC(economy: Economy?): CommandExecutor {
    private val econ = economy as Economy
    private var kyleStage = HashMap<Player, Int>()
    private val confirm = HashMap<Player, Boolean>()
    private val merlinConfirm = HashMap<Player, Boolean>()
    private val zarekConfirm = HashMap<Player, Boolean>()
    private var zarekStage = HashMap<Player, Int>()
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            sender.sendMessage("§cCR §8| §c${sender.displayName} kindly fuck off")
            return false
        }

        if (args.isEmpty()) {
            sender.sendMessage("§cCR §8| kindly fuck off")
            return false
        }

        fun ivanResponse(): String {
            when (ThreadLocalRandom.current().nextInt(0, 10)) {
                0 -> return "§cIvan §8|§r Welcome, comrade! You seek passage across the bridge? First, let's talk beets. They are the people's choice, after all."
                1 -> return "§cIvan §8|§r Ah, the bridge toll? It's simple, just a few beets for the journey. Keeps the bridge strong, just like our unity."
                2 -> return "§cIvan §8|§r In the Soviet tradition, we believe in sharing. You buy my beets, and I let you cross without hassle. Fair, da?"
                3 -> return "§cIvan §8|§r Remember, comrade, beets are the lifeblood of our revolution. Buy some, and you support the cause."
                4 -> return "§cIvan §8|§r Crossing the bridge is easy, but only for those who appreciate the power of beets. Care to indulge in some beet-utiful trade?"
                5 -> return "§cIvan §8|§r No beets, no passage! That's the way of the bridge, and the way of the revolution."
                6 -> return "§cIvan §8|§r A wise choice, comrade! With my beets in your possession, your journey across the bridge will be smooth as the hammer and sickle."
                7 -> return "§cIvan §8|§r Crossing the bridge of friendship and commerce, one beet at a time. Together, we shall prevail!"
                8 -> return "§cIvan §8|§r We may be on different sides of the bridge, but we can all appreciate the earthy goodness of beets. Buy some, and let's bridge our differences."
                9 -> return "§cIvan §8|§r Ah, capitalism may crumble, but my beets stand strong. Care to invest in a taste of the future?"
            }
            return ""
        }

        fun kyleResponse(): String {
            when (ThreadLocalRandom.current().nextInt(0, 8)) {
                0 -> return "§cKyle §8|§r Jeramey, my son, if you can hear me, know that your old man is doing everything he can to reunite with you."
                1 -> return "§cKyle §8|§r These dungeon waters are relentless, just like time itself. I must find a way out of here."
                2 -> return "§cKyle §8|§r In this watery abyss, I've heard whispers of a powerful dungeon token that can unlock the boss lair. Perhaps it's our ticket to escape."
                3 -> return "§cKyle §8|§r The waters here are deep, cold, and unforgiving. If only I had that elusive dungeon token to enter the boss lair and find Jeramey."
                4 -> return "§cKyle §8|§r Oh hi traveler. §o*mubles* §rLost in this endless maze... The walls... they close in on me."
                5 -> return "§cKyle §8|§r §o*wispers* §rThe darkness... it's consuming me. I can't find my way out. Please, forgive me..."
                6 -> return "§cKyle §8|§r I've been here for too long, the water, the echoes... it's all I know now. I can't remember anything else."
                7 -> return "§cKyle §8|§r The dungeon token, I need it. If I can just find it, I can escape this torment."
            }
            return ""
        }

        fun jerameyResponse(): String {
            when (ThreadLocalRandom.current().nextInt(0, 3)) {
                0 -> return "§cJeramey §8|§r I don't think you have the right thing sweetheart"
                1 -> return "§cJeramey §8|§r That Item isn't even close love"
                2 -> return "§cJeramey §8|§r Go on, show me what im looking for"
            }
            return ""
        }

        fun daveResponse(): String {
            return ""
        }

        val player = Bukkit.getServer().getPlayerExact(args[0])

        if (player is Player) {
            when (args[1]) {
                "ivan" -> {
                    val item = player.inventory.itemInMainHand
                    if (!Utils().checkID(item, arrayOf(1))) {
                        player.sendMessage(ivanResponse())
                        return false
                    }

                    player.sendMessage("§cIvan §8|§r I suppose for this I could lower the bridge")
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "opendoor 10")
                    player.inventory.itemInMainHand.amount -= 1
                }
                "cop" -> {
                    player.sendMessage("§cIvan §8|§r Drug Test ${DrugTest().doTest(player)}")
                }
                "kyle" -> {
                    val item = player.inventory.itemInMainHand
                    if (!Utils().checkID(item, arrayOf(50))) {
                        player.sendMessage(kyleResponse())
                        return false
                    }

                    if (!kyleStage.containsKey(player)) {
                        kyleStage[player] = 1
                    }
                    when (kyleStage[player]) {
                        1 -> {
                            player.sendMessage("§cKyle §8|§r Ah I see you have a Dungeon Token perhaps you want to give it to me?")
                            kyleStage[player] = 2
                            val window = Window.single()
                                    .setViewer(player)
                                    .setTitle("§6§lGive Kyle the Token?")
                                    .setGui(Confirm(confirm).makeGUI())
                                    .build()
                            window.open()
                        }
                        2 -> {
                            if (confirm.containsKey(player)) {
                                if(confirm[player] == true) {
                                    confirm.remove(player)
                                    kyleStage[player] = 1
                                    player.sendMessage("§cKyle §8|§r Thank you ${player.displayName}, I can finally go home and see my son Jeremy")
                                    player.inventory.itemInMainHand.amount -= 1
                                }
                                else {
                                    confirm.remove(player)
                                    kyleStage[player] = 3
                                    player.sendMessage("§cKyle §8|§r Fine, I suppose you want to defeat the dungeon boss your self?")
                                    val window = Window.single()
                                            .setViewer(player)
                                            .setTitle("§6§lFight the boss yourself?")
                                            .setGui(Confirm(confirm).makeGUI())
                                            .build()
                                    window.open()
                                }
                            } else {
                                player.sendMessage("§cServer §8|§r Something went wrong try again")
                                kyleStage[player] = 1
                            }
                        }
                        3 -> {
                            if (confirm.containsKey(player)) {
                                if (confirm[player] == true) {
                                    confirm.remove(player)
                                    kyleStage[player] = 1
                                    player.sendMessage("§cKyle §8|§r Okay well off you go then, ill use my leftover magic to send you to the boss lair. If you find my son please let him know I love him.")
                                    player.inventory.itemInMainHand.amount -= 1
                                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"warp WaterBoss ${player.playerListName}")
                                } else {
                                    confirm.remove(player)
                                    kyleStage[player] = 1
                                    player.sendMessage("§cKyle §8|§r So you wont give me the token and you wont fight the boss whats wrong with you?")
                                }
                            } else {
                                player.sendMessage("§cServer §8|§r Something went wrong try again")
                                kyleStage[player] = 1
                            }
                        }
                    }
                }
                "jeramey" -> {
                    val item = player.inventory.itemInMainHand
                    if (!Utils().checkID(item, arrayOf(52, 53, 54, 55))) {
                        player.sendMessage(jerameyResponse())
                        return false
                    }

                    player.sendMessage("§cJeramey §8|§r Alright lets crack the vault open")
                    player.inventory.itemInMainHand.amount -= 1
                    DecideLoot().decideLoot(player, item)
                    return false
                }
                "patrick" -> {
                    var hasCoke = false
                    player.inventory.forEach {
                        if (it != null) {
                            if(Utils().checkID(it, arrayOf(432))) {
                                hasCoke = true
                            }
                        }
                    }
                    if (hasCoke) {
                        player.sendMessage("§cPatrick Byattyman §8|§r Oooohhh is that for me?")
                        val window = Window.single()
                            .setViewer(player)
                            .setTitle("§c§lPatrick's Prison Wallet")
                            .setGui(CokeStore(econ).makeGUI(player))
                            .build()

                        window.open()
                        return false
                    } else {
                        when (ThreadLocalRandom.current().nextInt(0, 4)) {
                            0 -> player.sendMessage("§cPatrick Byattyman §8|§r Yeah ill trade some Diamonds.")
                            1 -> player.sendMessage("§cPatrick Byattyman §8|§r Got anything other than the usual shiny gems?")
                            2 -> player.sendMessage("§cPatrick Byattyman §8|§r Man I could do with some Devils Dandruff!")
                            3 -> player.sendMessage("§cPatrick Byattyman §8|§r Hey ${player.displayName}, do you wanna build a snowman?")
                        }
                        val window = Window.single()
                            .setViewer(player)
                            .setTitle("§3§lDiamond Exchange")
                            .setGui(DiamondExchange(econ, 1).makeGUI(player))
                            .build()

                        window.open()
                        return false
                    }
                }
                "sterling" -> {
                    player.sendMessage("§6Sterling §8|§r Welcome to the Diamond Exchange brother!")
                    val window = Window.single()
                            .setViewer(player)
                            .setTitle("§3§lDiamond Exchange")
                            .setGui(DiamondExchange(econ, 2).makeGUI(player))
                            .build()

                    window.open()
                    return false
                }
                "milton" -> {
                    player.sendMessage("§9Milton §8|§r Iv got some dungeon tokens for ya")
                    val window = Window.single()
                            .setViewer(player)
                            .setTitle("§6§lDungeon Token Exchange")
                            .setGui(DungeonToken(econ).makeGUI(player))
                            .build()

                    window.open()
                    return false
                }
                "eloise" -> {
                    player.sendMessage("§aEloise §8|§r I could take those pesky tokens off your hand for a price ;)")
                    val window = Window.single()
                            .setViewer(player)
                            .setTitle("§6§lDungeon Reward Exchange")
                            .setGui(RewardExchange(econ).makeGUI(player))
                            .build()

                    window.open()
                    return false
                }
                "merlin" -> {
                    Merlin(player, econ, merlinConfirm).run()
                    return false
                }
                "zarek" -> {
                    Zarek(player, zarekConfirm, zarekStage).run()
                    return false
                }
                "zarek_portal" -> {
                    Zarek(player, zarekConfirm, zarekStage).openPortal()
                    return false
                }
                in (1..20).map { "cop_$it" } -> {
                    val copNumber = args[1].removePrefix("cop_").toInt()
                    Cops(copNumber, player).run()
                    return false
                }
                "shade" -> {
                    player.sendMessage("§8Shade E §8|§r Looking for a lil something something?")
                    val window = Window.single()
                            .setViewer(player)
                            .setTitle("§8§lShade E's Drug Store")
                            .setGui(DrugStore(econ).makeGUI(player))
                            .build()

                    window.open()
                    return false
                }
                "oakley" -> {
                    player.sendMessage("§2Oakley §8|§r Welcome to my goods emporium?")
                    val window = Window.single()
                            .setViewer(player)
                            .setTitle("§2§lOakley's Furniture Store")
                            .setGui(FurnitureStore(econ).makeGUI(player))
                            .build()

                    window.open()
                    return false
                }
                "shaggy" -> {
                    player.sendMessage("§2Shaggy §8|§r Hey yooo you got something to sell me")
                    val window = Window.single()
                            .setViewer(player)
                            .setTitle("§2§lShaggy's Collection")
                            .setGui(WeedStore(econ).makeGUI(player))
                            .build()

                    window.open()
                    return false
                }
                "toad" -> {
                    player.sendMessage("§5Toad §8|§r Waaahhooooo you got some mushrooms for me")
                    val window = Window.single()
                            .setViewer(player)
                            .setTitle("§5§lToad's Psilocybin Emporium")
                            .setGui(MushroomStore(econ).makeGUI(player))
                            .build()

                    window.open()
                    return false
                }
                "charlie" -> {
                    player.sendMessage("§6Charlie §8|§r This company is being bled like a stuffed pig Mac, and I got a paper trail to prove it. Check this out, take a look at this.")
                    val window = Window.single()
                            .setViewer(player)
                            .setTitle("§6§lCharlie's Alcohol Intake")
                            .setGui(AlcoholStore(econ).makeGUI(player))
                            .build()

                    window.open()
                    return false
                }
                "wong" -> {
                    player.sendMessage("§fWong §8|§r You like the name? I took it from, uh, my favorite historical character and my second-favorite Szechuan restaurant in Brooklyn. Now tell me what you want, before I gut ya like a pig and feed you to the skin louse!")
                    val window = Window.single()
                            .setViewer(player)
                            .setTitle("§f§lWong's Noodle Business")
                            .setGui(OpiumStore(econ).makeGUI(player))
                            .build()

                    window.open()
                    return false
                }
                "cassian" -> {
                    player.sendMessage("§aCassian §8|§r What can I get for you Java the hutt?")
                    val window = Window.single()
                            .setViewer(player)
                            .setTitle("§a§lCassian's Barista")
                            .setGui(CoffeeShop(econ).makeGUI(player))
                            .build()

                    window.open()
                    return false
                }
                "gamble" -> {
                    player.sendMessage("§2Gamble-Tron §8|§r Bing bing bing!")
                    val window = Window.single()
                            .setViewer(player)
                            .setTitle("§2Fruit Gamble")
                            .setGui(FruitGamble(econ).makeGUI(player))
                            .build()

                    window.open()
                    return false
                }
                "shipment" -> {
                    player.sendMessage("§2Shipment §8|§r Testing")
                    val window = Window.single()
                        .setViewer(player)
                        .setTitle("§2Start Shipments")
                        .setGui(Shipment(econ).makeGUI(player))
                        .build()

                    window.open()
                    return false
                }
                "shipmentSubmit" -> {
                    player.sendMessage("§2Shipment §8|§r Testing")
                    val window = Window.single()
                        .setViewer(player)
                        .setTitle("§2Start Shipments")
                        .setGui(ShipmentSubmit(econ).makeGUI(player))
                        .build()

                    window.open()
                    return false
                }
            }
        }
        return false
    }
}