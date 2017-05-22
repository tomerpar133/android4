package com.example.shaysheli.assignment3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shaysheli.assignment3.Model.Model;
import com.example.shaysheli.assignment3.Model.Student;

import java.util.ArrayList;
import java.util.List;


public class StudentList extends Activity {
    ListView list;
    StudentListAdapter adapter = null;
    static final int REQUEST_ADD_ID = 1;
    static final int REQUEST_EDIT_ID = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_list);

        // layout vars
        list = (ListView) findViewById(R.id.stlist_List);
        Button addStuBTN = (Button) findViewById(R.id.buttonAdd);

        // Manage list
        adapter = new StudentListAdapter();
        list.setAdapter(adapter);

        // Moving to detail student page
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(StudentList.this, StudentDetail.class);
                intent.putExtra("STID", Model.instance.getAllStudents().get(position).id);
                startActivityForResult(intent, REQUEST_EDIT_ID);
            }
        });

        // Add button click event
        addStuBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentList.this, AddOrEdit.class);
                intent.putExtra("txtBTN", "ADD");
                startActivityForResult(intent, REQUEST_ADD_ID);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (((requestCode == REQUEST_ADD_ID) || (requestCode == REQUEST_EDIT_ID)) && ((resultCode == MainActivity.RESAULT_CHANGED) || (resultCode == MainActivity.RESAULT_DEL))){
           adapter.notifyDataSetChanged();
        }
    }

    class StudentListAdapter extends BaseAdapter {
        LayoutInflater _inflater = StudentList.this.getLayoutInflater();

        @Override
        public int getCount() {
            return Model.instance.getAllStudents().size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = _inflater.inflate(R.layout.list_object, null);

                CheckBox cb = (CheckBox) convertView.findViewById(R.id.strow_cb);
                cb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = (int) v.getTag();
                        Student st = Model.instance.getStudentByID(pos + "");
                        st.checked = !st.checked;
                        Model.instance.editStudent(st);
                    }
                });
            }

            if (position == 20)
            {
                list.getAdapter();
            }
            TextView name = (TextView) convertView.findViewById(R.id.strow_name);
            TextView id = (TextView) convertView.findViewById(R.id.strow_id);
            CheckBox cb = (CheckBox) convertView.findViewById(R.id.strow_cb);

            Student st = Model.instance.getAllStudents().get(position);
            name.setText(st.name);
            id.setText(st.id);
            cb.setChecked(st.checked);

            cb.setTag(position);
            return convertView;
        }
    }
}
