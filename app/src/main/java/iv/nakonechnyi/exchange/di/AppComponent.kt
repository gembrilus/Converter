package iv.nakonechnyi.exchange.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import iv.nakonechnyi.exchange.ConverterApp
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        MainActivityModule::class,
        AppModule::class,
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