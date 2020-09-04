package iv.nakonechnyi.exchange

import iv.nakonechnyi.exchange.model.ConvertOperation
import iv.nakonechnyi.exchange.model.Currency
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

internal fun generateConvertOperation() = ConvertOperation(
    amount = Random.nextInt(0, 100),
    from = Currency.UAH,
    to = Currency.USD,
    result = Random.nextDouble(0.0, 100.0)
)

internal fun generateListOfConvertOperation() : List<ConvertOperation> = mutableSetOf<ConvertOperation>().apply {
    repeat(Random.nextInt(3, 10)) {
        ::generateConvertOperation andThan this::add
    }
}.toList()


internal fun <T> repeatProcedure(list: List<T>, test: suspend (T) -> Unit) = list.forEach{
    runBlocking {
        test(it)
    }
}

private inline infix fun <T, U> (()-> T).andThan(other: (T) -> U) = other(invoke())
