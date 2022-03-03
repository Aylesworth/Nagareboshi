package aylesw.meteor.command.commands;

import aylesw.meteor.command.CommandContext;
import aylesw.meteor.command.Common;
import aylesw.meteor.command.ICommand;
import net.dv8tion.jda.api.JDA;

import java.util.List;

public class PingCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        JDA jda = ctx.getJDA();

        List<String> args = ctx.getArgs();

        if (args.isEmpty()) {
            jda.getRestPing().queue(
                    (ping) -> ctx.getChannel().sendMessageFormat("Reset ping: %sms\nWS ping: %sms", ping, jda.getGatewayPing()).queue()
            );
        } else {
            String arg = args.get(0);
            Common.divideMessageIfNeeded(arg.repeat(200),ctx);
        }
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

