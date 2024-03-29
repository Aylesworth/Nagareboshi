package aylesw.meteor.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

import aylesw.meteor.Config;
import aylesw.meteor.command.commands.*;
import aylesw.meteor.command.commands.music.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class CommandManager {
    private final List<ICommand> commands = new ArrayList<>();

    public CommandManager() {
        addCommand(new PingCommand());
        addCommand(new ChatCommand());
        addCommand(new RandomCommand());
        addCommand(new ChooseCommand());
        addCommand(new TeaseCommand());
        addCommand(new ChangePrefixCommand());
        addCommand(new JoinCommand());
        addCommand(new PlayCommand());
        addCommand(new PlayListCommand());
        addCommand(new StopCommand());
        addCommand(new SkipCommand());
        addCommand(new NowPlayingCommand());
        addCommand(new QueueCommand());
        addCommand(new RepeatCommand());
        addCommand(new LeaveCommand());
        addCommand(new HelpCommand(this));
    }

    private void addCommand(ICommand cmd) {
        boolean nameFound = this.commands.stream().anyMatch((it) -> it.getName().equalsIgnoreCase(cmd.getName()));
        if (nameFound) {
            throw new IllegalArgumentException("A command with this name is already present.");
        }
        commands.add(cmd);
    }

    public List<ICommand> getCommands() {
        return commands;
    }

    @Nullable
    public ICommand getCommand(String search) {
        String searchLower = search.toLowerCase();

        for (ICommand cmd : this.commands) {
            if (cmd.getName().equals(searchLower) || cmd.getAliases().contains(searchLower)) {
                return cmd;
            }
        }

        return null;
    }

    public void handle(GuildMessageReceivedEvent event) {
        String contentRaw = event.getMessage().getContentRaw();

        String[] split = contentRaw.replaceFirst("(?i)" + Pattern.quote(Config.getPrefix()), "")
                .split("\\s+");

        if (contentRaw.startsWith(Config.getPrefix() + " ") && split.length > 0) {
            event.getChannel().sendTyping().queue();
            List<String> args = Arrays.asList(split);
            CommandContext ctx = new CommandContext(event, args);
            new ChatCommand().handle(ctx);
            return;
        }

        String invoke = split[0].toLowerCase();
        ICommand cmd = this.getCommand(invoke);
        if (cmd != null) {
            event.getChannel().sendTyping().queue();
            List<String> args = Arrays.asList(split).subList(1, split.length);
            CommandContext ctx = new CommandContext(event, args);
            cmd.handle(ctx);
        }
    }
}
