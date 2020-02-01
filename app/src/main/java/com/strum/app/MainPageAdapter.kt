package com.strum.app

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import kotlinx.android.synthetic.main.pageritem.view.*

class MainPageAdapter(val models: List<MainScreenModel>, val context: Context): PagerAdapter(){

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view.equals(`object`)
    }

    override fun getCount(): Int {
        return models.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var layoutInflater: LayoutInflater = LayoutInflater.from(context)
        var view: View = layoutInflater.inflate(R.layout.pageritem, container, false)

        view.mainPagerItemTitle.text = models[position].title
        view.mainPagerItemIcn.setImageResource(models[position].imgId)
        view.mainPagerItemPgBar.setProgress(models[position].progress)
        view.mainPagerItemSubtitle.text = models[position].subTitle

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }
}