package me.proton.initsync.spigot.config

import me.proton.initsync.spigot.ProxyMotd
import me.proton.initsync.spigot.utils.Log
import org.bukkit.configuration.InvalidConfigurationException
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.io.IOException

class ConfigHandler (plugin: ProxyMotd, folder: String?, vararg files: String) {
	private val plugin: ProxyMotd
	private val fileMap: MutableMap<String, File>
	private val configMap: MutableMap<String, FileConfiguration>
	
	init {
		this.plugin = plugin
		this.fileMap = mutableMapOf()
		this.configMap = mutableMapOf()
		
		for (file in files) load(folder, file)
	}
	
	/**
	 * Create a new file if this not exist, else will be load the file.
	 *
	 * @param paramFolder folder of file.
	 * @param paramFile name of file to create/load.
	 */
	private fun load(paramFolder: String?, paramFile: String) {
		val file = File(plugin.dataFolder, paramFile)
		if (!file.exists()) {
			save(paramFolder, paramFile)
			
			Log.levelInfo(null, "Successful created &b'$paramFile' &afile.")
		}
		
		val configuration: FileConfiguration = YamlConfiguration.loadConfiguration(file)
		
		Log.levelInfo(null, "Successful loaded &b'$paramFile' &afile.")
		
		if (!fileMap.containsKey(paramFile)) fileMap.put(paramFile, file)
		if (!configMap.containsKey(paramFile)) configMap.put(paramFile, configuration)
	}
	
	/**
	 * Save the default content of file.
	 *
	 * @param paramFolder folder of file.
	 * @param paramFile name of file.
	 */
	private fun save(paramFolder: String?, paramFile: String) {
		if (paramFolder == null) plugin.saveResource(paramFile, false)
		else {
			val pluginFolder: String = plugin.dataFolder.name
			plugin.saveResource(
				pluginFolder + File.separator + paramFolder + File.separator + paramFile, false
			)
		}
	}
	
	/**
	 * Reload the specified file.
	 *
	 * @param paramFile name of file to reload.
	 */
	fun reload(paramFile: String) {
		if (fileMap.containsKey(paramFile) && configMap.containsKey(paramFile)) {
			try {
				configMap[paramFile]?.load(fileMap[paramFile]!!)
			} catch (e: InvalidConfigurationException) {
				Log.levelError(null, "Failed to reload the file: &e'$paramFile'&c.")
				
				e.printStackTrace()
			}
			return
		}
		
		Log.levelError(null, "The file to reload has not been found.")
	}
	
	/**
	 * Save the specified file.
	 *
	 * @param paramFile name of file to save.
	 */
	fun save(paramFile: String) {
		if (fileMap.containsKey(paramFile) && configMap.containsKey(paramFile)) {
			try {
				configMap[paramFile]?.save(fileMap[paramFile]!!)
			} catch (e: IOException) {
				Log.levelError(null, "Failed to save the file: &e'$paramFile'&c.")
				
				e.printStackTrace()
			}
			return
		}
		
		Log.levelError(null, "The file to save has not been found.")
	}
	
	/**
	 * Get the specified configuration file by her name.
	 *
	 * @param paramFile name of file.
	 *
	 * @return if the file was found, will be return, else will be return null.
	 */
	fun get(paramFile: String): FileConfiguration? {
		return if (configMap.containsKey(paramFile)) configMap[paramFile]
		else {
			Log.levelError(null, "The file to get has not been found.")
			null
		}
	}
}