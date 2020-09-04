package iv.nakonechnyi.exchange.clients

import iv.nakonechnyi.exchange.service.FixerConverterService

interface ApiClient {
    fun getService(): FixerConverterService
}