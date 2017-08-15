package app;

import app.cmd.CommandHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by ironman on 01.08.2017.
 */
public class Main {

    private final String GREETING = "Welcome to BitBucket Server notifier for Microsoft Teams";
    private final String HELP_ADVICE = "type 'help' for a list of supported commands, 'start' for start";
    private CommandHandler handler;

    public Main(CommandHandler handler) {
        this.handler = handler;
    }

    public static void main(String[] args) {
        Main app = configure();
        app.start();
    }

    public static Main configure() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-config.xml");
        Main app = (Main) ctx.getBean("app");
        return app;
    }

    private void start() {
        System.out.println(GREETING);
        System.out.println(HELP_ADVICE);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            try {
                String command = br.readLine();
                handler.handle(command);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (handler.quitRequested()) {
                    closeStream(br);
                }
            }
        }
    }

    private void closeStream(BufferedReader br) {
        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
