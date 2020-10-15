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
        return binding.getRoot();
    }



    /**
     * This function is called when fragment is already created and loading.
     * Before fragment loads, set some settings values and initialise the required parameter for
     * further use.
     */
    @Override
    public void onStart() {
        // Getting settings object
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getContext());

        // Getting settings values
        String promptName = sharedPreferences.getString("shell_prompt_name", "android");
        String shellPath = sharedPreferences.getString("shell_path", "/system/bin/sh");
        String home = sharedPreferences.getString("home", "/");

        // Setting the preference settings values
        terminalPrompt = promptName + ":%s/~$ ";
        try {
            terminal = new Shell(shellPath);
            terminal.runCommand("cd "+home);
        } catch (IOException e) {
            Toast.makeText(this.getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
        }
        binding.terminalView.setText(String.format(terminalPrompt, terminal.runCommand("pwd")));
        binding.sendToTerminal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSendButton();
            }
        });
        super.onStart();
    }



    public void onClickSendButton() {
        String command = binding.commandInput.getText().toString();
        if (command.equals("clear")) {
            binding.terminalView.setText("");
            binding.terminalView.append("\n" + String.format(terminalPrompt, terminal.runCommand("pwd")));
            binding.commandInput.setText("");
            return ;
        }
        binding.terminalView.append(command + "\n");
        try {
            String output = terminal.runCommand(command);
            binding.terminalView.append(output);
        } catch (Exception e) {
            binding.terminalView.append("\n" + terminalPrompt);
            Toast.makeText(this.getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
            return;
        }
        binding.terminalView.append("\n\n" + String.format(terminalPrompt, terminal.runCommand("pwd")));
        binding.commandInput.setText("");
        binding.terminalScroll.fullScroll(ScrollView.FOCUS_DOWN);
        binding.sendToTerminal.setImageResource(android.R.drawable.ic_menu_send);
    }
}