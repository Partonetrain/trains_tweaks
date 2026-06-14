package info.partonetrain.trains_tweaks.feature.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import info.partonetrain.trains_tweaks.Constants;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.network.Filterable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.WrittenBookContent;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class InternetTextBookFunction extends LootItemConditionalFunction {

    public static final MapCodec<InternetTextBookFunction> CODEC = RecordCodecBuilder.mapCodec(
            recordCodecBuilder -> commonFields(recordCodecBuilder)
                    .and(
                            recordCodecBuilder.group(
                                    Codec.STRING.optionalFieldOf("title", "???").forGetter(thisFunction -> thisFunction.bookTitle),
                                    Codec.STRING.optionalFieldOf("author", "Anonymous").forGetter(thisFunction -> thisFunction.bookAuthor),
                                    Codec.STRING.optionalFieldOf("text_source", "example.com").forGetter(thisFunction -> thisFunction.textSource),
                                    Codec.INT.optionalFieldOf("lines_per_page", 1).forGetter(thisFunction -> thisFunction.linesPerPage)
                            )
                    )
                    .apply(recordCodecBuilder, InternetTextBookFunction::new)
    );
    private final String bookTitle;
    private final String bookAuthor;
    private final String textSource;
    private final int linesPerPage;

    InternetTextBookFunction(List<LootItemCondition> conditons, String bookTitle, String bookAuthor, String textSource, int linesPerPage) {
        super(conditons);
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.textSource = textSource;
        this.linesPerPage = linesPerPage;
    }

    public @NotNull LootItemFunctionType<InternetTextBookFunction> getType() {
        return LootFeature.INTERNET_TEXT_BOOK_FUNCTION;
    }

    public @NotNull ItemStack run(ItemStack stack, LootContext context) {
        try {
            List<Filterable<Component>> pages = new ArrayList<>();
            List<String> netLines = getLinesFromAddress(this.textSource);

            List<List<String>> pageLines = nPartition(netLines, linesPerPage);

            for (List<String> linesOnThisPage : pageLines) {
                MutableComponent mutableComponent = Component.empty();
                for (String line : linesOnThisPage) {
                    mutableComponent.append(line);
                }
                Filterable<Component> filterable = Filterable.passThrough(mutableComponent);
                pages.add(filterable);
            }

            Filterable<String> filterableTitle = Filterable.passThrough(this.bookTitle);

            WrittenBookContent content = new WrittenBookContent(filterableTitle, this.bookAuthor, 0, pages, true);
            ItemStack writtenStack = new ItemStack(Items.WRITTEN_BOOK);
            writtenStack.set(DataComponents.WRITTEN_BOOK_CONTENT, content);
            return writtenStack;
        }
        catch (Exception e){
            Constants.LOG.error("Error while fetching lines from " + this.textSource + ": " + e.getMessage());
        }

        return stack;
    }

    public static List<String> getLinesFromAddress(String address) throws IOException, InterruptedException {
        List<String> ret = new ArrayList<>();

        if(!address.startsWith("http")){
            address = "http://" + address;
        }

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(address)).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String content = response.body();
        String[] split = content.split("(\r\n|\r|\n)"); //matches any line break character combination
        for(String s : split){
            ret.add(s + "\n");
        }

        return ret;
    }

    //list partitioning
    //https://stackoverflow.com/questions/5824825/efficient-way-to-divide-a-list-into-lists-of-n-size

    // Source - https://stackoverflow.com/a/50646576
    // Posted by Anshu Srivastava, modified by community. See post 'Timeline' for change history
    // Retrieved 2026-06-12, License - CC BY-SA 4.0
    public static <T> List<List<T>> nPartition(List<T> objs, final int N) {
        return new ArrayList<>(IntStream.range(0, objs.size()).boxed().collect(
                Collectors.groupingBy(e->e/N,Collectors.mapping(e->objs.get(e), Collectors.toList())
                )).values());
    }


}
