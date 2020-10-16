package in.ac.dducollegedu.shell;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import in.ac.dducollegedu.shell.databinding.FragmentEditorBinding;


public class Editor extends Fragment {
    FragmentEditorBinding binding;
    SharedPreferences sharedPreferences;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_editor, container, false);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        binding.run.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    onClickRunButton();
                } catch (IOException e) {}
            }
        });
        super.onStart();
    }
    /**
     * initializeSHELL initialises the basic parameters such as
     * SHELL'S PATH, HOME, setting current working directory as HOME and
     * setting onClickListener for send command button
     */
    private Shell initialize() throws IOException {
        // Getting settings values
        String shellPath = sharedPreferences.getString("shell_path", "/system/bin/sh");
        String home = sharedPreferences.getString("home", this.getContext().getFilesDir().getPath()+"/home");
        Shell shell = new Shell(shellPath);
        shell.runCommand("HOME="+home);
        shell.runCommand("cd");
        return shell;
    }
    public void onClickRunButton() throws IOException {
        String[] code = binding.editCode.getText().toString().split("\n");
        Shell sh = initialize();
        String output = "";
        for (String cmd: code) {
            output += sh.runCommand(cmd);
        }
        binding.output.setText(output);
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(binding.getRoot().getWindowToken(), 0);
    }
}