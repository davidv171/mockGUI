package davidv7.avastgui;

import android.app.ActivityManager;
import android.app.SearchManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
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
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Storage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Storage");
        toolbar.setBackgroundColor(Color.TRANSPARENT);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setTitleTextColor(getResources().getColor(R.color.red));
        //When the Back button on the toolbar is clicked, finish the activity to avoid processing in the background
        //Every activity with this button should have this
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        CollapsingToolbarLayout ctl = findViewById(R.id.toolbar_layout);
        ctl.setTitleEnabled(false);
        toolbar.setTitle("Storage");
        setSupportActionBar(toolbar);
        //pie chart logic, hardcoded values that are then randomized when clicking the fAB
        final PieChart pieChart = findViewById(R.id.pieChart);
        List<PieEntry> entries = new ArrayList<>();

        entries.add(new PieEntry(18.5f, "Facebook"));
        entries.add(new PieEntry(26.7f, "Instagram"));
        entries.add(new PieEntry(24.0f, "Relay"));
        entries.add(new PieEntry(30.8f, "Settings"));

        PieDataSet set = new PieDataSet(entries,"");
        set.setValueTextSize(14);

        set.setColors(new int[]{R.color.orange,R.color.complete,R.color.colorAccent,R.color.lightpink},getApplicationContext());

        PieData data = new PieData(set);
        pieChart.setData(data);

        pieChart.getLegend().setEnabled(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.invalidate(); // refresh
        final PieChart pieChart2 = findViewById(R.id.pieChart2);
        List<PieEntry> entries2 = new ArrayList<>();

        entries2.add(new PieEntry(11.5f, "Pictures"));
        entries2.add(new PieEntry(22.7f, "Music"));
        entries2.add(new PieEntry(28.0f, "Videos"));
        entries2.add(new PieEntry(13.8f, "Docs"));

        PieDataSet set2 = new PieDataSet(entries2,"");

        set2.setColors(new int[]{R.color.orange,R.color.complete,R.color.colorAccent,R.color.lightpink},getApplicationContext());
        set2.setValueTextSize(12);
        set2.setValueLineColor(R.color.black);
        PieData data2 = new PieData(set2);
        pieChart2.setData(data2);

        pieChart2.getLegend().setEnabled(false);
        pieChart2.getDescription().setEnabled(false);
        pieChart2.invalidate(); // refresh
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab33);
            fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    List<PieEntry> entries2 = new ArrayList<>();
                int min = 0;
                int max = 30;
                int randoms[] = new int[4];
                for(int i = 0;i<4;i++){
                    Random r = new Random();
                    randoms[i] = r.nextInt(max - min + 1) + min;
                }

                    entries2.add(new PieEntry(randoms[0], "Facebook"));
                    entries2.add(new PieEntry(randoms[1], "Instagram"));
                    entries2.add(new PieEntry(randoms[2], "Relay"));
                    entries2.add(new PieEntry(randoms[3], "Settings"));

                    PieDataSet set2 = new PieDataSet(entries2, "");
                    set2.setValueTextSize(14);

                    set2.setColors(new int[]{R.color.orange,R.color.complete,R.color.colorAccent,R.color.lightpink},getApplicationContext());

                    PieData data2 = new PieData(set2);
                    pieChart.setData(data2);
                    pieChart.getLegend().setEnabled(false);
                    pieChart.getDescription().setEnabled(false);
                    pieChart.invalidate(); // refresh
                    view.setTag(1);

                final TextView anComplete = findViewById(R.id.anComplete);
                anComplete.setText("Analysis in progress...");
                pieChart.spin( 5000,0,-360f, Easing.EasingOption.EaseInOutQuad);
                pieChart2.spin( 5000,0,-360f, Easing.EasingOption.EaseInOutQuad);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            anComplete.setText("Analysis complete!");
                        }
                    }, 5000);   //5 seconds
                System.out.println("In progress?" +pieChart.getRawRotationAngle());

            }
        });





    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_storage, menu);
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
            toolbar.setBackgroundColor(Color.parseColor("#ff0000"));
            TransitionManager.beginDelayedTransition((ViewGroup) Storage.this.findViewById(R.id.toolbar));
            MenuItemCompat.expandActionView(item);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
