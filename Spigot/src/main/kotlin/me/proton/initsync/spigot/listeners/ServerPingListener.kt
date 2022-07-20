package me.proton.initsync.spigot.listeners

import me.proton.initsync.spigot.ProxyMotd
import me.proton.initsync.spigot.enums.Configuration
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.server.ServerListPingEvent

class ServerPingListener (plugin: ProxyMotd): Listener {
	private val plugin: ProxyMotd
	
	init { this.plugin = plugin }
	
	@EventHandler
	fun onPingEvent(event: ServerListPingEvent) {
		if (Configuration.MOTD_ALLOWED.check()) {
			val max: Int = Configuration.MOTD_MAX_PLAYERS.getInt()
			
			if (Configuration.MOTD_PLAYERS_ZERO.check() && plugin.getPluginCommand()
					 .getMaintenanceModeEvent()
					 .getStatus()) {
				event.maxPlayers = 0
			} else event.maxPlayers = max
			
			if (Configuration.MOTD_ALLOWED_ONE.check()) event.numPlayers + 1
		}
	}
}