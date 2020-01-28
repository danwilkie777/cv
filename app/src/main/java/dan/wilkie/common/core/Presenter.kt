package dan.wilkie.common.core

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer


abstract class Presenter<V : Presenter.View> {
    private var view: V? = null
    private val compositeDisposable = CompositeDisposable()

    fun attach(view: V) {
        this.validateAttach(view)
        this.view = view
        this.onAttach(view)
    }

    fun detach(view: V) {
        this.validateDetach(view)
        this.view = null
        this.compositeDisposable.clear()
        this.onDetach(view)
    }

    protected open fun onAttach(view: V) {}

    protected open fun onDetach(view: V) {}

    private fun untilDetach(disposable: Disposable) {
        this.compositeDisposable.add(disposable)
    }

    protected fun <T> Observable<T>.subscribeUntilDetach(action: (T) -> Unit) =
        untilDetach(this.subscribe(action))

    private fun validateAttach(view: V) {
        if (this.view != null) {
            throw IllegalStateException("Tried to attach " + view + " when " + this.view + " is already attached.")
        }
    }

    private fun validateDetach(view: V) {
        if (this.view !== view) {
            throw IllegalStateException("Tried to detach " + view + "when currently attached view is " + this.view)
        }
    }

    interface View
}