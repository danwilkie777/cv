package dan.wilkie.common.core

import io.reactivex.Single.error
import io.reactivex.Single.just
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Test

class RepositoryTest {
    private var requests = 0
    private var result = "result"
    private var throwable = Throwable()

    @Test
    fun loadsData() {
        val repository = Repository<String> {
            requests++
            just(result)
        }
        val dataSubscriber = repository.observeData().test()

        dataSubscriber.assertValues("result")
        assertThat(requests, equalTo(1))
    }

    @Test
    fun cachesData() {
        val repository = Repository<String> {
            requests++
            just(result)
        }
        val dataSubscriber1 = repository.observeData().test()
        val dataSubscriber2 = repository.observeData().test()

        dataSubscriber1.assertValues("result")
        dataSubscriber2.assertValues("result")
        assertThat(requests, equalTo(1))
    }

    @Test
    fun refreshesData() {
        val repository = Repository<String> {
            requests++
            just(requests.toString())
        }
        val dataSubscriber = repository.observeData().test()

        repository.refresh()

        dataSubscriber.assertValues("1", "2")
        assertThat(requests, equalTo(2))
    }

    @Test
    fun emitsErrors() {
        val repository = Repository<String> {
            requests++
            error(throwable)
        }
        val errorSubscriber = repository.observeErrors().test()
        repository.observeData().test()

        errorSubscriber.assertValue(throwable)
        assertThat(requests, equalTo(1))
    }

    @Test
    fun recoversFromErrors() {
        val repository = Repository<String> {
            requests++
            if (requests > 1) {
                just("result")
            } else {
                error(throwable)
            }
        }
        val errorSubscriber = repository.observeErrors().test()
        val dataSubscriber = repository.observeData().test()

        repository.refresh()

        errorSubscriber.assertValue(throwable)
        dataSubscriber.assertValue("result")
        assertThat(requests, equalTo(2))
    }

    @Test
    fun emitsLoadingUntilDataArrives() {
        val repository = Repository<String> {
            requests++
            just(result)
        }
        val loadingSubscriber = repository.observeLoading().test()

        repository.observeData().test()

        loadingSubscriber.assertValues(true, false)
    }

    @Test
    fun emitsLoadingUntilErrorOccurs() {
        val repository = Repository<String> {
            requests++
            error(throwable)
        }
        val loadingSubscriber = repository.observeLoading().test()

        repository.observeData().test()

        loadingSubscriber.assertValues(true, false)
    }

    @Test
    fun emitsLoadingOnRefresh() {
        val repository = Repository<String> {
            requests++
            just(result)
        }
        val loadingSubscriber = repository.observeLoading().test()

        repository.observeData().test()
        repository.refresh()

        loadingSubscriber.assertValues(true, false, true, false)
    }
}