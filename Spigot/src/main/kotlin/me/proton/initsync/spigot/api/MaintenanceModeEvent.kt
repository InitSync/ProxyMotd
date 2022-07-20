package me.proton.initsync.spigot.api

import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class MaintenanceModeEvent (status: Boolean): Event() {
	companion object {
		val HANDLERS: HandlerList = HandlerList()
		
		fun getHandlerList(): HandlerList { return HANDLERS }
	}
	
	private var status: Boolean
	
	init { this.status = status }
	
	fun getStatus(): Boolean { return status }
	fun setStatus(status: Boolean) { this.status = status }
	
	override fun getHandlers(): HandlerList { return HANDLERS }
}