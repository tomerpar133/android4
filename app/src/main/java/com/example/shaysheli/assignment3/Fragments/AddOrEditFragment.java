package com.example.shaysheli.assignment3.Fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.shaysheli.assignment3.MainActivity;
import com.example.shaysheli.assignment3.Model.Model;
import com.example.shaysheli.assignment3.Model.Student;
import com.example.shaysheli.assignment3.R;

import static com.example.shaysheli.assignment3.Fragments.StudentDetailsFragment.tran;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddOrEditFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddOrEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddOrEditFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_STID = "STID";
    private static final String ARG_TYPE = "TYPE";
    private static Button btnAddEdit = null;
    private static Student stEdit;
    private static EditText edtName = null;
    private static EditText edtId = null;
    private static EditText edtPhone = null;
    private static EditText edtAddress = null;
    private static ImageView edtImage = null;
    private static CheckBox edtCb = null;

    // TODO: Rename and change types of parameters
    private String STID;
    private String TYPE;

    private OnFragmentInteractionListener mListener;

    public AddOrEditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param STID Parameter 1.
     * @return A new instance of fragment AddOrEditFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddOrEditFragment newInstance(String STID, String TYPE) {
        AddOrEditFragment fragment = new AddOrEditFragment();
        Bundle args = new Bundle();
        args.putString(ARG_STID, STID);
        args.putString(ARG_TYPE, TYPE);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        if (getArguments() != null) {
            STID = getArguments().getString(ARG_STID);
            TYPE = getArguments().getString(ARG_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_or_edit, container, false);
        btnAddEdit = (Button) v.findViewById(R.id.AddEditButton);
        Button btnAddEditCancel = (Button) v.findViewById(R.id.AddEditButtonCancel);
        Button btnAddEditDel = (Button) v.findViewById(R.id.AddEditButtonDel);
        edtName = (EditText) v.findViewById(R.id.AddEditName);
        edtId = (EditText) v.findViewById(R.id.AddEditId);
        edtPhone = (EditText) v.findViewById(R.id.AddEditPhone);
        edtAddress = (EditText) v.findViewById(R.id.AddEditAddress);
        edtImage = (ImageView) v.findViewById(R.id.AddEditImage);
        edtCb = (CheckBox) v.findViewById(R.id.AddEditCB);

        if (TYPE.equals("Add")) {
            btnAddEdit.setText("Add");
            stEdit = new Student();
        }else {
            btnAddEdit.setText("Edit");
            btnAddEditDel.setVisibility(View.VISIBLE);
            stEdit = Model.instance.getStudentByID(STID);
            edtAddress.setText(stEdit.address);
            edtId.setText(stEdit.id);
            edtName.setText(stEdit.name);
            edtPhone.setText(stEdit.phone);
        }

        btnAddEditDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Model.instance.rmStu(stEdit)) {
                    AlertDialog alertDialog = new AlertDialog.Builder(v.getContext()).create();
                    alertDialog.setTitle("STUDENT DELETED");
                    alertDialog.setMessage("SUCCESS");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }

                mListener.onFragmentInteractionAddOrEdit();
            }
        });

        btnAddEditCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onFragmentInteractionAddOrEdit();
            }
        });

        btnAddEdit.setOnClickListener(this);

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

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
            AlertDialog alertDialog = new AlertDialog.Builder(v.getContext()).create();
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
                AlertDialog alertDialog = new AlertDialog.Builder(v.getContext()).create();
                alertDialog.setTitle("STUDENT SAVED");
                alertDialog.setMessage("SUCCESS");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
            mListener.onFragmentInteractionAddOrEdit();
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteractionAddOrEdit();
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                switch (TYPE) {
                    // return to list mode
                    case "Add":
                        StudentListFragment listFragment = StudentListFragment.newInstance(1);
                        tran = getFragmentManager().beginTransaction();
                        tran.replace(R.id.main_container, listFragment);
                        tran.commit();
                        break;

                    // return to view mode
                    case "Edit":
                        StudentDetailsFragment details = StudentDetailsFragment.newInstance(STID);
                        tran = getFragmentManager().beginTransaction();
                        tran.replace(R.id.main_container, details).commit();
                        break;
                }
                break;
            default:
                break;
        }

        return true;
    }
}
