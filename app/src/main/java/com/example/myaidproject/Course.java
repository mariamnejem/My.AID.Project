package com.example.myaidproject;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Course implements Parcelable {
    private String name;
    private String description;
    private String photo;

    public Course()
    {

    }
    public Course(String name, String description, String photo) {
        this.name = name;
        this.description = description;
        this.photo = photo;
    }

    protected Course(Parcel in) {
        name = in.readString();
        description = in.readString();
        photo = in.readString();
    }

    public static final Creator<Course> CREATOR = new Creator<Course>() {
        @Override
        public Course createFromParcel(Parcel in) {
            return new Course(in);
        }

        @Override
        public Course[] newArray(int size) {
            return new Course[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "Cors{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(photo);
    }
}
