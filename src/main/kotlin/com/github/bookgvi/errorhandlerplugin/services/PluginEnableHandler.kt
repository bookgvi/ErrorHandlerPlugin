package com.github.bookgvi.errorhandlerplugin.services

import com.intellij.ide.plugins.IdeaPluginDescriptor
import com.intellij.ide.plugins.SberPluginStateListener
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.ui.Messages

@SuppressWarnings("UnstableApiUsage")
class PluginEnableHandler private constructor() : IHandler {

    companion object {
        fun getInstance(): PluginEnableHandler = PluginEnableHandler()
    }

    override fun proceed() {

        ApplicationManager.getApplication().messageBus.connect().subscribe(SberPluginStateListener.TOPIC,
            object : SberPluginStateListener {
                override fun disable(pluginDescriptor: IdeaPluginDescriptor) {
                    val status = "disabled"
                    val title = "PLUGIN ENABLER"
                    showMessage(pluginDescriptor, status, title)
                }

                override fun enable(pluginDescriptor: IdeaPluginDescriptor) {
                    val status = "enable"
                    val title = "PLUGIN ENABLER"
                    showMessage(pluginDescriptor, status, title)
                }

                override fun install(pluginDescriptor: IdeaPluginDescriptor) {
                    val status = "install"
                    val title = "PLUGIN INSTALLER"
                    showMessage(pluginDescriptor, status, title)
                }

                override fun uninstall(pluginDescriptor: IdeaPluginDescriptor) {
                    val status = "uninstall"
                    val title = "PLUGIN INSTALLER"
                    showMessage(pluginDescriptor, status, title)
                 }
            }
        )
    }

    private fun showMessage(pluginDescriptor : IdeaPluginDescriptor, status : String, title : String) {
        val strBuilder = StringBuilder()
        strBuilder.append(pluginDescriptor.pluginId)
                .append(", ")
        strBuilder.delete(strBuilder.length - 2, strBuilder.length - 1)
        strBuilder.append(status)
        ApplicationManager.getApplication().invokeLater { Messages.showInfoMessage(strBuilder.toString(), title) }
    }
}