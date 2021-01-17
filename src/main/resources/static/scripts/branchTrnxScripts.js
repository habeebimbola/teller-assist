
function branchTrnxCount() {
    var branchTrnxCountAJAXOptions = {
        url: "/TellerAssist/branchTrnx.action",
        type: "POST",
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        data: '{}',
        success: branchTrnxCountSuccess,
        error: OnFail
    };

    $.ajax(branchTrnxCountAJAXOptions);

}

function branchTrnxCountSuccess(response) {
    document.getElementById('branchTranCount').innerHTML = response + "";
    setTimeout(branchTrnxCount, 1000);
}

function requestQueueCount() {

    var requestQueueCountAJAXOptions = {
        url: "/TellerAssist/requestQueue.action",
        type: "POST",
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        data: '{}',
        success: requestQueueCountSuccess,
        error: OnFail
    };
    $.ajax(requestQueueCountAJAXOptions);

}
function requestQueueCountSuccess(response) {
    document.getElementById('branchRequestOnQueueCount').innerHTML = response + "";
    setTimeout(requestQueueCount, 1000);
}


function updateBranchAboveBenchmarkCount()
{
    var aboveBenchmarkCountAJAXOptions = {
        url: "/TellerAssist/aboveBenchmark.action",
        type: "POST",
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        data: '{}',
        success: successAboveBenchmark,
        error: OnFail
    };
    $.ajax(aboveBenchmarkCountAJAXOptions);
}
function successAboveBenchmark(response)
{
    document.getElementById("noOfTellerAboveBankBenchmark").innerHTML = "" + response;
    setTimeout(updateBranchAboveBenchmarkCount, 1000);
}



$(document).ready(function () {
    registerUpdateCharts();

    function registerUpdateCharts()
    {
        branchTrnxCount();
        requestQueueCount();
        updateBranchAboveBenchmarkCount();
    }

});
function OnFail(result) {
    //alert('Request Failed');
}
