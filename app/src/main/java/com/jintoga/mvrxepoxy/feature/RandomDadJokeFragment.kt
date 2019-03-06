package com.jintoga.mvrxepoxy.feature

import com.airbnb.mvrx.*
import com.jintoga.mvrxepoxy.core.BaseFragment
import com.jintoga.mvrxepoxy.core.MvRxViewModel
import com.jintoga.mvrxepoxy.core.simpleController
import com.jintoga.mvrxepoxy.model.Joke
import com.jintoga.mvrxepoxy.network.DadJokeService
import com.jintoga.mvrxepoxy.views.basicRow
import com.jintoga.mvrxepoxy.views.loadingRow
import com.jintoga.mvrxepoxy.views.marquee
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject

data class RandomDadJokeState(val joke: Async<Joke> = Uninitialized) : MvRxState

class RandomDadJokeViewModel(
        initialState: RandomDadJokeState,
        private val dadJokeService: DadJokeService
) : MvRxViewModel<RandomDadJokeState>(initialState) {
    init {
        fetchRandomJoke()
    }

    fun fetchRandomJoke() {
        dadJokeService.random().subscribeOn(Schedulers.io()).execute { copy(joke = it) }
    }

    companion object : MvRxViewModelFactory<RandomDadJokeViewModel, RandomDadJokeState> {

        override fun create(viewModelContext: ViewModelContext, state: RandomDadJokeState): RandomDadJokeViewModel {
            val service: DadJokeService by viewModelContext.activity.inject()
            return RandomDadJokeViewModel(state, service)
        }
    }
}

class RandomDadJokeFragment : BaseFragment() {
    private val viewModel: RandomDadJokeViewModel by fragmentViewModel()

    override fun epoxyController() = simpleController(viewModel) { state ->
        marquee {
            id("marquee")
            title("Dad Joke")
        }

        /**
         * Async overrides the invoke operator so we can just call it. It will return the value if
         * it is Success or null otherwise.
         */
        val joke = state.joke()
        if (joke == null) {
            loadingRow {
                id("loading")
            }
            return@simpleController
        }

        basicRow {
            id("joke")
            title(joke.joke)
            clickListener { _ -> viewModel.fetchRandomJoke() }
        }
    }
}