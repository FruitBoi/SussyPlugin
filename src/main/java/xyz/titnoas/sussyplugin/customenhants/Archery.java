package xyz.titnoas.sussyplugin.customenhants;

import com.destroystokyo.paper.event.entity.ProjectileCollideEvent;
import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.util.Vector;
import xyz.titnoas.sussyplugin.SussyPlugin;
import xyz.titnoas.sussyplugin.utilshit.ProjectileTargetPair;

import java.util.*;

public class Archery extends CustomEnchant implements Listener {

	public List<ProjectileTargetPair> pairs = new ArrayList<>();

	public Archery() {
		this.allowedItems = List.of(Material.BOW);
		this.key = "ARCHERY";
		this.localizedName = "Archery";
		Bukkit.getPluginManager().registerEvents(this, SussyPlugin.sussyPlugin);
		this.maxLevel = 10;
	}

	@EventHandler
	public void onTick(ServerTickEndEvent ev) {

		for (int i = 0; i < pairs.size(); i++) {
			ProjectileTargetPair pair = pairs.get(i);
			try {


				Vector realEntLoc = pair.target.getLocation().toVector();

				Vector entLoc = new Vector(realEntLoc.getX(), realEntLoc.getY() + (pair.target.getHeight() / 2), realEntLoc.getZ());

				Vector towards = entLoc.subtract(pair.projectile.getLocation().toVector()).normalize();

				double magnitude = pair.projectile.getVelocity().length();

				if (Math.abs(magnitude) <= 0.05) {
					pairs.remove(pair);
					continue;
				}

				if (pair.target.isDead() || !pair.target.isValid()) {
					pairs.remove(pair);
					continue;
				}
				Vector newVel = towards.multiply(magnitude);

				Vector difference = newVel.subtract(pair.projectile.getVelocity());

				Vector correction = difference.multiply(pair.correctionCoefficient);

				pair.projectile.setVelocity(pair.projectile.getVelocity().add(correction));
			}catch(Exception e){
				pairs.remove(pair);
			}
		}

	}

	@EventHandler
	public void onCollided(ProjectileCollideEvent ev){

		for(int i = 0; i < pairs.size(); i++){
			var pair = pairs.get(i);

			if(pair.projectile.getUniqueId() != ev.getEntity().getUniqueId())
				continue;

			pairs.remove(pair);
		}
	}

	@EventHandler
	public void onBowFired(EntityShootBowEvent ev) {

		var arrow = ev.getConsumable();
		var bow = ev.getBow();

		if (arrow == null || bow == null)
			return;

		if (!(ev.getEntity() instanceof Player))
			return;

		if (!this.itemHasCustomEnchant(bow))
			return;

		ProjectileTargetPair pair = new ProjectileTargetPair();
		pair.projectile = (Projectile) ev.getProjectile();

		World world = ev.getEntity().getWorld();

		var near = world.getNearbyLivingEntities(ev.getEntity().getLocation(), 150).toArray();

		float closestDist = 999999999.9f;
		Entity target = null;
		double angle = 360.0f;

		Arrays.asList(near).sort(Comparator.comparingDouble(e -> {

			Vector realEntLoc = ((Entity)e).getLocation().toVector();

			Vector entLoc = new Vector(realEntLoc.getX(), realEntLoc.getY() + (((Entity) e).getHeight() / 2), realEntLoc.getZ());

			Vector destDir = entLoc.subtract(ev.getEntity().getEyeLocation().toVector());
			Vector playerFacing = ev.getEntity().getEyeLocation().getDirection();

			double facingAngle = destDir.angle(playerFacing);

			if(((Entity)e).getLocation().distance(ev.getEntity().getEyeLocation()) > 256){
				facingAngle = 99999.9f;
			}

			if(facingAngle > 90)
				facingAngle = 99999.9f;

			if(!(e instanceof LivingEntity))
				facingAngle = 99999.9f;

			if(((Entity) e).getUniqueId() == ev.getEntity().getUniqueId())
				facingAngle = 99999.9f;


			return facingAngle;
		}));

		target = (Entity)near[0];

		if(target == null)
			return;


		Vector realEntLoc = target.getLocation().toVector();

		Vector entLoc = new Vector(realEntLoc.getX(), realEntLoc.getY() + (target.getHeight() / 2), realEntLoc.getZ());

		Vector destDir = entLoc.subtract(ev.getEntity().getEyeLocation().toVector());
		Vector playerFacing = ev.getEntity().getEyeLocation().getDirection();

		double facingAngle = destDir.angle(playerFacing);

		if(target.getLocation().distance(ev.getEntity().getEyeLocation()) > 256){
			target = null;
		}
		if(facingAngle > 25f)
			target = null;

		if(!(target instanceof LivingEntity))
			target = null;

		if(target.getUniqueId() == ev.getEntity().getUniqueId())
			target = null;

		if(target == null)
			return;

		pair.target = target;

		int level = this.GetItemEnchantLevel(bow);

		float percent = (level / 10f);

		pair.correctionCoefficient = percent;

		pairs.add(pair);

	}
}
