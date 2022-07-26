package me.proton.initsync.bungeecord

import me.proton.initsync.bungeecord.commands.PluginCommand
import me.proton.initsync.bungeecord.config.ConfigHandler
import me.proton.initsync.bungeecord.listeners.ProxyPingListener
import me.proton.initsync.bungeecord.utils.Log
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.api.plugin.Plugin
import net.md_5.bungee.api.plugin.PluginManager

class ProxyMotd: Plugin() {
	private val author: String = description.author
	private val currentVersion: String = description.version
	private val pluginManager: PluginManager = proxy.pluginManager
	private val config: ConfigHandler = ConfigHandler(this, "bungee_config.yml")
	private val maintenance: ConfigHandler = ConfigHandler(this, "maintenance.yml")
	
	private lateinit var pluginCommand: PluginCommand
	
	fun getAuthor(): String { return author }
	fun getCurrentVersion(): String { return currentVersion }
	fun getPluginManager(): PluginManager { return pluginManager }
	fun getConfiguration(): ConfigHandler { return config }
	fun getMaintenance(): ConfigHandler { return maintenance }
	fun getPluginCommand(): PluginCommand { return pluginCommand }
	
	override fun onEnable() {
		// Plugin startup logic
		
		val startupTimeAtMillis: Long = System.currentTimeMillis()
		
		config.create()
		config.load()
		maintenance.create()
		maintenance.load()
		
		pluginCommand = PluginCommand(this)
		
		pluginManager.registerCommand(this, pluginCommand)
		
		Log.levelInfo(this, null, "Successful loaded &b'PluginCommand.class' &acommand.")
		
		listeners(ProxyPingListener(this))
		
		val finalTime = System.currentTimeMillis() - startupTimeAtMillis
		
		Log.levelInfo(this, null, "Successful loaded plugin at &e'$finalTime'ms&a.")
		Log.levelInfo(this, null, "&fDeveloped by &e$author &8| &a$currentVersion&f.")
	}
	
	/**
	 * Collect all the listeners and be load.
	 *
	 * @param listeners listeners to load.
	 */
	private fun listeners(vararg listeners: Listener) {
		for (listener in listeners) {
			pluginManager.registerListener(this, listener)
			
			val className: String = listener.javaClass.simpleName
			
			Log.levelInfo(this, null, "Successful loaded &b'$className.class' &aevent.")
		}
	}
	
	override fun onDisable() {
		// Plugin shutdown logic
		
		Log.levelInfo(this, null, "Successful unloaded plugin.")
		Log.levelInfo(this, null, "&fDeveloped by &e$author &8| &a$currentVersion&f.")
	}
}