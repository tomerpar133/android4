package com.example.shaysheli.assignment3;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.example.shaysheli.assignment3.Model.Student;
import com.example.shaysheli.assignment3.dummy.DummyContent;

public class MainActivity extends Activity implements StudentListFragment.OnListFragmentInteractionListener{
    public static Activity mainActivity;
    public static int RESAULT_CHANGED = 200;
    public static int RESAULT_DEL = 300;
    public static int RESAULT_KEEP = 404;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StudentListFragment listFragment = StudentListFragment.newInstance(1);
        FragmentTransaction tran = getFragmentManager().beginTransaction();
        tran.add(R.id.main_container, listFragment);
        tran.commit();
    }

    @Override
    public void onListFragmentInteraction(Student item) {

    }
}
