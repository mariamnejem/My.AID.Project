package com.example.myaidproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_ForgotPassword#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_ForgotPassword extends Fragment {

    private FirebaseServices fbs;
    private EditText etEmail;
    private Button btnReset;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_ForgotPassword() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_ForgotPassword.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_ForgotPassword newInstance(String param1, String param2) {
        Fragment_ForgotPassword fragment = new Fragment_ForgotPassword();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__forgot_password, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        fbs = FirebaseServices.getInstance();
        etEmail = getView().findViewById(R.id.etEmailForgotpass);
        btnReset = getView().findViewById(R.id.btnResetForgotpass);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fbs.getAuth().sendPasswordResetEmail(etEmail.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getActivity(), "check your email.", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "failed. check the email address you entered", Toast.LENGTH_SHORT).show();
                    }
                    public void gotoLoginFragment() {
                        FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.frameLayout,new LoginFragment());
                        ft.addToBackStack(null);
                        ft.commit();
                    }
                });

            }
        });

    }

}