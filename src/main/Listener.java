package main;

import main.command.*;
import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Listener extends ListenerAdapter {
	private static final Logger LOGGER = LoggerFactory.getLogger(Listener.class);
	private final CommandManager manager = new CommandManager();
	
	@Override
	public void onReady(@Nonnull ReadyEvent event) {
		LOGGER.info("{} is ready", event.getJDA().getSelfUser().getAsTag());
	}
	
	@Override
	public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
		User user = event.getAuthor();
		if (user.isBot() || event.isWebhookMessage()) {
			return;
		}
		String prefix = Config.getPrefix();
		String raw = event.getMessage().getContentRaw();
		
		if (raw.startsWith(prefix)) {
			manager.handle(event);
		}
	}
}
