package iv.nakonechnyi.exchange.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import iv.nakonechnyi.exchange.ConverterApp

@Component(
    modules = [
        AndroidInjectionModule::class,
        MainActivityModule::class,
        ServicesModule::class,
        DatabaseModule::class
    ]
)
interface ConverterAppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ConverterAppComponent
    }

    fun inject(application: ConverterApp)
}