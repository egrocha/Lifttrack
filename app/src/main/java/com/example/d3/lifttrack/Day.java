package com.example.d3.lifttrack;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;


public class Day extends Activity {

    String dayID;
    ArrayList<String> exercises;
    ArrayList<Float> weights;
    ArrayList<String> reps;
    ArrayList<Float> scaling;
    ArrayList<String> days;
    Button buttonComp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);

        buttonComp = findViewById(R.id.buttonComp);

        buttonComp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                updateWeights(weights,scaling);
                finish();
            }
        });

        Intent i = getIntent();

        dayID = i.getStringExtra("dayID");

        exercises = loadExercises(dayID);
        weights = loadWeights(dayID);
        reps = loadReps();
        scaling = loadScaling(dayID);
        days = loadDays();

        setTextViews();
    }

    private void setTextViews(){
        TextView text1 = findViewById(R.id.textEx1);
        TextView text2 = findViewById(R.id.textEx2);
        TextView text3 = findViewById(R.id.textEx3);
        text1.setText(exercises.get(0));
        text2.setText(exercises.get(1));
        text3.setText(exercises.get(2));

        TextView text1Reps = findViewById(R.id.textEx1Reps);
        TextView text2Reps = findViewById(R.id.textEx2Reps);
        TextView text3Reps = findViewById(R.id.textEx3Reps);
        text1Reps.setText(reps.get(0));
        text2Reps.setText(reps.get(0));
        text3Reps.setText(reps.get(0));

        TextView text1Num = findViewById(R.id.textEx1Num);
        TextView text2Num = findViewById(R.id.textEx2Num);
        TextView text3Num = findViewById(R.id.textEx3Num);
        text1Num.setText(Float.toString(weights.get(0)));
        text2Num.setText(Float.toString(weights.get(1)));
        text3Num.setText(Float.toString(weights.get(2)));
    }

    private ArrayList<String> loadExercises(String dayID) {
        SharedPreferences settings = getSharedPreferences("com.d3.lifttrack", 0);
        String exercisesString = settings.getString("exercises" + dayID, "");
        String[] itemsExercises = exercisesString.split(",");
        ArrayList<String> exercises = new ArrayList<>();
        Collections.addAll(exercises, itemsExercises);
        return exercises;
    }

    private ArrayList<Float> loadWeights(String dayID) {
        SharedPreferences settings = getSharedPreferences("com.d3.lifttrack", 0);
        String weightsString = settings.getString("weights" + dayID, "");
        String[] itemsWeights = weightsString.split(",");
        ArrayList<Float> weights = new ArrayList<>();
        for (String itemsWeight : itemsWeights) {
            float number = Float.parseFloat(itemsWeight);
            number = Math.round(number * 1000) / 1000f;
            weights.add(number);

        }
        return weights;
    }

    private ArrayList<String> loadReps(){
        SharedPreferences settings = getSharedPreferences("com.d3.lifttrack", 0);
        String repsString = settings.getString("reps" + dayID, "");
        String[] itemsReps = repsString.split(",");
        ArrayList<String> reps = new ArrayList<>();
        Collections.addAll(reps, itemsReps);
        return reps;
    }

    private ArrayList<Float> loadScaling(String dayID){
        SharedPreferences settings = getSharedPreferences("com.d3.lifttrack", 0);
        String scalingString = settings.getString("scaling" + dayID, "");
        String[] itemsScaling = scalingString.split(",");
        ArrayList<Float> scaling = new ArrayList<>();
        for (String itemsWeight : itemsScaling) {
            float scalingNum = Float.parseFloat(itemsWeight);
            scalingNum = Math.round(scalingNum * 1000) / 1000f;
            scaling.add(scalingNum);

        }
        return scaling;
    }

    private ArrayList<String> loadDays() {
        SharedPreferences settings = getSharedPreferences("com.d3.lifttrack", 0);
        String daysString = settings.getString("days", "");
        String[] itemsDays = daysString.split(",");
        ArrayList<String> days = new ArrayList<>();
        Collections.addAll(days, itemsDays);
        return days;
    }

    private void updateWeights(ArrayList<Float> weights, ArrayList<Float> scaling){
        float newWeight;
        for(int i = 0; i < weights.size(); i++){
            newWeight = weights.get(i) + scaling.get(i);
            weights.set(i,newWeight);
        }
        writeWeights(weights, this.dayID);
        updateDaysExercise();
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

    private void updateDaysExercise(){
        int i = 0;
        for(String day : days){
            if(!day.equals(dayID)){
                ArrayList<String> exercises = loadExercises(day);
                ArrayList<Float> weights = loadWeights(day);
                ArrayList<Float> scaling = loadScaling(day);
                for(String exercise : exercises){
                    if(exercise.equals(this.exercises.get(i))){
                        weights.set(i,weights.get(i)+scaling.get(i));

                    }
                    i++;
                }
                writeWeights(weights,day);
            }
        }
    }
}
