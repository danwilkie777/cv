package dan.wilkie.common.di

import javax.inject.Scope
import kotlin.annotation.AnnotationRetention.RUNTIME

@Scope
@Retention(RUNTIME)
annotation class ActivityScope