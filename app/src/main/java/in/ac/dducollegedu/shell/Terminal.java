package in.ac.dducollegedu.shell;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import java.io.IOException;

import in.ac.dducollegedu.shell.databinding.ActivityMainBinding;
import in.ac.dducollegedu.shell.databinding.FragmentTerminalBinding;


public class Terminal extends Fragment {
    String terminalPrompt;
    Shell terminal;
    FragmentTerminalBinding binding;
    boolean busy = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_terminal, container, false);
        setHasOptionsMenu(true);
        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.navdrawer_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return NavigationUI.onNavDestinationSelected(item, Navigation.findNavController(requireView()))
            || super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        terminalPrompt = "android:%s/~$ ";
        try {
            terminal = new Shell("/system/bin/sh");
        } catch (IOException e) {
            //Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
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
            return ;
        }
        binding.terminalView.append(command + "\n");
        try {
            String output = terminal.runCommand(command);
            binding.terminalView.append(output);
        } catch (Exception e) {
            binding.terminalView.append("\n" + terminalPrompt);
            //Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            return;
        }
        binding.terminalView.append("\n" + String.format(terminalPrompt, terminal.runCommand("pwd")));
        binding.commandInput.setText("");
        binding.terminalScroll.fullScroll(ScrollView.FOCUS_DOWN);
        binding.sendToTerminal.setImageResource(android.R.drawable.ic_menu_send);
    }
}