package MTS.security.MTSClasses;

import OPERATIONPROJECT.model.BC.AM.AppModuleAMImpl;

import aj.org.objectweb.asm.TypeReference;

import java.io.IOException;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;
import java.security.KeyManagementException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.input.RichSelectManyChoice;

import oracle.adf.view.rich.component.rich.input.RichSelectManyListbox;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupFetchEvent;

import oracle.binding.OperationBinding;

import oracle.jbo.Key;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;


public class SupervisorBean {

    public SupervisorBean() {
        super();
    }
/******************************************* Bulk Assign Part ***************************************************************************************************************/
    
       public void SubmitBulkAssignDrivers()
    {
        ViewObject vo = ADFUtils.findIterator("AgentAreasSupervisorIterator").getViewObject();
        StringBuilder AreasStrb = new StringBuilder("");
        String AreasStr = "";
        String AgentName = ADFContext.getCurrent().getSecurityContext().getUserName();
        String sms_content="";
        Row row = null;
        vo.reset();
        RowSetIterator rs = vo.createRowSetIterator(null);
        rs.reset();
        String driver_id=(String)ADFUtils.getBoundAttributeValue("DriverUserName");
       // String Driver_name=(String)ADFUtils.getBoundAttributeValue("FullNameEn");
        System.err.println("Driver : "+driver_id);
        while (rs.hasNext()) {
            row = rs.next();
            System.err.println("ID:"+row.getAttribute("Id"));
                if (row.getAttribute("AreaFlag")!=null && row.getAttribute("AreaFlag").equals(true))
                {
                        Object ID = row.getAttribute("Id");
                     //   row.setAttribute("AssignedDrivers", driver_id);
                       // row.setAttribute("DriversName", Driver_name);
                     //   row.setAttribute("StatusName", "Drivers Assigned");

                     //   row.setAttribute("AreaFlag", false);

System.err.println("ID:"+ID);
                        AreasStrb.append(ID).append(","); 
                    }
        }
        rs.closeRowSetIterator();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
           LocalDateTime now = LocalDateTime.now();  
           System.out.println(dtf.format(now)); 
        if(AreasStrb.length()>0)
        {
        AreasStr = AreasStrb.deleteCharAt(AreasStrb.length() - 1).toString();
        // sms_content=sms_content.
        System.err.println("AreasStr "+AreasStr);
            ViewObject sp_vo = ADFUtils.findIterator("SpareVO1Iterator").getViewObject();
            RowSetIterator sp_rs = sp_vo.createRowSetIterator(null);
            sp_rs.reset();
            Row r=null;
            String spare_id="";
            while(sp_rs.hasNext()) {
                r=sp_rs.next();
                if (r.getAttribute("SpareFlag")!=null && r.getAttribute("SpareFlag").equals(true)){
              spare_id=r.getAttribute("UserName").toString();
         break;
                }
            }
System.err.println("Spare"+spare_id);
System.err.println("ids"+AreasStr);
System.err.println("driver"+driver_id);
            now = LocalDateTime.now();  
                       System.out.println(dtf.format(now)); 
        ADFUtils.callStoredFunction((AppModuleAMImpl)ADFUtils.getApplicationModuleForDataControl("AppModuleAMDataControl"),
                                   Types.NUMERIC, "BULK_ASSIGN_DRIVERS(?,?,?,?)",
                                                                      new Object[] {AreasStr, ADFContext.getCurrent().getSecurityContext().getUserName(),driver_id,spare_id});
    
            now = LocalDateTime.now();  
                       System.out.println(dtf.format(now)); 
          //  ADFUtils.findOperation("Execute").execute();
           // ADFUtils.findOperation("Execute4").execute();
               ViewObject notsplitted=ADFUtils.findIterator("AgentAreasSupervisorIterator").getViewObject();        
            notsplitted.setQueryMode(ViewObject.QUERY_MODE_SCAN_DATABASE_TABLES);
            notsplitted.executeQuery();
            ViewObject spare_vo=ADFUtils.findIterator("SpareVO1Iterator").getViewObject();
            spare_vo.setQueryMode(ViewObject.QUERY_MODE_SCAN_DATABASE_TABLES);
            spare_vo.executeQuery();
            now = LocalDateTime.now();  
                       System.out.println(dtf.format(now)); 
        //    AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("P2"));
            now = LocalDateTime.now();  
                       System.out.println(dtf.format(now)); 
        JSFUtils.addFacesInformationMessage("Drivers Assigned Successfully");
        }
        else {
            JSFUtils.addFacesErrorMessage("No Areas Selected For Assign");
        }

    } 
    
    public void ConfirmBulkAssignSpare(DialogEvent dialogEvent) {
        // Add event code here...
        if(dialogEvent.getOutcome().equals(dialogEvent.getOutcome().yes))
        {
        JSFUtils.showPopup("p22");
        }
        else{
            SubmitBulkAssignDrivers();
        }
    }

    public void SubmitConfirmBulkSpare(DialogEvent dialogEvent) {
        // Add event code here...
        JSFUtils.showPopup("p23");
    }

    public void LastSubmitBulkAssign(DialogEvent dialogEvent) {
        // Add event code here...
        if(dialogEvent.getOutcome().equals(dialogEvent.getOutcome().yes)) {
            SubmitBulkAssignDrivers();

        }
        else {
        }
    }    
    /******************************************************************************************************************************************************************************/
    /************************************* Reassign Pilot ********************************************************************************/
  
    public void ConfirmSubmitReassign(ActionEvent actionEvent) {
        // Add event code here...
        JSFUtils.showPopup("p9");
        
    }
    public void CallReassign() {
        Long Id = (Long) ADFUtils.getBoundAttributeValue("Id");
        StringBuilder DriversStrb = new StringBuilder("");
        String old_pilot_id_val=ADFUtils.getBoundAttributeValue("PilotId").toString();
        String new_pilot_id_val=ADFUtils.getBoundAttributeValue("NPilotId2").toString();
        System.err.println("Old Pilot :"+old_pilot_id_val);
        System.err.println("New Pilot :"+new_pilot_id_val);
        String DriversStr = "";
        ViewObject vo = ADFUtils.findIterator("AssignmentSummaryVO1Iterator").getViewObject();
        Row row = null;
        vo.reset();
        RowSetIterator rs = vo.createRowSetIterator(null);
        rs.reset();
        while (rs.hasNext()) {
            row = rs.next();
            if (row.getAttribute("SelectAss")!=null && row.getAttribute("SelectAss").equals(true)){
                DriversStrb.append(row.getAttribute("AssignmentId")).append(","); 
            }
        }
        rs.closeRowSetIterator();
        if(DriversStrb.length()!=0){
                DriversStr = DriversStrb.deleteCharAt(DriversStrb.length() - 1).toString();
            
        //System.err.println("Id " + Id);
        System.err.println("Next Delivery Date : "+ADFUtils.getBoundAttributeValue("NewDeliveryDate"));
        System.err.println("NewDriverId:"+ADFUtils.getBoundAttributeValue("DriverId2"));
        System.err.println("NewPilotId : "+ADFUtils.getBoundAttributeValue("NPilotId2"));
        System.err.println("DriversStr" + DriversStr);
        System.err.println("UserName " + ADFContext.getCurrent().getSecurityContext().getUserName());
        ADFUtils.callStoredFunction((AppModuleAMImpl)ADFUtils.getApplicationModuleForDataControl("AppModuleAMDataControl"),
                                                                    Types.NUMERIC, "REASSIGN_CONTRACTS(?,?,?,?)",
                                                                    new Object[] {DriversStr, ADFUtils.getBoundAttributeValue("NewDeliveryDate") , ADFUtils.getBoundAttributeValue("NewDriverId") , ADFUtils.getBoundAttributeValue("NPilotId2") });
        
        ADFUtils.findOperation("Execute3").execute();
        
        JSFUtils.hidePopup("p8");
        AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("t5"));
        JSFUtils.addFacesInformationMessage("Contracts Reassigned Successfully");
        }
        else {
        JSFUtils.addFacesErrorMessage("You Should Select at least one Contract");
          //  ADF.addFacesErrorMessage("You can't Submit without selecting at least one driver");
        }
       
    }   
    public void SubmitReassign(DialogEvent dialogEvent) {
        // Add event code here...
        if(dialogEvent.getOutcome().equals(dialogEvent.getOutcome().yes)) {
            CallReassign();
        }
    }
    /***************************************** Details View For Splitted ****************************************************************************************/
    /*************************************1. Assign Drivers ***************************************************************************************************************/
    
    public void fetchSelectDriversPopupDet(PopupFetchEvent popupFetchEvent) {
        // Add event code here...
        String AssignedDriversStr = (String) ADFUtils.getBoundAttributeValue("AssignedDrivers1");
        System.err.println("AssignedDriversStr " + AssignedDriversStr);
      System.err.println("Streets "+ADFUtils.getBoundAttributeValue("AssignedStreets"));
       /* JSONPar jsonParser;
        JSONObject json_object=(JSONObject)jsonParser.parse(ADFUtils.getBoundAttributeValue("AssignedStreets").toString());
      */ 
     // List<DailyAssignedStreet> streets=new ObjectMapper().readValue(ADFUtils.getBoundAttributeValue("AssignedStreets").toString(), new TypeReference<List<DailyAssignedStreet>>() {});
    /* DailyAssignedStreet[] streets;
        try {
            streets =
                new ObjectMapper()
                .readValue(ADFUtils.getBoundAttributeValue("AssignedStreets").toString(), DailyAssignedStreet[].class);
            System.err.println("Length"+streets.length);

        } catch (JsonMappingException | JsonParseException e) {
        } catch (IOException e) {
        }
    */

      if(AssignedDriversStr != null && !AssignedDriversStr.trim().isEmpty()){
        List shuttleValuesList = new ArrayList();
         String[] parts = AssignedDriversStr.split(",");
        for (String part : parts) {
            System.err.println(part);
            shuttleValuesList.add(part);
        }
        ViewObject vo = ADFUtils.findIterator("UsersROVIterator").getViewObject();
        vo.setQueryMode(ViewObject.QUERY_MODE_SCAN_DATABASE_TABLES);
        vo.executeQuery();
        Row row = null;
        vo.reset();
        RowSetIterator rs = vo.createRowSetIterator(null);
        rs.reset();
        while (rs.hasNext()) {
            row = rs.next();
            if (shuttleValuesList.contains(row.getAttribute("UserName").toString())){
                row.setAttribute("selectFlag", true);
                break;
            }
        }
        rs.closeRowSetIterator();
    }
    }
    public void selectDriversDialLisDet() {
        // Add event code here...
        
            Long Id = (Long) ADFUtils.getBoundAttributeValue("Id1");
            StringBuilder DriversStrb = new StringBuilder("");
            String DriversStr = "";
            ViewObject vo = ADFUtils.findIterator("UsersROVIterator").getViewObject();
            Row row = null;
            vo.reset();
            RowSetIterator rs = vo.createRowSetIterator(null);
            rs.reset();
            while (rs.hasNext()) {
                row = rs.next();
                if (row.getAttribute("selectFlag")!=null && row.getAttribute("selectFlag").equals(true)){
                    DriversStrb.append(row.getAttribute("UserName")).append(","); 
                }
            }
            rs.closeRowSetIterator();
            if(DriversStrb.length()!=0){
                    DriversStr = DriversStrb.deleteCharAt(DriversStrb.length() - 1).toString();
                
            System.err.println("Id " + Id);
            System.err.println("DriversStr" + DriversStr);
            System.err.println("UserName " + ADFContext.getCurrent().getSecurityContext().getUserName());
            ADFUtils.callStoredFunction((AppModuleAMImpl)ADFUtils.getApplicationModuleForDataControl("AppModuleAMDataControl"),
                                                                        Types.NUMERIC, "ASSIGN_DRIVERSTO_AREA(?,?,?)",
                                                                        new Object[] {Id , DriversStr , ADFContext.getCurrent().getSecurityContext().getUserName()});
        
            //ADFUtils.findOperation("Execute1").execute();
          
                Key key1 = ADFUtils.findIterator("SupervisorAreaMasterVO2Iterator")
                                  .getCurrentRow()
                                  .getKey();
                System.err.println("key : "+key1);
                DCIteratorBinding iter2 = ADFUtils.findIterator("SupervisorAreasDetailsVO1Iterator");
                Key key2 = ADFUtils.findIterator("SupervisorAreasDetailsVO1Iterator")
                                  .getCurrentRow()
                                  .getKey();
            ViewObject master_areas=ADFUtils.findIterator("SupervisorAreaMasterVO2Iterator").getViewObject();
            master_areas.setQueryMode(ViewObject.QUERY_MODE_SCAN_DATABASE_TABLES);
            master_areas.executeQuery();
              //  ADFUtils.findOperation("Execute2").execute();
            ViewObject details_areas=ADFUtils.findIterator("SupervisorAreasDetailsVO1Iterator").getViewObject();
            details_areas.setQueryMode(ViewObject.QUERY_MODE_SCAN_DATABASE_TABLES);
            details_areas.executeQuery();
                DCIteratorBinding iter_master=ADFUtils.findIterator("SupervisorAreaMasterVO2Iterator");
                DCIteratorBinding iter_det=ADFUtils.findIterator("SupervisorAreasDetailsVO1Iterator");
                iter_master.setCurrentRowWithKey(key1.toStringFormat(true));
                iter_det.setCurrentRowWithKey(key2.toStringFormat(true));
           // JSFUtils.addFacesInformationMessage("Drivers Assigned Successfully for this area");
            }
            else {
            JSFUtils.addFacesErrorMessage("You Should Select at least one driver");
              //  ADF.addFacesErrorMessage("You can't Submit without selecting at least one driver");
            }
        
    }  
/*********************************** Assign Spare ***************************************************************/
    public void fetchSelectedSparesDet(PopupFetchEvent popupFetchEvent) {
        String AssignedDriversStr = (String) ADFUtils.getBoundAttributeValue("AssignedSpares1");
        System.err.println("AssignedDriversStr " + AssignedDriversStr);
        if(AssignedDriversStr != null && !AssignedDriversStr.trim().isEmpty()){
        List shuttleValuesList = new ArrayList();
         String[] parts = AssignedDriversStr.split(",");
        for (String part : parts) {
            System.err.println(part);
            shuttleValuesList.add(part);
        }
        ViewObject vo = ADFUtils.findIterator("SpareVO1Iterator").getViewObject();
        vo.setQueryMode(ViewObject.QUERY_MODE_SCAN_DATABASE_TABLES);
        vo.executeQuery();
        Row row = null;
        vo.reset();
        RowSetIterator rs = vo.createRowSetIterator(null);
        rs.reset();
        while (rs.hasNext()) {
            row = rs.next();
            if (shuttleValuesList.contains(row.getAttribute("UserName").toString())){
                row.setAttribute("SpareFlag", true);
                break;
            }
        }
        rs.closeRowSetIterator();
        }
    }  

   

   
   
    public void ConfirmAssignSpareDetails(DialogEvent dialogEvent) {
        // Add event code here...
        JSFUtils.showPopup("p13");
    }

    public void ConfirmAssignSpareDetails1(DialogEvent dialogEvent) {
        // Add event code here...
        if(dialogEvent.getOutcome().equals(dialogEvent.getOutcome().yes)) {
                 JSFUtils.showPopup("p14");

             }
        else {
            selectDriversDialLisDet();
            AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("t8"));
                AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("t9"));
                AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("p17"));
                AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("p14"));
                AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("p12"));

            JSFUtils.addFacesInformationMessage("Drivers Assigned Successfully");
            
            
        }
    }
   

    public void ConfirmSubmitSpareDetail(DialogEvent dialogEvent) {
        // Add event code here...
        JSFUtils.showPopup("p15");
    }

    public void AcceptRejectSubmitSpare(DialogEvent dialogEvent) {
        // Add event code here...
        if(dialogEvent.getOutcome().equals(dialogEvent.getOutcome().yes)) {
            selectDriversDialLisDet();
            SubmitAssignSpareDet(0);
            
        }
    }
    public void SubmitAssignSpareDet(int confirmed) {
        
            Long Id = (Long) ADFUtils.getBoundAttributeValue("Id1");
            StringBuilder DriversStrb = new StringBuilder("");
            String DriversStr = "";
            ViewObject vo = ADFUtils.findIterator("SpareVO1Iterator").getViewObject();
            Row row = null;
            vo.reset();
            RowSetIterator rs = vo.createRowSetIterator(null);
            rs.reset();
            while (rs.hasNext()) {
                row = rs.next();
                if (row.getAttribute("SpareFlag")!=null && row.getAttribute("SpareFlag").equals(true)){
                    DriversStrb.append(row.getAttribute("UserName")); 
                    break;
                }
            }
            rs.closeRowSetIterator();
            if(DriversStrb.length()!=0){
                   DriversStr = DriversStrb.toString();
                
            System.err.println("Id " + Id);
            System.err.println("Working Date : "+ADFUtils.getBoundAttributeValue("WorkingDate"));
            System.err.println("Cover date :"+ADFUtils.getBoundAttributeValue("CoverDate2"));
            System.err.println("AreaId"+ADFUtils.getBoundAttributeValue("AreaId1"));
            System.err.println("DriverId:"+ADFUtils.getBoundAttributeValue("DriverId1"));
            System.err.println("DriversStr" + DriversStr);
            System.err.println("UserName " + ADFContext.getCurrent().getSecurityContext().getUserName());
           String Out_Submit=(String) ADFUtils.callStoredFunction((AppModuleAMImpl)ADFUtils.getApplicationModuleForDataControl("AppModuleAMDataControl"),
                                                                        Types.VARCHAR, "ASSIGN_SPARE_TO_DRIVER(?,?,?,?,?,?,?,?)",
                                                                        new Object[] {ADFUtils.getBoundAttributeValue("WorkingDate"),ADFUtils.getBoundAttributeValue("CoverDate"),ADFUtils.getBoundAttributeValue("AreaId"),ADFUtils.getBoundAttributeValue("DriverId"),DriversStr ,confirmed, ADFContext.getCurrent().getSecurityContext().getUserName(),Id});
             //   ADFUtils.findOperation("Execute1").execute();
             //   ADFUtils.findOperation("Execute2").execute();
           
          System.err.println("Out: "+Out_Submit);
           // ADFUtils.findOperation("Execute").execute();
            if(Out_Submit.equals("Success"))
            {
                Key key1 = ADFUtils.findIterator("SupervisorAreaMasterVO2Iterator")
                                  .getCurrentRow()
                                  .getKey();
                System.err.println("key : "+key1);
                DCIteratorBinding iter2 = ADFUtils.findIterator("SupervisorAreasDetailsVO1Iterator");
                Key key2 = ADFUtils.findIterator("SupervisorAreasDetailsVO1Iterator")
                                  .getCurrentRow()
                                  .getKey();
                ViewObject master_areas=ADFUtils.findIterator("SupervisorAreaMasterVO2Iterator").getViewObject();
                master_areas.setQueryMode(ViewObject.QUERY_MODE_SCAN_DATABASE_TABLES);
                master_areas.executeQuery();
                //  ADFUtils.findOperation("Execute2").execute();
                ViewObject details_areas=ADFUtils.findIterator("SupervisorAreasDetailsVO1Iterator").getViewObject();
                details_areas.setQueryMode(ViewObject.QUERY_MODE_SCAN_DATABASE_TABLES);
                details_areas.executeQuery();
                DCIteratorBinding iter_master=ADFUtils.findIterator("SupervisorAreaMasterVO2Iterator");
                DCIteratorBinding iter_det=ADFUtils.findIterator("SupervisorAreasDetailsVO1Iterator");
                iter_master.setCurrentRowWithKey(key1.toStringFormat(true));
                iter_det.setCurrentRowWithKey(key2.toStringFormat(true));
                
                AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("t8"));
                    AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("t9"));
                    AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("p17"));
                    AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("p14"));
                    AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("p12"));

            JSFUtils.addFacesInformationMessage("Spares Assigned Successfully for this area");
            }
            else {
                System.err.println("Spare");
                JSFUtils.showPopup("p7");
            }
            }
            else {
            JSFUtils.addFacesErrorMessage("You Should Select at least one Spare");
              //  ADF.addFacesErrorMessage("You can't Submit without selecting at least one driver");
            }
        
    }

    public void SubmitSpare2Det(DialogEvent dialogEvent) {
        // Add event code here...
        if(dialogEvent.getOutcome().equals(dialogEvent.getOutcome().yes)) {
            SubmitAssignSpareDet(2);
         /*   DCIteratorBinding iter1 = ADFUtils.findIterator("SupervisorAreaMasterVO2Iterator");
                   Key key1 = ADFUtils.findIterator("SupervisorAreaMasterVO2Iterator")
                                     .getCurrentRow()
                                     .getKey();
            System.err.println("key : "+key1);*/
            DCIteratorBinding iter2 = ADFUtils.findIterator("SupervisorAreasDetailsVO1Iterator");
                   Key key2 = ADFUtils.findIterator("SupervisorAreasDetailsVO1Iterator")
                                     .getCurrentRow()
                                     .getKey();
            System.err.println("key : "+key2);

ViewObject details=ADFUtils.findIterator("SupervisorAreasDetailsVO1Iterator").getViewObject();
details.setQueryMode(ViewObject.QUERY_MODE_SCAN_DATABASE_TABLES);
details.executeQuery();
iter2=ADFUtils.findIterator("SupervisorAreasDetailsVO1Iterator");
iter2.setCurrentRowWithKey(key2.toStringFormat(true));
          //  ADFUtils.findOperation("Execute1").execute();
           // ADFUtils.findOperation("Execute2").execute();
            //iter1.setCurrentRowWithKey(key1.toStringFormat(true));
           // iter2.setCurrentRowWithKey(key2.toStringFormat(true));
            AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("t8"));
            AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("t9"));
            AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("p17"));
            AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("p14"));
            AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("p12"));

        }
    }

    /**************************3. Assign Street *****************************************************************/
  
  
    public void ChangeSelectedCount(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        Row currentRow = ADFUtils.findIterator("DriversStreetVO1Iterator")
                                 .getViewObject()
                                 .getCurrentRow();
        System.err.println("Flag : "+currentRow.getAttribute("TotalContracts"));
        Long alreadySelected=0L;
        System.err.println("Total Contracts Started With :"+alreadySelected);
        if(ADFUtils.getBoundAttributeValue("TotalSelectedContracts")!=null)
        {
                alreadySelected=(Long)ADFUtils.getBoundAttributeValue("TotalSelectedContracts");
            }
           boolean isSelected =((Boolean)valueChangeEvent.getNewValue()).booleanValue(); 
           if( isSelected==true) {
               System.err.println("Current Sslected : "+ADFUtils.getBoundAttributeValue("TotalContracts1"));
               alreadySelected+=(Long)ADFUtils.getBoundAttributeValue("TotalContracts1");
           }
        System.err.println("Selected Contracts"+alreadySelected);
        ADFUtils.setBoundAttributeValue("TotalSelectedContracts", alreadySelected);
    }
    public void fetchStreetIdsDet(PopupFetchEvent popupFetchEvent) {
       /* String AssignedDriversStr = (String) ADFUtils.getBoundAttributeValue("AssignedStreets");
        System.err.println("AssignedDriversStr " + AssignedDriversStr);
        if(ADFUtils.getBoundAttributeValue("AssignedStreets") != null ){
       */ List shuttleValuesList = new ArrayList();
        Long selected_count=0L;
         /*String[] parts = AssignedDriversStr.split(",");
        for (String part : parts) {
            System.err.println(part);
            shuttleValuesList.add(part);
        }*/
        ViewObject vo = ADFUtils.findIterator("DriversStreetVO2Iterator").getViewObject();
        vo.executeQuery();
        Row row = null;
        vo.reset();
        RowSetIterator rs = vo.createRowSetIterator(null);
        rs.reset();
            System.err.println("Record_id"+ADFUtils.getBoundAttributeValue("Id1"));
        while (rs.hasNext()) {
            row = rs.next();
            System.err.println("ID"+row.getAttribute("Id"));
            if(((Long)row.getAttribute("Id")==null|| row.getAttribute("Id")==null)){
                row.setAttribute("DisabledVal", 0);

            }
            else if ((Long)row.getAttribute("Id")==(Long)ADFUtils.getBoundAttributeValue("Id1")|| ((Long)row.getAttribute("Id")).equals(ADFUtils.getBoundAttributeValue("Id1"))){
                System.err.println("Same");
                 row.setAttribute("AssignedFlag", true);
                 selected_count+=(Long)row.getAttribute("TotalContracts");
                row.setAttribute("DisabledVal", 0);

            }
            else {
                System.err.println("Disabled");
                row.setAttribute("DisabledVal", 1);
            }
        }
        ADFUtils.setBoundAttributeValue("TotalSelectedDet", selected_count);
        rs.closeRowSetIterator();
        
    }
    
    public void ChangeSelectAllStreetsDet(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        boolean isSelected=((Boolean)valueChangeEvent.getNewValue()).booleanValue();
        ViewObject vo = ADFUtils.findIterator("DriversStreetVO2Iterator").getViewObject();
        Row row=null;
        vo.reset();
        RowSetIterator rs=vo.createRowSetIterator(null);
        rs.reset();
        Long Assigned_count=0L;
        Long Available_count=0L;
        Long Available_street=0L;
         /* if(ADFUtils.getBoundAttributeValue("TotalSelectedDet")!=null)
        Assigned_count=(Long)ADFUtils.getBoundAttributeValue("TotalSelectedDet");
      */
      if(isSelected) {
          Available_count=0L;
          Available_street=0L;
      }
      else {
          Available_count=(Long)ADFUtils.getBoundAttributeValue("AvailableContracts");
          Available_street=(Long)ADFUtils.getBoundAttributeValue("AvailableStreets");
          Assigned_count=0L;
      }
      while(rs.hasNext()) {
            row=rs.next();
            if(isSelected) {
                //System.err.println("Flag"+);
                if((Long)row.getAttribute("DisabledVal")==0||((Long)row.getAttribute("DisabledVal")).equals("0")||(Long)ADFUtils.getBoundAttributeValue("Id1")==(Long)row.getAttribute("Id") || row.getAttribute("Id")==null)
                {
                row.setAttribute("AssignedFlag", isSelected);
                Assigned_count+=(Long)row.getAttribute("TotalContracts");
               //     Available_count-=(Long)row.getAttribute("TotalContracts");
               //     Available_street--;
                }
               
            }
            else {
                if((Long)row.getAttribute("DisabledVal")==0||((Long)row.getAttribute("DisabledVal")).equals("0")||(Long)ADFUtils.getBoundAttributeValue("Id1")==(Long)row.getAttribute("Id") || row.getAttribute("Id")==null)
                {
                row.setAttribute("AssignedFlag", false);
                Assigned_count-=(Long)row.getAttribute("TotalContracts");
                    Available_count+=(Long)row.getAttribute("TotalContracts");
                    Available_street++;
                }
                //row.setAttribute("AssignedFlag", );
                Assigned_count=0L;
            }
            
            
        }
        ADFUtils.setBoundAttributeValue("TotalSelectedDet", Assigned_count);
        ADFUtils.setBoundAttributeValue("AvailableContracts", Available_count);
        ADFUtils.setBoundAttributeValue("AvailableStreets",Available_street);
    }

    public void ChangeSelectCountDet(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        Row currentRow = ADFUtils.findIterator("DriversStreetVO2Iterator")
                                 .getViewObject()
                                 .getCurrentRow();
        System.err.println("row id"+currentRow.getKey());
        System.err.println("Current total : "+ADFUtils.getBoundAttributeValue("TotalContracts3"));
        System.err.println("Flag : "+currentRow.getAttribute("TotalContracts"));
        Long alreadySelected=0L;
        Long Available_count=0L;
        Long Available_street=0L;
        if(ADFUtils.getBoundAttributeValue("AvailableContracts")!=null)
        Available_count=(Long)ADFUtils.getBoundAttributeValue("AvailableContracts");
        if(ADFUtils.getBoundAttributeValue("AvailableStreets")!=null)
        Available_street=(Long)ADFUtils.getBoundAttributeValue("AvailableStreets");
        System.err.println("Total Contracts Started With :"+alreadySelected);
        if(ADFUtils.getBoundAttributeValue("TotalSelectedDet")!=null)
        {
                alreadySelected=(Long)ADFUtils.getBoundAttributeValue("TotalSelectedDet");
            }
           boolean isSelected =((Boolean)valueChangeEvent.getNewValue()).booleanValue(); 
           if( isSelected==true) {
               System.err.println("Count"+ADFUtils.getBoundAttributeValue("TotalContracts4"));

               System.err.println("Current Sslected : "+ADFUtils.getBoundAttributeValue("TotalContracts3"));
               alreadySelected+=(Long)ADFUtils.getBoundAttributeValue("TotalContracts3");
           Available_street--;
           Available_count-=(Long)ADFUtils.getBoundAttributeValue("TotalContracts3");
           }
           else {
               System.err.println("Count"+ADFUtils.getBoundAttributeValue("TotalContracts4"));

               System.err.println("Current Sslected : "+ADFUtils.getBoundAttributeValue("TotalContracts3"));
               alreadySelected-=(Long)ADFUtils.getBoundAttributeValue("TotalContracts3");
               Available_street++;
               Available_count+=(Long)ADFUtils.getBoundAttributeValue("TotalContracts3");

               
           }
        System.err.println("Selected Contracts"+alreadySelected);
        ADFUtils.setBoundAttributeValue("TotalSelectedDet", alreadySelected);
        ADFUtils.setBoundAttributeValue("AvailableContracts", Available_count);
        ADFUtils.setBoundAttributeValue("AvailableStreets", Available_street);
    }
    
    public void ConfirmSelectStreetDet(DialogEvent dialogEvent) {
        // Add event code here...
        JSFUtils.showPopup("p19");
    }

    public void SubmitConfirmAssignStreetDet(DialogEvent dialogEvent) {
        // Add event code here...
        if(dialogEvent.getOutcome().equals(dialogEvent.getOutcome().yes)) {
            JSONArray all_streets=new JSONArray();
            JSONArray not_Selected=new JSONArray();
                Long Id = (Long) ADFUtils.getBoundAttributeValue("Id1");
                StringBuilder DriversStrb = new StringBuilder("");
                String DriversStr = "";
                ViewObject vo = ADFUtils.findIterator("DriversStreetVO2Iterator").getViewObject();
                
                Row row = null;
                vo.reset();
                RowSetIterator rs = vo.createRowSetIterator(null);
                rs.reset();
                int count_select=0;
                while (rs.hasNext()) {
                    row = rs.next();
                    System.err.println("Flag:"+row.getAttribute("AssignedFlag"));
                    if (row.getAttribute("AssignedFlag")!=null && row.getAttribute("AssignedFlag").equals(true)){
                       JSONObject obj=new JSONObject();
                        count_select++;
                        try {
                            obj.put("city", row.getAttribute("CityId"));
                            obj.put("region", row.getAttribute("RegionId"));
                            obj.put("address", row.getAttribute("AddressArea"));
                            obj.put("street", row.getAttribute("StreetId"));
                            System.err.println("Object :"+obj);
                             all_streets.put(obj);
                        } catch (JSONException e) {
                        }


                 //       DriversStrb.append(row.getAttribute("StreetId")).append(","); 
                    }
                    else{
                        JSONObject excluded=new JSONObject();
                    try {
                        excluded.put("city", row.getAttribute("CityId"));
                        excluded.put("region", row.getAttribute("RegionId"));
                            excluded.put("address", row.getAttribute("AddressArea"));
                            excluded.put("street", row.getAttribute("StreetId"));
                            System.err.println("Object :"+excluded);
                        not_Selected.put(excluded);
                    } catch (JSONException e) {
                    }
                 
                    }
                }
                rs.closeRowSetIterator();
                if(count_select>0){
                      //  DriversStr = DriversStrb.deleteCharAt(DriversStrb.length() - 1).toString();
                    
                System.err.println("Id " + Id);
                System.err.println("DriversStr" + all_streets);
                System.err.println("UserName " + ADFContext.getCurrent().getSecurityContext().getUserName());
                ADFUtils.callStoredFunction((AppModuleAMImpl)ADFUtils.getApplicationModuleForDataControl("AppModuleAMDataControl"),
                                                                            Types.NUMERIC, "ASSIGN_DRIVERSTO_STREETS(?,?,?,?)",
                                                                            new Object[] {Id , all_streets.toString() , ADFContext.getCurrent().getSecurityContext().getUserName() , not_Selected.toString()});
                    DCIteratorBinding iter1 = ADFUtils.findIterator("SupervisorAreaMasterVO2Iterator");
                           Key key1 = ADFUtils.findIterator("SupervisorAreaMasterVO2Iterator")
                                             .getCurrentRow()
                                             .getKey();
System.err.println("key : "+key1);
                    DCIteratorBinding iter2 = ADFUtils.findIterator("SupervisorAreasDetailsVO1Iterator");
                           Key key2 = ADFUtils.findIterator("SupervisorAreasDetailsVO1Iterator")
                                             .getCurrentRow()
                                             .getKey();
                    System.err.println("key : "+key2);
ViewObject Master=ADFUtils.findIterator("SupervisorAreaMasterVO2Iterator").getViewObject();
Master.setQueryMode(ViewObject.QUERY_MODE_SCAN_DATABASE_TABLES);
Master.executeQuery();

               // ADFUtils.findOperation("Execute1").execute();
                   // ADFUtils.findOperation("Execute2").execute();
                    iter1.setCurrentRowWithKey(key1.toStringFormat(true));
                    ViewObject details=ADFUtils.findIterator("SupervisorAreasDetailsVO1Iterator").getViewObject();
                    details.setQueryMode(ViewObject.QUERY_MODE_SCAN_DATABASE_TABLES);
                    details.executeQuery();
                    
                    iter2.setCurrentRowWithKey(key2.toStringFormat(true));
                AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("t8"));
                    AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("t9"));
                    AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("p17"));
                    AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("p14"));
                    AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("p12"));

                JSFUtils.addFacesInformationMessage("Drivers Assigned Successfully for Selected Streets");
                }
                else {
                    AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("t9"));
                    AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("p17"));
                    AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("p14"));
                    AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("p12"));

                JSFUtils.addFacesErrorMessage("You Should Select at least one Street");
                  //  ADF.addFacesErrorMessage("You can't Submit without selecting at least one driver");
                }
            
        }
    }
/************************************* Assign Pilots *************************************************************************************************************************/
    public void ConfirmAssignPilotDetails(DialogEvent dialogEvent) {
        // Add event code here...
        if(dialogEvent.getOutcome().equals(dialogEvent.getOutcome().yes)) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
               LocalDateTime now = LocalDateTime.now();  
               System.out.println(dtf.format(now)); 
            Long id=(Long) ADFUtils.getBoundAttributeValue("Id1");
            System.err.println("Id:" +id);
            String username=ADFContext.getCurrent().getSecurityContext().getUserName();
            System.err.println("User : "+username);
            
              ADFUtils.callStoredFunction((AppModuleAMImpl)ADFUtils.getApplicationModuleForDataControl("AppModuleAMDataControl"),
                                                                          Types.NUMERIC, "ASSIGN_PILOTS(?,?)",
                                                                          new Object[] {id , ADFContext.getCurrent().getSecurityContext().getUserName()});
            now = LocalDateTime.now();  
                           System.out.println("Function Done"+dtf.format(now)); 
          /* ADFUtils.findOperation("Execute1") ;
            now = LocalDateTime.now();  
                           System.out.println(dtf.format(now)); 
            DCIteratorBinding iter1 = ADFUtils.findIterator("SupervisorAreaMasterVO2Iterator");
                   Key key1 = ADFUtils.findIterator("SupervisorAreaMasterVO2Iterator")
                                     .getCurrentRow()
                                     .getKey();
            System.err.println("key : "+key1);
            now = LocalDateTime.now();  
                           System.out.println(dtf.format(now)); */
            DCIteratorBinding iter2 = ADFUtils.findIterator("SupervisorAreasDetailsVO1Iterator");
                   Key key2 = ADFUtils.findIterator("SupervisorAreasDetailsVO1Iterator")
                                     .getCurrentRow()
                                     .getKey();
            now = LocalDateTime.now();  
                           System.out.println("Get Key"+dtf.format(now)); 
            System.err.println("key : "+key2);
           iter2 = ADFUtils.findIterator("SupervisorAreasDetailsVO1Iterator");
           iter2.setCurrentRowWithKey(key2.toStringFormat(true));

           /* ADFUtils.findOperation("Execute1").execute();
            now = LocalDateTime.now();  
                           System.out.println(dtf.format(now)); 
            ADFUtils.findOperation("Execute2").execute();
            now = LocalDateTime.now();  
                           System.out.println(dtf.format(now)); 
            iter1.setCurrentRowWithKey(key1.toStringFormat(true));
            now = LocalDateTime.now();  
                           System.out.println(dtf.format(now)); 
            iter2.setCurrentRowWithKey(key2.toStringFormat(true));
            now = LocalDateTime.now();  
                           System.out.println(dtf.format(now)); 
*/
                           ViewObject Area_Det=ADFUtils.findIterator("SupervisorAreasDetailsVO1Iterator").getViewObject();
                           Area_Det.setQueryMode(ViewObject.QUERY_MODE_SCAN_DATABASE_TABLES);
                           Area_Det.executeQuery();
            now = LocalDateTime.now();  
                           System.out.println("Query Executed"+dtf.format(now)); 
            AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("t8"));
            AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("t9"));
            AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("p17"));
            AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("p14"));
            AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("p12"));
            now = LocalDateTime.now();  
                           System.out.println(dtf.format(now)); 
            JSFUtils.addFacesInformationMessage("Pilots Assigned Successfully");
            now = LocalDateTime.now();  
                           System.out.println(dtf.format(now)); 
        }
        else{
            AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("t8"));
            AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("t9"));
            AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("p17"));
            AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("p14"));
            AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("p12"));

        }
    }
/************************************************** Dispatch Area T Upload Assignments To DMS ***************************************/
    public void CoonfirmDispatchDetails(DialogEvent dialogEvent) {
        // Add event code here...
        if(dialogEvent.getOutcome().equals(dialogEvent.getOutcome().yes)) {
            Long id=(Long) ADFUtils.getBoundAttributeValue("Id1");
            System.err.println("Id:" +id);
            String username=ADFContext.getCurrent().getSecurityContext().getUserName();
            System.err.println("User : "+username);
            
              ADFUtils.callStoredFunction((AppModuleAMImpl)ADFUtils.getApplicationModuleForDataControl("AppModuleAMDataControl"),
                                                                          Types.NUMERIC, "MARKAREAASREADYFORDISPATCH(?,?)",
                                                                          new Object[] {id , ADFContext.getCurrent().getSecurityContext().getUserName()});
          /*  DCIteratorBinding iter1 = ADFUtils.findIterator("SupervisorAreaMasterVO2Iterator");
                                       Key key1 = ADFUtils.findIterator("SupervisorAreaMasterVO2Iterator")
                                                         .getCurrentRow()
                                                         .getKey();
            System.err.println("key : "+key1);*/
                                DCIteratorBinding iter2 = ADFUtils.findIterator("SupervisorAreasDetailsVO1Iterator");
                                       Key key2 = ADFUtils.findIterator("SupervisorAreasDetailsVO1Iterator")
                                                         .getCurrentRow()
                                                         .getKey();
                                System.err.println("key : "+key2);
                                ViewObject Details_areas=ADFUtils.findIterator("SupervisorAreasDetailsVO1Iterator").getViewObject();
                                Details_areas.setQueryMode(ViewObject.QUERY_MODE_SCAN_DATABASE_TABLES);
                                Details_areas.executeQuery();
                                iter2.setCurrentRowWithKey(key2.toStringFormat(true));

                           /* ADFUtils.findOperation("Execute1").execute();
            ADFUtils.findOperation("Execute2").execute();

                                iter1.setCurrentRowWithKey(key1.toStringFormat(true));
                                iter2.setCurrentRowWithKey(key2.toStringFormat(true));
*/
                            AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("t8"));
                                AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("t9"));
                                AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("p17"));
                                AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("p14"));
                                AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("p12"));

            JSFUtils.addFacesInformationMessage("Area Marked As Ready For Dispatch Successfully ");

        }
        else{
            AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("t9"));
            AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("p17"));
            AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("p14"));
            AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("p12"));

        }
    }

/*******************************************************************************************************************************************************************************/
 // Check If There're Some Streets Not Assigned Display Error Message Else Dispatch Area To Agent To Work On
    public void ConfirmDispatchToAgent(DialogEvent dialogEvent) {
        // Add event code here...
        if(dialogEvent.getOutcome().equals(dialogEvent.getOutcome().yes)) {
            Long Id=(Long)ADFUtils.getBoundAttributeValue("Id2");
            System.err.println("ID"+Id);
            System.err.println("Logged User"+ADFContext.getCurrent().getSecurityContext().getUserName());
           String out_submit=(String)ADFUtils.callStoredFunction((AppModuleAMImpl)ADFUtils.getApplicationModuleForDataControl("AppModuleAMDataControl"),
                                                                        Types.NVARCHAR, "CHECK_ALL_STREETS_ASSIGNED(?,?)",
                                                                        new Object[] {Id , ADFContext.getCurrent().getSecurityContext().getUserName()});
            if(out_submit.equals("Success")) {
                ADFUtils.findOperation("Execute1").execute();
                ADFUtils.findOperation("Execute2").execute();

                JSFUtils.addFacesInformationMessage("Area Dispatched Successfully");
            }
            else{
                JSFUtils.addFacesErrorMessage("You Can't Dispatch Area as You have "+out_submit+" Streets Not Assigned Yet");
                
            }
        }
    }


/****************************************** Master Part For Not Splitted Area *********************************************************************************************************/
    
    /******************************************* 1. Assign Driver **************************************************************************************************************************/
    public void fetchSelectDriversPopup(PopupFetchEvent popupFetchEvent) {
        // Add event code here...
       AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("p1"));
        String AssignedDriversStr = (String) ADFUtils.getBoundAttributeValue("AssignedDrivers");
        System.err.println("AssignedDriversStr " + AssignedDriversStr);
      System.err.println("Streets "+ADFUtils.getBoundAttributeValue("AssignedStreets"));
       
       /* JSONPar jsonParser;
        JSONObject json_object=(JSONObject)jsonParser.parse(ADFUtils.getBoundAttributeValue("AssignedStreets").toString());
      */ 
     // List<DailyAssignedStreet> streets=new ObjectMapper().readValue(ADFUtils.getBoundAttributeValue("AssignedStreets").toString(), new TypeReference<List<DailyAssignedStreet>>() {});
    /* DailyAssignedStreet[] streets;
        try {
            streets =
                new ObjectMapper()
                .readValue(ADFUtils.getBoundAttributeValue("AssignedStreets").toString(), DailyAssignedStreet[].class);
            System.err.println("Length"+streets.length);

        } catch (JsonMappingException | JsonParseException e) {
        } catch (IOException e) {
        }
    */

      if(AssignedDriversStr != null && !AssignedDriversStr.trim().isEmpty()){
        List shuttleValuesList = new ArrayList();
         String[] parts = AssignedDriversStr.split(",");
        for (String part : parts) {
            System.err.println(part);
            shuttleValuesList.add(part);
        }
        ViewObject vo = ADFUtils.findIterator("UsersROVIterator").getViewObject();
        vo.setQueryMode(ViewObject.QUERY_MODE_SCAN_DATABASE_TABLES);
        vo.executeQuery();
        Row row = null;
        vo.reset();
        RowSetIterator rs = vo.createRowSetIterator(null);
        rs.reset();
        while (rs.hasNext()) {
            row = rs.next();
            if (shuttleValuesList.contains(row.getAttribute("UserName").toString())){
                row.setAttribute("selectFlag", true);
            }
        }
        rs.closeRowSetIterator();
    }
    } 
    
    public void selectDriversDialLis() {
        // Add event code here...
        
            Long Id = (Long) ADFUtils.getBoundAttributeValue("Id");
            StringBuilder DriversStrb = new StringBuilder("");
            String DriversStr = "";
            ViewObject vo = ADFUtils.findIterator("UsersROVIterator").getViewObject();
            Row row = null;
            vo.reset();
            RowSetIterator rs = vo.createRowSetIterator(null);
            rs.reset();
            while (rs.hasNext()) {
                row = rs.next();
                if (row.getAttribute("selectFlag")!=null && row.getAttribute("selectFlag").equals(true)){
                    DriversStrb.append(row.getAttribute("UserName")).append(","); 
              break;
                }
            }
            rs.closeRowSetIterator();
            if(DriversStrb.length()!=0){
                    DriversStr = DriversStrb.deleteCharAt(DriversStrb.length() - 1).toString();
                
            System.err.println("Id " + Id);
            System.err.println("DriversStr" + DriversStr);
            System.err.println("UserName " + ADFContext.getCurrent().getSecurityContext().getUserName());
            ADFUtils.callStoredFunction((AppModuleAMImpl)ADFUtils.getApplicationModuleForDataControl("AppModuleAMDataControl"),
                                                                        Types.NUMERIC, "ASSIGN_DRIVERSTO_AREA(?,?,?)",
                                                                        new Object[] {Id , DriversStr , ADFContext.getCurrent().getSecurityContext().getUserName()});
        
            ADFUtils.findOperation("Execute").execute();
           // JSFUtils.addFacesInformationMessage("Drivers Assigned Successfully for this area");
        AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("p1"));
            }
            else {
            JSFUtils.addFacesErrorMessage("You Should Select at least one driver");
              //  ADF.addFacesErrorMessage("You can't Submit without selecting at least one driver");
            }
        
    }
   // First Confirm To Assign Spare or Not 
    public void ConfirmAssignWithoutSpare(DialogEvent dialogEvent) {
        // Add event code here...
        System.err.println("Here");
        JSFUtils.showPopup("p4");
        
    }
  /* If User Select Yes Open New Popup to select spare 
     else Assign Selected Driver */
    public void DriversConfirmation(DialogEvent dialogEvent) {
        // Add event code here...
        if(dialogEvent.getOutcome().equals(dialogEvent.getOutcome().yes)) {
                 JSFUtils.showPopup("p2");

             }
        else {
            selectDriversDialLis();
            JSFUtils.addFacesInformationMessage("Drivers Assigned Successfully");
            
        }
    }
   // Fetch Spares To check if Already Selected Driver Before
   public void fetchSelectedSpares(PopupFetchEvent popupFetchEvent) {
       String AssignedDriversStr = (String) ADFUtils.getBoundAttributeValue("AssignedSpares");
       System.err.println("AssignedDriversStr " + AssignedDriversStr);
       if(AssignedDriversStr != null && !AssignedDriversStr.trim().isEmpty()){
       List shuttleValuesList = new ArrayList();
        String[] parts = AssignedDriversStr.split(",");
       for (String part : parts) {
           System.err.println(part);
           shuttleValuesList.add(part);
       }
       ViewObject vo = ADFUtils.findIterator("SpareVO1Iterator").getViewObject();
       vo.executeQuery();
       Row row = null;
       vo.reset();
       RowSetIterator rs = vo.createRowSetIterator(null);
       rs.reset();
       while (rs.hasNext()) {
           row = rs.next();
           if (shuttleValuesList.contains(row.getAttribute("UserName").toString())){
               row.setAttribute("SpareFlag", true);
           }
       }
       rs.closeRowSetIterator();
       }
   }
      
// Confirm To Submit Selected Spare
        public void ConfirmSpare1(DialogEvent dialogEvent) {
        // Add event code here...
        JSFUtils.showPopup("p6");
    }
// If Selected Yes Assign Driver And Check If Spare Already Selected With Another Driver 
            public void SubmitFirstConfirmation(DialogEvent dialogEvent) {
        // Add event code here...
        if(dialogEvent.getOutcome().equals(dialogEvent.getOutcome().yes)) {
            selectDriversDialLis();
            SubmitAssignSpare(0);
            
        }
        
    }

    public void SubmitAssignSpare(int confirmed) {
        
            Long Id = (Long) ADFUtils.getBoundAttributeValue("Id");
            StringBuilder DriversStrb = new StringBuilder("");
            String DriversStr = "";
            ViewObject vo = ADFUtils.findIterator("SpareVO1Iterator").getViewObject();
            Row row = null;
            vo.reset();
            RowSetIterator rs = vo.createRowSetIterator(null);
            rs.reset();
            while (rs.hasNext()) {
                row = rs.next();
                if (row.getAttribute("SpareFlag")!=null && row.getAttribute("SpareFlag").equals(true)){
                    DriversStrb.append(row.getAttribute("UserName")); 
                    System.out.println("Selected Spare"+DriversStrb);
                    break;
                }
            }
            rs.closeRowSetIterator();
            if(DriversStrb.length()!=0){
                   DriversStr = DriversStrb.toString();
                
            System.err.println("Id " + Id);
            System.err.println("Working Date : "+ADFUtils.getBoundAttributeValue("WorkingDate"));
            System.err.println("Cover date :"+ADFUtils.getBoundAttributeValue("CoverDate"));
            System.err.println("AreaId"+ADFUtils.getBoundAttributeValue("AreaId"));
            System.err.println("DriverId:"+ADFUtils.getBoundAttributeValue("DriverId"));
            System.err.println("SpareStr" + DriversStr);
            System.err.println("UserName " + ADFContext.getCurrent().getSecurityContext().getUserName());
           String Out_Submit=(String) ADFUtils.callStoredFunction((AppModuleAMImpl)ADFUtils.getApplicationModuleForDataControl("AppModuleAMDataControl"),
                                                                        Types.VARCHAR, "ASSIGN_SPARE_TO_DRIVER(?,?,?,?,?,?,?,?)",
                                                                        new Object[] {ADFUtils.getBoundAttributeValue("WorkingDate"),ADFUtils.getBoundAttributeValue("CoverDate"),ADFUtils.getBoundAttributeValue("AreaId"),ADFUtils.getBoundAttributeValue("DriverId"),DriversStr ,confirmed, ADFContext.getCurrent().getSecurityContext().getUserName() , Id});
                ADFUtils.findOperation("Execute").execute();
          System.err.println("Out: "+Out_Submit);
           // ADFUtils.findOperation("Execute").execute();
            if(Out_Submit.equals("Success"))
            {
                ADFUtils.findOperation("Execute4").execute();
                ADFUtils.findOperation("Execute5").execute();
                
                AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("p1"));
                AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("p2"));

                JSFUtils.addFacesInformationMessage("Spares Assigned Successfully for this area");
        
            }
            else {
                System.err.println("Spare");
                JSFUtils.showPopup("p7");
            }
            }
            else {
            JSFUtils.addFacesErrorMessage("You Should Select at least one Spare");
              //  ADF.addFacesErrorMessage("You can't Submit without selecting at least one driver");
            }
        
    }
// Confirm to OverWrite Spare if Already Assigned 
    public void ConfirmSpare2(DialogEvent dialogEvent) {
        // Add event code here...
        if(dialogEvent.getOutcome().equals(dialogEvent.getOutcome().yes)) {
            SubmitAssignSpare(2);
        }
    }

/******************* 2. Assign Street To Driver *******************************************************************************************/
    // Select All Streets
      public void Change_SelectAllStreets(ValueChangeEvent valueChangeEvent) {
        boolean isSelected=((Boolean)valueChangeEvent.getNewValue()).booleanValue();
        ViewObject vo = ADFUtils.findIterator("DriversStreetVO1Iterator").getViewObject();
        Row row=null;
        vo.reset();
        RowSetIterator rs=vo.createRowSetIterator(null);
        rs.reset();
        Long Assigned_count=0L;
        if(ADFUtils.getBoundAttributeValue("TotalSelectedContracts")!=null)
        Assigned_count=(Long)ADFUtils.getBoundAttributeValue("TotalSelectedContracts");
        while(rs.hasNext()) {
            row=rs.next();
            if(isSelected) {
                if((Long)ADFUtils.getBoundAttributeValue("Id")==(Long)row.getAttribute("Id") || row.getAttribute("Id")==null)
                row.setAttribute("AssignedFlag", isSelected);
                Assigned_count+=(Long)row.getAttribute("TotalContracts");
            }
            else {
                row.setAttribute("AssignedFlag", false);
                Assigned_count=0L;
            }
            
            
        }
        ADFUtils.setBoundAttributeValue("TotalSelectedContracts", Assigned_count);
    }
    // Fetch If Already Selected Streets

    public void fetchStreetIds(PopupFetchEvent popupFetchEvent) {
   /* String AssignedDriversStr = (String) ADFUtils.getBoundAttributeValue("AssignedStreets");
    System.err.println("AssignedDriversStr " + AssignedDriversStr);
    if(ADFUtils.getBoundAttributeValue("AssignedStreets") != null ){
   */ List shuttleValuesList = new ArrayList();
    Long selected_count=0L;
     /*String[] parts = AssignedDriversStr.split(",");
    for (String part : parts) {
        System.err.println(part);
        shuttleValuesList.add(part);
    }*/
    ViewObject vo = ADFUtils.findIterator("DriversStreetVO1Iterator").getViewObject();
    vo.executeQuery();
    Row row = null;
    vo.reset();
    RowSetIterator rs = vo.createRowSetIterator(null);
    rs.reset();
        System.err.println("Record_id"+ADFUtils.getBoundAttributeValue("Id"));
    while (rs.hasNext()) {
        row = rs.next();
        System.err.println("ID"+row.getAttribute("Id"));
        if(((Long)row.getAttribute("Id")==null|| row.getAttribute("Id")==null)){
         
        }
        else if ((Long)row.getAttribute("Id")==(Long)ADFUtils.getBoundAttributeValue("Id")|| ((Long)row.getAttribute("Id")).equals(ADFUtils.getBoundAttributeValue("Id"))){
            System.err.println("Same");
             row.setAttribute("AssignedFlag", true);
             selected_count+=(Long)row.getAttribute("TotalContracts");
        }
        else {
            row.setAttribute("DisabledVal", 1);
        }
    }
    ADFUtils.setBoundAttributeValue("TotalSelectedContracts", selected_count);
    rs.closeRowSetIterator();
    
}
   // Display Confirmation Popup
    public void ConfirmSelectStreet(DialogEvent dialogEvent) {
        // Add event code here...
        JSFUtils.showPopup("p5");
    }
   /*
    * Popup confirmation displayed if user select yes calling submit function
    * 
    */
    public void SelectStreet() {
        JSONArray all_streets=new JSONArray();
            Long Id = (Long) ADFUtils.getBoundAttributeValue("Id");
            StringBuilder DriversStrb = new StringBuilder("");
            String DriversStr = "";
            ViewObject vo = ADFUtils.findIterator("DriversStreetVO1Iterator").getViewObject();
            Row row = null;
            vo.reset();
            RowSetIterator rs = vo.createRowSetIterator(null);
            rs.reset();
            int count_select=0;
            while (rs.hasNext()) {
                row = rs.next();
                if (row.getAttribute("AssignedFlag")!=null && row.getAttribute("AssignedFlag").equals(true)){
                   JSONObject obj=new JSONObject();
                    count_select++;
                    try {
                        obj.put("city", row.getAttribute("CityId"));
                        obj.put("region", row.getAttribute("RegionId"));
                        obj.put("address", row.getAttribute("AddressArea"));
                        obj.put("street", row.getAttribute("StreetId"));
                        System.err.println("Object :"+obj);
                         all_streets.put(obj);
                    } catch (JSONException e) {
                    }


             //       DriversStrb.append(row.getAttribute("StreetId")).append(","); 
                }
            }
            rs.closeRowSetIterator();
            if(count_select>0){
                  //  DriversStr = DriversStrb.deleteCharAt(DriversStrb.length() - 1).toString();
                
            System.err.println("Id " + Id);
            System.err.println("DriversStr" + all_streets);
            String x=null;
            System.err.println("UserName " + ADFContext.getCurrent().getSecurityContext().getUserName());
            ADFUtils.callStoredFunction((AppModuleAMImpl)ADFUtils.getApplicationModuleForDataControl("AppModuleAMDataControl"),
                                                                        Types.NUMERIC, "ASSIGN_DRIVERSTO_STREETS(?,?,?,?)",
                                                                        new Object[] {Id , all_streets.toString() , ADFContext.getCurrent().getSecurityContext().getUserName(),x});
        
            ADFUtils.findOperation("Execute").execute();
         AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("p1"));
                AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("p2"));
                AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("p3"));

            JSFUtils.addFacesInformationMessage("Drivers Assigned Successfully for Selected Streets");
            }
            else {
            JSFUtils.addFacesErrorMessage("You Should Select at least one Street");
              //  ADF.addFacesErrorMessage("You can't Submit without selecting at least one driver");
            }
        
    }
    public void SubmitConfirmationOfStreets(DialogEvent dialogEvent) {
        // Add event code here...
        if(dialogEvent.getOutcome().equals(dialogEvent.getOutcome().yes)) {
            SelectStreet();
        }
    }
/******************************************************************************************************************************************/
    
    /*********************************************3. Assign Pilot ***********************************************************************************/
// If User Select Yes in Confirmation Call Submit For Assign Pilot 
    public void ConfirmSubmitpilots(DialogEvent dialogEvent) {
        // Add event code here...
        if(dialogEvent.getOutcome().equals(dialogEvent.getOutcome().yes)) {
            SubmitAssignPilots();
        }
    }
    
    public void SubmitAssignPilots() {
        Long id=(Long) ADFUtils.getBoundAttributeValue("Id");
        System.err.println("Id:" +id);
        String username=ADFContext.getCurrent().getSecurityContext().getUserName();
        System.err.println("User : "+username);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
           LocalDateTime now = LocalDateTime.now();  
           System.out.println(dtf.format(now)); 
          ADFUtils.callStoredFunction((AppModuleAMImpl)ADFUtils.getApplicationModuleForDataControl("AppModuleAMDataControl"),
                                                                      Types.NUMERIC, "ASSIGN_PILOTS(?,?)",
                                                                      new Object[] {id , ADFContext.getCurrent().getSecurityContext().getUserName()});
            now = LocalDateTime.now();  
           System.out.println(dtf.format(now)); 
       // ADFUtils.findOperation("Execute");
           ViewObject master=ADFUtils.findIterator("AgentAreasSupervisorIterator").getViewObject();
           master.setQueryMode(ViewObject.QUERY_MODE_SCAN_DATABASE_TABLES);
           master.executeQuery();
        now = LocalDateTime.now();  
        System.out.println(dtf.format(now));
        AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("AgentAreasSupervisorTab"));
        now = LocalDateTime.now();  
        System.out.println(dtf.format(now));
        JSFUtils.addFacesInformationMessage("Pilots Assigned Successfully");

    }
     /***********************************************************************************************************************************************/

/************************************************ 4. Dispatch Area (Mark Area as Ready For Dispatch) To Push Assignments To DMS*******************************************************************************************/
    public void ConfirmDispatch(DialogEvent dialogEvent) {
        // Add event code here...
        if(dialogEvent.getOutcome().equals(dialogEvent.getOutcome().yes)) {
            MarkAreaAsReadyForDispatch();
        }
    }
    public void MarkAreaAsReadyForDispatch() {
        Long id=(Long) ADFUtils.getBoundAttributeValue("Id");
        System.err.println("Id:" +id);
        String username=ADFContext.getCurrent().getSecurityContext().getUserName();
        System.err.println("User : "+username);
        
          ADFUtils.callStoredFunction((AppModuleAMImpl)ADFUtils.getApplicationModuleForDataControl("AppModuleAMDataControl"),
                                                                      Types.NUMERIC, "MARKAREAASREADYFORDISPATCH(?,?)",
                                                                      new Object[] {id , ADFContext.getCurrent().getSecurityContext().getUserName()});
        //ADFUtils.findOperation("Execute");
          Key key1=ADFUtils.findIterator("AgentAreasSupervisorIterator").getCurrentRow().getKey();
          ViewObject master_view=ADFUtils.findIterator("AgentAreasSupervisorIterator").getViewObject();
          master_view.setQueryMode(ViewObject.QUERY_MODE_SCAN_DATABASE_TABLES);
          master_view.executeQuery();
          DCIteratorBinding iter=ADFUtils.findIterator("AgentAreasSupervisorIterator");
          iter.setCurrentRowWithKey(key1.toStringFormat(true));
        AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("AgentAreasSupervisorTab"));

        JSFUtils.addFacesInformationMessage("Area Marked As Ready For Dispatch Successfully ");

    }

    /*****************************************************************************************************************************************************************/
    public void OpenDriverPopup(ActionEvent actionEvent) {
        // Add event code here...
        System.err.println("Details ID : "+ADFUtils.getBoundAttributeValue("Id1"));
        JSFUtils.showPopup("p12");
    }

    public void OpenStreetPopup(ActionEvent actionEvent) {
        // Add event code here...
        System.err.println("Details ID : "+ADFUtils.getBoundAttributeValue("Id1"));
        JSFUtils.showPopup("p17");
    }

    public void ChangeCollectorValue(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        UIComponent ui=valueChangeEvent.getComponent();
        ui.processUpdates(FacesContext.getCurrentInstance());
    }

    public void ConfirmAssignCollectionDriver(DialogEvent dialogEvent) {
        // Add event code here...
        if(dialogEvent.getOutcome().equals(dialogEvent.getOutcome().yes)) {
            JSFUtils.showPopup("p27");
        }
        
    }

    public void FetchCollectionDriver(PopupFetchEvent popupFetchEvent) {
        // Add event code here...
        ViewObject vo=ADFUtils.findIterator("AgentAreasSupervisorIterator").getViewObject();
        System.err.println("Collector : "+vo.getCurrentRow().getAttribute("CollectionDriverId"));
        String CollectorId=vo.getCurrentRow().getAttribute("CollectionDriverId").toString();
        if(CollectorId!=null && CollectorId.length()>0)
        {
        ViewObject collectors=ADFUtils.findIterator("CollectorsVO1Iterator").getViewObject();
        collectors.setQueryMode(ViewObject.QUERY_MODE_SCAN_DATABASE_TABLES);
        collectors.executeQuery();
        RowSetIterator rs=collectors.createRowSetIterator(null);
      
        while(rs.hasNext()) {
            Row r=rs.next();
            if(r.getAttribute("DriverUserName").toString().equals(CollectorId)) {
                r.setAttribute("Selected", true);
            }
        }
        }
     //   AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("t14"));

    }

    public void ConfirmAssignSpare1(DialogEvent dialogEvent) {
        // Add event code here...
        if(dialogEvent.getOutcome().equals(dialogEvent.getOutcome().yes)) {
            JSFUtils.showPopup("p29");
        }
        else {
            System.err.println("ID :"+ADFUtils.getBoundAttributeValue("Id"));
            ViewObject vo=ADFUtils.findIterator("CollectorsVO1Iterator").getViewObject();
            RowSetIterator rs=vo.createRowSetIterator(null);
            String DriverUserName="";
            while(rs.hasNext()) {
                Row row=rs.next();
                if(row.getAttribute("Selected")!=null&&row.getAttribute("Selected").equals(true)) {
                    DriverUserName=row.getAttribute("DriverUserName").toString();
                    break;
                }
            }
            System.err.println("Driver User Name : "+DriverUserName);
            System.err.println("Here We Assign Only Drivers");
            ADFUtils.callStoredFunction((AppModuleAMImpl)ADFUtils.getApplicationModuleForDataControl("AppModuleAMDataControl"),
                                                                        Types.NUMERIC, "ASSIGN_COLLECTION_DRIVERSTO_AREA(?,?,?)",
                                                                        new Object[] {ADFUtils.getBoundAttributeValue("Id") , DriverUserName , ADFContext.getCurrent().getSecurityContext().getUserName()});
            //ADFUtils.findOperation("Execute");
            Key key1=ADFUtils.findIterator("AgentAreasSupervisorIterator").getCurrentRow().getKey();
            ViewObject master_view=ADFUtils.findIterator("AgentAreasSupervisorIterator").getViewObject();
            master_view.setQueryMode(ViewObject.QUERY_MODE_SCAN_DATABASE_TABLES);
            master_view.executeQuery();
            DCIteratorBinding iter=ADFUtils.findIterator("AgentAreasSupervisorIterator");
            iter.setCurrentRowWithKey(key1.toStringFormat(true));
            AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("AgentAreasSupervisorTab"));

           JSFUtils.hidePopup("p25");
            JSFUtils.addFacesInformationMessage("Driver Assigned Successfully");

        }
    }

    public void ChangeSelectCollectionSpare(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        UIComponent ui=valueChangeEvent.getComponent();
        ui.processUpdates(FacesContext.getCurrentInstance());
    }
    public void SubmitAssignCollectionSpare(int confirmed) {
        
            Long Id = (Long) ADFUtils.getBoundAttributeValue("Id");
            StringBuilder DriversStrb = new StringBuilder("");
            String DriversStr = "";
            ViewObject vo = ADFUtils.findIterator("CollectionSpareVO2Iterator").getViewObject();
            Row row = null;
            vo.reset();
            RowSetIterator rs = vo.createRowSetIterator(null);
            rs.reset();
            while (rs.hasNext()) {
                row = rs.next();
                if (row.getAttribute("SpareFlag")!=null && row.getAttribute("SpareFlag").equals(true)){
                    DriversStrb.append(row.getAttribute("UserName")); 
                    System.out.println("Selected Spare"+DriversStrb);
                    break;
                }
            }
            rs.closeRowSetIterator();
            if(DriversStrb.length()!=0){
                   DriversStr = DriversStrb.toString();
                
            System.err.println("Id " + Id);
            System.err.println("Working Date : "+ADFUtils.getBoundAttributeValue("WorkingDate"));
            System.err.println("Cover date :"+ADFUtils.getBoundAttributeValue("CoverDate"));
            System.err.println("AreaId"+ADFUtils.getBoundAttributeValue("AreaId"));
            System.err.println("DriverId:"+ADFUtils.getBoundAttributeValue("DriverId"));
            System.err.println("SpareStr" + DriversStr);
            System.err.println("UserName " + ADFContext.getCurrent().getSecurityContext().getUserName());
           String Out_Submit=(String) ADFUtils.callStoredFunction((AppModuleAMImpl)ADFUtils.getApplicationModuleForDataControl("AppModuleAMDataControl"),
                                                                        Types.VARCHAR, "ASSIGN_COLLECTION_SPARE_TO_DRIVER(?,?,?,?,?,?,?,?)",
                                                                        new Object[] {ADFUtils.getBoundAttributeValue("WorkingDate"),ADFUtils.getBoundAttributeValue("CoverDate"),ADFUtils.getBoundAttributeValue("AreaId"),ADFUtils.getBoundAttributeValue("CollectionDriverId"),DriversStr ,confirmed, ADFContext.getCurrent().getSecurityContext().getUserName() , Id});
                ADFUtils.findOperation("Execute").execute();
          System.err.println("Out: "+Out_Submit);
           // ADFUtils.findOperation("Execute").execute();
            if(Out_Submit.equals("Success"))
            {
                ADFUtils.findOperation("Execute4").execute();
                ADFUtils.findOperation("Execute5").execute();
                
               // AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("p1"));
               // AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("p2"));

                JSFUtils.addFacesInformationMessage("Spare Assigned Successfully for this area");
        
            }
            else {
                System.err.println("Spare");
                JSFUtils.showPopup("p7");
            }
            }
            else {
            JSFUtils.addFacesErrorMessage("You Should Select at least one Spare");
              //  ADF.addFacesErrorMessage("You can't Submit without selecting at least one driver");
            }
        
    }
    public void ConfirmAssignCollSpare1(DialogEvent dialogEvent) {
        // Add event code here...
        if(dialogEvent.getOutcome().equals(dialogEvent.getOutcome().yes)) {
            SubmitAssignCollectionSpare(1);
        }
    }

    public void OpenCollectionDriverPopUp(ActionEvent actionEvent) {
        // Add event code here...
        JSFUtils.showPopup("p25");
    }


    public void OpenCollectionDriverDetails(ActionEvent actionEvent) {
        // Add event code here...
        JSFUtils.showPopup("p31");
    }

    public void ChangeSelectCollectorDet(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        UIComponent ui=valueChangeEvent.getComponent();
        ui.processUpdates(FacesContext.getCurrentInstance());
    }

    public void ConfirmSubmitCollDriverDet(DialogEvent dialogEvent) {
        // Add event code here...
        if(dialogEvent.getOutcome().equals(dialogEvent.getOutcome().yes)) {
            JSFUtils.showPopup("p33");
        }
    }

    public void OpenCollectionSpareDet(DialogEvent dialogEvent) {
        // Add event code here...
        if(dialogEvent.getOutcome().equals(dialogEvent.getOutcome().yes)) {
            JSFUtils.showPopup("p34");
        }
        else {
            Long Id = (Long) ADFUtils.getBoundAttributeValue("Id1");
            StringBuilder DriversStrb = new StringBuilder("");
            String DriversStr = "";
            ViewObject vo = ADFUtils.findIterator("UsersROVIterator").getViewObject();
            Row row = null;
            vo.reset();
            RowSetIterator rs = vo.createRowSetIterator(null);
            rs.reset();
            while (rs.hasNext()) {
                row = rs.next();
                if (row.getAttribute("selectFlag")!=null && row.getAttribute("selectFlag").equals(true)){
                    DriversStrb.append(row.getAttribute("UserName")).append(","); 
                }
            }
            rs.closeRowSetIterator();
            if(DriversStrb.length()!=0){
                    DriversStr = DriversStrb.deleteCharAt(DriversStrb.length() - 1).toString();
                
            System.err.println("Id " + Id);
            System.err.println("DriversStr" + DriversStr);
            System.err.println("UserName " + ADFContext.getCurrent().getSecurityContext().getUserName());
            ADFUtils.callStoredFunction((AppModuleAMImpl)ADFUtils.getApplicationModuleForDataControl("AppModuleAMDataControl"),
                                                                        Types.NUMERIC, "ASSIGN_DRIVERSTO_AREA(?,?,?)",
                                                                        new Object[] {Id , DriversStr , ADFContext.getCurrent().getSecurityContext().getUserName()});
            
            //ADFUtils.findOperation("Execute1").execute();
            
                Key key1 = ADFUtils.findIterator("SupervisorAreaMasterVO2Iterator")
                                  .getCurrentRow()
                                  .getKey();
                System.err.println("key : "+key1);
                DCIteratorBinding iter2 = ADFUtils.findIterator("SupervisorAreasDetailsVO1Iterator");
                Key key2 = ADFUtils.findIterator("SupervisorAreasDetailsVO1Iterator")
                                  .getCurrentRow()
                                  .getKey();
            ViewObject master_areas=ADFUtils.findIterator("SupervisorAreaMasterVO2Iterator").getViewObject();
            master_areas.setQueryMode(ViewObject.QUERY_MODE_SCAN_DATABASE_TABLES);
            master_areas.executeQuery();
              //  ADFUtils.findOperation("Execute2").execute();
            ViewObject details_areas=ADFUtils.findIterator("SupervisorAreasDetailsVO1Iterator").getViewObject();
            details_areas.setQueryMode(ViewObject.QUERY_MODE_SCAN_DATABASE_TABLES);
            details_areas.executeQuery();
                DCIteratorBinding iter_master=ADFUtils.findIterator("SupervisorAreaMasterVO2Iterator");
                DCIteratorBinding iter_det=ADFUtils.findIterator("SupervisorAreasDetailsVO1Iterator");
                iter_master.setCurrentRowWithKey(key1.toStringFormat(true));
                iter_det.setCurrentRowWithKey(key2.toStringFormat(true));
            // JSFUtils.addFacesInformationMessage("Drivers Assigned Successfully for this area");
            }
            else {
            JSFUtils.addFacesErrorMessage("You Should Select at least one driver");
              //  ADF.addFacesErrorMessage("You can't Submit without selecting at least one driver");
            }
            
        }
    }

    public void ChangeSelectCollSpareDet(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        UIComponent ui=valueChangeEvent.getComponent();
        ui.processUpdates(FacesContext.getCurrentInstance());
    }
    public void SubmitAssignCollSpareDet(int confirmed) {
        confirmed=1;
            Long Id = (Long) ADFUtils.getBoundAttributeValue("Id1");
            StringBuilder DriversStrb = new StringBuilder("");
            String DriversStr = "";
            ViewObject vo = ADFUtils.findIterator("SpareVO1Iterator").getViewObject();
            Row row = null;
            vo.reset();
            RowSetIterator rs = vo.createRowSetIterator(null);
            rs.reset();
            while (rs.hasNext()) {
                row = rs.next();
                if (row.getAttribute("SpareFlag")!=null && row.getAttribute("SpareFlag").equals(true)){
                    DriversStrb.append(row.getAttribute("UserName")); 
                    break;
                }
            }
            rs.closeRowSetIterator();
            if(DriversStrb.length()!=0){
                   DriversStr = DriversStrb.toString();
                
            System.err.println("Id " + Id);
            System.err.println("Working Date : "+ADFUtils.getBoundAttributeValue("WorkingDate"));
            System.err.println("Cover date :"+ADFUtils.getBoundAttributeValue("CoverDate2"));
            System.err.println("AreaId"+ADFUtils.getBoundAttributeValue("AreaId1"));
            System.err.println("DriverId:"+ADFUtils.getBoundAttributeValue("DriverId1"));
            System.err.println("DriversStr" + DriversStr);
            System.err.println("UserName " + ADFContext.getCurrent().getSecurityContext().getUserName());
           String Out_Submit=(String) ADFUtils.callStoredFunction((AppModuleAMImpl)ADFUtils.getApplicationModuleForDataControl("AppModuleAMDataControl"),
                                                                        Types.VARCHAR, "ASSIGN_COLLECTION_SPARE_TO_DRIVER(?,?,?,?,?,?,?,?)",
                                                                        new Object[] {ADFUtils.getBoundAttributeValue("WorkingDate"),ADFUtils.getBoundAttributeValue("CoverDate"),ADFUtils.getBoundAttributeValue("AreaId"),ADFUtils.getBoundAttributeValue("DriverId"),DriversStr ,confirmed, ADFContext.getCurrent().getSecurityContext().getUserName(),Id});
             //   ADFUtils.findOperation("Execute1").execute();
             //   ADFUtils.findOperation("Execute2").execute();
           
          System.err.println("Out: "+Out_Submit);
           // ADFUtils.findOperation("Execute").execute();
            if(Out_Submit.equals("Success"))
            {
                Key key1 = ADFUtils.findIterator("SupervisorAreaMasterVO2Iterator")
                                  .getCurrentRow()
                                  .getKey();
                System.err.println("key : "+key1);
                DCIteratorBinding iter2 = ADFUtils.findIterator("SupervisorAreasDetailsVO1Iterator");
                Key key2 = ADFUtils.findIterator("SupervisorAreasDetailsVO1Iterator")
                                  .getCurrentRow()
                                  .getKey();
                ViewObject master_areas=ADFUtils.findIterator("SupervisorAreaMasterVO2Iterator").getViewObject();
                master_areas.setQueryMode(ViewObject.QUERY_MODE_SCAN_DATABASE_TABLES);
                master_areas.executeQuery();
                //  ADFUtils.findOperation("Execute2").execute();
                ViewObject details_areas=ADFUtils.findIterator("SupervisorAreasDetailsVO1Iterator").getViewObject();
                details_areas.setQueryMode(ViewObject.QUERY_MODE_SCAN_DATABASE_TABLES);
                details_areas.executeQuery();
                DCIteratorBinding iter_master=ADFUtils.findIterator("SupervisorAreaMasterVO2Iterator");
                DCIteratorBinding iter_det=ADFUtils.findIterator("SupervisorAreasDetailsVO1Iterator");
                iter_master.setCurrentRowWithKey(key1.toStringFormat(true));
                iter_det.setCurrentRowWithKey(key2.toStringFormat(true));
                
                AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("t8"));
                    AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("t9"));
                    AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("p17"));
                    AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("p14"));
                    AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("p12"));

            JSFUtils.addFacesInformationMessage("Spares Assigned Successfully for this area");
            }
            else {
                System.err.println("Spare");
                JSFUtils.showPopup("p7");
            }
            }
            else {
            JSFUtils.addFacesErrorMessage("You Should Select at least one Spare");
              //  ADF.addFacesErrorMessage("You can't Submit without selecting at least one driver");
            }
        
    }
    public void ConfirmAssignCollSpareDet(DialogEvent dialogEvent) {
        // Add event code here...
        if(dialogEvent.getOutcome().equals(dialogEvent.getOutcome().yes)) {
            SubmitAssignCollSpareDet(1);
        }
        
    }
}
