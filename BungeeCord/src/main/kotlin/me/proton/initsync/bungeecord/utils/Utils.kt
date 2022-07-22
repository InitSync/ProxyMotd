package me.proton.initsync.bungeecord.utils

import me.proton.initsync.bungeecord.ProxyMotd
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.Title
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.connection.ProxiedPlayer

class Utils {
	companion object {
		/**
		 * Send a title.
		 *
		 * @param player player to send the title.
		 * @param fadeIn first time parameter.
		 * @param stay second time parameter.
		 * @param fadeOut third time parameter.
		 * @param paramTitle title message.
		 * @param paramSubtitle subtitle message.
		 */
		fun sendTitle(
			 plugin: ProxyMotd,
			 player: ProxiedPlayer,
			 fadeIn: Int,
			 stay: Int,
			 fadeOut: Int,
			 paramTitle: String,
			 paramSubtitle: String
		) {
			val title = Text.process(plugin, paramTitle)
			val subtitle = Text.process(plugin, paramSubtitle)
			val titleBuilder: Title = plugin.proxy
				 .createTitle()
				 .title(TextComponent(title))
				 .subTitle(TextComponent(subtitle))
				 .fadeIn(fadeIn)
				 .stay(stay)
				 .fadeOut(fadeOut)
			
			player.sendTitle(titleBuilder)
		}
		
		/**
		 * Uniquely build a message with the strings for send to player.
		 *
		 * @param sender to whom send the message.
		 * @param strings message strings.
		 */
		fun message(plugin: ProxyMotd, sender: CommandSender, vararg strings: String) {
			for (string in strings) {
				var source = string
				source = Text.process(plugin, source)
				
				sender.sendMessage(TextComponent(source))
			}
		}
	}
}