package in.ac.dducollegedu.shell;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import in.ac.dducollegedu.shell.databinding.FragmentEditorBinding;


public class Editor extends Fragment {
    FragmentEditorBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_editor, container, false);
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
    public void onClickRunButton() throws IOException {
        String[] code = binding.editCode.getText().toString().split("\n");
        Shell sh = new Shell("/system/bin/sh");
        String output = "";
        for (String cmd: code) {
            output += sh.runCommand(cmd);
        }
        binding.output.setText(output);
    }
}