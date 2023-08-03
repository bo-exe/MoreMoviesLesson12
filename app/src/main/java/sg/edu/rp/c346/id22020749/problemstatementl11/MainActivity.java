package sg.edu.rp.c346.id22020749.problemstatementl11;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    ToggleButton btnFilterRatings;
    Spinner spinnerRatings;
    ListView listViewMovies;
    CustomAdapter adapter;
    ArrayAdapter spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String defVal = "Filter by - Default";

        btnFilterRatings = findViewById(R.id.btnFilterRatings);
        spinnerRatings = findViewById(R.id.spinnerRatings);
        listViewMovies = findViewById(R.id.listViewMovies);

        DBHelper db = new DBHelper(MainActivity.this);
        ArrayList<Movie> movieList = db.getMovies();
        ArrayList<String> ratingArr = db.getUniqueRatings();
        ratingArr.add(defVal);
        Collections.reverse(ratingArr);

        db.close();

        spinnerAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, ratingArr);
        spinnerRatings.setAdapter(spinnerAdapter);

        adapter = new CustomAdapter(MainActivity.this, R.layout.row, movieList);
        listViewMovies.setAdapter(adapter);

        listViewMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int
                    position, long identity) {

                Movie data;
                data = movieList.get(position);

                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra("data", data);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        btnFilterRatings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = btnFilterRatings.isChecked();
                modifyMovieList(movieList, isChecked);
            }
        });

        spinnerRatings.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                btnFilterRatings.setEnabled(position == 0);
                btnFilterRatings.setChecked(false);

                modifyMovieList(movieList, selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

    }

    // Toggle Button
    public void modifyMovieList(ArrayList<Movie> movieList, boolean isChecked) {
        DBHelper db = new DBHelper(MainActivity.this);
        movieList.clear();

        if (isChecked) {
            movieList = db.getFilteredMovies();
            adapter.addAll(movieList);
            adapter.notifyDataSetChanged();

        } else {
            adapter.clear();
            movieList = db.getMovies();
            adapter.addAll(movieList);
            adapter.notifyDataSetChanged();
        }

        db.close();
    }

    // Spinner
    public void modifyMovieList(ArrayList<Movie> movieList, String selectedItem) {
        DBHelper db = new DBHelper(MainActivity.this);
        movieList.clear();
        String defVal = "Filter by - Default";

        if (selectedItem.equalsIgnoreCase(defVal)) {
            adapter.clear();
            movieList = db.getMovies();
            adapter.addAll(movieList);
            adapter.notifyDataSetChanged();
        } else {
            movieList = db.getFilteredMovies(selectedItem);
            adapter.clear();
            adapter.addAll(movieList);
            adapter.notifyDataSetChanged();
        }

        db.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.s
        int id = item.getItemId();

        if (id == R.id.insert) {
            Intent intent = new Intent(MainActivity.this, insertMovie.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}