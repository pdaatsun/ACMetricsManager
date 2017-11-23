'use strict';

myapp.controller('HomeController', ['$scope', '$mdToast', '$mdDialog', 'ACMetrics', function ($scope, $mdToast, $mdDialog, ACMetrics) {
    //$scope.acMetrics = new ACMetrics();
    $scope.acMetricsList = [];
    $scope.tranDate = new Date();

    $scope.analystList = [];
    $scope.fetchAllAnalysts = function () {
        $scope.analystList = ACMetrics.listAnalyst();
    };

    $scope.applicationList = [];
    $scope.fetchAllApplications = function () {
        $scope.applicationList = ACMetrics.listApplication();
    };

    $scope.operationList = [];
    $scope.fetchAllOperations = function () {
        $scope.operationList = ACMetrics.listOperation();
    };

    $scope.fetchAllACMetricss = function () {
        var formattedtranDate = $scope.tranDate.toISOString().slice(0,10);
        $scope.acMetricsList = ACMetrics.queryByDate({tranDate:formattedtranDate});
    };

    $scope.fetchAllAnalysts();
    $scope.fetchAllApplications();
    $scope.fetchAllOperations();
    $scope.fetchAllACMetricss();

    $scope.open = function() {
        $scope.popup.opened = true;
    };
    $scope.popup = {
        opened: false
    };

    // Only shows Metrics of the selected Analyst
    $scope.hideMetricsOfAnalyst = function(analyst) {
        if (!$scope.analyst) {
            console.log('DO NOT hide acm if not selected');
            return false;
        }
        if (analyst.analystID === $scope.analyst.analystID) {
            console.log('DO NOT hide acm for selected Analyst');
            return false;
        }
        console.log('Hide acm');
        return true;
    };

    $scope.dateOptions = {
        //dateDisabled: disabled,
        formatYear: 'yy',
        maxDate: new Date(2030, 1, 1),
        minDate: new Date(2017, 1, 1),
        startingDay: 1
    };

    // Disable weekend selection
    function disabled(data) {
        var date = data.date,
            mode = data.mode;
        return mode === 'day' && (date.getDay() === 0 || date.getDay() === 6);
    };

    //Dislay a message
    function simpleToastBase(message, position, delay, action) {
        $mdToast.show(
            $mdToast.simple()
                .content(message)
                .position(position)
                .hideDelay(delay)
                .action(action)
        );
    };

    function showError(message) {
        simpleToastBase(message, 'bottom right', 6000, 'X');
    };

    $scope.showDialog = function showDialog(operation, data, event) {
        //var tempData = undefined;
        var tempData = new ACMetrics();
        if (data === undefined) {
            tempData.tranDate = new Date();
            tempData.uploadDate = new Date()
        } else {
            tempData.acmID = data.acmID;
            tempData.tranDate = data.tranDate;
            tempData.snowid = data.snowid;
            tempData.analyst = data.analyst;
            tempData.operation = data.operation;
            tempData.numOfUsers = data.numOfUsers;
            tempData.application = data.application;
            tempData.uploadDate = data.uploadDate;
        }
        $mdDialog.show({
            templateUrl: 'editor.html',
            targetEvent: event,
            locals: {
                acMetrics: tempData,
                acMetricsList: $scope.acMetricsList,
                analystList: $scope.analystList,
                applicationList: $scope.applicationList,
                operationList: $scope.operationList,
                operation: operation
            },
            bindToController: true,
            controller: DialogController,
            parent: angular.element(document.body),
            onComplete: $scope.fetchAllACMetricss
        })
            .then(
                function (result) {
                    showError(result);
                }
            );
    };

    function DialogController($scope, $mdDialog, operation, acMetrics, acMetricsList, analystList, applicationList, operationList) {
        $scope.acMetrics = acMetrics;
        $scope.operation = operation;
        $scope.acMetricsList = acMetricsList;
        $scope.analystList = analystList;
        $scope.applicationList = applicationList;
        $scope.operationList = operationList;

       switch (operation) {
            case 'C':
                $scope.operation = 'Add';
                break;
            case 'UD':
                $scope.operation = 'Modify';
                break;
            case 'R':
                $scope.operation = 'Details';
                break;
            default:
                $scope.operation = 'Details';
                break;
        }

        $scope.goBack = function goBack() {
            $mdDialog.cancel();
        }

        console.log('acMetrics: ' + $scope.acMetrics.acmID);
        console.log('analystList: ' + $scope.analystList);

        $scope.saveRecord = function saveRecord() {
            if ($scope.acMetrics.acmID === undefined) $scope.addRecord();
            else $scope.modifyRecord();
        }

        $scope.addRecord = function addRecord() {
            $scope.acMetrics.$save(function () {
                //$scope.$parent.fetchAllACMetricss($scope.$parent.tranDate);
                $mdDialog.hide('Metrics Added');
            });
        }

        $scope.modifyRecord = function modifyRecord() {
            $scope.acMetrics.$update(function () {
                //$scope.$parent.fetchAllACMetricss($scope.$parent.tranDate);
                $mdDialog.hide('Metrics Modified');
            });
        }

        $scope.deleteRecord = function deleteRecord() {
            var acMetrics = $scope.$parent.ACMetrics.get({id: $scope.acMetrics.id}, function () {
                acMetrics.$delete(function () {
                    //$scope.$parent.fetchAllACMetricss($scope.$parent.tranDate);
                    $mdDialog.hide('Metrics Deleted');
                });
            });
        }
    };
}]);
