package info.partonetrain.trains_tweaks.mixin;

import info.partonetrain.trains_tweaks.CommonClass;
import info.partonetrain.trains_tweaks.Constants;
import net.minecraft.CrashReport;
import net.minecraft.ReportType;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.gui.screens.DatapackLoadFailureScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

//Client only!
@Mixin(DatapackLoadFailureScreen.class)
public class _DatapackLoadFailureScreenMixin extends Screen {

    @Unique
    private static boolean trains_tweaks$registryChecked = false;
    @Unique
    private static boolean trains_tweaks$reportSaved = false;
    @Unique
    MultiLineLabel trains_tweaks$nullItemCheckMessage;

    protected _DatapackLoadFailureScreenMixin(Component title) {
        super(title);
    }

    @Inject(method = "init", at=@At("HEAD"))
    public void trains_tweaks$init1(CallbackInfo ci){
        //Constants.LOG.info("init1");
        trains_tweaks$registryChecked = false;
        trains_tweaks$reportSaved = false;
    }

    @Inject(method = "init", at=@At("RETURN"))
    public void trains_tweaks$init2(CallbackInfo ci){
        //Constants.LOG.info("init2");
        AtomicInteger itemCount = new AtomicInteger(0);
        AtomicInteger lastItemId = new AtomicInteger();
        AtomicReference<ResourceLocation> lastItem = null;
        try {
            if (!trains_tweaks$registryChecked){
                BuiltInRegistries.ITEM.stream().forEach(item ->
                        {
                            Constants.LOG.info("item count: " + itemCount);
                            lastItemId.set(BuiltInRegistries.ITEM.getId(item));
                            lastItem.set(BuiltInRegistries.ITEM.getKey(item)); //throws here
                            Constants.LOG.info("The raw ID for " + lastItem + " is " + String.valueOf(lastItemId));
                            itemCount.getAndIncrement();
                        }
                );
                trains_tweaks$registryChecked = true;
            }
            trains_tweaks$nullItemCheckMessage = MultiLineLabel.create(this.font, Component.literal("Train's Tweaks: " + itemCount + " entries in BuiltInRegistries.ITEM" ), this.width - 50);
        }
        catch (NullPointerException npe){
            if(!trains_tweaks$reportSaved) {
                Constants.LOG.error("NullPointerException caught: " + npe.getMessage() + " . worldOpenException: " + (CommonClass.worldOpenException == null));
                Exception exceptionToReport = CommonClass.worldOpenException;
                CrashReport cr = CrashReport.forThrowable(exceptionToReport, "Train's Tweaks null item registry check: " + "\nNPE at " + lastItemId + ". " + itemCount + " / " + CommonClass.getItemCount());
                String crashReportPath = System.getProperty("user.dir") + "\\crash-reports\\" + "trains_tweaks_null_item_registry_check.txt";
                cr.saveToFile(Paths.get(crashReportPath), ReportType.TEST);
                Constants.LOG.error("Saved " + crashReportPath);
                trains_tweaks$reportSaved = true;
            }
            trains_tweaks$nullItemCheckMessage = MultiLineLabel.create(this.font, Component.translatable("trains_tweaks.null_item_registry"), this.width - 50);

        }
    }

    @Inject(method = "render", at=@At("RETURN"))
    public void trains_tweaks$render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci){
        if(trains_tweaks$nullItemCheckMessage != null){
            trains_tweaks$nullItemCheckMessage.renderCentered(guiGraphics, this.width / 2, 200);
        }
    }
}
