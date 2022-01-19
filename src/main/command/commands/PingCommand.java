package main.command.commands;

import main.command.CommandContext;
import main.command.ICommand;
import net.dv8tion.jda.api.JDA;

public class PingCommand implements ICommand {
	@Override
	public void handle(CommandContext ctx) {
		JDA jda = ctx.getEvent().getJDA();
		
		jda.getRestPing().queue(
				(ping) -> ctx.getEvent().getChannel().sendMessageFormat("Reset ping: %sms\nWS ping: %sms", ping, jda.getGatewayPing()).queue()
		);
	}
	
	@Override
	public String getHelp() {
		return "Shows the current ping from the bot to the Discord servers";
	}
	
	@Override
	public String getName() {
		return "ping";
	}
	
}