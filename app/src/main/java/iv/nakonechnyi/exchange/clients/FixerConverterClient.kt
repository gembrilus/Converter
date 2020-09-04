package iv.nakonechnyi.exchange.clients

import iv.nakonechnyi.exchange.service.FixerConverterService
import retrofit2.Retrofit
import javax.inject.Inject

class FixerConverterClient @Inject constructor(
    private val retrofit: Retrofit
) : ApiClient {

    override fun getService(): FixerConverterService =
        retrofit.create(FixerConverterService::class.java)

}