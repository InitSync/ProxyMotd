package me.proton.initsync.spigot.enums

import me.proton.initsync.spigot.ProxyMotd
import org.bukkit.plugin.java.JavaPlugin

enum class Configuration (path: String) {
	PREFIX ("config.prefix"),
	
	SOUNDS_ALLOWED ("config.sounds.allow"),
	SOUNDS_NO_PERM ("config.sounds.no-perm"),
	SOUNDS_RELOAD ("config.sounds.reload"),
	SOUNDS_MAINTENANCE ("config.sounds.maintenance"),
	SOUNDS_VOLUME ("config.sounds.volume-level"),
	
	TITLES_ALLOWED ("config.titles.allow"),
	TITLES_FADE_IN ("config.titles.fade-in"),
	TITLES_STAY ("config.titles.stay"),
	TITLES_FADE_OUT ("config.titles.fade-out"),
	
	MOTD_ALLOWED ("config.motd.allow"),
	MOTD_ALLOWED_RANDOM ("config.motd.allow-random-lines"),
	MOTD_ALLOWED_SAMPLES ("config.motd.allow-samples"),
	MOTD_COMMON_UP ("config.motd.common.up"),
	MOTD_COMMON_DOWN ("config.motd.common.down"),
	MOTD_COMMON_SAMPLE ("config.motd.common.sample"),
	MOTD_MAINTENANCE_UP ("config.motd.maintenance.up"),
	MOTD_MAINTENANCE_DOWN ("config.motd.maintenance.down"),
	MOTD_MAINTENANCE_SAMPLE ("config.motd.maintenance.sample"),
	MOTD_ALLOWED_PLAYERS ("config.motd.players-to-zero"),
	MOTD_MAX_PLAYERS ("config.motd.max"),
	MOTD_ONLINE_PLAYERS ("config.motd.online"),
	
	MESSAGES_NO_PERM ("messages.no-perm"),
	MESSAGES_NO_COMMAND ("messages.no-command"),
	
	MESSAGES_HELP ("messages.help"),
	
	MESSAGES_RELOAD ("messages.reload"),
	
	MESSAGES_MAINTENANCE_ON ("messages.maintenance-on"),
	MESSAGES_MAINTENANCE_OFF ("messages.maintenance-off"),
	MESSAGES_MAINTENANCE_TITLE_ON ("messages.maintenance-title-on"),
	MESSAGES_MAINTENANCE_SUBTITLE_ON ("messages.maintenance-subtitle-on"),
	MESSAGES_MAINTENANCE_TITLE_OFF ("messages.maintenance-title-off"),
	MESSAGES_MAINTENANCE_SUBTITLE_OFF ("messages.maintenance-subtitle-off");
	
	private val plugin: ProxyMotd = JavaPlugin.getPlugin(ProxyMotd::class.java)
	private val path: String
	
	init { this.path = path }
	
	fun getPath(): String {
		val prefix: String? = plugin.getConfigHandler()
			.get("config.yml")!!
			.getString("config.prefix")
		
		return plugin.getConfigHandler()
			.get("config.yml")!!
			.getString(path)!!.replace("#prefix", prefix!!)
	}
	
	fun getInt(): Int {
		return plugin.getConfigHandler()
			.get("config.yml")!!
			.getInt(path)
	}
	
	fun getFloat(): Float {
		return plugin.getConfigHandler()
			.get("config.yml")!!
			.getInt(path)
			.toFloat()
	}
	
	fun getList(): List<String> {
		return plugin.getConfigHandler()
			.get("config.yml")!!
			.getStringList(path)
	}
	
	fun check(): Boolean {
		return plugin.getConfigHandler()
			.get("config.yml")!!
			.getBoolean(path)
	}
}