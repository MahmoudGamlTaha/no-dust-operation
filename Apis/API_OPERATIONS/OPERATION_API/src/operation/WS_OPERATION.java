package operation;

import java.sql.Connection;

import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.Date;

import javax.jws.WebParam;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("NODUST")
public class WS_OPERATION {
    public WS_OPERATION() {
        super();
    }



     /******************************************************* 1. Upload Assignments From Operation To DMS ***********************************************************************************************/
    @GET
    @Produces(value = { "application/json", "application/xml" })
    @Path("/UploadAssignmentsFromOperations")
    public Response UploadAssignemntsFromOperation_(@QueryParam("Action_Date") String Action_Date, @QueryParam("Area_ID") int area_id,@QueryParam("Driver_id") String driver_id) {
        Response resp=new Response();
        DBOPERATION operation=new DBOPERATION();
        
            if (operation.UploadReadyAssignments(area_id, Action_Date, driver_id)) {
                resp.setMessage("success");
            } else {
                resp.setMessage("fail");
            }
     
        
        return resp;
    }
    
    /********************************************************* 2.  Update Assignment On Cloud *******************************************************************************************/
       @GET
       @Produces(value = { "application/json", "application/xml" })
       @Path("/UpdateAssignmentsOnCloud")
       public Response UpdateAssignmentsOnCloud() {
         Response resp=new Response();
         DBOPERATION operation=new DBOPERATION();

          if (!operation.UpdateAssignmentsOnCloud()) {
               resp.setMessage("fail");
           } else {
               resp.setMessage("success");
           }


           return resp;
       }
       
       
       /************************************************************** 3. Download Dispatched Shipments **********************************************************************************************/
    
       /*************************************************************  3.1 Download Dispatched Shipment By Id *********************************************************************************************/
       @GET
       @Produces(value = { "application/json", "application/xml" })
       @Path("/DownloadDispatchedById")
       public Response DownloadDispatchedById(@QueryParam("Shipment_id") int shipment_id) {
         Response resp=new Response();
         DBOPERATION operation=new DBOPERATION();
         
            if (operation.DownloadDispatchShipmentById(shipment_id)) {
                 resp.setMessage("success");
             } else {
                 resp.setMessage("fail");
             }
         return resp;
       }
       
    /************************************************************* 3.2 Download Missed Dispatched Shipment  *********************************************************************************************/
   
    @GET
    @Produces(value = { "application/json", "application/xml" })
    @Path("/DownloadMissedDispatchedShipment")
    public Response DownloadMissedDispatched(@QueryParam("Shipment_date") String shipment_date) {
      Response resp=new Response();
      DBOPERATION operation=new DBOPERATION();
      
         if (operation.DownloadMissedDispatchedShipments(shipment_date)) {
              resp.setMessage("success");
          } else {
              resp.setMessage("fail");
          }
    
      
      return resp;
    }
      
    /************************************************************* 4. Download DMS Mobile Actions Delivery *******************************************************************************************************/
   
    @GET
    @Produces(value = { "application/json", "application/xml" })
    @Path("/DownloadDMSDeliveriesToOperations")
    public Response DownloadDMSDeliveries() {
      Response resp=new Response();
      DBOPERATION operation=new DBOPERATION();
      
        if(operation.DownloadAssignment_Actions()) {
            resp.setMessage("Success");
        }
        else {
            resp.setMessage("Fail");
        }
      return resp;
    }
      
   
    /************************************************************** 5. Download Reconciliations **************************************************************************************************************************/  
    @GET
    @Produces(value = { "application/json", "application/xml" })
    @Path("/DownloadReconciliationData")  
    public Response DownloadReconciliationData(@QueryParam("return_date") String return_date) {
        Response resp=new Response();
        DBOPERATION operation=new DBOPERATION();
        Date date= new Date();
        Timestamp ts = new Timestamp(date.getTime());
        System.out.println("Download Driver / HUB Cash Reconciliation Data at "+ts);

        if(operation.DownloadDriverHUBCashAmount(return_date)) {
            date= new Date();
            ts = new Timestamp(date.getTime());
            System.out.println("Download Driver / HUB Product Reconciliation Data at "+ts);
            if(operation.DownloadDriverHUBReconciliations(return_date)) {
                date= new Date();
                ts = new Timestamp(date.getTime());
                System.out.println("Download Driver / Pilot Cash Reconciliation Data at "+ts);
               if(operation.DownloadDriverPilotAmounts(return_date)) {
                    date= new Date();
                    ts = new Timestamp(date.getTime());
                    System.out.println("Download Driver / Pilot Product Reconciliation Data at "+ts);
                    if(operation.DownloadDriverPilotProducts(return_date)) {
                        date= new Date();
                        ts = new Timestamp(date.getTime());
                        System.out.println("Download Drivers Cover Products  at "+ts);
                        if(operation.GetDriverCoverOnDate(return_date))
                        {
                            date= new Date();
                            ts = new Timestamp(date.getTime());
                            System.out.println("Download Pilots Cover Products  at "+ts);
                            if(operation.GetPilotCoverProductOnDate(return_date))
                        resp.setMessage("Success");
                            else
                            resp.setMessage("Fail");
                        }
                        else{
                            resp.setMessage("Fail");
                        }
                    }
                    
                    else {
                        resp.setMessage("Fail");
                    }
                }
                else {
                    resp.setMessage("Fail");
                }
            }
            else {
                resp.setMessage("Fail");
            }
        }
            else {
            resp.setMessage("Fail");
        }
        return resp;
    }
    
    @GET
    @Produces(value = { "application/json", "application/xml" })
    @Path("/testapi")  
    public Response testdata() {
        Response resp=new Response();
        DBOPERATION operation=new DBOPERATION();
        return resp;
    }
    /*********************************************************Send SMS Api ***********************************************************************************/
    @GET
    @Produces(value = { "application/json", "application/xml" })
    @Path("/SendSMS")  
    public Response SendSMS(@QueryParam("IDS")String IDS,@QueryParam("Agent")String Agent) {
        Response resp=new Response();
        DBOPERATION operation=new DBOPERATION();
        Date date= new Date();
        Timestamp ts = new Timestamp(date.getTime());
        System.out.println("Start Sending SMS For Areas : "+IDS+" Sent By : "+ Agent +" at "+ts);
        if(operation.SendSMS(IDS,Agent)) {
      resp.setMessage("Success");
        }
        else {
            resp.setMessage("Fail");
        }
        
        System.out.println("ID : "+IDS);
        System.out.println("Agent:"+Agent);
              
        resp.setMessage("Success");
        return resp;
    }
   /************************************************************** Api Run at 12:00 am to add scheduling table **********************************************************************************************************/ 
   @GET
   @Produces(value = { "application/json", "application/xml" })
   @Path("/AddingScheduling")  
   public Response AddingScheduling(@QueryParam("WorkDate")String WorkDate) {
       Response resp=new Response();
       DBOPERATION operation=new DBOPERATION();
       Date date= new Date();
       Timestamp ts = new Timestamp(date.getTime());
       System.out.println("Start Adding Scheduling areas at "+ts);
       if(operation.Adding_Scheduling(WorkDate)) {
     resp.setMessage("Success");
       }
       else {
           resp.setMessage("Fail");
       }
       
      
             
       resp.setMessage("Success");
       return resp;
   }
    @GET
    @Produces(value = { "application/json", "application/xml" })
    @Path("/PayrollDriversData")
    public Response PayrollDriverData() {
        Response resp=new Response();
        DBOPERATION operation=new DBOPERATION();
        
            if (operation.DownloadPayrollDriver_order_count()) {
                resp.setMessage("success");
            } else {
                resp.setMessage("fail");
            }
     
        
        return resp;
    }
    
    @GET
    @Produces(value = { "application/json", "application/xml" })
    @Path("/PayrollDriversPilotsData")
    public Response PayrollDriverPilotData() {
        Response resp=new Response();
        DBOPERATION operation=new DBOPERATION();
        
            if (operation.DownloadPayrollEmp_order_count()) {
                resp.setMessage("success");
            } else {
                resp.setMessage("fail");
            }
     
        
        return resp;
    }
    
  /********************************************* New Added Function To integrate With Old Operation System ***********************************************************************************************************/
  @GET
  @Produces(value = { "application/json", "application/xml" })
  @Path("/UploadContractsToOperationCloudFlag")  
  public Response UploadContractsFromOldOperationFlag() {
      Response resp=new Response();
      DBOPERATION operation=new DBOPERATION();
      Date date= new Date();
      Timestamp ts = new Timestamp(date.getTime());
      if(operation.UploadContractsFromOldOperationFlag()) {
    resp.setMessage("Success");
      }
      else {
          resp.setMessage("Fail");
      }
      
     
            
      resp.setMessage("Success");
      return resp;
  }
 
 
  @GET
  @Produces(value = { "application/json", "application/xml" })
  @Path("/UploadContractsToOperationCloud")
    public Response UploadContractsFromOldOperation() {
      Response resp=new Response();
      DBOPERATION operation=new DBOPERATION();
      if(operation.UploadNewContractsFromOperationToOperationCloud()) {
          Date date= new Date();
          Timestamp ts = new Timestamp(date.getTime());
          System.err.println("New Contracts Uploaded at "+ts);
         /* if(operation.uploadContractUpdatesFromLocalOperationToCloud()) {
              date=new Date();
              ts=new Timestamp(date.getTime());
              System.err.println("Updated Contracts Uploaded at"+ts);
              if(operation.DeletedContractsFromLocalOperationToCloud()) {
                  date=new Date();
                  ts=new Timestamp(date.getTime());
                  System.err.println("Deleted Contracts Uploaded at"+ts);
              }
          }
          else{
              resp.setMessage("Failed");
          }*/
      }
      else {
          resp.setMessage("Failed");
      }
      return resp;
  }

/********************************** Contract Address Migration ***********************************************************************************************************/
   @GET
  @Produces(value = { "application/json", "application/xml" })
  @Path("/UploadContractAddressToOperationCloud")
  public Response UploadContractAddressFromOldOperation() {
    Response resp=new Response();
    DBOPERATION operation=new DBOPERATION();
    if(operation.UploadNewContractAddress()) {
        Date date=new Date();
        Timestamp ts=new Timestamp(date.getTime());
        System.out.println("New Address Uploaded at "+ts);
       /* if(operation.UploadUpdatedContractAddress()) {
            date=new Date();
            ts=new Timestamp(date.getTime());
            System.out.println("Updated Address Uploaded at "+ts);
            if(operation.UploadDeletedContractAddress()) {
                date=new Date();
                ts=new Timestamp(date.getTime());
                System.out.println("Deleted Address Uploaded at "+ts);
            }
           else {
                resp.setMessage("Failed");
            }
        }
        else{
            resp.setMessage("Failed");
        }*/
    }
    else{
        resp.setMessage("Failed");
    }
    return resp;
}
/************************************* Product Contract ******************************************************************************************************/
  @GET
  @Produces(value = { "application/json", "application/xml" })
  @Path("/UploadProductContractsToOperationCloud")
public Response UploadNewProductContract() {
    Response resp=new Response();
    DBOPERATION operation=new DBOPERATION();
    if(operation.UploadNewProductContract()) {
        Date date=new Date();
        Timestamp ts=new Timestamp(date.getTime());
        System.out.println("Upload New Product Contract at"+ts);
       /*if(operation.UploadUpdatedProductContract()) {
            date=new Date();
            ts=new Timestamp(date.getTime());
            System.out.println("Upload Updated Product Contract at "+ts);
            if(operation.UploadDeletedProductContract()) {
                date=new Date();
                ts=new Timestamp(date.getTime());
                System.out.println("Upload Deleted Product Contract at "+ts);

            }
            else{
                resp.setMessage("Failed");
            }
        }
        else{
            resp.setMessage("Failed");
        }*/
    }
    else {
        resp.setMessage("Failed");
    }
    return resp;
}


    @GET
    @Produces(value = { "application/json", "application/xml" })
    @Path("/UploadLookups")  
    public Response UploadLookups() {
        Response resp=new Response();
        DBOPERATION operation=new DBOPERATION();
        Date date= new Date();
       Timestamp ts=new Timestamp(date.getTime());
        System.out.println("Start Download City at "+ts);
        if(operation.UploadCity()) {
            date=new Date();
            ts=new Timestamp(date.getTime());
            System.err.println("Start Download Region at "+ts);
            if(operation.UploadRegion()) {
                date=new Date();
                ts=new Timestamp(date.getTime());
                System.err.println("Start Download Area at "+ts);
                if(operation.UploadArea()) {
                    date=new Date();
                    ts=new Timestamp(date.getTime());
                    System.err.println("Start Download Street at "+ts);
                    if(operation.UploadStreet()) {
                        resp.setMessage("Success");
                    }
                    else{
                        resp.setMessage("Failed");
                    }
                }
                else {
                    resp.setMessage("Failed");
                }
            }
            else {
                resp.setMessage("Failed");
            }
        }
        else{
            resp.setMessage("Failed");
        }
        return resp;
    }

    @GET
    @Produces(value = { "application/json", "application/xml" })
    @Path("/FlushTablesAfterUpload")  
    public Response FlushTablesAfterUpload() {
        Response resp=new Response();
        DBOPERATION operation=new DBOPERATION();
        Date date= new Date();
        Timestamp ts = new Timestamp(date.getTime());
        if(operation.FlushTablesAfterUpload()) {
      resp.setMessage("Success");
        }
        else {
            resp.setMessage("Fail");
        }
        
       
              
        resp.setMessage("Success");
        return resp;
    }
    
    
    
    @GET
    @Produces(value = { "application/json", "application/xml" })
    @Path("/UploadDeliveryDates")
    public Response UploadDeliveryDates() {
        Response resp=new Response();
        DBOPERATION operation=new DBOPERATION();
        
            if (operation.UploadDeliveryDates()) {
                resp.setMessage("success");
            } else {
                resp.setMessage("fail");
            }
     
        
        return resp;
    }
    
    
    
    @GET
    @Produces(value = { "application/json", "application/xml" })
    @Path("/DownloadDailyAssignments")
    public Response DownloadDailyAssignments(@QueryParam("Assign_date") String assign_date) {
        Response resp=new Response();
        DBOPERATION operation=new DBOPERATION();
        
            if (operation.DownloadDailyAssignments(assign_date)) {
                if(operation.DownloadDailyCollectionAssignments(assign_date))
                {
                resp.setMessage("success");
                }
                else {
                    resp.setMessage("fail");

                }
                
            } else {
                resp.setMessage("fail");
            }
     
        
        return resp;
    }
    @GET
    @Produces(value = { "application/json", "application/xml" })
    @Path("/UploadLockedAssignments")
    public Response UploadLockedAssignments() {
        Response resp=new Response();
        DBOPERATION operation=new DBOPERATION();
        
           if(operation.UploadLockedAssignments()) {
               resp.setMessage("Success");
           }
           else {
               resp.setMessage("fail");
           }
        
        return resp;
    }
}

