package sg.edu.rp.c346.id22020749.problemstatementl11;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

public class EditActivity extends AppCompatActivity {

    EditText editEtId, editEtTitle, editEtGenre, editEtYear;
    Spinner editRating;
    Button btnCancel, btnDelete, btnUpdate;
    Movie data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        editEtId = findViewById(R.id.editEtId);
        editEtTitle = findViewById(R.id.editEtTitle);
        editEtGenre = findViewById(R.id.editEtGenre);
        editEtYear = findViewById(R.id.editEtYear);
        editRating = findViewById(R.id.editRating);
        btnCancel = findViewById(R.id.btnCancel);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        editEtId.setEnabled(false);
        editEtId.setClickable(false);

        String[] ratingArr = {"U", "PG", "12A", "12", "15", "18", "R18"};

        ArrayAdapter ratingAdapter = new ArrayAdapter<>(EditActivity.this, android.R.layout.simple_list_item_1, ratingArr);
        editRating.setAdapter(ratingAdapter);

        Intent intent = getIntent();
        data = (Movie) intent.getSerializableExtra("data");

        editEtId.setText(Integer.toString(data.getId()));
        editEtTitle.setText(data.getTitle());
        editEtGenre.setText(data.getGenre());
        editEtYear.setText(Integer.toString(data.getYear()));

        for (int i = 0; i < ratingAdapter.getCount(); i++) {
            if (ratingAdapter.getItem(i).equals(data.getRating())) {
                editRating.setSelection(i);
                break;
            }
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editEtTitle.getText().toString();
                String genre = editEtGenre.getText().toString();
                int year = Integer.parseInt(editEtYear.getText().toString());
                String rating = editRating.getSelectedItem().toString();

                data.setTitle(title);
                data.setGenre(genre);
                data.setYear(year);
                data.setRating(rating);

                DBHelper db = new DBHelper(EditActivity.this);
                db.updateMovie(data);
                db.close();

                Intent intent = new Intent(EditActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                Toast.makeText(EditActivity.this, "Movie updated", Toast.LENGTH_LONG).show();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder myBuilder = new AlertDialog.Builder(EditActivity.this);

                myBuilder.setTitle("Danger");
                myBuilder.setMessage("Are you sure you want to delete the movie " + editEtTitle);
                myBuilder.setCancelable(false);

                myBuilder.setPositiveButton("CANCEL", null);
                myBuilder.setNegativeButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBHelper db = new DBHelper(EditActivity.this);
                        db.deleteMovie(data.getId());

                        Intent intent = new Intent(EditActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                        Toast.makeText(EditActivity.this, "Movie deleted", Toast.LENGTH_LONG).show();
                    }
                });
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();
                    }
                });



        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder btnCancelBuilder = new AlertDialog.Builder(EditActivity.this);

                btnCancelBuilder.setTitle("Danger");
                btnCancelBuilder.setMessage("Are you sure you want to discard the changes");
                btnCancelBuilder.setCancelable(false);

                btnCancelBuilder.setPositiveButton("DO NOT DISCARD", null);
                btnCancelBuilder.setNegativeButton("DISCARD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(EditActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });
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

        if (id == R.id.insert) {
            Intent intent = new Intent(EditActivity.this, insertMovie.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return true;

        } else if (id == R.id.showList) {
            Intent intent = new Intent(EditActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}