package me.proton.initsync.bungeecord.listeners

import me.proton.initsync.bungeecord.ProxyMotd
import me.proton.initsync.bungeecord.utils.Text
import net.md_5.bungee.api.ServerPing
import net.md_5.bungee.api.event.ProxyPingEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.config.Configuration
import net.md_5.bungee.event.EventHandler
import java.util.Random
import java.util.UUID

class ProxyPingListener (plugin: ProxyMotd): Listener {
	private val plugin: ProxyMotd
	private val random: Random
	private val configuration: Configuration
	
	init {
		this.plugin = plugin
		this.random = Random()
		this.configuration = plugin.getConfiguration().get()
	}
	
	@EventHandler
	fun onProxyPing(event: ProxyPingEvent) {
		val serverPing: ServerPing = event.response
		
		if (configuration.getBoolean("config.motd.allow")) {
			val max: Int = configuration.getInt("config.motd.max")
			val online: Int = serverPing.players.online
			
			val maintenance: Boolean = plugin.getPluginCommand()
				 .getMaintenanceModeEvent()
				 .getStatus()
			if (maintenance) {
				val listUp: List<String> = Text.process(plugin,
					 configuration.getStringList("config.motd.maintenance.up")
				)
				val listDown: List<String> = Text.process(plugin,
					 configuration.getStringList("config.motd.maintenance.down")
				)
				
				val maintenanceMotd = if (configuration.getBoolean("config.motd.allow-random-lines")) {
					listUp[random.nextInt(listUp.size)] + System.lineSeparator() +
					listDown[random.nextInt(listDown.size)]
				} else {
					listUp[1] +
					System.lineSeparator() +
					listDown[1]
				}
				
				serverPing.description = maintenanceMotd
				serverPing.version.protocol = configuration.getInt("config.motd.protocol-version")
				
				if (configuration.getBoolean("config.motd.allow-samples")) {
					val fakeUuid = UUID(0, 0)
					val sampleString: List<String> =
						Text.process(plugin, configuration.getStringList("config.motd.maintenance.sample"))
					var sample: Array<ServerPing.PlayerInfo>? = null
					
					for (i in 0..sampleString.size) {
						sample?.set(i,
							 ServerPing.PlayerInfo(sampleString[i], fakeUuid)
						)
					}
					
					serverPing.players.sample = sample
				}
				
				if (configuration.getBoolean("config.motd.players-to-zero")) {
					serverPing.players.online = 0
					serverPing.players.max = 0
				} else {
					if (configuration.getBoolean("config.motd.one-player-more")) {
						serverPing.players.max = online + 1
					} else serverPing.players.max = max
					
					if (configuration.getBoolean("config.motd.allow-fake-players")) {
						serverPing.players.online = configuration.getInt("config.motd.online")
					}
				}
			} else {
				val listUp: List<String> = Text.process(plugin,
					 configuration.getStringList("config.motd.common.up")
				)
				val listDown: List<String> = Text.process(plugin,
					 configuration.getStringList("config.motd.common.down")
				)
				
				val commonMotd: String = if (configuration.getBoolean("config.motd.allow-random-lines")) {
					listUp[random.nextInt(listUp.size)] +
					System.lineSeparator() +
					listDown[random.nextInt(listDown.size)]
				} else {
					listUp[1] +
					System.lineSeparator() +
					listDown[1]
				}
				
				serverPing.description = commonMotd
				
				if (configuration.getBoolean("config.motd.allow-samples")) {
					val fakeUuid = UUID(0, 0)
					val sampleString: List<String> =
						Text.process(plugin, configuration.getStringList("config.motd.common.sample"))
					var sample: Array<ServerPing.PlayerInfo>? = null
					
					for (i in 0..sampleString.size) {
						sample?.set(i,
							 ServerPing.PlayerInfo(sampleString[i], fakeUuid)
						)
					}
					
					serverPing.players.sample = sample
				}
				
				if (configuration.getBoolean("config.motd.one-player-more")) {
					serverPing.players.max = online + 1
				} else serverPing.players.max = max
				
				if (configuration.getBoolean("config.motd.allow-fake-players")) {
					serverPing.players.online = configuration.getInt("config.motd.online")
				}
			}
		}
	}
}