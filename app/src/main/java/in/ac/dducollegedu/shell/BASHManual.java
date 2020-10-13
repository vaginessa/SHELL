package in.ac.dducollegedu.shell;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import in.ac.dducollegedu.shell.databinding.FragmentBashManualBinding;


public class BASHManual extends Fragment {
    FragmentBashManualBinding binding;
    SharedPreferences sharedPreferences;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bash_manual, container, false);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        /*
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        boolean DARK_MODE = sharedPreferences.getBoolean("dark", false);
        if (DARK_MODE) {
            binding.notesLayout.setBackgroundColor(0);
            binding.part4Notes.color
         */
        super.onStart();
    }
}