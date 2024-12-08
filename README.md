
# Cosmic Reach Minecraft Mono

Created a mono repo for all the cosmic reach server resources




## Resource Packs
#### S5
For Minecraft 1.20.3 and season 5 of Cosmic Reach containing 3D models and sounds for the server
#### S6
For Minecraft 1.21.4 and season 6 of Cosmic Reach containing 3D models and sounds for the server
## Commands

#### Get Relics
Used to get relics for testing.
```command
  /getrelic {item} {type}
```

| Items     | Types    |
| :-------- | :------- |
| `sword`     | `fire` | 
| `pick`     | `water` | 
| `axe`     | `earth` | 
| `shovel`     | `air` | 
| `hoe`     |   | 
| `helmet`     |    | 
| `chestplate`     |    | 
| `leggins`     |    | 
| `boots`     |    | 
| `wings`     |    | 

#### Add Relics
Used to add relics to the vault if they somehow go missing (same table as above).
```command
  /addrelic {type} {item}
```

#### Get item
Used to get tokens for testing.
```command
  /gettoken {token} {type}
```

| Token | Types     |
| :-------- | :------- |
| `normal`      | `fire` |
| `hard`      | `water` |
| `reward`      | `earth` |
|       | `air` |

#### Start Tag
Starts a game of Tag using player username. Null makes command runner start.
```command
  /starttag {username}
```

#### Materia
Transforms vanilla items into custom items based on type. Some items have a bonus argument.
```command
  /materia {type} {arg}
```

#### NPC
Server only command to make NPC's interact with player with custom dialogue or GUIs.
```command
  /redcorp:npc {player} {npc}
```

#### Get Reward
Server only command to give player a dungeon reward.
```command
  /redcorp:npc {player} {type}
```

#### Toggle Particle
Particle Test.
```command
  /toggleparticle
```

#### Test Command
Test command for testing stuff.
```command
  /testcommand
```
## Deployment

To deploy this project it must be packaged with Maven :)

```bash
  Maven package -f pom.xml
```


## Plugin Dependancies

 - [item-nbt-api-plugin](https://github.com/tr7zw/Item-NBT-API)
 - [worldguard-bukkit](https://github.com/EngineHub/WorldGuard)
 - [vault](https://github.com/milkbowl/Vault)
 - [ProtocolLib](https://github.com/dmulloy2/ProtocolLib)

## Shaded Plugins

- [invui](https://github.com/NichtStudioCode/InvUI)