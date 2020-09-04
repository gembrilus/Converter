package iv.nakonechnyi.exchange.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import iv.nakonechnyi.exchange.R
import iv.nakonechnyi.exchange.ui.fragments.HistoryFragment
import iv.nakonechnyi.exchange.ui.fragments.MainFragment
import iv.nakonechnyi.exchange.ui.viewmodels.ConverterViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    private val model by lazy {
        ViewModelProvider(this, viewModelFactory).get(ConverterViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.findFragmentById(R.id.host)
            ?: supportFragmentManager.beginTransaction()
                .add(R.id.host, MainFragment.newInstance())
                .commit()

        model.openHistory.observe(this, { isTrue ->
            if (isTrue) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.host, HistoryFragment.newInstance())
                    .addToBackStack(null)
                    .commit()
            }
        })
    }

}