package aylesw.meteor.command.commands;

import aylesw.meteor.command.CommandContext;
import aylesw.meteor.command.ICommand;
import aylesw.meteor.simsimi.Client;

import java.util.Arrays;
import java.util.List;

public class ChatCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        List<String> args = ctx.getArgs();
        StringBuilder builder = new StringBuilder();

        for (String arg : args) {
            builder.append(arg).append(" ");
        }

        String qtext = builder.toString().trim();
        String atext = Client.getAnswer(qtext,"en");

        ctx.getChannel().sendMessage(atext).queue();
    }

    @Override
    public String getName() {
        return "chat";
    }

    @Override
    public String getHelp() {
        return "To chat with the bot";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("c");
    }
}
