KingdomsOfArden-ToolHandler (www.kingdomsofarden.net)
===========

Version 2.1.0 Dev
Author: Andrew Wen (andrew2060)

FOR CB 1.6.X - This will NOT work with prior versions!

##Introduction

This plugin adds an RPG element to crafted items, integrating with the SoulSap Heroes Skill, 
as well as the Repair, Forge, and ChainMailCraft skills, to bring several unique additions to the game.

##Features
Upon installing this plugin, the following changes can be noted:

Enchants:

* It will no longer be possible to obtain the following enchants via. the enchanting table
    * Sharpness
    * Environmental Protection
    * Efficiency
* These enchants will instead scale automatically based on the "Improvement Quality" item statitic which can be increased with the Forge skill and decreases with use
    * By default the breakpoints for Improvement quality are as follows:
        * 00.00%>Improvement Quality>=20.00% == Enchant Level 1
        * 20.00%>Improvement Quality>=40.00% == Enchant Level 2
        * 40.00%>Improvement Quality>=60.00% == Enchant Level 3
        * 60.00%>Improvement Quality>=80.00% == Enchant Level 4
        * 80.00%>Improvement Quality == Enchant Level 5
    * This prevents Protection 5/Sharpness 5 OP Equipment Syndrome as it is fairly easy to adjust improvement costs to limit availability
    * Most importantly Level 5 Enchants do NOT stay that way, users must constantly spend materials to maintain a high enchant level
* Dev/In-Progress: Custom Enchantments can be added, with variable degradation rates/refresh charges

Heroes Integration:

* Comes with the following four skills
    * SkillDisenchant: disenchanted items gives you bottles of enchanting instead of experience as is the case with the Heroes bundled SkillDisenchant (More on that later)
    * SkillForge: Skill to adjust / increase improvement quality of items (anvil on top of a block with fire underneath)
    * SkillRepair: repair the durability of the item, does not affect improvement quality (normal anvil)
    * SkillSoulSap: Charge soul gems (Emeralds) of varying power based on enemy's level. (More on this later)

Potion Effects:

* Potion Effect Queuing
    * Instead of complete potion overwrite/duration overwrite upon new effects being added, it will instead queue the potion effects
    * For example: 10 minute speed 1 potion effect exists on a player, and we are applying speed 2 for 30 seconds. Instead of cancelling the remaining 10 minutes and applying a 30 second speed 2, the 30 second speed 2 will be applied, and once that ends, the 9:30 speed 1 will be reapplied
    
Item Mods:

* Soul Gems can be used to apply custom Item Modifications. These can do basic things such as increasing an item's attributes, or do more advanced things such as apply a speed/slow effect, and/or other actions performable with the Bukkit/Heroes API
* Soul Gems can be used using the Mod Combiner Interface (Enchantment table on top of an enderchest)
* Soul Gems of lower quality can be combined to that of a higher quality using bottles of enchanting
* Tools/Armor have a configurable chance of breaking upon having mods applied to them
* Tools/Armor have 2 mod slots by default, this can also be increased with bottles of enchanting with a configurable break chance


## Dependencies

This plugin depends on

* Libigot (Until the time if/when Heroes no longer requires this as a dependency)
* Heroes
* Vault

## Compiling using maven

You WILL need to customize the provided pom.xml to point to a local repository as you will most likely not be able to resolve the artifacts as they are currently provided in the pom.