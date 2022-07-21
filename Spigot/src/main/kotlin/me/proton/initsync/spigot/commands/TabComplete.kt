package me.proton.initsync.spigot.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

class TabComplete: TabCompleter {
	
	private val unique: MutableList<String> = mutableListOf()
	
	override fun onTabComplete(
		 sender: CommandSender,
		 command: Command,
		 alias: String,
		 args: Array<out String>
	): MutableList<String>? {
		if (unique.isEmpty()) {
			unique.add("help")
			unique.add("reload")
			unique.add("maintenance")
		}
		
		val result: MutableList<String> = mutableListOf()
		if (args.size == 1) {
			for (a in unique) {
				if (a.lowercase()
						 .startsWith(args[0].lowercase())) {
					result.add(a)
				}
			}
			return result
		}
		return null
	}
}