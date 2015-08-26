package com.andryr.musicplayer.fragments;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andryr.musicplayer.R;

/**
 * A simple {@link Fragment} subclass. Use the {@link MainFragment#newInstance}
 * factory method to create an instance of this fragment.
 */
public class MainFragment extends BaseFragment {

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
     * derivative, which will keep every loaded fragment in memory. If this
     * becomes too memory intensive, it may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();

        return fragment;
    }

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container,
                false);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(
                getChildFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) rootView.findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        ((ActionBarActivity) getActivity()).setSupportActionBar(toolbar);
        return rootView;
    }

    @Override
    public void refresh() {
        int fragmentCount = mSectionsPagerAdapter.getCount();
        for(int pos = 0; pos < fragmentCount; pos++)
        {
            BaseFragment fragment = (BaseFragment) mSectionsPagerAdapter.getFragment(pos);
            if(fragment != null)
            {
                Log.d("frag1", fragment.getClass().getCanonicalName());

                fragment.refresh();
            }
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private Map<Integer, String> mFragmentTags;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            mFragmentTags = new HashMap<Integer, String>();

        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return SongListFragment.newInstance();
                case 1:
                    return AlbumListFragment.newInstance(null);
                case 2:
                    return ArtistListFragment.newInstance();
                case 3:
                    return GenreListFragment.newInstance();
                case 4:
                    return PlaylistBrowserFragment.newInstance();
            }
            return null;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Object obj = super.instantiateItem(container, position);
            if (obj instanceof Fragment) {
                Fragment f = (Fragment) obj;
                String tag = f.getTag();
                mFragmentTags.put(position, tag);
                Log.d("fragtag", tag);

            }
            return obj;
        }

        public Fragment getFragment(int position) {
            String tag = mFragmentTags.get(position);
            if (tag == null)
                return null;
            return getChildFragmentManager().findFragmentByTag(tag);
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.titles).toUpperCase(l);
                case 1:
                    return getString(R.string.albums).toUpperCase(l);
                case 2:
                    return getString(R.string.artists).toUpperCase(l);
                case 3:
                    return getString(R.string.genres).toUpperCase(l);
                case 4:
                    return getString(R.string.playlists).toUpperCase(l);

            }
            return null;
        }
    }

}