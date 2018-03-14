package com.example.d3.lifttrack;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainMenu extends AppCompatActivity {

    private ArrayList<String> exercisesA;
    private ArrayList<String> exercisesB;

    private ArrayList<Float> weightsA;
    private ArrayList<Float> weightsB;

    private ArrayList<String> repsA;
    private ArrayList<String> repsB;

    private ArrayList<Float> scalingA;
    private ArrayList<Float> scalingB;

    private ArrayList<String> days;

    Button buttonA;
    Button buttonB;
    Button buttonAddDay;

    SharedPreferences prefs = null;

    private void init(){
        buttonA = findViewById(R.id.buttonA);
        buttonB = findViewById(R.id.buttonB);
        buttonAddDay = findViewById(R.id.buttonAddDay);

        buttonA.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainMenu.this,Day.class);
                intent.putExtra("dayID","A");
                startActivity(intent);
            }
        });

        buttonB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainMenu.this,Day.class);
                intent.putExtra("dayID","B");
                startActivity(intent);
            }
        });

        buttonAddDay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                addDay();
            }
        });
    }

    private void setWeights(){
        this.weightsA = new ArrayList<>();
        this.weightsB = new ArrayList<>();

        weightsA.add((float) 87.5);
        weightsA.add((float) 45.0);
        weightsA.add((float) 67.5);

        weightsB.add((float) 90.0);
        weightsB.add((float) 30.0);
        weightsB.add((float) 30.0);
    }

    private void setExercises(){
        this.exercisesA = new ArrayList<>();
        this.exercisesB = new ArrayList<>();

        exercisesA.add("Squats");
        exercisesA.add("Deadlifts");
        exercisesA.add("Bench Press");

        exercisesB.add("Squats");
        exercisesB.add("Overhead Press");
        exercisesB.add("Pendlay Rows");
    }

    private void setReps(){
        this.repsA = new ArrayList<>();
        this.repsB = new ArrayList<>();

        repsA.add("3x5");
        repsA.add("1x5");
        repsA.add("3x5");

        repsB.add("3x5");
        repsB.add("3x5");
        repsB.add("3x5");
    }

    private void setScaling(){
        this.scalingA = new ArrayList<>();
        this.scalingB = new ArrayList<>();

        scalingA.add(2.5f);
        scalingA.add(5.0f);
        scalingA.add(2.5f);

        scalingB.add(2.5f);
        scalingB.add(2.5f);
        scalingB.add(2.5f);
    }

    private void setDays(){
        this.days = new ArrayList<>();

        days.add("A");
        days.add("B");
    }

    private void writeExercises(ArrayList<String> exercises, String id){
        StringBuilder stringBuilder = new StringBuilder();
        for(String s : exercises){
            stringBuilder.append(s);
            stringBuilder.append(",");
        }
        SharedPreferences settings = getSharedPreferences("com.d3.lifttrack",0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("exercises"+id,stringBuilder.toString());
        editor.apply();
    }

    private void writeWeights(ArrayList<Float> weights, String id){
        StringBuilder stringBuilder = new StringBuilder();
        for(Float f : weights){
            stringBuilder.append(f);
            stringBuilder.append(",");
        }
        SharedPreferences settings = getSharedPreferences("com.d3.lifttrack",0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("weights"+id,stringBuilder.toString());
        editor.apply();
    }

    private void writeReps(ArrayList<String> reps, String id){
        StringBuilder stringBuilder = new StringBuilder();
        for(String s : reps){
            stringBuilder.append(s);
            stringBuilder.append(",");
        }
        SharedPreferences settings = getSharedPreferences("com.d3.lifttrack",0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("reps"+id,stringBuilder.toString());
        editor.apply();
    }

    private void writeScaling(ArrayList<Float> scaling, String id){
        StringBuilder stringBuilder = new StringBuilder();
        for(Float f : scaling){
            stringBuilder.append(f);
            stringBuilder.append(",");
        }
        SharedPreferences settings = getSharedPreferences("com.d3.lifttrack",0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("scaling"+id,stringBuilder.toString());
        editor.apply();
    }

    private void writeDays(ArrayList<String> days){
        StringBuilder stringBuilder = new StringBuilder();
        for(String s : days){
            stringBuilder.append(s);
            stringBuilder.append(",");
        }
        SharedPreferences settings = getSharedPreferences("com.d3.lifttrack",0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("days",stringBuilder.toString());
        editor.apply();
    }

    private void addDay(){
        int numButtons = 5;
        ConstraintLayout layout = findViewById(R.id.layout);
        Button button = new Button(this);
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(15, 15, 15, 15);
        button.setLayoutParams(params);
        layout.addView(button);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(layout);

        constraintSet.connect(button.getId(), ConstraintSet.TOP, layout.getId(), ConstraintSet.TOP, numButtons*42);
        constraintSet.connect(button.getId(), ConstraintSet.LEFT, buttonAddDay.getId(), ConstraintSet.LEFT, 0);

        constraintSet.applyTo(layout);
    }

    private void loadDays(ArrayList<String> days){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        prefs = getSharedPreferences("com.d3.lifttrack", MODE_PRIVATE);

        setWeights();
        setExercises();
        setReps();
        setScaling();
        setDays();

        writeExercises(this.exercisesA,"A");
        writeExercises(this.exercisesB,"B");
        writeWeights(this.weightsA,"A");
        writeWeights(this.weightsB,"B");
        writeReps(this.repsA,"A");
        writeReps(this.repsB,"B");
        writeScaling(this.scalingA,"A");
        writeScaling(this.scalingB,"B");
        writeDays(this.days);

        loadDays(days);

        init();
    }
}