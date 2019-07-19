package dan.wilkie.cv.di

import dagger.Component
import dan.wilkie.common.di.ActivityScope
import dan.wilkie.common.di.AppComponent
import dan.wilkie.cv.view.CvActivity

@ActivityScope
@Component(dependencies = [AppComponent::class], modules = [ CvModule::class])
interface CvComponent {
    fun inject(cvActivity: CvActivity)
}
