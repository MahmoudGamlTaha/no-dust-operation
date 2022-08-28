package MTS.security.MTSClasses;
import MTS.security.MTSClasses.ADFUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.MalformedURLException;

import java.net.URL;

import java.security.KeyManagementException;

import java.security.NoSuchAlgorithmException;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.share.ADFContext;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.adf.view.rich.context.AdfFacesContext;
import java.sql.Timestamp;
import java.util.Date;
import weblogic.net.http.HttpsURLConnection;
import weblogic.jaxrs.api.client.Client;
import com.sun.jersey.api.client.WebResource;
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
    }
    public void includeAction(ActionEvent actionEvent) {
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
    public void excludeAction(ActionEvent actionEvent) {
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

    public void submitIncludeExcludeAction(ActionEvent actionEvent) {
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
            if (row.getAttribute("Scheduled")==null || (Long)row.getAttribute("Scheduled")==0 || row.getAttribute("Scheduled").equals(0)||row.getAttribute("Scheduled").equals(null))
            {
                   row.setAttribute("Scheduled", 1);
                    row.setAttribute("Agent", ADFContext.getCurrent().getSecurityContext().getUserName());
                }
        }
        rs.closeRowSetIterator();
        ADFUtils.findOperation("Commit").execute();
    }

    public void submitSMSAction(ActionEvent actionEvent) {
        // Add event code here...
        ViewObject vo = ADFUtils.findIterator("AreaSmsVOIterator").getViewObject();
        StringBuilder AreasStrb = new StringBuilder("");
        String AreasStr = "";
        String AgentName = ADFContext.getCurrent().getSecurityContext().getUserName();
        Row row = null;
        vo.reset();
        RowSetIterator rs = vo.createRowSetIterator(null);
        rs.reset();
        while (rs.hasNext()) {
            row = rs.next();
                if (row.getAttribute("SelectTrans")!=null && row.getAttribute("SelectTrans").equals(true))
                {
                        Object ID = row.getAttribute("Id");
                        AreasStrb.append(ID).append(","); 
                    }
        }
        rs.closeRowSetIterator();
        AreasStr = AreasStrb.deleteCharAt(AreasStrb.length() - 1).toString();
        System.err.println("AreasStr "+AreasStr);
        try {
            SendMail(AreasStr, AgentName);
        } catch (MalformedURLException | KeyManagementException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static boolean SendMail(String AreasStr,String AgentName) //throws UnsupportedOperationException, SOAPException, IOException
        //{
            throws MalformedURLException,IOException, KeyManagementException, NoSuchAlgorithmException
        
         {
        
        String NewURL="http://rpt.nodust-eg.com:8001/API_OPERATIONS-OPERATION_API-context-root/resources/NODUST/SendSMS?IDS="+AreasStr+"&Agent="+AgentName;
         System.out.println("Send mail "+ NewURL);
        // HTTPSTrustManager.allowAllSSL();
         //System.out.println(NewURL);
          URL url = new URL(NewURL);

          HttpsURLConnection conn;
          conn = (HttpsURLConnection) url.openConnection();


          conn.setUseCaches(false);
          conn.setDoInput(true);
          conn.setDoOutput(true);
    //set method as POST or GET
         //conn.setRequestMethod("GET");
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
        Date date= new Date();
                 //getTime() returns current time in milliseconds
                 long time = date.getTime();
                 //Passed the milliseconds to constructor of Timestamp class 
                 Timestamp ts = new Timestamp(time);
                 System.out.println("Current Time Stamp: "+ts);
        currentRow.setAttribute("UpdatedBy",  ADFContext.getCurrent().getSecurityContext().getUserName());
        currentRow.setAttribute("UpdatedAt", ts);

    }
}
