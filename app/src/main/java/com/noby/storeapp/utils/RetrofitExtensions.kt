package com.noby.storeapp.utils

import retrofit2.Response
import retrofit2.Retrofit


/**
 * Created by Ahmed Noby Ahmed on 3/19/23.
 */

// Retrofit
inline fun <reified T> Retrofit.create(): T = create(T::class.java)

fun <T> httpError(code: Int): Response<T> = Response.error<T>(code, null)