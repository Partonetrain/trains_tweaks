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


    static {
        builder = new ModConfigSpec.Builder();
        registerConfig(builder);
        SPEC = builder.build();
    }

    public static void registerConfig(ModConfigSpec.Builder builder) {

        ENABLED = builder.comment("Whether or not to enable any of the tweaks relating to npcs/villagers/illagers")
                .define("Enable NPC tweaks",true);

        WANTED_ITEMS_TAG = builder.comment("If enabled, items that all villagers want to pick up will be converted to the item tag " + Constants.VILLAGER_WANTS_TAG)
                .comment("This tag includes all the usual items by default EXCEPT for torchflowers and pitcher pods (because there is no reason for villagers to farm these)")
                .comment("Farmer villagers are hardcoded to want Bone Meal in addition to these items")
                .comment("This functionality disables itself if Smarter Farmers is installed")
                .define("Use Wanted Items Tag", !Services.PLATFORM.isModLoaded("smarterfarmers"));
        //defaults to off is smarterfarmers is installed, but can still be manually set to true
        //if this is the case, the mixin still checks for the mod, and has no effect if it is present

        ILLAGERS_ATTACK_BABIES = builder.comment("If enabled, illagers will be allowed to attack baby villagers")
                .comment("Only enable this if you are evil")
                .define("Illagers Attack Babies", false);
    }
}
