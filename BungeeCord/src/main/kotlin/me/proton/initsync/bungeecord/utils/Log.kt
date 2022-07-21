package me.proton.initsync.bungeecord.utils

import me.proton.initsync.bungeecord.ProxyMotd
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.connection.ProxiedPlayer

class Log {
	companion object {
		/**
		 * Send messages to the console or player marked as information.
		 *
		 * @param player player to send the message/s.
		 * @param strings strings to send.
		 */
		fun levelInfo(plugin: ProxyMotd, player: ProxiedPlayer?, vararg strings: String) {
			if (player != null && player.isConnected) {
				for (string in strings) {
					var source = string
					source = "&6[ProxyMotd] &a[INFO] $source"
					source = Text.process(plugin, source)
					
					player.sendMessage(TextComponent(source))
				}
				return
			}
			
			for (string in strings) {
				var source = string
				source = "&6[ProxyMotd] &a[INFO] $source"
				source = Text.process(plugin, source)
				
				plugin.proxy
					 .console
					 .sendMessage(TextComponent(source))
			}
		}
		
		/**
		 * Send messages to the console or player marked as warnings.
		 *
		 * @param player player to send the message/s.
		 * @param strings strings to send.
		 */
		fun levelWarn(plugin: ProxyMotd, player: ProxiedPlayer?, vararg strings: String) {
			if (player != null && player.isConnected) {
				for (string in strings) {
					var source = string
					source = "&6[ProxyMotd] &e[WARN] $source"
					source = Text.process(plugin, source)
					
					player.sendMessage(TextComponent(source))
				}
				return
			}
			
			for (string in strings) {
				var source = string
				source = "&6[ProtonMotd] &e[WARN] $source"
				source = Text.process(plugin, source)
				
				plugin.proxy
					 .console
					 .sendMessage(TextComponent(source))
			}
		}
		
		/**
		 * Send messages to the console or player marked as errors.
		 *
		 * @param player player to send the message/s.
		 * @param strings strings to send.
		 */
		fun levelError(plugin: ProxyMotd, player: ProxiedPlayer?, vararg strings: String) {
			if (player != null && player.isConnected) {
				for (string in strings) {
					var source = string
					source = "&6[ProxyMotd] &c[ERROR] $source"
					source = Text.process(plugin, source)
					
					player.sendMessage(TextComponent(source))
				}
				return
			}
			
			for (string in strings) {
				var source = string
				source = "&6[ProxyMotd] &c[ERROR] $source"
				source = Text.process(plugin, source)
				
				plugin.proxy
					 .console
					 .sendMessage(TextComponent(source))
			}
		}
	}
}