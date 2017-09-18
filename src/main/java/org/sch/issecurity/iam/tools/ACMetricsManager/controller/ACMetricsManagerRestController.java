package org.sch.issecurity.iam.tools.ACMetricsManager.controller;
 
import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

import org.sch.issecurity.iam.tools.ACMetricsManager.dao.ACMetricsDAO;
import org.sch.issecurity.iam.tools.ACMetricsManager.dao.AnalystDAO;
import org.sch.issecurity.iam.tools.ACMetricsManager.dao.ApplicationDAO;
import org.sch.issecurity.iam.tools.ACMetricsManager.dao.OperationDAO;
import org.sch.issecurity.iam.tools.ACMetricsManager.model.ACMetrics;
import org.sch.issecurity.iam.tools.ACMetricsManager.model.Analyst;
import org.sch.issecurity.iam.tools.ACMetricsManager.model.Application;
import org.sch.issecurity.iam.tools.ACMetricsManager.model.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class ACMetricsManagerRestController {
 
    @Autowired
    ACMetricsDAO acMetricsDAO;

    @Autowired
    AnalystDAO analystDAO;

    @Autowired
    ApplicationDAO applicationDAO;

    @Autowired
    OperationDAO operationDAO;



    //-------------------Retrieve All ACMetricss--------------------------------------------------------
     
    @RequestMapping(value = "/acm/byDate", params="tranDate", method = RequestMethod.GET)
    public ResponseEntity<List<ACMetrics>> listACMetricssByDate(@RequestParam("tranDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate tranDate) {

        java.sql.Date tranDateSQL = Date.valueOf(tranDate);
        List<ACMetrics> acMetricsList = acMetricsDAO.listACMetricssByDate(tranDateSQL);
        if(acMetricsList.isEmpty()){
            return new ResponseEntity<List<ACMetrics>>(HttpStatus.NO_CONTENT);//You may decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<ACMetrics>>(acMetricsList, HttpStatus.OK);
    }
 
 
    
    //-------------------Retrieve Single ACMetrics--------------------------------------------------------

    @RequestMapping(value = "/acm/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ACMetrics> getACMetrics(@PathVariable("id") long id) {
        System.out.println("Fetching ACMetrics with id " + id);
        ACMetrics acMetrics = acMetricsDAO.getACMetricsByID(id);
        if (acMetrics == null) {
            System.out.println("ACMetrics with id " + id + " not found");
            return new ResponseEntity<ACMetrics>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<ACMetrics>(acMetrics, HttpStatus.OK);
    }
     
    //-------------------Create a ACMetrics--------------------------------------------------------
     
    @RequestMapping(value = "/acm", method = RequestMethod.POST)
    public ResponseEntity<Void> createACMetrics(@RequestBody ACMetrics acMetrics,    UriComponentsBuilder ucBuilder) {
        System.out.println("Creating ACMetrics " + acMetrics.toString());
 
        if (acMetricsDAO.isACMetricsExist(acMetrics)) {
            System.out.println("A ACMetrics of " + acMetrics.toString() + " already exist");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        acMetrics.setUploadDate(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
        acMetricsDAO.addACMetrics(acMetrics);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/acm/{id}").buildAndExpand(acMetrics.getAcmID()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
 
    
     
    //------------------- Update a ACMetrics --------------------------------------------------------
     
    @RequestMapping(value = "/acm/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ACMetrics> updateACMetrics(@PathVariable("id") long id, @RequestBody ACMetrics acMetrics) {
        System.out.println("Updating ACMetrics " + id);
         
        ACMetrics currentACMetrics = acMetricsDAO.getACMetricsByID(id);
         
        if (currentACMetrics==null) {
            System.out.println("ACMetrics with id " + id + " not found");
            return new ResponseEntity<ACMetrics>(HttpStatus.NOT_FOUND);
        }
 
        currentACMetrics.setAnalyst(acMetrics.getAnalyst());
        currentACMetrics.setApplication(acMetrics.getApplication());
        currentACMetrics.setNumOfUsers(acMetrics.getNumOfUsers());
        currentACMetrics.setOperation(acMetrics.getOperation());
        currentACMetrics.setSNOWID(acMetrics.getSNOWID());
        currentACMetrics.setTranDate(acMetrics.getTranDate());

        java.sql.Date uploadDate = new java.sql.Date(new java.util.Date().getTime());
        currentACMetrics.setUploadDate(uploadDate);

        acMetricsDAO.updateACMetrics(currentACMetrics);
        return new ResponseEntity<ACMetrics>(currentACMetrics, HttpStatus.OK);
    }
 
    
    
    //------------------- Delete a ACMetrics --------------------------------------------------------
     
    @RequestMapping(value = "/acm/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ACMetrics> deleteACMetrics(@PathVariable("id") long id) {
        System.out.println("Fetching & Deleting ACMetrics with id " + id);
 
        ACMetrics acMetrics = acMetricsDAO.getACMetricsByID(id);
        if (acMetrics == null) {
            System.out.println("Unable to delete. ACMetrics with id " + id + " not found");
            return new ResponseEntity<ACMetrics>(HttpStatus.NOT_FOUND);
        }
 
        acMetricsDAO.deleteACMetrics(id);
        return new ResponseEntity<ACMetrics>(HttpStatus.NO_CONTENT);
    }
 
     
    
    //------------------- Delete All ACMetricss --------------------------------------------------------
     
    @RequestMapping(value = "/acm", method = RequestMethod.DELETE)
    public ResponseEntity<ACMetrics> deleteAllACMetricss() {
        System.out.println("Deleting All ACMetricss is not supported");
 
        return new ResponseEntity<ACMetrics>(HttpStatus.NO_CONTENT);
    }


    //-------------------Retrieve All Analysts--------------------------------------------------------

    @RequestMapping(value = "/acm/analyst", method = RequestMethod.GET)
    public ResponseEntity<List<Analyst>> listAllAnalyst() {
        List<Analyst> analystList = analystDAO.listAnalyst();
        if(analystList.isEmpty()){
            return new ResponseEntity<List<Analyst>>(HttpStatus.NO_CONTENT);//You may decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Analyst>>(analystList, HttpStatus.OK);
    }

    //-------------------Retrieve All Application--------------------------------------------------------

    @RequestMapping(value = "/acm/application", method = RequestMethod.GET)
    public ResponseEntity<List<Application>> listAllApplication() {
        List<Application> applicationList = applicationDAO.listApplication();
        if(applicationList.isEmpty()){
            return new ResponseEntity<List<Application>>(HttpStatus.NO_CONTENT);//You may decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Application>>(applicationList, HttpStatus.OK);
    }

    //-------------------Retrieve All Operation--------------------------------------------------------

    @RequestMapping(value = "/acm/operation", method = RequestMethod.GET)
    public ResponseEntity<List<Operation>> listAllOperation() {
        List<Operation> operationList = operationDAO.listOperation();
        if(operationList.isEmpty()){
            return new ResponseEntity<List<Operation>>(HttpStatus.NO_CONTENT);//You may decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Operation>>(operationList, HttpStatus.OK);
    }
}