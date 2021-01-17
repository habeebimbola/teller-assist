//﻿var data = [], chartLabels = ["Cash Deposit","Check Deposit","Check Withdrawal", "Cash Withdrawal", "Transfers", "Payments"],
var totalBranchTrnxUrl = "/TellerAssist/completeBranchTrnx.action",
        branchTrnxOnQueueUrl = "/TellerAssist/incompleteBranchTrnx.action";

var chartOptions = {
    series: {
        bars: {
            show: true,
            barWidth: 0.9,
            align: "center"
        }
    },
    yaxis: {max: 20, show: true},
    xaxis: {
        mode: "categories",
        tickLength: 0,
        show: true
    },
    grid: {
        backgroundColor: {colors: ["#ffffff", "#EDF5FF"]}
    }
};

function getBranchTotalTrnx()
{
    $.ajax({
        url: totalBranchTrnxUrl,
        type: 'POST',
        contentType: 'application/json;charset=utf-8',
        dataType: 'json',
        data: '{}',
        success: updateBranchTotalTrnx,
        error: function (data) {
            //window.alert('Request Failed');
        }
    });
}

function getBranchQueueTrnx()
{
    $.ajax({
        url: branchTrnxOnQueueUrl, type: 'POST',
        contentType: 'application/json;charset=utf-8',
        dataType: 'json',
        data: '{}',
        success: updateBranchTrnxQueue,
        error: function (data) {
            //window.alert('Request Failed');
        }
    });
}


$(document).ready(function ()
{
    getBranchTotalTrnx();
    getBranchQueueTrnx();
}
);

function updateBranchTotalTrnx(data) {
    temp = [];
    $.each(data, function (k, v) {
        temp.push(["" + k + "", v]);
    });
    $.plot($('#branchTransactionPlaceholder'), [temp], chartOptions);
    setTimeout(getBranchTotalTrnx, 1000);
}

function updateBranchTrnxQueue(data)
{
    temp = [];
    $.each(data, function (k, v)
    {
        temp.push(["" + k + "", v]);
    }
    );
    $.plot($('#totalTransactionOnQueuePlaceholder'), [temp], chartOptions);
    setTimeout(getBranchQueueTrnx, 1000);
}

