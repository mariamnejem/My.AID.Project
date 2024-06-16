package com.example.myaidproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class  MainActivity extends AppCompatActivity {

    private FirebaseServices fbs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fbs = FirebaseServices.getInstance();
          if (fbs.getAuth().getCurrentUser() == null) {
              gotoLoginFragment();
          } else
            gotoCoursesList();
    }


    private void gotoLoginFragment() {
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout, new LoginFragment());
        ft.addToBackStack(null);
        ft.commit();
    }

    public void gotoCoursesList(){
        FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout,new CourseListFragment());
        ft.addToBackStack(null);

        ft.commit();
    }

}
