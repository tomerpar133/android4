package com.example.shaysheli.assignment3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shaysheli.assignment3.Model.Model;
import com.example.shaysheli.assignment3.Model.Student;

public class StudentDetail extends Activity {
    static final int REQUEST_FOR_EDIT = 2;
    static String stId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_detail);
        Intent intent = getIntent();
        String stID = intent.getStringExtra("STID");
        Button btnEdit = (Button) findViewById(R.id.btnDetailEdit);

        stId = stID;
        Student stDetail = Model.instance.getStudentByID(stID);
        ImageView stuIMG = (ImageView) findViewById(R.id.stDetailImage);
        TextView nameTXT = (TextView) findViewById(R.id.stDetailName);
        nameTXT.setText(stDetail.name);
        TextView idTXT = (TextView) findViewById(R.id.stDetailId);
        idTXT.setText(stDetail.id);
        TextView phoneTXT = (TextView) findViewById(R.id.stDetailphone);
        phoneTXT.setText(stDetail.phone);
        TextView addressTXT = (TextView) findViewById(R.id.stDetailAddress);
        addressTXT.setText(stDetail.address);
        CheckBox stuCB = (CheckBox) findViewById(R.id.stDetailCB);
        stuCB.setChecked(stDetail.checked);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentDetail.this, AddOrEdit.class);
                intent.putExtra("txtBTN", "EDIT");
                intent.putExtra("stId", stId);
                startActivityForResult(intent, REQUEST_FOR_EDIT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_FOR_EDIT) {
            if (resultCode == MainActivity.RESAULT_CHANGED) {
                Student stDetail = Model.instance.getStudentByID(data.getStringExtra("stId"));
                TextView nameTXT = (TextView) findViewById(R.id.stDetailName);
                nameTXT.setText(stDetail.name);
                TextView idTXT = (TextView) findViewById(R.id.stDetailId);
                idTXT.setText(stDetail.id);
                TextView phoneTXT = (TextView) findViewById(R.id.stDetailphone);
                phoneTXT.setText(stDetail.phone);
                TextView addressTXT = (TextView) findViewById(R.id.stDetailAddress);
                addressTXT.setText(stDetail.address);
                CheckBox stuCB = (CheckBox) findViewById(R.id.stDetailCB);
                stuCB.setChecked(stDetail.checked);
            }else if(resultCode == MainActivity.RESAULT_DEL) {
                Intent returnIntent = new Intent();
                setResult(MainActivity.RESAULT_DEL, returnIntent);

                finish();
            }
        }
    }
}
