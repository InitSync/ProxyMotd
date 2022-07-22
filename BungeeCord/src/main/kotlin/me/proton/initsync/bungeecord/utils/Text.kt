package me.proton.initsync.bungeecord.utils

import me.proton.initsync.bungeecord.ProxyMotd
import net.md_5.bungee.api.ChatColor
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.streams.toList

class Text {
	companion object {
		private val PATTERN: Pattern = Pattern.compile("#[a-fA-F0-9]{6}")
		
		fun process(plugin: ProxyMotd, string: String): String {
			var source = string
			var matcher: Matcher = PATTERN.matcher(source)
			
			while (matcher.find()) {
				val color: String = source.substring(matcher.start(), matcher.end())
				source = source.replace(color, "" + ChatColor.of(color))
				
				matcher = PATTERN.matcher(source)
			}
			
			source = ChatColor.translateAlternateColorCodes('&', source)
			return source
		}
		
		fun process(plugin: ProxyMotd, collection: Collection<String>): List<String> {
			return collection.stream()
				 .map { process(plugin, it) }
				 .toList()
		}
	}
}