package aylesw.meteor.command;

public class Common {
    public static void divideMessageIfNeeded(String s, CommandContext ctx) {
        try {
            ctx.getChannel().sendMessage(s).queue();
        } catch (IllegalArgumentException e) {
            int i = s.length() / 2;
            while (i < s.length() && s.charAt(i) != '<' && s.charAt(i) != ' ' && s.charAt(i) != '\n')
                i++;
            if (i == s.length()) i = s.length() / 2;
            divideMessageIfNeeded(s.substring(0, i), ctx);
            divideMessageIfNeeded(s.substring(i), ctx);
        }
    }
}
