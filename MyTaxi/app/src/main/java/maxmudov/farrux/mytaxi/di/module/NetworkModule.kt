package maxmudov.farrux.mytaxi.di.module

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule(var context: Context) {

    @Singleton
    @Provides
    fun provideGsonConvertorFactory(): GsonConverterFactory = GsonConverterFactory.create()



    @Singleton
    @Provides
    fun provideOkHttpClient(
//        encryptedSharedPref: EncryptedSharedPref
    ): OkHttpClient {
        val chuckInterceptor = ChuckerInterceptor.Builder(context)
            .maxContentLength(500_000L)
            .alwaysReadResponseBody(true)
            .build()

        val builder = OkHttpClient.Builder()
            .addInterceptor(chuckInterceptor)
            .addNetworkInterceptor(Interceptor { chain: Interceptor.Chain ->
                val request = chain.request().newBuilder()
//                    .addHeader("Authorization", "Bearer ${encryptedSharedPref.getToken()}")
//                    .addHeader("language", encryptedSharedPref.getLanguage())
//                    .addHeader("Content-Type", "application/json")
//                    .addHeader("Accept", "application/json")
                    .build()
                chain.proceed(request)
            })
            .build()
        return builder
    }


    @Singleton
    @Provides
    fun provideRetrofit(
        gsonGsonConverterFactory: GsonConverterFactory,
        builder: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
//            .baseUrl(getString(R.string.))
            .client(builder)
            .addConverterFactory(gsonGsonConverterFactory)
            .build()
    }


}