package uk.ac.sussex.deliveryservice;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import uk.ac.sussex.deliveryservice.model.DrivingInstruction;
import uk.ac.sussex.deliveryservice.model.Route;

public class RouteDirectionsActivity extends AppCompatActivity {

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
    private ArrayList<DrivingInstruction> instructions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_directions);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i = getIntent();
        instructions = (ArrayList<DrivingInstruction>) i.getSerializableExtra("directions");
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        Bundle instructionsBundle = new Bundle();
        instructionsBundle.putSerializable("instructions", instructions);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), instructionsBundle);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class DirectionsFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";


        public DirectionsFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static DirectionsFragment newInstance(int sectionNumber, ArrayList<DrivingInstruction> instructions) {
            DirectionsFragment fragment = new DirectionsFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putSerializable("INSTRUCTION", instructions.get(sectionNumber));
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            final Bundle args = getArguments();
            final DrivingInstruction instruction = (DrivingInstruction) args.getSerializable("instruction");

        }

       @Override
        public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.activity_directions_list, container, false);
            //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
           final Bundle args = getArguments();
           final DrivingInstruction instruction = (DrivingInstruction) args.getSerializable("instruction");


           ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(),
                   R.layout.directions_single_element_view, instruction.getDirections()) {
               @Override
               public View getView(int position, View convertView, ViewGroup parent) {
                   View row;

                   if (null == convertView) {
                       row = inflater.inflate(R.layout.directions_single_element_view, null);
                   } else {
                       row = convertView;
                   }


                   TextView tv = (TextView) row.findViewById(R.id.direction_item);
                   tv.setText(Html.fromHtml(getItem(position)));
                   //tv.setText(getItem(position));

                   return row;
               }

           };

           ListView listView = (ListView) rootView.findViewById(R.id.directions_list);
           listView.setAdapter(adapter);



            //textView.setText(instruction.getDirections().get(0));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private  Bundle data;
        public SectionsPagerAdapter(FragmentManager fm, Bundle instructions) {
            super(fm);
            this.data = instructions;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a DirectionsFragment (defined as a static inner class below).
            Fragment fragment =  DirectionsFragment.newInstance(position, instructions);
            Bundle b = new Bundle();
            ArrayList<DrivingInstruction> instructions = (ArrayList<DrivingInstruction>) data.getSerializable("instructions");
            b.putSerializable("instruction", instructions.get(position));
            fragment.setArguments(b);
            return fragment;
        }

        @Override
        public int getCount() {
            return instructions.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return "I'll sort this out";
        }
    }
}
