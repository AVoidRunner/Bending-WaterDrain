package me.Mastervrunner.Drained;

import java.util.Collection;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.Validate;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.PlantAbility;
import com.projectkorra.projectkorra.ability.WaterAbility;
import com.projectkorra.projectkorra.util.DamageHandler;
import com.projectkorra.projectkorra.util.ParticleEffect;

public class Drained extends PlantAbility implements AddonAbility {

	private long chargeTime;
	private int abilityState;
	
	private Location loc;
	
	private long waterCollected;
	
	public Drained(Player player) {
		super(player);
		
		setField();
		start();
	}

	public void setField() {
		chargeTime = 2000;
		abilityState = 0;
	}
	
	@Override
	public void progress() {
		
		/* WRITE YOUR CODE HERE */
		//Controls that will remove the move goes here.
		//(Region protection check, ability range check, ability duration check etc.)
		/* WRITE YOUR CODE HERE */
		
		if (abilityState == 0) { //State 0 means ability has started but it is not charged yet.
			if (!player.isSneaking()) {
				remove(); //Don't add cooldown to the ability if it is removed from here.
				return; 
			} else {
				/* WRITE YOUR CODE HERE */
				//Not charged particle codes goes here.
				/* WRITE YOUR CODE HERE */
				
				ParticleEffect.WATER_WAKE.display(GeneralMethods.getMainHandLocation(player), 0, 0, 0, 0, 1);
				
				Location playerLookBlock = player.getTargetBlock(null, 100).getLocation();
			
				
				List<Block> blocks = GeneralMethods.getBlocksAroundPoint(playerLookBlock, 5);
				
				for(Block blocky: blocks){
					if(WaterAbility.isPlantbendable(player, blocky.getType(), false)) {
						if(blocky.getType() != Material.DEAD_BUSH) {
							waterCollected++;
							blocky.setType(Material.DEAD_BUSH);
							
							if(waterCollected >= 3) {
								abilityState = 1;
							}
						}
					}
				}
				
			}
		}
		
		else if (abilityState == 1) { //State 1 means ability is charged but not released yet.
			if (!player.isSneaking()) {
				abilityState++;
			} else {
				/* WRITE YOUR CODE HERE */
				//Charged particle codes goes here.
				/* WRITE YOUR CODE HERE */
				ParticleEffect.WATER_WAKE.display(GeneralMethods.getMainHandLocation(player), 0, 3, 3, 3, 1);
			}
		} else if (abilityState == 2) { //State 2 means ability is launched.
			Collection<Player> aroundPlayers = GeneralMethods.getPlayersAroundPoint(player.getLocation(), 10);
			for(Player p : aroundPlayers) {
				if ((p instanceof LivingEntity) && p.getUniqueId() != player.getUniqueId()) {
					drawLine(player.getLocation(), p.getLocation(), 0.5);
					
					DamageHandler.damageEntity(p, 10, this);
					waterCollected-= 3;
					remove();
					return;
				}
			}
		}
	}
	
	public void drawLine(Location point1, Location point2, double space) {
	    World world = point1.getWorld();
	    Validate.isTrue(point2.getWorld().equals(world), "Lines cannot be in different worlds!");
	    double distance = point1.distance(point2);
	    Vector p1 = point1.toVector();
	    Vector p2 = point2.toVector();
	    Vector vector = p2.clone().subtract(p1).normalize().multiply(space);
	    double length = 0;
	    for (; length < distance; p1.add(vector)) {
	        world.spawnParticle(Particle.DRIP_WATER, p1.getX(), p1.getY(), p1.getZ(), 1);
	        length += space;
	    }
	}
	
	@Override
	public long getCooldown() {
		// TODO Auto-generated method stub
		return 1000;
	}

	@Override
	public Location getLocation() {
		// TODO Auto-generated method stub
		return loc;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Drained";
	}

	@Override
	public boolean isHarmlessAbility() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSneakAbility() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getAuthor() {
		// TODO Auto-generated method stub
		return "MasterV";
	}

	@Override
	public String getVersion() {
		// TODO Auto-generated method stub
		return "1.0";
	}

	@Override
	public void load() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

}