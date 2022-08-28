package MTS.security.MTSClasses;

import OPERATIONPROJECT.model.BC.AM.AppModuleAMImpl;
import OPERATIONPROJECT.model.BC.VO.InvoiceScheduleVORowImpl;

import java.sql.Types;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.server.Entity;

public class AccountingTeamLeaderBean {
    public AccountingTeamLeaderBean() {
        super();
    }

    public void ChangeAccountingAgent(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        Row currentRow = ADFUtils.findIterator("InvoiceScheduleVO1Iterator")
                                 .getViewObject()
                                 .getCurrentRow();
        System.err.println("Agent : "+currentRow.getAttributeValues().toString());
        System.err.println("Value"+valueChangeEvent.getNewValue());
       // System.err.println("Invoices"+);
       UIComponent c = valueChangeEvent.getComponent();
       c.processUpdates(FacesContext.getCurrentInstance());
      // FacesContext.getCurrentInstance().renderResponse();
        FacesContext.getCurrentInstance().renderResponse();
        AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("t1"));
        ADFUtils.findIterator("InvoiceScheduleVO1Iterator").getViewObject().getCurrentRow().setAttribute("Track", "1");

    }

    public void ConfirmAssignAccountant(DialogEvent dialogEvent) {
        // Add event code here...
        if(dialogEvent.getOutcome().equals(dialogEvent.getOutcome().yes)) {
            String Area_id ="";
            String Agents="";
            ViewObject vo=ADFUtils.findIterator("InvoiceScheduleVO1Iterator").getViewObject();
            RowSetIterator rs=vo.createRowSetIterator(null);
            InvoiceScheduleVORowImpl r=null;
            System.err.println(Entity.STATUS_MODIFIED);
            while(rs.hasNext()){
               // Row r=rs.next();
                r=(InvoiceScheduleVORowImpl)rs.next();
                System.err.println("State"+r.getEntity(0).getEntityState());
                System.err.println("Track"+r.getAttribute("Track"));
                if(r.getEntity(0).getEntityState()==Entity.STATUS_MODIFIED) {
                    Area_id+=r.getAttribute("AreaId").toString()+",";
                    System.err.println("Agent "+r.getAttribute("AccountingAgent"));
                    Agents+=r.getAttribute("AccountingAgent").toString()+",";
                    
                }
            }
            System.err.println("Working Date :"+ADFUtils.getBoundAttributeValue("WorkingDate"));
            System.err.println("Area IDs:"+Area_id);
            System.err.println("Accounting Agents :"+Agents);
            if(Area_id.length()>0 && Agents.length()>0) {
                String x_out =
                    (String) ADFUtils.callStoredFunction((AppModuleAMImpl) ADFUtils.getApplicationModuleForDataControl("AppModuleAMDataControl"),
                                                         Types.VARCHAR, "ASSIGN_ACCOUNTING_AGENTS(?,?,?,?)",
                                                         new Object[] { Area_id , Agents , ADFUtils.getBoundAttributeValue("WorkingDate") , ADFContext.getCurrent().getSecurityContext().getUserName() });
                System.out.println("Out Submit : "+x_out);
                JSFUtils.addFacesInformationMessage("Accounting Agents Assigned Successfully");
            }
            else {
                JSFUtils.addFacesErrorMessage("No New Updates To Be Submitted!");
            }
        }
    }
}
