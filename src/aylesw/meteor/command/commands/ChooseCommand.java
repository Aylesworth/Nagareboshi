package aylesw.meteor.command.commands;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import aylesw.meteor.Config;
import aylesw.meteor.command.CommandContext;
import aylesw.meteor.command.ICommand;

public class ChooseCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        Random rd = new Random();
        StringBuilder str = new StringBuilder();
        for (String arg : ctx.getArgs()) {
            str.append(arg + " ");
        }
        String[] choices = str.toString().split("/");
        String ans = choices[rd.nextInt(choices.length)].trim();
        ctx.getEvent().getChannel().sendMessage("I choose **" + ans + "**!").queue();
    }

    @Override
    public String getName() {
        return "choose";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("ch");
    }

    @Override
    public String getHelp() {
        return "Chooses one from given choices\n" + "Usage: `" + Config.getPrefix() + "choose [choice 1] / [choice 2] / ...`\n" + "Aliases: ch";
    }
}

