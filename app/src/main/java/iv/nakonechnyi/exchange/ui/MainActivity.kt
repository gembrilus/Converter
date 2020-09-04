package iv.nakonechnyi.exchange.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import dagger.android.AndroidInjection
import iv.nakonechnyi.exchange.R
import iv.nakonechnyi.exchange.ui.fragments.HistoryFragment
import iv.nakonechnyi.exchange.ui.fragments.MainFragment
import iv.nakonechnyi.exchange.ui.viewmodels.ConverterViewModel
import iv.nakonechnyi.exchange.utils.ResultWrapper.Error
import iv.nakonechnyi.exchange.utils.showError
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

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

        model.error.observe(this, {
            when(it){
                is Error -> showError(it.value){message ->
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }
}