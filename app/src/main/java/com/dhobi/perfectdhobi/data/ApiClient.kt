package com.dhobi.perfectdhobi.data

import com.dhobi.perfectdhobi.utils.Shared_Preferences
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    const val BASE_URL = "http://3.108.121.124:8888/api/"  //Dev
    private var retrofit: Retrofit? = null
    val retrofitInstance: Retrofit?
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }


    private fun getRetrofit(): Retrofit {
        val httpClient = OkHttpClient.Builder()
        //Timeout
        httpClient.readTimeout(ApiConfig.READ_TIMEOUT, TimeUnit.SECONDS)
        httpClient.connectTimeout(ApiConfig.CONNECT_TIMEOUT, TimeUnit.SECONDS)
        httpClient.writeTimeout(ApiConfig.WRITE_TIMEOUT, TimeUnit.SECONDS)


        val interceptorbody = HttpLoggingInterceptor()
        interceptorbody.setLevel(HttpLoggingInterceptor.Level.BODY)

        val interceptor =
            Interceptor { chain ->
                val request = chain.request()
                    .newBuilder()
                    .addHeader("Authorization", "Bearer " + Shared_Preferences.getToken()!!)
                    .addHeader("x-requested-with", "XMLHttpRequest")
                    .build()
                // }
                return@Interceptor chain.proceed(request)
            }

        httpClient.addInterceptor(interceptor)
        httpClient.addInterceptor(interceptorbody)
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build() //Doesn't require the adapter
    }





    val apiService: ApiInterface = getRetrofit().create(ApiInterface::class.java)

}