package com.cyz.rpc.registry;

public interface RegistryKeys {
    /**
     * 本地注册中心
     */
    String LOCAL = "local";
    /**
     * Etcd注册中心
     */
    String ETCD = "etcd";
    /**
     * ZooKeeper注册中心
     */
    String ZOOKEEPER = "zookeeper";
}