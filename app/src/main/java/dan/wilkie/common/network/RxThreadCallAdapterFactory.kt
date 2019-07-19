package dan.wilkie.common.network

import io.reactivex.Scheduler
import io.reactivex.Single
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.lang.reflect.Type

class RxThreadCallAdapterFactory(
    private val ioScheduler: Scheduler,
    private val mainScheduler: Scheduler
) : CallAdapter.Factory() {

    private val rxFactory = RxJava2CallAdapterFactory.create()

    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *> {
        val rxCallAdapter = rxFactory.get(returnType, annotations, retrofit) as CallAdapter<*, Single<*>>
        return RxThreadCallAdapter(rxCallAdapter, ioScheduler, mainScheduler)
    }
    private class RxThreadCallAdapter<R>(
        private val rxCallAdapter: CallAdapter<R, Single<*>>,
        private val ioScheduler: Scheduler,
        private val mainScheduler: Scheduler
    ) : CallAdapter<R, Single<*>> {

        override fun responseType(): Type {
            return this.rxCallAdapter.responseType()
        }

        override fun adapt(call: Call<R>): Single<*> {
            return (this.rxCallAdapter.adapt(call) as Single<*>)
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler)
        }
    }
}
