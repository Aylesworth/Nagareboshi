package aylesw.meteor.command.commands;

import static aylesw.meteor.simsimi.LanguageCode.*;
import aylesw.meteor.Config;
import aylesw.meteor.command.CommandContext;
import aylesw.meteor.command.ICommand;
import aylesw.meteor.simsimi.Client;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class ChatCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        List<String> args = ctx.getArgs();

        if (args.isEmpty()) {
            ctx.getChannel().sendMessage("You must write your text here.\n"
                    + "Usage: `" + Config.getPrefix() + "chat [text]` or `"
                    + Config.getPrefix() + " [text]`").queue();
            return;
        }

        List<String> keys = Arrays.asList(languageCodes.keySet().toArray(String[]::new));
        Collections.sort(keys);

        if (args.size() == 1 && args.get(0).equalsIgnoreCase("lang")) {
            StringBuilder str = new StringBuilder();
            str.append("**List of languages supported**\n")
                    .append("Type `").append(Config.getPrefix()).append("chat set [lang]` to set your language.\n")
                    .append("Type `").append(Config.getPrefix()).append("chat currentlang` to see current language.")
                    .append("\n```");
            for (String key : keys) {
                str.append("\n").append(key).append(" : ").append(languageCodes.get(key));
            }
            str.append("```");

            ctx.getChannel().sendMessage(str.toString()).queue();
            return;
        }

        Guild guild = ctx.getGuild();

        if (args.size() == 2 && args.get(0).equalsIgnoreCase("set")) {
            if (keys.contains(args.get(1))) {
                Client.setLc(guild, args.get(1));
                ctx.getChannel().sendMessage("Language changed to **" + languageCodes.get(args.get(1)) + "**!").queue();
            } else {
                ctx.getChannel().sendMessage("Not a valid language code.\n" +
                        "Type `" + Config.getPrefix() + "chat lang` to see list of languages supported.").queue();
            }
            return;
        }

        if (args.size() == 1 && args.get(0).equalsIgnoreCase("currentlang")) {
            ctx.getChannel().sendMessage("The current language is **" + languageCodes.get(Client.getLc(guild)) + "**.").queue();
            return;
        }

        StringBuilder builder = new StringBuilder();

        for (String arg : args) {
            builder.append(arg).append(" ");
        }

        String qtext = builder.toString().trim();
        String atext = Client.getAnswer(qtext, guild);

        ctx.getChannel().sendMessage(atext).queue();
    }

    @Override
    public String getName() {
        return "chat";
    }

    @Override
    public String getHelp() {
        return "To chat with the bot\n" + "Usage: `" + Config.getPrefix() + "chat [text]`\n"
                + "Aliases: c, or you can even type `- [text]`";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("c");
    }
}
