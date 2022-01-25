package aylesw.meteor.command.commands;

import aylesw.meteor.Config;
import aylesw.meteor.command.CommandContext;
import aylesw.meteor.command.ICommand;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        List<String> args = ctx.getArgs();
        int n = args.toArray().length;
        MessageAction wrongUsage = ctx.getChannel().sendMessage("Wrong Usage!\n" +
                "You should type `" + Config.getPrefix() + "random [n1] [n2]`\n" +
                "where n1 and n2 are two integers and n1 <= n2");

        if (n != 2) {
            wrongUsage.queue();
            return;
        }

        int a = Integer.parseInt(args.get(0));
        int b = Integer.parseInt(args.get(1));

        if (a > b) {
            wrongUsage.queue();
            return;
        }

        Random rd = new Random();
        int ans = a + rd.nextInt(b - a + 1);

        ctx.getEvent().getChannel().sendMessage("You got **" + ans + "**!").queue();
    }

    @Override
    public String getName() {
        return "random";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("rd", "rand");
    }

    @Override
    public String getHelp() {
        return "Gets a random number between `n1` and `n2`\n" + "Usage: `" + Config.getPrefix() + "random [n1] [n2]`\n" + "Aliases: rd, rand";
    }
}

