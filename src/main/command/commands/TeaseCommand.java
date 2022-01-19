package main.command.commands;

import static main.resources.TeaseResources.texts;

import java.util.List;
import java.util.Random;

import main.Config;
import main.command.CommandContext;
import main.command.ICommand;

public class TeaseCommand implements ICommand {

	private void divideMessageIfNeeded(String s, CommandContext ctx) {
		try {
			ctx.getEvent().getChannel().sendMessage(s).queue();
		} catch (IllegalArgumentException e) {
			int i = s.length() / 2;
			while (s.charAt(i) != ' ' && s.charAt(i) != '\n')
				i++;
			divideMessageIfNeeded(s.substring(0, i), ctx);
			divideMessageIfNeeded(s.substring(i + 1, s.length()), ctx);
		}
	}

	@Override
	public void handle(CommandContext ctx) {
		Random rd = new Random();
		
		StringBuilder builder = new StringBuilder();
		for (String str : ctx.getArgs()) {
			builder.append(str + " ");
		}
		
		String name = builder.toString().trim();
		String generated = texts[rd.nextInt(texts.length)].replaceAll("@", name);
		
		divideMessageIfNeeded(generated, ctx);
	}

	@Override
	public String getName() {
		return "tease";
	}

	@Override
	public List<String> getAliases() {
		return List.of("te", "tế");
	}

	@Override
	public String getHelp() {
		return "Teases somebody with a random wall of text\n" + "Usage: `" + Config.getPrefix() + "tease [somebody]`\n"
				+ "Aliases: te, tế";
	}

}
