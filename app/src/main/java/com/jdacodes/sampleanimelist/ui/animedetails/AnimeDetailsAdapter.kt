package com.jdacodes.sampleanimelist.ui.animedetails

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jdacodes.sampleanimelist.ui.animedetails.tabs.AnimeOverview
import com.jdacodes.sampleanimelist.ui.animedetails.tabs.AnimeStaffFragment

class AnimeDetailsAdapter(
    fragment: Fragment,
    private var totalCount: Int
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return totalCount
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AnimeOverview()
            1 -> AnimeStaffFragment()
            else -> AnimeOverview()
        }
    }

}