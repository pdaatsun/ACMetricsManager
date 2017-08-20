'use strict';

App.controller('ACMetricsController', ['$scope', 'ACMetrics', function ($scope, ACMetrics) {
    var self = this;

    self.acMetrics = new ACMetrics();
    self.acMetricsList = [];
    self.tranDate = new Date();

    self.fetchAllACMetricss = function (tranDate) {
        self.acMetricsList = ACMetrics.queryByDate({tranDate:tranDate});
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

    self.fetchAllACMetricss(self.tranDate);

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

}]);
