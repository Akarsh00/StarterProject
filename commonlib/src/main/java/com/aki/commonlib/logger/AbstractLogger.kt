package com.aki.commonlib.logger

import android.util.Log

abstract class AbstractLogger protected constructor(private val enabledLogs: Boolean) {

    enum class LogLevel {
        VERBOSE, DEBUG, INFO, WARNING, ERROR
    }

    protected fun enabledLogs(): Boolean {
        return enabledLogs
    }

    private fun defaultTag(): String {
        return try {
            val stackTrace =
                Throwable().stackTrace
            val className = stackTrace[3].className
            className.substring(className.lastIndexOf('.') + 1)
        } catch (e: Exception) {
            AbstractLogger::class.java.name
        }
    }

    protected abstract fun logOk(
        level: LogLevel?,
        tag: String?,
        message: String?,
        exception: Throwable?
    )

    protected abstract fun logOk(
        level: LogLevel?,
        tag: String?,
        message: String?
    )

    protected abstract fun logFail(level: LogLevel?, e: Throwable?)

    // ============================ GENERIC ============================ \\
    fun log(
        level: LogLevel,
        tag: Any,
        message: Any,
        exception: Throwable?
    ) {
        try {
            if (enabledLogs()) {
                logNative(level, tag.toString(), message.toString(), exception)
            }
            logOk(level, tag.toString(), message.toString(), exception)
        } catch (e: Exception) {
            logFail(level, e)
        }
    }

    fun log(level: LogLevel, tag: Any, message: Any) {
        try {
            if (enabledLogs()) {
                logNative(level, tag.toString(), message.toString())
            }
            logOk(level, tag.toString(), message.toString())
        } catch (e: Exception) {
            logFail(level, e)
        }
    }

    fun log(level: LogLevel, tag: Any, exception: Throwable) {
        try {
            if (enabledLogs()) {
                logNative(level, tag.toString(), exception.message, exception)
            }
            logOk(level, tag.toString(), exception.message, exception)
        } catch (e: Exception) {
            logFail(level, e)
        }
    }

    fun log(level: LogLevel, message: Any) {
        try {
            val tag = defaultTag()
            if (enabledLogs()) {
                logNative(level, tag, message.toString())
            }
            logOk(level, tag, message.toString())
        } catch (e: Exception) {
            logFail(level, e)
        }
    }

    fun log(level: LogLevel, exception: Throwable) {
        try {
            val tag = defaultTag()
            if (enabledLogs()) {
                logNative(level, tag, exception.message, exception)
            }
            logOk(level, tag, exception.message, exception)
        } catch (e: Exception) {
            logFail(level, e)
        }
    }

    fun logNative(
        level: LogLevel,
        tag: String?,
        message: String?,
        throwable: Throwable?
    ) {
        when (level) {
            LogLevel.VERBOSE -> Log.v(tag, message, throwable)
            LogLevel.DEBUG -> Log.d(tag, message, throwable)
            LogLevel.INFO -> Log.i(tag, message, throwable)
            LogLevel.WARNING -> Log.w(tag, message, throwable)
            LogLevel.ERROR -> Log.e(tag, message, throwable)
        }
    }

    fun logNative(level: LogLevel, tag: String?, message: String?) {
        when (level) {
            LogLevel.VERBOSE -> Log.v(tag, message!!)
            LogLevel.DEBUG -> Log.d(tag, message!!)
            LogLevel.INFO -> Log.i(tag, message!!)
            LogLevel.WARNING -> Log.w(tag, message!!)
            LogLevel.ERROR -> Log.e(tag, message!!)
        }
    }

}