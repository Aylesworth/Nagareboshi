package main;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

public class Bot {
	private Bot() throws LoginException {
		JDABuilder.createDefault(Config.getToken())
				.setActivity(Activity.playing("-help")).setStatus(OnlineStatus.ONLINE).addEventListeners(new Listener())
				.build();
	}
	
	public static void main(String[] args) throws LoginException {
		new Bot();
	}
}
