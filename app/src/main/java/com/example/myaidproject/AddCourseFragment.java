package com.example.myaidproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddCourseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddCourseFragment extends Fragment {

    private static final int GALLERY_REQUEST_CODE = 123;

    private TextView m;
 private EditText etTitleAddData, etInfoAddData;
 private ImageView ivPicAddData;
 private Button btnAddAddData;
 private FirebaseServices fbs;
    private Utils utils;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddCourseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddData.
     */
    // TODO: Rename and change types and number of parameters
    public static AddCourseFragment newInstance(String param1, String param2) {
        AddCourseFragment fragment = new AddCourseFragment();
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
        return inflater.inflate(R.layout.fragment_add_course, container, false);
    }

    public void onStart() {
        super.onStart();
        init();
    }

    private void init() {
        fbs = FirebaseServices.getInstance();
        utils = Utils.getInstance();
        etTitleAddData = getView().findViewById(R.id.etTitleAddData);
        etInfoAddData = getView().findViewById(R.id.etInfoAddData);
        ivPicAddData = getView().findViewById(R.id.ivPicAddData);
        btnAddAddData = getView().findViewById(R.id.btnAddAddData);

     btnAddAddData.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             addToFirestore();
         }
     });
        ivPicAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openGallery();}
        });
    }
    private void addToFirestore(){

        String corsTitle, info, photo;

        corsTitle=etTitleAddData.getText().toString();
        info=etInfoAddData.getText().toString();
        photo=ivPicAddData.toString();

        if (corsTitle.trim().isEmpty() || info.trim().isEmpty() || photo.trim().isEmpty())
        {
            Toast.makeText(getActivity(), "نعتذر، بعض المعلومات فارغة", Toast.LENGTH_LONG).show();
            return;
        }
        Course cors;
        if (fbs.getSelectedImageURL() == null){
            cors= new Course(corsTitle,info,"");
        }
        else {
            cors= new Course(corsTitle,info,fbs.getSelectedImageURL().toString());

        }
        fbs.getFire().collection("courses").add(cors)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getActivity(), "تمت الإضافة بنجاح", Toast.LENGTH_SHORT).show();
                        Log.e("addToFirestore() - add to collection: ","تم بنجاح!");
                        gotoCoursesList();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("addToFirestore() - add to collection: ", e.getMessage());
                    }
                });

    }

    private void openGallery(){
        Intent galleryIntent= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == getActivity().RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            ivPicAddData.setImageURI(selectedImageUri);
            utils.uploadImage(getActivity(), selectedImageUri);
        }
    }

    public void gotoCoursesList(){
        FragmentTransaction ft= getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout,new CourseListFragment());
        ft.addToBackStack(null);
        ft.commit();
    }
    public void toBigImg(View view ){
    }

}















