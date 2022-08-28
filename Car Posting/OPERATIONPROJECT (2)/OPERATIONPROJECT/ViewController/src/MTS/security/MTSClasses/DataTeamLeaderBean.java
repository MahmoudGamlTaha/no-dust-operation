package MTS.security.MTSClasses;

import OPERATIONPROJECT.model.BC.AM.AppModuleAMImpl;

import OPERATIONPROJECT.model.BC.VO.DataTeamLeaderContractsReviewVORowImpl;
import OPERATIONPROJECT.model.BC.VO.DataTeamLeaderEndOfDayVORowImpl;

import OPERATIONPROJECT.model.BC.VO.DataTeamLeaderNotesReviewVORowImpl;

import OPERATIONPROJECT.model.BC.VO.DataTeamLeaderPendingWorkVORowImpl;

import java.sql.Types;

import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.server.Entity;
import java.io.IOException;

import java.net.MalformedURLException;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;

import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;

import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.event.AttributeChangeEvent;
import org.apache.myfaces.trinidad.event.DisclosureEvent;
public class DataTeamLeaderBean {
    public DataTeamLeaderBean() {
        super();
    }

    /********************** Pending Work ******************************************************************************/

    public void ConfirmSubmitPendingWorkUpdates(DialogEvent dialogEvent) {
        // Add event code here...
        if(dialogEvent.getOutcome().equals(dialogEvent.getOutcome().yes)) {
         //   System.err.println("Selected Agent :"+ADFUtils.getBoundAttributeValue("Username"));
            ViewObject CarPostVO=ADFUtils.findIterator("DataTeamLeaderPendingWorkVO1Iterator").getViewObject();
            DataTeamLeaderPendingWorkVORowImpl r=null;
            String ass_dates="";
            String area_ids="";
            String driv_ids="";
            String pilot_ids="";
            String agent_ids="";
            String reschedule_delivery="";
            String new_delivery_dates="";
            String perfect="";
            String Issue_Bover="";
            String Lost="";
            String dispatchable="";
            String Total_contracts="";
            String NeedCall="";
            String Priority_8="";
            String Priority_9="";
            String Priority_10="";
            CarPostVO.reset();
            RowSetIterator rs=CarPostVO.createRowSetIterator(null);
            while (rs.hasNext()) {
                r = (DataTeamLeaderPendingWorkVORowImpl) rs.next();
                if(r.getEntity(0).getEntityState()== Entity.STATUS_MODIFIED) {
                    dispatchable+=r.getAttribute("Dispatchable")+",";
                    perfect+=r.getAttribute("Perfect")+",";
                    Issue_Bover+=r.getAttribute("IssueBover")+",";
                    Lost+=r.getAttribute("LostContracts")+",";
                    Total_contracts+=r.getAttribute("TotalAssignemnt")+",";
                    NeedCall+=r.getAttribute("NeedCall")+",";
                    Priority_8+=r.getAttribute("Priority8")+",";
                    Priority_9+=r.getAttribute("Priority9")+",";
                    Priority_10+=r.getAttribute("Priority10")+",";
                    
                    String [] x=r.getAttribute("AssignDate").toString().split(" ");
                   ass_dates+=x[0]+",";
                   area_ids+= r.getAttribute("AreaId")+",";
                   driv_ids+= r.getAttribute("DrivId")+",";
                   pilot_ids+= r.getAttribute("PilotId")+",";
                    agent_ids+=r.getAttribute("DataAgent")+",";
                    if(r.getAttribute("RescheduleDelivery")!=null)
                reschedule_delivery+=r.getAttribute("RescheduleDelivery")+",";
                    else
                        reschedule_delivery+="2,";

                new_delivery_dates+=r.getAttribute("NewDeliveryDate")+",";
                }
            }
            System.err.println("ass_dates"+ass_dates);
            System.err.println("area_ids"+area_ids);
            System.err.println("driv_ids"+driv_ids);
            System.err.println("pilot_ids"+pilot_ids);
            System.err.println("agent ids"+agent_ids);
            System.err.println("reschedule:"+reschedule_delivery);
            System.err.println("new_delivery dates:"+new_delivery_dates);
            System.err.println("Dispatchable : "+dispatchable);
            System.err.println("Perfect"+perfect);
            System.err.println("Issue Bover "+Issue_Bover);
            System.err.println("Lost"+Lost);
            System.err.println("Total Contracts"+Total_contracts);
            System.err.println("Need Call"+NeedCall);
            System.err.println("Priority 8"+Priority_8);
            System.err.println("Priority 9 "+Priority_9);
            System.err.println("Priority 10"+Priority_10);
                
            if(ass_dates.length()>0)
            {
            ADFUtils.callStoredFunction((AppModuleAMImpl)ADFUtils.getApplicationModuleForDataControl("AppModuleAMDataControl"),
                                                                                Types.NUMERIC, "ASSIGN_DATA_AGENT_PENDING_WORK(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                                                                new Object[] {ass_dates,area_ids,driv_ids,pilot_ids,agent_ids, ADFContext.getCurrent().getSecurityContext().getUserName(),reschedule_delivery,new_delivery_dates,dispatchable,perfect,Issue_Bover,Lost,Total_contracts,NeedCall,Priority_8,Priority_9,Priority_10});
            AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("t2"));

            JSFUtils.addFacesInformationMessage("Updates Submitted Successfully");
            }
            else {
                JSFUtils.addFacesErrorMessage("No Updates To Submit");
            }
        }
    }

    public void ConfirmBulkAssignPendingWork(DialogEvent dialogEvent) {
        // Add event code here...
        if(dialogEvent.getOutcome().equals(dialogEvent.getOutcome().yes)) {
            System.err.println("Selected Agent :"+ADFUtils.getBoundAttributeValue("Username"));
            ViewObject CarPostVO=ADFUtils.findIterator("DataTeamLeaderPendingWorkVO1Iterator").getViewObject();
            DataTeamLeaderPendingWorkVORowImpl r=null;
            String ass_dates="";
            String area_ids="";
            String driv_ids="";
            String pilot_ids="";
            CarPostVO.reset();
            RowSetIterator rs=CarPostVO.createRowSetIterator(null);
            while (rs.hasNext()) {
                r = (DataTeamLeaderPendingWorkVORowImpl) rs.next();
                System.err.println("record");
                if(r.getAttribute("PendingWorkFlag")!=null&&r.getAttribute("PendingWorkFlag").equals(true)) {
                    System.err.println("flag:"+r.getAttribute("PendingWorkFlag"));
                    String [] x =r.getAttribute("AssignDate").toString().split(" ");
                   ass_dates+= x[0]+",";
                   area_ids+= r.getAttribute("AreaId")+",";
                   driv_ids+= r.getAttribute("DrivId")+",";
                   pilot_ids+= r.getAttribute("PilotId")+",";
                 //  r.setAttribute("PendingWorkFlag", false);

                }
            }
            if(ass_dates.length()>0)
            {
                System.err.println("ass_dates"+ass_dates);
                System.err.println("area_ids"+area_ids);
                System.err.println("driv_ids"+driv_ids);
                System.err.println("pilot_ids"+pilot_ids);
            ADFUtils.callStoredFunction((AppModuleAMImpl)ADFUtils.getApplicationModuleForDataControl("AppModuleAMDataControl"),
                                                                                Types.NUMERIC, "BULK_ASSIGN_DATA_AGENT_PENDING_WORK(?,?,?,?,?,?)",
                                                                                new Object[] {ass_dates,area_ids,driv_ids,pilot_ids,ADFUtils.getBoundAttributeValue("Username"), ADFContext.getCurrent().getSecurityContext().getUserName()});
            
            ADFUtils.findOperation("Execute3").execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("t2"));
            
            JSFUtils.addFacesInformationMessage("Data Agent Assigned Successfully");
            }
            else {
                JSFUtils.addFacesErrorMessage("No Upates To Submit");
            }
        }
    }

 /*********************** End Of Day Review **************************************************************************************************/
    public void ConfirmBulkAssignEndOfDayReview(DialogEvent dialogEvent) {
        // Add event code here...
        if(dialogEvent.getOutcome().equals(dialogEvent.getOutcome().yes)) {
            System.err.println("Selected Agent :"+ADFUtils.getBoundAttributeValue("Username"));
            ViewObject CarPostVO=ADFUtils.findIterator("DataTeamLeaderEndOfDayVO1Iterator").getViewObject();
            DataTeamLeaderEndOfDayVORowImpl r=null;
            String ass_dates="";
            String area_ids="";
            String driv_ids="";
            String pilot_ids="";
            CarPostVO.reset();
            RowSetIterator rs=CarPostVO.createRowSetIterator(null);
            while (rs.hasNext()) {
                r = (DataTeamLeaderEndOfDayVORowImpl) rs.next();
                if(r.getAttribute("endofdayflag")!=null && r.getAttribute("endofdayflag").equals(true)) {
                    System.err.println("flag:"+r.getAttribute("endofdayflag"));
                    String [] x=r.getAttribute("ReturnDate").toString().split(" ");
                   ass_dates+= x[0]+",";
                   area_ids+= r.getAttribute("AreaId")+",";
                   driv_ids+= r.getAttribute("DriverId")+",";
                 //   pilot_ids+=r.getAttribute("PilotId")+",";
                 //  r.setAttribute("endofdayflag", false);

                }
            }
            if(ass_dates.length()>0)
            {
            ADFUtils.callStoredFunction((AppModuleAMImpl)ADFUtils.getApplicationModuleForDataControl("AppModuleAMDataControl"),
                                                                                Types.NUMERIC, "BULK_ASSIGN_DATA_AGENT_ENDOFDAY(?,?,?,?,?)",
                                                                                new Object[] {ass_dates,area_ids,driv_ids,ADFUtils.getBoundAttributeValue("Username"), ADFContext.getCurrent().getSecurityContext().getUserName()});
            ADFUtils.findOperation("Execute1").execute();
          /*  ViewObject end_of_day_vo=ADFUtils.findIterator("DataTeamLeaderEndOfDayVO1Iterator").getViewObject();
            end_of_day_vo.setQueryMode(ViewObject.QUERY_MODE_SCAN_DATABASE_TABLES);
            end_of_day_vo.executeQuery();*/
            AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("t4"));

            JSFUtils.addFacesInformationMessage("Data Agent Assigned Successfully");
            }
            else {
                JSFUtils.addFacesErrorMessage("No Updates To Submit");
            }
        }
    }

    public void ConfirmAssignEndOfDayReview(DialogEvent dialogEvent) {
        // Add event code here...
        if(dialogEvent.getOutcome().equals(dialogEvent.getOutcome().yes)) {
         //   System.err.println("Selected Agent :"+ADFUtils.getBoundAttributeValue("Username"));
            ViewObject CarPostVO=ADFUtils.findIterator("DataTeamLeaderEndOfDayVO1Iterator").getViewObject();
            DataTeamLeaderEndOfDayVORowImpl r=null;
            String ass_dates="";
            String area_ids="";
            String driv_ids="";
            String agent_ids="";
            String pilot_ids="";
            CarPostVO.reset();
            RowSetIterator rs=CarPostVO.createRowSetIterator(null);
            while (rs.hasNext()) {
                r = (DataTeamLeaderEndOfDayVORowImpl)rs.next();
                if(r.getEntity(0).getEntityState()== Entity.STATUS_MODIFIED) {
                    String [] x=r.getAttribute("ReturnDate").toString().split(" ");
                   ass_dates+= x[0]+",";
                   area_ids+= r.getAttribute("AreaId")+",";
                   driv_ids+= r.getAttribute("DriverId")+",";
                    agent_ids+=r.getAttribute("DataAgent")+",";
                 //   pilot_ids+=r.getAttribute("PilotId")+",";
                }
            }
            if(ass_dates.length()>0)
            {
            ADFUtils.callStoredFunction((AppModuleAMImpl)ADFUtils.getApplicationModuleForDataControl("AppModuleAMDataControl"),
                                                                                Types.NUMERIC, "ASSIGN_DATA_AGENT_END_OF_DAY(?,?,?,?,?)",
                                                                                new Object[] {ass_dates,area_ids,driv_ids,agent_ids, ADFContext.getCurrent().getSecurityContext().getUserName()});
           /*     ViewObject end_of_day_vo=ADFUtils.findIterator("DataTeamLeaderEndOfDayVO1Iterator").getViewObject();
                end_of_day_vo.setQueryMode(ViewObject.QUERY_MODE_SCAN_DATABASE_TABLES);
                end_of_day_vo.executeQuery();*/
            AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("t4"));

            JSFUtils.addFacesInformationMessage("Updates Submitted Successfully");
            }
            else {
                JSFUtils.addFacesErrorMessage("No Updates To Submit");
            }
        
    }
}
/***************************** Notes Review *************************************************************************************************************/
    public void ConfirmBulkAssignNotesReview(DialogEvent dialogEvent) {
        // Add event code here...
        if(dialogEvent.getOutcome().equals(dialogEvent.getOutcome().yes)) {
            String user_selected=ADFUtils.getBoundAttributeValue("Username").toString();
            System.err.println("Selected Agent :"+user_selected);
            ViewObject CarPostVO=ADFUtils.findIterator("DataTeamLeaderNotesReviewVO1Iterator").getViewObject();
            DataTeamLeaderNotesReviewVORowImpl r=null;
            String ass_dates="";
            String area_ids="";
            String driv_ids="";
            String pilot_ids="";
            CarPostVO.reset();
            System.err.println("Bulk Assign Notes");
            RowSetIterator rs=CarPostVO.createRowSetIterator(null);
            while (rs.hasNext()) {
                r = (DataTeamLeaderNotesReviewVORowImpl) rs.next();
                System.err.println("flag:"+r.getAttribute("NotesFlag"));

                if(r.getAttribute("NotesFlag")!=null&&r.getAttribute("NotesFlag").equals(true)) {
                    System.err.println("flag:"+r.getAttribute("NotesFlag"));
                    String [] x=r.getAttribute("AssignDate").toString().split(" ");
                   ass_dates+= x[0]+",";
                   area_ids+= r.getAttribute("AreaId")+",";
                   driv_ids+= r.getAttribute("DrivId")+",";
                   pilot_ids+= r.getAttribute("PilotId")+",";
                  // r.setAttribute("NotesFlag", false);

                }
                
            }
            System.err.println("ass_dates"+ass_dates);
            System.err.println("area_ids"+area_ids);
            System.err.println("driv_ids"+driv_ids);
            System.err.println("pilot_id"+pilot_ids);
            if(ass_dates.length()>0)
            {
            ADFUtils.callStoredFunction((AppModuleAMImpl)ADFUtils.getApplicationModuleForDataControl("AppModuleAMDataControl"),
                                                                                Types.NUMERIC, "BULK_ASSIGN_DATA_AGENT_NOTES_REVIEW(?,?,?,?,?,?)",
                                                                                new Object[] {ass_dates,area_ids,driv_ids,pilot_ids,user_selected, ADFContext.getCurrent().getSecurityContext().getUserName()});
            ADFUtils.findOperation("Execute2").execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("t3"));

            JSFUtils.addFacesInformationMessage("Data Agent Assigned Successfully");
            }
            else {
                JSFUtils.addFacesErrorMessage("No Updates To Submit");
            }
        }
    }

    public void ConfirmAssignNotesReview(DialogEvent dialogEvent) {
        // Add event code here...
        if(dialogEvent.getOutcome().equals(dialogEvent.getOutcome().yes)) {
         //   System.err.println("Selected Agent :"+ADFUtils.getBoundAttributeValue("Username"));
            ViewObject CarPostVO=ADFUtils.findIterator("DataTeamLeaderNotesReviewVO1Iterator").getViewObject();
            DataTeamLeaderNotesReviewVORowImpl r=null;
            String ass_dates="";
            String area_ids="";
            String driv_ids="";
            String agent_ids="";
            String pilot_ids="";
            CarPostVO.reset();
            RowSetIterator rs=CarPostVO.createRowSetIterator(null);
            while (rs.hasNext()) {
                r =(DataTeamLeaderNotesReviewVORowImpl) rs.next();
                if(r.getEntity(0).getEntityState()== Entity.STATUS_MODIFIED) {
                    String [] x =r.getAttribute("AssignDate").toString().split(" ");
                   ass_dates+= x[0]+",";
                   area_ids+= r.getAttribute("AreaId")+",";
                   driv_ids+= r.getAttribute("DrivId")+",";
                    agent_ids+=r.getAttribute("DataAgent")+",";
                    pilot_ids+=r.getAttribute("PilotId")+",";
                }
            }
            if(ass_dates.length()>0)
            {
            ADFUtils.callStoredFunction((AppModuleAMImpl)ADFUtils.getApplicationModuleForDataControl("AppModuleAMDataControl"),
                                                                                Types.NUMERIC, "ASSIGN_DATA_AGENT_NOTES_REVIEW(?,?,?,?,?,?)",
                                                                                new Object[] {ass_dates,area_ids,driv_ids,pilot_ids,agent_ids, ADFContext.getCurrent().getSecurityContext().getUserName()});
            AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("t3"));
            
            JSFUtils.addFacesInformationMessage("Updates Submitted Successfully");
            }
            else {
                JSFUtils.addFacesErrorMessage("No Updates To Submit");
            }
        }
    }
/********************************************* Contracts Review ***********************************************************************************************************************************************/
    public void ConfirmBulkAssignContractsReview(DialogEvent dialogEvent) {
        // Add event code here...
        if(dialogEvent.getOutcome().equals(dialogEvent.getOutcome().yes)) {
            String x=ADFUtils.getBoundAttributeValue("Username").toString();
            System.err.println("Selected Agent :"+x);
            ViewObject CarPostVO=ADFUtils.findIterator("DataTeamLeaderContractsReviewVO1Iterator").getViewObject();
            Row r=null;
            String ass_dates="";
            String area_ids="";
            String driv_ids="";
            String pilot_ids="";
            String delivery_types="";
            CarPostVO.reset();
            RowSetIterator rs=CarPostVO.createRowSetIterator(null);
            while (rs.hasNext()) {
                r = rs.next();
                if(r.getAttribute("ContractFlag")!=null && r.getAttribute("ContractFlag").equals(true)) {
                    System.err.println("flag:"+r.getAttribute("ContractFlag"));
                   ass_dates+= r.getAttribute("AssignDate")+",";
                   area_ids+= r.getAttribute("AreaId")+",";
                   driv_ids+= r.getAttribute("DrivId")+",";
                   pilot_ids+= r.getAttribute("PilotId")+",";
                    delivery_types+=r.getAttribute("DeliveryType")+",";
          //         r.setAttribute("ContractFlag", false);

                }
            }
            System.err.println("Assign_date:"+ass_dates);
            System.err.println("Area_Ids:"+area_ids);
            System.err.println("Driv_ids:"+driv_ids);
            System.err.println("pilot_id:"+pilot_ids);
            System.err.println("delivery_type:"+delivery_types);
            if(ass_dates.length()>0)
            {
            ADFUtils.callStoredFunction((AppModuleAMImpl)ADFUtils.getApplicationModuleForDataControl("AppModuleAMDataControl"),
                                                                                Types.NUMERIC, "BULKASSIGNAGENT_CONTRACT_REV(?,?,?,?,?,?,?)",
                                                                                new Object[] {ass_dates,area_ids,driv_ids,pilot_ids,x, ADFContext.getCurrent().getSecurityContext().getUserName(),delivery_types});
            ADFUtils.findOperation("Execute").execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("t5"));

            JSFUtils.addFacesInformationMessage("Data Agent Assigned Successfully");
            }
            else {
                JSFUtils.addFacesErrorMessage("No Updates to Submit");
            }
        }
    }

    public void ConfirmSubmitContractsReview(DialogEvent dialogEvent) {
        // Add event code here...
        if(dialogEvent.getOutcome().equals(dialogEvent.getOutcome().yes)) {
         //   System.err.println("Selected Agent :"+ADFUtils.getBoundAttributeValue("Username"));
            ViewObject CarPostVO=ADFUtils.findIterator("DataTeamLeaderContractsReviewVO1Iterator").getViewObject();
            DataTeamLeaderContractsReviewVORowImpl r=null;
            String ass_dates="";
            String area_ids="";
            String driv_ids="";
            String agent_ids="";
            String pilot_ids="";
            String delivery_types="";
            CarPostVO.reset();
            RowSetIterator rs=CarPostVO.createRowSetIterator(null);
            while (rs.hasNext()) {
                r = (DataTeamLeaderContractsReviewVORowImpl)rs.next();
                if(r.getEntity(0).getEntityState()== Entity.STATUS_MODIFIED) {
                   ass_dates+= r.getAttribute("AssignDate")+",";
                   area_ids+= r.getAttribute("AreaId")+",";
                   driv_ids+= r.getAttribute("DrivId")+",";
                    agent_ids+=r.getAttribute("DataAgent")+",";
                pilot_ids+=r.getAttribute("PilotId")+",";
                delivery_types+=r.getAttribute("DeliveryType")+",";
                }
            }
            System.err.println("Assign_date:"+ass_dates);
            System.err.println("Area_Ids:"+area_ids);
            System.err.println("Driv_ids:"+driv_ids);
            System.err.println("pilot_id:"+pilot_ids);
            System.err.println("delivery_type:"+delivery_types);
            if(ass_dates.length()>0)
            {
            ADFUtils.callStoredFunction((AppModuleAMImpl)ADFUtils.getApplicationModuleForDataControl("AppModuleAMDataControl"),
                                                                                Types.NUMERIC, "ASSIGN_DATA_AGENT_CONT_REVIEW(?,?,?,?,?,?,?)",
                                                                                new Object[] {ass_dates,area_ids,driv_ids,pilot_ids,agent_ids, ADFContext.getCurrent().getSecurityContext().getUserName(), delivery_types});
           // ADFUtils.findOperation("Execute").execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("t5"));

            JSFUtils.addFacesInformationMessage("Updates Submitted Successfully");
            }
            else {
                JSFUtils.addFacesErrorMessage("No Updates To Submit");
            }
        }
    }

    public void RescheduleDeliveryChanged(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        System.err.println(valueChangeEvent.getNewValue());
       // ADFUtils.findIterator("DataTeamLeaderPendingWorkVO1Iterator").getViewObject().getCurrentRow().setAttribute("RescheduleDelivery", valueChangeEvent.getNewValue());
      ViewObject vo=ADFUtils.findIterator("ResceduleDeliveryROV1Iterator").getViewObject();
      RowSetIterator rs=vo.createRowSetIterator(null);
      int index=1;
      while(rs.hasNext()) {
          System.err.println("index"+index);
          Row r=rs.next();
          System.err.println(r.getAttribute("Id"));
          if(index==Integer.parseInt(valueChangeEvent.getNewValue().toString())) {
              System.err.println("matched"+r.getAttribute("Id"));
              ADFUtils.findIterator("DataTeamLeaderPendingWorkVO1Iterator").getViewObject().getCurrentRow().setAttribute("RescheduleDelivery", r.getAttribute("Id"));
              System.err.println("Current Id"+ADFUtils.findIterator("DataTeamLeaderPendingWorkVO1Iterator").getViewObject().getCurrentRow().getAttribute("RescheduleDelivery"));
              break;

          }
          index++;
      }
        FacesContext.getCurrentInstance().renderResponse();
        AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("soc7"));

        
    }
}
