package iv.nakonechnyi.exchange.clients

import com.squareup.moshi.Moshi
import iv.nakonechnyi.exchange.BuildConfig
import iv.nakonechnyi.exchange.service.FixerConverterService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject

class FixerConverterClient @Inject constructor(
    private val moshi: Moshi,
    private val httpClient: OkHttpClient
): ApiClient<FixerConverterService> {

    override fun getService(): FixerConverterService = Retrofit.Builder()
        .baseUrl(BuildConfig.FIXER_API_URL)
        .client(httpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
        .create(FixerConverterService::class.java)

}