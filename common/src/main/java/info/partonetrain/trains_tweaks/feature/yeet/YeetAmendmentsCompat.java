package info.partonetrain.trains_tweaks.feature.yeet;

import info.partonetrain.trains_tweaks.Constants;
import info.partonetrain.trains_tweaks.platform.Services;

public class YeetAmendmentsCompat {

    public static boolean isAmendmentsFireballEnabled(){
        boolean ret = Services.PLATFORM.isModLoaded("amendments") && Services.PLATFORM.isAmendmentsFireballEnabled();
        //Constants.LOG.info("!Amendments fireball: " + ret);
        return ret;
    }

    public static boolean isAmendmentsSnowballEnabled(){
        boolean ret = Services.PLATFORM.isModLoaded("amendments") && Services.PLATFORM.isAmendmentsSnowballEnabled();
        //Constants.LOG.info("!Amendments snowball: " + ret);
        return ret;
    }
}
