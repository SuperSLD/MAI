package com.raspisanie.mai.common.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.raspisanie.mai.R
import com.raspisanie.mai.common.base.BaseFragment
import org.koin.android.ext.android.inject
import com.raspisanie.mai.common.CiceroneHolder
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command

open class FlowFragment(router: String) : BaseFragment(R.layout.layout_container) {

    private val navigatorHolder: CiceroneHolder by inject()

    private val cicerone = navigatorHolder.getCicerone(router)

    private val currentFragment: BaseFragment?
        get() = childFragmentManager.findFragmentById(R.id.container) as? BaseFragment

    protected val navigator: Navigator by lazy {
        object : SupportAppNavigator(requireActivity(), childFragmentManager, R.id.container) {
            override fun setupFragmentTransaction(
                command: Command,
                currentFragment: Fragment?,
                nextFragment: Fragment?,
                fragmentTransaction: FragmentTransaction
            ) {
                fragmentTransaction.setReorderingAllowed(true)

                fragmentTransaction.setCustomAnimations(
                    R.anim.fade_in,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.fade_out
                )
            }
        }
    }

    override fun onBackPressed() {
        currentFragment?.onBackPressed() ?: super.onBackPressed()
    }

    override fun onResume() {
        super.onResume()
        cicerone?.navigatorHolder?.setNavigator(navigator)
    }

    override fun onPause() {
        cicerone?.navigatorHolder?.removeNavigator()
        super.onPause()
    }
}