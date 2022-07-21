package me.proton.initsync.spigot

import me.proton.initsync.spigot.commands.PluginCommand
import me.proton.initsync.spigot.commands.TabComplete
import me.proton.initsync.spigot.config.ConfigHandler
import me.proton.initsync.spigot.listeners.ServerPingListener
import me.proton.initsync.spigot.utils.Log
import org.bukkit.event.Listener
import org.bukkit.plugin.PluginManager
import org.bukkit.plugin.java.JavaPlugin

class ProxyMotd: JavaPlugin() {
	private val author: String = description.authors.joinToString()
	private val currentVersion: String = description.version
	private val pluginManager: PluginManager = server.pluginManager
	
	private lateinit var configHandler: ConfigHandler
	private lateinit var pluginCommand: PluginCommand
	
	fun getAuthor(): String { return author }
	fun getCurrentVersion(): String { return currentVersion }
	fun getPluginManager(): PluginManager { return pluginManager }
	fun getConfigHandler(): ConfigHandler { return configHandler }
	fun getPluginCommand(): PluginCommand { return pluginCommand }
	
	override fun onEnable() {
		// Plugin startup logic
		
		val startupTimeAtMillis: Long = System.currentTimeMillis()
		
		configHandler = ConfigHandler(this, null,
			"config.yml",
			"maintenance.yml"
		)
		pluginCommand = PluginCommand(this)
		
		getCommand("proxymotd")?.setExecutor(pluginCommand)
		getCommand("proxymotd")?.tabCompleter = TabComplete()
		
		Log.levelInfo(null, "Successful loaded &b'PluginCommand.class' &acommand.")
		
		listeners(ServerPingListener(this))
		
		val finalTime = System.currentTimeMillis() - startupTimeAtMillis
		
		Log.levelInfo(null, "Successful loaded plugin at &e'$finalTime'ms&a.")
		Log.levelInfo(null, "&fDeveloped by &e$author &8| &a$currentVersion&f.")
	}
	
	/**
	 * Collect all the listeners and be load.
	 *
	 * @param listeners listeners to load.
	 */
	private fun listeners(vararg listeners: Listener) {
		for (listener in listeners) {
			pluginManager.registerEvents(listener, this)
			
			val className: String = listener.javaClass.simpleName
			
			Log.levelInfo(null, "Successful loaded &b'$className.class' &aevent.")
		}
	}
	
	override fun onDisable() {
		// Plugin shutdown logic
		
		Log.levelInfo(null, "Successful unloaded plugin.")
		Log.levelInfo(null, "&fDeveloped by &e$author &8| &a$currentVersion&f.")
	}
}