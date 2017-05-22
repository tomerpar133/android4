package com.example.shaysheli.assignment3;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shaysheli.assignment3.Model.Model;
import com.example.shaysheli.assignment3.Model.Student;

public class AddOrEdit extends Activity {
    private static Student stEdit;
    private static Intent returnIntent;
    private static Button btnAddEdit = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        returnIntent = new Intent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_or_edit);
        btnAddEdit = (Button) findViewById(R.id.AddEditButton);
        Button btnAddEditCancel = (Button) findViewById(R.id.AddEditButtonCancel);
        Button btnAddEditDel = (Button) findViewById(R.id.AddEditButtonDel);
        final EditText edtName = (EditText) findViewById(R.id.AddEditName);
        final EditText edtId = (EditText) findViewById(R.id.AddEditId);
        final EditText edtPhone = (EditText) findViewById(R.id.AddEditPhone);
        final EditText edtAddress = (EditText) findViewById(R.id.AddEditAddress);
        final ImageView edtImage = (ImageView) findViewById(R.id.AddEditImage);
        final CheckBox edtCb = (CheckBox) findViewById(R.id.AddEditCB);

        if (getIntent().getStringExtra("txtBTN").equals("ADD")) {
            btnAddEdit.setText("Add");
            stEdit = new Student();
        }else {
            btnAddEdit.setText("Edit");
            btnAddEditDel.setVisibility(View.VISIBLE);
            String stID = getIntent().getStringExtra("stId");
            stEdit = Model.instance.getStudentByID(stID);
            edtAddress.setText(stEdit.address);
            edtId.setText(stEdit.id);
            edtName.setText(stEdit.name);
            edtPhone.setText(stEdit.phone);
        }

        btnAddEditCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(MainActivity.RESAULT_KEEP, returnIntent);
                finish();
            }
        });

        btnAddEditDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Model.instance.rmStu(stEdit);

                setResult(MainActivity.RESAULT_DEL, returnIntent);

                finish();
            }
        });
        btnAddEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stEdit.name = edtName.getText().toString();
                stEdit.address = edtAddress.getText().toString();
                String idToCheck = edtId.getText().toString();
                stEdit.phone = edtPhone.getText().toString();
                stEdit.imageUrl = "../res/drawable/grid.png";
                stEdit.checked = edtCb.isChecked();

                if (((Model.instance.getStudentByID(idToCheck) != null) && (btnAddEdit.getText().equals("Add"))) ||
                    ((!idToCheck.equals(stEdit.id)) && Model.instance.getStudentByID(idToCheck) != null) && (btnAddEdit.getText().equals("Edit")))
                {
                    AlertDialog alertDialog = new AlertDialog.Builder(AddOrEdit.this).create();
                    alertDialog.setTitle("ID IN USE");
                    alertDialog.setMessage("Choose another id");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
                else {
                    stEdit.id = idToCheck;
                    if (Model.instance.editStudent(stEdit)) {
                        setResult(MainActivity.RESAULT_CHANGED, returnIntent);
                        returnIntent.putExtra("stId", stEdit.id);
                    } else {
                        setResult(MainActivity.RESAULT_KEEP, returnIntent);
                    }

                    finish();
                }
            }
        });
    }
}
