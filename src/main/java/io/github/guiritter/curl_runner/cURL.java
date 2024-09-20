package io.github.guiritter.curl_runner;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;;

/**
 * Downloads a file through the Tor (The Onion Router) network.
 * Needs the <code>tor</code> package installed.
 * @author Guilherme Alan Ritter
 */
@Component
@Scope(SCOPE_PROTOTYPE)
public final class cURL implements Runnable{

    private String commands[];

    private String fileName;

    private String fileURL;

    private String line;

    private Process process;

    private BufferedReader reader;

    private boolean isTor;

    public cURL configure(String fileName, String fileURL, boolean isTor) {
        this.fileName = fileName;
        this.fileURL = fileURL;
        this.isTor = isTor;

        commands = new String[isTor ? 6 : 4];
        commands[0] = "curl";

        if (isTor) {
            commands[1] = "--socks5";
            commands[2] = "127.0.0.1:9150";
            commands[3] = "-o";
        } else {
            commands[1] = "-o";
        }

        return this;
    }

    @Override
    public void run() {
        commands[isTor ? 4 : 2] = fileName;
        commands[isTor ? 5 : 3] = fileURL;
        try {
            process = Runtime.getRuntime().exec(commands);
            reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            line = null;
            while ((line = reader.readLine()) != null) {
                System.err.println(line);
            }
            process.waitFor();
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
