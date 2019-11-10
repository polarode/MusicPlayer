package de.elite.musicplayer.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import de.elite.musicplayer.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_queue, R.string.tab_text_player, R.string.tab_text_folder, R.string.tab_text_playlists};
    private final Context mContext;

    private QueueFragment queueFragment = new QueueFragment();
    private PlayerFragment playerFragment = new PlayerFragment();
    private FolderFragment folderFragment = new FolderFragment();
    private PlaylistsFragment playlistsFragment = new PlaylistsFragment();

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return queueFragment;
            case 1:
                return playerFragment;
            case 2:
                return folderFragment;
            case 3:
                return playlistsFragment;
            default:
                throw new UnsupportedOperationException("unknown tab index");
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return TAB_TITLES.length;
    }
}