'use strict';

myapp.factory('ACMetrics', ['$resource', function ($resource) {
	//$resource() function returns an object of resource class
    return $resource(
    		'http://localhost:8080/acm/:id',
    		{id: '@acmID'},//Handy for update & delete. id will be set with id of instance
    		{
    			update: {
    			      method: 'PUT' // To send the HTTP Put request when calling this custom update method.
    			},
                queryByDate :{
    				url: 'http://localhost:8080/acm/byDate',
                    method : 'GET',
                    isArray: true
                },
                listAnalyst :{
                    url: 'http://localhost:8080/acm/analyst',
                    method : 'GET',
                    isArray: true
                },
                listApplication :{
                    url: 'http://localhost:8080/acm/application',
                    method : 'GET',
                    isArray: true
                },
                listOperation :{
                    url: 'http://localhost:8080/acm/operation',
                    method : 'GET',
                    isArray: true
                }
    		}
    );
}]);