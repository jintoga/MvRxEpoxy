package com.jintoga.mvrxepoxy.feature

import com.airbnb.mvrx.*
import com.jintoga.mvrxepoxy.core.BaseFragment
import com.jintoga.mvrxepoxy.core.MvRxViewModel
import com.jintoga.mvrxepoxy.core.simpleController
import com.jintoga.mvrxepoxy.model.EarningTime
import com.jintoga.mvrxepoxy.network.EarningService
import com.jintoga.mvrxepoxy.views.basicRow
import com.jintoga.mvrxepoxy.views.loadingRow
import com.jintoga.mvrxepoxy.views.marquee
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject


data class EarningState(val earnings: Async<List<EarningTime>> = Uninitialized) : MvRxState

class EarningViewModel(
        initialState: EarningState,
        private val earningService: EarningService
) : MvRxViewModel<EarningState>(initialState) {
    init {
        fetchEarning()
    }

    fun fetchEarning() {
        earningService.loadEarnings("20190215").subscribeOn(Schedulers.io()).execute { copy(earnings = it) }
    }

    companion object : MvRxViewModelFactory<EarningViewModel, EarningState> {

        override fun create(viewModelContext: ViewModelContext, state: EarningState): EarningViewModel {
            val service: EarningService by viewModelContext.activity.inject()
            return EarningViewModel(state, service)
        }
    }
}

class EarningFragment : BaseFragment() {
    private val viewModel: EarningViewModel by fragmentViewModel()

    override fun epoxyController() = simpleController(viewModel) { state ->
        marquee {
            id("marquee")
            title("Earnings")
        }

        /**
         * Async overrides the invoke operator so we can just call it. It will return the value if
         * it is Success or null otherwise.
         */
        val earnings = state.earnings()
        if (earnings == null) {
            loadingRow {
                id("loading")
            }
            return@simpleController
        }

        earnings.forEach {
            basicRow {
                id("earning")
                title(it.ticker)
                subtitle(it.earningTime)
                clickListener { _ -> viewModel.fetchEarning() }
            }
        }
    }
}