package xyz.titnoas.sussyplugin.utilshit;

import java.util.Random;

public class Utils {

	private static Random random = new Random();

	public static Random getRandom(){
		return random;
	}

	public static boolean doChance(float percent){
		float chance = random.nextFloat();

		return chance <= percent;
	}

}
