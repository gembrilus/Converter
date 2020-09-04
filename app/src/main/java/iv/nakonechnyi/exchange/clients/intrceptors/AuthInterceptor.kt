package iv.nakonechnyi.exchange.clients.intrceptors

import iv.nakonechnyi.exchange.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

private const val ACCESS_FIXER_KEY = "api_key"

class AuthInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain.request().newBuilder().url(
                chain.request().url.newBuilder()
                    .addQueryParameter(ACCESS_FIXER_KEY, BuildConfig.ACCESS_KEY)
                    .build()
            ).build()
        )
    }
}