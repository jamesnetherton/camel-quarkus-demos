package org.acme;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.time.Instant;

@RegisterForReflection
public class RuntimeMetrics {
    private final double memoryUsed;
    private final double openFileDescriptors;
    private final double processCpuUsage;
    private final double systemCpuUsage;
    private final long timestamp = Instant.now().toEpochMilli();

    public RuntimeMetrics(double memoryUsed, double openFileDescriptors, double processCpuUsage, double systemCpuUsage) {
        this.memoryUsed = memoryUsed;
        this.openFileDescriptors = openFileDescriptors;
        this.processCpuUsage = processCpuUsage;
        this.systemCpuUsage = systemCpuUsage;
    }

    public double getMemoryUsed() {
        return memoryUsed;
    }

    public double getOpenFileDescriptors() {
        return openFileDescriptors;
    }

    public double getProcessCpuUsage() {
        return processCpuUsage;
    }

    public double getSystemCpuUsage() {
        return systemCpuUsage;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
