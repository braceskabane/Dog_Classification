//package com.dicoding.asclepius.data.remote.retrofit
//
//import com.dicoding.asclepius.BuildConfig
//import com.google.gson.GsonBuilder
//import okhttp3.OkHttpClient
//import okhttp3.logging.HttpLoggingInterceptor
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//
//class ApiConfig {
//    companion object {
//        fun getApiService(): ApiService {
//            val loggingInterceptor = if (BuildConfig.DEBUG) {
//                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
//            } else {
//                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
//            }
//            val client = OkHttpClient.Builder()
//                .addInterceptor(loggingInterceptor)
//                .build()
//            val gson = GsonBuilder()
//                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
//                .create()
//            val BASE_URL = BuildConfig.BASE_URL
//
//            val retrofit = Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .client(client)
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .build()
//
//            return retrofit.create(ApiService::class.java)
//        }
//    }
//}
