package iv.nakonechnyi.exchange.di

import android.content.Context
import android.util.Log
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import iv.nakonechnyi.exchange.BuildConfig
import iv.nakonechnyi.exchange.clients.ApiClient
import iv.nakonechnyi.exchange.clients.CurrencyConverter
import iv.nakonechnyi.exchange.clients.FixerConverterClient
import iv.nakonechnyi.exchange.clients.intrceptors.AuthInterceptor
import iv.nakonechnyi.exchange.service.FixerConverterService
import okhttp3.OkHttpClient
import okhttp3.Protocol
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class ServicesModule {

        @Provides
        @Singleton
        fun provideMoshi(): Moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(CurrencyConverter())
            .build()

        @Provides
        @Singleton
        fun provideHTTPClient(context: Context): OkHttpClient = OkHttpClient.Builder()
            .protocols(listOf(Protocol.HTTP_1_1))
            .connectTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(AuthInterceptor())
            .addInterceptor(ChuckerInterceptor(context))
            .build()

        @Provides
        @Singleton
        fun provideRetrofit(moshi: Moshi, httpClient: OkHttpClient): Retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.FIXER_API_URL)
            .client(httpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        @Provides
        @Singleton
        @Named("fixer")
        fun provideApiClient(retrofit: Retrofit): ApiClient = FixerConverterClient(retrofit)

}