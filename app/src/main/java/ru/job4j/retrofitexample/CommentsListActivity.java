package ru.job4j.retrofitexample;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class CommentsListActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commemts);
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.comments);
        if (fragment == null) {
            fragment = new CommentsListFragment();
            manager.beginTransaction()
                    .add(R.id.comments, fragment)
                    .commit();
        }


    }
}
