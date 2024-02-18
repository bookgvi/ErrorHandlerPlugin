package com.github.bookgvi.errorhandlerplugin.services

import com.github.bookgvi.errorhandlerplugin.ErrorHandlerBundle
import com.intellij.diagnostic.MessagePool
import com.intellij.diagnostic.MessagePoolListener
import com.intellij.ide.AppLifecycleListener
import com.intellij.ide.plugins.PluginUtil
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.ui.Messages
import com.jetbrains.rd.util.getThrowableText

class IdeErrorHandler : AppLifecycleListener {

    private val ERROR_TITLE : String = ErrorHandlerBundle.message("error.handler.window.title.text")
    override fun appFrameCreated(commandLineArgs: MutableList<String>) {
        super.appFrameCreated(commandLineArgs)
        MessagePool.getInstance().addListener(
                object : MessagePoolListener {
                    override fun newEntryAdded() {
                        val msgPool = MessagePool.getInstance().getFatalErrors(false, true)
                        for (msg in msgPool) {
                            val t = msg.throwable
                            val pluginId = PluginUtil.getInstance().findPluginId(t)
                            if (pluginId != null) {
                                val errorMsg = String.format("Error occured %s:\n %s", pluginId.idString, t.getThrowableText())
                                println(errorMsg)
                                ApplicationManager.getApplication().invokeLater { Messages.showInfoMessage(errorMsg, ERROR_TITLE) }
                                msg.isRead = true;
                            }
                        }
                    }

                    override fun entryWasRead() {
                        val msg = "Error has been read"
                        println(msg)
                        ApplicationManager.getApplication().invokeLater { Messages.showInfoMessage(msg, ERROR_TITLE) }
                    }
                }
        )
    }
}