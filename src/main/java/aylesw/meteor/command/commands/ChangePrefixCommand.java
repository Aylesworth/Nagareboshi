package aylesw.meteor.command.commands;

import aylesw.meteor.Config;
import aylesw.meteor.command.CommandContext;
import aylesw.meteor.command.ICommand;

import java.util.Arrays;
import java.util.List;

public class ChangePrefixCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        String newPrefix = ctx.getArgs().get(0);
        Config.setPrefix(newPrefix);
        ctx.getChannel().sendMessageFormat("Prefix changed to `%s`.", newPrefix).queue();
    }

    @Override
    public String getName() {
        return "changeprefix";
    }

    @Override
    public String getHelp() {
        return "Changes the bot's prefix";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("setprefix");
    }
}
