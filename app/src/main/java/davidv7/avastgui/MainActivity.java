package davidv7.avastgui;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.black));
        drawer.addDrawerListener(toggle);
        //HIDE the infinite progress bar
        final CircularProgressButton progressBar1 = findViewById(R.id.progressBar);
        final CircularProgressButton progressBar2 = findViewById(R.id.progressBar2);
        toggle.syncState();

        NavigationView navigationView =findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //enables custom colors of the icons in the navigationd rawer
        navigationView.setItemIconTintList(null);

        //PULSATING ANIMATION
        final ImageView startB = findViewById(R.id.startImg);
        final ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(
                startB,
                PropertyValuesHolder.ofFloat("scaleX", 1.2f),
                PropertyValuesHolder.ofFloat("scaleY", 1.2f));
        scaleDown.setDuration(310);
        scaleDown.setRepeatCount(ObjectAnimator.INFINITE);
        scaleDown.setRepeatMode(ObjectAnimator.REVERSE);
        scaleDown.setStartDelay(5000);
        scaleDown.start();
        //textview that shows "Complete" or "In progress" when animation is in progress
        final TextView completeText = findViewById(R.id.clean);
        final TextView doingNothingText1 = findViewById(R.id.progressText1);

        final TextView doingNothingText2 = findViewById(R.id.progressText2);
        doingNothingText1.setVisibility(View.INVISIBLE);
        doingNothingText2.setVisibility(View.INVISIBLE);
        progressBar1.setVisibility(View.INVISIBLE);
        progressBar2.setVisibility(View.INVISIBLE);


        startB.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                scaleDown.cancel();
                final CircularProgressBar circularProgressBar = findViewById(R.id.circProgress);
                circularProgressBar.setColor(R.color.blue);
                RotateAnimation rotateAnimation = new RotateAnimation(0, 360,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                final int animationDuration = 20000; // 2500ms = 2,5s
                rotateAnimation.setDuration(animationDuration);

                startB.startAnimation(rotateAnimation);
                //HIDE infinite progress before animation starts, show when it starts
                //circular progress bar gets restarted  0 and then gets back to 100 every time animation starts
                rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        progressBar1.revertAnimation();
                        progressBar2.revertAnimation();
                        progressBar1.startAnimation();
                        progressBar2.startAnimation();
                        circularProgressBar.setProgressWithAnimation(0,0);
                        circularProgressBar.setProgressWithAnimation(100,animationDuration);
                        completeText.setText("In progress...");
                        doingNothingText1.setVisibility(View.VISIBLE);
                        doingNothingText2.setVisibility(View.VISIBLE);
                        progressBar1.setVisibility(View.VISIBLE);
                        progressBar2.setVisibility(View.VISIBLE);
                        //Fade in animation for doingNothingText1/2 and progressBar1/2

                        Animation startAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
                        doingNothingText1.startAnimation(startAnimation);
                        doingNothingText2.startAnimation(startAnimation);
                        progressBar1.startAnimation(startAnimation);
                        progressBar2.startAnimation(startAnimation);


                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        circularProgressBar.setBackgroundColor(getResources().getColor(R.color.complete));
                        //displays a black check when the animation is complete
                        Bitmap myLogo = ((BitmapDrawable) ResourcesCompat.getDrawable(getResources(), R.drawable.checked, null)).getBitmap();
                        progressBar1.doneLoadingAnimation(R.color.green,myLogo);
                        progressBar2.doneLoadingAnimation(R.color.green,myLogo);
                        completeText.setText("Complete!");
                        startB.setImageDrawable(getResources().getDrawable(R.drawable.refresh));
                        //TODO: Change Drawable gradually
                        scaleDown.start();

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                circularProgressBar.setProgressWithAnimation(100, animationDuration);


            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        // Retrieve the SearchView and plug it into SearchManager
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
        if(id == R.id.action_search){
            //change color of action bar to light blue
            Toolbar toolbar = findViewById(R.id.toolbar);
            toolbar.setBackgroundColor(Color.parseColor("#ff0000"));
            TransitionManager.beginDelayedTransition((ViewGroup) MainActivity.this.findViewById(R.id.toolbar));
            MenuItemCompat.expandActionView(item);


        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_app_list) {
            Intent intent = new Intent(MainActivity.this,Apps.class);
            startActivity(intent);
        }

        DrawerLayout drawer =findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
