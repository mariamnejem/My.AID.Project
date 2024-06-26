package com.example.myaidproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CourseListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CourseListFragment extends Fragment {

    private RecyclerView recyclerView;
    private ImageView ivProfile;
    private FirebaseServices fbs;
    private CourseAdapter myAdapter;
    private ArrayList<Course> list , filteredList;
    private SearchView srchView;
    private Button btnAddDataList, btnLogoutList;


    //TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CourseListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CourseListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CourseListFragment newInstance(String param1, String param2) {
        CourseListFragment fragment = new CourseListFragment();
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

    public void onStart(){
        super.onStart();
        init();
    }

    private void init() {
        recyclerView = getView().findViewById(R.id.rvCourseListFragment);
        fbs = FirebaseServices.getInstance();
        //fbs.setUserChangeFlag(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        list = new ArrayList<>();
        myAdapter = new CourseAdapter(getActivity(), list);
        recyclerView.setAdapter(myAdapter);
        filteredList = new ArrayList<>();
        list = getCourses();
        btnAddDataList= getView().findViewById(R.id.btnAddDataList);

        myAdapter.setOnItemClickListener(new CourseAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(int position) {
                // Handle item click here
                String selectedItem = list.get(position).getName();
                Toast.makeText(getActivity(), "Clicked: " + selectedItem, Toast.LENGTH_SHORT).show();
                Bundle args = new Bundle();
                args.putParcelable("course", list.get(position)); // or use Parcelable for better performance
                CourseDetailsFragment cd = new CourseDetailsFragment();
                cd.setArguments(args);
                FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayout,cd);
                ft.commit();
            }
        });
        srchView = getView().findViewById(R.id.srchListFragment);
        srchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                applyFilter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //applyFilter(newText);
                return false;
            }
        });

        btnAddDataList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayout, new AddCourseFragment());
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        btnLogoutList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayout, new LoginFragment());
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }

    private void applyFilter(String query) {
        // TODO: add onBackspace - old and new query
        if (query.trim().isEmpty())
        {
            myAdapter = new CourseAdapter(getContext(), list);
            recyclerView.setAdapter(myAdapter);
            return;
        }
        filteredList.clear();
        for(Course course :  list)
        {
            if (course.getName().toLowerCase().contains(query.toLowerCase()) ||
                    course.getDescription().toLowerCase().contains(query.toLowerCase()) ||
                    course.getPhoto().toLowerCase().contains((query.toLowerCase())))
            {
                filteredList.add(course);
            }
        }
        if (filteredList.size() == 0)
        {
            showNoDataDialogue();
            return;
        }
        myAdapter = new CourseAdapter(getContext(), filteredList);
        recyclerView.setAdapter(myAdapter);


/*
        myAdapter.setOnItemClickListener(new CourseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {*/
                /*
                // Handle item click here
                String selectedItem = filteredList.get(position).getNameMeal();
                Toast.makeText(getActivity(), "Clicked: " + selectedItem, Toast.LENGTH_SHORT).show();
                Bundle args = new Bundle();
                args.putParcelable(meal", filteredList.get(position)); // or use Parcelable for better performance
                MealDetailsFragment cd = new MealDetailsFragment();
                cd.setArguments(args);
                FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayout,cd);
                ft.commit();
            }
        });*/
    }
    private void showNoDataDialogue() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("No Results");
        builder.setMessage("Try again!");
        builder.show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_course_list, container, false);
    }
    public void gotoAddCourseFragment() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout,new AddCourseFragment());
        //ft.addToBackStack(null);
        ft.commit();
    }

    public ArrayList<Course> getCourses() {
        ArrayList<Course> list = new ArrayList<>();
        try {
            list.clear();
            fbs.getFire().collection("courses")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    list.add(document.toObject(Course.class));
                                }

                                CourseAdapter adapter = new CourseAdapter(getActivity(), list);
                                recyclerView.setAdapter(adapter);
                                //addUserToCompany(companies, user);
                            } else {
                                Log.e("AllRestActivity: readData()", "Error getting documents.", task.getException());
                            }
                        }
                    });
        }
        catch (Exception e)
        {
            Log.e("getCompaniesMap(): ", e.getMessage());
        }

        return list;

    }
}