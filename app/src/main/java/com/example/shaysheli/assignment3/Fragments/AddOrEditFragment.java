package com.example.shaysheli.assignment3.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

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
public class AddOrEditFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_STID = "STID";
    private static final String ARG_TYPE = "TYPE";
    private static Button btnAddEdit = null;
    private static Student stEdit;

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
        final EditText edtName = (EditText) v.findViewById(R.id.AddEditName);
        final EditText edtId = (EditText) v.findViewById(R.id.AddEditId);
        final EditText edtPhone = (EditText) v.findViewById(R.id.AddEditPhone);
        final EditText edtAddress = (EditText) v.findViewById(R.id.AddEditAddress);
        final ImageView edtImage = (ImageView) v.findViewById(R.id.AddEditImage);
        final CheckBox edtCb = (CheckBox) v.findViewById(R.id.AddEditCB);

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

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
        void onFragmentInteraction(Uri uri);
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
