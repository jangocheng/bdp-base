package com.platform.hbase.api;

import org.apache.hadoop.hbase.client.BufferedMutator;

/**
 * @author wlhbdp
 * @ClassName: MutatorCallback
 * @Description:  callback for hbase put delete and update
 *
 */

public interface MutatorCallback {

    /**
     * 使用mutator api to update put and delete
     * @param mutator
     * @throws Throwable
     */
    void doInMutator(BufferedMutator mutator) throws Throwable;
}