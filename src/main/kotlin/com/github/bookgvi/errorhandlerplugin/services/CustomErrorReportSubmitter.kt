package com.github.bookgvi.errorhandlerplugin.services

import com.github.bookgvi.errorhandlerplugin.ErrorHandlerBundle
import com.intellij.ide.DataManager
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.diagnostic.ErrorReportSubmitter
import com.intellij.openapi.diagnostic.IdeaLoggingEvent
import com.intellij.openapi.diagnostic.SubmittedReportInfo
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.Task
import com.intellij.openapi.ui.Messages
import com.intellij.util.Consumer
import java.awt.Component

class CustomErrorReportSubmitter : ErrorReportSubmitter() {

    override fun getReportActionText(): String {
        return ErrorHandlerBundle.message("error.handler.submit.button.text")
    }

    override fun submit(events: Array<out IdeaLoggingEvent>, additionalInfo: String?, parentComponent: Component, consumer: Consumer<in SubmittedReportInfo>): Boolean {
        val dataManager = DataManager.getInstance()
        val ctx = dataManager.getDataContext(parentComponent)
        val project = CommonDataKeys.PROJECT.getData(ctx)

        object : Task.Backgroundable(project, ErrorHandlerBundle.message("error.handler.submit.task.name")) {
            override fun run(progressIndicator: ProgressIndicator) {
                for (event in events) {
                    val stackTrace = event.throwableText
                    ApplicationManager.getApplication().invokeLater { Messages.showInfoMessage(stackTrace, ErrorHandlerBundle.message("error.handler.window.title.text")) }
                    val status : SubmittedReportInfo = SubmittedReportInfo(SubmittedReportInfo.SubmissionStatus.NEW_ISSUE)
                    consumer.consume(status)
                }
            }

        }
        return false
    }
}