package com.noby.storeapp.ui.productslist

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noby.storeapp.data.repository.ProductsRepository
import com.noby.storeapp.data.room.entity.ProductsDb
import com.noby.storeapp.utils.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val productsRepository: ProductsRepository,
    private val app : Application
) : ViewModel(){
    private val  _getProducts  =  MutableLiveData<ViewState<List<ProductsDb>>>()
    val  getProducts: LiveData<ViewState<List<ProductsDb>>> get()  =  _getProducts
    fun getProductsList(){
        viewModelScope.launch {
            productsRepository.getProductsArticles(app).collect {
                _getProducts.value =  it
            }
        }
    }
}