//package info.partonetrain.trains_tweaks.feature.interdimensional;
//
//import info.partonetrain.trains_tweaks.Constants;
//import net.minecraft.core.Holder;
//import net.minecraft.core.registries.Registries;
//import net.minecraft.resources.ResourceKey;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.server.MinecraftServer;
//import net.minecraft.server.level.ServerLevel;
//import net.minecraft.world.level.dimension.DimensionType;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class AutoResetHandler {
//
//    public static boolean configParsed = false;
//    public static Map<ResourceKey<DimensionType>, Integer> configuredTimes = new HashMap<>();
//
//    private static List<ResourceKey<DimensionType>> markedDimensions = new ArrayList<>();
//
//    public static void tick(MinecraftServer server){
//        if(!markedDimensions.isEmpty()){
//            for (ResourceKey<DimensionType> dimensionTypeResourceKey : markedDimensions) {
//                for (ServerLevel sl : server.getAllLevels()) {
//                    Holder<DimensionType> dimensionTypeHolder = sl.dimensionTypeRegistration();
//                    if(dimensionTypeHolder.is(dimensionTypeResourceKey)) {
//                        if(sl.players().isEmpty()) {
//                            preReset(server, sl);
//                            //doReset();
//                            //postReset();
//                        }
//                    }
//                }
//            }
//
//        }
//    }
//
//    public static void preReset(MinecraftServer server, ServerLevel level){
//        //cache forced chunks maybe?
//        /*
//        LongSet forcedChunks = level.getForcedChunks();
//        level.setChunkForced( x, z, false)
//
//         */
//    }
//
//    public static void doReset(Path dimensionFolder) {
//        try {
//            Files.deleteIfExists(dimensionFolder);
//        } catch (IOException e) {
//            Constants.LOG.error("Failed to delete dimension folder: " + e);
//        }
//    }
//
//    public static void postReset(){
//
//    }
//
//    public static void markDimensionForReset(ResourceKey<DimensionType> dimensionTypeResourceKey){
//        markedDimensions.add(dimensionTypeResourceKey);
//    }
//
//    public static void parseAutoResets(){
//        String cfg = InterdimensionalFeatureConfig.AUTO_RESET.get();
//        List<String> split = List.of(cfg.split(";"));
//
//        for(String s : split) {
//            List<String> split2 = List.of(s.split(","));
//
//            try {
//                ResourceKey<DimensionType> dim = ResourceKey.create(Registries.DIMENSION_TYPE, ResourceLocation.parse(split2.get(0)));
//                int time = Integer.parseInt(split2.get(1));
//
//                configuredTimes.put(dim, time);
//            } catch (Exception e) {
//                Constants.LOG.error("Interdimensional AutoReset parse error: " + e.getMessage());
//                configuredTimes.clear();
//            }
//        }
//
//        Constants.LOG.info("AutoResets parsed");
//        configParsed = true;
//    }
//}
