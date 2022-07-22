package me.proton.initsync.bungeecord.commands

import me.proton.initsync.bungeecord.ProxyMotd
import me.proton.initsync.bungeecord.api.MaintenanceModeEvent
import me.proton.initsync.bungeecord.enums.Permissions
import me.proton.initsync.bungeecord.utils.Log
import me.proton.initsync.bungeecord.utils.Text
import me.proton.initsync.bungeecord.utils.Utils
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.connection.ProxiedPlayer
import net.md_5.bungee.api.plugin.Command
import net.md_5.bungee.config.Configuration

class PluginCommand (plugin: ProxyMotd): Command("proxymotd", null, "pm") {
	private val plugin: ProxyMotd
	private val author: String
	private val version: String
	private val config: Configuration
	private val maintenance: Configuration
	private val maintenanceModeEvent: MaintenanceModeEvent
	
	init {
		this.plugin = plugin
		this.author = plugin.getAuthor()
		this.version = plugin.getCurrentVersion()
		this.config = plugin.getMaintenance().get()
		this.maintenance = plugin.getMaintenance().get()
		this.maintenanceModeEvent = MaintenanceModeEvent(false)
	}
	
	fun getMaintenanceModeEvent(): MaintenanceModeEvent { return maintenanceModeEvent }
	
	override fun execute(sender: CommandSender?, args: Array<out String>?) {
		val prefix: String = Text.process(plugin,
			config.getString("config.prefix")
		)
		
		if (sender is ProxiedPlayer) {
			val player: ProxiedPlayer = sender
			
			if (args!!.isEmpty()) {
				Utils.message(plugin, player,
					 "&8[&6ProxyMotd&8] &fRunning At &eBungeeCord &8(&b" + plugin.proxy.gameVersion + "&8)&f.",
					 "&8[&6ProxyMotd&8] &fDeveloped by &a$author &8~ &a$version"
				)
				return
			}
			
			if (args[0].equals("help", true)) {
				if (player.hasPermission(Permissions.ALL_COMMANDS.getPerm()) ||
						player.hasPermission(Permissions.COMMAND_HELP.getPerm())) {
					val list: List<String> = Text.process(plugin,
						 config.getStringList("messages.help")
					)
					for (string in list) {
						var source = string
						source = source.replace("#prefix", prefix)
						
						player.sendMessage(TextComponent(source))
					}
				} else {
					player.sendMessage(TextComponent(
						 Text.process(plugin,
							 config.getString("messages.no-perm")
								  .replace("#prefix", prefix)
						 )
					))
				}
				return
			}
			
			if (args[0].equals("reload", true)) {
				Log.levelError(plugin, player, "To reload the plugin you must be restart the Bungee.")
				return
			}
			
			if (args[0].equals("maintenance", true)) {
				if (player.hasPermission(Permissions.COMMAND_MAINTENANCE.getPerm())) {
					plugin.getPluginManager().callEvent(maintenanceModeEvent)
					if (maintenanceModeEvent.getStatus()) {
						maintenanceModeEvent.setStatus(false)
						
						maintenance.set("maintenance", false)
						plugin.getMaintenance().save()
						
						Utils.sendTitle(
							 plugin,
							 player,
							 config.getInt("config.titles.fade-in"),
							 config.getInt("config.titles.stay"),
							 config.getInt("config.titles.fade-out"),
							 config.getString("messages.maintenance-title-off"),
							 config.getString("messages.maintenance-subtitle-off"),
						)
						
						player.sendMessage(TextComponent(
							 Text.process(plugin,
									config.getString("messages.maintenance-off")
										 .replace("#prefix", prefix)
							 )
						))
						return
					}
					
					maintenanceModeEvent.setStatus(true)
					
					maintenance.set("maintenance", true)
					plugin.getMaintenance().save()
					
					Utils.sendTitle(
						 plugin,
						 player,
						 config.getInt("config.titles.fade-in"),
						 config.getInt("config.titles.stay"),
						 config.getInt("config.titles.fade-out"),
						 config.getString("messages.maintenance-title-on"),
						 config.getString("messages.maintenance-subtitle-on"),
					)
					
					player.sendMessage(TextComponent(
						 Text.process(plugin,
								config.getString("messages.maintenance-on")
									 .replace("#prefix", prefix)
						 )
					))
				} else {
					player.sendMessage(TextComponent(
						 Text.process(plugin,
								config.getString("messages.no-perm")
									 .replace("#prefix", prefix)
						 )
					))
				}
				return
			}
			
			player.sendMessage(TextComponent(
				 Text.process(plugin,
						config.getString("messages.no-command")
							 .replace("#prefix", prefix)
				 )
			))
			return
		}
		
		val console = sender!!
		
		if (args!!.isEmpty()) {
			Utils.message(plugin, console,
				 "&8[&6ProxyMotd&8] &fRunning At &eBungeeCord &8(&b" + plugin.proxy.gameVersion + "&8)&f.",
				 "&8[&6ProxyMotd&8] &fDeveloped by &a$author &8~ &a$version"
			)
			return
		}
		
		if (args[0].equals("help", true)) {
			if (console.hasPermission(Permissions.ALL_COMMANDS.getPerm()) ||
					console.hasPermission(Permissions.COMMAND_HELP.getPerm())) {
				val list: List<String> = Text.process(plugin,
					 config.getStringList("messages.help")
				)
				for (string in list) {
					var source = string
					source = source.replace("#prefix", prefix)
					
					console.sendMessage(TextComponent(source))
				}
			} else {
				console.sendMessage(TextComponent(
					 Text.process(plugin,
							config.getString("messages.no-perm")
								 .replace("#prefix", prefix)
					 )
				))
			}
			return
		}
		
		if (args[0].equals("reload", true)) {
			Log.levelError(plugin, null, "To reload the plugin you must be restart the Bungee.")
			return
		}
		
		if (args[0].equals("maintenance", true)) {
			if (console.hasPermission(Permissions.ALL_COMMANDS.getPerm()) ||
					console.hasPermission(Permissions.COMMAND_MAINTENANCE.getPerm())) {
				plugin.getPluginManager().callEvent(maintenanceModeEvent)
				if (maintenanceModeEvent.getStatus()) {
					maintenanceModeEvent.setStatus(false)
					
					maintenance.set("maintenance", false)
					plugin.getMaintenance().save()
					
					console.sendMessage(TextComponent(
						 Text.process(plugin,
								config.getString("messages.maintenance-off")
									 .replace("#prefix", prefix)
						 )
					))
					return
				}
				
				maintenanceModeEvent.setStatus(true)
				
				maintenance.set("maintenance", true)
				plugin.getMaintenance().save()
				
				console.sendMessage(TextComponent(
					 Text.process(plugin,
							config.getString("messages.maintenance-on")
								 .replace("#prefix", prefix)
					 )
				))
			} else {
				console.sendMessage(TextComponent(
					 Text.process(plugin,
							config.getString("messages.no-perm")
								 .replace("#prefix", prefix)
					 )
				))
			}
			return
		}
		
		console.sendMessage(TextComponent(
			 Text.process(plugin,
					config.getString("messages.no-command")
						 .replace("#prefix", prefix)
			 )
		))
	}
}