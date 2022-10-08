package com.billcoreatech.bespeak1003.retrofit

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface RetrofitApi {
//    @Headers("Authorization: Basic dGVzdF9za19PeUwwcVo0RzFWT0xvYkI2S3d2cm9XYjJNUVlnOg==\n")
//    @GET("/v2/local/search/keyword.json")
//    fun getKeywordData(
//        @Query(value = "query", encoded = true) strAddr: String,
//        @Query("x") x: Double,
//        @Query("y") y: Double,
//        @Query("radius") radius: Int,
//        @Query("page") page: Int
//    ): Call<ResponseBean>

    companion object { // static 처럼 공유객체로 사용가능함. 모든 인스턴스가 공유하는 객체로서 동작함.
        private const val BASE_URL = "https://dapi.kakao.com" // 주소

        fun create(): RetrofitApi {
            val gson : Gson =   GsonBuilder().setLenient().create();
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(RetrofitApi::class.java)
        }

    }
}