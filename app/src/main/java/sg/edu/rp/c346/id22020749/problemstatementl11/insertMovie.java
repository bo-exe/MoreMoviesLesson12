package sg.edu.rp.c346.id22020749.problemstatementl11;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class insertMovie extends AppCompatActivity {

    EditText etTitle, etGenre, etYear;
    Spinner insertRating;
    Button btnInsert, btnShowList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_movie);

        etTitle = findViewById(R.id.etTitle);
        etGenre = findViewById(R.id.etGenre);
        etYear = findViewById(R.id.etYear);
        insertRating = findViewById(R.id.insertRating);
        btnInsert = findViewById(R.id.btnInsert);
        btnShowList = findViewById(R.id.btnShowList);

        String[] ratingArr = {"U", "PG", "12A", "12", "15", "18", "R18"};

        ArrayAdapter ratingAdapter = new ArrayAdapter<>(insertMovie.this, android.R.layout.simple_list_item_1, ratingArr);
        insertRating.setAdapter(ratingAdapter);
        insertRating.setSelection(0, false);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isEmpty;

                String title = etTitle.getText().toString();
                String genre = etGenre.getText().toString();
                String yearStr = etYear.getText().toString();
                String rating = insertRating.getSelectedItem().toString();

                if (title.isEmpty() || genre.isEmpty() || yearStr.isEmpty() || rating.isEmpty())
                    isEmpty = true;
                else
                    isEmpty = false;

                DBHelper db = new DBHelper(insertMovie.this);

                if (!isEmpty) {
                    int year = Integer.parseInt(yearStr);
                    db.insertMovie(title, genre, year, rating);
                    Toast.makeText(insertMovie.this, "Movie added!", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(insertMovie.this, "Error. Please input on all fields.", Toast.LENGTH_SHORT).show();

                // Reset values
                etTitle.setText("");
                etGenre.setText("");
                etYear.setText("");
                insertRating.setSelection(0, false);
            }
        });

        btnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(insertMovie.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

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

        if (id == R.id.showList) {
            Intent intent = new Intent(insertMovie.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}