package dan.wilkie.common

import android.os.AsyncTask
import androidx.test.runner.AndroidJUnitRunner
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers

class RxEspressoRunner: AndroidJUnitRunner() {
    override fun onStart() {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.from(AsyncTask.THREAD_POOL_EXECUTOR) }
        super.onStart()
    }

    override fun onDestroy() {
        super.onDestroy()
        RxJavaPlugins.reset()
    }
}