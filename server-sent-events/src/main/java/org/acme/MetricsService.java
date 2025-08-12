package org.acme;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class MetricsService {
    @Inject
    MeterRegistry meterRegistry;

    public RuntimeMetrics getRuntimeMetrics() {
        return new RuntimeMetrics(
                getMemoryUsed(),
                getOpenFileDescriptors(),
                getProcessCpuUsage(),
                getSystemCpuUsage()
        );
    }

    double getMemoryUsed() {
        Gauge gauge = meterRegistry.find("jvm.memory.used")
                .tags("area", "nonheap", "id", "Metaspace")
                .gauge();

        if (gauge == null) {
            return 0.0;
        }

        double bytes = gauge.value() / (1024 * 1024);
        return Math.round(bytes * 100.0) / 100.0;
    }

    double getOpenFileDescriptors() {
        Gauge gauge = meterRegistry.find("process.files.open").gauge();
        if (gauge == null) {
            return 0.0;
        }
        return gauge.value();
    }

    double getProcessCpuUsage() {
        Gauge gauge = meterRegistry.find("process.cpu.usage").gauge();
        if (gauge == null) {
            return 0.0;
        }
        double cpuPercent = gauge.value() * 100;
        return Math.round(cpuPercent * 100.0) / 100.0;
    }

    double getSystemCpuUsage() {
        Gauge gauge = meterRegistry.find("system.cpu.usage").gauge();
        if (gauge == null) {
            return 0.0;
        }
        double cpuPercent = gauge.value() * 100;
        return Math.round(cpuPercent * 100.0) / 100.0;
    }
}
