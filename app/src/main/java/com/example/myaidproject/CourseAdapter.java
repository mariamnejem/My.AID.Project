package com.example.myaidproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.auth.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.MyViewHolder> { // extends RecyclerView.Adapter<CourseAdapter.MyViewHolder> {

    Context context;
    private ArrayList<Course> coursesList;
    private CourseAdapter.OnItemClickListener itemClickListener;

    private CourseAdapter myAdapter;
    private FirebaseServices fbs;

    public CourseAdapter(Context context, ArrayList<Course> coursesList) {
        this.context = context;
        this.coursesList = coursesList;
        this.fbs = FirebaseServices.getInstance();
/*
        this.itemClickListener = new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

               /* String selectedItem = filteredList.get(position).getNameCar();
                Toast.makeText(getActivity(), "Clicked: " + selectedItem, Toast.LENGTH_SHORT).show();*/
                /*Bundle args = new Bundle();
                args.putParcelable("course", coursesList.get(position)); // or use Parcelable for better performance
                CourseDetailsFragment cd = new CourseDetailsFragment();
                cd.setArguments(args);
                FragmentTransaction ft = ((MainActivity) context).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayout, cd);
                ft.commit();

                //fbs.setSelectedImageURL(coursesList.get(position).);
            }
        };*/
    }


    @NonNull
    @Override
    public CourseAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new CourseAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Course course= coursesList.get(position);
        holder.Name.setText(course.getName());
        //holder.info.setText(course.getDescription());

        if (course.getPhoto() == null ||course.getPhoto().isEmpty()) {

        } else {
            Picasso.get().load(course.getPhoto()).into(holder.ivCourse);
        }

        holder.Name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putParcelable("course", coursesList.get(position)); // or use Parcelable for better performance
                CourseDetailsFragment cd = new CourseDetailsFragment();
                cd.setArguments(args);
                FragmentTransaction ft= ((MainActivity)context).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayout,cd);
                ft.commit();
            }
        });
    };

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Name,info;
        ImageView ivCourse;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Name=itemView.findViewById(R.id.tvNameItem);
            //info=itemView.findViewById(R.id.);
            ivCourse= itemView.findViewById(R.id.ivPhotoItem);
        }
    }

    @Override
    public int getItemCount() {
        return coursesList.size();
    }

   public interface OnItemClickListener {
       void onItemClick(int position);
   }
    public void setOnItemClickListener(CourseAdapter.OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

}
