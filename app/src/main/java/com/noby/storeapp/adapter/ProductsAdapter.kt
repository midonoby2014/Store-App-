package com.noby.storeapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.noby.storeapp.data.room.entity.ProductsDb
import com.noby.storeapp.databinding.ProductsItemBinding


/**
 * Created by Ahmed Noby Ahmed on 3/19/23.
 */
class ProductsAdapter (private val listener :onItemClickListener)
    : ListAdapter<ProductsDb, ProductsAdapter.ArticleViewHolder>(ArticleDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ArticleViewHolder( ProductsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class ArticleViewHolder(private val binder: ProductsItemBinding) : RecyclerView.ViewHolder(binder.root) {

        fun bind(product: ProductsDb) {
            Glide.with(itemView)
                .load(product.image)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .skipMemoryCache(true)
                .into(binder.imgNews)
            binder.product = product
            binder.root.setOnClickListener { listener.onItemClick(product) }
            binder.executePendingBindings()
        }
    }

    interface onItemClickListener{
        fun onItemClick (product: ProductsDb)
    }
    private object ArticleDiff : DiffUtil.ItemCallback<ProductsDb>() {

        override fun areItemsTheSame(oldItem: ProductsDb, newItem: ProductsDb) = oldItem == newItem

        override fun areContentsTheSame(oldItem: ProductsDb, newItem: ProductsDb): Boolean {
            // In this case, if items are the same then content will always be the same
            return true
        }

    }
}
