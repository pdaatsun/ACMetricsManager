<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Access Control Operation Metrics Manager</title>
    <style>
        .username.ng-valid {
            background-color: lightgreen;
        }

        .username.ng-dirty.ng-invalid-required {
            background-color: red;
        }

        .username.ng-dirty.ng-invalid-minlength {
            background-color: yellow;
        }

        .email.ng-valid {
            background-color: lightgreen;
        }

        .email.ng-dirty.ng-invalid-required {
            background-color: red;
        }

        .email.ng-dirty.ng-invalid-email {
            background-color: yellow;
        }

    </style>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
    <link href="//netdna.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
</head>
<body ng-app="myApp" class="ng-cloak">
<md-content ng-controller="ACMetricsController as ctrl" layout-padding ng-cloak>
    <div class="panel panel-default">
        <div class="panel-heading"><span class="lead">Access Control Operation Metrics Manager </span></div>
        <div class="formcontainer">
            <form ng-submit="ctrl.submit()" name="myForm" class="form-horizontal">

                <input type="hidden" ng-model="ctrl.acMetrics.acmID"/>
                <h4>Transaction Date</h4>
                <div class="row">
                        <div class="col-md-3">
                            <p class="input-group">
                        <input type="text" class="form-control" uib-datepicker-popup ng-model="ctrl.tranDate"
                               id="tranDate" is-open="ctrl.popup.opened" datepicker-options="ctrl.dateOptions"
                               ng-required="true" close-text="Close" ng-change="ctrl.fetchAllACMetricss()"/>

                        <span class="input-group-btn">
                            <button type="button" class="btn btn-default" ng-click="ctrl.open()"><i class="glyphicon glyphicon-calendar"></i></button>
                        </span>

                            </p>
                        </div>
                </div>

                <div class="row">
                    <div class="form-group col-md-12">
                        <label class="col-md-2 control-lable" for="analystID">Analyst ID</label>
                        <div class="col-md-7">
                            <input type="text" ng-model="ctrl.acMetrics.analystID" id="analystID"
                                   class="form-control input-sm" placeholder="Enter Analyst ID" required/>
                            <div class="has-error" ng-show="myForm.$dirty">
                                <span ng-show="myForm.analystID.$error.required">This is a required field</span>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="form-group col-md-12">
                        <label class="col-md-2 control-lable" for="SNOWID">SNOW ID</label>
                        <div class="col-md-7">
                            <input type="text" ng-model="ctrl.acMetrics.SNOWID" id="SNOWID"
                                   class="form-control input-sm" placeholder="Enter SNOW ID" required/>
                            <div class="has-error" ng-show="myForm.$dirty">
                                <span ng-show="myForm.SNOWID.$error.required">This is a required field</span>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="form-group col-md-12">
                        <label class="col-md-2 control-lable" for="appID">Application ID</label>
                        <div class="col-md-7">
                            <input type="text" ng-model="ctrl.acMetrics.appID" id="appID" class="form-control input-sm"
                                   placeholder="Enter Application ID" required/>
                            <div class="has-error" ng-show="myForm.$dirty">
                                <span ng-show="myForm.appID.$error.required">This is a required field</span>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="form-group col-md-12">
                        <label class="col-md-2 control-lable" for="operationID">Operation ID</label>
                        <div class="col-md-7">
                            <input type="text" ng-model="ctrl.acMetrics.operationID" id="operationID"
                                   class="form-control input-sm" placeholder="Enter Operation ID" required/>
                            <div class="has-error" ng-show="myForm.$dirty">
                                <span ng-show="myForm.operationID.$error.required">This is a required field</span>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="form-group col-md-12">
                        <label class="col-md-2 control-lable" for="numOfUsers">Number of Users</label>
                        <div class="col-md-7">
                            <input type="text" ng-model="ctrl.acMetrics.numOfUsers" id="numOfUsers"
                                   class="form-control input-sm" placeholder="Enter Number of Users" required/>
                            <div class="has-error" ng-show="myForm.$dirty">
                                <span ng-show="myForm.numOfUsers.$error.required">This is a required field</span>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="form-actions floatRight">
                        <input type="submit" value="{{!ctrl.acMetrics.acmID ? 'Add' : 'Update'}}"
                               class="btn btn-primary btn-sm" ng-disabled="myForm.$invalid">
                        <button type="button" ng-click="ctrl.reset()" class="btn btn-warning btn-sm"
                                ng-disabled="myForm.$pristine">Reset Form
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="lead">List of Metrics </span></div>
        <div class="tablecontainer">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>Metrics ID</th>
                    <th>Analyst ID</th>
                    <th>SNOW ID</th>
                    <th>Application ID</th>
                    <th>Operation ID</th>
                    <th>Number of Users</th>
                    <th>Upload Date</th>
                    <th width="20%"></th>
                </tr>
                </thead>
                <tbody>
                <tr ng-repeat="acm in ctrl.acMetricsList">
                    <td><span ng-bind="acm.acmID"></span></td>
                    <td><span ng-bind="acm.analystID"></span></td>
                    <td><span ng-bind="acm.SNOWID"></span></td>
                    <td><span ng-bind="acm.appID"></span></td>
                    <td><span ng-bind="acm.operationID"></span></td>
                    <td><span ng-bind="acm.numOfUsers"></span></td>
                    <td><span ng-bind="acm.uploadDate"></span></td>
                    <td>
                        <button type="button" ng-click="ctrl.edit(acm.acmID)" class="btn btn-success custom-width">
                            Edit
                        </button>
                        <button type="button" ng-click="ctrl.remove(acm.acmID)" class="btn btn-danger custom-width">
                            Remove
                        </button>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</md-content>

<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.4/angular.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.4/angular-resource.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.6.4/angular-animate.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.6.4/angular-sanitize.js"></script>

<script src="<c:url value='/static/js/app.js' />"></script>
<script src="<c:url value='/static/js/service/ACMetrics_service.js' />"></script>
<script src="<c:url value='/static/js/controller/ACMetrics_controller.js' />"></script>

<script src="//angular-ui.github.io/bootstrap/ui-bootstrap-tpls-2.5.0.js"></script>


</body>
</html>
