package iv.nakonechnyi.exchange.di

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Named

@Module
class AppModule {

    @Provides
    @Named("IO")
    fun provideDispatcher(): CoroutineDispatcher = Dispatchers.IO

}