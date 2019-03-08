package com.jintoga.mvrxepoxy

import com.jintoga.mvrxepoxy.core.BaseFragment
import com.jintoga.mvrxepoxy.core.simpleController
import com.jintoga.mvrxepoxy.views.basicRow
import com.jintoga.mvrxepoxy.views.marquee

class MainFragment : BaseFragment() {

    override fun epoxyController() = simpleController {
        marquee {
            id("marquee")
            title("Welcome to MvRx")
            subtitle("Select a demo below")
        }

        basicRow {
            id("hello_world")
            title("Hello World")
            subtitle(demonstrates("Simple MvRx usage"))
            clickListener { _ -> navigateTo(R.id.action_mainFragment_to_earningFragment) }
        }

        basicRow {
            id("random_dad_joke")
            title("Random Dad Joke")
            subtitle(demonstrates("fragmentViewModel", "Network requests", "Dependency Injection"))
            clickListener { _ -> navigateTo(R.id.action_mainFragment_to_randomDadJokeFragment) }
        }
    }

    private fun demonstrates(vararg items: String) =
            arrayOf("Demonstrates:", *items).joinToString("\n\t\t• ")
}