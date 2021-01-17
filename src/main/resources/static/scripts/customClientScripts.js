var currentAvgTransValue = 0;

var timeData = [];
var currentTime = new Date().getTime();
var updateInterval = 8000;
var totalTimeDatapoints = 100;
var timeChartoptions = {
    series: {lines: {show: true, fill: true, lineWidth: 1.2}},
    xaxis: {mode: "time", tickSize: [8, "minute"],
        tickFormatter: function (v, axis) {
            var date = new Date(v);
            if (date.getMinutes() % 5 == 0)
            {
                var hours = (date.getHours() < 10) ? "0" + date.getHours() : date.getHours();
                var minutes = (date.getMinutes() < 10) ? "0" + date.getMinutes() : date.getMinutes();
                var seconds = (date.getSeconds() < 10) ? "0" + date.getSeconds() : date.getSeconds();
                return hours + ":" + minutes + ":" + seconds;
            }
            else
            {
                return "";
            }
        }, axisLabel: "Time", axisLabelUseCanvas: true,
        axisLabelFontSizePixels: 12, axisLabelFontFamily: 'Verdana,Arial',
        axisLabelPadding: 10},
    yaxis: {min: 0, max: 100, tickFormatter: function (v, axis)
        {
            if (v % 10 == 0)
            {
                return v + "/min";
            }
        }
        , axisLabel: "Speed",
        axisLabelUseCanvas: true,
        axisLabelFontSizePixels: 12,
        axisLabelFontFamily: "Verdana,Arial",
        axisLabelPadding: 6
    }
    ,
    legend: {labelBoxBorderColor: "#fff"},
    grid: {backgroundColor: "#000000", tickColor: "#008040"}
};


function getData( )
{
    var tellerTrnxAmountAjaxOptions = {
        url: "/TellerAssist/avgSpeedList.action",
        type: "POST",
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        data: '{}',
        success: updateChart,
        error: OnFail
    };

    $.ajax(tellerTrnxAmountAjaxOptions);

}

function updateChart(_data) {
    timeData.shift();
    currentTime += updateInterval;

    $.each(_data, function (k, v) {
        timeData.push([currentTime, v]);
    });
    chartData = [{label: "Speed", data: timeData, color: "#00FF00"}];
    $.plot($("#summaryChartplaceholder"), chartData, timeChartoptions);
    setTimeout(getData, updateInterval);
}

function updateRequestQueueCount() {
    var requestsQueueCountAJAXOptions = {
        url: "/TellerAssist/incompleteTrnx.action",
        type: "POST",
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        data: '{}',
        success: requestQueueSuccess,
        error: OnFail
    };

    $.ajax(requestsQueueCountAJAXOptions);

}

function requestQueueSuccess(response) {
    document.getElementById('requestQueueCount').innerHTML = response + "";
    setTimeout(updateRequestQueueCount, 1000);
}

function updateTotalTrnxCount() {

    var totalTrnxCountAJAXOptions = {
        url: "/TellerAssist/totalTrnxCount.action",
        type: "POST",
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        data: '{}',
        success: successTotalTrnxcount,
        error: OnFail
    };
    $.ajax(totalTrnxCountAJAXOptions);

}
function successTotalTrnxcount(response) {
    document.getElementById('totalTranCount').innerHTML = response + "";
    setTimeout(updateTotalTrnxCount, 1000);
}


function updateBranchBelowBenchmarkCount()
{
    var belowBenchmarkCountAJAXOptions = {
        url: "/TellerAssist/belowBenchmark.action",
        type: "POST",
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        data: '{}',
        success: successBelowBenchmark,
        error: OnFail
    };
    $.ajax(belowBenchmarkCountAJAXOptions);
}
function successBelowBenchmark(response)
{
    document.getElementById("branchBelowBenchmarkCount").innerHTML = "" + response;
    setTimeout(updateBranchBelowBenchmarkCount, 1000);
}



$(document).ready(function () {

    getData();
    chartData = [{label: "Speed", data: timeData, color: "#00FF00"}];
    $.plot($("#summaryChartplaceholder"), chartData, timeChartoptions);


    registerUpdateCharts();

    function registerUpdateCharts()
    {
        updateRequestQueueCount();
        updateTotalTrnxCount();
        updateBranchBelowBenchmarkCount();
    }

});
function OnFail(result) {
    //alert('Request Failed');
}
