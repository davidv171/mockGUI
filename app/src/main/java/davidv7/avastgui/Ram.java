package davidv7.avastgui;

import android.app.SearchManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Random;

public class Ram extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ram);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(Color.TRANSPARENT);

        toolbar.setTitleTextColor(getResources().getColor(R.color.red));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        //When the Back button on the toolbar is clicked, finish the activity to avoid processing in the background
        //Every activity with this button should have this
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final GraphView graphView = findViewById(R.id.graphRAM);
        graphView.setTitle("% RAM Usage");
        GridLabelRenderer gridLabelRenderer = new GridLabelRenderer(graphView);
        gridLabelRenderer.setHorizontalAxisTitle("Time");
        gridLabelRenderer.setVerticalAxisTitle("% RAM Used");
        graphView.setTitleTextSize(48);
        Viewport vp = graphView.getViewport();

        final LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {


                new DataPoint(0, 15),
                new DataPoint(1, 14),
                new DataPoint(2, 14),
                new DataPoint(3, 14),
                new DataPoint(4, 14),
                new DataPoint(5, 14),
                new DataPoint(6, 14),
                new DataPoint(7, 1),
                new DataPoint(8, 15),
                new DataPoint(9, 14),
                new DataPoint(10, 11),
                new DataPoint(11, 12),
                new DataPoint(12, 11),
                new DataPoint(13, 14),
                new DataPoint(14, 14),
                new DataPoint(15, 1)


        });
        graphView.addSeries(series);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                int min = 15;
                int max = 22;

                Random r = new Random();
                int randN = r.nextInt(max - min + 1) + min;
                series.appendData(new DataPoint(series.getHighestValueX() + 1, randN), false, 100);
                graphView.addSeries(series);
                System.out.println("DODAN " + randN);
                if (series.getHighestValueX() == 30) {
                    //TODO: restart data
                    System.out.println("END");
                    graphView.removeAllSeries();
                }
                handler.postDelayed(this, 1000);
            }
        }, 2000);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ram, menu);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        EditText editText = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        editText.setTextColor(Color.WHITE);
        searchView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View view) {

            }

            @Override
            public void onViewDetachedFromWindow(View view) {
                Toolbar toolbar = findViewById(R.id.toolbar);
                toolbar.setBackgroundColor(Color.TRANSPARENT);
                System.out.println("YES!");
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            //change color of action bar to light blue
            Toolbar toolbar = findViewById(R.id.toolbar);
            toolbar.setBackgroundColor(getResources().getColor(R.color.red));
            TransitionManager.beginDelayedTransition((ViewGroup) Ram.this.findViewById(R.id.toolbar));
            MenuItemCompat.expandActionView(item);
        }

        return super.onOptionsItemSelected(item);
    }

}
