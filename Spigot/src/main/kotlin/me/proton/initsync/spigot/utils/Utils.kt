package me.proton.initsync.spigot.utils

import com.cryptomorin.xseries.XSound
import com.cryptomorin.xseries.messages.Titles
import org.bukkit.Sound
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.Optional

class Utils {
	companion object {
		/**
		 * Send a title.
		 *
		 * @param player player to send the title.
		 * @param fadeIn first time parameter.
		 * @param stay second time parameter.
		 * @param fadeOut third time parameter.
		 * @param paramTitle title message.
		 * @param paramSubtitle subtitle message.
		 */
		fun sendTitle(
			player: Player,
			fadeIn: Int,
			stay: Int,
			fadeOut: Int,
			paramTitle: String,
			paramSubtitle: String
		) {
			val title = Text.process(paramTitle)
			val subtitle = Text.process(paramSubtitle)
	
			Titles.sendTitle(
				player,
				fadeIn,
				stay,
				fadeOut,
				title,
				subtitle
			)
		}
		
		/**
		 * Replay a sound.
		 *
		 * @param player player to replay the sound.
		 * @param sound sound id
		 * @param volumeLevel volume level for the sound.
		 * @param pitchLevel pitch for the sound.
		 */
		fun play(
			player: Player,
			sound: String,
			volumeLevel: Float,
			pitchLevel: Float
		) {
			val soundOptional: Optional<XSound> = XSound.matchXSound(sound)
			if (!soundOptional.isPresent) {
				Log.levelError(player,
					"Failed to replay the sound.",
					"The sound is empty."
				)
				return
			}
			
			val bukkitSound: Sound = soundOptional.get().parseSound()!!
			
			player.playSound(
				player.location,
				bukkitSound,
				volumeLevel,
				pitchLevel
			)
		}
		
		/**
		 * Uniquely build a message with the strings for send to player.
		 *
		 * @param sender to whom send the message.
		 * @param strings message strings.
		 */
		fun message(sender: CommandSender, vararg strings: String) {
			for (string in strings) {
				var source = string
				source = Text.process(source)
				
				sender.sendMessage(source)
			}
		}
	}
}