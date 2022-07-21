package me.proton.initsync.bungeecord.enums

enum class Permissions (perm: String) {
	ALL_COMMANDS ("command.*"),
	COMMAND_HELP ("command.help"),
	COMMAND_RELOAD ("command.reload"),
	COMMAND_MAINTENANCE ("command.maintenance");
	
	private val perm: String
	
	init { this.perm = perm }
	
	fun getPerm(): String { return "proxymotd.$perm" }
}