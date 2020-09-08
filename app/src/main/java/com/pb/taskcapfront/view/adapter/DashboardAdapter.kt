package com.pb.taskcapfront.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.pb.taskcapfront.R
import com.pb.taskcapfront.enums.SectionType
import com.pb.taskcapfront.model.response.dashboard.Contents
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.child_banner.view.*
import kotlinx.android.synthetic.main.layout_category.view.*
import kotlinx.android.synthetic.main.layout_recycler.view.*

/**
 * Created by balaji on 7/9/20 11:50 PM
 */

class DashboardAdapter(private val context: Context) :
    RecyclerView.Adapter<DashboardAdapter.DashboardViewHolder>() {

    private var contentsList: List<Contents> = emptyList()
    private val recyclerViewPool = RecyclerView.RecycledViewPool()

    fun setContents(lContentsList: List<Contents>) {
        this.contentsList = lContentsList
        notifyDataSetChanged()
    }

    inner class DashboardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(contents: Contents) {
            when (contents.sectionType) {
                SectionType.BANNER -> {
                    itemView.bannerImage?.apply {
                        try {
                            Glide.with(context).load(contents.bannerImage)
                                .placeholder(R.drawable.placholder_image)
                                .into(this)
                        } catch (ex: Exception) {
                            ex.printStackTrace()
                        }
                    }
                }

                SectionType.SPLIT_BANNER -> {
                    try {
                        val gridLayoutManager = GridLayoutManager(context, 2)
                        val splitBannerAdapter = SplitBannerAdapter(context)
                        itemView.genericRecycler?.apply {
                            layoutManager = gridLayoutManager
                            adapter = splitBannerAdapter
                        }
                        splitBannerAdapter.setItems(
                            listOf(
                                contents.firstImage,
                                contents.secondImage
                            )
                        )
                    } catch (ex: Exception) {
                        ex.printStackTrace()
                    }
                }

                SectionType.HORIZONTAL_FREE_SCROLL -> {
                    try {
                        itemView.categoryTitle.text = contents.name
                        val linearLayoutManager =
                            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                        val productsAdapter = ProductsAdapter(context)
                        val dashboardRecyclerView =
                            itemView.categoryRecycler.findViewById<RecyclerView>(R.id.genericRecycler)
                        dashboardRecyclerView?.apply {
                            layoutManager = linearLayoutManager
                            adapter = productsAdapter
                            setRecycledViewPool(recyclerViewPool)
                        }
                        productsAdapter.setProducts(contents.products)
                    } catch (ex: Exception) {
                        ex.printStackTrace()
                    }
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        try {
            val view = when (SectionType.values()[viewType]) {
                SectionType.SPLIT_BANNER -> {
                    getContentView(parent, R.layout.layout_recycler)
                }
                SectionType.BANNER -> {
                    getContentView(parent, R.layout.child_banner)
                }
                SectionType.HORIZONTAL_FREE_SCROLL -> {
                    getContentView(parent, R.layout.layout_category)
                }
            }
            return DashboardViewHolder(view)
        } catch (ex: Exception) {
            ex.printStackTrace()
            throw Exception("View must not be null")
        }
    }

    override fun getItemViewType(position: Int): Int {
        super.getItemViewType(position)
        try {
            return when (contentsList[position].sectionType) {
                SectionType.SPLIT_BANNER -> {
                    SectionType.SPLIT_BANNER.ordinal
                }
                SectionType.BANNER -> {
                    SectionType.BANNER.ordinal
                }
                SectionType.HORIZONTAL_FREE_SCROLL -> {
                    SectionType.HORIZONTAL_FREE_SCROLL.ordinal
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            throw Exception("No View type found")
        }

    }

    override fun getItemCount(): Int {
        return contentsList.size
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        holder.bindItems(contents = contentsList[position])
    }

    private fun getContentView(parent: ViewGroup, layout: Int): View {
        return LayoutInflater.from(parent.context).inflate(layout, parent, false)
    }

    //
    /**
     * Pending things
     * 1. Splash
     * 2. Dashboard
     * 3. Child Item Product, Category, banner, split banner
     * 4. Handling internet issue
     * 5. Dagger (try)
     * 6. Unit testing (try)
     * 7. try to check option banner in the top
     */

}