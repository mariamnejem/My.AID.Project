package com.example.myaidproject;

import android.os.Bundle;

import androidx.collection.CircularArray;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CourseDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CourseDetailsFragment extends Fragment {

 private FirebaseServices fbs;
 private TextView tvTitle, info;
 private ImageView ivCoursePhoto;
 private Course course;
 private Button btnBackDetails;
 private boolean isEnlarged = false;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CourseDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CourseDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CourseDetailsFragment newInstance(String param1, String param2) {
        CourseDetailsFragment fragment = new CourseDetailsFragment();
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
        return inflater.inflate(R.layout.fragment_course_details, container, false);
    }

    public void onStart(){
        super.onStart();
        init();

    }

    public void init(){
        fbs= FirebaseServices.getInstance();
        tvTitle= getView().findViewById(R.id.tvTitleDetails);
        info= getView().findViewById(R.id.tvInfoDetails);
        ivCoursePhoto=getView().findViewById(R.id.ivPhotoDetails);
        btnBackDetails= getView().findViewById(R.id.btnBackDetails);


        /*ivCoursePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup.LayoutParams layoutParams = ivCoursePhoto.getLayoutParams();
                if (isEnlarged) {
                    layoutParams.height =500;
                } else {
                    layoutParams.height = 2200;
                }
                ivCoursePhoto.setLayoutParams(layoutParams);

                // נשנה את המצב הנוכחי של התמונה
                isEnlarged = !isEnlarged;
            }
        });*/
        Bundle args = getArguments();
        if (args != null){
            Course course = args.getParcelable("course");
            if (course != null) {
                tvTitle.setText(course.getName());
                info.setText(course.getDescription());
                if (course.getPhoto() == null || course.getPhoto().isEmpty()){
                    Picasso.get().load(R.drawable.addimage).into(ivCoursePhoto);
                }
                else {
                    Picasso.get().load(course.getPhoto()).into(ivCoursePhoto);
                }
                btnBackDetails.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.frameLayout, new CourseListFragment());
                        ft.commit();
                    }
                });
            }
        }
    }

}
