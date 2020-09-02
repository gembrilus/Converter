package iv.nakonechnyi.exchange.di

import dagger.Subcomponent
import dagger.android.AndroidInjector
import iv.nakonechnyi.exchange.MainActivity

@Subcomponent(modules = [ViewModelFactoryModule::class])
interface MainActivitySubComponent : AndroidInjector<MainActivity>{
    @Subcomponent.Factory
    interface Factory : AndroidInjector.Factory<MainActivity>
}