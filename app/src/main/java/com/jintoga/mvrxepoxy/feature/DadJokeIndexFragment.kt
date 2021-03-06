package com.jintoga.mvrxepoxy.feature

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.airbnb.mvrx.Fail
import com.airbnb.mvrx.fragmentViewModel
import com.jintoga.mvrxepoxy.core.BaseFragment
import com.jintoga.mvrxepoxy.core.simpleController
import com.jintoga.mvrxepoxy.views.basicRow
import com.jintoga.mvrxepoxy.views.loadingRow
import com.jintoga.mvrxepoxy.views.marquee
import com.jintoga.mvrxepoxy.views.retryRow


class DadJokeIndexFragment : BaseFragment() {

    /**
     * This will get or create a new ViewModel scoped to this Fragment. It will also automatically
     * subscribe to all state changes and call [invalidate] which we have wired up to
     * call [buildModels] in [BaseFragment].
     */
    private val viewModel: DadJokeIndexViewModel by fragmentViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        /**
         * Use viewModel.subscribe to listen for changes. The parameter is a shouldUpdate
         * function that is given the old state and new state and returns whether or not to
         * call the subscriber. onSuccess, onFail, and propertyWhitelist ship with MvRx.
         */
        viewModel.asyncSubscribe(DadJokeIndexState::request, onFail = { error ->
            Snackbar.make(coordinatorLayout, error.localizedMessage, Snackbar.LENGTH_SHORT)
                    .show()
        })

        val spanCount = 2
        val layoutManager = GridLayoutManager(context, spanCount)
        epoxyController.spanCount = spanCount
        layoutManager.spanSizeLookup = epoxyController.spanSizeLookup
        recyclerView.layoutManager = layoutManager
    }

    override fun epoxyController() = simpleController(viewModel) { state ->

        marquee {
            id("marquee")
            title("Dad Jokes")
        }
        state.jokes.forEach { joke ->
            basicRow {
                id(joke.id)
                title(joke.joke)
                clickListener { _ ->
                    /*navigateTo(
                            R.id.action_dadJokeIndex_to_dadJokeDetailFragment,
                            DadJokeDetailArgs(joke.id)
                    )*/
                }
                spanSizeOverride { totalSpanCount, _, _ ->
                    totalSpanCount / 2
                }
            }
        }

        if (state.request is Fail) {
            retryRow {
                id("retry")
                clickListener { _ ->
                    viewModel.fetchNextPage()
                }
            }
        } else {
            loadingRow {
                // Changing the ID will force it to rebind when new data is loaded even if it is
                // still on screen which will ensure that we trigger loading again.
                id("loading${state.jokes.size}")
                onBind { _, _, _ -> viewModel.fetchNextPage() }
            }
        }

    }
}