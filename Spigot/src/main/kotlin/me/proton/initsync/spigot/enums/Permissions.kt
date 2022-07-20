package me.proton.initsync.spigot.enums

enum class Permissions (perm: String) {
	COMMAND_HELP ("command.help"),
	COMMAND_RELOAD ("command.reload"),
	COMMAND_MAINTENANCE ("command.maintenance");
	
	private val perm: String
	
	init { this.perm = perm }
	
	fun getPerm(): String { return "protonmotd.$perm" }
}