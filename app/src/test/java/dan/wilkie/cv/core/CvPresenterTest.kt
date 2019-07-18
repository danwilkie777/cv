package dan.wilkie.cv.core

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import io.reactivex.Single.just
import io.reactivex.subjects.PublishSubject
import org.junit.Before
import org.junit.Test
import org.mockito.InOrder
import org.mockito.Mockito

class CvPresenterTest {

    private val cvService = mock<CvService>()
    private val presenter = CvPresenter(CvRepository(cvService))
    private val view = mock<CvPresenter.View>()
    private val view2 = mock<CvPresenter.View>()
    private val inOrder: InOrder = Mockito.inOrder(view, view2)
    private val cv = Cv("profile", emptyList(), emptyList(), emptyList())
    private val cv2 = Cv("profile2", emptyList(), emptyList(), emptyList())
    private val throwable: Throwable = Throwable()
    private val refreshSubject = PublishSubject.create<Unit>()

    @Before
    fun setUp() {
        whenever(view.onRefresh()).thenReturn(refreshSubject)
        whenever(view2.onRefresh()).thenReturn(refreshSubject)
    }

    @Test
    fun showsDataWhenLoadSucceeds() {
        whenever(cvService.getCv()).thenReturn(just(cv))

        presenter.attach(view)

        verify(view).showLoading(true)
        verify(view).showLoading(false)
        verify(view).showCv(cv)
    }

    @Test
    fun showsErrorWhenLoadFails() {
        whenever(cvService.getCv()).thenReturn(Single.error(throwable))

        presenter.attach(view)

        verify(view).showLoading(true)
        verify(view).showLoading(false)
        verify(view).showError()
    }

    @Test
    fun refreshesDataWhenRequested() {
        whenever(cvService.getCv()).thenReturn(just(cv), just(cv2))

        presenter.attach(view)
        refreshSubject.onNext(Unit)

        verify(cvService, times(2)).getCv()
        inOrder.verify(view).showLoading(true)
        inOrder.verify(view).showLoading(false)
        inOrder.verify(view).showCv(cv)
        inOrder.verify(view).showLoading(true)
        inOrder.verify(view).showLoading(false)
        inOrder.verify(view).showCv(cv2)
    }

    @Test
    fun showsCachedDataWhenNewViewIsAttached() {
        whenever(cvService.getCv()).thenReturn(just(cv))

        presenter.attach(view)
        presenter.detach(view)
        presenter.attach(view2)

        verify(view).showCv(cv)
        verify(view2).showCv(cv)
        verify(cvService, times(1)).getCv()
    }
}