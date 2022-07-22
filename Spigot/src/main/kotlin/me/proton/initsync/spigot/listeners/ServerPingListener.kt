package me.proton.initsync.spigot.listeners

import me.proton.initsync.spigot.ProxyMotd
import me.proton.initsync.spigot.enums.Configuration
import me.proton.initsync.spigot.utils.Text
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.server.ServerListPingEvent
import java.util.Random
import kotlin.streams.toList

class ServerPingListener (plugin: ProxyMotd): Listener {
	private val plugin: ProxyMotd
	private val random: Random
	
	init {
		this.plugin = plugin
		this.random = Random()
	}
	
	@EventHandler
	fun onPingEvent(event: ServerListPingEvent) {
		if (Configuration.MOTD_ALLOWED.check()) {
			val max: Int = Configuration.MOTD_MAX_PLAYERS.getInt()
			val online: Int = event.numPlayers
			
			val maintenance: Boolean = plugin.getPluginCommand()
				 .getMaintenanceModeEvent()
				 .getStatus()
			if (maintenance) {
				val listUp: List<String> = Text.process(Configuration.MOTD_MAINTENANCE_UP.getList())
				val listDown: List<String> = Text.process(Configuration.MOTD_MAINTENANCE_DOWN.getList())
				
				val maintenanceMotd = if (Configuration.MOTD_ALLOWED_RANDOM.check()) {
					listUp[random.nextInt(listUp.size)] +
					System.lineSeparator() +
					listDown[random.nextInt(listDown.size)]
				} else {
					listUp[1] +
					System.lineSeparator() +
					listDown[1]
				}
				
				event.motd = maintenanceMotd
				
				if (Configuration.MOTD_PLAYERS_ZERO.check()) event.maxPlayers = 0
				else event.maxPlayers = max
			} else {
				val listUp: List<String> = Text.process(Configuration.MOTD_COMMON_UP.getList())
				val listDown: List<String> = Text.process(Configuration.MOTD_COMMON_DOWN.getList())
				
				val commonMotd: String = if (Configuration.MOTD_ALLOWED_RANDOM.check()) {
					listUp[random.nextInt(listUp.size)] +
					System.lineSeparator() +
					listDown[random.nextInt(listDown.size)]
				} else {
					listUp[1] +
					System.lineSeparator() +
					listDown[1]
				}
				
				event.motd = commonMotd
				
				if (Configuration.MOTD_ALLOWED_ONE.check()) event.maxPlayers = online + 1
				else event.maxPlayers = max
			}
		}
	}
}