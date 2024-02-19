package com.github.bookgvi.errorhandlerplugin.listeners

import com.github.bookgvi.errorhandlerplugin.services.IdeErrorHandler
import com.github.bookgvi.errorhandlerplugin.services.PluginEnableHandler
import com.intellij.ide.AppLifecycleListener


internal class ApplicationActivationListener : AppLifecycleListener {
    override fun appFrameCreated(commandLineArgs: MutableList<String>) {
        super.appFrameCreated(commandLineArgs)
        IdeErrorHandler.getInstance().proceed()
        PluginEnableHandler.getInstance().proceed()
    }
}
