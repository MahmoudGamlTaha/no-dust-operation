package MTS.security.MTSClasses;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.MalformedURLException;
import OPERATIONPROJECT.model.BC.AM.AppModuleAMImpl;

import java.net.URL;

import java.security.KeyManagementException;

import java.security.NoSuchAlgorithmException;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.share.ADFContext;

import oracle.adf.view.rich.event.DialogEvent;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.adf.view.rich.context.AdfFacesContext;
import java.sql.Timestamp;
import java.util.Date;
import weblogic.net.http.HttpsURLConnection;
import weblogic.jaxrs.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.sql.CallableStatement;
import oracle.jbo.server.ViewObjectImpl;

import java.sql.SQLException;
import java.sql.Types;
import oracle.jbo.server.DBTransactionImpl;

import oracle.jbo.JboException;


public class PostTeamLeaderBean  extends ViewObjectImpl {
    public PostTeamLeaderBean() {
        super();
    }
    public void SubmitCarPost(ActionEvent action_event) {
        ViewObject CarPostVO=ADFUtils.findIterator("CarPostIterator").getViewObject();
        String shipment_ids="";
        String agent_ids=""; 
        String logged_user=ADFContext.getCurrent().getSecurityContext().getUserName();
        System.out.println(logged_user);
        Row r=null;
        CarPostVO.reset();
        RowSetIterator rs=CarPostVO.createRowSetIterator(null);
        while (rs.hasNext()) {
            r = rs.next();
            
             shipment_ids+=r.getAttribute("ShipmentId")+",";
            agent_ids+=r.getAttribute("OperationAgent")+",";
            
            }
        shipment_ids=shipment_ids.substring(0, shipment_ids.length()-1);
        agent_ids=agent_ids.substring(0,agent_ids.length()-1);
        System.out.println(shipment_ids);
        System.out.println(agent_ids);
       // Connection conn=null;
        String out_param=null;
        ADFUtils.callStoredFunction((AppModuleAMImpl)ADFUtils.getApplicationModuleForDataControl("AppModuleAMDataControl"),
                                                                            Types.NUMERIC, "ASSIGN_OPERATION_AGENT(?,?,?)",
                                                                            new Object[] {shipment_ids,agent_ids, ADFContext.getCurrent().getSecurityContext().getUserName()});

        //OADBTransaction txn = getDBTransaction();
       /* CallableStatement cs=null;
        int var=0;
        String query="call ASSIGN_OPERATION_AGENT(?,?,?)";
        try{
        cs=getDBTransaction().createCallableStatement(query, getDBTransaction().DEFAULT);
            cs.setString(1, shipment_ids);
            cs.setString(2,agent_ids);
            cs.setString(3, logged_user);
            var=cs.executeUpdate();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            try {
                cs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }*/
        FacesMessage facesMessage =
            new FacesMessage(FacesMessage.SEVERITY_INFO,"Success",
                             "Agents Assigned Successfully");
        
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        System.out.println("bbb");
    }
    public void SubmitDriverPayment(ActionEvent actionevent) {
        ViewObject driver_payVO=ADFUtils.findIterator("DriverPaymentIterator").getViewObject();
        String payment_ids="";
        String due_dates="";
        String status="";
        String logged_user=ADFContext.getCurrent().getSecurityContext().getUserName();
        Row r=null;
        driver_payVO.reset();
        RowSetIterator rs=driver_payVO.createRowSetIterator(null);
        while(rs.hasNext()){
        r=rs.next();
            payment_ids+=r.getAttribute("TransactionId")+",";
            String[] d=r.getAttribute("DueDate").toString().split(" ");
            due_dates+=d[0]+",";
            status+=r.getAttribute("PaymentStatus")+",";
        }
        payment_ids=payment_ids.substring(0,payment_ids.length()-1);
        due_dates=due_dates.substring(0,due_dates.length()-1);
        status=status.substring(0,status.length()-1);
        System.out.println(payment_ids);
        System.out.println(due_dates);
        System.out.println(status);
        ADFUtils.callStoredFunction((AppModuleAMImpl)ADFUtils.getApplicationModuleForDataControl("AppModuleAMDataControl"),
                                                                            Types.NUMERIC, "UPDATE_PAYROLL(?,?,?,?)",
                                                                            new Object[] {payment_ids,due_dates,status, ADFContext.getCurrent().getSecurityContext().getUserName()});

        FacesMessage facesMessage =
            new FacesMessage(FacesMessage.SEVERITY_INFO,"Success",
                             "Payments Updated Successfully");
        
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    }
    
    public void SubmitPilotPayments(ActionEvent action_event) {
        ViewObject pilot_paymentVO=ADFUtils.findIterator("PilotPaymentIterator").getViewObject();
        String payment_ids="";
        String due_dates="";
        String status="";
        String logged_user=ADFContext.getCurrent().getSecurityContext().getUserName();
        Row r=null;
        pilot_paymentVO.reset();
        RowSetIterator rs=pilot_paymentVO.createRowSetIterator(null);
        while(rs.hasNext()){
        r=rs.next();
            payment_ids+=r.getAttribute("TransactionId")+",";
            String[] d=r.getAttribute("DueDate").toString().split(" ");
            due_dates+=d[0]+",";
            status+=r.getAttribute("PaymentStatus")+",";
        }
        payment_ids=payment_ids.substring(0,payment_ids.length()-1);
        due_dates=due_dates.substring(0,due_dates.length()-1);
        status=status.substring(0,status.length()-1);
        ADFUtils.callStoredFunction((AppModuleAMImpl)ADFUtils.getApplicationModuleForDataControl("AppModuleAMDataControl"),
                                                                            Types.NUMERIC, "UPDATE_PAYROLL(?,?,?,?)",
                                                                            new Object[] {payment_ids,due_dates,status, ADFContext.getCurrent().getSecurityContext().getUserName()});

      //  payment_ids=payment_ids.substring(0,payment_ids.length()-1);
        //due_dates=due_dates.substring(0,due_dates.length()-1);
        //status=status.substring(0,status.length()-1);
        FacesMessage facesMessage =
            new FacesMessage(FacesMessage.SEVERITY_INFO,"Success",
                             "Payments Updated Successfully");
        
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    }

    public String SubmitPost(ActionEvent action_event) {
        // Add event code here...
        System.out.println("xxxxxx");
        ViewObject CarPostVO=ADFUtils.findIterator("CarPostIterator").getViewObject();
        String shipment_ids="";
        String agent_ids=""; 
        String logged_user=ADFContext.getCurrent().getSecurityContext().getUserName();
        Row r=null;
        CarPostVO.reset();
        RowSetIterator rs=CarPostVO.createRowSetIterator(null);
        while (rs.hasNext()) {
            r = rs.next();
                shipment_ids+=r.getAttribute("SHIPMENT_ID")+",";
            agent_ids+=r.getAttribute("OPERATION_AGENT")+",";
            
            }
        shipment_ids=shipment_ids.substring(0, shipment_ids.length()-1);
        agent_ids=agent_ids.substring(0,agent_ids.length()-1);
       System.out.println(logged_user);
        
        return null;
    }

    public void SubmitChanges(ActionEvent actionEvent) {
        // Add event code here...
        ADFUtils.findOperation("Commit").execute();
        
    }

    public void RejectCar(DialogEvent dialogEvent) {
        // Add event code here...
        if(dialogEvent.getOutcome().equals(dialogEvent.getOutcome().yes)) {
          System.err.println (ADFUtils.getBoundAttributeValue("HrDate"));
          System.err.println(ADFUtils.getBoundAttributeValue("AccManagerDate"));
            ADFUtils.callStoredFunction((AppModuleAMImpl)ADFUtils.getApplicationModuleForDataControl("AppModuleAMDataControl"),
                                                                                Types.NUMERIC, "REJECT_CAR(?,?,?,?,?,?,?,?,?)",
                                                                                new Object[] {ADFUtils.getBoundAttributeValue("Id") , ADFUtils.getBoundAttributeValue("AmountReturned") , ADFUtils.getBoundAttributeValue("ItReview") , ADFUtils.getBoundAttributeValue("ItSignature")  , ADFUtils.getBoundAttributeValue("AccManagerSignature") , ADFUtils.getBoundAttributeValue("AccManagerDate") , ADFUtils.getBoundAttributeValue("HrSignature") , ADFUtils.getBoundAttributeValue("HrDate") , ADFContext.getCurrent().getSecurityContext().getUserName()});
           
            JSFUtils.addFacesInformationMessage("Car Rejected Successfully");
   
        }
    }

    public void ChangeReconciliationAgent(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        Row row=ADFUtils.findIterator("CarPostIterator").getCurrentRow();
        row.setAttribute("UpdatedBy", ADFContext.getCurrent().getSecurityContext().getUserName());
        System.err.println("Shipment Id"+row.getAttribute("ShipmentId"));
    }

    public void ChangePilotPaymentAgent(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        Row row=ADFUtils.findIterator("PilotPaymentIterator").getViewObject().getCurrentRow();
        row.setAttribute("UpdatedBy", ADFContext.getCurrent().getSecurityContext().getUserName());
    }

    public void ChangeDriverPaymentAgent(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        Row row=ADFUtils.findIterator("DriverPaymentIterator").getViewObject().getCurrentRow();
        row.setAttribute("UpdatedBy", ADFContext.getCurrent().getSecurityContext().getUserName());
    }

    public void OpenCarRejectionPopup(ActionEvent actionEvent) {
        // Add event code here...
        JSFUtils.showPopup("p11");
    }
}
