package me.proton.initsync.spigot.utils

import org.bukkit.ChatColor
import kotlin.streams.toList

class Text {
	companion object {
		fun process(string: String): String {
			return ChatColor.translateAlternateColorCodes('&', string)
		}
		
		fun process(collection: Collection<String>): List<String> {
			return collection.stream()
				.map { string -> process(string) }
				.toList()
		}
	}
}