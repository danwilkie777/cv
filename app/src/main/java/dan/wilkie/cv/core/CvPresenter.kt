package dan.wilkie.cv.core

import dan.wilkie.common.core.Presenter
import io.reactivex.Observable

class CvPresenter(private val cvRepository: CvRepository) : Presenter<CvPresenter.View>() {

    override fun onAttach(view: View) {
        view.onRefresh().subscribeUntilDetach { cvRepository.refresh() }
        cvRepository.observeLoading().subscribeUntilDetach { view.showLoading(it) }
        cvRepository.observeErrors().subscribeUntilDetach { view.showError() }
        cvRepository.observeData().subscribeUntilDetach { view.showCv(it) }
    }

    interface View : Presenter.View {
        fun onRefresh(): Observable<Unit>
        fun showLoading(show: Boolean)
        fun showCv(cv: Cv)
        fun showError()
    }

}
