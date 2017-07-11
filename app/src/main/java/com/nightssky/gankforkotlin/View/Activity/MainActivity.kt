package com.nightssky.gankforkotlin.View.Activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import android.support.v7.app.AppCompatActivity
import com.nightssky.gankforkotlin.Model.Constants
import com.nightssky.gankforkotlin.Model.getList
import com.nightssky.gankforkotlin.R
import com.nightssky.gankforkotlin.View.Contract.MainContract
import com.nightssky.gankforkotlin.View.Fragment.MeiziFragment
import com.nightssky.gankforkotlin.View.Fragment.RecommendFragment
import com.nightssky.gankforkotlin.View.Fragment.TypeFragment
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity(),MainContract.View {


    private lateinit var list: MutableList<Fragment>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setView()
        setViewPager()
    }

     override fun setView() {
         toolbar.title = "GankForKotlin"
        setTabLayout()
        label.setOnClickListener({
            startActivity(Intent(MainActivity@this,LabelActivity::class.java ))
        })
        search.setOnClickListener({ startActivity(Intent(MainActivity@this,SearchActivity::class.java ))})

    }

    fun setTabLayout() {
        tablayout.addTab(tablayout.newTab().setText("每日推荐"))
        Constants.TYPELIST.getList().forEach {

            tablayout.addTab(tablayout.newTab().setText(it))
        }
    }
    override fun setViewPager() {
        tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {}
            override fun onTabUnselected(p0: TabLayout.Tab?) {}
            override fun onTabSelected(tab: TabLayout.Tab?) {
                container.currentItem = tab!!.position;

            }

        })
        container.addOnPageChangeListener(object : TabLayout.TabLayoutOnPageChangeListener(tablayout) {})
         setFAdapter()
    }

    fun setFAdapter() {
        list = mutableListOf<Fragment>()
        list.add(RecommendFragment())
        Constants.TYPELIST.getList().forEach {
            when {
                it.equals("福利") ->  list.add(MeiziFragment())
                else ->             list.add(TypeFragment().setType(it))
            }
        }
        //ViewPager的适配器
        var adapter: MyAdapter =  MyAdapter(supportFragmentManager);
        container.adapter = adapter;
    }

    override fun onResume() {
        super.onResume()
        if (Constants.TABISCHANGED) {
            tablayout.removeAllTabs()
            setTabLayout()
            container.removeAllViews()
            setFAdapter()
            Constants.TABISCHANGED = false
        }

    }

    internal inner class MyAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return list[position]
        }

        override fun getCount(): Int {
            return list.size
        }
        override fun getPageTitle(position: Int): CharSequence = Constants.TYPELIST[position]


        override fun getItemPosition(`object`: Any?): Int = PagerAdapter.POSITION_NONE

    }

}
