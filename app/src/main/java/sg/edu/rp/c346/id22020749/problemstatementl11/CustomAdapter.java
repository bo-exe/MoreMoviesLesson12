package sg.edu.rp.c346.id22020749.problemstatementl11;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {

    Context parent_context;
    int layout_id;
    ArrayList<Movie> movieList;

    public CustomAdapter(Context context, int resource, ArrayList<Movie> objects) {
        super(context, resource, objects);

        parent_context = context;
        layout_id = resource;
        movieList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtain the LayoutInflater object
        LayoutInflater inflater = (LayoutInflater)
                parent_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // "Inflate" the View for each row
        View rowView = inflater.inflate(layout_id, parent, false);

        // Obtain the UI components and do the necessary binding
        TextView listTitle = rowView.findViewById(R.id.rowTitle);
        TextView listGenre = rowView.findViewById(R.id.rowGenre);
        TextView listYear = rowView.findViewById(R.id.rowYear);
        ImageView listRating = rowView.findViewById(R.id.imageRating);

        // Obtain the Android Version information based on the position
        Movie currentMovie = movieList.get(position);

        // Set values to the TextView to display the corresponding information
        listTitle.setText(currentMovie.getTitle());
        listGenre.setText(currentMovie.getGenre());
        listYear.setText(currentMovie.getYear() + "");

        String uUrl = "https://images.immediate.co.uk/production/volatile/sites/28/2019/02/16277-28797ce.jpg?quality=90&webp=true&fit=584,471";
        String pgUrl = "https://images.immediate.co.uk/production/volatile/sites/28/2019/02/16278-28797ce.jpg?quality=90&webp=true&fit=584,471";
        String twelveAUrl = "https://images.immediate.co.uk/production/volatile/sites/28/2019/02/16279-8d5bdb7.jpg?quality=90&webp=true&fit=490,490";
        String twelveUrl = "https://images.immediate.co.uk/production/volatile/sites/28/2019/02/16280-8d5bdb7.jpg?quality=90&webp=true&fit=320,320";
        String fifteenUrl = "https://images.immediate.co.uk/production/volatile/sites/28/2019/02/16281-8d5bdb7.jpg?quality=90&webp=true&fit=490,490";
        String eighteenUrl = "https://images.immediate.co.uk/production/volatile/sites/28/2019/02/16282-05127b2.jpg?quality=90&webp=true&fit=300,300";
        String restrictedUrl = "https://images.immediate.co.uk/production/volatile/sites/28/2019/02/16283-05127b2.jpg?quality=90&webp=true&fit=515,424";

        // Depending on rating, get image through web
        if (currentMovie.getRating().equals("U"))
            Picasso.with(parent_context).load(uUrl).into(listRating);

        else if (currentMovie.getRating().equals("PG"))
            Picasso.with(parent_context).load(pgUrl).into(listRating);

        else if (currentMovie.getRating().equals("12A"))
            Picasso.with(parent_context).load(twelveAUrl).into(listRating);

        else if (currentMovie.getRating().equals("12"))
            Picasso.with(parent_context).load(twelveUrl).into(listRating);

        else if (currentMovie.getRating().equals("15"))
            Picasso.with(parent_context).load(fifteenUrl).into(listRating);

        else if (currentMovie.getRating().equals("18"))
            Picasso.with(parent_context).load(eighteenUrl).into(listRating);

        else if (currentMovie.getRating().equals("R18"))
            Picasso.with(parent_context).load(restrictedUrl).into(listRating);

        return rowView;

    }

}