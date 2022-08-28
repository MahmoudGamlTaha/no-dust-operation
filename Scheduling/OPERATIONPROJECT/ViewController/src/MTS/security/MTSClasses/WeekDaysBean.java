package MTS.security.MTSClasses;
import MTS.security.MTSClasses.ADFUtils;

import OPERATIONPROJECT.model.BC.AM.AppModuleAMImpl;

import OPERATIONPROJECT.model.BC.VO.ProductContractDeliveryVORowImpl;
import OPERATIONPROJECT.model.BC.VO.ProductContractVORowImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.math.BigDecimal;

import java.net.MalformedURLException;

import java.net.URL;

import java.security.KeyManagementException;

import java.security.NoSuchAlgorithmException;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.share.ADFContext;

import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupFetchEvent;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.adf.view.rich.context.AdfFacesContext;
import java.sql.Timestamp;
import java.sql.Types;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import oracle.binding.OperationBinding;

import oracle.jbo.server.Entity;

import weblogic.net.http.HttpsURLConnection;

public class WeekDaysBean {
    public WeekDaysBean() {
        super();
    }
//    public void selectAllCheckboxIncludeValueChange(ValueChangeEvent valueChangeEvent) {
//        // Add event code here...
//        boolean isSelected = ((Boolean)valueChangeEvent.getNewValue()).booleanValue();
//        ViewObject vo = ADFUtils.findIterator("SchedulingTableIncludeVOIterator").getViewObject();
//        Row row = null;
//        vo.reset();
//        RowSetIterator rs = vo.createRowSetIterator(null);
//        rs.reset();
//        while (rs.hasNext()) {
//            row = rs.next();
//            if (isSelected)
//                row.setAttribute("ExcludeTrans", true);
//            else
//                row.setAttribute("ExcludeTrans", false);
//        }
//        rs.closeRowSetIterator();
//        //Refresh the table
//        AdfFacesContext.getCurrentInstance().addPartialTarget(valueChangeEvent.getComponent().getParent().getParent());
//    }
    
    public void UpdateBNum()
    {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
               LocalDateTime now = LocalDateTime.now();  
               System.out.println(dtf.format(now)); 
        ViewObject vo=ADFUtils.findIterator("OperationWorkVOIterator").getViewObject();
            now = LocalDateTime.now();  
                           System.out.println(dtf.format(now)); 
        Long p_num=(Long)ADFUtils.getBoundAttributeValue("B_Num");
        System.err.println(p_num);
        Row row=null;
        vo.reset();
            now = LocalDateTime.now();  
                           System.out.println(dtf.format(now)); 
        RowSetIterator rs=vo.createRowSetIterator(null);
            now = LocalDateTime.now();  
                           System.out.println(dtf.format(now)); 
        rs.reset();
            now = LocalDateTime.now();  
                           System.out.println(dtf.format(now)); 
        while(rs.hasNext()) {
            row=rs.next();
            row.setAttribute("PNumForDispatch", p_num);
            row.setAttribute("UpdatedBy", ADFContext.getCurrent().getSecurityContext().getUserName());
            now = LocalDateTime.now();  
                           System.out.println(dtf.format(now)); 
        }
        System.err.println("iterator finished");
            now = LocalDateTime.now();  
                           System.out.println(dtf.format(now)); 
        rs.closeRowSetIterator();
        System.err.println("Iterator Closed");
            now = LocalDateTime.now();  
                           System.out.println(dtf.format(now)); 
           // OperationBinding operationBinding = ADFUtils.findOperation("Commit1");
            System.err.println("Getting Operation");
            ADFUtils.findOperation("Commit1").execute();
            System.err.println("Executed");
            now = LocalDateTime.now();  
                           System.out.println(dtf.format(now)); 
           // AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("OperationWorkVOTab"));

            JSFUtils.addFacesInformationMessage("B Num Updated Successfully ");
        }
         
    public void ConfirmExcludeAreas(ActionEvent actionEvent) {
        
    }
    public void selectAllCheckboxExcludeValueChange(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        boolean isSelected = ((Boolean)valueChangeEvent.getNewValue()).booleanValue();
        ViewObject vo = ADFUtils.findIterator("SchedulingTableExcludeVOIterator").getViewObject();
        Row row = null;
        vo.reset();
        RowSetIterator rs = vo.createRowSetIterator(null);
        rs.reset();
        while (rs.hasNext()) {
            row = rs.next();
            if (isSelected)
                row.setAttribute("IncludeTrans", true);
            else
                row.setAttribute("IncludeTrans", false);
        }
        rs.closeRowSetIterator();
        //Refresh the table
        AdfFacesContext.getCurrentInstance().addPartialTarget(valueChangeEvent.getComponent().getParent().getParent());
        System.err.println(valueChangeEvent.getComponent().getParent().getParent());

    }
    public void selectallScheduledv(ValueChangeEvent valuechangedevent) {
        boolean isselected=((Boolean) valuechangedevent.getNewValue()).booleanValue();
        ViewObject vo=ADFUtils.findIterator("SchedulingTableIncludeVOIterator").getViewObject();
        Row row=null;
        vo.reset();
        RowSetIterator rs=vo.createRowSetIterator(null);
        rs.reset();
        while(rs.hasNext()) {
            row=rs.next();
            if(isselected) {

                if(row.getAttribute("Scheduled")=="1" || row.getAttribute("Scheduled").equals(1) || (Long)row.getAttribute("Scheduled")==1)
                {
                    System.out.println("here");

                row.setAttribute("ExcludeTrans", false);
                }
                else
                {
                    System.out.println("New");
                row.setAttribute("ExcludeTrans", true);
                }
            }
            else
            row.setAttribute("ExcludeTrans", false);
            System.err.println("flag "+row.getAttribute("ExcludeTrans"));

        }
        
        rs.closeRowSetIterator();
        //Refresh the table
        AdfFacesContext.getCurrentInstance().addPartialTarget(valuechangedevent.getComponent().getParent().getParent());
        System.err.println(valuechangedevent.getComponent().getParent().getParent());

    }
    public void includeAction() {
        // Add event code here...
        ViewObject vo = ADFUtils.findIterator("SchedulingTableExcludeVOIterator").getViewObject();
        Row row = null;
        vo.reset();
        RowSetIterator rs = vo.createRowSetIterator(null);
        rs.reset();
        while (rs.hasNext()) {
            row = rs.next();
            if (row.getAttribute("IncludeTrans")!=null && row.getAttribute("IncludeTrans").equals(true))
            {
                   row.setAttribute("IncludeExclude", 1);
                }
        }
        rs.closeRowSetIterator();
        ADFUtils.getApplicationModuleForDataControl("AppModuleAMDataControl").getTransaction().postChanges();
        ViewObject vo2 = ADFUtils.findIterator("SchedulingTableIncludeVOIterator").getViewObject();
        vo.executeQuery();
        vo2.executeQuery();
        AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("SchedulingTableIncludeVOTab"));
        AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("SchedulingTableExcludeVOTab"));
    }
    public void excludeAction() {
        // Add event code here...
        ViewObject vo = ADFUtils.findIterator("SchedulingTableIncludeVOIterator").getViewObject();
        Row row = null;
        vo.reset();
        RowSetIterator rs = vo.createRowSetIterator(null);
        rs.reset();
        while (rs.hasNext()) {
            row = rs.next();
            if (row.getAttribute("ExcludeTrans")!=null && row.getAttribute("ExcludeTrans").equals(true))
            {
                System.err.println("ExcludeReason "+row.getAttribute("ExcludeReason"));
                if(row.getAttribute("ExcludeReason")==null || row.getAttribute("ExcludeReason").equals(null)){
                    JSFUtils.addFacesErrorMessage("Enter exclude reason for all selected areas");
                        return;
                    }
                else{
                   row.setAttribute("IncludeExclude", 0);
                }
                }
        }
        rs.closeRowSetIterator();
        ADFUtils.getApplicationModuleForDataControl("AppModuleAMDataControl").getTransaction().postChanges();
        ViewObject vo2 = ADFUtils.findIterator("SchedulingTableExcludeVOIterator").getViewObject();
        vo.executeQuery();
        vo2.executeQuery();
        AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("SchedulingTableIncludeVOTab"));
        AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("SchedulingTableExcludeVOTab"));
    }

    public void submitIncludeExcludeAction() {
        // Add event code here...
        System.err.println("hereeeeee");
        ViewObject vo = ADFUtils.findIterator("SchedulingTableIncludeVOIterator").getViewObject();
        Row row = null;
        vo.reset();
        RowSetIterator rs = vo.createRowSetIterator(null);
        rs.reset();
        while (rs.hasNext()) {
            row = rs.next();
            System.err.println("Scheduled "+row.getAttribute("Scheduled"));
            if (row.getAttribute("Scheduled")==null || row.getAttribute("Scheduled").equals(0)|| (Long)row.getAttribute("Scheduled")==0||row.getAttribute("Scheduled").equals(null))
            {
                   row.setAttribute("Scheduled", 1);
                    row.setAttribute("Agent", ADFContext.getCurrent().getSecurityContext().getUserName());
                }
        }
        rs.closeRowSetIterator();
        OperationBinding operationBinding = ADFUtils.findOperation("Commit");
        operationBinding.execute();
        OperationBinding operationbinding2=ADFUtils.findOperation("Execute1");
        operationbinding2.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("t1"));

        JSFUtils.addFacesInformationMessage("Scheduling Submitted Successfully");
    }

    public void submitSMSAction() {
        // Add event code here...
              ViewObject vo = ADFUtils.findIterator("AreaSmsVOIterator").getViewObject();
              StringBuilder AreasStrb = new StringBuilder("");
              String AreasStr = "";
              String AgentName = ADFContext.getCurrent().getSecurityContext().getUserName();
              String sms_content="";
              Row row = null;
              vo.reset();
              RowSetIterator rs = vo.createRowSetIterator(null);
              rs.reset();
              while (rs.hasNext()) {
                  row = rs.next();
                      if (row.getAttribute("SelectTrans")!=null && row.getAttribute("SelectTrans").equals(true))
                      {
                              Object ID = row.getAttribute("Id");
                              row.setAttribute("SmsFlag", 1);
                              row.setAttribute("SelectTrans", false);
                              Object SMS_Content=row.getAttribute("MessageContent");
                              sms_content+=SMS_Content+",||,";


                              AreasStrb.append(ID).append(","); 
                          }
              }
              rs.closeRowSetIterator();
              AreasStr = AreasStrb.deleteCharAt(AreasStrb.length() - 1).toString();
       // sms_content=sms_content.
              System.err.println("AreasStr "+AreasStr);
        System.err.println("SMS_Content "+sms_content);

        ADFUtils.callStoredFunction((AppModuleAMImpl)ADFUtils.getApplicationModuleForDataControl("AppModuleAMDataControl"),
                                         Types.NUMERIC, "UPDATE_SMS_FLAG(?,?,?)",
                                                                            new Object[] {AreasStr, ADFContext.getCurrent().getSecurityContext().getUserName(),sms_content});

              JSFUtils.addFacesInformationMessage("SMS Sent Successfully");



             /* try {
                  SendMail(AreasStr, AgentName);
                  
                  JSFUtils.addFacesInformationMessage("SMS Sent Successfully");
              } catch (MalformedURLException | KeyManagementException | NoSuchAlgorithmException e) {
                  e.printStackTrace();
              } catch (IOException e) {
                  e.printStackTrace();
              }*/
    }
    public static boolean SendMail(String AreasStr,String AgentName) //throws UnsupportedOperationException, SOAPException, IOException
        //{
            throws MalformedURLException,IOException, KeyManagementException, NoSuchAlgorithmException
        
         {
        String NewURL="http://rpt.nodust-eg.com:8001/API_OPERATIONS-OPERATION_API-context-root/resources/NODUST/SendSMS?IDS="+AreasStr+"&Agent="+AgentName;
         System.out.println("Send mail "+ NewURL);
         HTTPSTrustManager.allowAllSSL();
          URL url = new URL(NewURL);
          HttpsURLConnection conn;
          conn = (HttpsURLConnection) url.openConnection();
          conn.setUseCaches(false);
          conn.setDoInput(true);
          conn.setDoOutput(true);
    //set method as POST or GET
         conn.setRequestMethod("GET");
         int status = 0;
         if( null != conn ){

            status = conn.getResponseCode();
           }
           else{
    System.out.println("calling Send mail  Failed to Connect to Webservice ");

    return false;
    }
    if(status!=200){
    conn.disconnect();
    return false;
    }
    else{
    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    String str= reader.readLine();
    if(str.contains("failed")){
    System.out.println("Loading send mail Failed  Due To webservice Failure");

    conn.disconnect();

    return false;
    }
    else{
    System.out.println("Loading send mail has been successed");

    conn.disconnect();
    return true;
    }

    }

    }

    public void changeAgentDropD(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        Row currentRow = ADFUtils.findIterator("OperationWorkVOIterator")
                                 .getViewObject()
                                 .getCurrentRow();
        System.err.println("Agent : "+currentRow.getAttributeValues().toString());
        Date date= new Date();
                 //getTime() returns current time in milliseconds
                 long time = date.getTime();
                 //Passed the milliseconds to constructor of Timestamp class 
                 Timestamp ts = new Timestamp(time);
                 System.out.println("Current Time Stamp: "+ts);
        currentRow.setAttribute("UpdatedBy",  ADFContext.getCurrent().getSecurityContext().getUserName());
        currentRow.setAttribute("UpdateTime", ts);

    }

    public void submitOperationWork() {
        // Add event code here...
        OperationBinding operationBinding = ADFUtils.findOperation("Commit");
        operationBinding.execute();
        JSFUtils.addFacesInformationMessage("Areas Updated Successfully");
    }


    public void ConfirmInclude(DialogEvent dialogEvent) {
        // Add event code here...
        if(dialogEvent.getOutcome().equals(dialogEvent.getOutcome().yes)) {
            includeAction();
        }
    }

    public void ConfirmExclude(DialogEvent dialogEvent) {
        // Add event code here...
        if(dialogEvent.getOutcome().equals(dialogEvent.getOutcome().yes)) {
            excludeAction();
        }
    }

    public void ConfirmSubmitScheduling(DialogEvent dialogEvent) {
        // Add event code here...
        if(dialogEvent.getOutcome().equals(dialogEvent.getOutcome().yes)) {
            submitIncludeExcludeAction();
        }
    }

    public void ConfirmUpdateBNum(DialogEvent dialogEvent) {
        // Add event code here...
        if(dialogEvent.getOutcome().equals(dialogEvent.getOutcome().yes)) {
            UpdateBNum();
        }
    }

    public void ConfirmSubmitOperationWork(DialogEvent dialogEvent) {
        // Add event code here...
        if(dialogEvent.getOutcome().equals(dialogEvent.getOutcome().yes)) {
            submitOperationWork();
        }
    }

    public void ConfirmSendSMS(DialogEvent dialogEvent) {
        // Add event code here...
        if(dialogEvent.getOutcome().equals(dialogEvent.getOutcome().yes)) {
            submitSMSAction();
        }
    }
public void SendBulkSMS() {
    ViewObject vo=ADFUtils.findIterator("BulkSMSVO1Iterator").getViewObject();
    RowSetIterator rs=vo.createRowSetIterator(null);
    rs.reset();
    String SelectedCards="";

    while(rs.hasNext()) {
        Row r=rs.next();
        if(r.getAttribute("Checked")!=null&&r.getAttribute("Checked").equals(true)) {
            SelectedCards+=r.getAttribute("CardNo").toString()+",";
        }
    }
    if(SelectedCards.length()>0) {
        SelectedCards=SelectedCards.substring(0, SelectedCards.length()-1);
        String x_out =
            (String) ADFUtils.callStoredFunction((AppModuleAMImpl) ADFUtils.getApplicationModuleForDataControl("AppModuleAMDataControl"),
                                                 Types.VARCHAR, "SEND_BULK_SMS(?,?,?)",
                                                 new Object[] {SelectedCards, ADFContext.getCurrent().getSecurityContext().getUserName() , ADFUtils.getBoundAttributeValue("SMSContent")});
        System.out.println("Out Submit : "+x_out);
        JSFUtils.addFacesInformationMessage("Bulk SMS Sent Successfully");

    }
    else {
        JSFUtils.addFacesErrorMessage("You Should Select at least One Card To Send SMS");
    }
}
    public void ConfirmSendBulkSMS(DialogEvent dialogEvent) {
        // Add event code here...
        if(dialogEvent.getOutcome().equals(dialogEvent.getOutcome().yes)) {
            SendBulkSMS();
        }
    }

    public void SelectAllBulkSMS(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        boolean isSelected = ((Boolean)valueChangeEvent.getNewValue()).booleanValue();
        ViewObject vo = ADFUtils.findIterator("ContractDataVO1Iterator").getViewObject();
        Row row = null;
        vo.reset();
        RowSetIterator rs = vo.createRowSetIterator(null);
        rs.reset();
        while (rs.hasNext()) {
            row = rs.next();
            if (isSelected)
                row.setAttribute("selectBSMS", true);
            else
                row.setAttribute("selectBSMS", false);
        }
        rs.closeRowSetIterator();
        //Refresh the table
        AdfFacesContext.getCurrentInstance().addPartialTarget(valueChangeEvent.getComponent().getParent().getParent());

    }

    public void SelectAllSMSAreas(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        boolean isSelected = ((Boolean)valueChangeEvent.getNewValue()).booleanValue();
        ViewObject vo=ADFUtils.findIterator("AreaSmsVOIterator").getViewObject();
        Row row=null;
        vo.reset();
        RowSetIterator rs=vo.createRowSetIterator(null);
        while(rs.hasNext()) {
            
            row=rs.next();
            if(isSelected)
            {
            if(((Long)row.getAttribute("Splitted")==1 && (Long) row.getAttribute("AreaStatus")==1 ) || ((Long)row.getAttribute("SmsFlag")==1)) {
                
            }
            else {
                row.setAttribute("SelectTrans", true);
            }
            }
            else{
                row.setAttribute("SelectTrans", false);
            }
        }
        rs.closeRowSetIterator();
        AdfFacesContext.getCurrentInstance().addPartialTarget(valueChangeEvent.getComponent().getParent().getParent());

    }
    public void FilterWeekDays() {
        ViewObject vo=ADFUtils.findIterator("WeekDaysVOIterator").getViewObject();
        RowSetIterator rs=vo.createRowSetIterator(null);
        while(rs.hasNext()) {
            Row row=null;
            row=rs.next();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
               LocalDateTime now = LocalDateTime.now();  
               System.out.println(dtf.format(now)); 
            System.err.println("Current"+row.getAttribute("WorkingDate"));
        }
    }
    public void SetFirstRowSelected() {
        ViewObject vo=ADFUtils.findIterator("WeekDaysVOIterator").getViewObject();
        RowSetIterator rs=vo.createRowSetIterator(null);
        
        while(rs.hasNext()) {
            Row r=rs.next();
            System.err.println("R"+r.getAttribute("WorkingDate"));
        }
    }

    public void ChangeSMSUpdated(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        ViewObject vo= ADFUtils.findIterator("SMSTempVO1Iterator").getViewObject();
        System.err.println("SMS"+vo.getCurrentRow().getAttribute("TemplateContent"));
        vo.getCurrentRow().setAttribute("Updatedby", ADFContext.getCurrent().getSecurityContext().getUserName());
    }

    public void ConfirmSMSTemplateUpdates(DialogEvent dialogEvent) {
        // Add event code here...
        if(dialogEvent.getOutcome().equals(dialogEvent.getOutcome().yes))
        {
        ADFUtils.findOperation("Commit1").execute();
        JSFUtils.addFacesInformationMessage("Your Data Submitted Successfully");
        }
    }

    public void OpenAdhocDetails(ActionEvent actionEvent) {
        // Add event code here...
        System.err.println("Here");
        JSFUtils.showPopup("p10");
        ViewObject vo=ADFUtils.findIterator("AdhocContractsVO1Iterator").getViewObject();
        System.err.println("Card No :"+vo.getCurrentRow().getAttribute("CardNo"));
        //System.err.println("Card No: "+ADFUtils.findIterator("AreaContractVO2Iterator").getViewObject().getCurrentRow().getAttribute("CardNo"));
       // ADFUtils.findOperation("CreateInsert").execute();
  
    }

    public void InsertAdhocPromos(ActionEvent actionEvent) {
        // Add event code here...
        ADFUtils.findOperation("CreateInsert").execute();
         AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("t19"));
        
        
    }

    public void EditPromosPrice(ValueChangeEvent valueChangeEvent) {
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

    public void AddNewProduct(ActionEvent actionEvent) {
        // Add event code here...
        System.err.println("Product Added");
        ADFUtils.findOperation("CreateInsert1").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("t18"));

    }


    public void ChangeProductId(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        System.err.println(ADFUtils.findIterator("ContractDataVO2Iterator").getViewObject().getCurrentRow().getAttribute("DId"));
       UIComponent c = valueChangeEvent.getComponent();
       c.processUpdates(FacesContext.getCurrentInstance());
       FacesContext.getCurrentInstance().renderResponse();
       System.err.println(valueChangeEvent.getNewValue().toString());
       ADFUtils.findIterator("ProductContractVO1Iterator").getViewObject().getCurrentRow().setAttribute("Active", 1);
       System.err.println(ADFUtils.findIterator("ProductContractVO1Iterator").getViewObject().getCurrentRow().getAttribute("ProductId"));
       
       String val=ADFUtils.findIterator("ProductContractVO1Iterator").getViewObject().getCurrentRow().getAttribute("ProductId").toString();
       
         
         Row ass_det=ADFUtils.findIterator("ContractDataVO2Iterator").getViewObject().getCurrentRow();
       System.err.println("Duration"+ass_det.getAttribute("DId"));
       Long duration=(Long)ass_det.getAttribute("DId");
       Row r=ADFUtils.findIterator("ProductContractVO1Iterator").getViewObject().getCurrentRow();
       r.setAttribute("UpdatedBy", ADFContext.getCurrent().getSecurityContext().getUserName());
       // System.err.println("Product"+r.getAttribute("ProductName"));
       // System.err.println("Product"+ADFUtils.getBoundAttributeValue("ProductId"));
       ViewObject vo=ADFUtils.findIterator("ProductsAndPackagesROV1Iterator").getViewObject();
       //  System.err.println("ValueSku : "+ADFUtils.getBoundAttributeValue("ProductSku"));
       RowSetIterator rs=vo.createRowSetIterator(null);
       r=null;
       int check_pack=0;
       while(rs.hasNext()) {
       r=rs.next();
       String Val_Prod=r.getAttribute("ProductId").toString();
       Long Price=0L;
       
       System.err.println("PId"+Val_Prod);
       if(Val_Prod.equals(val)) {
         if(((Long)r.getAttribute("PackFlag"))==1) {
             check_pack=1;
             ADFUtils.findIterator("ProductContractVO1Iterator").getViewObject().removeCurrentRow();
             System.err.println("PackageSelected");
             ADFUtils.setBoundAttributeValue("SelectedId", r.getAttribute("ProductId").toString());
             ADFUtils.setBoundAttributeValue("PackSelected", 1);
              System.err.println("Selected Package : "+ADFUtils.getBoundAttributeValue("SelectedId"));
             System.err.println("Prod_ids:"+r.getAttribute("DetIds"));
             System.err.println("Quantity:"+r.getAttribute("DetQuan"));
             String [] p_ids=r.getAttribute("DetIds").toString().split(",");
             int len=p_ids.length;
             for(int ind=0;ind<len;ind++) {
                 
                 if(ind==0) {
                     ADFUtils.findOperation("CreateInsert1").execute();
                     AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("t2"));

                 }
                 else {
                     ADFUtils.findOperation("CreateInsert1").execute();
                     AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("t2"));


                 }
             }
              ViewObject rv=ADFUtils.findIterator("ProductContractVO1Iterator").getViewObject();
              //  System.err.println("ValueSku : "+ADFUtils.getBoundAttributeValue("ProductSku"));
              RowSetIterator rsv2=rv.createRowSetIterator(null);
             //  rv.executeQuery();
             System.err.println("LengthDet:"+len);
             
             String [] p_q=r.getAttribute("DetQuan").toString().split(",");
             String [] p_names=r.getAttribute("DetNames").toString().split(",");
             System.err.println("Length:"+r.getAttribute("DetIds").toString().length());
              ViewObject vo2=ADFUtils.findIterator("ProductContractVO1Iterator").getViewObject();
              //  System.err.println("ValueSku : "+ADFUtils.getBoundAttributeValue("ProductSku"));
              System.err.println("Duration : "+ADFUtils.getBoundAttributeValue("DId"));
              Long Pack_Price=0L;
              if(ADFUtils.getBoundAttributeValue("DId")!=null&&((Long)ADFUtils.getBoundAttributeValue("DId")==1||(Long)ADFUtils.getBoundAttributeValue("DId")==3))
              {
              Pack_Price= Long.getLong(r.getAttribute("Price4w").toString());
              }
              else if(ADFUtils.getBoundAttributeValue("DId")!=null&&(Long)ADFUtils.getBoundAttributeValue("DId")==2) {
                  Pack_Price=Long.getLong(r.getAttribute("Price2w").toString());

              }
              else {
                  Pack_Price= Long.getLong(r.getAttribute("Price1w").toString());

              }
              Long Prod_Price =0L;
             
              RowSetIterator rs2=vo2.createRowSetIterator(null);
              
              for(int c1=0;c1<p_ids.length;c1++) {
                  System.err.println(c1);
                  
                  if(c1==0) {
                      Row r3=null;

                      r3=rs2.first();
                     // r3.setAttribute("ProductName", p_names[c]);
                      r3.setAttribute("NewPackFlag", 1);
                      r3.setAttribute("ProductId", p_ids[c1]);
                      if(ADFUtils.getBoundAttributeValue("DId")!=null&&((Long)ADFUtils.getBoundAttributeValue("DId")==1||(Long)ADFUtils.getBoundAttributeValue("DId")==3))
                      {
                      r3.setAttribute("Price", r.getAttribute("Price4w"));
                      }
                      else if(ADFUtils.getBoundAttributeValue("DId")!=null&&(Long)ADFUtils.getBoundAttributeValue("DId")==2) {
                          r3.setAttribute("Price", r.getAttribute("Price2w"));

                      }
                      else {
                          r3.setAttribute("Price", r.getAttribute("Price1w"));

                      }
                     
                      
                      r3.setAttribute("Quantity", p_q[c1]);
                      r3.setAttribute("PartialSubmit", 1);
                      System.err.println("Pid set");
                      System.err.println("PName:"+r3.getAttribute("ProductId"));

                      System.err.println("RowId:"+r3.getAttribute("Id"));

                   
                      System.err.println("Test Here");
                      AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("t18"));
                    System.err.println("Test Here Again");
                   
                      FacesContext.getCurrentInstance().renderResponse();

                  }
                  else {

System.err.println("Index"+c1);
                      Row r4=null;

                      r4=rs2.next();
                      r4.setAttribute("NewPackFlag", 1);                         
                     r4.setAttribute("ProductId", p_ids[c1]);

                      r4.setAttribute("Quantity", p_q[c1]);
                      r4.setAttribute("PartialSubmit", 1);
                      System.err.println("RowId:"+r4.getAttribute("Id"));
                      if(ADFUtils.getBoundAttributeValue("DId")!=null&&((Long)ADFUtils.getBoundAttributeValue("DId")==1||(Long)ADFUtils.getBoundAttributeValue("DId")==3))
                      {
                      r4.setAttribute("Price", r.getAttribute("Price4w"));
                      }
                      else if(ADFUtils.getBoundAttributeValue("DId")!=null&&(Long)ADFUtils.getBoundAttributeValue("DId")==2) {
                          r4.setAttribute("Price", r.getAttribute("Price2w"));

                      }
                      else {
                          r4.setAttribute("Price", r.getAttribute("Price1w"));

                      }
                      System.err.println("UnitPrice set");
                      System.err.println("PName:"+r4.getAttribute("ProductId"));
                             
                   
                      AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("t18"));

             System.err.println("xxxx");
                  }
              
              }
              System.err.println("Test 1");
              FacesContext.getCurrentInstance().renderResponse();

             break;
             }
           
         
         else {
             if(duration==1||duration==3) {
                 Price=(Long)r.getAttribute("Price4w");
             }
             else if(duration==2) {
                 Price=(Long)r.getAttribute("Price2w");
             }
             else {
                 Price=(Long)r.getAttribute("Price1w");

             }
                    ADFUtils.findIterator("ProductContractVO1Iterator").getViewObject().getCurrentRow().setAttribute("UnitPrice", Price);
             ADFUtils.findIterator("ProductContractVO1Iterator").getViewObject().getCurrentRow().setAttribute("UpdatedBy", ADFContext.getCurrent().getSecurityContext().getUserName());
             ADFUtils.findIterator("ProductContractVO1Iterator").getViewObject().getCurrentRow().setAttribute("Active", 1);
             AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("t18"));
             System.err.println("Hereeeeeeee");

                   System.err.println("Price"+Price);
         }
       }
       }
       AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("t18"));
       AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("pt6"));

   }

    public void ValidateProducts(ActionEvent actionEvent) {
        // Add event code here...
        ViewObject vo1 = ADFUtils.findIterator("ProductContractVO1Iterator").getViewObject();
        System.err.println("Get View");
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
        ProductContractVORowImpl  row1 = null;
        vo1.reset();
        RowSetIterator rs1 = vo1.createRowSetIterator(null);
        rs1.reset();
        System.err.println("Iterator");
        while (rs1.hasNext()) {
           System.err.println("row");
        row1 = (ProductContractVORowImpl)rs1.next();
                    Object ProductId = row1.getAttribute("ProductId");
            ProductStr+=ProductId+",";
            System.err.println("Product"+ProductId);
                    Object TreatmentId = row1.getAttribute("TreatmentId");
            TreatmentStr+=TreatmentId+",";
            System.err.println("Treatment"+TreatmentId);
                    Object PackageNo = row1.getAttribute("PackageNo");
            PackageNoStr+=PackageNo+",";
            System.err.println("pno"+PackageNo);
            if(PackageNo==null)
            PackageNo=0;
                    Object Quantity = row1.getAttribute("Quantity");
            System.err.println("quantity"+Quantity);
            QuantityPStr+=Quantity+",";
                   // Long Quantityp =(Long) row1.getAttribute("Quantity");
                   // Object ConfQuant = row1.getAttribute("ConfQuant");
                   // Object ProductFlag = row1.getAttribute("DeliveryFlag1");
                   // Long ProductFlag1 = (Long)row1.getAttribute("DeliveryFlag1");
                  /*  if(row1.getAttribute("DeliveryFlag1")!=null && ProductFlag1 == 1 && (Quantity == null || Quantityp <= 0)){
                            //JSFUtils.addFacesInformationMessage("Enter quantity for all selected records for Products with value > 0");
                            JSFUtils.setExpressionValue("#{requestScope.ErrorMess2}", 1);
                            return;
                        }*/
            System.err.println("Here");
            BigDecimal Num=new BigDecimal("0");
            System.err.println(PackageNo);
            if(PackageNo!=null&&(Long)PackageNo==0)
            {
                System.err.println("Here");
            System.err.println("Product1 : "+ProductStr);
            System.err.println("Treatemnt1 : "+TreatmentStr);
            System.err.println("Quantity1 : "+QuantityPStr);
                    if(row1.getEntity(0).getEntityState()== Entity.STATUS_NEW  && (ProductId == null || TreatmentId == null ||  Quantity == null)){
                        //JSFUtils.addFacesInformationMessage("Enter product ,Treatment ,Package No and quantity for all selected records for Products");
                       System.err.println("Here 1 ");
                        JSFUtils.addFacesErrorMessage("You Should Enter Product , treatment , quantity before validation");

                        JSFUtils.setExpressionValue("#{requestScope.ErrorMess3}", 1);
                        return;
                        }
            if(TreatmentStr==null || ProductStr ==null ||QuantityPStr==null) {
                System.err.println("Here 2");
                JSFUtils.addFacesErrorMessage("You Should Select All data before validation");
                return ;
            }
            
                    ProductStrb.append(ProductId).append(","); 
                    System.err.println(ProductStrb);
                    TreatmentStrb.append(TreatmentId).append(","); 
                    System.err.println(TreatmentStrb);
                    PackageNoStrb.append(PackageNo).append(","); 
                    System.err.println(PackageNoStrb);
                    QuantityPStrb.append(Quantity).append(",");
                    System.err.println(QuantityPStrb);
                    //ConfQuantStrb.append(ConfQuant).append(",");
                    //ProductFlagStrb.append(ProductFlag).append(",");
            }
        }
        rs1.closeRowSetIterator();
        if(ProductStrb.length()!=0 || TreatmentStrb.length()!=0 || PackageNoStrb.length()!=0 || QuantityPStrb.length()!=0 ){
        ProductStr = ProductStrb.deleteCharAt(ProductStrb.length() - 1).toString();
        TreatmentStr = TreatmentStrb.deleteCharAt(TreatmentStrb.length() - 1).toString();
        PackageNoStr = PackageNoStrb.deleteCharAt(PackageNoStrb.length() - 1).toString();
        QuantityPStr = QuantityPStrb.deleteCharAt(QuantityPStrb.length() - 1).toString();
        //   ConfQuantStr = ConfQuantStrb.deleteCharAt(ConfQuantStrb.length() - 1).toString();
        // ProductFlagStr = ProductFlagStrb.deleteCharAt(ProductFlagStrb.length() - 1).toString();
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
         System.err.println("Pack:"+ADFUtils.getBoundAttributeValue("CheckPackageName"));
         JSFUtils.showPopup("replacepopup");
        }
        else {
            System.err.println("Validated");
        ADFUtils.setBoundAttributeValue("CheckPackageName", null);
         ADFUtils.setBoundAttributeValue("VALID", 1);
         
            // AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("b4"));

         JSFUtils.addFacesInformationMessage("Your Product Contract Is Valid");
         
         }
    }

    public void UpdateAdhocProducts(DialogEvent dialogEvent) {
        // Add event code here...
        if(dialogEvent.getOutcome().equals(dialogEvent.getOutcome().yes)) {
            System.err.println("x");
            ViewObject vo1 = ADFUtils.findIterator("ProductContractVO1Iterator").getViewObject();
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
            ProductContractVORowImpl row1 = null;
            vo1.reset();
            RowSetIterator rs1 = vo1.createRowSetIterator(null);
            rs1.reset();
            while (rs1.hasNext()) {
            row1 = (ProductContractVORowImpl)rs1.next();
                        Object ProductId = row1.getAttribute("ProductId");
                        Object TreatmentId = row1.getAttribute("TreatmentId");
                        Object PackageNo = row1.getAttribute("PackageNo");
                        Object Quantity = row1.getAttribute("Quantity");
                       // Long Quantityp =(Long) row1.getAttribute("Quantity");
                       // Object ConfQuant = row1.getAttribute("ConfQuant");
                       // Object ProductFlag = row1.getAttribute("DeliveryFlag1");
                       // Long ProductFlag1 = (Long)row1.getAttribute("DeliveryFlag1");
                        if((Quantity == null || Integer.parseInt(Quantity.toString()) <= 0)){
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
                      //  ConfQuantStrb.append(ConfQuant).append(",");
                       // ProductFlagStrb.append(ProductFlag).append(",");

                ProductConId.append(row1.getAttribute("ProductConId")).append(",");
            }
            rs1.closeRowSetIterator();
            if(ProductStrb.length()!=0 && TreatmentStrb.length()!=0 && PackageNoStrb.length()!=0 && QuantityPStrb.length()!=0 ){
            ProductStr = ProductStrb.deleteCharAt(ProductStrb.length() - 1).toString();
            TreatmentStr = TreatmentStrb.deleteCharAt(TreatmentStrb.length() - 1).toString();
            PackageNoStr = PackageNoStrb.deleteCharAt(PackageNoStrb.length() - 1).toString();
            QuantityPStr = QuantityPStrb.deleteCharAt(QuantityPStrb.length() - 1).toString();
            // ConfQuantStr = ConfQuantStrb.deleteCharAt(ConfQuantStrb.length() - 1).toString();
            // ProductFlagStr = ProductFlagStrb.deleteCharAt(ProductFlagStrb.length() - 1).toString();
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
                                                      Types.VARCHAR, "UPDATE_PACKAGE_PRODUCTS_n(?,?,?,?,?,?)",
                                                      new Object[] {ProductStr ,TreatmentStr , QuantityPStr , ADFUtils.getBoundAttributeValue("CardNo") , ProductConIdStr ,ADFContext.getCurrent().getSecurityContext().getUserName()});
            // ADFUtils.findOperation("Rollback").execute();

            ViewObject vo3 = ADFUtils.findIterator("ProductContractVO1Iterator").getViewObject();
            vo3.clearCache();
            vo3.executeQuery();
            // ADFUtils.findOperation("Execute").execute();
            System.err.println("xxxxxxx");
            vo3.executeQuery();

            AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("t18"));
            vo3.setQueryMode(ViewObject.QUERY_MODE_SCAN_DATABASE_TABLES);

             vo3.executeQuery();
            
            // ADFUtils.findOperation("Rollback").execute();
            RowSetIterator rss = vo3.createRowSetIterator(null);
            ProductContractVORowImpl row=null;
            rss.reset();
            while (rss.hasNext()) {
              row = (ProductContractVORowImpl)rss.next();
              if(row.getEntity(0).getEntityState()== Entity.STATUS_NEW ){
                  vo3.removeCurrentRow();
              }
              else {
                  break;
              }
            }
            // ADFUtils.findOperation("Execute1").execute();

            System.err.println("xxxxxxx");
            // vo3.executeQuery();
             AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("t18"));
             
            ADFUtils.setBoundAttributeValue("VALID", 1);
          
            JSFUtils.addFacesInformationMessage("Contract Updated Successfully");

        }
        
    }

    public void ConfirmSubmitPackage(DialogEvent dialogEvent) {
        // Add event code here...
        if(dialogEvent.getOutcome().equals(dialogEvent.getOutcome().yes)) {
            ViewObject vo1 = ADFUtils.findIterator("ProductContractVO1Iterator").getViewObject();
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
            ProductContractVORowImpl row1 = null;
            vo1.reset();
            RowSetIterator rs1 = vo1.createRowSetIterator(null);
            rs1.reset();
            while (rs1.hasNext()) {
            row1 = (ProductContractVORowImpl)rs1.next();
                        Object ProductId = row1.getAttribute("ProductId");
                        Object TreatmentId = row1.getAttribute("TreatmentId");
                        Object PackageNo = row1.getAttribute("PackageNo");
                        Object Quantity = row1.getAttribute("Quantity");
                        Long Quantityp =(Long) row1.getAttribute("Quantity");
                        Object ConfQuant = row1.getAttribute("Quantity");
                       // Object ProductFlag = row1.getAttribute("DeliveryFlag1");
                     //   Long ProductFlag1 = (Long)row1.getAttribute("DeliveryFlag1");
                       Long ProdConId=(Long)row1.getAttribute("ProductConId");
                        if((Quantity == null || Quantityp <= 0)){
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
                      //  ProductFlagStrb.append(ProductFlag).append(",");
                ProductConId.append(ProdConId).append(",");
                    
                
                }
            }
            System.err.println("Ended");
            rs1.closeRowSetIterator();
            if(ProductStrb.length()!=0 && TreatmentStrb.length()!=0 ){
            ProductStr = ProductStrb.deleteCharAt(ProductStrb.length() - 1).toString();
            TreatmentStr = TreatmentStrb.deleteCharAt(TreatmentStrb.length() - 1).toString();
            PackageNoStr = PackageNoStrb.deleteCharAt(PackageNoStrb.length() - 1).toString();
            QuantityPStr = QuantityPStrb.deleteCharAt(QuantityPStrb.length() - 1).toString();
            ConfQuantStr = ConfQuantStrb.deleteCharAt(ConfQuantStrb.length() - 1).toString();
            //  ProductFlagStr = ProductFlagStrb.deleteCharAt(ProductFlagStrb.length() - 1).toString();
                ProductConIdStr=ProductConId.deleteCharAt(ProductConId.length()-1).toString();
            }
            System.err.println("ProductStr " + ProductStr);
            System.err.println("TreatmentStr " + TreatmentStr);
            System.err.println("PackageNoStr " + PackageNoStr);
            System.err.println("QuantityPStr " + QuantityPStr);
            System.err.println("ConfQuantStr " + ConfQuantStr);
            //System.err.println("ProductFlagStr " + ProductFlagStr);
            System.err.println("ProductConId"+ProductConIdStr);
            System.err.println("SelectedPackage : "+ADFUtils.getBoundAttributeValue("SelectedId"));
            ADFUtils.callStoredFunction((AppModuleAMImpl) ADFUtils.getApplicationModuleForDataControl("AppModuleAMDataControl"),
                                                      Types.VARCHAR, "UPDATE_CON_PACKAGE_PRODUCTS_(?,?,?,?,?,?,?)",
                                                      new Object[] {ProductStr ,TreatmentStr , QuantityPStr , ADFUtils.getBoundAttributeValue("CardNo") , ProductConIdStr , ADFUtils.getBoundAttributeValue("SelectedId") , ADFContext.getCurrent().getSecurityContext().getUserName()});
            // ADFUtils.findOperation("Rollback").execute();
            System.err.println("Executed");


            ViewObject vo3 = ADFUtils.findIterator("ProductContractVO1Iterator").getViewObject();
            vo3.clearCache();
            vo3.setQueryMode(ViewObject.QUERY_MODE_SCAN_DATABASE_TABLES);

            vo3.executeQuery();
            
            // ADFUtils.findOperation("Rollback").execute();
            RowSetIterator rss = vo3.createRowSetIterator(null);
            ProductContractVORowImpl row=null;
            rss.reset();
            while (rss.hasNext()) {
             row = (ProductContractVORowImpl)rss.next();
             if(row.getEntity(0).getEntityState()== Entity.STATUS_NEW ){
                 vo3.removeCurrentRow();
             }
             else {
                 break;
             }
            }
            // ADFUtils.findOperation("Execute1").execute();

            System.err.println("xxxxxxx");
            // vo3.executeQuery();
            AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("t18"));
            
            ADFUtils.setBoundAttributeValue("VALID", 1);
            ADFUtils.setBoundAttributeValue("PackSelected", 0);
            
            JSFUtils.addFacesInformationMessage("Contract Updated Successfully");
        }
    }

    public void ConfirmSubmit(DialogEvent dialogEvent) {
        // Add event code here...
        if(dialogEvent.getOutcome().equals(dialogEvent.getOutcome().yes)) {
            String Promos_SKU="";
            String Promos_Quantity="";
            String Promos_Price="";
            String Product_ids="";
            String treatment_ids="";
            String product_quantity="";
            String Package_no="";
            String Product_Price="";
         /*   ViewObject vo = ADFUtils.findIterator("PromosDeliveryVOIterator").getViewObject();
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
            Product_ids = ProductStrb.deleteCharAt(ProductStrb.length() - 1).toString();
            treatment_ids = TreatmentStrb.deleteCharAt(TreatmentStrb.length() - 1).toString();
            Package_no = PackageNoStrb.deleteCharAt(PackageNoStrb.length() - 1).toString();
            product_quantity = QuantityPStrb.deleteCharAt(QuantityPStrb.length() - 1).toString();
            product_quantity = ConfQuantStrb.deleteCharAt(ConfQuantStrb.length() - 1).toString();
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
*/
            ADFUtils.callStoredFunction((AppModuleAMImpl) ADFUtils.getApplicationModuleForDataControl("AppModuleAMDataControl"),
                                                      Types.VARCHAR, "SUBMIT_ADHOCCONTRACTS(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                                      new Object[] {ADFUtils.getBoundAttributeValue("AddressArea") , ADFUtils.getBoundAttributeValue("CardNo") , ADFUtils.getBoundAttributeValue("DeliveryDate") , ADFUtils.getBoundAttributeValue("CloseCode") , ADFUtils.getBoundAttributeValue("CloseReason") , ADFUtils.getBoundAttributeValue("OperationComment") ,Promos_SKU , Promos_Quantity , Promos_Price , Product_ids , treatment_ids , Package_no , Product_Price ,ADFContext.getCurrent().getSecurityContext().getUserName() });
            // ADFUtils.findOperation("Rollback").execute();
            System.err.println("Executed");

            JSFUtils.addFacesInformationMessage("Your Assignment Created Successfully");
        }
    }

    public void ChangeSelectAllBulkSMS(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        ViewObject vo=ADFUtils.findIterator("BulkSMSVO1Iterator").getViewObject();
        RowSetIterator rs=vo.createRowSetIterator(null);
        Boolean new_val=(Boolean)valueChangeEvent.getNewValue();
        while(rs.hasNext()) {
            Row r=rs.next();
            r.setAttribute("Checked", valueChangeEvent.getNewValue());
            
        }
    }
}
