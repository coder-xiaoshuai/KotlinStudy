package com.example.kotlinstudy.adapter

import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.example.common_ui.base.BaseFragment


class CommonVpAdapter : FragmentStatePagerAdapter {
    private lateinit var fragmentList: List<Fragment>
    private lateinit var titles: List<String>

    constructor(fragmentManager: FragmentManager, fragments: List<Fragment>, titles: List<String>) : super(
        fragmentManager) {
        this.fragmentList = fragments
        this.titles = titles
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        // 覆写destroyItem并且空实现,这样每个Fragment中的视图就不会被销毁
//        super.destroyItem(container, position, `object`)
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

    fun getFragments(): List<Fragment> {
        return fragmentList
    }
}