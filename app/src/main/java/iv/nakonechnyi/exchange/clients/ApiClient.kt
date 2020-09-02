package iv.nakonechnyi.exchange.clients

interface ApiClient<T> {
    fun getService(): T
}