package iv.nakonechnyi.exchange.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import iv.nakonechnyi.exchange.ConvertViewModelFactory
import iv.nakonechnyi.exchange.ConverterViewModel
import iv.nakonechnyi.exchange.annotations.ViewModelKey

@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindsModelFactory(factory: ConvertViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ConverterViewModel::class)
    abstract fun bindTestViewModel(viewModel: ConverterViewModel): ViewModel
}