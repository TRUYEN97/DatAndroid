/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nextone.common.communication.Impl.Cmd;

import com.nextone.common.communication.AbsCommunicate;
import com.nextone.common.communication.AbsStreamReadable;
import com.nextone.common.communication.IReadStream;
import com.nextone.common.communication.ISender;
import com.nextone.common.communication.ReadStreamOverTime;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class Cmd extends AbsCommunicate implements ISender, IReadStream {

    private Process process;
    private final ProcessBuilder builder;

    public Cmd() {
        this(new ReadStreamOverTime());
    }

    public Cmd(AbsStreamReadable reader) {
        if (reader == null) {
            throw new NullPointerException("StreamReader == null");
        }
        this.input = reader;
        this.builder = new ProcessBuilder();
        this.builder.redirectErrorStream(true);
    }
    private static final List<String> BLOCKED_COMMANDS = Arrays.asList(
            "rm -rf /",
            "rm -rf *",
            ":(){ :|:& };:"
    );
    @Override
    public boolean insertCommand(String command, Object... params) {
        try {
            destroy();
            String newCommand = params.length == 0 ? command : String.format(command, params);
            for (String blocked : BLOCKED_COMMANDS) {
                if (newCommand.contains(blocked)) {
                    return false;
                }
            }
            this.builder.command("sh", "-c", newCommand);
            this.process = builder.start();
            this.input.setReader(process.getInputStream());
            this.out = new PrintStream(process.getOutputStream());
            return true;
        } catch (IOException ex) {
            showException(ex);
            return false;
        }
    }

    public int waitFor() {
        try {
            return this.process.waitFor();
        } catch (InterruptedException ex) {
            return -1;
        }
    }

    public void destroy() {
        try {
            close();
        } catch (IOException e) {
            showException(e);
        }
    }

    @Override
    protected void closeThis() throws IOException {
        if (process != null) {
            process.destroy();
        }
    }
    private static final String PING_LINUX = "ping -c 2 %s";

    public boolean ping(String adder, int cycle) {
        if (adder == null || adder.isBlank()) {
            return false;
        }
        String command = String.format(PING_LINUX, adder);
        for (int i = 0; i < cycle; i++) {
            if (sendCommand(command)) {
                String response = readAll().trim();
                if (response.contains("TTL=") || response.contains("ttl=")) {
                    return true;
                }
            } else {
                break;
            }
        }
        return false;
    }
}
