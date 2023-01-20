package com.example.lab2;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView cityList;
    private ArrayList<String> dataList;
    private ArrayAdapter<String> cityAdapter;
    private final Context context = this;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataList = new ArrayList<>();
        // sets the list view to the one in activity_main.xml
        this.cityList = findViewById((R.id.cityList));
        //creating a list of cities
        City[] cities = {new City("Edmonton"), new City("Vancouver"), new City("Calgary"), new City("Toronto")};
        // setting the list of city name strings
        for (City city : cities) {
            dataList.add(city.getCityName());
        }
        // sets the city list style to be insert into the app
        this.cityAdapter = new ArrayAdapter<>(this, R.layout.content_xml, this.dataList);
        // sets the city list in activity_main.xml
        this.cityList.setAdapter(this.cityAdapter);

        Button addCityBtn = findViewById(R.id.addCityButton);

        addCityBtn.setOnClickListener(view -> addCityFunc());

        Button deleteCityBtn = findViewById(R.id.DeleteCityButton);

        // this function does the same as the lambda function below, creates a much cleaner look
//        deleteCityBtn.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                deleteCity();
//            }
//        });
        deleteCityBtn.setOnClickListener(view -> deleteCity());

    }

    public void addCityFunc(){

        // this stuff is a little bit of a black box to me!!
        // inspiration from:
        //  mkyong, August 29, 2012, Android prompt user input dialog example, https://mkyong.com/android/android-prompt-user-input-dialog-example/

        LayoutInflater li =  LayoutInflater.from(this.context);
        View prompView = li.inflate(R.layout.prompt, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this.context);

        alertDialogBuilder.setView(prompView);

        final EditText newCityInput = prompView.findViewById(R.id.newCityUserInput);

        alertDialogBuilder.setCancelable(false).setPositiveButton(
                "confirm",
                (dialogInterface, i) -> {
                    /// get result
                    String newCityString = newCityInput.getText().toString();
                    City newCity = new City(newCityString);

                    addToListAndDisplay(newCity);

                }
        );

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();


    }

    public void addToListAndDisplay(City newCity){
        // get city name from the new city
        String newCityString = newCity.getCityName();
        // checking to see if the city is already in the data list
        if(dataList.contains(newCityString)){
            Toast.makeText(getApplicationContext(), "Unable to add already existing city!!", Toast.LENGTH_LONG).show();
        }else{
            // add the new city to data list
            this.dataList.add(newCityString);
            // sets the city list style to be insert into the app
            this.cityAdapter = new ArrayAdapter<>(this, R.layout.content_xml, this.dataList);
            // sets the city list in activity_main.xml
            this.cityList.setAdapter(this.cityAdapter);
        }
    }


    public void deleteCity(){

        // inspiration from:
        //  Linh, May 24, 2016 , Delete item from listview when the item is clicked, https://stackoverflow.com/questions/37403175/delete-item-from-listview-when-the-item-is-clicked

      // this code can be replaced by a lambda function, this leads to much cleaner code!!!
//        this.cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                deleteItemFromListView(position);
//            }
//        });

        this.cityList.setOnItemClickListener((adapterView, view, position, l) -> deleteItemFromListView(position));
    }

    public void deleteItemFromListView(int position){
       // Toast.makeText(getApplicationContext(), "remove item at " + position, Toast.LENGTH_LONG).show();

        // remove item from data list
        this.dataList.remove(position);
        // sets the city list style to be insert into the app
        this.cityAdapter = new ArrayAdapter<>(this, R.layout.content_xml, this.dataList);
        // sets the city list in activity_main.xml
        this.cityList.setAdapter(this.cityAdapter);


    }
}