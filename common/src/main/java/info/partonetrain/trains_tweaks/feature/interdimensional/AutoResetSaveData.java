//package info.partonetrain.trains_tweaks.feature.interdimensional;
//
//import info.partonetrain.trains_tweaks.CommonClass;
//import net.minecraft.core.HolderLookup;
//import net.minecraft.core.registries.Registries;
//import net.minecraft.nbt.CompoundTag;
//import net.minecraft.resources.ResourceKey;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.server.level.ServerLevel;
//import net.minecraft.world.level.dimension.DimensionType;
//import net.minecraft.world.level.saveddata.SavedData;
//import org.jetbrains.annotations.Nullable;
//
//import java.util.*;
//
//public class AutoResetSaveData extends SavedData {
//
//    static Map<ResourceKey<DimensionType>, Integer> remainingTime = new HashMap<>();
//
//    public static AutoResetSaveData create() {
//        return new AutoResetSaveData();
//    }
//
//    public static AutoResetSaveData load(CompoundTag tag, HolderLookup.Provider lookupProvider) {
//        AutoResetSaveData data = AutoResetSaveData.create();
//        Set<String> keys = tag.getAllKeys();
//        for(String key : keys){
//            Optional<Integer> maybeInt = Optional.of(tag.getInt(key));
//            ResourceLocation rl = ResourceLocation.tryParse(key);
//            ResourceKey<DimensionType> dt = ResourceKey.create(Registries.DIMENSION_TYPE, rl);
//            maybeInt.ifPresent(blockPos -> remainingTime.put(dt, maybeInt.get()));
//        }
//        return data;
//    }
//
//    @Nullable
//    public static AutoResetSaveData getInstance(ServerLevel sl) {
//        if (sl != null) {
//                return sl.getDataStorage().computeIfAbsent(new Factory<>(AutoResetSaveData::new, AutoResetSaveData::load, null), "trains_tweaks_interdimensional_auto_reset");
//        }
//        return null;
//    }
//
//    public void tick(){
//        for(ResourceKey<DimensionType> dimensionKey : remainingTime.keySet()) {
//            update(dimensionKey);
//        }
//    }
//
//    @Override
//    public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
//        for(ResourceKey<DimensionType> dimensionKey : remainingTime.keySet()){
//            int ticksLeft = remainingTime.get(dimensionKey);
//            tag.putInt(dimensionKey.location().toString(), ticksLeft);
//        }
//        return tag;
//    }
//
//    public void add(ResourceKey<DimensionType> dimensionKey, int timeLeft) {
//        remainingTime.put(dimensionKey, timeLeft);
//        CommonClass.printInDev(dimensionKey.toString() + " added: " + String.valueOf(timeLeft));
//        this.setDirty();
//    }
//
//    public void remove(ResourceKey<DimensionType> dimensionKey){
//        Integer removed = remainingTime.remove(dimensionKey);
//        CommonClass.printInDev(dimensionKey.toString() + " removed: " + String.valueOf(removed));
//        this.setDirty();
//    }
//
//    public void update(ResourceKey<DimensionType> dimensionKey) {
//        int timeLeft = remainingTime.remove(dimensionKey);
//        timeLeft--;
//        remainingTime.put(dimensionKey, timeLeft);
//        if(timeLeft == 0){
//            AutoResetHandler.markDimensionForReset(dimensionKey);
//            timeLeft = AutoResetHandler.configuredTimes.get(dimensionKey);
//        }
//        CommonClass.printInDev(dimensionKey.toString() + ", updated, time left: " + String.valueOf(timeLeft));
//        this.setDirty();
//    }
//
//    public boolean contains(ResourceKey<DimensionType> dimensionKey){
//        CommonClass.printInDev(dimensionKey.toString() + " contains: " + remainingTime.get(dimensionKey));
//        return remainingTime.get(dimensionKey) != null;
//    }
//}