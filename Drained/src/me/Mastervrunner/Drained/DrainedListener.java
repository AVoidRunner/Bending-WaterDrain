package me.Mastervrunner.Drained;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import com.projectkorra.projectkorra.BendingPlayer;

public class DrainedListener implements Listener {

	@EventHandler
	public void onClick(PlayerToggleSneakEvent event) {
		Player player = event.getPlayer();
		BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);

		if (event.isCancelled() || bPlayer == null) {
			return;
		}

		if (bPlayer.getBoundAbilityName().equalsIgnoreCase(null)) {
			return;
		}

		if (bPlayer.getBoundAbilityName().equalsIgnoreCase("Drained")) {
			new Drained(player);
		}
	}
	
}