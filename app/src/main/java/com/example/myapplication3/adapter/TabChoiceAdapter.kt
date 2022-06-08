package com.example.myapplication3.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabChoiceAdapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        val fragment: Fragment = ViewpagerFragment()
        val args = Bundle()
        args.putInt(ViewpagerFragment.ARG_OBJECT, position + 1)
        fragment.arguments = args
        return fragment
    }

    override fun getItemCount(): Int {
        return 3
    }
}