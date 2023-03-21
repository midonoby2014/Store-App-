package com.noby.storeapp.ui.productslist

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.noby.storeapp.R
import com.noby.storeapp.adapter.ProductsAdapter
import com.noby.storeapp.data.room.entity.ProductsDb
import com.noby.storeapp.databinding.FragmentProductListBinding
import com.noby.storeapp.utils.ViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductListFragment : Fragment() ,ProductsAdapter.onItemClickListener{


    private val viewModel: ProductListViewModel by viewModels()
    lateinit var binding:FragmentProductListBinding
    lateinit var adapter: ProductsAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_product_list, container, false)
        var view:View = binding!!.getRoot()
        initRecycle()
        getData()
        return view
    }

    fun getData(){
        viewModel.getProductsList()
        viewModel.getProducts.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ViewState.Success -> {
                    hideProgressBar()
                    Log.e(ContentValues.TAG,  state.data.size.toString())
                    adapter.submitList(state.data)
                }
                is ViewState.Loading -> showProgressBar()
                is ViewState.Error -> {
                    hideProgressBar()
                    Toast.makeText(requireActivity() , state.message, Toast.LENGTH_LONG ).show()
                }
            }
        }
    }

    private fun initRecycle() {
        adapter = ProductsAdapter(this)
        binding.rvList.layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
        binding.rvList.adapter = adapter
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun onItemClick(product: ProductsDb) {
        val bundel = Bundle()
        bundel.putParcelable("product", product)
        findNavController().navigate(R.id.action_newsFragment_to_productDetailsFragment, bundel)
    }
}