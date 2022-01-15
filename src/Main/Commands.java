package Main;

import java.awt.Color;
import java.util.Random;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import static Main.Resources.*;

public class Commands extends ListenerAdapter {
	private String prefix = "-";

	public String getPrefix() {
		return prefix;
	}

	private String[] unallowed = { "dũng", "dung", "công", "đức anh", "đa", "đanh", "đ.anh", "đ. anh", "bale", "bêu",
			"beu", "da", "d.anh", "danh" };

	private boolean isUnallowed(String s) {
		for (String word : unallowed) {
			if (s.toLowerCase().contains(word)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split(" ");
		Random rd = new Random();

		// test command
		if (args[0].equalsIgnoreCase(prefix + "test")) {

			event.getChannel().sendMessage("I'm fuckin' alive!").queue();
		}

		// choose command
		else if (args[0].equalsIgnoreCase(prefix + "choose")) {
			StringBuilder line = new StringBuilder();
			for (int i = 1; i <= args.length - 1; i++) {
				line.append(args[i] + " ");
			}
			String[] choices = line.toString().split("/");
			String choice = choices[rd.nextInt(choices.length)].trim();

			event.getChannel().sendMessage("I choose **" + choice + "**!").queue();
		}

		// random command
		else if (args[0].equalsIgnoreCase(prefix + "random")) {
			int a = Integer.parseInt(args[1]), b = Integer.parseInt(args[2]);
			int ans = a + rd.nextInt(b - a + 1);

			event.getChannel().sendMessage("You got **" + ans + "**!").queue();
		}

		// info command
		else if (args[0].equalsIgnoreCase(prefix + "info")) {
			EmbedBuilder embed = new EmbedBuilder();
			embed.setTitle("Bot Info");
			embed.setDescription("A bot that is created for fun");
			embed.addField("Commands", "See `" + prefix + "help` for list of commands.", false);
			embed.setFooter("Bot created by Aylesworth#0764");
			embed.setColor(Color.YELLOW);

			event.getChannel().sendMessageEmbeds(embed.build()).queue();
			embed.clear();
		}

		// help command
		else if (args[0].equalsIgnoreCase(prefix + "help")) {
			EmbedBuilder embed = new EmbedBuilder();
			embed.setTitle("List of Commands");
			embed.addField("`" + prefix + "info`", "Bot info", false);
			embed.addField("`" + prefix + "help`", "List of commands", false);
			embed.addField("`" + prefix + "test`", "Check if the bot is alive", false);
			embed.addField("`" + prefix + "choose choice1 / choice2 / ...`", "Get the bot to make a choice", false);
			embed.addField("`" + prefix + "random a b`", "Get a random number in range a to b (inclusive)", false);
			embed.addField("`" + prefix + "tế somebody`", "Get văn mẫu", false);
			embed.setFooter("Bot created by Aylesworth#0764");
			embed.setColor(Color.GREEN);

			event.getChannel().sendMessageEmbeds(embed.build()).queue();
			embed.clear();
		}

		// tế command
		else if (args[0].equalsIgnoreCase(prefix + "tế")) {
			StringBuilder all = new StringBuilder();
			for (int i = 1; i < args.length; i++) {
				all.append(args[i] + " ");
			}
			String name = all.toString().trim();
			if (isUnallowed(name)) {
				name = event.getMember().getAsMention();
			}
			String generated = texts[rd.nextInt(texts.length)].replaceAll("@", name);
			try {
				event.getChannel().sendMessage(generated).queue();
			} catch (IllegalArgumentException e) {
				int i = generated.length() / 2;
				while(generated.charAt(i) != ' ') i++;
				System.out.println("Illegal Argument Exception: " + generated);
				event.getChannel().sendMessage(generated.substring(0, i)).queue();
				event.getChannel().sendMessage("(continued)\r\n"+generated.substring(i+1, generated.length())).queue();
			}
		}
	}
}
