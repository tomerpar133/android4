package com.example.shaysheli.assignment3.Model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ShaySheli on 28/04/2017.
 */

public class Model {
    public final static Model instance = new Model();
    private static int id = 19;
    private Model(){
        for(int i = 0; i < 20; i++) {
            Student st = new Student();
            st.name = "kuku" + i;
            st.id = i + "";
            st.checked = false;
            st.imageUrl = "../res/drawable/grid.png";

            data.add(i, st);
        }
    }

   private ArrayList<Student> data = new ArrayList<>();

    public ArrayList<Student> getAllStudents(){
        return data;
    }

    public void addStudent(Student st){
        st.id = ++id + "";
        st.imageUrl = "../res/drawable/grid.png";
        data.add(Integer.parseInt(st.id), st);
    }

    public Student getStudentByID (String stID){
        for (Student st : this.data) {
            if (st.id.equals(stID)) {
                return st;
            }
        }

        return null;
    }

    public Boolean rmStu(Student st) {
        id--;
        return data.remove(st);
    }

    public Boolean editStudent(Student st){
        if (this.getStudentByID(st.id) == null) {
            this.addStudent(st);
        }else {
            data.set(Integer.parseInt(st.id), st);
        }

        return true;
    }
}
