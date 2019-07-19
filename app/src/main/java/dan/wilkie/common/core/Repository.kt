package dan.wilkie.common.core

import android.util.Log
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

open class Repository<T>(requestFunction: () -> Single<T>) {
    private val refreshes = BehaviorSubject.create<Unit>()
    private val errors = PublishSubject.create<Throwable>()
    private val loading = BehaviorSubject.create<Boolean>()
    private val data: Observable<T> = refreshes
        .startWith(Unit)
        .flatMapMaybe {
            requestFunction()
                .doOnSubscribe { loading.onNext(true) }
                .doOnSuccess { loading.onNext(false) }
                .doOnError { loading.onNext(false) }
                .doOnError { errors.onNext(it) }
                .doOnError { Log.e("Repository", "Request failed", it) }
                .toMaybe()
                .onErrorComplete()
        }
        .cache()

    fun refresh() {
        refreshes.onNext(Unit)
    }

    fun observeData() = data

    fun observeErrors(): Observable<Throwable> = errors.hide()

    fun observeLoading(): Observable<Boolean> = loading.hide()
}