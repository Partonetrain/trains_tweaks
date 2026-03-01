package info.partonetrain.trains_tweaks.feature.quasi;

import info.partonetrain.trains_tweaks.CommonClass;
import info.partonetrain.trains_tweaks.IEarlyConfigReader;
import info.partonetrain.trains_tweaks.ModFeature;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.data.DataProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.DropperBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class QuasiFeature extends ModFeature implements IEarlyConfigReader {
    public static boolean configRead = false;

    public static boolean enabled = false;
    public static QuasiMode dispenserMode = null;
    public static QuasiMode pistonMode = null;

    public QuasiFeature() {
        super("Quasi", QuasiFeatureConfig.SPEC);
        incompatibleMods.add("noquasi");
        incompatibleMods.add("carpet");
    }

    public static boolean isDispenserUsable(){
        return dispenserMode == QuasiMode.SHIFT_RCLICK_OPT_OUT || dispenserMode == QuasiMode.SHIFT_RCLICK_OPT_IN;
    }

    public static boolean isPistonUsable(){
        return pistonMode == QuasiMode.SHIFT_RCLICK_OPT_OUT || pistonMode == QuasiMode.SHIFT_RCLICK_OPT_IN;
    }

    public static void sendPlayerMessage(ServerPlayer player, BlockState state, BlockPos pos, boolean removedFromSave){
        boolean quasiEnabled = false;
        boolean isDispenser = state.getBlock() instanceof DispenserBlock;
        if(isDispenser){
            if(QuasiFeatureConfig.DISPENSER_MODE.get() == QuasiMode.SHIFT_RCLICK_OPT_IN){
                quasiEnabled = removedFromSave;
            }
            else if(QuasiFeatureConfig.DISPENSER_MODE.get() == QuasiMode.SHIFT_RCLICK_OPT_OUT){
                quasiEnabled = !removedFromSave;
            }
        }
        else{
            if(QuasiFeatureConfig.PISTON_MODE.get() == QuasiMode.SHIFT_RCLICK_OPT_IN){
                quasiEnabled = removedFromSave;
            }
            else if(QuasiFeatureConfig.PISTON_MODE.get() == QuasiMode.SHIFT_RCLICK_OPT_OUT){
                quasiEnabled = !removedFromSave;
            }
        }
        player.sendSystemMessage(makeComponent(quasiEnabled, state, pos));
    }

    public static Component makeComponent(boolean quasiEnabled, BlockState state, BlockPos pos){
        String cfgString = QuasiFeatureConfig.TOGGLE_MESSAGE.get();
        if(cfgString.isEmpty()){
            return Component.empty();
        }

        MutableComponent ret = Component.empty();

        if(cfgString.contains("$QUASIENABLED")){
            String[] split = cfgString.split("\\$QUASIENABLED");
            ret.append(split[0]);
            cfgString = split[1];
            ret.append(quasiEnabled ? Component.translatable("addServer.resourcePack.enabled") : Component.translatable("addServer.resourcePack.disabled"));
            //these are vanilla translation strings that contain exactly what you'd expect
        }
        if(cfgString.contains("$COORDS")){
            String[] split = cfgString.split("\\$COORDS");
            ret.append(split[0]);
            cfgString = split[1];
            ret.append(Component.literal("X: " + pos.getX() + " Y: " + pos.getY() + " Z: " + pos.getZ()) );
        }
        if(cfgString.contains("$BLOCKNAME")){
            String[] split = cfgString.split("\\$BLOCKNAME");
            ret.append(split[0]);
            ret.append(state.getBlock().getName());
            //last one so add the rest
            ret.append(Component.literal(split[1]));
        }

        return ret.withStyle(ChatFormatting.ITALIC);
    }

    public static boolean isCoordsInLevelData(ServerLevel level, BlockPos pos){
        QuasiSaveData data = QuasiSaveData.getInstance(level);
        return data.contains(pos);
    }

    public static boolean updateCoordsInLevelData(ServerLevel level, BlockPos pos){
        boolean alreadyContains = isCoordsInLevelData(level, pos);
        return updateCoordsInLevelData(level, pos, alreadyContains);
    }

    //returns true if the coords are in the save now
    public static boolean updateCoordsInLevelData(ServerLevel level, BlockPos pos, boolean remove){
        QuasiSaveData data = QuasiSaveData.getInstance(level);
        if(remove){
            data.remove(pos);
            return false;
        }
        else{
            data.add(pos);
            return true;
        }
    }


    @Override
    public void readConfigsEarly() {
        if(this.isIncompatibleLoaded())
        {
            enabled = false;
            return;
        }
        if(configRead){
            return;
        }
        final String configFileLoc = System.getProperty("user.dir") + "\\config\\trains_tweaks\\" + "Quasi" + ".toml";
        Path configFilePath = Paths.get(configFileLoc); //converts to correct path regardless of platform
        try {
            List<String> allLines = Files.readAllLines(configFilePath);
            if (allLines.contains("\"Quasi-connectivity tweaks\" = true")) {
                enabled = true;
            }
            else {
                enabled = false;
                configRead = true;
                return; //don't bother checking the rest
            }

            for (String line : allLines) {
                switch (line) {
                    case "\"Dispenser Quasi Mode\" = \"SHIFT_RCLICK_OPT_IN\"" ->
                            dispenserMode = QuasiMode.SHIFT_RCLICK_OPT_IN;
                    case "\"Dispenser Quasi Mode\" = \"SHIFT_RCLICK_OPT_OUT\"" ->
                            dispenserMode = QuasiMode.SHIFT_RCLICK_OPT_OUT;
                    case "\"Piston Quasi Mode\" = \"SHIFT_RCLICK_OPT_IN\"" ->
                            pistonMode = QuasiMode.SHIFT_RCLICK_OPT_IN;
                    case "\"Piston Quasi Mode\" = \"SHIFT_RCLICK_OPT_OUT\"" ->
                            pistonMode = QuasiMode.SHIFT_RCLICK_OPT_OUT;
                }
            }
            configRead = true;

        } catch (IOException e) {
            CommonClass.printEarlyConfigError("Quasi", e);
            configRead = true;
        }
    }
}
