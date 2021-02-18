package com.raspisanie.mai.common

import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import java.util.HashMap

class CiceroneHolder {
    private var containers: HashMap<String, Cicerone<Router>> = HashMap()

    var currentRouter: Router? = null
    var currentRouterTag: String? = null

    fun getCicerone(containerTag: String): Cicerone<Router>? {
        if (!containers.containsKey(containerTag)) {
            containers[containerTag] = Cicerone.create()
        }

        containers[containerTag]?.let {
            currentRouter = it.router
            currentRouterTag = containerTag
        }

        return containers[containerTag]
    }
}