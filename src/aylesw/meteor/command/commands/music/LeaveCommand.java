package aylesw.meteor.command.commands.music;

import aylesw.meteor.command.CommandContext;
import aylesw.meteor.command.ICommand;
import aylesw.meteor.lavaplayer.GuildMusicManager;
import aylesw.meteor.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.Arrays;
import java.util.List;

public class LeaveCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();

        final Member self = ctx.getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();

        if (!selfVoiceState.inVoiceChannel()) {
            channel.sendMessage("I need to be in a voice channel first!").queue();
            return;
        }

        final Member member = ctx.getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();

        if (!memberVoiceState.inVoiceChannel()) {
            channel.sendMessage("You need to join a voice channel first!").queue();
            return;
        }

        if (!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
            channel.sendMessage("I'm already in a different voice channel!").queue();
            return;
        }

        final Guild guild = ctx.getGuild();
        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(guild);

        musicManager.scheduler.queue.clear();
        musicManager.audioPlayer.stopTrack();

        final AudioManager audioManager = guild.getAudioManager();

        audioManager.closeAudioConnection();
        channel.sendMessage("Left the voice channel.").queue();
    }

    @Override
    public String getName() {
        return "leave";
    }

    @Override
    public String getHelp() {
        return "Makes the bot leave the voice channel";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("lv");
    }
}
