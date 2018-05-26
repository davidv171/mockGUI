package davidv7.avastgui;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.linroid.filtermenu.library.FilterMenu;
import com.linroid.filtermenu.library.FilterMenuLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.datatype.Duration;

public class Apps extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apps);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(Color.TRANSPARENT);

        toolbar.setTitleTextColor(getResources().getColor(R.color.red));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(toolbar);



        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setTabTextColors(getResources().getColor(R.color.black),getResources().getColor(R.color.red));

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_apps, menu);
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
            TransitionManager.beginDelayedTransition((ViewGroup) Apps.this.findViewById(R.id.toolbar));
            MenuItemCompat.expandActionView(item);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        private List<AppList>appList = new ArrayList<>();
        private RecyclerView rv;
        private AppListAdapter adapter;
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_apps2, container, false);
            Context context = getActivity().getApplicationContext();
            Intent fragIntent = new Intent(Intent.ACTION_MAIN,null);
            fragIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            final PackageManager pm = context.getPackageManager();
            final List<ResolveInfo> mApps = pm.queryIntentActivities(fragIntent,0);
            rv = rootView.findViewById(R.id.rv);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
            rv.setLayoutManager(mLayoutManager);
            rv.setItemAnimator(new DefaultItemAnimator());
            adapter = new AppListAdapter(appList);
            rv.setAdapter(adapter);
            rv.addItemDecoration(new DividerItemDecoration(context,
                    DividerItemDecoration.VERTICAL));
            appList.clear();

            if(getArguments().getInt(ARG_SECTION_NUMBER)==1){
                final Handler handler = new Handler();
                //last index, used to get the last iterator inside the handler
                //used to print out apps by 15 every second
                final ArrayList<Integer>lastIndex = new ArrayList<>();
                lastIndex.add(3);
                //Init first 15 applications
                for(int i = 0;i<2;i++){
                    ResolveInfo info = mApps.get(i);
                    String appTitle = info.loadLabel(pm).toString();
                    Drawable appIcon = info.loadIcon(pm);
                    AppList newInstance = new AppList(appTitle, appIcon);
                    appList.add(newInstance);
                    adapter.notifyDataSetChanged();
                }
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Running");
                        int i;
                      for(i = lastIndex.get(0) ;i<lastIndex.get(0)+15;i++) {
                          ResolveInfo info = null;
                          try {
                              info = mApps.get(i);
                          }
                          catch(IndexOutOfBoundsException e){
                              Toast.makeText(getActivity().getApplicationContext(),"Loading apps complete", Toast.LENGTH_SHORT);

                              handler.removeCallbacks(this);
                              break;
                          }
                          String appTitle = info.loadLabel(pm).toString();
                          Drawable appIcon = info.loadIcon(pm);
                          AppList newInstance = new AppList(appTitle, appIcon);
                          appList.add(newInstance);
                          adapter.notifyDataSetChanged();


                      }
                        lastIndex.clear();
                        lastIndex.add(i);
                        System.out.println("INDEX" + lastIndex.get(0));
                        //to make it run more than once
                        handler.postDelayed(this, 500);
                        //Once we iterate over all the apps, we end the handler!
                        if(lastIndex.get(0)>=mApps.size()){

                            handler.removeCallbacks(this);
                        }
                        System.out.println("LOOPIN");

                    }
                },1000);

            }
            ArrayList<Integer>favoriteIntegers = new ArrayList<>();

            favoriteIntegers.add(2);
            favoriteIntegers.add(3);
            favoriteIntegers.add(4);
            if(getArguments().getInt(ARG_SECTION_NUMBER)==2){
                //Print out random 3 apps, from favoriteIntegers arraylist
                for(int i = 0;i<favoriteIntegers.size();i++){
                    ResolveInfo info = mApps.get(favoriteIntegers.get(i));
                    String appTitle = info.loadLabel(pm).toString();
                    Drawable appIcon = info.loadIcon(pm);
                    AppList newInstance = new AppList(appTitle, appIcon);
                    appList.add(newInstance);
                    adapter.notifyDataSetChanged();
                }
            }
            if(getArguments().getInt(ARG_SECTION_NUMBER)==3){
                //Print out 3 different apps
                System.out.println("SECTION" + getArguments().getInt(ARG_SECTION_NUMBER));
                for(int i = mApps.size()-1;i>=mApps.size()-favoriteIntegers.size();i--){

                    ResolveInfo info = mApps.get(i);
                    String appTitle = info.loadLabel(pm).toString();
                    Drawable appIcon = info.loadIcon(pm);
                    AppList newInstance = new AppList(appTitle, appIcon);
                    appList.add(newInstance);
                    adapter.notifyDataSetChanged();
                }
            }

            //A fab that listens for a click to sort by alphabet!
            FloatingActionButton fab = rootView.findViewById(R.id.sort);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //get the list of titles into a temporary arraylist,sort that arraylist, put it into the main arraylist
                    ArrayList<String>titleList = new ArrayList<>();
                    Map<String,Drawable> map = new HashMap<>();
                    for(int i =0;i<appList.size();i++){
                        map.put(appList.get(i).getAppName(),appList.get(i).getAppIcon());
                    }
                    Map<String,Drawable>sortedMap = new TreeMap<>(map);

                    //set the value of the sorted titleList into appList
                    ArrayList<Drawable>iconList = new ArrayList<>(sortedMap.values());
                    for ( String key : sortedMap.keySet() ) {
                        titleList.add(key);
                    }
                    appList.clear();
                    for(int i =0;i<titleList.size();i++){
                        appList.add(new AppList(titleList.get(i),iconList.get(i)));
                    }
                    adapter.notifyDataSetChanged();
                }
            });
            return rootView;
        }

        //used for iterating with the Handler, loading 15 apps at a time!

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }
}
