'use strict';

App.controller('ACMetricsController', ['$scope', 'ACMetrics', function ($scope, ACMetrics) {
    var self = this;

    self.acMetrics = new ACMetrics();
    self.acMetricsList = [];
    self.tranDate = new Date();

    self.analystList = [];

    self.analystListtest =
    [  {
        analystID: '1',
        firstName: 'Xiaofang',

} ,  {
        analystID: '2',
        firstName: 'John',

    }];
    self.fetchAllAnalysts = function () {
        self.analystList = ACMetrics.listAnalyst();
    }

    self.fetchAllACMetricss = function () {
        var formattedtranDate = self.tranDate.toISOString().slice(0,10);
        self.acMetricsList = ACMetrics.queryByDate({tranDate:formattedtranDate});
    };

    self.createACMetrics = function () {
        self.acMetrics.$save(function () {
            self.fetchAllACMetricss(self.tranDate);
        });
    };

    self.updateACMetrics = function () {
        self.acMetrics.$update(function () {
            self.fetchAllACMetricss(self.tranDate);
        });
    };

    self.deleteACMetrics = function (identity) {
        var acMetrics = ACMetrics.get({id: identity}, function () {
            acMetrics.$delete(function () {
                console.log('Deleting acMetrics with id ', identity);
                self.fetchAllACMetricss(self.tranDate);
            });
        });
    };

    self.fetchAllAnalysts();
    self.fetchAllACMetricss();

    self.submit = function () {
        if (self.acMetrics.acmID == null) {
            console.log('Saving New ACMetrics', self.acMetrics);
            self.acMetrics.tranDate = self.tranDate;
            self.createACMetrics();
        } else {
            console.log('Upddating acMetrics with id ', self.acMetrics.acmID);
            self.acMetrics.tranDate = self.tranDate;
            self.updateACMetrics();
            console.log('ACMetrics updated with id ', self.acMetrics.acmID);
        }
        self.reset();
    };

    self.edit = function (acmID) {
        console.log('id to be edited', acmID);
        for (var i = 0; i < self.acMetricsList.length; i++) {
            if (self.acMetricsList[i].acmID === acmID) {
                self.acMetrics = angular.copy(self.acMetricsList[i]);
                break;
            }
        }
    };

    self.remove = function (acmID) {
        console.log('id to be deleted', acmID);
        if (self.acMetrics.acmID === acmID) {//If it is the one shown on screen, reset screen
            self.reset();
        }
        self.deleteACMetrics(acmID);
    };


    self.reset = function () {
        self.acMetrics = new ACMetrics();
        $scope.myForm.$setPristine(); //reset Form
    };

    self.open = function() {
        self.popup.opened = true;
    };
    self.popup = {
        opened: false
    };

    self.dateOptions = {
        dateDisabled: disabled,
        formatYear: 'yy',
        maxDate: new Date(2020, 5, 22),
        minDate: new Date(2017, 1, 1),
        startingDay: 1
    };

    // Disable weekend selection
    function disabled(data) {
        var date = data.date,
            mode = data.mode;
        return mode === 'day' && (date.getDay() === 0 || date.getDay() === 6);
    }

}]);
