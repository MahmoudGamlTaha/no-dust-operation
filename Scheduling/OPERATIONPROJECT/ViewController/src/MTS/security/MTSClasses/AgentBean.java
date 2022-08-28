package MTS.security.MTSClasses;

import OPERATIONPROJECT.model.BC.AM.AppModuleAMImpl;
import OPERATIONPROJECT.model.BC.VO.AreaContractVORowImpl;
import OPERATIONPROJECT.model.BC.VO.ProductContractDeliveryVORowImpl;


import java.time.format.DateTimeFormatter;  
import java.util.Date;   
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;
import oracle.adf.view.rich.event.PopupFetchEvent;

import oracle.jbo.server.Entity;
import java.sql.Types;
import java.io.IOException;

import java.net.MalformedURLException;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.time.LocalDateTime;

import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import oracle.adf.model.BindingContext;

import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
//import oracle.adf.model.binding.DCIteratorBinding;

import oracle.adf.share.ADFContext;

import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.OperationBinding;

import oracle.jbo.ApplicationModule;
import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;

import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.event.AttributeChangeEvent;
import org.apache.myfaces.trinidad.event.DisclosureEvent;

public class AgentBean {
    private static int REFRESH_WITH_DB_FORGET_CHANGES;

    public AgentBean() {
        super();
    }
    //public static final int REFRESH_WITH_DB_FORGET_CHANGES =0 ;
    public void submitAreaContract() {
        // Add event code here...
        ViewObject vo = ADFUtils.findIterator("AreaContractVOIterator").getViewObject();
        StringBuilder CardNoStrb = new StringBuilder("");
        StringBuilder DispFlagStrb = new StringBuilder("");
        String CardNoStr = "";
        String DispFlagStr = "";
        AreaContractVORowImpl row = null;
        vo.reset();
        RowSetIterator rs = vo.createRowSetIterator(null);
        rs.reset();
        while (rs.hasNext()) {
            row = (AreaContractVORowImpl)rs.next();
            if(row.getEntity(0).getEntityState()== Entity.STATUS_MODIFIED ){
                        Object CardNo = row.getAttribute("CardNo");
                        Object Dispatchable = row.getAttribute("Dispatchable");
                        if((Long)row.getAttribute("Dispatchable")==0) {
                           JSFUtils.addFacesErrorMessage("You Can't Undispatch Contracts Without Confirmation For Postpone");
                           row.setAttribute("Dispatchable", 1);
                           vo.setQueryMode(ViewObject.QUERY_MODE_SCAN_DATABASE_TABLES);
                           vo.executeQuery();
                            AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("AreaContractVOTab"));
                           return;
                        }
                        CardNoStrb.append(CardNo).append(","); 
                        DispFlagStrb.append(Dispatchable).append(","); 
            }
        }
        rs.closeRowSetIterator();
        System.err.println("CardNoStr "+CardNoStrb);
        System.err.println("DispFlagStr "+DispFlagStrb);
        if(CardNoStrb.length() != 0 && DispFlagStrb.length() != 0){
        CardNoStr = CardNoStrb.deleteCharAt(CardNoStrb.length() - 1).toString();
        DispFlagStr = DispFlagStrb.deleteCharAt(DispFlagStrb.length() - 1).toString();
        System.err.println("CardNoStr "+CardNoStr);
        System.err.println("DispFlagStr "+DispFlagStr);
        ADFUtils.callStoredFunction((AppModuleAMImpl)ADFUtils.getApplicationModuleForDataControl("AppModuleAMDataControl"),
                                                                    Types.NUMERIC, "UPDATE_DISPATCH_FLAG(?,?)",
                                                                    new Object[] { CardNoStr, DispFlagStr });
            //ADFUtils.findOperation("Execute").execute();
            JSFUtils.addFacesInformationMessage("Contracts Updated Successfully");
        }
    }


    public void submitPopup() {
        System.err.println("Here");
        // Add event code here...
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
           LocalDateTime now = LocalDateTime.now();  
           System.out.println(dtf.format(now)); 
        System.err.println("Comment"+ADFUtils.getBoundAttributeValue("OperationComment"));
        System.err.println("Close Code"+ADFUtils.getBoundAttributeValue("CloseCode"));
        System.err.println("Close Reason"+ADFUtils.getBoundAttributeValue("CloseReason"));
        if(ADFUtils.getBoundAttributeValue("OperationComment")==null || ADFUtils.getBoundAttributeValue("CloseCode")==null || ADFUtils.getBoundAttributeValue("CloseReason")==null) {
            JSFUtils.addFacesErrorMessage("You Should Enter Close Code , Close Reason , Operation Comment ");
        return ;
        }
        ////////////////Contract Information////////////////
        JSFUtils.setExpressionValue("#{requestScope.ErrorMess1}", 0);
        JSFUtils.setExpressionValue("#{requestScope.ErrorMess2}", 0);
        JSFUtils.setExpressionValue("#{requestScope.ErrorMess3}", 0);
        StringBuilder ContractInfoStrb = new StringBuilder("");
        String ContractInfoStr = "";
        ContractInfoStrb.append(ADFUtils.getBoundAttributeValue("CardNo")).append(",")
                        .append(ADFUtils.getBoundAttributeValue("Duration")).append(",")
                        .append(ADFUtils.getBoundAttributeValue("CityId")).append(",")
                        .append(ADFUtils.getBoundAttributeValue("AddressArea")).append(",")
                        .append(ADFUtils.getBoundAttributeValue("AreaContractInformationVOHomeNo")).append(",")
                        .append(ADFUtils.getBoundAttributeValue("AreaContractInformationVOFloorNo")).append(",")
                        .append(ADFUtils.getBoundAttributeValue("AreaContractInformationVOFlatNo")).append(",")
                        .append(ADFUtils.getBoundAttributeValue("AreaContractInformationVOContractClientName")).append(",")
                        .append(ADFUtils.getBoundAttributeValue("AreaContractInformationVOPNum")).append(",")
                        .append(ADFUtils.getBoundAttributeValue("AreaContractInformationVOLastChangedDate")).append(",")
                        .append(ADFUtils.getBoundAttributeValue("RegionId")).append(",")
                        .append(ADFUtils.getBoundAttributeValue("StreetId")).append(",")
                        .append(ADFUtils.getBoundAttributeValue("AreaContractInformationVOAddress")).append(",")
                        .append(ADFUtils.getBoundAttributeValue("Remarks")).append(",")
                        .append(ADFUtils.getBoundAttributeValue("AreaIdCon")).append(",")
                        .append(ADFUtils.getBoundAttributeValue("AreaNameCon"));
        ContractInfoStr = ContractInfoStrb.toString();
        System.err.println("ContractInfoStr "+ContractInfoStr);
        ////////////////Promos////////////////
        ViewObject vo = ADFUtils.findIterator("PromosDeliveryVOIterator").getViewObject();
        StringBuilder PromosStrb = new StringBuilder("");
        StringBuilder QuantityStrb = new StringBuilder("");
        StringBuilder PromosFlagStrb = new StringBuilder("");
        String PromosStr = "";
        String QuantityStr = "";
        String PromosFlagStr = "";
        Row row = null;
        vo.reset();
        RowSetIterator rs = vo.createRowSetIterator(null);
        rs.reset();
        while (rs.hasNext()) {
            row = rs.next();
                        Object ProductSku = row.getAttribute("ProductSku");
                        Object Quantity = row.getAttribute("Quantity");
                        Long Quantityq = (Long)row.getAttribute("Quantity");
                        Object PromosFlag = row.getAttribute("DeliveryFlag");
                        Long PromosFlag1 = (Long)row.getAttribute("DeliveryFlag");
                    /*if(row.getAttribute("DeliveryFlag")!=null && PromosFlag1 ==1 &&(ProductSku == null || Quantity == null || Quantityq<=0)){
                            JSFUtils.addFacesInformationMessage("Enter product and quantity for all selected records for Promos with quantity > 0");
                            //JSFUtils.setExpressionValue("#{requestScope.ErrorMess1}", 1);
                            return;
                            }*/
                        PromosStrb.append(ProductSku).append(","); 
                        QuantityStrb.append(Quantity).append(","); 
                        PromosFlagStrb.append(PromosFlag).append(",");
        }
        rs.closeRowSetIterator();
        if(PromosStrb.length()!=0 && QuantityStrb.length()!=0 && PromosFlagStrb.length()!=0){
        PromosStr = PromosStrb.deleteCharAt(PromosStrb.length() - 1).toString();
        QuantityStr = QuantityStrb.deleteCharAt(QuantityStrb.length() - 1).toString();
        PromosFlagStr = PromosFlagStrb.deleteCharAt(PromosFlagStrb.length() - 1).toString();
        }
        System.err.println("PromosStr "+PromosStr);
        System.err.println("QuantityStr "+QuantityStr);
        System.err.println("PromosFlagStr "+PromosFlagStr);

        //////////////////Product/////////////////////
        ViewObject vo1 = ADFUtils.findIterator("ProductContractDeliveryVOIterator").getViewObject();
        StringBuilder ProductStrb = new StringBuilder("");
        StringBuilder TreatmentStrb = new StringBuilder("");
        StringBuilder PackageNoStrb = new StringBuilder("");
        StringBuilder QuantityPStrb = new StringBuilder("");
        StringBuilder ConfQuantStrb = new StringBuilder("");
        StringBuilder ProductFlagStrb = new StringBuilder("");
        StringBuilder ProductConIdStrb = new StringBuilder("");
        StringBuilder ProductPriceStrb=new StringBuilder("");
        StringBuilder AssignmentStrb = new StringBuilder("");
        StringBuilder Package_IdStrb=new StringBuilder("");
        String Package_Id_str="";
        String ProductStr = "";
        String TreatmentStr = "";
        String PackageNoStr = "";
        String QuantityPStr = "";
        String ConfQuantStr = "";
        String ProductFlagStr = "";
        String ProductConIdStr = "";
        String Price_str="";
        String AssignmentStr = "";
        ProductContractDeliveryVORowImpl row1 = null;
        vo1.reset();
        RowSetIterator rs1 = vo1.createRowSetIterator(null);
        rs1.reset();
        while (rs1.hasNext()) {
        row1 = (ProductContractDeliveryVORowImpl)rs1.next();
                    Object ProductId = row1.getAttribute("ProductId");
                    Object TreatmentId = row1.getAttribute("TreatmentId");
                    Object PackageNo = row1.getAttribute("PackageNo");
                    Object Quantity = row1.getAttribute("Quantity");
            Object Pack_Id=row1.getAttribute("PackageId");
                    Object ConfQuant = row1.getAttribute("Quantity");
                    Long Quantityp =(Long) row1.getAttribute("Quantity");
                    Object ProductFlag = row1.getAttribute("DeliveryFlag1");
                    Long ProductFlag1 = (Long)row1.getAttribute("DeliveryFlag1");
                    Object ProductConId = row1.getAttribute("ProductConId");
            Long ProductPrice=(Long)row1.getAttribute("Price");
                    if(row1.getAttribute("DeliveryFlag1")!=null && ProductFlag1 == 1 && (Quantity == null || ConfQuant== null || Quantityp <= 0)){
                            JSFUtils.addFacesInformationMessage("Enter quantity for all selected records for Products with value > 0");
                            //JSFUtils.setExpressionValue("#{requestScope.ErrorMess2}", 1);
                            return;
                        }
                    if(row1.getEntity(0).getEntityState()== Entity.STATUS_NEW  && (ProductId == null || TreatmentId == null || PackageNo == null || Quantity == null)){
                        JSFUtils.addFacesInformationMessage("Enter product ,Treatment ,Package No and quantity for all selected records for Products");
                        //JSFUtils.setExpressionValue("#{requestScope.ErrorMess3}", 1);
                        return;
                        }
                    ProductStrb.append(ProductId).append(","); 
                    TreatmentStrb.append(TreatmentId).append(","); 
                    PackageNoStrb.append(PackageNo).append(","); 
                    QuantityPStrb.append(Quantity).append(",");
                    ConfQuantStrb.append(ConfQuant).append(",");
                    ProductFlagStrb.append(ProductFlag).append(",");
                    ProductConIdStrb.append(ProductConId==null?0:ProductConId).append(",");
            if(ProductPrice==null)
                ProductPriceStrb.append("0").append(",");
                else   
            ProductPriceStrb.append(ProductPrice).append(",");
            Package_IdStrb.append(Pack_Id).append(",");
        }
        rs1.closeRowSetIterator();
        if(ProductStrb.length()!=0 && TreatmentStrb.length()!=0 && PackageNoStrb.length()!=0 && QuantityPStrb.length()!=0 && ProductFlagStrb.length()!=0 && ProductConIdStrb.length()!=0){
        ProductStr = ProductStrb.deleteCharAt(ProductStrb.length() - 1).toString();
        TreatmentStr = TreatmentStrb.deleteCharAt(TreatmentStrb.length() - 1).toString();
        PackageNoStr = PackageNoStrb.deleteCharAt(PackageNoStrb.length() - 1).toString();
        QuantityPStr = QuantityPStrb.deleteCharAt(QuantityPStrb.length() - 1).toString();
        ConfQuantStr = ConfQuantStrb.deleteCharAt(ConfQuantStrb.length() - 1).toString();
        ProductFlagStr = ProductFlagStrb.deleteCharAt(ProductFlagStrb.length() - 1).toString();
        ProductConIdStr = ProductConIdStrb.deleteCharAt(ProductConIdStrb.length() - 1).toString();
        Price_str=ProductPriceStrb.deleteCharAt(ProductPriceStrb.length()-1).toString();
        Package_Id_str=Package_IdStrb.deleteCharAt(PackageNoStrb.length()-1).toString();
        }
        System.err.println("ProductStr " + ProductStr);
        System.err.println("TreatmentStr " + TreatmentStr);
        System.err.println("PackageNoStr " + PackageNoStr);
        System.err.println("QuantityPStr " + QuantityPStr);
        System.err.println("ConfQuantStr " + ConfQuantStr);
        System.err.println("ProductFlagStr " + ProductFlagStr);
        System.err.println("ProductConIdStr " + ProductConIdStr);
        System.err.println("ProductPrice " + Price_str);

        AssignmentStrb.append(ADFUtils.getBoundAttributeValue("CoverDate")).append(",")
                        .append(ADFUtils.getBoundAttributeValue("CloseCode")).append(",")
                        .append(ADFUtils.getBoundAttributeValue("CloseReason")).append(",")
                        .append("l").append(",")
                        .append(ADFUtils.getBoundAttributeValue("AssignmentId"));
        AssignmentStr = AssignmentStrb.toString();
        System.err.println("AssignmentStr "+AssignmentStr);
        System.err.println("Duration"+ADFUtils.getBoundAttributeValue("PotponeDuration1"));
        System.err.println("working date "+ADFUtils.getBoundAttributeValue("WorkDate"));
         now = LocalDateTime.now();  
        System.out.println(dtf.format(now)); 
        Object x=ADFUtils.callStoredFunction((AppModuleAMImpl)ADFUtils.getApplicationModuleForDataControl("AppModuleAMDataControl"),
                                                                    Types.NUMERIC, "UPDATE_ASSIGNEMNT(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                                                    new Object[] {PromosFlagStr,PromosStr,QuantityStr,ContractInfoStr,AssignmentStr,ProductStr,TreatmentStr,PackageNoStr,QuantityPStr,ConfQuantStr,ProductFlagStr,ProductConIdStr,ADFUtils.getBoundAttributeValue("WorkDate"),ADFUtils.getBoundAttributeValue("Id"), ADFContext.getCurrent().getSecurityContext().getUserName(),ADFUtils.getBoundAttributeValue("PotponeDuration1"),Price_str , Package_Id_str,ADFUtils.getBoundAttributeValue("AreaContractInformationVOAddress"),ADFUtils.getBoundAttributeValue("Remarks"),ADFUtils.getBoundAttributeValue("OperationComment") , ADFUtils.getBoundAttributeValue("AssignmentId")});
      
      //   ADFUtils.findOperation("Rollback").execute();

      //  ADFUtils.findOperation("Commit").execute();   
       // ADFUtils.findOperation("Execute3").execute();
    /*   now = LocalDateTime.now();  
       System.out.println("commit"+dtf.format(now));*/
     //   ADFUtils.findIterator("AreaContractVOIterator").getViewObject().executeQuery();
        
       now = LocalDateTime.now();  
       System.out.println("execute"+dtf.format(now));
     // ADFUtils.findIterator("ContractDeliveryScheduleVOIterator").getCurrentRow().refresh(REFRESH_WITH_DB_FORGET_CHANGES);
       /* now = LocalDateTime.now();  
        System.out.println("refreshed"+dtf.format(now));
        AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("AreaContractVOTab"));
*/
       // ADFUtils.findOperation("Rollback").execute();
       /* now = LocalDateTime.now();  
       System.out.println(dtf.format(now)); 
       System.err.println(x);
       DCIteratorBinding iter1 = ADFUtils.findIterator("AreaContractVOIterator");
        Key key1 = ADFUtils.findIterator("AreaContractVOIterator")
                          .getCurrentRow()
                          .getKey();
        now = LocalDateTime.now();  
        System.out.println("get iterator key"+dtf.format(now));
        
        Row currentRow = ADFUtils.findIterator("AreaContractVOIterator").getCurrentRow();
        now = LocalDateTime.now();  
        System.out.println("get row "+dtf.format(now));
        DCIteratorBinding iter = ADFUtils.findIterator("AreaContractVOIterator");
        now = LocalDateTime.now();  
        System.out.println("get iterator "+dtf.format(now));
        ADFUtils.findOperation("Delete1").execute();

        Key key = ADFUtils.findIterator("AreaContractVOIterator")
                          .getCurrentRow()
                          .getKey();
        now = LocalDateTime.now();  
        System.out.println("get key "+dtf.format(now));
        now = LocalDateTime.now();  
        System.out.println("roll back execute "+dtf.format(now));

       //  iter.setCurrentRowWithKey(key.toStringFormat(true));
        System.out.println("set key "+dtf.format(now));

        JSFUtils.hidePopup("p1");

       // iter1.setCurrentRowWithKey(key1.toStringFormat(true));
        
        //iter.setCurrentRowWithKey(key.toStringFormat(true));
      /*  now = LocalDateTime.now();  
        System.out.println("get key "+dtf.format(now));
        DCIteratorBinding iter1 = ADFUtils.findIterator("WeekDaysAgentVOIterator");
              Key key1 = ADFUtils.findIterator("WeekDaysAgentVOIterator")
                                 .getCurrentRow()
                                 .getKey();
        now = LocalDateTime.now();  
        System.out.println("get key "+dtf.format(now));
               Row currentRow = ADFUtils.findIterator("WeekDaysAgentVOIterator").getCurrentRow();
               DCIteratorBinding iter = ADFUtils.findIterator("AgentAreasVOIterator");
               Key key = ADFUtils.findIterator("AgentAreasVOIterator")
                                 .getCurrentRow()
                                 .getKey();
        now = LocalDateTime.now();  
        System.out.println("get key "+dtf.format(now));
              iter1.setCurrentRowWithKey(key1.toStringFormat(true));
        now = LocalDateTime.now();  
        System.out.println("set key1 "+dtf.format(now));
               iter.setCurrentRowWithKey(key.toStringFormat(true));
        now = LocalDateTime.now();  
        System.out.println("set key2 "+dtf.format(now));

//        DCIteratorBinding iter1 = ADFUtils.findIterator("WeekDaysAgentVOIterator");
  //      Key key1 = ADFUtils.findIterator("WeekDaysAgentVOIterator")
    //                      .getCurrentRow()
      //                    .getKey();
       // Row currentRow = ADFUtils.findIterator("WeekDaysAgentVOIterator").getCurrentRow();
       // DCIteratorBinding iter = ADFUtils.findIterator("AgentAreasVOIterator");
       // Key key = ADFUtils.findIterator("AgentAreasVOIterator")
         //                 .getCurrentRow()
           //               .getKey();
       // iter1.setCurrentRowWithKey(key1.toStringFormat(true));
       /// iter.setCurrentRowWithKey(key.toStringFormat(true));
       // ADFUtils.findOperation("Delete").execute();
*/
       // ADFUtils.findOperation("Delete1").execute();
       System.err.println("Hide popup line ");
      //  JSFUtils.hidePopup("p1");
      ViewObject AreaSummary=ADFUtils.findIterator("AgentAreasSummaryVO1Iterator").getViewObject();
      //  AreaSummary.clearCache();
      //AreaSummary.setQueryMode(ViewObject.QUERY_MODE_SCAN_DATABASE_TABLES);
      AreaSummary.executeQuery();
      now = LocalDateTime.now();

      System.out.println("Area Summary Executed"+dtf.format(now));
        ViewObject Contract_Status=ADFUtils.findIterator("ContractStatusVO1Iterator").getViewObject();
       Contract_Status.executeQuery();
        
        now = LocalDateTime.now();
        System.out.println("Contract Status Executed"+dtf.format(now));
        JSFUtils.addFacesInformationMessage("Assignment Created Successfully");



    }
    public static java.sql.Date convertOracleDateToJavaSqlDate(oracle.jbo.domain.Date oracleDate)
      {
        if(oracleDate == null)
          return null;
     
        return oracleDate.dateValue();
      }
    public void markAreaAsDone() {
        // Add event code here...
        //ADFUtils.findIterator("AgentAreasVOIterator");
        oracle.jbo.Key k =  new oracle.jbo.Key(new Object[] { ADFUtils.findIterator("AgentAreasVOIterator").getViewObject().getCurrentRow().getAttribute("Id") });
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
           LocalDateTime now = LocalDateTime.now();  
           System.out.println(dtf.format(now)); 

        System.err.println("markAreaAsDone "+ADFUtils.getBoundAttributeValue("AreaId")+ ADFContext.getCurrent().getSecurityContext().getUserName()+ADFUtils.getBoundAttributeValue("WorkDate"));
       /* ADFUtils.callStoredFunction((AppModuleAMImpl)ADFUtils.getApplicationModuleForDataControl("AppModuleAMDataControl"),
                                                                     Types.VARCHAR, "DISPATCH_CONTRACTS(?,?,?,?)",
                                                                     new Object[] { ADFUtils.getBoundAttributeValue("AreaId"),ADFUtils.getBoundAttributeValue("WorkDate"), ADFContext.getCurrent().getSecurityContext().getUserName(),ADFUtils.getBoundAttributeValue("Id")});
*/         System.err.println("Dispatched"+ADFUtils.getBoundAttributeValue("Id"));
        now = LocalDateTime.now();  
                   System.out.println(dtf.format(now)); 
       String out_data=(String) ADFUtils.callStoredFunction((AppModuleAMImpl)ADFUtils.getApplicationModuleForDataControl("AppModuleAMDataControl"),
                                                                    Types.VARCHAR, "MARK_AREA_AS_DONE(?,?,?,?)",
                                                                    new Object[] { ADFUtils.getBoundAttributeValue("AreaId"), ADFContext.getCurrent().getSecurityContext().getUserName(),ADFUtils.getBoundAttributeValue("WorkDate"),ADFUtils.getBoundAttributeValue("Id")});
       System.err.println(out_data);
        now = LocalDateTime.now();  
                   System.out.println(dtf.format(now)); 
        System.err.println("Done");

        ADFUtils.findIterator("AgentAreasVOIterator").getViewObject().executeQuery();  
        now = LocalDateTime.now();  
                   System.out.println("Query executed"+dtf.format(now)); 
                  oracle.jbo.Row r= ADFUtils.findIterator("AgentAreasVOIterator").getViewObject().getRow(k);
        now = LocalDateTime.now();  
                   System.out.println("get row"+dtf.format(now)); 
                  ADFUtils.findIterator("AgentAreasVOIterator").getViewObject().setCurrentRow(r);
        now = LocalDateTime.now();  
                   System.out.println("set row"+dtf.format(now)); 
        JSFUtils.addFacesInformationMessage("Area Marked As done successfully");
    }

    public void createTicket(PopupFetchEvent popupFetchEvent) {
        // Add event code here...
        ADFUtils.findOperation("CreateInsert").execute();
        Row currentRow = ADFUtils.findIterator("OpenTicketVOIterator").getCurrentRow();
        Row ContractRow = ADFUtils.findIterator("AreaContractVOIterator").getCurrentRow();
        System.err.println("AreaName "+ContractRow.getAttribute("AreaName"));
        currentRow.setAttribute("Creator", ADFContext.getCurrent().getSecurityContext().getUserName());
        currentRow.setAttribute("Name", ContractRow.getAttribute("ContractClientName"));
        currentRow.setAttribute("CurrentTeam",ContractRow.getAttribute("AreaName"));
        currentRow.setAttribute("AreaCode",ContractRow.getAttribute("AreaId"));
        currentRow.setAttribute("Landline", ContractRow.getAttribute("CardNo"));
    }

    public void cancelTicket(PopupCanceledEvent popupCanceledEvent) {
        // Add event code here...
//        DCIteratorBinding iter1 = ADFUtils.findIterator("WeekDaysAgentVOIterator");
//        Key key1 = ADFUtils.findIterator("WeekDaysAgentVOIterator")
//                          .getCurrentRow()
//                          .getKey();
//        Row currentRow = ADFUtils.findIterator("WeekDaysAgentVOIterator").getCurrentRow();
//        DCIteratorBinding iter = ADFUtils.findIterator("AgentAreasVOIterator");
//        Key key = ADFUtils.findIterator("AgentAreasVOIterator")
//                          .getCurrentRow()
//                          .getKey();
        ADFUtils.findOperation("Delete").execute();
//        iter1.setCurrentRowWithKey(key1.toStringFormat(true));
//        iter.setCurrentRowWithKey(key.toStringFormat(true));
        JSFUtils.hidePopup("CreateTicket");
    }

    public void sumbitCreateTicket() {
        // Add event code here...
//        DCIteratorBinding iter1 = ADFUtils.findIterator("WeekDaysAgentVOIterator");
//        Key key1 = ADFUtils.findIterator("WeekDaysAgentVOIterator")
//                          .getCurrentRow()
//                          .getKey();
//        Row currentRow = ADFUtils.findIterator("WeekDaysAgentVOIterator").getCurrentRow();
//        DCIteratorBinding iter = ADFUtils.findIterator("AgentAreasVOIterator");
//        Key key = ADFUtils.findIterator("AgentAreasVOIterator")
//                          .getCurrentRow()
//                          .getKey();
        StringBuilder TicketStrb = new StringBuilder("");
        String TicketStr = "";
        TicketStrb.append(ADFUtils.getBoundAttributeValue("Creator")).append(",")
                        .append(ADFUtils.getBoundAttributeValue("Name")).append(",")
                        .append(ADFUtils.getBoundAttributeValue("CurrentTeam")).append(",")
                        .append(ADFUtils.getBoundAttributeValue("AreaCode")).append(",")
                        .append(ADFUtils.getBoundAttributeValue("Landline")).append(",")
                        .append(ADFUtils.getBoundAttributeValue("TicketDirection")).append(",")
                        .append(ADFUtils.getBoundAttributeValue("TicketChannel")).append(",")
                        .append(ADFUtils.getBoundAttributeValue("TicketFamily")).append(",")
                        .append(ADFUtils.getBoundAttributeValue("TicketClassification")).append(",")
                        .append(ADFUtils.getBoundAttributeValue("TicketCase")).append(",")
                        .append(ADFUtils.getBoundAttributeValue("Notes"));
        TicketStr = TicketStrb.toString();
        System.err.println("ContractInfoStr "+TicketStr);
        ADFUtils.callStoredFunction((AppModuleAMImpl)ADFUtils.getApplicationModuleForDataControl("AppModuleAMDataControl"),
                                                                    Types.NUMERIC, "CREATE_TICKET(?,?)",
                                                                    new Object[] {TicketStr,ADFUtils.getBoundAttributeValue("Notes")});
        
        ADFUtils.findOperation("Delete").execute();
//        try{
//        iter1.setCurrentRowWithKey(key1.toStringFormat(true));
//        iter.setCurrentRowWithKey(key.toStringFormat(true));
//        }
//        catch(Exception e){System.err.println("Exception occurred");}
        JSFUtils.hidePopup("CreateTicket");
        JSFUtils.addFacesInformationMessage("Ticket Created Successfully");
    }

    public void CreateProduct(ActionEvent actionEvent) {
        // Add event code here...CreateInsertAssign
        ADFUtils.findOperation("CreateProductContractDeliveryVO").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("ProductContractDeliveryVOTopLcSdi"));
   ADFUtils.setBoundAttributeValue("VALID", 0);
   Long num=0L;
  /* if(ADFUtils.getBoundAttributeValue("iDSEQ")!=null) {
       num=(Long)ADFUtils.getBoundAttributeValue("iDSEQ");
   }
       ViewObject vo2=ADFUtils.findIterator("ProductContractDeliveryVOIterator").getViewObject();
       //  System.err.println("ValueSku : "+ADFUtils.getBoundAttributeValue("ProductSku"));
       RowSetIterator rs2=vo2.createRowSetIterator(null);
       while(rs2.hasNext()) {
           Row r=rs2.next();
           if(r.getAttribute("Id")==null) {
               
               r.setAttribute("Id", num+1);
               ADFUtils.setBoundAttributeValue("iDSEQ", num+1);

           }
       }
   */
    }

    public void okSubmitPopup(DialogEvent dialogEvent) {
        // Add event code here...
       
        if(dialogEvent.getOutcome() == DialogEvent.Outcome.ok){
                ////////////////Contract Information////////////////
                JSFUtils.setExpressionValue("#{requestScope.ErrorMess1}", 0);
                JSFUtils.setExpressionValue("#{requestScope.ErrorMess2}", 0);
                JSFUtils.setExpressionValue("#{requestScope.ErrorMess3}", 0);
                StringBuilder ContractInfoStrb = new StringBuilder("");
                String ContractInfoStr = "";
                ContractInfoStrb.append(ADFUtils.getBoundAttributeValue("CardNo")).append(",")
                                .append(ADFUtils.getBoundAttributeValue("Duration")).append(",")
                                .append(ADFUtils.getBoundAttributeValue("CityId")).append(",")
                                .append(ADFUtils.getBoundAttributeValue("AddressArea")).append(",")
                                .append(ADFUtils.getBoundAttributeValue("AreaContractInformationVOHomeNo")).append(",")
                                .append(ADFUtils.getBoundAttributeValue("AreaContractInformationVOFloorNo")).append(",")
                                .append(ADFUtils.getBoundAttributeValue("AreaContractInformationVOFlatNo")).append(",")
                                .append(ADFUtils.getBoundAttributeValue("AreaContractInformationVOContractClientName")).append(",")
                                .append(ADFUtils.getBoundAttributeValue("AreaContractInformationVOPNum")).append(",")
                                .append(ADFUtils.getBoundAttributeValue("AreaContractInformationVOLastChangedDate")).append(",")
                                .append(ADFUtils.getBoundAttributeValue("RegionId")).append(",")
                                .append(ADFUtils.getBoundAttributeValue("StreetId")).append(",")
                                .append(ADFUtils.getBoundAttributeValue("AreaContractInformationVOAddress")).append(",")
                                .append(ADFUtils.getBoundAttributeValue("Remarks")).append(",")
                                .append(ADFUtils.getBoundAttributeValue("AreaIdCon")).append(",")
                                .append(ADFUtils.getBoundAttributeValue("AreaNameCon"));
                ContractInfoStr = ContractInfoStrb.toString();
                System.err.println("ContractInfoStr "+ContractInfoStr);
                ////////////////Promos////////////////
                ViewObject vo = ADFUtils.findIterator("PromosDeliveryVOIterator").getViewObject();
                StringBuilder PromosStrb = new StringBuilder("");
                StringBuilder QuantityStrb = new StringBuilder("");
                StringBuilder PromosFlagStrb = new StringBuilder("");
                String PromosStr = "";
                String QuantityStr = "";
                String PromosFlagStr = "";
                Row row = null;
                vo.reset();
                RowSetIterator rs = vo.createRowSetIterator(null);
                rs.reset();
                while (rs.hasNext()) {
                    row = rs.next();
                                Object ProductSku = row.getAttribute("ProductSku");
                                Object Quantity = row.getAttribute("Quantity");
                                Long Quantityq = (Long)row.getAttribute("Quantity");
                                Object PromosFlag = row.getAttribute("DeliveryFlag");
                                Long PromosFlag1 = (Long)row.getAttribute("DeliveryFlag");
                                if(row.getAttribute("DeliveryFlag")!=null && PromosFlag1 ==1 &&(ProductSku == null || Quantity == null || Quantityq<=0)){
                                    //JSFUtils.addFacesInformationMessage("Enter product and quantity for all selected records for Promos with quantity > 0");
                                    JSFUtils.setExpressionValue("#{requestScope.ErrorMess1}", 1);
                                    return;
                                    }
                                PromosStrb.append(ProductSku).append(","); 
                                QuantityStrb.append(Quantity).append(","); 
                                PromosFlagStrb.append(PromosFlag).append(",");
                }
                rs.closeRowSetIterator();
                if(PromosStrb.length()!=0 && QuantityStrb.length()!=0 && PromosFlagStrb.length()!=0){
                PromosStr = PromosStrb.deleteCharAt(PromosStrb.length() - 1).toString();
                QuantityStr = QuantityStrb.deleteCharAt(QuantityStrb.length() - 1).toString();
                PromosFlagStr = PromosFlagStrb.deleteCharAt(PromosFlagStrb.length() - 1).toString();
                }
                System.err.println("PromosStr "+PromosStr);
                System.err.println("QuantityStr "+QuantityStr);
                System.err.println("PromosFlagStr "+PromosFlagStr);

                //////////////////Product/////////////////////
                ViewObject vo1 = ADFUtils.findIterator("ProductContractDeliveryVOIterator").getViewObject();
                StringBuilder ProductStrb = new StringBuilder("");
                StringBuilder TreatmentStrb = new StringBuilder("");
                StringBuilder PackageNoStrb = new StringBuilder("");
                StringBuilder QuantityPStrb = new StringBuilder("");
                StringBuilder ConfQuantStrb = new StringBuilder("");
                StringBuilder ProductFlagStrb = new StringBuilder("");
                StringBuilder AssignmentStrb = new StringBuilder("");
                String ProductStr = "";
                String TreatmentStr = "";
                String PackageNoStr = "";
                String QuantityPStr = "";
                String ConfQuantStr = "";
                String ProductFlagStr = "";
                String AssignmentStr = "";
                ProductContractDeliveryVORowImpl row1 = null;
                vo1.reset();
                RowSetIterator rs1 = vo1.createRowSetIterator(null);
                rs1.reset();
                while (rs1.hasNext()) {
                row1 = (ProductContractDeliveryVORowImpl)rs1.next();
                            Object ProductId = row1.getAttribute("ProductId");
                            Object TreatmentId = row1.getAttribute("TreatmentId");
                            Object PackageNo = row1.getAttribute("PackageNo");
                            Object Quantity = row1.getAttribute("Quantity");
                            Long Quantityp =(Long) row1.getAttribute("Quantity");
                            Object ConfQuant = row1.getAttribute("ConfQuant");
                            Object ProductFlag = row1.getAttribute("DeliveryFlag1");
                            Long ProductFlag1 = (Long)row1.getAttribute("DeliveryFlag1");
                            if(row1.getAttribute("DeliveryFlag1")!=null && ProductFlag1 == 1 && (Quantity == null || Quantityp <= 0)){
                                    //JSFUtils.addFacesInformationMessage("Enter quantity for all selected records for Products with value > 0");
                                    JSFUtils.setExpressionValue("#{requestScope.ErrorMess2}", 1);
                                    return;
                                }
                            if(row1.getEntity(0).getEntityState()== Entity.STATUS_NEW  && (ProductId == null || TreatmentId == null || PackageNo == null || Quantity == null)){
                                //JSFUtils.addFacesInformationMessage("Enter product ,Treatment ,Package No and quantity for all selected records for Products");
                                JSFUtils.setExpressionValue("#{requestScope.ErrorMess3}", 1);
                                return;
                                }
                            ProductStrb.append(ProductId).append(","); 
                            TreatmentStrb.append(TreatmentId).append(","); 
                            PackageNoStrb.append(PackageNo).append(","); 
                            QuantityPStrb.append(Quantity).append(",");
                            ConfQuantStrb.append(ConfQuant).append(",");
                            ProductFlagStrb.append(ProductFlag).append(",");
                }
                rs1.closeRowSetIterator();
                if(ProductStrb.length()!=0 && TreatmentStrb.length()!=0 && PackageNoStrb.length()!=0 && QuantityPStrb.length()!=0 && ProductFlagStrb.length()!=0){
                ProductStr = ProductStrb.deleteCharAt(ProductStrb.length() - 1).toString();
                TreatmentStr = TreatmentStrb.deleteCharAt(TreatmentStrb.length() - 1).toString();
                PackageNoStr = PackageNoStrb.deleteCharAt(PackageNoStrb.length() - 1).toString();
                QuantityPStr = QuantityPStrb.deleteCharAt(QuantityPStrb.length() - 1).toString();
                ConfQuantStr = ConfQuantStrb.deleteCharAt(ConfQuantStrb.length() - 1).toString();
                ProductFlagStr = ProductFlagStrb.deleteCharAt(ProductFlagStrb.length() - 1).toString();
                }
                System.err.println("ProductStr " + ProductStr);
                System.err.println("TreatmentStr " + TreatmentStr);
                System.err.println("PackageNoStr " + PackageNoStr);
                System.err.println("QuantityPStr " + QuantityPStr);
                System.err.println("ConfQuantStr " + ConfQuantStr);
                System.err.println("ProductFlagStr " + ProductFlagStr);
                AssignmentStrb.append(ADFUtils.getBoundAttributeValue("NextDeliveryDate")).append(",")
                                .append(ADFUtils.getBoundAttributeValue("CloseCode")).append(",")
                                .append(ADFUtils.getBoundAttributeValue("CloseReason")).append(",")
                                .append("l").append(",")
                                .append(ADFUtils.getBoundAttributeValue("AssignmentId"));
                AssignmentStr = AssignmentStrb.toString();
                System.err.println("AssignmentStr "+AssignmentStr);
                ADFUtils.callStoredFunction((AppModuleAMImpl)ADFUtils.getApplicationModuleForDataControl("AppModuleAMDataControl"),
                                                                            Types.NUMERIC, "UPDATE_ASSIGNEMNT(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                                                            new Object[] {PromosFlagStr,PromosStr,QuantityStr,ContractInfoStr,AssignmentStr,ProductStr,TreatmentStr,PackageNoStr,QuantityPStr,ConfQuantStr,ProductFlagStr,ADFUtils.getBoundAttributeValue("WorkDate"),ADFUtils.getBoundAttributeValue("Id"), ADFContext.getCurrent().getSecurityContext().getUserName()});
                //ADFUtils.findOperation("Execute").execute();
                ADFUtils.findOperation("Rollback").execute();

                JSFUtils.hidePopup("p1");

               JSFUtils.addFacesInformationMessage("Assignment Created Successfully");
               // DCIteratorBinding iter1 = ADFUtils.findIterator("WeekDaysAgentVOIterator");
               // Key key1 = ADFUtils.findIterator("WeekDaysAgentVOIterator")
                           //       .getCurrentRow()
                            //      .getKey();
               // Row currentRow = ADFUtils.findIterator("WeekDaysAgentVOIterator").getCurrentRow();
               // DCIteratorBinding iter = ADFUtils.findIterator("AgentAreasVOIterator");
               // Key key = ADFUtils.findIterator("AgentAreasVOIterator")
                   //               .getCurrentRow()
                    //              .getKey();
               // iter1.setCurrentRowWithKey(key1.toStringFormat(true));
               // iter.setCurrentRowWithKey(key.toStringFormat(true));
            }
    }

    public void cancelSubmitPopup(PopupCanceledEvent popupCanceledEvent) {
        // Add event code here...
          DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
           LocalDateTime now = LocalDateTime.now();
           System.out.println("get key 1"+dtf.format(now));
        Key key = ADFUtils.findIterator("AreaContractVOIterator")
                          .getCurrentRow()
                          .getKey();
         now = LocalDateTime.now();
        System.out.println("get key 1"+dtf.format(now));
        System.err.println(key);

ViewObject schedule_vo=ADFUtils.findIterator("ContractDeliveryScheduleVOIterator").getViewObject();
schedule_vo.setQueryMode(ViewObject.QUERY_MODE_SCAN_DATABASE_TABLES);
schedule_vo.executeQuery();
       
        ViewObject vo3 = ADFUtils.findIterator("AreaContractVOIterator").getViewObject();
        now = LocalDateTime.now();
        System.out.println("get View Object"+dtf.format(now));
        vo3.clearCache();
        now = LocalDateTime.now();
        System.out.println("Clear Cache"+dtf.format(now));
        vo3.setQueryMode(ViewObject.QUERY_MODE_SCAN_DATABASE_TABLES);
        now = LocalDateTime.now();
        System.out.println("Set Query Mode"+dtf.format(now));
        vo3.executeQuery();
        now = LocalDateTime.now();
        System.out.println("Execute Query"+dtf.format(now));
        DCIteratorBinding iter=ADFUtils.findIterator("AreaContractVOIterator");
          iter.setCurrentRowWithKey(key.toStringFormat(true));
        now = LocalDateTime.now();
        System.out.println("Set Current Row"+dtf.format(now));
        System.err.println("Executed");
        
       
  /*      DCBindingContainer bindings1 = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
/*
        DCIteratorBinding iter1 = bindings1.findIteratorBinding("WeekDaysAgentVOIterator");
        oracle.jbo.Key k1 =  new oracle.jbo.Key(new Object[] { iter1.getViewObject().getCurrentRow().getAttribute("WorkingDate") });
*/
       /* DCIteratorBinding iter2 = bindings1.findIteratorBinding("AgentAreasVOIterator");
        oracle.jbo.Key k2 =  new oracle.jbo.Key(new Object[] { iter2.getViewObject().getCurrentRow().getAttribute("Id")  });
       now = LocalDateTime.now();
           System.out.println("get key 2"+dtf.format(now));
        ADFUtils.findOperation("Rollback").execute();
        now = LocalDateTime.now();
            System.out.println("rolled back"+dtf.format(now));    
            iter2.getViewObject().executeQuery();
        now = LocalDateTime.now();
            System.out.println("Query executed"+dtf.format(now));    
        oracle.jbo.Row r2= iter2.getViewObject().getRow(k2);
        now = LocalDateTime.now();
         System.out.println("get row2"+dtf.format(now));
        iter2.getViewObject().setCurrentRow(r2);
       /* DCIteratorBinding iter3 = bindings1.findIteratorBinding("AreaContractVOIterator");
        oracle.jbo.Key k3 =  new oracle.jbo.Key(new Object[] { iter3.getViewObject().getCurrentRow().getAttribute("CardNo") , iter3.getViewObject().getCurrentRow().getAttribute("WorkDate") , iter3.getViewObject().getCurrentRow().getAttribute("CoverDate") });
        now = LocalDateTime.now();
           System.out.println("get key 3"+dtf.format(now));
       Key key1 = ADFUtils.findIterator("AgentAreasVOIterator")
                         .getCurrentRow()
                         .getKey();
       System.err.println(key1);
       now = LocalDateTime.now();
                  System.out.println("get Agent Area Key"+dtf.format(now));
        
        Key key = ADFUtils.findIterator("AreaContractVOIterator")
                          .getCurrentRow()
                          .getKey();
        System.err.println(key);

        now = LocalDateTime.now();
                   System.out.println("get Area Contract key"+dtf.format(now));
                   
       
                   
        ADFUtils.findOperation("Rollback").execute();
        
          
        now = LocalDateTime.now();
                  System.out.println("roll back"+dtf.format(now));
        DCIteratorBinding iter1 = ADFUtils.findIterator("AgentAreasVOIterator1");
        now = LocalDateTime.now();
                   System.out.println("get Agent Area Iterator"+dtf.format(now));
         
        
        DCIteratorBinding iter = ADFUtils.findIterator("AreaContractVOIterator");
        now = LocalDateTime.now();
                   System.out.println("get Area Contract Iterator"+dtf.format(now));
        iter1.setCurrentRowWithKey(key1.toStringFormat(true));
        now = LocalDateTime.now();
        
                   System.out.println("Set Agent Area Row"+dtf.format(now));
    
      //  iter.setCurrentRowWithKey(key.toStringFormat(true));
        now = LocalDateTime.now();
                   System.out.println("Set Area Contract Row"+dtf.format(now));
                   
             
                      System.err.println("popup closed"); 
                      
*/
    }

    public void changeConfirmedQuantity(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        Long ConfirmedQuantity = (Long)valueChangeEvent.getNewValue();
        Long Quantity = (Long)ADFUtils.getBoundAttributeValue("Quantity1");
        System.out.println("Q:"+Quantity);
        System.out.println("CQ:"+ConfirmedQuantity);
        if(ConfirmedQuantity != null && Quantity != null && ConfirmedQuantity.compareTo(Quantity)>0){
                RichInputText component = (RichInputText)valueChangeEvent.getComponent();
                component.resetValue(); 
                AdfFacesContext.getCurrentInstance().addPartialTarget(component);
            JSFUtils.addFacesErrorMessage("Confirmed Quantity must be less than or equals Quantity");
            }
    }
public void ChangePromosPrice(ValueChangeEvent valueChangeEvent) {
        boolean isSelected = ((Boolean)valueChangeEvent.getNewValue()).booleanValue();

    System.out.println("rrr");
  
    if(isSelected)
    {
    Long num=0L;
    if((Long)ADFUtils.getBoundAttributeValue("PromosPrice")!=null) {
    num=(Long)ADFUtils.getBoundAttributeValue("PromosPrice");
    System.err.println("num:"+ADFUtils.getBoundAttributeValue("PromosPrice"));
    }
    //System.err.println("Total Price :"+row.getAttribute("Price"));
    //  System.err.println("Flag :"+row.getAttribute("DeliveryFlag1"));
    Long Quantity = (Long)ADFUtils.getBoundAttributeValue("PromoPrice");
    System.out.println(Quantity);
           if(Quantity!=null)
               
           num+=Quantity;
        
        ADFUtils.setBoundAttributeValue("PromosPrice", num);
        System.err.println("Product_total : "+num);
    }
    else {
        Long num=0L;
        
        if(ADFUtils.getBoundAttributeValue("PromosPrice")!=null) {
        Long num1=(Long)ADFUtils.getBoundAttributeValue("PromosPrice");
        num=num1;
        }
        
        //System.err.println("Total Price :"+row.getAttribute("Price"));
        //  System.err.println("Flag :"+row.getAttribute("DeliveryFlag1"));
        Long Quantity = (Long)ADFUtils.getBoundAttributeValue("PromoPrice");

               
              // num-=Quantity;
        ADFUtils.setBoundAttributeValue("PromosPrice", num);
        System.err.println("Product_total : "+num);
           
    }
    Long prom=0L;
    Long prod=0L;
    if(ADFUtils.getBoundAttributeValue("PromosPrice")!=null)
    {
     prom=(Long)ADFUtils.getBoundAttributeValue("PromosPrice");
    }
    if(ADFUtils.getBoundAttributeValue("productsprice")!=null)
    {    prod=(Long)ADFUtils.getBoundAttributeValue("productsprice");}
    Long Total=prom+prod;
    ADFUtils.setBoundAttributeValue("TotalOrder", Total);
}
public void Change_Product_Price(ValueChangeEvent valueChangeEvent) {
    ViewObject vo=ADFUtils.findIterator("ProductContractDeliveryVOIterator").getViewObject();
        boolean isSelected = ((Boolean)valueChangeEvent.getNewValue()).booleanValue();

    Row row=null;
    vo.reset();
    System.out.println("rrr");
    RowSetIterator rs=vo.createRowSetIterator(null);
    rs.reset();
    if(isSelected)
    {
    Long num=0L;
if((Long)ADFUtils.getBoundAttributeValue("productsprice")!=null) {
    num=(Long)ADFUtils.getBoundAttributeValue("productsprice");
    System.err.println("num:"+ADFUtils.getBoundAttributeValue("productsprice"));
}
//System.err.println("Total Price :"+row.getAttribute("Price"));
  //  System.err.println("Flag :"+row.getAttribute("DeliveryFlag1"));
    Long Quantity = (Long)ADFUtils.getBoundAttributeValue("Price");
System.out.println(Quantity);
           if(Quantity!=null)
               
           num+=Quantity;
        
        ADFUtils.setBoundAttributeValue("productsprice", num);
        System.err.println("Product_total : "+num);
    }
    else {
        Long num=0L;
        
        if(ADFUtils.getBoundAttributeValue("productsprice")!=null) {
        Long num1=(Long)ADFUtils.getBoundAttributeValue("productsprice");
        num=num1;
        }
        
        //System.err.println("Total Price :"+row.getAttribute("Price"));
        //  System.err.println("Flag :"+row.getAttribute("DeliveryFlag1"));
        Long Quantity = (Long)ADFUtils.getBoundAttributeValue("Price");

               if(Quantity!=null)
               {
               num-=Quantity;
               }
        ADFUtils.setBoundAttributeValue("productsprice", num);
        System.err.println("Product_total : "+num);
           
    }
    
}
    public void discloseCallResultTab(PopupFetchEvent disclosureEvent) {
        // Add event code here...
      //  System.err.println("Total Product :"+ADFUtils.getBoundAttributeValue("TotalProductPrice"));
        if(ADFUtils.findIterator("ContractDeliveryScheduleVOIterator").getCurrentRow()==null){
                ADFUtils.findOperation("CreateInsertConD").execute();
                
            }
        Long TotalProd=0L;
        Long TotalPromos=0L;
        Long TotalOrder=0L;
        String Pack_nums="";
        ViewObject vo1=ADFUtils.findIterator("ProductContractDeliveryVOIterator").getViewObject();
        Row row = null;
        
        ProductContractDeliveryVORowImpl row1 = null;
        vo1.reset();
        RowSetIterator rs1 = vo1.createRowSetIterator(null);
        rs1.reset();
        while (rs1.hasNext()) {
        row1 = (ProductContractDeliveryVORowImpl)rs1.next();
            Long pp=0L;
            if(row1.getAttribute("PackageNo")!=null && ((Long)row1.getAttribute("PackageNo")> 0)) {
                System.err.println("Package_no : "+row1.getAttribute("PackageNo"));
                if(Pack_nums.contains(row1.getAttribute("PackageNo").toString())) {
                    
                }
                else{Pack_nums+=row1.getAttribute("PackageNo")+",";
                        if (row1.getAttribute("Price")!=null)
                            pp=(Long)row1.getAttribute("Price");
                        TotalProd+=(pp)* ((Long)row1.getAttribute("Quantity"));
                    }
            }
            else{
            if (row1.getAttribute("Price")!=null)
                pp=(Long)row1.getAttribute("Price");
            TotalProd+=(pp)* ((Long)row1.getAttribute("Quantity"));
            }
            System.err.println("Total Products : "+TotalProd);
                   System.err.println("Quantity : "+row1.getAttribute("Quantity"));
            System.err.println("Price : "+row1.getAttribute("Price"));
                    Object Quantity = row1.getAttribute("Quantity");
                    Object ConfQuant = row1.getAttribute("Quantity");
                    Long Quantityp =(Long) row1.getAttribute("Quantity");
                    Object ProductFlag = row1.getAttribute("DeliveryFlag1");
                    Long ProductFlag1 = (Long)row1.getAttribute("DeliveryFlag1");
                    Object ProductConId = row1.getAttribute("ProductConId");
    }
        System.err.println("All Package No : "+Pack_nums);
        ViewObject promos_vo=ADFUtils.findIterator("PromosDeliveryVOIterator").getViewObject();
        Row Prow=null;
        RowSetIterator promo_rs=promos_vo.createRowSetIterator(null);
        while(promo_rs.hasNext()) {
            Prow=promo_rs.next();
            Long prom_price=(Long)Prow.getAttribute("PromoPrice");
            System.err.println("Promo Price : "+prom_price);
            TotalPromos+=prom_price;
        }
        TotalOrder=TotalProd+TotalPromos;
        ADFUtils.setBoundAttributeValue("productsprice", TotalProd);
        ADFUtils.setBoundAttributeValue("PromosPrice", TotalPromos);
        ADFUtils.setBoundAttributeValue("TotalOrder", TotalOrder);
        
    }
private Date now ;


    public void setNow(Date now) {
        this.now = now;
    }

    public Date getNow() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
        Date date = new Date();   
        String ParseDate = formatter.format(date);
        SimpleDateFormat formatter1=new SimpleDateFormat("yyyy-MM-dd");
        try {
            return formatter1.parse(ParseDate);
        } catch (ParseException e) {
        }
        return now;
    }

    public void ConfirmSubmitContracts(DialogEvent dialogEvent) {
        // Add event code here...
        if(dialogEvent.getOutcome().equals(dialogEvent.getOutcome().yes)) {
            submitAreaContract();
        }
    }

    public void ConfirmMarkAreaAsDone(DialogEvent dialogEvent) {
        // Add event code here...
        if(dialogEvent.getOutcome().equals(dialogEvent.getOutcome().yes)) {
            markAreaAsDone();
        }
    }

    public void ConfirmCreateTicket(DialogEvent dialogEvent) {
        // Add event code here...
        if(dialogEvent.getOutcome().equals(dialogEvent.getOutcome().yes)) {
            sumbitCreateTicket();
        }
    }

    public void ChangeProductDel(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        Row currentRow = ADFUtils.findIterator("ProductContractDeliveryVOIterator")
                                 .getViewObject()
                                 .getCurrentRow();
        System.err.println("Product : "+valueChangeEvent.getNewValue());

        String val=(valueChangeEvent.getNewValue()).toString();
        System.err.println("Product : "+val);
        System.err.println("SKU"+valueChangeEvent.getNewValue());
        ViewObject vo=ADFUtils.findIterator("ProductsAndPackagesROV1Iterator").getViewObject();
        //  System.err.println("ValueSku : "+ADFUtils.getBoundAttributeValue("ProductSku"));
        RowSetIterator rs=vo.createRowSetIterator(null);
        Row r=null;
       int check_pack=0;
        while(rs.hasNext()) {
            r=rs.next();
            String Val_Prod=r.getAttribute("ProductName").toString();
            if(Val_Prod.equals(val)) {
                if(((Long)r.getAttribute("PackFlag"))==1) {
                    check_pack=1;
                   ADFUtils.setBoundAttributeValue("SelectedId", r.getAttribute("ProductId").toString());
                   ADFUtils.setBoundAttributeValue("PackSelected", 1);
                    System.err.println("Selected Package : "+ADFUtils.getBoundAttributeValue("SelectedId"));
                   System.err.println("Prod_ids:"+r.getAttribute("DetIds"));
                   System.err.println("Quantity:"+r.getAttribute("DetQuan"));
                   String [] p_ids=r.getAttribute("DetIds").toString().split(",");
                   int len=p_ids.length;
                   for(int ind=0;ind<len;ind++) {
                       
                       if(ind==0) {
                           ADFUtils.findOperation("CreateProductContractDeliveryVO").execute();
                           AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("ProductContractDeliveryVOTab"));

                       }
                       else {
                           ADFUtils.findOperation("CreateProductContractDeliveryVO").execute();
                           AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("ProductContractDeliveryVOTab"));


                       }
                   }
                    ViewObject rv=ADFUtils.findIterator("ProductContractDeliveryVOIterator").getViewObject();
                    //  System.err.println("ValueSku : "+ADFUtils.getBoundAttributeValue("ProductSku"));
                    RowSetIterator rsv2=rv.createRowSetIterator(null);
                  //  rv.executeQuery();
                   System.err.println("LengthDet:"+len);
                   
                   String [] p_q=r.getAttribute("DetQuan").toString().split(",");
                   String [] p_names=r.getAttribute("DetNames").toString().split(",");
                  System.err.println("Length:"+r.getAttribute("DetIds").toString().length());
                    ViewObject vo2=ADFUtils.findIterator("ProductContractDeliveryVOIterator").getViewObject();
                    //  System.err.println("ValueSku : "+ADFUtils.getBoundAttributeValue("ProductSku"));
                    System.err.println("Duration : "+ADFUtils.getBoundAttributeValue("Duration"));
                    Long Pack_Price=0L;
                    if(ADFUtils.getBoundAttributeValue("Duration")!=null&&(Long)ADFUtils.getBoundAttributeValue("Duration")==1)
                    {
                    Pack_Price= (Long)r.getAttribute("Price4w");
                    }
                    else if(ADFUtils.getBoundAttributeValue("Duration")!=null&&(Long)ADFUtils.getBoundAttributeValue("Duration")==2) {
                        Pack_Price= (Long)r.getAttribute("Price2w");

                    }
                    else {
                        Pack_Price= (Long)r.getAttribute("Price1w");

                    }
                    Long Prod_Price =0L;
                    if( ADFUtils.getBoundAttributeValue("productsprice")!=null)
                        Prod_Price=(Long)ADFUtils.getBoundAttributeValue("productsprice")+Pack_Price;
                    System.err.println("Product Price"+Pack_Price);
                    Long Promo_Price=0L;
                    if(ADFUtils.getBoundAttributeValue("PromosPrice")!=null)
                        Promo_Price=(Long)ADFUtils.getBoundAttributeValue("PromosPrice");
                    Long Total_Amount=0L;
                    Total_Amount=Prod_Price+Promo_Price;
                    ADFUtils.setBoundAttributeValue("productsprice", Prod_Price);
                    ADFUtils.setBoundAttributeValue("TotalOrder", Total_Amount);
                    RowSetIterator rs2=vo2.createRowSetIterator(null);
                    
                    for(int c=0;c<p_ids.length;c++) {
                        System.err.println(c);
                        
                        if(c==0) {
                            Row r3=null;

                            r3=rs2.first();
                            r3.setAttribute("ProductName", p_names[c]);
                            r3.setAttribute("NewPackFlag", 1);
                            r3.setAttribute("ProductId", p_ids[c]);
                            if(ADFUtils.getBoundAttributeValue("Duration")!=null&&(Long)ADFUtils.getBoundAttributeValue("Duration")==1)
                            {
                            r3.setAttribute("Price", r.getAttribute("Price4w"));
                            }
                            else if(ADFUtils.getBoundAttributeValue("Duration")!=null&&(Long)ADFUtils.getBoundAttributeValue("Duration")==2) {
                                r3.setAttribute("Price", r.getAttribute("Price2w"));

                            }
                            else {
                                r3.setAttribute("Price", r.getAttribute("Price1w"));

                            }
                           
                            
                            r3.setAttribute("Quantity", p_q[c]);
                            r3.setAttribute("PartialSubmit", 1);
                            System.err.println("Pid set");
                            System.err.println("PName:"+r3.getAttribute("ProductId"));
                            System.err.println("PName:"+r3.getAttribute("ProductName"));

                            System.err.println("RowId:"+r3.getAttribute("Id"));

                            AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("productNameId"));
                            AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("ProductContractDeliveryVOTopLcSdi"));

                            AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("it26"));
                            AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("it4"));

                            AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("ProductContractDeliveryVOTab"));
                            FacesContext.getCurrentInstance().renderResponse();

                        }
                        else {
                            Long num=(Long)ADFUtils.getBoundAttributeValue("iDSEQ");

                            Row r4=null;

                            r4=rs2.next();
                            r4.setAttribute("ProductName", p_names[c]);
                            r4.setAttribute("NewPackFlag", 1);                         
                           r4.setAttribute("ProductId", p_ids[c]);

                            r4.setAttribute("Quantity", p_q[c]);
                            r4.setAttribute("PartialSubmit", 1);
                            System.err.println("RowId:"+r4.getAttribute("Id"));
                            System.err.println("RowId:"+r4.getAttribute("ProductName"));
                            if(ADFUtils.getBoundAttributeValue("Duration")!=null&&(Long)ADFUtils.getBoundAttributeValue("Duration")==1)
                            {
                            r4.setAttribute("Price", r.getAttribute("Price4w"));
                            }
                            else if(ADFUtils.getBoundAttributeValue("Duration")!=null&&(Long)ADFUtils.getBoundAttributeValue("Duration")==2) {
                                r4.setAttribute("Price", r.getAttribute("Price2w"));

                            }
                            else {
                                r4.setAttribute("Price", r.getAttribute("Price1w"));

                            }
                            System.err.println("Pid set");
                            System.err.println("PName:"+r4.getAttribute("ProductId"));
                                   
                            AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("productNameId"));
                            AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("ProductContractDeliveryVOTopLcSdi"));

                            AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("it26"));
                            AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("it4"));

                            AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("ProductContractDeliveryVOTab"));

         System.err.println("xxxx");
                        }
                    
                    }
                    
                    FacesContext.getCurrentInstance().renderResponse();

                   break;
                }
                else {
                    break;
                }
            }
        }
        System.err.println("ValuesSet");
       
     

       
if(check_pack==0)
{
    
     //   System.err.println(vo3.getAttributeDefs());
}
else {
    ViewObject vo3 = ADFUtils.findIterator("ProductContractDeliveryVOIterator").getViewObject();
    System.err.println("Package");

    vo3.executeQuery(); 
     FacesContext.getCurrentInstance().renderResponse();

}
        System.err.println("QueryExecuted");

  
    AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("productNameId"));
    System.err.println("1");
    AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("ProductContractDeliveryVOTopLcSdi"));

    AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("it26"));
    
        AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("ProductContractDeliveryVOTab"));
      //   FacesContext.getCurrentInstance().renderResponse();

        System.err.println("2");


    }

    public void productidattChanged(AttributeChangeEvent attributeChangeEvent) {
        // Add event code here...
        System.out.println(attributeChangeEvent.getNewValue());
    }

    public void ConfirmSubmitPopoup(DialogEvent dialogEvent) {
        // Add event code here...
        System.err.println("Here");
        if(dialogEvent.getOutcome().equals(dialogEvent.getOutcome().yes)) {
            submitPopup();
        }
        
    }

    public void ChangePromoSku(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        System.err.println("SKU"+valueChangeEvent.getNewValue());
        ViewObject vo=ADFUtils.findIterator("PromosLookupVO1Iterator").getViewObject();
      //  System.err.println("ValueSku : "+ADFUtils.getBoundAttributeValue("ProductSku"));
        RowSetIterator rs=vo.createRowSetIterator(null);
        int index_val=1;
        Row r=null;
        Long promos_amount=(Long)ADFUtils.getBoundAttributeValue("PromosPrice");
        Long total_amount=(Long)ADFUtils.getBoundAttributeValue("TotalOrder");
        int i = ((Integer) valueChangeEvent.getNewValue()).intValue();
        System.err.println("selected : "+i);
        while(rs.hasNext()) {
            r=rs.next();
            System.err.println("index : "+index_val);
       
            
          if(index_val==i) {
              System.err.println("Promo:"+r.getAttribute("ProductId"));
              System.err.println("Price : "+r.getAttribute("SellingPrice"));
              Long p_price=(Long)r.getAttribute("SellingPrice");
              System.err.println("promos before : "+promos_amount);
              promos_amount+=p_price;
              System.err.println("promos after : "+promos_amount);
              ADFUtils.setBoundAttributeValue("PromosPrice", promos_amount);
              System.err.println("total before : "+total_amount);
              total_amount+=p_price;
              System.err.println("total after : "+total_amount);
              ADFUtils.setBoundAttributeValue("TotalOrder", total_amount);
ADFUtils.setBoundAttributeValue("PromoPrice", p_price);
              AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("PromosDeliveryVOTab"));

              break;
          }
              index_val++;
           /* int x =(int)valueChangeEvent.getNewValue().;
            System.err.println(x);
            if(index_val==x) {
               
            }
            index_val++;*/
        }
    }

    public void ChangeProductTreatmentPrice(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        System.err.println("SKU"+valueChangeEvent.getNewValue());
        ViewObject treatments_vo=ADFUtils.findIterator("TreatmentTypesROV1Iterator").getViewObject();
        RowSetIterator tr_rs=treatments_vo.createRowSetIterator(null);
        int i = ((Integer) valueChangeEvent.getNewValue()).intValue();
Long treatment_id=0L;
       int index_val=0;
        Row tr=null;
        
        while(tr_rs.hasNext()) {
            tr=tr_rs.next();
            if(i==index_val) {
                treatment_id=(Long)tr.getAttribute("TreatmentId");
                System.err.println("Treatment :"+treatment_id);
                break;
            }
            index_val++;
        }
        
        
        ViewObject vo=ADFUtils.findIterator("ProductContractDeliveryVOIterator").getViewObject();
        //  System.err.println("ValueSku : "+ADFUtils.getBoundAttributeValue("ProductSku"));
        RowSetIterator rs=vo.createRowSetIterator(null);
       // int index_val=1;
        Row r=null;
        r=rs.first();
        String x_sku=(String)r.getAttribute("ProductId");
        Long n_pack_flag=0L;
        System.err.println("New Pack Flag :"+r.getAttribute("NewPackFlag"));
        System.err.println("PartialSubmit : "+r.getAttribute("PartialSubmit"));
        if(r.getAttribute("PartialSubmit")!=null&&(Long)r.getAttribute("PartialSubmit")==1) {
            return;
        }
            
        System.err.println("SKU : "+r.getAttribute("ProductId"));
        System.err.println("Treatment : "+r.getAttribute("TreatmentId"));
        ViewObject productprice_vo=ADFUtils.findIterator("ProductRentPriceVO1Iterator").getViewObject();
        System.err.println("Duration : "+ADFUtils.getBoundAttributeValue("Duration"));
        RowSetIterator productprice_rs=productprice_vo.createRowSetIterator(null);
        Row pr=null;
        while(productprice_rs.hasNext()) {
            pr=productprice_rs.next();
            if(x_sku.equals(pr.getAttribute("DmsProductId")) && ((Long)pr.getAttribute("TreatmentId")).equals(treatment_id)) {
                System.err.println("xx");
                System.err.println("Duration : "+ADFUtils.getBoundAttributeValue("Duration"));
                Long duration_val=(Long)ADFUtils.getBoundAttributeValue("Duration");
                if(duration_val==1) {
                    System.err.println("4w : "+pr.getAttribute("Price4w"));
                    r.setAttribute("Price", pr.getAttribute("Price4w"));
                }
                else if(duration_val==2) {
                    System.err.println("2W : "+pr.getAttribute("Price2w"));
                    r.setAttribute("Price", pr.getAttribute("Price2w"));
                }
                else if(duration_val==3) {
                    System.err.println("4w : "+pr.getAttribute("Price4w"));
                    r.setAttribute("Price", pr.getAttribute("Price4w"));
                }
                else {
                    System.err.println("1w : "+pr.getAttribute("Price1w"));
                    r.setAttribute("Price", pr.getAttribute("Price1w"));
                    
                }
                Long prod_amount = (Long)ADFUtils.getBoundAttributeValue("productsprice");
                Long total_amount=(Long)ADFUtils.getBoundAttributeValue("TotalOrder");
                System.err.println("product_amount before : "+prod_amount);
              if(r.getAttribute("Price")!=null)
                prod_amount+=(Long)r.getAttribute("Price");
                System.err.println("product_amount before : "+prod_amount);
                System.err.println("total before : "+total_amount);
                if(r.getAttribute("Price")!=null)
                total_amount+=(Long)r.getAttribute("Price");
                System.err.println("total before : "+total_amount);
ADFUtils.setBoundAttributeValue("productsprice", prod_amount);
                ADFUtils.setBoundAttributeValue("TotalOrder", total_amount);
   
                
                break;
            }
        }
        ADFUtils.setBoundAttributeValue("Price", (Long)r.getAttribute("Price"));

        rs.closeRowSetIterator();
        AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("ProductContractDeliveryVOTab"));

    }

    public void DeliveryQuantityChange(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        Row currentRow = ADFUtils.findIterator("ProductContractDeliveryVOIterator")
                                 .getViewObject()
                                 .getCurrentRow();
        System.err.println("Product : "+currentRow.getAttribute("ProductId"));
        System.err.println("Price : "+currentRow.getAttribute("Price"));
        System.err.println("Quantity : "+valueChangeEvent.getNewValue());
        Long quan= (Long) valueChangeEvent.getNewValue();
        Long prod_price=(Long)ADFUtils.getBoundAttributeValue("productsprice");
       System.err.println("Product Price Before :"+prod_price);
       Long old_quan=(Long) valueChangeEvent.getOldValue();
        prod_price-=(((Long)currentRow.getAttribute("Price")))*old_quan;
        prod_price+=(((Long)currentRow.getAttribute("Price")))*quan;
        System.err.println("Products after"+prod_price);
        ADFUtils.setBoundAttributeValue("productsprice",prod_price );
        Long totals=(Long)ADFUtils.getBoundAttributeValue("TotalOrder");
        System.err.println("total before : "+totals);
        totals-=(((Long)currentRow.getAttribute("Price")))*old_quan;

        totals+=(((Long)currentRow.getAttribute("Price")))*quan;
        System.err.println("total after : "+totals);

        ADFUtils.setBoundAttributeValue("TotalOrder", totals);
    }

    public void PromosDeliveryQuantityChange(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        Row currentRow = ADFUtils.findIterator("PromosDeliveryVOIterator")
                                 .getViewObject()
                                 .getCurrentRow();
        System.err.println("Product : "+currentRow.getAttribute("ProductSku"));
        System.err.println("Price : "+currentRow.getAttribute("PromoPrice"));
        System.err.println("Quantity : "+valueChangeEvent.getNewValue());
        Long quan= (Long) valueChangeEvent.getNewValue();
        Long old_quan=(Long) valueChangeEvent.getOldValue();

        Long prod_price=(Long)ADFUtils.getBoundAttributeValue("PromosPrice");
        prod_price-=(((Long)currentRow.getAttribute("PromoPrice")))*old_quan;

        prod_price+=(((Long)currentRow.getAttribute("PromoPrice")))*quan;
        System.err.println("promos"+prod_price);
        ADFUtils.setBoundAttributeValue("PromosPrice",prod_price );
        Long totals=(Long)ADFUtils.getBoundAttributeValue("TotalOrder");
        totals-=(((Long)currentRow.getAttribute("PromoPrice")))*old_quan;

        totals+=(((Long)currentRow.getAttribute("PromoPrice")))*quan;
        ADFUtils.setBoundAttributeValue("TotalOrder", totals);

    }

    public void ValidateProductsMatchPackage(ActionEvent actionEvent) {
        // Add event code here...
        ViewObject vo1 = ADFUtils.findIterator("ProductContractDeliveryVOIterator").getViewObject();
        StringBuilder ProductStrb = new StringBuilder("");
        StringBuilder TreatmentStrb = new StringBuilder("");
        StringBuilder PackageNoStrb = new StringBuilder("");
        StringBuilder QuantityPStrb = new StringBuilder("");
        StringBuilder ConfQuantStrb = new StringBuilder("");
        StringBuilder ProductFlagStrb = new StringBuilder("");
        StringBuilder AssignmentStrb = new StringBuilder("");
        String ProductStr = "";
        String TreatmentStr = "";
        String PackageNoStr = "";
        String QuantityPStr = "";
        String ConfQuantStr = "";
        String ProductFlagStr = "";
        String AssignmentStr = "";
        ProductContractDeliveryVORowImpl row1 = null;
        vo1.reset();
        RowSetIterator rs1 = vo1.createRowSetIterator(null);
        rs1.reset();
        while (rs1.hasNext()) {
           
        row1 = (ProductContractDeliveryVORowImpl)rs1.next();
                    Object ProductId = row1.getAttribute("ProductId");
                    Object TreatmentId = row1.getAttribute("TreatmentId");
                    Object PackageNo = row1.getAttribute("PackageNo");
                    Object Quantity = row1.getAttribute("Quantity");
                    Long Quantityp =(Long) row1.getAttribute("Quantity");
                    Object ConfQuant = row1.getAttribute("ConfQuant");
                    Object ProductFlag = row1.getAttribute("DeliveryFlag1");
                    Long ProductFlag1 = (Long)row1.getAttribute("DeliveryFlag1");
                    if(row1.getAttribute("DeliveryFlag1")!=null && ProductFlag1 == 1 && (Quantity == null || Quantityp <= 0)){
                            //JSFUtils.addFacesInformationMessage("Enter quantity for all selected records for Products with value > 0");
                            JSFUtils.setExpressionValue("#{requestScope.ErrorMess2}", 1);
                            return;
                        }
            if((Long)PackageNo==0)
            {
            System.err.println("Product : "+ProductId);
            System.err.println("Treatemnt : "+TreatmentId);
            System.err.println("Quantity : "+Quantity);
                    if(row1.getEntity(0).getEntityState()== Entity.STATUS_NEW  && (ProductId == null || TreatmentId == null ||  Quantity == null)){
                        //JSFUtils.addFacesInformationMessage("Enter product ,Treatment ,Package No and quantity for all selected records for Products");
                        JSFUtils.addFacesErrorMessage("You Should Enter Product , treatment , quantity before validation");

                        JSFUtils.setExpressionValue("#{requestScope.ErrorMess3}", 1);
                        return;
                        }
            if(TreatmentId==null || ProductId ==null ||Quantity==null) {
                JSFUtils.addFacesErrorMessage("You Should Select All data before validation");
                return ;
            }
            
                    ProductStrb.append(ProductId).append(","); 
                    TreatmentStrb.append(TreatmentId).append(","); 
                    PackageNoStrb.append(PackageNo).append(","); 
                    QuantityPStrb.append(Quantity).append(",");
                    ConfQuantStrb.append(ConfQuant).append(",");
                    ProductFlagStrb.append(ProductFlag).append(",");
            }
        }
        rs1.closeRowSetIterator();
        if(ProductStrb.length()!=0 && TreatmentStrb.length()!=0 && PackageNoStrb.length()!=0 && QuantityPStrb.length()!=0 && ProductFlagStrb.length()!=0){
        ProductStr = ProductStrb.deleteCharAt(ProductStrb.length() - 1).toString();
        TreatmentStr = TreatmentStrb.deleteCharAt(TreatmentStrb.length() - 1).toString();
        PackageNoStr = PackageNoStrb.deleteCharAt(PackageNoStrb.length() - 1).toString();
        QuantityPStr = QuantityPStrb.deleteCharAt(QuantityPStrb.length() - 1).toString();
        ConfQuantStr = ConfQuantStrb.deleteCharAt(ConfQuantStrb.length() - 1).toString();
        ProductFlagStr = ProductFlagStrb.deleteCharAt(ProductFlagStrb.length() - 1).toString();
        }
        System.err.println("ProductStr " + ProductStr);
        System.err.println("TreatmentStr " + TreatmentStr);
        System.err.println("PackageNoStr " + PackageNoStr);
        System.err.println("QuantityPStr " + QuantityPStr);
        System.err.println("ConfQuantStr " + ConfQuantStr);
        System.err.println("ProductFlagStr " + ProductFlagStr);
       String x_out =
            (String) ADFUtils.callStoredFunction((AppModuleAMImpl) ADFUtils.getApplicationModuleForDataControl("AppModuleAMDataControl"),
                                                 Types.VARCHAR, "VALIDATE_PACKAGE_PRODUCTS(?,?,?,?)",
                                                 new Object[] {ProductStr ,TreatmentStr , QuantityPStr , ADFUtils.getBoundAttributeValue("CardNo")  });
     System.out.println("Out Submit : "+x_out);   
     if(x_out!=null) {
         ADFUtils.setBoundAttributeValue("CheckPackageName", "Your Selections Match Package "+x_out+"Do You Want To Replace With Package Price ?");
         ADFUtils.setBoundAttributeValue("VALID", 0);

         JSFUtils.showPopup("p7");
     }
     else {
ADFUtils.setBoundAttributeValue("CheckPackageName", null);
         ADFUtils.setBoundAttributeValue("VALID", 1);
         
             AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("b4"));

         JSFUtils.addFacesInformationMessage("Your Product Contract Is Valid");
         
         }
    }

    public void ChangeToPackage(DialogEvent dialogEvent) {
        // Add event code here...
        if(dialogEvent.getOutcome().equals(dialogEvent.getOutcome().yes)) {
            ViewObject vo1 = ADFUtils.findIterator("ProductContractDeliveryVOIterator").getViewObject();
            StringBuilder ProductStrb = new StringBuilder("");
            StringBuilder TreatmentStrb = new StringBuilder("");
            StringBuilder PackageNoStrb = new StringBuilder("");
            StringBuilder QuantityPStrb = new StringBuilder("");
            StringBuilder ConfQuantStrb = new StringBuilder("");
            StringBuilder ProductFlagStrb = new StringBuilder("");
            StringBuilder AssignmentStrb = new StringBuilder("");
            StringBuilder ProductConId=new StringBuilder("");
            String ProductStr = "";
            String TreatmentStr = "";
            String PackageNoStr = "";
            String QuantityPStr = "";
            String ConfQuantStr = "";
            String ProductFlagStr = "";
            String AssignmentStr = "";
            String ProductConIdStr="";
            ProductContractDeliveryVORowImpl row1 = null;
            vo1.reset();
            RowSetIterator rs1 = vo1.createRowSetIterator(null);
            rs1.reset();
            while (rs1.hasNext()) {
            row1 = (ProductContractDeliveryVORowImpl)rs1.next();
                        Object ProductId = row1.getAttribute("ProductId");
                        Object TreatmentId = row1.getAttribute("TreatmentId");
                        Object PackageNo = row1.getAttribute("PackageNo");
                        Object Quantity = row1.getAttribute("Quantity");
                        Long Quantityp =(Long) row1.getAttribute("Quantity");
                        Object ConfQuant = row1.getAttribute("ConfQuant");
                        Object ProductFlag = row1.getAttribute("DeliveryFlag1");
                        Long ProductFlag1 = (Long)row1.getAttribute("DeliveryFlag1");
                       Long ProdConId=(Long)row1.getAttribute("ProductConId");
                        if(row1.getAttribute("DeliveryFlag1")!=null && ProductFlag1 == 1 && (Quantity == null || Quantityp <= 0)){
                                //JSFUtils.addFacesInformationMessage("Enter quantity for all selected records for Products with value > 0");
                                JSFUtils.setExpressionValue("#{requestScope.ErrorMess2}", 1);
                                return;
                            }
                        if(row1.getEntity(0).getEntityState()== Entity.STATUS_NEW  && (ProductId == null || TreatmentId == null || PackageNo == null || Quantity == null)){
                            //JSFUtils.addFacesInformationMessage("Enter product ,Treatment ,Package No and quantity for all selected records for Products");
                            JSFUtils.setExpressionValue("#{requestScope.ErrorMess3}", 1);
                            return;
                            }
                        ProductStrb.append(ProductId).append(","); 
                        TreatmentStrb.append(TreatmentId).append(","); 
                        PackageNoStrb.append(PackageNo).append(","); 
                        QuantityPStrb.append(Quantity).append(",");
                        ConfQuantStrb.append(ConfQuant).append(",");
                        ProductFlagStrb.append(ProductFlag).append(",");
                ProductConId.append(ProdConId).append(",");
            }
            rs1.closeRowSetIterator();
            if(ProductStrb.length()!=0 && TreatmentStrb.length()!=0 && PackageNoStrb.length()!=0 && QuantityPStrb.length()!=0 && ProductFlagStrb.length()!=0){
            ProductStr = ProductStrb.deleteCharAt(ProductStrb.length() - 1).toString();
            TreatmentStr = TreatmentStrb.deleteCharAt(TreatmentStrb.length() - 1).toString();
            PackageNoStr = PackageNoStrb.deleteCharAt(PackageNoStrb.length() - 1).toString();
            QuantityPStr = QuantityPStrb.deleteCharAt(QuantityPStrb.length() - 1).toString();
            ConfQuantStr = ConfQuantStrb.deleteCharAt(ConfQuantStrb.length() - 1).toString();
            ProductFlagStr = ProductFlagStrb.deleteCharAt(ProductFlagStrb.length() - 1).toString();
                ProductConIdStr=ProductConId.deleteCharAt(ProductConId.length()-1).toString();
            }
            System.err.println("ProductStr " + ProductStr);
            System.err.println("TreatmentStr " + TreatmentStr);
            System.err.println("PackageNoStr " + PackageNoStr);
            System.err.println("QuantityPStr " + QuantityPStr);
            System.err.println("ConfQuantStr " + ConfQuantStr);
            System.err.println("ProductFlagStr " + ProductFlagStr);
            System.err.println("ProductConId"+ProductConIdStr);
            
            ADFUtils.callStoredFunction((AppModuleAMImpl) ADFUtils.getApplicationModuleForDataControl("AppModuleAMDataControl"),
                                                      Types.VARCHAR, "UPDATE_PACKAGE_PRODUCTS(?,?,?,?,?)",
                                                      new Object[] {ProductStr ,TreatmentStr , QuantityPStr , ADFUtils.getBoundAttributeValue("CardNo") , ProductConIdStr });
           // ADFUtils.findOperation("Rollback").execute();

            ViewObject vo3 = ADFUtils.findIterator("ProductContractDeliveryVOIterator").getViewObject();
            vo3.clearCache();
            vo3.executeQuery();
            // ADFUtils.findOperation("Execute").execute();
System.err.println("xxxxxxx");
            vo3.executeQuery();

            AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("ProductContractDeliveryVOTab"));
            vo3.setQueryMode(ViewObject.QUERY_MODE_SCAN_DATABASE_TABLES);

             vo3.executeQuery();
            
            // ADFUtils.findOperation("Rollback").execute();
            RowSetIterator rss = vo3.createRowSetIterator(null);
            ProductContractDeliveryVORowImpl row=null;
            rss.reset();
            while (rss.hasNext()) {
              row = (ProductContractDeliveryVORowImpl)rss.next();
              if(row.getEntity(0).getEntityState()== Entity.STATUS_NEW ){
                  vo3.removeCurrentRow();
              }
              else {
                  break;
              }
            }
             ADFUtils.findOperation("Execute1").execute();

            System.err.println("xxxxxxx");
            // vo3.executeQuery();
             AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("ProductContractDeliveryVOTab"));
             
            ADFUtils.setBoundAttributeValue("VALID", 1);
            Long TotalProd=0L;
            Long TotalPromos=0L;
            Long TotalOrder=0L;
            String Pack_nums="";
            ViewObject vo11=ADFUtils.findIterator("ProductContractDeliveryVOIterator").getViewObject();
            Row row11 = null;
            
            ProductContractDeliveryVORowImpl row21 = null;
            vo1.reset();
            RowSetIterator rs11 = vo11.createRowSetIterator(null);
            rs11.reset();
            while (rs11.hasNext()) {
            row21 = (ProductContractDeliveryVORowImpl)rs11.next();
                Long pp=0L;
                if(row21.getAttribute("PackageNo")!=null && ((Long)row21.getAttribute("PackageNo")> 0)) {
                    System.err.println("Package_no : "+row21.getAttribute("PackageNo"));
                    if(Pack_nums.contains(row21.getAttribute("PackageNo").toString())) {
                        
                    }
                    else{Pack_nums+=row21.getAttribute("PackageNo")+",";
                            if (row21.getAttribute("Price")!=null)
                                pp=(Long)row21.getAttribute("Price");
                            TotalProd+=(pp)* ((Long)row21.getAttribute("Quantity"));
                        }
                }
                else{
                if (row21.getAttribute("Price")!=null)
                    pp=(Long)row21.getAttribute("Price");
                TotalProd+=(pp)* ((Long)row21.getAttribute("Quantity"));
                }
                System.err.println("Total Products : "+TotalProd);
                       System.err.println("Quantity : "+row21.getAttribute("Quantity"));
                System.err.println("Price : "+row21.getAttribute("Price"));
                        Object Quantity = row21.getAttribute("Quantity");
                        Object ConfQuant = row21.getAttribute("Quantity");
                        Long Quantityp =(Long) row21.getAttribute("Quantity");
                        Object ProductFlag = row21.getAttribute("DeliveryFlag1");
                        Long ProductFlag1 = (Long)row21.getAttribute("DeliveryFlag1");
                     //   Object ProductConId = row1.getAttribute("ProductConId");
            }
            System.err.println("All Package No : "+Pack_nums);
            ViewObject promos_vo=ADFUtils.findIterator("PromosDeliveryVOIterator").getViewObject();
            Row Prow=null;
            RowSetIterator promo_rs=promos_vo.createRowSetIterator(null);
            while(promo_rs.hasNext()) {
                Prow=promo_rs.next();
                Long prom_price=(Long)Prow.getAttribute("PromoPrice");
                System.err.println("Promo Price : "+prom_price);
                TotalPromos+=prom_price;
            }
            TotalOrder=TotalProd+TotalPromos;
            ADFUtils.setBoundAttributeValue("productsprice", TotalProd);
            ADFUtils.setBoundAttributeValue("PromosPrice", TotalPromos);
            ADFUtils.setBoundAttributeValue("TotalOrder", TotalOrder);
            
        JSFUtils.addFacesInformationMessage("Contract Updated Successfully");
        }
        
        
    }
    
    
    
    public void ChangeToPackageSelected(DialogEvent dialogEvent) {
        // Add event code here...
        if(dialogEvent.getOutcome().equals(dialogEvent.getOutcome().yes)) {
            ViewObject vo1 = ADFUtils.findIterator("ProductContractDeliveryVOIterator").getViewObject();
            StringBuilder ProductStrb = new StringBuilder("");
            StringBuilder TreatmentStrb = new StringBuilder("");
            StringBuilder PackageNoStrb = new StringBuilder("");
            StringBuilder QuantityPStrb = new StringBuilder("");
            StringBuilder ConfQuantStrb = new StringBuilder("");
            StringBuilder ProductFlagStrb = new StringBuilder("");
            StringBuilder AssignmentStrb = new StringBuilder("");
            StringBuilder ProductConId=new StringBuilder("");
            String ProductStr = "";
            String TreatmentStr = "";
            String PackageNoStr = "";
            String QuantityPStr = "";
            String ConfQuantStr = "";
            String ProductFlagStr = "";
            String AssignmentStr = "";
            String ProductConIdStr="";
            ProductContractDeliveryVORowImpl row1 = null;
            vo1.reset();
            RowSetIterator rs1 = vo1.createRowSetIterator(null);
            rs1.reset();
            while (rs1.hasNext()) {
            row1 = (ProductContractDeliveryVORowImpl)rs1.next();
                        Object ProductId = row1.getAttribute("ProductId");
                        Object TreatmentId = row1.getAttribute("TreatmentId");
                        Object PackageNo = row1.getAttribute("PackageNo");
                        Object Quantity = row1.getAttribute("Quantity");
                        Long Quantityp =(Long) row1.getAttribute("Quantity");
                        Object ConfQuant = row1.getAttribute("ConfQuant");
                        Object ProductFlag = row1.getAttribute("DeliveryFlag1");
                        Long ProductFlag1 = (Long)row1.getAttribute("DeliveryFlag1");
                       Long ProdConId=(Long)row1.getAttribute("ProductConId");
                        if(row1.getAttribute("DeliveryFlag1")!=null && ProductFlag1 == 1 && (Quantity == null || Quantityp <= 0)){
                                //JSFUtils.addFacesInformationMessage("Enter quantity for all selected records for Products with value > 0");
                                JSFUtils.setExpressionValue("#{requestScope.ErrorMess2}", 1);
                                return;
                            }
                        if(row1.getEntity(0).getEntityState()== Entity.STATUS_NEW  && (ProductId == null || TreatmentId == null || PackageNo == null || Quantity == null)){
                            JSFUtils.addFacesInformationMessage("Enter Treatment for all selected records for Products");
                           // JSFUtils.setExpressionValue("#{requestScope.ErrorMess3}", 1);
                            return;
                            }
                System.err.println("Flag:"+row1.getAttribute("NewPackFlag"));
                if(row1.getAttribute("ProductConId")==null)
                {
                   
                        System.err.println("PackProduct");
                        ProductStrb.append(ProductId).append(","); 
                        TreatmentStrb.append(TreatmentId).append(","); 
                        PackageNoStrb.append(PackageNo).append(","); 
                        QuantityPStrb.append(Quantity).append(",");
                       ConfQuantStrb.append(ConfQuant).append(",");
                        ProductFlagStrb.append(ProductFlag).append(",");
                ProductConId.append(ProdConId).append(",");
                    
                
                }
            }
            rs1.closeRowSetIterator();
            if(ProductStrb.length()!=0 && TreatmentStrb.length()!=0 && PackageNoStrb.length()!=0 && QuantityPStrb.length()!=0 && ProductFlagStrb.length()!=0){
            ProductStr = ProductStrb.deleteCharAt(ProductStrb.length() - 1).toString();
            TreatmentStr = TreatmentStrb.deleteCharAt(TreatmentStrb.length() - 1).toString();
            PackageNoStr = PackageNoStrb.deleteCharAt(PackageNoStrb.length() - 1).toString();
            QuantityPStr = QuantityPStrb.deleteCharAt(QuantityPStrb.length() - 1).toString();
            ConfQuantStr = ConfQuantStrb.deleteCharAt(ConfQuantStrb.length() - 1).toString();
            ProductFlagStr = ProductFlagStrb.deleteCharAt(ProductFlagStrb.length() - 1).toString();
                ProductConIdStr=ProductConId.deleteCharAt(ProductConId.length()-1).toString();
            }
            System.err.println("ProductStr " + ProductStr);
            System.err.println("TreatmentStr " + TreatmentStr);
            System.err.println("PackageNoStr " + PackageNoStr);
            System.err.println("QuantityPStr " + QuantityPStr);
            System.err.println("ConfQuantStr " + ConfQuantStr);
            System.err.println("ProductFlagStr " + ProductFlagStr);
            System.err.println("ProductConId"+ProductConIdStr);
            System.err.println("SelectedPackage : "+ADFUtils.getBoundAttributeValue("SelectedId"));
            ADFUtils.callStoredFunction((AppModuleAMImpl) ADFUtils.getApplicationModuleForDataControl("AppModuleAMDataControl"),
                                                      Types.VARCHAR, "UPDATE_CON_PACKAGE_PRODUCTS(?,?,?,?,?,?)",
                                                      new Object[] {ProductStr ,TreatmentStr , QuantityPStr , ADFUtils.getBoundAttributeValue("CardNo") , ProductConIdStr , ADFUtils.getBoundAttributeValue("SelectedId")});
           // ADFUtils.findOperation("Rollback").execute();

            ViewObject vo3 = ADFUtils.findIterator("ProductContractDeliveryVOIterator").getViewObject();
            vo3.clearCache();
           vo3.setQueryMode(ViewObject.QUERY_MODE_SCAN_DATABASE_TABLES);

            vo3.executeQuery();
           
         // ADFUtils.findOperation("Rollback").execute();
         RowSetIterator rss = vo3.createRowSetIterator(null);
         ProductContractDeliveryVORowImpl row=null;
         rss.reset();
         while (rss.hasNext()) {
             row = (ProductContractDeliveryVORowImpl)rss.next();
             if(row.getEntity(0).getEntityState()== Entity.STATUS_NEW ){
                 vo3.removeCurrentRow();
             }
             else {
                 break;
             }
         }
            ADFUtils.findOperation("Execute1").execute();

          System.err.println("xxxxxxx");
           // vo3.executeQuery();
            AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("ProductContractDeliveryVOTab"));
            
            ADFUtils.setBoundAttributeValue("VALID", 1);
            ADFUtils.setBoundAttributeValue("PackSelected", 0);
            Long TotalProd=0L;
            Long TotalPromos=0L;
            Long TotalOrder=0L;
            String Pack_nums="";
            ViewObject vo11=ADFUtils.findIterator("ProductContractDeliveryVOIterator").getViewObject();
            Row row11 = null;
            
            ProductContractDeliveryVORowImpl row21 = null;
            vo1.reset();
            RowSetIterator rs11 = vo11.createRowSetIterator(null);
            rs11.reset();
            while (rs11.hasNext()) {
            row21 = (ProductContractDeliveryVORowImpl)rs11.next();
                Long pp=0L;
                if(row21.getAttribute("PackageNo")!=null && ((Long)row21.getAttribute("PackageNo")> 0)) {
                    System.err.println("Package_no : "+row21.getAttribute("PackageNo"));
                    if(Pack_nums.contains(row21.getAttribute("PackageNo").toString())) {
                        
                    }
                    else{Pack_nums+=row21.getAttribute("PackageNo")+",";
                            if (row21.getAttribute("Price")!=null)
                                pp=(Long)row21.getAttribute("Price");
                            TotalProd+=(pp)* ((Long)row21.getAttribute("Quantity"));
                        }
                }
                else{
                if (row21.getAttribute("Price")!=null)
                    pp=(Long)row21.getAttribute("Price");
                TotalProd+=(pp)* ((Long)row21.getAttribute("Quantity"));
                }
                System.err.println("Total Products : "+TotalProd);
                       System.err.println("Quantity : "+row21.getAttribute("Quantity"));
                System.err.println("Price : "+row21.getAttribute("Price"));
                        Object Quantity = row21.getAttribute("Quantity");
                        Object ConfQuant = row21.getAttribute("Quantity");
                        Long Quantityp =(Long) row21.getAttribute("Quantity");
                        Object ProductFlag = row21.getAttribute("DeliveryFlag1");
                        Long ProductFlag1 = (Long)row21.getAttribute("DeliveryFlag1");
                     //   Object ProductConId = row1.getAttribute("ProductConId");
            }
            System.err.println("All Package No : "+Pack_nums);
            ViewObject promos_vo=ADFUtils.findIterator("PromosDeliveryVOIterator").getViewObject();
            Row Prow=null;
            RowSetIterator promo_rs=promos_vo.createRowSetIterator(null);
            while(promo_rs.hasNext()) {
                Prow=promo_rs.next();
                Long prom_price=(Long)Prow.getAttribute("PromoPrice");
                System.err.println("Promo Price : "+prom_price);
                TotalPromos+=prom_price;
            }
            TotalOrder=TotalProd+TotalPromos;
            ADFUtils.setBoundAttributeValue("productsprice", TotalProd);
            ADFUtils.setBoundAttributeValue("PromosPrice", TotalPromos);
            ADFUtils.setBoundAttributeValue("TotalOrder", TotalOrder);
            
        JSFUtils.addFacesInformationMessage("Contract Updated Successfully");
        }
        
        
    }
    

    public void ProdIdAttrChanged(AttributeChangeEvent valueChangeEvent) {
        // Add event code here...
        System.err.println("New Value"+valueChangeEvent.getNewValue());
    }

    public void ChangeProductDeliveryPrice(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        System.err.println("New Price :"+valueChangeEvent.getNewValue());
        FacesContext.getCurrentInstance().renderResponse();
        ADFUtils.findIterator("ProductContractDeliveryVOIterator").getViewObject().getCurrentRow().setAttribute("Price", valueChangeEvent.getNewValue());
        Long TotalProducts=0L;
        Long TotalPromos=0L;
        Long TotalOrder=0L;
        RowSetIterator Prs=ADFUtils.findIterator("ProductContractDeliveryVOIterator").getViewObject().createRowSetIterator(null);
        while(Prs.hasNext()) {
            Row row=Prs.next();
            if((Long)row.getAttribute("DeliveryFlag1")==1 || row.getAttribute("DeliveryFlag1").equals(1)) {
                TotalProducts+=(Long)row.getAttribute("Price")*(Long)row.getAttribute("Quantity");
            }
        }
        RowSetIterator PromoRs=ADFUtils.findIterator("PromosDeliveryVOIterator").getViewObject().createRowSetIterator(null);
        while(PromoRs.hasNext()) {
            Row PromoRow=PromoRs.next();
            if((Long)PromoRow.getAttribute("DeliveryFlag")==1 || PromoRow.getAttribute("DeliveryFlag").equals(1)) {
                TotalPromos+=(Long)PromoRow.getAttribute("PromoPrice")*(Long)PromoRow.getAttribute("Quantity");
                
            }
        }
        TotalOrder=TotalProducts+TotalPromos;
        System.err.println("Product Price"+TotalProducts);
        System.err.println("Promos Price"+TotalPromos);
        System.err.println("Total Price"+TotalOrder);
        ADFUtils.setBoundAttributeValue("productsprice", TotalProducts);
        ADFUtils.setBoundAttributeValue("PromosPrice", TotalPromos);
        ADFUtils.setBoundAttributeValue("TotalOrder", TotalOrder);
    }

    public String CallPhone1() {
        // Add event code here...
        System.err.println("UserName : "+ADFContext.getCurrent().getSessionScope().get("CallUserName"));
        if(ADFContext.getCurrent().getSessionScope().get("CallUserName")==null) {
            JSFUtils.addFacesErrorMessage("You Should Enter UserName in Call Parameters");
        }
        else if(ADFContext.getCurrent().getSessionScope().get("CallPass")==null) {
            JSFUtils.addFacesErrorMessage("You Should Enter Password in Call Parameters");
        }
        else if(ADFContext.getCurrent().getSessionScope().get("ExtensionNum")==null) {
            JSFUtils.addFacesErrorMessage("You Should Enter Extension Number in Call Parameters");
        }
        else if (ADFContext.getCurrent().getSessionScope().get("QueueNum")==null) {
            JSFUtils.addFacesErrorMessage("You Should Enter Queue Number in Call Parameters");
        }
        else {
            System.err.println("Here We Call ");
        }

        System.err.println();
        return null;
    }

    public String CallPhone2() {
        // Add event code here...
        System.err.println("UserName : "+ADFContext.getCurrent().getSessionScope().get("CallUserName"));
        if(ADFContext.getCurrent().getSessionScope().get("CallUserName")==null) {
            JSFUtils.addFacesErrorMessage("You Should Enter UserName in Call Parameters");
        }
        else if(ADFContext.getCurrent().getSessionScope().get("CallPass")==null) {
            JSFUtils.addFacesErrorMessage("You Should Enter Password in Call Parameters");
        }
        else if(ADFContext.getCurrent().getSessionScope().get("ExtensionNum")==null) {
            JSFUtils.addFacesErrorMessage("You Should Enter Extension Number in Call Parameters");
        }
        else if (ADFContext.getCurrent().getSessionScope().get("QueueNum")==null) {
            JSFUtils.addFacesErrorMessage("You Should Enter Queue Number in Call Parameters");
        }
        else {
            System.err.println("Here We Call ");
        }

        System.err.println();
        return null;
    }

    public String CallMobile1() {
        // Add event code here...
        System.err.println("UserName : "+ADFContext.getCurrent().getSessionScope().get("CallUserName"));
        if(ADFContext.getCurrent().getSessionScope().get("CallUserName")==null) {
            JSFUtils.addFacesErrorMessage("You Should Enter UserName in Call Parameters");
        }
        else if(ADFContext.getCurrent().getSessionScope().get("CallPass")==null) {
            JSFUtils.addFacesErrorMessage("You Should Enter Password in Call Parameters");
        }
        else if(ADFContext.getCurrent().getSessionScope().get("ExtensionNum")==null) {
            JSFUtils.addFacesErrorMessage("You Should Enter Extension Number in Call Parameters");
        }
        else if (ADFContext.getCurrent().getSessionScope().get("QueueNum")==null) {
            JSFUtils.addFacesErrorMessage("You Should Enter Queue Number in Call Parameters");
        }
        else {
            System.err.println("Here We Call ");
        }

        System.err.println();
        return null;
    }

    public String CallMobile2() {
        // Add event code here...
        System.err.println("UserName : "+ADFContext.getCurrent().getSessionScope().get("CallUserName"));
        if(ADFContext.getCurrent().getSessionScope().get("CallUserName")==null) {
            JSFUtils.addFacesErrorMessage("You Should Enter UserName in Call Parameters");
        }
        else if(ADFContext.getCurrent().getSessionScope().get("CallPass")==null) {
            JSFUtils.addFacesErrorMessage("You Should Enter Password in Call Parameters");
        }
        else if(ADFContext.getCurrent().getSessionScope().get("ExtensionNum")==null) {
            JSFUtils.addFacesErrorMessage("You Should Enter Extension Number in Call Parameters");
        }
        else if (ADFContext.getCurrent().getSessionScope().get("QueueNum")==null) {
            JSFUtils.addFacesErrorMessage("You Should Enter Queue Number in Call Parameters");
        }
        else {
            System.err.println("Here We Call ");
        }

        System.err.println();
        return null;
    }

    public String OpenAuditLocation() {
        // Add event code here...
        JSFUtils.showPopup("p9");
        return null;
    }

    public String OpenContractResultLocation() {
        // Add event code here...
        JSFUtils.showPopup("p10");
        return null;
        
    }

    public String OpenArchivedContractResult() {
        // Add event code here...
        JSFUtils.showPopup("p12");
        return null;
    }

    public String OpenArchivedMobileAudit() {
        // Add event code here...
        JSFUtils.showPopup("p11");
        return null;
    }
}
