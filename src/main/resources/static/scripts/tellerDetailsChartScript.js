
//var chartValues = [];
//var data = [], chartLabels = ["Deposit", "Withdrawal", "Cheque", "Transfers", chartValues[0], "Payments"];
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

function getTellerTrnxAmount() {
    $.ajax({url: "/TellerAssist/tellerTrnxAmount.action",
        type: "POST",
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        data: '{}',
        success: updateTransactionAmountChart});
}
function getTellerTrnxCount() {

    $.ajax({url: "/TellerAssist/trnxCount.action",
        type: "POST",
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        data: '{}',
        success: updateTellerTrnxCountChart});
}
function getTellerAvgSpeed() {

    $.ajax({url: "/TellerAssist/avgSpeed.action",
        type: "POST",
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        data: '{}',
        success: updateAvgTellerSpeedChart});
}

$(document).ready(function () {
    getTellerTrnxAmount();
    getTellerTrnxCount();
    getTellerAvgSpeed();
});


function updateTransactionAmountChart(response) {
    temp = [];
    $.each(response, function (k, v) {
        temp.push(["" + k + "", v]);
    }
    );
    $.plot($('#tellerAmountPlaceholder'), [temp], chartOptions);
    setTimeout(getTellerTrnxAmount, 3000);
}


function updateTellerTrnxCountChart(response) {
    temp = [];
    $.each(response, function (k, v) {
        temp.push(["" + k + "", v]);
    }
    );
    $.plot($('#totalTellerTransCountPlaceholder'), [temp], chartOptions);
    setTimeout(getTellerTrnxCount, 3000);
}


function updateAvgTellerSpeedChart(response) {
    temp = [];
    $.each(response, function (k, v) {
        temp.push(["" + k + "", v]);
    }
    );
    $.plot($('#tellerAvgSpeedPlaceholder'), [temp], chartOptions);
    setTimeout(getTellerAvgSpeed, 3000);
}
