package in.ac.dducollegedu.shell;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.ac.dducollegedu.shell.databinding.FragmentOnlineScriptingBinding;


public class OnlineScripting extends Fragment {
    FragmentOnlineScriptingBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_online_scripting, container, false);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        binding.onlineScriptingWeb.loadUrl(getString(R.string.used_online_shell));
        binding.onlineScriptingWeb.getSettings().setJavaScriptEnabled(true);
        super.onStart();
    }
}