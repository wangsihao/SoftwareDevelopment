package edu.gatech.seclass.sdpcryptogram;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class PlayerMainActivity extends AppCompatActivity {
    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    Library mLibrary;
    private static String currentUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_main);

        mLibrary = Library.getInstance();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        Intent intent = getIntent();
        currentUsername = intent.getStringExtra("username");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_player_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.player_menuitem_logout) {
            Intent intent  = new Intent(PlayerMainActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            currentUsername = "";
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private static class PlayerCryptogramAdapter extends ArrayAdapter<CryptogramInstance> {
        PlayerCryptogramAdapter(Context mContext, ArrayList<CryptogramInstance> mCIAL) {
            super(mContext, 0, mCIAL);
        }

        @NonNull
        @Override
        public View getView(int position, View view, @NonNull ViewGroup parent) {
            CryptogramInstance currentInstance = getItem(position);

            if(view == null) {
                view = LayoutInflater.from(getContext()).inflate(R.layout.listrow_player_cryptogram, parent, false);
            }

            if(currentInstance != null) {
                if(currentInstance.isSolved()) {
                    ((ImageView) view.findViewById(R.id.row_player_solved_icon)).setImageResource(android.R.drawable.ic_partial_secure);
                    ((ImageView) view.findViewById(R.id.row_player_solved_icon)).setColorFilter(0xff669900, PorterDuff.Mode.SRC_ATOP);
                    ((TextView) view.findViewById(R.id.row_player_solved_label)).setText(R.string.label_Solved);
                } else {
                    ((ImageView) view.findViewById(R.id.row_player_solved_icon)).setImageResource(android.R.drawable.ic_secure);
                    ((ImageView) view.findViewById(R.id.row_player_solved_icon)).setColorFilter(0xffe57373, PorterDuff.Mode.SRC_ATOP);
                    ((TextView) view.findViewById(R.id.row_player_solved_label)).setText("");
                }
                ((TextView) view.findViewById(R.id.row_player_cryptogram_ID)).setText(String.valueOf(currentInstance.getCryptogramID()));
                ((TextView) view.findViewById(R.id.row_player_cleartext)).setText(Library.getCryptogram(currentInstance.getCryptogramID()).getEncodedPhrase());
                ((TextView) view.findViewById(R.id.row_player_attempts)).setText(String.valueOf(currentInstance.getIncorrectAttempts()));
            }
            return view;
        }
    }


    private static class PlayerRatingAdapter extends ArrayAdapter<Rating> {
        PlayerRatingAdapter(Context mContext, ArrayList<Rating> mRAL) {
            super(mContext, 0, mRAL);
        }

        @NonNull
        @Override
        public View getView(int position, View view, @NonNull ViewGroup parent) {
            Rating currentRating = getItem(position);

            if(view == null) {
                view = LayoutInflater.from(getContext()).inflate(R.layout.listrow_player_rating, parent, false);
            }

            if(currentRating != null) {

                ((TextView) view.findViewById(R.id.row_player_name)).setText(currentRating.getFirstname() + " " + currentRating.getLastname());
                ((TextView) view.findViewById(R.id.row_player_solved)).setText(String.valueOf(currentRating.getCryptogramsSolved()));
                ((TextView) view.findViewById(R.id.row_player_started)).setText(String.valueOf(currentRating.getCryptogramsStarted()));
                ((TextView) view.findViewById(R.id.row_player_total)).setText(String.valueOf(currentRating.getTotalIncorrectSolutions()));
            }
            return view;
        }
    }

    public static class Player_Tab1_Cryptograms extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_player_tab1_cryptograms, container, false);
            perform(rootView);
            return rootView;
        }

        private void perform(View view) {
            ArrayList<CryptogramInstance> currentInstanceList = Library.getAllCryptogramInstances(currentUsername);
            ArrayList<Cryptogram> currentCryptogramList = Library.getAllCryptograms();

            for (Cryptogram c : currentCryptogramList) {
                boolean match = false;
                for(CryptogramInstance ci: currentInstanceList){
                    if((ci.getCryptogramID() == c.getCryptogramID()) && (ci.getUsername().equalsIgnoreCase(currentUsername))) {
                        match = true;
                    }
                }
                if(!match){
                    Library.addCryptogramInstance(new CryptogramInstance(c.getCryptogramID(), currentUsername, false, 0));
                }
            }

            final PlayerCryptogramAdapter PCA = new PlayerCryptogramAdapter(getActivity(), Library.getAllCryptogramInstances(currentUsername));
            ((ListView) view.findViewById(R.id.listview_player_cryptograms)).setAdapter(PCA);
            ((ListView) view.findViewById(R.id.listview_player_cryptograms)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent  = new Intent(view.getContext(), CryptogramActivity.class);
                    try {
                        intent.putExtra("cryptogramID", PCA.getItem(position).getCryptogramID());
                    } catch (NullPointerException e) {
                        intent.putExtra("cryptogramID", -1);
                    }
                    intent.putExtra("username", currentUsername);
                    startActivity(intent);
                }
            });
        }
    }

    public static class Player_Tab2_Ratings extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_player_tab2_ratings, container, false);
            perform(rootView);
            return rootView;
        }

        private void perform(View view) {
            PlayerRatingAdapter PRA = new PlayerRatingAdapter(getActivity(), Library.getPlayerRatings());
            ((ListView) view.findViewById(R.id.listview_player_ratings)).setAdapter(PRA);
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    private class SectionsPagerAdapter extends FragmentPagerAdapter {
        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return new Player_Tab1_Cryptograms();
                case 1:
                    return new Player_Tab2_Ratings();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "CRYPTOGRAMS";
                case 1:
                    return "RATINGS";
            }
            return null;
        }
    }
}
