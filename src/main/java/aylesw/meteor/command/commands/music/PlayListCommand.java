package aylesw.meteor.command.commands.music;

import aylesw.meteor.Config;
import aylesw.meteor.command.CommandContext;
import aylesw.meteor.command.ICommand;
import aylesw.meteor.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

public class PlayListCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();

        if (ctx.getArgs().isEmpty()) {
            channel.sendMessage("You should type `" + Config.getPrefix() + "playlist [playlist name / youtube link]`!").queue();
            return;
        }

        final Member self = ctx.getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();

        final Member member = ctx.getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();

        if (!memberVoiceState.inVoiceChannel()) {
            channel.sendMessage("You need to join a voice channel first!").queue();
            return;
        }

        if (selfVoiceState.inVoiceChannel() && !memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
            channel.sendMessage("I'm already in a different voice channel!").queue();
            return;
        }

        if (!selfVoiceState.inVoiceChannel()) {
            new JoinCommand().handle(ctx);
        }

        String link = String.join(" ", ctx.getArgs());

        if (!isUrl(link)) {
            link = "ytsearch: " + link;
        }

        PlayerManager.getInstance().loadAndPlayList(channel, link);
    }

    @Override
    public String getName() {
        return "playlist";
    }

    @Override
    public String getHelp() {
        return "Plays a list of songs\n" +
                "Usage: `" + Config.getPrefix() + "playlist [song name / youtube link]`\n" +
                "Aliases: pl";
    }

    private boolean isUrl(String url) {
        try {
            new URI(url);
            return true;
        } catch (URISyntaxException e) {
            return false;
        }
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("pl");
    }
}
