package iv.nakonechnyi.exchange.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import iv.nakonechnyi.exchange.clients.ApiClient
import iv.nakonechnyi.exchange.clients.FixerConverterClient
import iv.nakonechnyi.exchange.service.ConverterService
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
abstract class ServicesModule {

    companion object {

        @Provides
        @Singleton
        fun provideMoshi(): Moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        @Provides
        @Singleton
        fun provideHTTPClient(): OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .build()

    }

    @Binds
    @Singleton
    abstract fun bindApiClient(client: FixerConverterClient): ApiClient<out ConverterService>
}