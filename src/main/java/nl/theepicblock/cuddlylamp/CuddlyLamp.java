package nl.theepicblock.cuddlylamp;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.fabric.api.gamerule.v1.rule.DoubleRule;
import net.minecraft.world.GameRules;

import java.util.Random;

public class CuddlyLamp implements ModInitializer {
	public static final Random CREEPY_DOOR_RANDOM = new Random();
	public static final GameRules.Key<GameRules.IntRule> CREEPY_DOOR_CHANCE = register("creepyDoorChance", GameRules.Category.MISC, GameRuleFactory.createIntRule(20, 0, 100));
	public static final GameRules.Key<GameRules.BooleanRule> ONLY_DARK_OAK = register("creepyDoorOnlyDarkOak", GameRules.Category.MISC, GameRuleFactory.createBooleanRule(false));

	private static <T extends GameRules.Rule<T>> GameRules.Key<T> register(String name, GameRules.Category category, GameRules.Type<T> type) {
		return GameRuleRegistry.register(name, category, type);
	}

	@Override
	public void onInitialize() {

	}
}
