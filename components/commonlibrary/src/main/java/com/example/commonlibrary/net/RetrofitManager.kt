package com.example.commonlibrary.net

import com.example.commonlibrary.cookie.CookieManager
import com.example.commonlibrary.util.Constants
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 *       Created by xiaolanlaia on 2019/5/6 13:39
 */
object RetrofitManager  {


        /**
         * 获取retrofit
         */
        fun getRetrofit(): Retrofit {


            /**
             * 设置okhttp
             */
            val okHttp = OkHttpClient.Builder()
                .addInterceptor {
                    val request = it.request().newBuilder()
//            request.addHeader("x-client-token", SharedHelper.getShared().getString("token", ""))
                    val response = it.proceed(request.build())

                    response
                }

                .addInterceptor(HttpLoggingInterceptor().apply {
                    this.level = HttpLoggingInterceptor.Level.BODY
                })
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .cookieJar(CookieManager.getInstance())

            return Retrofit.Builder()
                .client(okHttp.build())
                .baseUrl(Constants.URL.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }


        /**
         * 封装RequestBody
         */
        fun getRequestBody(str: String): RequestBody {
            return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), str)
        }


}