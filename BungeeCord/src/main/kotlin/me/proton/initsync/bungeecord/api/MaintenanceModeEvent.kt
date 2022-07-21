package me.proton.initsync.bungeecord.api

import net.md_5.bungee.api.plugin.Event

class MaintenanceModeEvent (status: Boolean): Event() {
	private var status: Boolean
	
	init { this.status = status }
	
	fun getStatus(): Boolean { return status }
	fun setStatus(status: Boolean) { this.status = status }
}