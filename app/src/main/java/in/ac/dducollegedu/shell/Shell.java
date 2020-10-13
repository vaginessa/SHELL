package in.ac.dducollegedu.shell;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Scanner;

/**
 * Shell class to initialise any shell available in system.
 */
public class Shell {
    Process shell; // shell process
    OutputStream input; // shell input stream
    InputStream output; // shell output stream
    InputStream error; // shell error stream

    /**
     * Initialise SHELL process and start it. The shell executable path is given as
     * parameter <b> shell </b>
     * 
     * @param shell path in String
     * @throws IOException
     */
    Shell(String shell) throws IOException {
        ProcessBuilder builder = new ProcessBuilder(shell);
        builder.redirectErrorStream(true);
        this.shell = builder.start();
        input = this.shell.getOutputStream();
        output = this.shell.getInputStream();
        error = this.shell.getErrorStream();
    }
    /**
     * This function executes the given command as parameter to initialised SHELL
     * process
     * 
     * @param command
     */
    public String runCommand(String command) {
        String cmdOutput = "";
        try {
            /*
             * Entering Command to shell
             */
            int tries = 500;
            while (cmdOutput.equals("") && tries > 0) {
                tries--;
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(input)
                );
                writer.write(command + "\n");
                writer.flush();
                command = "";
                /*
                 * Getting Output of resultant command
                 */
                cmdOutput += getOutput(output);
                /*
                 * Getting Error output of resultant command
                 */
                cmdOutput += getOutput(error);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        int linefeedi = cmdOutput.lastIndexOf('\n');
        if (linefeedi >= 0 && linefeedi < cmdOutput.length()) {
            cmdOutput = cmdOutput.substring(0, linefeedi);
        }
        return cmdOutput;
    }
    private String getOutput(InputStream input) throws IOException {
        InputStreamReader reader = new InputStreamReader(output);
        String output = "";
        while (reader.ready()) {
            int i = reader.read();
            if (i <= 0) break;
            output += (char) i;
        }
        return output;
    }
    /**
     * Freeup the process memory and destroy the process
     */
    public void finalize() throws Throwable {
        this.shell.destroy();
    }
    public static void main(String[] args) throws Throwable {
        Scanner in = new Scanner(System.in);
        Shell shell = new Shell("/bin/bash");
        while (true) {
            String prompt = "\n"+shell.runCommand("pwd");
            prompt = prompt + ":~$ ";
            System.out.printf(prompt);
            System.out.flush();
            String cmd = in.nextLine();
            if (cmd.equals("exit")) break;
            System.out.println(shell.runCommand(cmd));
            shell.runCommand("\n");
            System.out.flush();
        }
        shell.finalize();
        in.close();
    }
}