package info.partonetrain.trains_tweaks.feature.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import info.partonetrain.trains_tweaks.Constants;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.network.Filterable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.WrittenBookContent;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class InternetMarkdownBookFunction extends LootItemConditionalFunction {

    public static final MapCodec<InternetMarkdownBookFunction> CODEC = RecordCodecBuilder.mapCodec(
            recordCodecBuilder -> commonFields(recordCodecBuilder)
                    .and(
                            recordCodecBuilder.group(
                                    Codec.STRING.optionalFieldOf("title", "???").forGetter(thisFunction -> thisFunction.bookTitle),
                                    Codec.STRING.optionalFieldOf("author", "Anonymous").forGetter(thisFunction -> thisFunction.bookAuthor),
                                    Codec.STRING.optionalFieldOf("text_source", "example.com").forGetter(thisFunction -> thisFunction.textSource),
                                    Codec.INT.optionalFieldOf("lines_per_page", 1).forGetter(thisFunction -> thisFunction.linesPerPage)
                            )
                    )
                    .apply(recordCodecBuilder, InternetMarkdownBookFunction::new)
    );

    private final String bookTitle;
    private final String bookAuthor;
    private final String textSource;
    private final int linesPerPage;

    private static final Pattern LINK_PATTERN = Pattern.compile("^\\[(.*?)]\\((.*?)\\)(.*)", Pattern.DOTALL);

    InternetMarkdownBookFunction(List<LootItemCondition> conditons, String bookTitle, String bookAuthor, String textSource, int linesPerPage) {
        super(conditons);
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.textSource = textSource;
        this.linesPerPage = linesPerPage;
    }

    public @NotNull LootItemFunctionType<InternetMarkdownBookFunction> getType() {
        return LootFeature.INTERNET_MARKDOWN_BOOK_FUNCTION;
    }

    public @NotNull ItemStack run(ItemStack stack, LootContext context) {
        try {
            List<Filterable<Component>> pages = new ArrayList<>();
            List<String> netLines = InternetTextBookFunction.getLinesFromAddress(this.textSource);

            List<Component> componentizedMarkdown = parseMarkdown(netLines);
            List<List<Component>> pageLines = InternetTextBookFunction.nPartition(componentizedMarkdown, linesPerPage);

            for (List<Component> linesOnThisPage : pageLines) {
                MutableComponent mutableComponent = Component.empty();
                for (Component line : linesOnThisPage) {
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

    //adapted from ssblur's unfocused library
    //https://github.com/ssblur/unfocused/blob/main/common/src/main/kotlin/com/ssblur/unfocused/helper/MarkdownFormatter.kt
    //thx blur!
    public static List<Component> parseMarkdown(List<String> markdownLines) {
        List<Component> ret = new ArrayList<>();
        MutableComponent lastComponent = Component.empty();

        for (String originalLine : markdownLines) {
            String line = originalLine;

            String current = "";
            boolean bold = false;
            boolean italic = false;
            boolean strikethrough = false;
            final boolean underlined = false;

            int titleDepth = 0;
            for (char c : line.stripLeading().toCharArray()) {
                if (c == '#'){
                    titleDepth++;
                }
                else {
                    break;
                }
            }

            boolean isTitle = titleDepth > 0;
            if (isTitle) {
                line = line.stripLeading().replaceAll("^[# \t]+", ""); //any number of #s
                ret.add(lastComponent);
                lastComponent = Component.empty();
            }

            //this part does not support multilevel lists
            boolean isList = line.stripLeading().startsWith("* ") || line.stripLeading().startsWith("- ");
            if (isList) {
                line = line.stripLeading().substring(1).stripLeading();
                lastComponent = lastComponent.append("\n  • ");
            }

            while (!line.isEmpty()) {
                if (line.startsWith("***") || line.startsWith("___")) {
                    lastComponent = appendWithStyle(lastComponent, current, bold, italic, strikethrough, underlined);
                    current = "";
                    bold = !bold;
                    italic = !italic;
                    line = line.substring(3);
                    continue;
                }
                else if (line.startsWith("**") || line.startsWith("__")) {
                    lastComponent = appendWithStyle(lastComponent, current, bold, italic, strikethrough, underlined);
                    current = "";
                    bold = !bold;
                    line = line.substring(2);
                    continue;
                }
                else if (line.startsWith("*") || line.startsWith("_")) {
                    lastComponent = appendWithStyle(lastComponent, current, bold, italic, strikethrough, underlined);
                    current = "";
                    italic = !italic;
                    line = line.substring(1);
                    continue;
                }
                else if (line.startsWith("~~")) {
                    lastComponent = appendWithStyle(lastComponent, current, bold, italic, strikethrough, underlined);
                    current = "";
                    strikethrough = !strikethrough;
                    line = line.substring(2);
                    continue;
                }
                else if (line.startsWith("[")) {
                    Matcher linkMatcher = LINK_PATTERN.matcher(line);
                    if (linkMatcher.matches()) {
                        String text = linkMatcher.group(1);
                        String link = linkMatcher.group(2);
                        String remainder = linkMatcher.group(3);

                        lastComponent = appendWithStyle(lastComponent, current, bold, italic, strikethrough, underlined);
                        current = "";

                        lastComponent = lastComponent.append(
                                Component.literal(text).withStyle(
                                        Style.EMPTY
                                                .withBold(bold)
                                                .withItalic(italic)
                                                .withStrikethrough(strikethrough)
                                                .withUnderlined(true)
                                                .withColor(ChatFormatting.BLUE)
                                                .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, link))
                                )
                        );
                        line = remainder;
                        continue;
                    }
                }

                current += line.charAt(0);
                line = line.substring(1);
            }

            if (!current.isEmpty()) {
                lastComponent = appendWithStyle(lastComponent, current, bold, italic, strikethrough, underlined);
            }

            if (isTitle) {
                ret.add(lastComponent.withStyle(Style.EMPTY
                        .withFont(ResourceLocation.parse("minecraft:uniform"))));
            }
            else {
                ret.add(lastComponent);
            }

            lastComponent = Component.empty();
        }

        ret.add(Component.empty());
        return ret;
    }

    public static MutableComponent appendWithStyle(MutableComponent component, String text, boolean bold, boolean italic, boolean strikethrough, boolean underline) {
        return component.append(
                Component.literal(text).withStyle(
                        Style.EMPTY
                                .withBold(bold)
                                .withItalic(italic)
                                .withStrikethrough(strikethrough)
                                .withUnderlined(underline))
        );
    }

}
