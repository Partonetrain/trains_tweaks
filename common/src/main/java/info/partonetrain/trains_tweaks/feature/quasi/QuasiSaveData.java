package info.partonetrain.trains_tweaks.feature.quasi;

import info.partonetrain.trains_tweaks.CommonClass;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class QuasiSaveData extends SavedData {

    static List<BlockPos> toggledBlocks = new ArrayList<>();

    // Create new instance of saved data
    public static QuasiSaveData create() {
        return new QuasiSaveData();
    }

    // Load existing instance of saved data
    public static QuasiSaveData load(CompoundTag tag, HolderLookup.Provider lookupProvider) {
        QuasiSaveData data = QuasiSaveData.create();
        Set<String> keys = tag.getAllKeys();
        for(String key : keys){
            Optional<BlockPos> maybeBlock = NbtUtils.readBlockPos(tag, key);
            maybeBlock.ifPresent(blockPos -> toggledBlocks.add(blockPos));
        }
        return data;
    }

    @Nullable
    public static QuasiSaveData getInstance(ServerLevel sl) {
        if (sl != null) {
                return sl.getDataStorage().computeIfAbsent(new Factory<>(QuasiSaveData::new, QuasiSaveData::load, null), "trains_tweaks_quasi");
        }
        return null;
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
        int i = 0;
        for(BlockPos pos : toggledBlocks){
            tag.put(String.valueOf(i), NbtUtils.writeBlockPos(pos));
            //this will need to be changed to BlockPos.CODEC in the future
            i++;
        }
        return tag;
    }

    public void add(BlockPos pos) {
        CommonClass.printInDev(pos.toShortString() + " added: " + toggledBlocks.add(pos));
        this.setDirty();
    }

    public void remove(BlockPos pos){
        boolean removed = toggledBlocks.remove(pos);
        CommonClass.printInDev(pos.toShortString() + " removed: " + String.valueOf(removed));
        this.setDirty();
    }

    public boolean contains(BlockPos pos){
        CommonClass.printInDev(pos.toShortString() + " contains: " + toggledBlocks.contains(pos));
        return toggledBlocks.contains(pos);
    }
}