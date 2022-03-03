package aylesw.meteor.command.commands;

import static aylesw.meteor.resources.TeaseResources.texts;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import aylesw.meteor.Config;
import aylesw.meteor.command.CommandContext;
import aylesw.meteor.command.Common;
import aylesw.meteor.command.ICommand;

public class TeaseCommand implements ICommand {

    @Override
    public void handle(CommandContext ctx) {
        Random rd = new Random();

        if (ctx.getArgs().isEmpty()) {
            ctx.getChannel().sendMessage("You should give at least one word!").queue();
            return;
        }

        StringBuilder builder = new StringBuilder();
        for (String str : ctx.getArgs()) {
            builder.append(str + " ");
        }

        String[] newArgs = builder.toString().split("[|]");

        String name = newArgs[0].trim();
        String generated = "";
        if (newArgs.length == 1) {
            generated = texts[rd.nextInt(texts.length)].replaceAll("@", name);
        } else if (newArgs.length == 2) {
            for (String text : texts) {
                if (text.toLowerCase().contains(newArgs[1].trim().toLowerCase())) {
                    generated = text.replaceAll("@", name);
                    break;
                }
            }
            if (generated.isEmpty()) {
                generated = "No text with `" + newArgs[1] + "` found.";
            }
        } else {
            generated = "Wrong Usage!\n"
                    + "See `" + Config.getPrefix() + "help tease` for the right usage of this command.";
        }

        Common.divideMessageIfNeeded(generated, ctx);
    }

    @Override
    public String getName() {
        return "tease";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("te", "tế");
    }

    @Override
    public String getHelp() {
        return "Teases somebody with a random wall of text\n"
                + "Usage: `" + Config.getPrefix() + "tease [somebody]`\nor `"
                + Config.getPrefix() + "tease [somebody] | [keywords to search for the text you want]`\n"
                + "Aliases: te, tế";
    }

}
