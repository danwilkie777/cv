package dan.wilkie.common.core

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Test


class PresenterTest {

    private val view = TestView("1")
    private val view2 = TestView("2")
    private val presenter = TestPresenter()

    @Test
    fun unsubscribesWhenViewIsDetached() {
        presenter.attach(view)

        view.enterString()
        presenter.detach(view)
        view.enterString()

        assertThat(presenter.stringsEntered, equalTo(listOf("1")))
    }

    @Test
    fun resubscribesWhenNewViewIsAttached() {
        presenter.attach(view)

        view.enterString()
        presenter.detach(view)
        view.enterString()
        presenter.attach(view2)
        view2.enterString()

        assertThat(presenter.stringsEntered, equalTo(listOf("1", "2")))
    }

    @Test(expected = IllegalStateException::class)
    fun cannotAttachViewWhenItIsAlreadyAttached() {
        presenter.attach(view)

        presenter.attach(view)
    }

    @Test(expected = IllegalStateException::class)
    fun cannotAttachViewWhenDifferentViewIsAlreadyAttached() {
        presenter.attach(view)

        presenter.attach(view2)
    }

    @Test(expected = IllegalStateException::class)
    fun cannotDetachViewWhenNoneIsAttached() {
        presenter.detach(view)
    }

    @Test(expected = IllegalStateException::class)
    fun cannotDetachViewWhenDifferentViewIsAttached() {
        presenter.attach(view)

        presenter.detach(view2)
    }

    private class TestPresenter : Presenter<TestPresenter.View>() {
        val stringsEntered = ArrayList<String>()

        override fun onAttach(view: View) {
            super.onAttach(view)
            view.onStringEntered().subscribeUntilDetach {
                stringsEntered.add(it)
            }
        }

        internal interface View : Presenter.View {
            fun onStringEntered(): Observable<String>
        }
    }

    private class TestView constructor(private val stringToEnter: String) : TestPresenter.View {
        internal var stringEntries = PublishSubject.create<String>()

        override fun onStringEntered(): Observable<String> {
            return stringEntries
        }

        fun enterString() {
            stringEntries.onNext(stringToEnter)
        }
    }
}