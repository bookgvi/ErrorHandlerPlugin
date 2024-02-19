package com.github.bookgvi.errorhandlerplugin.services

import com.intellij.ide.plugins.DynamicPluginEnabler
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.ui.Messages

@SuppressWarnings("UnstableApiUsage")
class PluginEnableHandler private constructor() : IHandler {

    companion object {
        fun getInstance() : PluginEnableHandler = PluginEnableHandler()
    }

    override fun proceed() {
        DynamicPluginEnabler.addPluginStateChangedListener { pluginDescriptors, enable ->
            val strBuilder = StringBuilder()
            for (descriptor in pluginDescriptors) {
                strBuilder.append(descriptor.pluginId.idString)
                        .append(", ")
            }
            strBuilder.delete(strBuilder.length - 2, strBuilder.length - 1)
            var status = "disabled"
            if (enable) {
                status = "enabled"
            }
            strBuilder.append(" - ").append(status)
            ApplicationManager.getApplication().invokeLater { Messages.showInfoMessage(strBuilder.toString(), "PLUGIN ENABLER") }
        }
    }
}