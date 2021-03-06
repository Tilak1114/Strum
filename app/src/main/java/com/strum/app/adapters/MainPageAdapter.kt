package com.strum.app.adapters

import android.content.Context
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.PagerAdapter
import com.strum.app.R
import com.strum.app.models.MainScreenModel
import kotlinx.android.synthetic.main.pageritem.view.*


class MainPageAdapter(private val models: List<MainScreenModel>,
                      private val context: Context,
                      private val itemClickListener: ItemClickListener
): PagerAdapter(){

    interface ItemClickListener{
        fun onItemClick(itemId: Int?, progress: Int?)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return models.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = layoutInflater.inflate(R.layout.pageritem, container, false)

        view.mainPagerItemTitle.text = models[position].title

        if(models[position].title.equals("Personal")){
            view.mainPagerItemTitle.setTextColor(ContextCompat.getColor(context,
                R.color.personalColor
            ))
            //view.mainPagerItemPgBar.progressDrawable.setColorFilter(ContextCompat.getColor(context, R.color.personalColor),PorterDuff.Mode.MULTIPLY)
        }

        else{
            view.mainPagerItemTitle.setTextColor(ContextCompat.getColor(context,
                R.color.workColor
            ))
            view.mainPagerItemPgBar.progressDrawable.setColorFilter(ContextCompat.getColor(context,
                R.color.workColor
            ),PorterDuff.Mode.MULTIPLY)
        }

        view.mainPagerItemIcn.setImageResource(models[position].imgId)
        view.mainPagerItemPgBar.progress = models[position].progress
        view.mainPagerItemSubtitle.text = models[position].subTitleNum.toString()+"/"+models[position].subTitleDen+" Tasks"

        // click listener

        view.setOnClickListener {
            itemClickListener.onItemClick(position, models[position].progress)
        }

        container.addView(view)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }
}