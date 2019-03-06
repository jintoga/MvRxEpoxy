package com.jintoga.mvrxepoxy.core

import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.MvRxState
import com.jintoga.mvrxepoxy.BuildConfig

abstract class MvRxViewModel<S : MvRxState>(initialState: S) : BaseMvRxViewModel<S>(initialState, debugMode = BuildConfig.DEBUG)