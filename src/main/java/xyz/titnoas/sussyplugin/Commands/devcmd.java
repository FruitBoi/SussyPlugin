package xyz.titnoas.sussyplugin.Commands;

import net.minecraft.server.commands.SeedCommand;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.storage.PrimaryLevelData;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_18_R1.CraftWorld;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;

public class devcmd implements CommandExecutor {
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

		if(!(sender instanceof Player player))
		{
			sender.sendMessage("You can only use this command as a player.");
			return true;
		}

		if(!sender.hasPermission("sussyplugin.debug")){
			sender.sendMessage("You do not have permission to use this command.");
			return true;
		}

		if(args.length != 1)
		{
			sender.sendMessage("Invalid arguments: /devcmd (seed)");
			return true;
		}

		long newSeed = 0;

		try{
			newSeed = Long.parseLong(args[0]);
		}catch(NumberFormatException e){
			sender.sendMessage("Invalid seed");
			return true;
		}


		CraftWorld world = (CraftWorld) player.getWorld();

		ServerLevel level = world.getHandle();
		PrimaryLevelData levelData = null;
		try {

			Field seedVar = FindFieldOfTypeInClass(ServerLevel.class, PrimaryLevelData.class);
			levelData = (PrimaryLevelData) seedVar.get(level);

		} catch (IllegalAccessException e) {
			sender.sendMessage("Error.");
			e.printStackTrace();
			return true;
		}

		if(levelData == null){
			sender.sendMessage("Failed to find primary level data");
			return true;
		}

		try {
			Field seedVar = FindFieldOfTypeInClass(WorldGenSettings.class, long.class);
			seedVar.setAccessible(true);
			seedVar.set(levelData.worldGenSettings(), newSeed);
		} catch (IllegalAccessException e) {
			sender.sendMessage("Error. 34");
			e.printStackTrace();
			return true;
		}

		for(var gen : levelData.worldGenSettings().dimensions()){

			try {

				Field seedVar = FindFieldOfTypeInClass(gen.generator().getClass(), long.class);

				seedVar.setAccessible(true);
				seedVar.set(gen.generator(), newSeed);

			} catch (IllegalAccessException e) {
				e.printStackTrace();
				sender.sendMessage("Error. 5");
				continue;
			}

		}

		sender.sendMessage("Successfully set seed to " + newSeed);
		return true;
	}


	private static <T,Z> Field FindFieldOfTypeInClass(Class<T> target, Class<Z> type){
		var worldGenFields = target.getDeclaredFields();

		Field seedVar = null;
		for(Field f : worldGenFields){
			if(f.getType() != type)
				continue;
			seedVar = f;
			break;
		}
		return seedVar;
	}
}
