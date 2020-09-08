package com.pb.taskcapfront.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pb.taskcapfront.R
import com.pb.taskcapfront.model.response.dashboard.Contents
import com.pb.taskcapfront.model.response.dashboard.Products
import kotlinx.android.synthetic.main.child_products.view.*
import kotlinx.android.synthetic.main.child_splitbanner.view.*

/**
 * Created by balaji on 7/9/20 11:50 PM
 */


class ProductsAdapter(private val context: Context) :
    RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {

    private var productsList: List<Products> = emptyList()

    fun setProducts(lProductsList: List<Products>) {
        this.productsList = lProductsList
        notifyDataSetChanged()
    }

    inner class ProductsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(product: Products) {
            // set the name, price, image, type
            itemView.productImage?.apply {
                Glide.with(context).load(product.image)
                    .placeholder(R.drawable.placholder_image)
                    .into(this)
            }
            itemView.productNameTxt.text = product.name
            itemView.productTypeTxt.text = product.type
            val price = "${context.getString(R.string.rupee_symbol) + " " + product.price}/-"
            itemView.productPriceTxt.text = price
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        try {
            val view = getContentView(parent, R.layout.child_products)
            return ProductsViewHolder(view)
        } catch (ex: Exception) {
            ex.printStackTrace()
            throw Exception("View must not be null")
        }
    }

    override fun getItemCount(): Int {
        return productsList.size
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        holder.bindItems(productsList[position])
    }

    private fun getContentView(parent: ViewGroup, layout: Int): View {
        return LayoutInflater.from(parent.context).inflate(layout, parent, false)
    }

}