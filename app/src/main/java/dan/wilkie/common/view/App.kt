package dan.wilkie.common.view

import dan.wilkie.common.di.AppComponent
import dan.wilkie.common.di.AppModule
import android.app.Application
import dan.wilkie.common.di.DaggerAppComponent

class App : Application() {
    lateinit var appComponent: AppComponent private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}
