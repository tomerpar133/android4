package com.example.shaysheli.assignment3;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.shaysheli.assignment3.Fragments.*;
import com.example.shaysheli.assignment3.Fragments.AddOrEditFragment;
import com.example.shaysheli.assignment3.Model.Student;

public class MainActivity extends Activity implements StudentListFragment.OnListFragmentInteractionListener, StudentDetailsFragment.OnFragmentInteractionListener, AddOrEditFragment.OnFragmentInteractionListener{
    public static Activity mainActivity;
    public static int RESAULT_CHANGED = 200;
    public static int RESAULT_DEL = 300;
    public static int RESAULT_KEEP = 404;
    public static FragmentTransaction tran;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        StudentListFragment listFragment = StudentListFragment.newInstance(1);
        tran = getFragmentManager().beginTransaction();
        tran.add(R.id.main_container, listFragment);
        tran.commit();
    }

    @Override
    public void onListFragmentInteraction(Student item, String type) {
        if (type.equals("Edit"))
        {
            StudentDetailsFragment details = StudentDetailsFragment.newInstance(item.id);
            tran = getFragmentManager().beginTransaction();
            tran.replace(R.id.main_container, details).commit();
        } else {
            AddOrEditFragment add = AddOrEditFragment.newInstance(null, "Add");
            tran = getFragmentManager().beginTransaction();
            tran.replace(R.id.main_container, add).commit();
        }
    }

    @Override
    public void onFragmentInteraction(String stid) {
        AddOrEditFragment details = AddOrEditFragment.newInstance(stid, "Edit");
        tran = getFragmentManager().beginTransaction();
        tran.replace(R.id.main_container, details).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
