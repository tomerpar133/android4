package com.example.shaysheli.assignment3.Fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shaysheli.assignment3.Model.Model;
import com.example.shaysheli.assignment3.Model.Student;
import com.example.shaysheli.assignment3.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StudentDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StudentDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudentDetailsFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_STID = "STID";

    // TODO: Rename and change types of parameters
    private String STID;

    private OnFragmentInteractionListener mListener;
    public static FragmentTransaction tran;

    public StudentDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param STID Parameter 1.
     * @return A new instance of fragment StudentDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StudentDetailsFragment newInstance(String STID) {
        StudentDetailsFragment fragment = new StudentDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_STID, STID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            STID = getArguments().getString(ARG_STID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_student_details, container, false);
        Student stDetail = Model.instance.getStudentByID(STID);
        ImageView stuIMG = (ImageView) v.findViewById(R.id.stDetailImage);
        TextView nameTXT = (TextView) v.findViewById(R.id.stDetailName);
        nameTXT.setText(stDetail.name);
        TextView idTXT = (TextView) v.findViewById(R.id.stDetailId);
        idTXT.setText(stDetail.id);
        TextView phoneTXT = (TextView) v.findViewById(R.id.stDetailphone);
        phoneTXT.setText(stDetail.phone);
        TextView addressTXT = (TextView) v.findViewById(R.id.stDetailAddress);
        addressTXT.setText(stDetail.address);
        CheckBox stuCB = (CheckBox) v.findViewById(R.id.stDetailCB);
        stuCB.setChecked(stDetail.checked);

        TextView stuBirthdayTime = (TextView) v.findViewById(R.id.stDetailBirthdayTime);
        stuBirthdayTime.setText(stDetail.birthdayTime);

        TextView stuBirthdayDate = (TextView) v.findViewById(R.id.stDetailBirthdayDate);
        stuBirthdayDate.setText(stDetail.birthdayDate);

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String uri) {
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

    @Override
    public void onClick(View v) {
        mListener.onFragmentInteraction(STID);
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
        void onFragmentInteraction(String stid);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_edit, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
                Log.d("dev", "edit now");
                AddOrEditFragment details = AddOrEditFragment.newInstance(STID, "Edit");
                tran = getFragmentManager().beginTransaction();
                tran.replace(R.id.main_container, details).commit();

                break;
            case android.R.id.home:
                StudentListFragment listFragment = StudentListFragment.newInstance(1);
                tran = getFragmentManager().beginTransaction();
                tran.replace(R.id.main_container, listFragment);
                tran.commit();
                break;
            default:
                break;
        }

        return true;
    }
}
