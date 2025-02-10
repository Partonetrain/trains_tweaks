package info.partonetrain.trains_tweaks.feature.npc;

import info.partonetrain.trains_tweaks.Constants;
import info.partonetrain.trains_tweaks.platform.Services;
import net.neoforged.neoforge.common.ModConfigSpec;

public class NpcFeatureConfig {
    public static ModConfigSpec.Builder builder;
    public final static ModConfigSpec SPEC;

    public static ModConfigSpec.BooleanValue ENABLED;
    public static ModConfigSpec.BooleanValue WANTED_ITEMS_TAG;
    public static ModConfigSpec.BooleanValue ILLAGERS_ATTACK_BABIES;
    public static ModConfigSpec.BooleanValue DISABLE_HERO_EFFECT;
    public static ModConfigSpec.IntValue ENCHANTED_BOOK_MAX;
    public static ModConfigSpec.ConfigValue<String>  MAJOR_NEGATIVE;
    public static ModConfigSpec.ConfigValue<String> MINOR_NEGATIVE;
    public static ModConfigSpec.ConfigValue<String> MINOR_POSITIVE;
    public static ModConfigSpec.ConfigValue<String> MAJOR_POSITIVE;
    public static ModConfigSpec.ConfigValue<String> TRADING;

    static {
        builder = new ModConfigSpec.Builder();
        registerConfig(builder);
        SPEC = builder.build();
    }

    public static void registerConfig(ModConfigSpec.Builder builder) {

        ENABLED = builder.comment("Whether or not to enable any of the tweaks relating to npcs/villagers/illagers")
                .define("Enable NPC tweaks",false);

        WANTED_ITEMS_TAG = builder.comment("If enabled, items that all villagers want to pick up will be converted to the item tag " + Constants.VILLAGER_WANTS_TAG)
                .comment("This tag includes all the usual items by default EXCEPT for torchflowers and pitcher pods (because there is no reason for villagers to farm these)")
                .comment("Farmer villagers are hardcoded to want Wheat Seeds, Wheat, Beetroot Seeds, and Bone Meal in addition to these items")
                .comment("This functionality disables itself if Smarter Farmers is installed")
                .define("Use Wanted Items Tag", !Services.PLATFORM.isModLoaded("smarterfarmers"));
        //defaults to off is smarterfarmers is installed, but can still be manually set to true
        //if this is the case, the mixin still checks for the mod, and has no effect if it is present

        ILLAGERS_ATTACK_BABIES = builder.comment("If enabled, illagers will be allowed to attack baby villagers")
                .comment("Only enable this if you are evil")
                .define("Illagers Attack Babies", false);

        DISABLE_HERO_EFFECT = builder.comment("If enabled, Hero of the Village will not be granted to players that killed a raider in a raid")
                .comment("Note that this makes the effect unobtainable and the How Did We Get Here advancement unachievable (unless you have a mod that gives it)")
                .define("Disable Hero of the Village", false);

        ENCHANTED_BOOK_MAX = builder.comment("If this is not 0, Enchanted Books sold by villagers will be limited to this level")
                .comment("This cannot be used to increase the levels at which enchanted books are sold")
                .defineInRange("Enchanted Book Level Limit", 0, 0, 255);

        builder.comment("The following options can be used to configure the reputation mechanic with villagers")
                .comment("These options use this format: value (amount gained), weight (multiplier in price calculation), maximum, decay per in-game day, decay per transfer (share penalty)")
                .comment("The default values are identical to vanilla. All values besides weight should be positive")
                .comment("See https://minecraft.wiki/w/Villager#Gossiping for more information on how gossip and reputation works");

        MAJOR_NEGATIVE = builder.comment("The values used by major negative gossip from killing a nearby villager")
                .define("Major Negative Gossip","25, -5, 100, 10, 10");

        MINOR_NEGATIVE = builder.comment("The values used by minor negative gossip from attacking a nearby villager")
                .define("Minor Negative Gossip","25, -1, 200, 20, 20");

        MINOR_POSITIVE = builder.comment("The values used by minor positive gossip from curing a villager")
                .define("Minor Positive Gossip","25, 1, 200, 1, 5");

        MAJOR_POSITIVE = builder.comment("The values used by major positive gossip from curing a villager")
                .comment("Since the decay per transfer is greater than its max amount, this gossip will never be shared by default")
                .define("Major Positive Gossip","20, 5, 20, 0, 100");

        TRADING = builder.comment("The values used by trade gossip from trading with a villager")
                .define("Trading Gossip","2, 1, 25, 2, 20");
    }
}
