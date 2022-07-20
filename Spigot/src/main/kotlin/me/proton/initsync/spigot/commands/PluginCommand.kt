package me.proton.initsync.spigot.commands

import me.proton.initsync.spigot.ProxyMotd
import me.proton.initsync.spigot.api.MaintenanceModeEvent
import me.proton.initsync.spigot.enums.Configuration
import me.proton.initsync.spigot.enums.Permissions
import me.proton.initsync.spigot.utils.Text
import me.proton.initsync.spigot.utils.Utils
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.entity.Player

class PluginCommand (plugin: ProxyMotd): CommandExecutor {
	private val plugin: ProxyMotd
	private val permSound: String
	private val reloadSound: String
	private val maintenanceSound: String
	private val author: String
	private val version: String
	private val maintenance: FileConfiguration?
	private val maintenanceModeEvent: MaintenanceModeEvent
	
	init {
		this.plugin = plugin
		this.permSound = Configuration.SOUNDS_NO_PERM.getPath()
		this.reloadSound = Configuration.SOUNDS_RELOAD.getPath()
		this.maintenanceSound = Configuration.SOUNDS_MAINTENANCE.getPath()
		this.author = plugin.getAuthor()
		this.version = plugin.getCurrentVersion()
		this.maintenance = plugin.getConfigHandler().get("maintenance.yml")
		this.maintenanceModeEvent = MaintenanceModeEvent(false)
	}
	
	fun getMaintenanceModeEvent(): MaintenanceModeEvent { return maintenanceModeEvent }
	
	override fun onCommand(
		 sender: CommandSender,
		 command: Command,
		 label: String,
		 args: Array<out String>
	): Boolean {
		if (sender is Player) {
			val player: Player = sender
			
			if (args.isEmpty()) {
				Utils.message(
					 player,
					"&8[&6ProxyMotd&8] &fRunning At &eSpigot &8(&b" + Bukkit.getBukkitVersion() + "&8)&f.",
					"&8[&6ProxyMotd&8] &fDeveloped by &a$author &8~ &a$version"
				)
				return true
			}
			
			if (args[0].equals("help", true)) {
				if (player.hasPermission(Permissions.COMMAND_HELP.getPerm())) {
					val list: List<String> = Text.process(Configuration.MESSAGES_HELP.getList())
					for (string in list) player.sendMessage(string)
				} else {
					Utils.play(
						 player,
						 permSound,
						 Configuration.SOUNDS_VOLUME.getFloat(),
						 1.0.toFloat()
					)
					
					player.sendMessage(Text.process(Configuration.MESSAGES_NO_PERM.getPath()))
				}
				return true
			}
			
			if (args[0].equals("reload", true)) {
				if (player.hasPermission(Permissions.COMMAND_RELOAD.getPerm())) {
					plugin.getConfigHandler().reload("config.yml")
					
					Utils.play(
						player,
						reloadSound,
						Configuration.SOUNDS_VOLUME.getFloat(),
						1.0.toFloat()
					)
					
					player.sendMessage(Text.process(Configuration.MESSAGES_RELOAD.getPath()))
				} else {
					Utils.play(
						 player,
						 permSound,
						 Configuration.SOUNDS_VOLUME.getFloat(),
						 1.0.toFloat()
					)
					
					player.sendMessage(Text.process(Configuration.MESSAGES_NO_PERM.getPath()))
				}
				return true
			}
			
			if (args[0].equals("maintenance", true)) {
				if (player.hasPermission(Permissions.COMMAND_MAINTENANCE.getPerm())) {
					plugin.getPluginManager().callEvent(maintenanceModeEvent)
					if (maintenanceModeEvent.getStatus()) {
						maintenanceModeEvent.setStatus(false)
						
						plugin.getConfigHandler()
							 .get("maintenance.yml")!!
							 .set("maintenance", false)
						plugin.getConfigHandler()
							 .save("maintenance.yml")
						
						Utils.play(
							 player,
							 maintenanceSound,
							 Configuration.SOUNDS_VOLUME.getFloat(),
							 1.0.toFloat()
						)
						
						Utils.sendTitle(
							 player,
							 Configuration.TITLES_FADE_IN.getInt(),
							 Configuration.TITLES_STAY.getInt(),
							 Configuration.TITLES_FADE_OUT.getInt(),
							 Configuration.MESSAGES_MAINTENANCE_TITLE_OFF.getPath(),
							 Configuration.MESSAGES_MAINTENANCE_SUBTITLE_OFF.getPath(),
						)
						
						player.sendMessage(Text.process(Configuration.MESSAGES_MAINTENANCE_OFF.getPath()))
						return true
					}
					
					maintenanceModeEvent.setStatus(true)
					
					plugin.getConfigHandler()
						 .get("maintenance.yml")!!
						 .set("maintenance", true)
					plugin.getConfigHandler()
						 .save("maintenance.yml")
					
					Utils.play(
						 player,
						 maintenanceSound,
						 Configuration.SOUNDS_VOLUME.getFloat(),
						 1.0.toFloat()
					)
					
					Utils.sendTitle(
						 player,
						 Configuration.TITLES_FADE_IN.getInt(),
						 Configuration.TITLES_STAY.getInt(),
						 Configuration.TITLES_FADE_OUT.getInt(),
						 Configuration.MESSAGES_MAINTENANCE_TITLE_ON.getPath(),
						 Configuration.MESSAGES_MAINTENANCE_SUBTITLE_ON.getPath(),
					)
					
					player.sendMessage(Text.process(Configuration.MESSAGES_MAINTENANCE_ON.getPath()))
				} else {
					Utils.play(
						 player,
						 permSound,
						 Configuration.SOUNDS_VOLUME.getFloat(),
						 1.0.toFloat()
					)
					
					player.sendMessage(Text.process(Configuration.MESSAGES_NO_PERM.getPath()))
				}
				return true
			}
			
			player.sendMessage(Text.process(Configuration.MESSAGES_NO_COMMAND.getPath()))
			return false
		}
		
		if (args.isEmpty()) {
			Utils.message(
				sender,
				"&8[&6ProxyMotd&8] &fRunning At &eSpigot &8(&b" + Bukkit.getBukkitVersion() + "&8)&f.",
				"&8[&6ProxyMotd&8] &fDeveloped by &a$author &8~ &a$version"
			)
			return true
		}
		
		if (args[0].equals("help", true)) {
			if (sender.hasPermission(Permissions.COMMAND_HELP.getPerm())) {
				val list: List<String> = Text.process(Configuration.MESSAGES_HELP.getList())
				for (string in list) sender.sendMessage(string)
			} else {
				sender.sendMessage(Text.process(Configuration.MESSAGES_NO_PERM.getPath()))
			}
			return true
		}
		
		if (args[0].equals("reload", true)) {
			if (sender.hasPermission(Permissions.COMMAND_RELOAD.getPerm())) {
				plugin.getConfigHandler().reload("config.yml")
				
				sender.sendMessage(Text.process(Configuration.MESSAGES_RELOAD.getPath()))
			} else {
				sender.sendMessage(Text.process(Configuration.MESSAGES_NO_PERM.getPath()))
			}
			return true
		}
		
		if (args[0].equals("maintenance", true)) {
			if (sender.hasPermission(Permissions.COMMAND_MAINTENANCE.getPerm())) {
				plugin.getPluginManager().callEvent(maintenanceModeEvent)
				if (maintenanceModeEvent.getStatus()) {
					maintenanceModeEvent.setStatus(false)
					
					plugin.getConfigHandler()
						 .get("maintenance.yml")!!
						 .set("maintenance", false)
					plugin.getConfigHandler()
						 .save("maintenance.yml")
					
					sender.sendMessage(Text.process(Configuration.MESSAGES_MAINTENANCE_OFF.getPath()))
					return true
				}
				
				maintenanceModeEvent.setStatus(true)
				
				plugin.getConfigHandler()
					 .get("maintenance.yml")!!
					 .set("maintenance", true)
				plugin.getConfigHandler()
					 .save("maintenance.yml")
				
				sender.sendMessage(Text.process(Configuration.MESSAGES_MAINTENANCE_ON.getPath()))
			} else {
				sender.sendMessage(Text.process(Configuration.MESSAGES_NO_PERM.getPath()))
			}
			return true
		}
		
		sender.sendMessage(Text.process(Configuration.MESSAGES_NO_COMMAND.getPath()))
		return false
	}
}