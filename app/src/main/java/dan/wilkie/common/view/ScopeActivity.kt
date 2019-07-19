package dan.wilkie.common.view

import dan.wilkie.common.di.AppComponent
import androidx.appcompat.app.AppCompatActivity

abstract class ScopeActivity<T> : AppCompatActivity() {
    val component : T by lazy {
        if (lastCustomNonConfigurationInstance != null) {
            lastCustomNonConfigurationInstance as T
        } else {
            buildComponent((applicationContext as App).appComponent)
        }
    }

    protected abstract fun buildComponent(appComponent: AppComponent): T

    override fun onRetainCustomNonConfigurationInstance() = component
}
