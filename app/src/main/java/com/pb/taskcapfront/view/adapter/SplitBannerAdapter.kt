package com.pb.taskcapfront.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pb.taskcapfront.R
import kotlinx.android.synthetic.main.child_splitbanner.view.*

/**
 * Created by balaji on 7/9/20 11:50 PM
 */


class SplitBannerAdapter(private val context: Context) :
    RecyclerView.Adapter<SplitBannerAdapter.SplitBannerViewHolder>() {

    private var splitBannerList: List<String> = emptyList()

    fun setItems(lProductsList: List<String>) {
        this.splitBannerList = lProductsList
        notifyDataSetChanged()
    }

    inner class SplitBannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(bannerImage: String) {
            itemView.splitBannerImage?.apply {
                Glide.with(context).load(bannerImage)
                    .placeholder(R.drawable.placholder_image)
                    .into(this)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SplitBannerViewHolder {
        try {
            val view = getContentView(parent,R.layout.child_splitbanner)
            return SplitBannerViewHolder(view)
        } catch (ex: Exception) {
            ex.printStackTrace()
            throw Exception("View must not be null")
        }
    }

    override fun getItemCount(): Int {
        return splitBannerList.size
    }

    override fun onBindViewHolder(holder: SplitBannerViewHolder, position: Int) {
        holder.bindItems(splitBannerList[position])
    }

    private fun getContentView(parent: ViewGroup, layout: Int): View {
        return LayoutInflater.from(parent.context).inflate(layout, parent, false)
    }

}