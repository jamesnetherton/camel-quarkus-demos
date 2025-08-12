function createChart(elementId, title, yAxisTitle, color, borderColor) {
    const data = {
      labels: [],
      datasets: [{
        label: title,
        data: [],
        backgroundColor: [
          color,
          "rgba(54, 162, 235, 0.2)",
          "rgba(255, 206, 86, 0.2)",
          "rgba(75, 192, 192, 0.2)",
          "rgba(153, 102, 255, 0.2)",
          "rgba(255, 159, 64, 0.2)"
        ],
        borderColor: [
            borderColor,
            "rgba(54, 162, 235, 1)",
            "rgba(255, 206, 86, 1)",
            "rgba(75, 192, 192, 1)",
            "rgba(153, 102, 255, 1)",
            "rgba(255, 159, 64, 1)"
        ],
        borderWidth: 1,
        fill: 'start'
      }]
    };

    const config = {
      type: 'line',
      data: data,
      options: {
        plugins: {
          title: {
            text: title,
            display: true
          }
        },
        scales: {
          x: {
            type: 'time',
            time: {
              tooltipFormat: 'DD T'
            },
            title: {
              display: true,
              text: 'Time'
            }
          },
          y: {
            title: {
              display: true,
              text: yAxisTitle
            }
          }
        },
      },
    };

    return new Chart(
        document.getElementById(elementId),
        config
    );
}

const memoryFreeChart = createChart('memory-used', 'JVM Metaspace Used (MB)', 'MB', 'rgba(153, 204, 255, 0.2)', 'rgba(153, 204, 255, 1)');
const fileDescriptorsChart = createChart('file-descriptors', 'Open File Descriptors', 'Open File Descriptors', 'rgba(255, 99, 132, 0.2)', 'rgba(255, 99, 132, 1)');
const processCpuUsageChart = createChart('process-cpu-usage', 'Process CPU %', 'CPU %', 'rgb(204, 255, 204, 0.2)', 'rgb(204, 255, 204, 1)');
const systemCpuUsageChart = createChart('system-cpu-usage', 'System CPU %', 'CPU %', 'rgb(255, 204, 255, 0.2)', 'rgb(255, 204, 255, 1)');

const eventSource = new EventSource("/metrics");
eventSource.onmessage = (event) => {
    const message = JSON.parse(event.data)
    const date = new Date(message.timestamp);

    memoryFreeChart.data.datasets[0].data.push({y: message.memoryUsed, x: date});
    memoryFreeChart.update();

    fileDescriptorsChart.data.datasets[0].data.push({y: message.openFileDescriptors, x: date});
    fileDescriptorsChart.update();

    processCpuUsageChart.data.datasets[0].data.push({y: message.processCpuUsage, x: date});
    processCpuUsageChart.update();

    systemCpuUsageChart.data.datasets[0].data.push({y: message.systemCpuUsage, x: date});
    systemCpuUsageChart.update();
};
