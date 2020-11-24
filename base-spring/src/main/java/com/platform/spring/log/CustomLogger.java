package com.platform.spring.log;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.MessageFactory;
import org.apache.logging.log4j.spi.AbstractLogger;
import org.apache.logging.log4j.spi.ExtendedLoggerWrapper;
import org.apache.logging.log4j.util.MessageSupplier;
import org.apache.logging.log4j.util.Supplier;

/**
 * log4j2日志扩展，支持自定义日志
 * 记录用于数据分析的业务日志 使用log.business(msg);</br>
 * @author wlhbdp
 *
 */
public class CustomLogger extends ExtendedLoggerWrapper {
    private static final long serialVersionUID = 103418572168532L;
    private final ExtendedLoggerWrapper logger;

    private static final String FQCN = CustomLogger.class.getName();
    private static final Level DATA = Level.getLevel("DATA");


    private CustomLogger(final Logger logger) {
        super((AbstractLogger) logger, logger.getName(), logger.getMessageFactory());
        this.logger = this;
    }

    public static CustomLogger getLogger() {
        final Logger wrapped = LogManager.getLogger();
        return new CustomLogger(wrapped);
    }

    public static CustomLogger getLogger(final Class<?> loggerName) {
        final Logger wrapped = LogManager.getLogger(loggerName);
        return new CustomLogger(wrapped);
    }

    public static CustomLogger getLogger(final Class<?> loggerName, final MessageFactory factory) {
        final Logger wrapped = LogManager.getLogger(loggerName, factory);
        return new CustomLogger(wrapped);
    }

    public static CustomLogger getLogger(final Object value) {
        final Logger wrapped = LogManager.getLogger(value);
        return new CustomLogger(wrapped);
    }

    public static CustomLogger getLogger(final Object value, final MessageFactory factory) {
        final Logger wrapped = LogManager.getLogger(value, factory);
        return new CustomLogger(wrapped);
    }

    public static CustomLogger getLogger(final String name) {
        final Logger wrapped = LogManager.getLogger(name);
        return new CustomLogger(wrapped);
    }

    public static CustomLogger getLogger(final String name, final MessageFactory factory) {
        final Logger wrapped = LogManager.getLogger(name, factory);
        return new CustomLogger(wrapped);
    }

    public void data(final Marker marker, final Message msg) {
        logger.logIfEnabled(FQCN, DATA, marker, msg, (Throwable) null);
    }

    public void data(final Marker marker, final Message msg, final Throwable t) {
        logger.logIfEnabled(FQCN, DATA, marker, msg, t);
    }

    public void data(final Marker marker, final Object message) {
        logger.logIfEnabled(FQCN, DATA, marker, message, (Throwable) null);
    }

    public void data(final Marker marker, final Object message, final Throwable t) {
        logger.logIfEnabled(FQCN, DATA, marker, message, t);
    }

    public void data(final Marker marker, final String message) {
        logger.logIfEnabled(FQCN, DATA, marker, message, (Throwable) null);
    }

    public void data(final Marker marker, final String message, final Object... params) {
        logger.logIfEnabled(FQCN, DATA, marker, message, params);
    }

    public void data(final Marker marker, final String message, final Throwable t) {
        logger.logIfEnabled(FQCN, DATA, marker, message, t);
    }

    public void data(final Message msg) {
        logger.logIfEnabled(FQCN, DATA, null, msg, (Throwable) null);
    }

    public void data(final Message msg, final Throwable t) {
        logger.logIfEnabled(FQCN, DATA, null, msg, t);
    }

    public void data(final Object message) {
        logger.logIfEnabled(FQCN, DATA, null, message, (Throwable) null);
    }

    public void data(final Object message, final Throwable t) {
        logger.logIfEnabled(FQCN, DATA, null, message, t);
    }

    public void data(final String message) {
        logger.logIfEnabled(FQCN, DATA, null, message, (Throwable) null);
    }

    public void data(final String message, final Object... params) {
        logger.logIfEnabled(FQCN, DATA, null, message, params);
    }

    public void data(final String message, final Throwable t) {
        logger.logIfEnabled(FQCN, DATA, null, message, t);
    }

    public void data(final Supplier<?> msgSupplier) {
        logger.logIfEnabled(FQCN, DATA, null, msgSupplier, (Throwable) null);
    }

    public void data(final Supplier<?> msgSupplier, final Throwable t) {
        logger.logIfEnabled(FQCN, DATA, null, msgSupplier, t);
    }

    public void data(final Marker marker, final Supplier<?> msgSupplier) {
        logger.logIfEnabled(FQCN, DATA, marker, msgSupplier, (Throwable) null);
    }

    public void data(final Marker marker, final String message, final Supplier<?>... paramSuppliers) {
        logger.logIfEnabled(FQCN, DATA, marker, message, paramSuppliers);
    }

    public void data(final Marker marker, final Supplier<?> msgSupplier, final Throwable t) {
        logger.logIfEnabled(FQCN, DATA, marker, msgSupplier, t);
    }

    public void data(final String message, final Supplier<?>... paramSuppliers) {
        logger.logIfEnabled(FQCN, DATA, null, message, paramSuppliers);
    }

    public void data(final Marker marker, final MessageSupplier msgSupplier) {
        logger.logIfEnabled(FQCN, DATA, marker, msgSupplier, (Throwable) null);
    }

    public void data(final Marker marker, final MessageSupplier msgSupplier, final Throwable t) {
        logger.logIfEnabled(FQCN, DATA, marker, msgSupplier, t);
    }

    public void data(final MessageSupplier msgSupplier) {
        logger.logIfEnabled(FQCN, DATA, null, msgSupplier, (Throwable) null);
    }

    public void data(final MessageSupplier msgSupplier, final Throwable t) {
        logger.logIfEnabled(FQCN, DATA, null, msgSupplier, t);
    }
}