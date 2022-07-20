package me.proton.initsync.spigot.utils

import org.bukkit.Bukkit
import org.bukkit.entity.Player

class Log {
	companion object {
		/**
		 * Send messages to the console or player marked as information.
		 *
		 * @param player player to send the message/s.
		 * @param strings strings to send.
		 */
		fun levelInfo(player: Player?, vararg strings: String) {
			if (player != null && player.isOnline) {
				for (string in strings) {
					var source = string
					source = "&6[ProxyMotd] &a[INFO] $source"
					source = Text.process(source)
					
					player.sendMessage(source)
				}
				return
			}
			
			for (string in strings) {
				var source = string
				source = "&6[ProxyMotd] &a[INFO] $source"
				source = Text.process(source)
				
				Bukkit.getServer()
					.consoleSender
					.sendMessage(source)
			}
		}
		
		/**
		 * Send messages to the console or player marked as warnings.
		 *
		 * @param player player to send the message/s.
		 * @param strings strings to send.
		 */
		fun levelWarn(player: Player?, vararg strings: String) {
			if (player != null && player.isOnline) {
				for (string in strings) {
					var source = string
					source = "&6[ProxyMotd] &e[WARN] $source"
					source = Text.process(source)
					
					player.sendMessage(source)
				}
				return
			}
			
			for (string in strings) {
				var source = string
				source = "&6[ProtonMotd] &e[WARN] $source"
				source = Text.process(source)
				
				Bukkit.getServer()
					.consoleSender
					.sendMessage(source)
			}
		}
		
		/**
		 * Send messages to the console or player marked as errors.
		 *
		 * @param player player to send the message/s.
		 * @param strings strings to send.
		 */
		fun levelError(player: Player?, vararg strings: String) {
			if (player != null && player.isOnline) {
				for (string in strings) {
					var source = string
					source = "&6[ProxyMotd] &c[ERROR] $source"
					source = Text.process(source)
					
					player.sendMessage(source)
				}
				return
			}
			
			for (string in strings) {
				var source = string
				source = "&6[ProxyMotd] &c[ERROR] $source"
				source = Text.process(source)
				
				Bukkit.getServer()
					.consoleSender
					.sendMessage(source)
			}
		}
	}
}