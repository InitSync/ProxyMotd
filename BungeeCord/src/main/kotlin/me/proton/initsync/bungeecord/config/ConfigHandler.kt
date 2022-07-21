package me.proton.initsync.bungeecord.config

import me.proton.initsync.bungeecord.ProxyMotd
import me.proton.initsync.bungeecord.utils.Log
import net.md_5.bungee.config.Configuration
import net.md_5.bungee.config.ConfigurationProvider
import net.md_5.bungee.config.YamlConfiguration
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.nio.file.Files

class ConfigHandler (plugin: ProxyMotd, file: String) {
	private val plugin: ProxyMotd
	private val file: String
	
	private lateinit var configuration: Configuration
	
	init {
		this.plugin = plugin
		this.file = file
	}
	
	fun get(): Configuration { return configuration }
	
	/**
	 * Load a file.
	 *
	 * @param paramFolder folder of file.
	 * @param paramFile name of file to load.
	 */
	fun load() {
		try {
			configuration = ConfigurationProvider.getProvider(YamlConfiguration::class.java)
				 .load(File(plugin.dataFolder, file))
			
			Log.levelInfo(plugin, null, "Successful loaded &b'$file' &afile.")
		} catch (e: IOException) {
			Log.levelError(plugin, null, "Failed to load the file: &e'$file'&c.")
			
			e.printStackTrace()
		}
	}
	
	/**
	 * Save a file.
	 *
	 * @param paramFolder folder of file.
	 * @param paramFile name of file.
	 */
	fun save() {
		plugin.proxy
			 .scheduler
			 .runAsync(plugin) {
				 try {
					 ConfigurationProvider.getProvider(YamlConfiguration::class.java)
						  .save(configuration,
						    File(plugin.dataFolder, file)
						  )
				 } catch (e: IOException) {
					 Log.levelError(plugin, null, "Failed to save the file: &e'$file'&c.")
					 
					 e.printStackTrace()
				 }
			 }
	}
	
	/**
	 * Create a file.
	 *
	 * @param paramFile name of file to create.
	 */
	fun create() {
		try {
			val ioFile = File(plugin.dataFolder, file)
			if (ioFile.exists()) return
			
			if (!ioFile.parentFile.exists() && !ioFile.parentFile.mkdirs()) {
				Log.levelError(plugin, null, "Can't create parent directories.")
				return
			}
			
			val inputStream: InputStream? = plugin.javaClass
				 .classLoader
				 .getResourceAsStream(file)
			if (inputStream == null) {
				Log.levelError(plugin, null, "The file &e'$file' &cisn't at the plugin jar.")
				return
			}
			
			Files.copy(inputStream, ioFile.toPath())
			
			Log.levelInfo(plugin, null, "Successful created &b'$file' &afile.")
		} catch (e: Exception) {
			Log.levelError(plugin, null, "Failed to create the file &e'$file'&c.")
			
			e.printStackTrace()
		}
	}
}