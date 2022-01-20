package aylesw.meteor.command.commands;

import aylesw.meteor.Config;
import aylesw.meteor.command.CommandContext;
import aylesw.meteor.command.ICommand;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        Random rd = new Random();
        int n = ctx.getArgs().toArray().length;
        int[] args = new int[n];
        for (int i = 0; i < n; i++) {
            args[i] = Integer.parseInt(ctx.getArgs().get(i));
        }
        int ans = args[0] + rd.nextInt(args[1] - args[0] + 1);
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
        return "Gets a random number between `a` and `b`\n" + "Usage: `" + Config.getPrefix() + "random a b`\n" + "Aliases: rd, rand";
    }
}

