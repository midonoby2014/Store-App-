package com.noby.storeapp.ui.productdetails

import android.content.Intent.getIntent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.noby.storeapp.R
import com.noby.storeapp.data.room.entity.ProductsDb
import com.noby.storeapp.databinding.FragmentProductDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailsFragment() : Fragment() {

    var getBundle: Bundle? = null
    var productsDb: ProductsDb? = null
    lateinit var binding: FragmentProductDetailsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_product_details, container, false)
        var view:View = binding!!.getRoot()
        Log.d("productsssssss2"," productsDb!!.title")
        productsDb = requireArguments().getParcelable("product") as ProductsDb?
        productsDb.let {
            binding.product =  it
            Glide.with(requireActivity())
                .load(it!!.image)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(binding.imageView)
        }
        return view
    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        productsDb = requireArguments().getParcelable("product") as ProductsDb?
//    }
}