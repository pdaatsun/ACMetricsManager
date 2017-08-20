package org.sch.issecurity.iam.tools.ACMetricsManager.controller;
 
import java.sql.Date;
import java.util.List;

import org.sch.issecurity.iam.tools.ACMetricsManager.dao.ACMetricsDAO;
import org.sch.issecurity.iam.tools.ACMetricsManager.model.ACMetrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class ACMetricsManagerRestController {
 
    @Autowired
    ACMetricsDAO acMetricsDAO;  //Service which will do all data retrieval/manipulation work
 
    
    //-------------------Retrieve All ACMetricss--------------------------------------------------------
     
    @RequestMapping(value = "/acm/{tranDate}", method = RequestMethod.GET)
    public ResponseEntity<List<ACMetrics>> listACMetricssByDate(@PathVariable("tranDate") Date tranDate) {
        List<ACMetrics> acMetricsList = acMetricsDAO.listACMetricssByDate(tranDate);
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
 
        currentACMetrics.setAnalystID(acMetrics.getAnalystID());
        currentACMetrics.setAppID(acMetrics.getAppID());
        currentACMetrics.setNumOfUsers(acMetrics.getNumOfUsers());
        currentACMetrics.setOperationID(acMetrics.getOperationID());
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
 
}