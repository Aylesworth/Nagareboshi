package Main;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

public class BotStartup {
	public static void main(String[] args) throws LoginException {
		JDABuilder jda = JDABuilder.createDefault("OTMxMzc3ODU1MzkxMjI3OTQ2.YeDjOA.Zsl6zk4BE1aV17yYUOygdJSozes");
		jda.setActivity(Activity.watching("porn"));
		jda.setStatus(OnlineStatus.ONLINE);
		jda.addEventListeners(new Commands());
		jda.build();
	}
}