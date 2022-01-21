package aylesw.meteor.command.commands;

import java.awt.Color;
import java.util.List;

import aylesw.meteor.Config;
import aylesw.meteor.command.CommandContext;
import aylesw.meteor.command.CommandManager;
import aylesw.meteor.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;

public class HelpCommand implements ICommand {
    private final CommandManager manager;

    public HelpCommand(CommandManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(CommandContext ctx) {
        List<String> args = ctx.getArgs();
        MessageChannel channel = ctx.getChannel();
        String credit = "__Bot created by Aylesworth#0764__";

        if (args.isEmpty()) {
            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("List of Commands").setFooter(credit).setColor(Color.GREEN);
            for (ICommand cmd : manager.getCommands()) {
                embed.addField("**" + Config.getPrefix() + cmd.getName() + "**", cmd.getHelp(), false);
            }

            channel.sendMessageEmbeds(embed.build()).queue();
            embed.clear();

            /*
             * StringBuilder builder = new StringBuilder();
             * builder.append("List of commands\n");
             * manager.getCommands().stream().map(ICommand::getName).forEach( (it) ->
             * builder.append('`').append(Config.getPrefix()).append(it).append("`\n") );
             *
             * channel.sendMessage(builder.toString()).queue(); return;
             */
        }

        String search = args.get(0);
        ICommand command = manager.getCommand(search);

        if (command == null) {
            channel.sendMessage("Nothing found for " + search).queue();
            return;
        } else {
            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("Help")
                    .addField("**" + Config.getPrefix() + command.getName() + "**", command.getHelp(), false)
                    .setFooter(credit).setColor(Color.GREEN);
            channel.sendMessageEmbeds(embed.build()).queue();
            embed.clear();
        }
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getHelp() {
        return "Shows the list of commands of the bot\n" + "Usage: `" + Config.getPrefix() + "help [command]`";
    }
}

