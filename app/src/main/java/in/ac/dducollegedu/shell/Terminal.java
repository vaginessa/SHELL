package in.ac.dducollegedu.shell;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import java.io.IOException;

import in.ac.dducollegedu.shell.databinding.FragmentTerminalBinding;


public class Terminal extends Fragment {
    String terminalPrompt; // Terminal Prompt
    Shell terminal; // Shell object for executing commands from given shell path
    FragmentTerminalBinding binding; // using Data Binding feature
    SharedPreferences sharedPreferences; // Get settings preference object for further setting values
    String home;

    /**
     * This function is called when fragment views are created. Initialising fragment with binding for
     * further reference without using findViewById
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return binding root that is fragment itself
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_terminal, container, false);

        // Getting settings object
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        initializeSHELL();
        return binding.getRoot();
    }

    /**
     * onClickSendButton -> Click Listener for send command button
     *
     * gets commands and run it on shell then get outputs and error if any
     * then sets the output to the terminal and reinitialise terminal prompt
     */
    public void onClickSendButton() {
        // GETTING COMMAND FROM INPUT EDITTEXT VIEW
        String command = binding.commandInput.getText().toString();
        // DOING CLEAR IF COMMAND IS CLEAR COMMAND
        if (command.trim().equals("clear")) {
            binding.terminalView.setText("");
            binding.terminalView.append("\n" + String.format(terminalPrompt, terminal.runCommand("pwd")));
            binding.commandInput.setText("");
            return ;
        }
        // ADDING COMMAND TO TERMINAL
        binding.terminalView.append(command + "\n");
        try {
            // EXECUTING GIVEN COMMAND
            String output = terminal.runCommand(command);
            // ADDING GIVEN COMMAND'S OUTPUT TO TERMINAL
            binding.terminalView.append(output);
        } catch (Exception e) {
            // IF ERROR OCCUR IN RUNNING GIVEN COMMAND
            binding.terminalView.append("\n" + terminalPrompt);
            Toast.makeText(this.getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
            return;
        }
        // ADDING NEW PROMPT TO TERMINAL FOR NEXT COMMAND PLACEMENT
        binding.terminalView.append("\n\n"+String.format(terminalPrompt, terminal.runCommand("pwd").replace(home, "~")));
        // CLEAR COMMAND INPUT EDITTEXT
        binding.commandInput.setText("");
        // AUTO-SCROLL DOWN TERMINAL ON OUTPUT PRODUCED
        binding.terminalScroll.fullScroll(ScrollView.FOCUS_DOWN);
    }

    /**
     * initializeSHELL initialises the basic parameters such as
     * SHELL'S PATH, HOME, setting current working directory as HOME and
     * setting onClickListener for send command button
     */
    private void initializeSHELL() {
        // Getting settings values
        String promptName = sharedPreferences.getString("shell_prompt_name", "android");
        String shellPath = sharedPreferences.getString("shell_path", "/system/bin/sh");
        home = sharedPreferences.getString("home", this.getContext().getFilesDir().getPath()+"/home");
        // Setting the preference settings values
        terminalPrompt = promptName + ":%s/-$ ";
        try {
            terminal = new Shell(shellPath);
            terminal.runCommand("HOME="+home);
            terminal.runCommand("cd");
        } catch (IOException e) {
            Toast.makeText(this.getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
        }
        binding.terminalView.setText(String.format(terminalPrompt, terminal.runCommand("pwd").replace(home, "~")));
        binding.sendToTerminal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSendButton();
            }
        });
    }
}