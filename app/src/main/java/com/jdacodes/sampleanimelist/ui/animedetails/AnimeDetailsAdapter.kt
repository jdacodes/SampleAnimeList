package com.jdacodes.sampleanimelist.ui.animedetails

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jdacodes.sampleanimelist.ui.animedetails.overview.AnimeOverview

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
            1 -> AnimeOverview()
            else -> AnimeOverview()
        }
    }

}