package MTS.security.MTSClasses;

import OPERATIONPROJECT.model.BC.AM.AppModuleAMImpl;

import java.sql.Types;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.adf.view.rich.event.DialogEvent;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.server.Entity;

public class AccountingAgent {
    public AccountingAgent() {
        super();
    }

    public String OpenInvoicesForCard() {
        // Add event code here...
        System.err.println("Card No : "+ADFUtils.getBoundAttributeValue("CardNo"));
        JSFUtils.showPopup("pinvoices");
        return null;
    }

    public void SelectAllInvoices(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        Boolean val=(Boolean)valueChangeEvent.getNewValue();
        System.err.println("Select All Value : "+val);
        ViewObject vo=ADFUtils.findIterator("FilteredInvoicesVO1Iterator").getViewObject();
        RowSetIterator rs=vo.createRowSetIterator(null);
        while(rs.hasNext()) {
            Row r=rs.next();
            r.setAttribute("Selected", val);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("t2"));

    }

    public void ConfirmSubmitCollectionAssignment(DialogEvent dialogEvent) {
        // Add event code here...
        if(dialogEvent.getOutcome().equals(dialogEvent.getOutcome().yes)) {
            ViewObject vo=ADFUtils.findIterator("FilteredInvoicesVO1Iterator").getViewObject();
            RowSetIterator rs=vo.createRowSetIterator(null);
            System.err.println("Card"+ADFUtils.getBoundAttributeValue("CardNo"));
            String InvoiceNo="";
            String InvoiceDate="";
            String Invoice_Amount="";
            while(rs.hasNext()) {
                Row r=rs.next();
                System.err.println("Selected"+r.getAttribute("Selected"));
                if(r.getAttribute("Selected").equals(true)) {
                    InvoiceNo+=r.getAttribute("InvoiceNo")+",";
                    InvoiceDate+=r.getAttribute("InvoiceDate").toString().split(" ")[0]+",";
                    Invoice_Amount+=r.getAttribute("InvoiceAmount")+",";
                }
            }
            System.err.println("Invoice No : "+InvoiceNo);
            System.err.println("Invoice Date : "+InvoiceDate);
            System.err.println("InvoiceAmount : "+Invoice_Amount);
            System.err.println("Working Date : "+ADFUtils.getBoundAttributeValue("WorkingDate") );
            System.err.println("Cover Date : "+ADFUtils.getBoundAttributeValue("CoverDate"));
            System.err.println("Card No "+ADFUtils.getBoundAttributeValue("CardNo"));
            System.err.println("ID"+ADFUtils.getBoundAttributeValue("Id"));
            System.err.println("City Id"+ADFUtils.getBoundAttributeValue("CityId"));
            System.err.println("Region Id"+ADFUtils.getBoundAttributeValue("RegionId") );
            System.err.println("Address Area"+ADFUtils.getBoundAttributeValue("AddressArea"));
            System.err.println("Street Id"+ADFUtils.getBoundAttributeValue("StreetId"));
            System.err.println("Flat No"+ADFUtils.getBoundAttributeValue("FlatNo") );
            System.err.println("Floor No "+ADFUtils.getBoundAttributeValue("FloorNo"));
            System.err.println("Home "+ADFUtils.getBoundAttributeValue("HomeNo"));
            System.err.println("Address"+ADFUtils.getBoundAttributeValue("Address"));
            System.err.println("Remarks"+ADFUtils.getBoundAttributeValue("Remarks"));
            System.err.println("Area Id"+ADFUtils.getBoundAttributeValue("AreaId"));
            String x_out =
                
                (String) ADFUtils.callStoredFunction((AppModuleAMImpl) ADFUtils.getApplicationModuleForDataControl("AppModuleAMDataControl"),
                                                     Types.VARCHAR, "CREATE_COLLECTION_ASSIGNMENT(?,?,?,? ,? ,? , ? ,? , ? , ? , ? ,?,?,?)",
                                                     new Object[] {  ADFUtils.getBoundAttributeValue("WorkingDate") , ADFUtils.getBoundAttributeValue("CoverDate") , ADFUtils.getBoundAttributeValue("CardNo") , InvoiceNo , InvoiceDate , Invoice_Amount , ADFUtils.getBoundAttributeValue("Id") , ADFUtils.getBoundAttributeValue("CityId") , ADFUtils.getBoundAttributeValue("RegionId") , ADFUtils.getBoundAttributeValue("AddressArea") , ADFUtils.getBoundAttributeValue("StreetId"),ADFUtils.getBoundAttributeValue("FlatNo") , ADFUtils.getBoundAttributeValue("FloorNo") , ADFUtils.getBoundAttributeValue("HomeNo") , ADFUtils.getBoundAttributeValue("Address") , ADFUtils.getBoundAttributeValue("Remarks") , ADFUtils.getBoundAttributeValue("AreaId"),ADFContext.getCurrent().getSecurityContext().getUserName() });
            System.out.println("Out Submit : "+x_out);
            JSFUtils.addFacesInformationMessage("Assignment Created Successfully");
            
        }
        
    }
}
