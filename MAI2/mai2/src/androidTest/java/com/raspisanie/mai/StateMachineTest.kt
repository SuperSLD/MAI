package com.raspisanie.mai

import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.Assert.assertEquals
import online.jutter.supersld.JTStateMachine
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.IllegalArgumentException

@RunWith(AndroidJUnit4::class)
class StateMachineTest {

    private class TestStateMachine : JTStateMachine<String>() {
        companion object {
            const val START = "start"
            const val FIRST = "first"
            const val SECOND = "second"
        }

        override fun initStateMap(): HashMap<String, List<String>> {
            return hashMapOf(
                START to listOf(FIRST),
                FIRST to listOf(SECOND, START),
                SECOND to listOf(START)
            )
        }

        override fun setStartState(): String {
            return START
        }

    }

    @Test
    fun goToState() {
        val stateMachine = TestStateMachine()
        stateMachine.goToState(TestStateMachine.FIRST)
        stateMachine.goToState(TestStateMachine.SECOND)
        stateMachine.goToState(TestStateMachine.START)

        assertEquals(TestStateMachine.START, stateMachine.getCurrentState())
    }
}