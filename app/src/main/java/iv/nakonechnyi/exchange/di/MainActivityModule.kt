package iv.nakonechnyi.exchange.di

import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import iv.nakonechnyi.exchange.MainActivity

@Module(subcomponents = [MainActivitySubComponent::class])
abstract class MainActivityModule {
    @Binds
    @IntoMap
    @ClassKey(MainActivity::class)
    abstract fun bindMainActivityFactory(factory: MainActivitySubComponent.Factory)
            : AndroidInjector.Factory<*>
}