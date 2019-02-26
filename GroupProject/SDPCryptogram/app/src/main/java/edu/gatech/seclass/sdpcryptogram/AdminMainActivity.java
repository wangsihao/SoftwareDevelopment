package edu.gatech.seclass.sdpcryptogram;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdminMainActivity extends AppCompatActivity {
    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    Library mLibrary;
    private int currentTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        mLibrary = Library.getInstance();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(getIntent().getIntExtra("tab_index", 0));
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currentTab = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentTab == 0) {
                    Intent intent  = new Intent(view.getContext(), AddEditCryptogramActivity.class);
                    intent.putExtra("isExisting", false);
                    startActivity(intent);
                } else if(currentTab == 1) {
                    Intent intent  = new Intent(AdminMainActivity.this, AddEditUserActivity.class);
                    intent.putExtra("isExisting", false);
                    startActivity(intent);
                } else {
                    Snackbar.make(view, "Something went wrong, please report.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.admin_menuitem_logout) {
            Intent intent  = new Intent(AdminMainActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private static class AdminCryptogramAdapter extends ArrayAdapter<Cryptogram> {
        AdminCryptogramAdapter(Context mContext, ArrayList<Cryptogram> mCAL) { super(mContext, 0, mCAL); }

        @NonNull
        @Override
        public View getView(int position, View view, @NonNull ViewGroup parent) {
            Cryptogram currentCryptogram = getItem(position);
            if(view == null) {
                view = LayoutInflater.from(getContext()).inflate(R.layout.listrow_admin_cryptogram, parent, false);
            }
            if(currentCryptogram != null) {
                ((TextView) view.findViewById(R.id.row_admin_cleartext)).setText(currentCryptogram.getSolutionPhrase());
                ((TextView) view.findViewById(R.id.row_admin_ciphertext)).setText(currentCryptogram.getEncodedPhrase());
                ((TextView) view.findViewById(R.id.row_admin_cryptogram_ID)).setText(Integer.toString(currentCryptogram.getCryptogramID()));

            }
            return view;
        }
    }

    private static class AdminPlayerAdapter extends ArrayAdapter<Player> {
        AdminPlayerAdapter(Context mContext, ArrayList<Player> mPAL) {
            super(mContext, 0, mPAL);
        }

        @NonNull
        @Override
        public View getView(int position, View view, @NonNull ViewGroup parent) {
            Player currentPlayer = getItem(position);
            if(view == null) {
                view = LayoutInflater.from(getContext()).inflate(R.layout.listrow_admin_player, parent, false);
            }
            if(currentPlayer != null) {
                ((TextView) view.findViewById(R.id.row_admin_playername)).setText(currentPlayer.getFirstname() + " " + currentPlayer.getLastname());
                ((TextView) view.findViewById(R.id.row_admin_username)).setText(currentPlayer.getUsername());
            }
            return view;
        }
    }

    public static class Admin_Tab1_Cryptograms extends Fragment {
        Library mLibrary;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_admin_tab1_cryptograms, container, false);
            perform(rootView);
            return rootView;
        }

        private void perform(View view) {
            mLibrary = Library.getInstance();
            final AdminCryptogramAdapter ACA = new AdminCryptogramAdapter(getActivity(), mLibrary.getAllCryptograms());
            ((ListView) view.findViewById(R.id.listview_admin_cryptograms)).setAdapter(ACA);

            //Do not remove - This code enables cryptogram items to be editted from admin activity
            /*((ListView) view.findViewById(R.id.listview_admin_cryptograms)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent  = new Intent(view.getContext(), AddEditCryptogramActivity.class);
                    intent.putExtra("isExisting", true);
                    intent.putExtra("cryptogramID", ACA.getItem(position).cryptogramID);
                    ////////BEGIN DEBUG ONLY ///////
                    intent.putExtra("clear", ACA.getItem(position).cleartext);
                    intent.putExtra("cipher", ACA.getItem(position).ciphertext);
                    ////////END DEBUG ONLY ////////
                    startActivity(intent);
                }
            });*/

        }
    }

    public static class Admin_Tab2_Users extends Fragment {
        Library mLibrary;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_admin_tab2_users, container, false);
            perform(rootView);
            return rootView;
        }

        private void perform(View view) {
            mLibrary = Library.getInstance();
            final AdminPlayerAdapter APA = new AdminPlayerAdapter(getActivity(), mLibrary.getPlayers());
            ((ListView) view.findViewById(R.id.listview_admin_users)).setAdapter(APA);

            ((ListView) view.findViewById(R.id.listview_admin_users)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent  = new Intent(view.getContext(), AddEditUserActivity.class);
                    intent.putExtra("isExisting", true);
                    intent.putExtra("username", APA.getItem(position).getUsername());
                    intent.putExtra("first", APA.getItem(position).getFirstname());
                    intent.putExtra("last", APA.getItem(position).getLastname());
                    startActivity(intent);
                }
            });
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    private class SectionsPagerAdapter extends FragmentPagerAdapter {
        SectionsPagerAdapter(FragmentManager fm) {super(fm);}

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return new Admin_Tab1_Cryptograms();
                case 1:
                    return new Admin_Tab2_Users();
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
                    return "USERS";
            }
            return null;
        }
    }
}
