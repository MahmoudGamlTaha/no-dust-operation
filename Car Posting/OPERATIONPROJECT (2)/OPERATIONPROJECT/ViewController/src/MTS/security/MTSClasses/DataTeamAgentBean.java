package MTS.security.MTSClasses;

import OPERATIONPROJECT.model.BC.AM.AppModuleAMImpl;

import OPERATIONPROJECT.model.BC.VO.ProductContractVORowImpl;

import java.math.BigDecimal;

import java.sql.SQLException;
import oracle.adf.model.BindingContext;
import javax.faces.context.FacesContext;
import javax.faces.application.Application;

import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupFetchEvent;

import oracle.binding.OperationBinding;
import javax.el.ValueExpression;
import oracle.jbo.server.Entity;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;

import javax.el.ELContext;
import javax.el.ExpressionFactory;

import javax.faces.component.UIComponent;

import oracle.adf.view.rich.component.rich.RichPopup;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;

import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.sql.DataSource;
import java.sql.Types;

import java.text.SimpleDateFormat;

import javax.faces.component.UIViewRoot;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;
import oracle.adf.share.ADFContext;

import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.jbo.Row;

import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;

import org.apache.myfaces.trinidad.event.ReturnEvent;


public class DataTeamAgentBean {
    private RichPopup markAsDoneConfirmationPopupBinding;

    public DataTeamAgentBean() {
        super();
    }

    public void FilterProductContractView() {
        DCBindingContainer bindings1 = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter = bindings1.findIteratorBinding("ProductContractDDownloadVO1Iterator");
        System.out.println("Ass" + resolveExpression("#{pageFlowScope.AssignmentId}"));
        iter.getViewObject()
            .setNamedWhereClauseParam("P_ASSIGNMENT_ID", resolveExpression("#{pageFlowScope.AssignmentId}"));
        iter.getViewObject().executeQuery();
        
      DCIteratorBinding iter2=bindings1.findIteratorBinding("FilteredPendingWorkVO1Iterator");
      iter2.getViewObject().setNamedWhereClauseParam("p_assignment_id", resolveExpression("#{pageFlowScope.AssignmentId}"));
      iter2.getViewObject().executeQuery();
      System.err.println("CardNo"+ADFUtils.getBoundAttributeValue("CardNo"));
      System.err.println("ClientName"+ADFUtils.getBoundAttributeValue("ClientName"));
        System.err.println("Status"+ADFUtils.getBoundAttributeValue("ContractStatus"));
        System.err.println("CloseCode"+ADFUtils.getBoundAttributeValue("CloseCode"));
        System.err.println("PaidAmount"+ADFUtils.getBoundAttributeValue("ActualPaid"));
    }

    public static Object resolveExpression(String expression) {
        FacesContext facesContext = getFacesContext();
        Application app = facesContext.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, expression, Object.class);
        return valueExp.getValue(elContext);
    }

    public static FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    public void FilterCloseCodeReasons(ValueChangeEvent valueChangeEvent) {

        UIComponent c = valueChangeEvent.getComponent();
        c.processUpdates(FacesContext.getCurrentInstance());
        FacesContext.getCurrentInstance().renderResponse();
        DCBindingContainer bindings1 = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter = bindings1.findIteratorBinding("CloseCodeReasonsVO1Iterator");
        System.out.println("P_CloseCodeGroup" + resolveExpression("#{bindings.GroupId.inputValue}"));
        iter.getViewObject()
            .setNamedWhereClauseParam("P_CloseCodeGroup", resolveExpression("#{bindings.GroupId.inputValue}"));
        iter.getViewObject().executeQuery();

    }
    public void FilterCloseCodeReasons1(ValueChangeEvent valueChangeEvent) {

        UIComponent c = valueChangeEvent.getComponent();
        c.processUpdates(FacesContext.getCurrentInstance());
        FacesContext.getCurrentInstance().renderResponse();
/*        DCBindingContainer bindings1 = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter = bindings1.findIteratorBinding("CloseCodeReasonsVO1Iterator");
        System.out.println("P_CloseCodeGroup" + resolveExpression("#{bindings.GroupId.inputValue}"));
        
        iter.getViewObject()
            .setNamedWhereClauseParam("P_CloseCodeGroup", resolveExpression("#{bindings.GroupId.inputValue}"));
        iter.getViewObject().executeQuery();
*/
                AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("soc9"));

    }

    public void OpenMarkAsDoneConfirmationPopUp(ActionEvent actionEvent) {
        // Add event code here...
        System.err.println("Confirmation");
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        getMarkAsDoneConfirmationPopupBinding().show(hints);
    }

    public void setMarkAsDoneConfirmationPopupBinding(RichPopup markAsDoneConfirmationPopupBinding) {
        this.markAsDoneConfirmationPopupBinding = markAsDoneConfirmationPopupBinding;
    }

    public RichPopup getMarkAsDoneConfirmationPopupBinding() {
        return markAsDoneConfirmationPopupBinding;
    }

    public String MarkPendingAssAsDone() {
        // Add event code here...
        BindingContainer bindings =BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding operation = bindings.getOperationBinding("Commit");
        operation.execute();
        java.util.List errors = operation.getErrors();
        System.out.println("errors"+errors);
        System.out.println("After");
        CallableStatement cst = null;
        String Result = "";
        Connection Con = null;
        try {
            Con = getConnection();
            cst = Con.prepareCall("begin ? := MARK_PENDING_ASS_AS_DONE(?,?,?,?,?,?) ;end;");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            cst.registerOutParameter(1, Types.VARCHAR);
            System.out.println(resolveExpression("#{pageFlowScope.AssignmentId}"));
            cst.setObject(2, resolveExpression("#{pageFlowScope.AssignmentId}"));
            System.out.println(resolveExpression("#{bindings.ContractStatus.inputValue}"));
            cst.setObject(3, resolveExpression("#{bindings.ContractStatus.inputValue}"));
            cst.setObject(4, ADFContext.getCurrent().getSecurityContext().getUserName());

            System.out.println(resolveExpression("#{bindings.CloseCode.inputValue}"));
            cst.setObject(5, resolveExpression("#{bindings.CloseCode.inputValue}"));
            
            /*FacesContext faces_context=FacesContext.getCurrentInstance();
            UIViewRoot root = faces_context.getViewRoot();
            RichInputText input_text=(RichInputText)root.findComponent("it1");*/
          //  System.out.println("notes"+resolveExpression("#{bindings.CustomerNotes.inputValue}"));
            cst.setObject(6, resolveExpression("#{bindings.CustomerNotes.inputValue}"));
            cst.setObject(7, resolveExpression("#{bindings.ActualPaid.inputValue}"));

            cst.executeUpdate();
            //Result = cst.getObject(1).toString();
            Con.commit();
            ViewObject pen_obj=ADFUtils.findIterator("DataTeamAgentPendingWorkVO1Iterator").getViewObject();
            pen_obj.setQueryMode(ViewObject.QUERY_MODE_SCAN_DATABASE_TABLES);
            pen_obj.executeQuery();
            JSFUtils.addFacesInformationMessage("Assignment Submitted Successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (Con != null) {
                try {
                    Con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (cst != null) {
                try {
                    cst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return "DoneProductContractDDownloadVOReturn";
    }

    private Connection getConnection() throws Exception {
        DataSource ds = null;
        Connection conn = null;
        try {
            Context context = new InitialContext();
            Context envCtx = (Context) context.lookup("java:comp/env");
            ds = (DataSource) envCtx.lookup("jdbc/Operation");
            if (ds != null) {
                conn = ds.getConnection();
                return conn;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void RefreshPendingAssView(ReturnEvent returnEvent) {
        // Add event code here...
        DCBindingContainer bindings1 = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter = bindings1.findIteratorBinding("PendingWorkAssIterator");
        iter.executeQuery();
        
    }
    public void FilterNewCancelSalesProductContractView() {
        System.err.println("Start ");
        DCBindingContainer bindings1 = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter = bindings1.findIteratorBinding("NewCancelSalesContractsProducts1Iterator");
        System.out.println("Ass" + resolveExpression("#{pageFlowScope.AssignmentId}"));
        iter.getViewObject()
            .setNamedWhereClauseParam("P_ASSIGNMENT_ID", resolveExpression("#{pageFlowScope.AssignmentId}"));
        iter.getViewObject().executeQuery();
        System.err.println("CardNo:"+resolveExpression("#{bindings.CardNo.inputValue}"));
        //System.out.println("Card"+ADFUtils.getBoundAttributeValue("CardNo"));

DCBindingContainer bindings2=(DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
       DCIteratorBinding iter2=bindings2.findIteratorBinding("ProductContractVO2Iterator");
       // System.out.println("Card"+ADFUtils.getBoundAttributeValue("CardNo"));
        iter2.getViewObject().setNamedWhereClauseParam("P_CARDNO", resolveExpression("#{bindings.CardNo.inputValue}"));
        iter2.getViewObject().executeQuery();
  System.err.println("Status"+ resolveExpression("#{bindings.ContractStatus.inputValue}"));
        System.err.println("Reason"+ resolveExpression("#{bindings.CloseCode.inputValue}"));
        System.err.println("Customer Notes"+ resolveExpression("#{bindings.CustomerNotes.inputValue}"));
      ADFUtils.setBoundAttributeValue("Id", resolveExpression("#{bindings.DataStatus.inputValue}"));
        //AdfFacesContext.getCurrentInstance().addPartialTarget(bindings2.bindingContext(). findComponentInRoot("soc1"));
        DCBindingContainer bindings11 = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();

   System.err.println("Id Set");
    }
    
    public void FilterNewCancelSalesProductContractViewPopUp() {
        System.err.println("Start ");
        DCBindingContainer bindings1 = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter = bindings1.findIteratorBinding("NewCancelSalesContractsProducts1Iterator");
        System.out.println("Ass" + resolveExpression("#{pageFlowScope.AssignmentId}"));
        iter.getViewObject()
            .setNamedWhereClauseParam("P_ASSIGNMENT_ID", resolveExpression("#{pageFlowScope.AssignmentId}"));
        iter.getViewObject().executeQuery();
        System.err.println("CardNo:"+resolveExpression("#{bindings.CardNo.inputValue}"));
        //System.out.println("Card"+ADFUtils.getBoundAttributeValue("CardNo"));

    DCBindingContainer bindings2=(DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
       DCIteratorBinding iter2=bindings2.findIteratorBinding("ProductContractVO2Iterator");
       // System.out.println("Card"+ADFUtils.getBoundAttributeValue("CardNo"));
        iter2.getViewObject().setNamedWhereClauseParam("P_CARDNO", resolveExpression("#{bindings.CardNo.inputValue}"));
        iter2.getViewObject().executeQuery();
    System.err.println("Status"+ resolveExpression("#{bindings.ContractStatus.inputValue}"));
        System.err.println("Reason"+ resolveExpression("#{bindings.CloseCode.inputValue}"));
        System.err.println("Customer Notes"+ resolveExpression("#{bindings.CustomerNotes.inputValue}"));
      ADFUtils.setBoundAttributeValue("Id", resolveExpression("#{bindings.DataStatus.inputValue}"));
        //AdfFacesContext.getCurrentInstance().addPartialTarget(bindings2.bindingContext(). findComponentInRoot("soc1"));
        DCBindingContainer bindings11 = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();

    System.err.println("Id Set");
    }

    public String SubmitDataStatus() {
        // Add event code here...
        CallableStatement cst = null;
        String Result = "";
        Connection Con = null;
        try {
            Con = getConnection();
            cst = Con.prepareCall("begin ? := SUBMIT_CONTRACTS_DATA_STATUS(?,?,?,?,?,?) ;end;");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            cst.registerOutParameter(1, Types.VARCHAR);
            System.err.println("Submitted Status : "+ resolveExpression("#{bindings.DataStatus.inputValue}"));
            cst.setObject(2, resolveExpression("#{pageFlowScope.AssignmentId}"));
            cst.setObject(3, resolveExpression("#{bindings.Id.inputValue}"));
            cst.setObject(4, ADFContext.getCurrent().getSecurityContext().getUserName());
            cst.setObject(5, ADFUtils.getBoundAttributeValue("ContractStatus"));
            cst.setObject(6, ADFUtils.getBoundAttributeValue("CloseCode"));
            cst.setObject(7, ADFUtils.getBoundAttributeValue("CustomerNotes"));
            cst.executeUpdate();
            //Result = cst.getObject(1).toString();
           // Con.commit();
            ADFUtils.findOperation("Commit").execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (Con != null) {
                try {
                    Con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (cst != null) {
                try {
                    cst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return "DoneNewCancelSalesContractProductsReturn";
    }

    public void SubmitCar(ActionEvent actionEvent) {
        // Add event code here...
        
        BigDecimal d=new BigDecimal("0");
        System.err.println(ADFUtils.getBoundAttributeValue("CarStatus"));
        if(ADFUtils.getBoundAttributeValue("CarStatus")!=null&&!ADFUtils.getBoundAttributeValue("CarStatus").toString().equals("Pass"))
        {JSFUtils.showPopup("p4");
        return;
        }
        else {
            close_pass_car();
        }
     
    }
    public void close_pass_car() {
        CallableStatement cst = null;
        String Result = "";
        Connection Con = null;
        try {
            Con = getConnection();
            cst = Con.prepareCall("begin ? := CLOSE_CAR(?,?) ;end;");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            cst.registerOutParameter(1, Types.VARCHAR);
            System.out.println("Ship"+resolveExpression("#{bindings.ShipmentId.inputValue}"));
            cst.setObject(2, resolveExpression("#{bindings.ShipmentId.inputValue}"));
            cst.setObject(3, ADFContext.getCurrent().getSecurityContext().getUserName());
            cst.executeUpdate();
            Con.commit();
            DCBindingContainer bindings1 = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding iter = bindings1.findIteratorBinding("DataTeamAgentCarReconciliationIterator");
            oracle.jbo.Key k =  new oracle.jbo.Key(new Object[] { iter.getViewObject().getCurrentRow().getAttribute("ShipmentId") });
            iter.getViewObject().executeQuery();    
            oracle.jbo.Row r= iter.getViewObject().getRow(k);
            iter.getViewObject().setCurrentRow(r);
            String Internal_issue="";
            String Driver_issue="";
         /*   ADFUtils.callStoredFunction((AppModuleAMImpl)ADFUtils.getApplicationModuleForDataControl("AppModuleAMDataControl"),
                                                                        Types.NUMERIC, "SUBMIT_CARISSUE(?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                                                        new Object[] {resolveExpression("#{bindings.ShipmentId.inputValue}") ,Internal_issue , Driver_issue , ADFUtils.getBoundAttributeValue("IssueCardNo") , ADFUtils.getBoundAttributeValue("AgentdriverName") , ADFUtils.getBoundAttributeValue("CaseNotes") , ADFUtils.getBoundAttributeValue("ReviewCoaching") , ADFUtils.getBoundAttributeValue("TeamleaderReview") , ADFUtils.getBoundAttributeValue("EmployeeSignature") , ADFUtils.getBoundAttributeValue("PostagentSignature") , ADFUtils.getBoundAttributeValue("TeamSpvsignature") , ADFUtils.getBoundAttributeValue("DataSpvsignature") , ADFContext.getCurrent().getSecurityContext().getUserName() });
           ADFUtils.findOperation("Delete").execute();
            JSFUtils.hidePopup("p4");*/
          JSFUtils.addFacesInformationMessage("Car Submitted Successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (Con != null) {
                try {
                    Con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (cst != null) {
                try {
                    cst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void close_car() {
          CallableStatement cst = null;
          String Result = "";
          Connection Con = null;
          try {
              Con = getConnection();
              cst = Con.prepareCall("begin ? := CLOSE_CAR(?,?) ;end;");
          } catch (Exception e) {
              e.printStackTrace();
          }

          try {
              cst.registerOutParameter(1, Types.VARCHAR);
              System.out.println("Ship"+resolveExpression("#{bindings.ShipmentId.inputValue}"));
              cst.setObject(2, resolveExpression("#{bindings.ShipmentId.inputValue}"));
              cst.setObject(3, ADFContext.getCurrent().getSecurityContext().getUserName());
              cst.executeUpdate();
              Con.commit();
              DCBindingContainer bindings1 = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
              DCIteratorBinding iter = bindings1.findIteratorBinding("DataTeamAgentCarReconciliationIterator");
              oracle.jbo.Key k =  new oracle.jbo.Key(new Object[] { iter.getViewObject().getCurrentRow().getAttribute("ShipmentId") });
              iter.getViewObject().executeQuery();    
              oracle.jbo.Row r= iter.getViewObject().getRow(k);
              iter.getViewObject().setCurrentRow(r);
              
              String Internal_issue="";
              String Driver_issue="";
              ViewObject vo=ADFUtils.findIterator("InternalIssueVO1Iterator").getViewObject();
              RowSetIterator rs=vo.createRowSetIterator(null);
              while(rs.hasNext())
              {
                  Row r1=rs.next();
                  if(r1.getAttribute("issue_selected")!=null && r1.getAttribute("issue_selected").equals(true)) {
                      Internal_issue+=r1.getAttribute("IssueId")+",";
                  }
                  }
              
              ViewObject vo2=ADFUtils.findIterator("DriverPilotIssueVO1Iterator").getViewObject();
              RowSetIterator rs2=vo2.createRowSetIterator(null);
              while(rs2.hasNext())
              {
                  Row d_issue=rs2.next();
                  if(d_issue.getAttribute("d_issue_selected")!=null && d_issue.getAttribute("d_issue_selected").equals(true)) {
                      Driver_issue+=d_issue.getAttribute("IssueId")+",";
                  }
              }
              System.err.println("Internal"+Internal_issue);
              System.err.println("External"+Driver_issue);
              
              ADFUtils.callStoredFunction((AppModuleAMImpl)ADFUtils.getApplicationModuleForDataControl("AppModuleAMDataControl"),
                                                                          Types.NUMERIC, "SUBMIT_CARISSUE(?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                                                          new Object[] {resolveExpression("#{bindings.ShipmentId.inputValue}") ,Internal_issue , Driver_issue , ADFUtils.getBoundAttributeValue("IssueCardNo") , ADFUtils.getBoundAttributeValue("AgentdriverName") , ADFUtils.getBoundAttributeValue("CaseNotes") , ADFUtils.getBoundAttributeValue("ReviewCoaching") , ADFUtils.getBoundAttributeValue("TeamleaderReview") , ADFUtils.getBoundAttributeValue("EmployeeSignature") , ADFUtils.getBoundAttributeValue("PostagentSignature") , ADFUtils.getBoundAttributeValue("TeamSpvsignature") , ADFUtils.getBoundAttributeValue("DataSpvsignature") , ADFContext.getCurrent().getSecurityContext().getUserName() });
             ADFUtils.findOperation("Delete").execute();
              JSFUtils.hidePopup("p4");
            JSFUtils.addFacesInformationMessage("Car Submitted Successfully");
          } catch (SQLException e) {
              e.printStackTrace();
          } finally {
              if (Con != null) {
                  try {
                      Con.close();
                  } catch (SQLException e) {
                      e.printStackTrace();
                  }
              }
              if (cst != null) {
                  try {
                      cst.close();
                  } catch (SQLException e) {
                      e.printStackTrace();
                  }
              }
          }
        
    }
    
    public void FilterPilotCoverGroup(){
        DCBindingContainer bindings1 = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter = bindings1.findIteratorBinding("PilotCoverReconDriverPilotVO1Iterator");
        DCIteratorBinding iter1 = bindings1.findIteratorBinding("PilotCoverPilotCoverProductVO1Iterator");
        BigDecimal ShipmentId = new BigDecimal(resolveExpression("#{pageFlowScope.ShipmentId}").toString());
        iter.getViewObject().setNamedWhereClauseParam("P_SHIPMENT_ID", ShipmentId);
        iter1.getViewObject().setNamedWhereClauseParam("P_SHIPMENT_ID", ShipmentId);
        iter.getViewObject().executeQuery();
        iter1.getViewObject().executeQuery();
    }

    public String SubmitPilotCoverDetailsChanges() {
        // Add event code here...
        BindingContainer bindings =BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding operation = bindings.getOperationBinding("Commit");
        operation.execute();
        BigDecimal ShipmentId = new BigDecimal(resolveExpression("#{pageFlowScope.ShipmentId}").toString());
        CallableStatement cst = null;
        String Result = "";
        Connection Con = null;
        try {
            Con = getConnection();
            cst = Con.prepareCall("begin ? := SUBMIT_PILOT_COVER_UPDATES(?,?,?,?) ;end;");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            cst.registerOutParameter(1, Types.VARCHAR);
            cst.setObject(2, ShipmentId);
            cst.setObject(3, ADFContext.getCurrent().getSecurityContext().getUserName());
            cst.setObject(4, resolveExpression("#{bindings.PilotCoverGroupComment.inputValue}"));
            cst.setObject(5, 1);
            cst.executeUpdate();
            Con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (Con != null) {
                try {
                    Con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (cst != null) {
                try {
                    cst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return "DonePilotCoverGroupReturn";
    }

    public void PilotCoverDetailsRetListener(ReturnEvent returnEvent) {
        // Add event code here...
        DCBindingContainer bindings1 = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter = bindings1.findIteratorBinding("DataTeamAgentCarReconciliationIterator");
        oracle.jbo.Key k =  new oracle.jbo.Key(new Object[] { iter.getViewObject().getCurrentRow().getAttribute("ShipmentId") });
        iter.getViewObject().executeQuery();    
        oracle.jbo.Row r= iter.getViewObject().getRow(k);
        iter.getViewObject().setCurrentRow(r);
        
    }

    public void DocumentChangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if(valueChangeEvent.getNewValue() != null){
        DCBindingContainer bindings1 = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter = bindings1.findIteratorBinding("PilotCoverReconDriverPilotIterator");
        iter.getViewObject().getCurrentRow().setAttribute("Document", "Has File");
        System.out.println("Driver"+iter.getViewObject().getCurrentRow().getAttribute("DriverId"));
        System.out.println("Pilot"+iter.getViewObject().getCurrentRow().getAttribute("PilotId"));
        System.out.println("CoverDate"+iter.getViewObject().getCurrentRow().getAttribute("ReturnDate"));
            DCBindingContainer bindings2 = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding iter2 = bindings2.findIteratorBinding("PilotCoverPilotCoverProductIterator");
            RowSetIterator rs=iter2.getViewObject().createRowSetIterator(null);
            while(rs.hasNext()) {
                
                Row r=rs.next();
                System.out.println("pilot" + r.getAttribute("PilotId"));
                if(r.getAttribute("PilotId").equals(iter.getViewObject().getCurrentRow().getAttribute("PilotId"))) {
                    System.out.println("Same Pilot");
                    r.setAttribute("Document", "Has File");
                }
            }
        }
    }

    public void ProductDocumentChangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if(valueChangeEvent.getNewValue() != null){
        DCBindingContainer bindings1 = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter = bindings1.findIteratorBinding("PilotCoverPilotCoverProductIterator");
        iter.getViewObject().getCurrentRow().setAttribute("Document", "Has File");
        }
    }
    
    public void FilterProductDetails(){
        System.err.println("Filtered ShipmentID"+resolveExpression("#{pageFlowScope.ShipmentId}"));
        System.err.println("Filtered Product"+resolveExpression("#{pageFlowScope.ProductId}"));
        DCBindingContainer bindings1 = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter = bindings1.findIteratorBinding("ProductDetailsHubReconciliationSummaryVO1Iterator");
        iter.getViewObject().setNamedWhereClauseParam("P_SHIPMENT_ID", resolveExpression("#{pageFlowScope.ShipmentId}"));
        iter.getViewObject().setNamedWhereClauseParam("P_PRODUCT_ID", resolveExpression("#{pageFlowScope.ProductId}"));
        iter.getViewObject().executeQuery();
        
        
        DCIteratorBinding iter1 = bindings1.findIteratorBinding("ProductDetailsProductDetailsSummaryVO1Iterator");
        iter1.getViewObject().setNamedWhereClauseParam("P_SHIPMENT_ID", resolveExpression("#{pageFlowScope.ShipmentId}"));
        iter1.getViewObject().setNamedWhereClauseParam("P_PRODUCT_ID", resolveExpression("#{pageFlowScope.ProductId}"));
        iter1.getViewObject().executeQuery();
        
        
        DCIteratorBinding iter2 = bindings1.findIteratorBinding("ProductDetailsTotalProductDetailsSummaryVO1Iterator");
        iter2.getViewObject().setNamedWhereClauseParam("P_SHIPMENT_ID", resolveExpression("#{pageFlowScope.ShipmentId}"));
        iter2.getViewObject().setNamedWhereClauseParam("P_PRODUCT_ID", resolveExpression("#{pageFlowScope.ProductId}"));
        iter2.getViewObject().executeQuery();
        
        
        DCIteratorBinding iter3 = bindings1.findIteratorBinding("ProductDetailsRLostReconciliationVO1Iterator");
        iter3.getViewObject().setNamedWhereClauseParam("P_SHIPMENT_ID", resolveExpression("#{pageFlowScope.ShipmentId}"));
        iter3.getViewObject().setNamedWhereClauseParam("P_PRODUCT_ID", resolveExpression("#{pageFlowScope.ProductId}"));
        iter3.getViewObject().executeQuery();
        
        
        DCIteratorBinding iter4 = bindings1.findIteratorBinding("ProductDetailsLostReconciliationVO1Iterator");
        iter4.getViewObject().setNamedWhereClauseParam("P_SHIPMENT_ID", resolveExpression("#{pageFlowScope.ShipmentId}"));
        iter4.getViewObject().setNamedWhereClauseParam("P_PRODUCT_ID", resolveExpression("#{pageFlowScope.ProductId}"));
        iter4.getViewObject().executeQuery();
        
        DCIteratorBinding iter5 = bindings1.findIteratorBinding("EligibleRLostHeaderVO1Iterator");
        iter5.getViewObject().setNamedWhereClauseParam("P_SHIPMENT_ID", resolveExpression("#{pageFlowScope.ShipmentId}"));
        iter5.getViewObject().setNamedWhereClauseParam("P_PRODUCT_ID", resolveExpression("#{pageFlowScope.ProductId}"));
        iter5.getViewObject().executeQuery();
        
        
        DCIteratorBinding iter6 = bindings1.findIteratorBinding("EligibleLostHeaderVO1Iterator");
        iter6.getViewObject().setNamedWhereClauseParam("P_SHIPMENT_ID", resolveExpression("#{pageFlowScope.ShipmentId}"));
        iter6.getViewObject().setNamedWhereClauseParam("P_PRODUCT_ID", resolveExpression("#{pageFlowScope.ProductId}"));
        iter6.getViewObject().executeQuery();
        
        
    }
    
    public void FilterLostHistory(){
        DCBindingContainer bindings1 = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter = bindings1.findIteratorBinding("ProductLostRlostVO1Iterator");
        iter.getViewObject().setNamedWhereClauseParam("P_SHIPMENT_ID", resolveExpression("#{pageFlowScope.ShipmentId}"));
        iter.getViewObject().setNamedWhereClauseParam("P_PRODUCT_ID", resolveExpression("#{pageFlowScope.ProductId}"));
        iter.getViewObject().setNamedWhereClauseParam("P_PILOT_ID", resolveExpression("#{pageFlowScope.PilotId}"));
        iter.getViewObject().executeQuery();
    }
    
    public void FilterHubCoverGroup(){
        DCBindingContainer bindings1 = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter = bindings1.findIteratorBinding("Shipment1VO1Iterator");
        iter.getViewObject().setNamedWhereClauseParam("P_SHIPMENT_ID", resolveExpression("#{pageFlowScope.ShipmentId}"));
        iter.getViewObject().executeQuery();
        
        DCIteratorBinding iter1 = bindings1.findIteratorBinding("CoverProductVO1Iterator");
        iter1.getViewObject().setNamedWhereClauseParam("P_SHIPMENT_ID", resolveExpression("#{pageFlowScope.ShipmentId}"));
        iter1.getViewObject().executeQuery();
        
        System.err.println("Shipment_id"+resolveExpression("#{pageFlowScope.ShipmentId}"));
    }

    public void EditInChangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        DCBindingContainer bindings1 = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter = bindings1.findIteratorBinding("HubCoverGroupIterator");
        if(valueChangeEvent.getNewValue() !=null){
            iter.getViewObject().getCurrentRow().setAttribute("EditIn", 1);
        }
        else{
            iter.getViewObject().getCurrentRow().setAttribute("EditIn", 0);
        }
    }

    public void EditOutChangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        DCBindingContainer bindings1 = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter = bindings1.findIteratorBinding("HubCoverGroupIterator");
        if(valueChangeEvent.getNewValue() !=null){
            iter.getViewObject().getCurrentRow().setAttribute("EditOut", 1);
        }
        else{
            iter.getViewObject().getCurrentRow().setAttribute("EditOut", 0);
        }
    }

    public void CreateCarIssue(PopupFetchEvent popupFetchEvent) {
        // Add event code here...
        ADFUtils.findOperation("CreateInsert").execute();
        Row issue_row=ADFUtils.findIterator("CarIssueVO1Iterator").getCurrentRow();
        Row Car_recon=ADFUtils.findIterator("DataTeamAgentCarReconciliationIterator").getCurrentRow();
        issue_row.setAttribute("CarNo", Car_recon.getAttribute("ShipmentId"));
        issue_row.setAttribute("PostAgent", ADFContext.getCurrent().getSecurityContext().getUserName());
      //  SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
         //   Date date = new Date();  
           // System.out.println(formatter.format(date));
       // issue_row.setAttribute("PostDate", formatter.format(date));
        issue_row.setAttribute("DriverId", Car_recon.getAttribute("DriverName"));
        issue_row.setAttribute("CarWorkDate", Car_recon.getAttribute("ReturnDate"));
        
    }

    public void Submit(ActionEvent actionEvent) {
        // Add event code here...
        close_car();
    }

    public void RejectCar(DialogEvent dialogEvent) {
        // Add event code here...
    }

    public void test(ActionEvent actionEvent) {
        // Add event code here...
        System.err.println("test here");
    }

    public void GetNotesDetailsForCard(ActionEvent actionEvent) {
        // Add event code here...
        System.out.println("AssignmentId"+ADFUtils.getBoundAttributeValue("AssignmentId"));
        ADFUtils.findIterator("ProductContractDDownloadVO2Iterator").getViewObject().setNamedWhereClauseParam("P_ASSIGNMENT_ID", ADFUtils.getBoundAttributeValue("AssignmentId"));
      System.err.println("Here1");
        ADFUtils.findIterator("ProductContractDDownloadVO2Iterator").executeQuery();
       // System.out.println("CardNo"+ADFUtils.getBoundAttributeValue("CardNo"));
        ADFUtils.findIterator("ProductContractVO1Iterator").getViewObject().setNamedWhereClauseParam("P_CARDNO",ADFUtils.getBoundAttributeValue("CardNo"));
       ADFUtils.findIterator("ProductContractVO1Iterator").executeQuery();
       System.err.println("Here");
        JSFUtils.showPopup("notescontract");

    }

    public void AddContractProductContract(ActionEvent actionEvent) {
        // Add event code here...
        ADFUtils.findOperation("CreateInsert").execute();
        ADFUtils.findIterator("ProductContractVO2Iterator").getViewObject().getCurrentRow().setAttribute("CardNo", ADFUtils.getBoundAttributeValue("CardNo"));
        ADFUtils.findIterator("ProductContractVO2Iterator").getViewObject().getCurrentRow().setAttribute("Active", 1);

        AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("t2"));
    }

    public void MarkNotesAssignmentAsdone(ActionEvent actionEvent) {
        // Add event code here...
        System.err.println("AssignmentId"+ADFUtils.getBoundAttributeValue("AssignmentId"));
         /*  ADFUtils.callStoredFunction((AppModuleAMImpl)ADFUtils.getApplicationModuleForDataControl("AppModuleAMDataControl"),
                                                                       Types.NUMERIC, "MARK_NOTES_ASS_AS_DONE(?,?)",
                                                                       new Object[] {resolveExpression("#{bindings.AssignmentId.inputValue}") , ADFContext.getCurrent().getSecurityContext().getUserName() });
       */ ADFUtils.findOperation("Commit").execute();
        ViewObject Pcontract=ADFUtils.findIterator("ProductContractVO1Iterator").getViewObject();
        Pcontract.clearCache();
        Pcontract.setQueryMode(ViewObject.QUERY_MODE_SCAN_DATABASE_TABLES);
        Pcontract.executeQuery();
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("t2"));
        JSFUtils.hidePopup("notescontract");

        JSFUtils.addFacesInformationMessage("Updates Submitted Successfully ");
        
    }

    public void AddNotesProductContract(ActionEvent actionEvent) {
        // Add event code here...
        ADFUtils.findOperation("CreateInsert").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("tab2"));

    }

    public void UpdatePendingWorkAgent(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        System.err.println("Entered");
        Row r=ADFUtils.findIterator("ProductContractDDownloadVOIterator").getViewObject().getCurrentRow();
        r.setAttribute("UpdatedBy", ADFContext.getCurrent().getSecurityContext().getUserName());
    }

    public void UpdateNotesAgent(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        Row ass_det=ADFUtils.findIterator("AssNotesReviewDetailsIterator").getViewObject().getCurrentRow();
        System.err.println("Duration"+ass_det.getAttribute("Duration"));
        Row r=ADFUtils.findIterator("ProductContractVO1Iterator").getViewObject().getCurrentRow();
        r.setAttribute("UpdatedBy", ADFContext.getCurrent().getSecurityContext().getUserName());
        System.err.println("Product"+r.getAttribute("ProductId"));
    }

    public void ChangeEndOfDayAgent(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        Row r=ADFUtils.findIterator("EndOfDayRevDetailsIterator").getViewObject().getCurrentRow();
        r.setAttribute("UpdatedBy", ADFContext.getCurrent().getSecurityContext().getUserName());
    }

    public void ChangeNotesValue(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        FacesContext faces_context=FacesContext.getCurrentInstance();
        UIViewRoot root = faces_context.getViewRoot();
        RichInputText input_text=(RichInputText)root.findComponent("it1");
        System.err.println("New Value :"+input_text.getValue());
    }
    public void ValidateProductsMatchPackage(ActionEvent actionEvent) {
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
           ProductContractVORowImpl row1 = null;
           vo1.reset();
           RowSetIterator rs1 = vo1.createRowSetIterator(null);
           rs1.reset();
           System.err.println("Iterator");
           while (rs1.hasNext()) {
              System.err.println("row");
           row1 = (ProductContractVORowImpl)rs1.next();
                       Object ProductId = row1.getAttribute("ProductId");
               System.err.println("Product"+ProductId);
                       Object TreatmentId = row1.getAttribute("TreatmentId");
               System.err.println("Treatment"+TreatmentId);
                       Object PackageNo = row1.getAttribute("PackageNo");
               System.err.println("pno"+PackageNo);
               if(PackageNo==null)
               PackageNo=0;
                       Object Quantity = row1.getAttribute("Quantity");
               System.err.println("quantity"+Quantity);
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
    ADFUtils.setBoundAttributeValue("CheckPackageName", null);
            ADFUtils.setBoundAttributeValue("VALID", 1);
            
                AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("b4"));

            JSFUtils.addFacesInformationMessage("Your Product Contract Is Valid");
            
            }
       }
    public void ChangeToPackage(DialogEvent dialogEvent) {
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

                ProductConId.append(row1.getAttribute("Id")).append(",");
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

            AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("t2"));
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
             AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("t2"));
             
            ADFUtils.setBoundAttributeValue("VALID", 1);
           /* Long TotalProd=0L;
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
            
        }
        
        */
           JSFUtils.addFacesInformationMessage("Contract Updated Successfully");

    }
    
    }
    
    public void ChangeToPackageSelected(DialogEvent dialogEvent) {
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
                        Object Quantityp =(Long) row1.getAttribute("Quantity");
                      //  Object ConfQuant = row1.getAttribute("ConfQuant");
                       // Object ProductFlag = row1.getAttribute("DeliveryFlag1");
                       // Long ProductFlag1 = (Long)row1.getAttribute("DeliveryFlag1");
                        if((Quantity == null || Integer.parseInt(Quantityp.toString()) <= 0)){
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
                if(row1.getAttribute("Id")==null)
                {

                        System.err.println("PackProduct");
                        ProductStrb.append(ProductId).append(","); 
                        TreatmentStrb.append(TreatmentId).append(","); 
                        PackageNoStrb.append(PackageNo).append(","); 
                        QuantityPStrb.append(Quantity).append(",");
                    //   ConfQuantStrb.append(ConfQuant).append(",");
                      //  ProductFlagStrb.append(ProductFlag).append(",");
                ProductConId.append(row1.getAttribute("Id")).append(",");
                    
                
                }
            }
            rs1.closeRowSetIterator();
            if(ProductStrb.length()!=0 && TreatmentStrb.length()!=0 && PackageNoStrb.length()!=0 && QuantityPStrb.length()!=0 && ProductFlagStrb.length()!=0){
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
            System.err.println("SelectedPackage : "+ADFUtils.getBoundAttributeValue("SelectedId"));
            ADFUtils.callStoredFunction((AppModuleAMImpl) ADFUtils.getApplicationModuleForDataControl("AppModuleAMDataControl"),
                                                      Types.VARCHAR, "UPDATE_CON_PACKAGE_PRODUCTS(?,?,?,?,?,?)",
                                                      new Object[] {ProductStr ,TreatmentStr , QuantityPStr , ADFUtils.getBoundAttributeValue("CardNo") , ProductConIdStr , ADFUtils.getBoundAttributeValue("SelectedId")});
           // ADFUtils.findOperation("Rollback").execute();

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
            ADFUtils.findOperation("Execute1").execute();

          System.err.println("xxxxxxx");
           // vo3.executeQuery();
            AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("ProductContractDeliveryVOTab"));
            
            ADFUtils.setBoundAttributeValue("VALID", 1);
            ADFUtils.setBoundAttributeValue("PackSelected", 0);
           /* Long TotalProd=0L;
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
            
        }
        */
           JSFUtils.addFacesInformationMessage("Contract Updated Successfully");
        
    }
    
}

    public void ChangeDataReviewStatus(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        
        Row row=ADFUtils.findIterator("ContractsReviewIterator").getViewObject().getCurrentRow();
        row.setAttribute("UpdatedBy", ADFContext.getCurrent().getSecurityContext().getUserName());
        System.err.println("ass_id"+row.getAttribute("AssignmentId"));
    }

    public void ChangeReconciliationNotesStatus(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        Row row=ADFUtils.findIterator("ReconciliationNotesIterator").getViewObject().getCurrentRow();
        row.setAttribute("UpdatedBy", ADFContext.getCurrent().getSecurityContext().getUserName());
    System.err.println("id"+row.getAttribute("Id"));
    }

    public void ChangeCoverProductAgent(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        Row row=ADFUtils.findIterator("CoverProductIterator").getViewObject().getCurrentRow();
        row.setAttribute("UpdatedBy", ADFContext.getCurrent().getSecurityContext().getUserName());
    }

    public void ChangePilotCoverAgent(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        Row row=ADFUtils.findIterator("PilotCoverPilotCoverProductIterator").getViewObject().getCurrentRow();
        row.setAttribute("EditedBy", ADFContext.getCurrent().getSecurityContext().getUserName());
    }

    public void ChangeDriverPilotReconAgent(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        Row row=ADFUtils.findIterator("PilotCoverReconDriverPilotIterator").getViewObject().getCurrentRow();
        row.setAttribute("UpdatedBy", ADFContext.getCurrent().getSecurityContext().getUserName());
    }
    
    public void ChangeProductDel(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        Row ass_det=ADFUtils.findIterator("AssNotesReviewDetailsIterator").getViewObject().getCurrentRow();
        System.err.println("Duration"+ass_det.getAttribute("Duration"));
        Row r=ADFUtils.findIterator("ProductContractVO1Iterator").getViewObject().getCurrentRow();
        r.setAttribute("UpdatedBy", ADFContext.getCurrent().getSecurityContext().getUserName());
        System.err.println("Product"+r.getAttribute("ProductName"));
        Row currentRow = ADFUtils.findIterator("ProductContractVO1Iterator")
                                 .getViewObject()
                                 .getCurrentRow();
        System.err.println("Product : "+valueChangeEvent.getNewValue());

        String val=(valueChangeEvent.getNewValue()).toString();
        System.err.println("Product : "+val);
       // System.err.println("SKU"+ADFUtils.getBoundAttributeValue("ProductId"));
        ViewObject vo=ADFUtils.findIterator("ProductPackageVO1Iterator").getViewObject();
        //  System.err.println("ValueSku : "+ADFUtils.getBoundAttributeValue("ProductSku"));
        RowSetIterator rs=vo.createRowSetIterator(null);
         r=null;
       int check_pack=0;
        while(rs.hasNext()) {
            r=rs.next();
            String Val_Prod=r.getAttribute("ProductName").toString();
            System.err.println("PId"+Val_Prod);
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
                           ADFUtils.findOperation("CreateInsert").execute();
                           AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("t2"));

                       }
                       else {
                           ADFUtils.findOperation("CreateInsert").execute();
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
                    System.err.println("Duration : "+ADFUtils.getBoundAttributeValue("Duration"));
                    Long Pack_Price=0L;
                    if(ADFUtils.getBoundAttributeValue("Duration")!=null&&(Long)ADFUtils.getBoundAttributeValue("Duration")==1)
                    {
                    Pack_Price= Long.getLong(r.getAttribute("Price4w").toString());
                    }
                    else if(ADFUtils.getBoundAttributeValue("Duration")!=null&&(Long)ADFUtils.getBoundAttributeValue("Duration")==2) {
                        Pack_Price=Long.getLong(r.getAttribute("Price2w").toString());

                    }
                    else {
                        Pack_Price= Long.getLong(r.getAttribute("Price1w").toString());

                    }
                    Long Prod_Price =0L;
                   /* if( ADFUtils.getBoundAttributeValue("productsprice")!=null)
                        Prod_Price=(Long)ADFUtils.getBoundAttributeValue("productsprice")+Pack_Price;
                    System.err.println("Product Price"+Pack_Price);
                    Long Promo_Price=0L;
                    if(ADFUtils.getBoundAttributeValue("PromosPrice")!=null)
                        Promo_Price=(Long)ADFUtils.getBoundAttributeValue("PromosPrice");
                    Long Total_Amount=0L;
                    Total_Amount=Prod_Price+Promo_Price;
                    ADFUtils.setBoundAttributeValue("productsprice", Prod_Price);
                    ADFUtils.setBoundAttributeValue("TotalOrder", Total_Amount);
                    */
                    RowSetIterator rs2=vo2.createRowSetIterator(null);
                    
                    for(int c=0;c<p_ids.length;c++) {
                        System.err.println(c);
                        
                        if(c==0) {
                            Row r3=null;

                            r3=rs2.first();
                           // r3.setAttribute("ProductName", p_names[c]);
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

                          /*  AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("productNameId"));
                            AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("ProductContractDeliveryVOTopLcSdi"));
*/
                            AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("t2"));
                           /* AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("it4"));

                            AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("ProductContractDeliveryVOTab"));
                            */ 
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
                                   
                           /* AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("productNameId"));
                            AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("ProductContractDeliveryVOTopLcSdi"));

                            AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("it26"));
                            AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("it4"));
*/
                            AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("t2"));

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
    ViewObject vo3 = ADFUtils.findIterator("ProductContractVO1Iterator").getViewObject();
    System.err.println("Package");

    vo3.executeQuery(); 
     FacesContext.getCurrentInstance().renderResponse();

    }
        System.err.println("QueryExecuted");

    
   /* AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("productNameId"));
    System.err.println("1");
    AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("ProductContractDeliveryVOTopLcSdi"));

    AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("it26"));
    
        AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("ProductContractDeliveryVOTab"));
      //   FacesContext.getCurrentInstance().renderResponse();
*/
        System.err.println("2");


    }

    public void ChangeContractProductId(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        UIComponent c = valueChangeEvent.getComponent();
        c.processUpdates(FacesContext.getCurrentInstance());
        FacesContext.getCurrentInstance().renderResponse();
        System.err.println(valueChangeEvent.getNewValue().toString());
        ADFUtils.findIterator("ProductContractVO2Iterator").getViewObject().getCurrentRow().setAttribute("Active", 1);
        System.err.println(ADFUtils.findIterator("ProductContractVO2Iterator").getViewObject().getCurrentRow().getAttribute("ProductId"));
      
      String val=ADFUtils.findIterator("ProductContractVO2Iterator").getViewObject().getCurrentRow().getAttribute("ProductId").toString();
      
          
          Row ass_det=ADFUtils.findIterator("NewCancelSalesContractsVO1Iterator").getViewObject().getCurrentRow();
        System.err.println("Duration"+ass_det.getAttribute("Duration"));
        Long duration=(Long)ass_det.getAttribute("Duration");
        Row r=ADFUtils.findIterator("ProductContractVO2Iterator").getViewObject().getCurrentRow();
        r.setAttribute("UpdatedBy", ADFContext.getCurrent().getSecurityContext().getUserName());
       // System.err.println("Product"+r.getAttribute("ProductName"));
  // System.err.println("Product"+ADFUtils.getBoundAttributeValue("ProductId"));
  ViewObject vo=ADFUtils.findIterator("ProductPackageVO1Iterator").getViewObject();
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
              ADFUtils.findIterator("ProductContractVO2Iterator").getViewObject().removeCurrentRow();
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
               ViewObject rv=ADFUtils.findIterator("ProductContractVO2Iterator").getViewObject();
               //  System.err.println("ValueSku : "+ADFUtils.getBoundAttributeValue("ProductSku"));
               RowSetIterator rsv2=rv.createRowSetIterator(null);
              //  rv.executeQuery();
              System.err.println("LengthDet:"+len);
              
              String [] p_q=r.getAttribute("DetQuan").toString().split(",");
              String [] p_names=r.getAttribute("DetNames").toString().split(",");
              System.err.println("Length:"+r.getAttribute("DetIds").toString().length());
               ViewObject vo2=ADFUtils.findIterator("ProductContractVO2Iterator").getViewObject();
               //  System.err.println("ValueSku : "+ADFUtils.getBoundAttributeValue("ProductSku"));
               System.err.println("Duration : "+ADFUtils.getBoundAttributeValue("Duration"));
               Long Pack_Price=0L;
               if(ADFUtils.getBoundAttributeValue("Duration")!=null&&((Long)ADFUtils.getBoundAttributeValue("Duration")==1||(Long)ADFUtils.getBoundAttributeValue("Duration")==3))
               {
               Pack_Price= Long.getLong(r.getAttribute("Price4w").toString());
               }
               else if(ADFUtils.getBoundAttributeValue("Duration")!=null&&(Long)ADFUtils.getBoundAttributeValue("Duration")==2) {
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
                       if(ADFUtils.getBoundAttributeValue("Duration")!=null&&((Long)ADFUtils.getBoundAttributeValue("Duration")==1||(Long)ADFUtils.getBoundAttributeValue("Duration")==3))
                       {
                       r3.setAttribute("UnitPrice", r.getAttribute("Price4w"));
                       }
                       else if(ADFUtils.getBoundAttributeValue("Duration")!=null&&(Long)ADFUtils.getBoundAttributeValue("Duration")==2) {
                           r3.setAttribute("UnitPrice", r.getAttribute("Price2w"));

                       }
                       else {
                           r3.setAttribute("UnitPrice", r.getAttribute("Price1w"));

                       }
                      
                       
                       r3.setAttribute("Quantity", p_q[c1]);
                       r3.setAttribute("PartialSubmit", 1);
                       System.err.println("Pid set");
                       System.err.println("PName:"+r3.getAttribute("ProductId"));

                       System.err.println("RowId:"+r3.getAttribute("Id"));

                     /*  AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("productNameId"));
                       AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("ProductContractDeliveryVOTopLcSdi"));
              */
                       AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("t2"));
                      /* AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("it4"));

                       AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("ProductContractDeliveryVOTab"));
                       */ 
                       FacesContext.getCurrentInstance().renderResponse();

                   }
                   else {

                       Row r4=null;

                       r4=rs2.next();
                       r4.setAttribute("NewPackFlag", 1);                         
                      r4.setAttribute("ProductId", p_ids[c1]);

                       r4.setAttribute("Quantity", p_q[c1]);
                       r4.setAttribute("PartialSubmit", 1);
                       System.err.println("RowId:"+r4.getAttribute("Id"));
                       if(ADFUtils.getBoundAttributeValue("Duration")!=null&&((Long)ADFUtils.getBoundAttributeValue("Duration")==1||(Long)ADFUtils.getBoundAttributeValue("Duration")==3))
                       {
                       r4.setAttribute("UnitPrice", r.getAttribute("Price4w"));
                       }
                       else if(ADFUtils.getBoundAttributeValue("Duration")!=null&&(Long)ADFUtils.getBoundAttributeValue("Duration")==2) {
                           r4.setAttribute("UnitPrice", r.getAttribute("Price2w"));

                       }
                       else {
                           r4.setAttribute("UnitPrice", r.getAttribute("Price1w"));

                       }
                       System.err.println("UnitPrice set");
                       System.err.println("PName:"+r4.getAttribute("ProductId"));
                              
                    
                       AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("t2"));

              System.err.println("xxxx");
                   }
               
               }
               
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
                     ADFUtils.findIterator("ProductContractVO2Iterator").getViewObject().getCurrentRow().setAttribute("UnitPrice", Price);
              ADFUtils.findIterator("ProductContractVO2Iterator").getViewObject().getCurrentRow().setAttribute("UpdatedBy", ADFContext.getCurrent().getSecurityContext().getUserName());
              ADFUtils.findIterator("ProductContractVO2Iterator").getViewObject().getCurrentRow().setAttribute("Active", 1);
              AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("t2"));

                    System.err.println("Price"+Price);
          }
      }
  }
    }

    public void CommitContractProductChanges(DialogEvent dialogEvent) {
        // Add event code here...
        ADFUtils.findOperation("Commit").execute();
        ADFUtils.findOperation("Execute").execute();
        JSFUtils.addFacesInformationMessage("Data Submitted Successfully");
    }
    
    public void ChangeToContractPackageSelected(DialogEvent dialogEvent) {
        // Add event code here...
        if(dialogEvent.getOutcome().equals(dialogEvent.getOutcome().yes)) {
            ViewObject vo1 = ADFUtils.findIterator("ProductContractVO2Iterator").getViewObject();
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
                       Long ProdConId=(Long)row1.getAttribute("Id");
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
                if(row1.getAttribute("Id")==null)
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


            ViewObject vo3 = ADFUtils.findIterator("ProductContractVO2Iterator").getViewObject();
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
            AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("t2"));
            
            ADFUtils.setBoundAttributeValue("VALID", 1);
            ADFUtils.setBoundAttributeValue("PackSelected", 0);
  
        JSFUtils.addFacesInformationMessage("Contract Updated Successfully");
        }
        
        
    }
    public void ValidateContractProductsMatchPackage(ActionEvent actionEvent) {
           // Add event code here...
           ViewObject vo1 = ADFUtils.findIterator("ProductContractVO2Iterator").getViewObject();
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
           ProductContractVORowImpl row1 = null;
           vo1.reset();
           RowSetIterator rs1 = vo1.createRowSetIterator(null);
           rs1.reset();
           System.err.println("Iterator");
           while (rs1.hasNext()) {
              System.err.println("row");
           row1 = (ProductContractVORowImpl)rs1.next();
                       Object ProductId = row1.getAttribute("ProductId");
               System.err.println("Product"+ProductId);
                       Object TreatmentId = row1.getAttribute("TreatmentId");
               System.err.println("Treatment"+TreatmentId);
                       Object PackageNo = row1.getAttribute("PackageNo");
               System.err.println("pno"+PackageNo);
               if(PackageNo==null)
               PackageNo=0;
                       Object Quantity = row1.getAttribute("Quantity");
               System.err.println("quantity"+Quantity);
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
    ADFUtils.setBoundAttributeValue("CheckPackageName", null);
            ADFUtils.setBoundAttributeValue("VALID", 1);
            
                AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("b4"));

            JSFUtils.addFacesInformationMessage("Your Product Contract Is Valid");
            
            }
       }
    public void ChangeContractToPackage(DialogEvent dialogEvent) {
        // Add event code here...
        if(dialogEvent.getOutcome().equals(dialogEvent.getOutcome().yes)) {
            ViewObject vo1 = ADFUtils.findIterator("ProductContractVO2Iterator").getViewObject();
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

                ProductConId.append(row1.getAttribute("Id")).append(",");
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

            ViewObject vo3 = ADFUtils.findIterator("ProductContractVO2Iterator").getViewObject();
            vo3.clearCache();
            vo3.executeQuery();
            // ADFUtils.findOperation("Execute").execute();
    System.err.println("xxxxxxx");
            vo3.executeQuery();

            AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("t2"));
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
             AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("t2"));
             
            ADFUtils.setBoundAttributeValue("VALID", 1);
           /* Long TotalProd=0L;
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
            
        }
        
        */
           JSFUtils.addFacesInformationMessage("Contract Updated Successfully");

    }
    
    }

    public void ContractProductContractAgent(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        ADFUtils.findIterator("ProductContractVO2Iterator").getViewObject().getCurrentRow().setAttribute("UpdatedBy", ADFContext.getCurrent().getSecurityContext().getUserName());
    }
    
    
    public void ChangeNotesProductId(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        UIComponent c = valueChangeEvent.getComponent();
        c.processUpdates(FacesContext.getCurrentInstance());
        FacesContext.getCurrentInstance().renderResponse();
        System.err.println(valueChangeEvent.getNewValue().toString());
        ADFUtils.findIterator("ProductContractVO1Iterator").getViewObject().getCurrentRow().setAttribute("Active", 1);
        System.err.println(ADFUtils.findIterator("ProductContractVO1Iterator").getViewObject().getCurrentRow().getAttribute("ProductId"));
      
      String val=ADFUtils.findIterator("ProductContractVO1Iterator").getViewObject().getCurrentRow().getAttribute("ProductId").toString();
      
          
          Row ass_det=ADFUtils.findIterator("AssNotesReviewDetailsIterator").getViewObject().getCurrentRow();
        System.err.println("Duration"+ass_det.getAttribute("Duration"));
        Long duration=(Long)ass_det.getAttribute("Duration");
        Row r=ADFUtils.findIterator("ProductContractVO1Iterator").getViewObject().getCurrentRow();
        r.setAttribute("UpdatedBy", ADFContext.getCurrent().getSecurityContext().getUserName());
       // System.err.println("Product"+r.getAttribute("ProductName"));
    // System.err.println("Product"+ADFUtils.getBoundAttributeValue("ProductId"));
    ViewObject vo=ADFUtils.findIterator("ProductPackageVO1Iterator").getViewObject();
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
                      ADFUtils.findOperation("CreateInsert").execute();
                      AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("t2"));

                  }
                  else {
                      ADFUtils.findOperation("CreateInsert").execute();
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
               System.err.println("Duration : "+ADFUtils.getBoundAttributeValue("Duration"));
               Long Pack_Price=0L;
               if(ADFUtils.getBoundAttributeValue("Duration")!=null&&((Long)ADFUtils.getBoundAttributeValue("Duration")==1||(Long)ADFUtils.getBoundAttributeValue("Duration")==3))
               {
               Pack_Price= Long.getLong(r.getAttribute("Price4w").toString());
               }
               else if(ADFUtils.getBoundAttributeValue("Duration")!=null&&(Long)ADFUtils.getBoundAttributeValue("Duration")==2) {
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
                       if(ADFUtils.getBoundAttributeValue("Duration")!=null&&((Long)ADFUtils.getBoundAttributeValue("Duration")==1||(Long)ADFUtils.getBoundAttributeValue("Duration")==3))
                       {
                       r3.setAttribute("UnitPrice", r.getAttribute("Price4w"));
                       }
                       else if(ADFUtils.getBoundAttributeValue("Duration")!=null&&(Long)ADFUtils.getBoundAttributeValue("Duration")==2) {
                           r3.setAttribute("UnitPrice", r.getAttribute("Price2w"));

                       }
                       else {
                           r3.setAttribute("UnitPrice", r.getAttribute("Price1w"));

                       }
                      
                       
                       r3.setAttribute("Quantity", p_q[c1]);
                       r3.setAttribute("PartialSubmit", 1);
                       System.err.println("Pid set");
                       System.err.println("PName:"+r3.getAttribute("ProductId"));

                       System.err.println("RowId:"+r3.getAttribute("Id"));

                     /*  AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("productNameId"));
                       AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("ProductContractDeliveryVOTopLcSdi"));
              */
                       AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("t2"));
                      /* AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("it4"));

                       AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("ProductContractDeliveryVOTab"));
                       */ 
                       FacesContext.getCurrentInstance().renderResponse();

                   }
                   else {

                       Row r4=null;

                       r4=rs2.next();
                       r4.setAttribute("NewPackFlag", 1);                         
                      r4.setAttribute("ProductId", p_ids[c1]);

                       r4.setAttribute("Quantity", p_q[c1]);
                       r4.setAttribute("PartialSubmit", 1);
                       System.err.println("RowId:"+r4.getAttribute("Id"));
                       if(ADFUtils.getBoundAttributeValue("Duration")!=null&&((Long)ADFUtils.getBoundAttributeValue("Duration")==1||(Long)ADFUtils.getBoundAttributeValue("Duration")==3))
                       {
                       r4.setAttribute("UnitPrice", r.getAttribute("Price4w"));
                       }
                       else if(ADFUtils.getBoundAttributeValue("Duration")!=null&&(Long)ADFUtils.getBoundAttributeValue("Duration")==2) {
                           r4.setAttribute("UnitPrice", r.getAttribute("Price2w"));

                       }
                       else {
                           r4.setAttribute("UnitPrice", r.getAttribute("Price1w"));

                       }
                       System.err.println("UnitPrice set");
                       System.err.println("PName:"+r4.getAttribute("ProductId"));
                              
                    
                       AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("t2"));

              System.err.println("xxxx");
                   }
               
               }
               
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
              AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("t2"));

                    System.err.println("Price"+Price);
          }
      }
    }
    }
    public void ChangeToNotesPackageSelected(DialogEvent dialogEvent) {
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
                       Long ProdConId=(Long)row1.getAttribute("Id");
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
                if(row1.getAttribute("Id")==null)
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
            AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("t2"));
            
            ADFUtils.setBoundAttributeValue("VALID", 1);
            ADFUtils.setBoundAttributeValue("PackSelected", 0);
    
        JSFUtils.addFacesInformationMessage("Contract Updated Successfully");
        }
        
        
    }

    public void ChangePendingCloseCode(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        UIComponent c = valueChangeEvent.getComponent();
        c.processUpdates(FacesContext.getCurrentInstance());
        FacesContext.getCurrentInstance().renderResponse();
       System.err.println(ADFUtils.getBoundAttributeValue("ContractStatus"));
        DCBindingContainer bindings1 = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter = bindings1.findIteratorBinding("CloseCodeReasonsVO1Iterator");
        System.out.println("P_CloseCodeGroup" + resolveExpression("#{bindings.GroupId.inputValue}"));
        
        iter.getViewObject()
            .setNamedWhereClauseParam("P_CloseCodeGroup", resolveExpression("#{bindings.ContractStatus.inputValue}"));
        iter.getViewObject().executeQuery();
        AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("soc2"));

    }

    public void UpdatePendingWorkQuantitiesPrice(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        System.err.println("Entered");
        Row r=ADFUtils.findIterator("ProductContractDDownloadVOIterator").getViewObject().getCurrentRow();
        r.setAttribute("UpdatedBy", ADFContext.getCurrent().getSecurityContext().getUserName());
        DCIteratorBinding iter=ADFUtils.findIterator("ProductContractDDownloadVOIterator");
        RowSetIterator rs=iter.getViewObject().createRowSetIterator(null);
        Long Price=0L;
        UIComponent c = valueChangeEvent.getComponent();
        c.processUpdates(FacesContext.getCurrentInstance());
        FacesContext.getCurrentInstance().renderResponse();
        String included_pack="";
        System.err.println("Value"+valueChangeEvent.getNewValue());
        while(rs.hasNext()) {
            Row r1=rs.next();
            System.err.println(r1.getAttribute("PackageNo"));
            System.err.println(r1.getAttribute("QuntityReplaced"));
            System.err.println(r1.getAttribute("UnitPrice"));
            
            if((Long)r1.getAttribute("PackageNo")==0 || r1.getAttribute("PackageNo").toString().equals("0"))
            {
            if((Long)r1.getAttribute("QuntityReplaced")>0 || !(r1.getAttribute("QuntityReplaced").toString().equals("0")))
            {
            Long Row_Price=(Long)r1.getAttribute("QuntityReplaced")*(Long)r1.getAttribute("UnitPrice");
            Price+=Row_Price;
            }
            }
            else {
                String curr_pack=r1.getAttribute("PackageNo").toString()+",";
                if(included_pack.contains(curr_pack)) {
                    
                }
                else {
                    if((Long)r1.getAttribute("QuntityReplaced")>0 || !(r1.getAttribute("QuntityReplaced").toString().equals("0")))
                    {
                    Long Row_Price=(Long)r1.getAttribute("QuntityReplaced")*(Long)r1.getAttribute("UnitPrice");
                    Price+=Row_Price;
                        included_pack+=r1.getAttribute("PackageNo").toString()+",";
                    } 
                }
            }
        }
        System.err.println("Total Amount"+Price);
        ADFUtils.setBoundAttributeValue("ActualPaid", Price);
        AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("it1"));

    }

    public void ChangePendingCustomerNotes(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        UIComponent c = valueChangeEvent.getComponent();
        c.processUpdates(FacesContext.getCurrentInstance());
        FacesContext.getCurrentInstance().renderResponse();
    }

    public void MarkAgentNotesDone(DialogEvent dialogEvent) {
        // Add event code here...
        System.err.println("1");
        System.err.println("AssignmentId"+ADFUtils.getBoundAttributeValue("AssignmentId"));
        System.err.println("2");
        String Status=ADFUtils.getBoundAttributeValue("Status1").toString();
        String CloseCodeReason =ADFUtils.getBoundAttributeValue("CloseCodeReason1").toString();
        String Actual_Paid=ADFUtils.getBoundAttributeValue("ActualPaid").toString();
        String customer_notes=ADFUtils.getBoundAttributeValue("CustomerNotes").toString();
       
        ADFUtils.callStoredFunction((AppModuleAMImpl)ADFUtils.getApplicationModuleForDataControl("AppModuleAMDataControl"),
                                                                       Types.NUMERIC, "MARK_NOTES_ASS_AS_DONE(?,?,?,?,?,?)",
                                                                       new Object[] {resolveExpression("#{bindings.AssignmentId.inputValue}") , ADFContext.getCurrent().getSecurityContext().getUserName() , Status ,CloseCodeReason , Actual_Paid, customer_notes});
        System.err.println("Function Submitted");
      /*  ADFUtils.findOperation("Rollback").execute();
        //FacesContext.getCurrentInstance().renderResponse();
        System.err.println("Commit Done");
        try{
         ViewObject vo=ADFUtils.findIterator("AssNotesReviewDetailsIterator").getViewObject();
         System.err.println("Get View Object");
               Row r1=vo.getCurrentRow();
               vo.clearCache();
         vo.setQueryMode(ViewObject.QUERY_MODE_SCAN_DATABASE_TABLES);
          System.err.println("Set Query Mode");                                                               
         vo.executeQuery();
         vo.setCurrentRow(r1);
         System.err.println("Query Executed");
            System.err.println("Partial Target");
            ViewObject vo2=ADFUtils.findIterator("DataTeamAgentNotesReviewIterator").getViewObject();
            System.err.println("Second View");
            Row r2=vo2.getCurrentRow();
            vo2.clearCache();
            vo2.setQueryMode(ViewObject.QUERY_MODE_SCAN_DATABASE_TABLES);
            System.err.println("Query Mode For Second View");
             vo2.executeQuery();
             vo2.setCurrentRow(r2);
            System.err.println("Second View Executed");
            FacesContext.getCurrentInstance().renderResponse();

        }
        catch (RuntimeException tre) {
            System.err.println(tre.getMessage());
        }
      
       AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("t3"));
       System.err.println("Set Partial Target");
//Row r=ADFUtils.findIterator("AssNotesReviewDetailsIterator").getCurrentRow();
        AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("DataTeamAgentNotesReviewTab"));
//System.err.println(ADFUtils.findIterator("AssNotesReviewDetailsIterator").getViewObject().getCurrentRow().getKey().toStringFormat(true));
*/
      AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("t3"));

     // AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("DataTeamAgentNotesReviewTab"));

        JSFUtils.hidePopup("notescontract");
  //      System.err.println(ADFUtils.findIterator("AssNotesReviewDetailsIterator").getViewObject().getCurrentRow().getKey().toStringFormat(true));
//ADFUtils.findIterator("AssNotesReviewDetailsIterator").getViewObject().executeQuery();
        JSFUtils.addFacesInformationMessage("Assignment Marked As Done Successfully");
      //  System.err.println(ADFUtils.findIterator("AssNotesReviewDetailsIterator").getViewObject().getCurrentRow().getKey().toStringFormat(true));
//ADFUtils.findIterator("AssNotesReviewDetailsIterator").getViewObject().setCurrentRow(r);
    }

    public void ChangeNotesReviewStatus(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        UIComponent c = valueChangeEvent.getComponent();
        ViewObject vo=ADFUtils.findIterator("NAssNotesDetVO3Iterator").getViewObject();
        //vo.getCurrentRow().setAttribute("", arg1);

        c.processUpdates(FacesContext.getCurrentInstance());
        FacesContext.getCurrentInstance().renderResponse();
        AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("soc14"));

    }

    public void FilterNewCancelContractsLink(ActionEvent actionEvent) {
        // Add event code here...
        System.err.println("Start ");
        System.err.println("Assid"+ADFUtils.findIterator("NewCancelSalesContractsIterator").getViewObject().getCurrentRow().getAttribute("AssignmentId"));
        DCBindingContainer bindings1 = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter = bindings1.findIteratorBinding("NewCancelSalesContractsProducts1Iterator");
        System.out.println("Ass" + resolveExpression("#{bindings.AssignmentId.inputValue}"));
        iter.getViewObject()
            .setNamedWhereClauseParam("P_ASSIGNMENT_ID", resolveExpression("#{bindings.AssignmentId.inputValue}"));
        iter.getViewObject().executeQuery();
        System.err.println("CardNo:"+resolveExpression("#{bindings.CardNo.inputValue}"));
        //System.out.println("Card"+ADFUtils.getBoundAttributeValue("CardNo"));

        DCBindingContainer bindings2=(DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter2=bindings2.findIteratorBinding("ProductContractVO2Iterator");
        // System.out.println("Card"+ADFUtils.getBoundAttributeValue("CardNo"));
        iter2.getViewObject().setNamedWhereClauseParam("P_CARDNO", resolveExpression("#{bindings.CardNo.inputValue}"));
        iter2.getViewObject().executeQuery();
        System.err.println("Status"+ resolveExpression("#{bindings.ContractStatus.inputValue}"));
        System.err.println("Reason"+ resolveExpression("#{bindings.CloseCode.inputValue}"));
        System.err.println("Customer Notes"+ resolveExpression("#{bindings.CustomerNotes.inputValue}"));
        ADFUtils.setBoundAttributeValue("Id", resolveExpression("#{bindings.DataStatus.inputValue}"));
        //AdfFacesContext.getCurrentInstance().addPartialTarget(bindings2.bindingContext(). findComponentInRoot("soc1"));
        DCBindingContainer bindings11 = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        JSFUtils.showPopup("p11");
        System.err.println("Id Set");
    }

    public void SubmitContractReviewDataStatus(DialogEvent dialogEvent) {
        // Add event code here...
        if(dialogEvent.getOutcome().equals(dialogEvent.getOutcome().yes)) {
            CallableStatement cst = null;
            System.err.println("AssId"+resolveExpression("#{bindings.AssignmentId.inputValue}"));
            String Result = "";
            ADFUtils.callStoredFunction((AppModuleAMImpl)ADFUtils.getApplicationModuleForDataControl("AppModuleAMDataControl"),
                                                                                    Types.NUMERIC, "SUBMIT_CONTRACTS_DATA_STATUS(?,?,?,?,?,?)",
                                                                                    new Object[] {resolveExpression("#{bindings.AssignmentId.inputValue}") , resolveExpression("#{bindings.DataStatus1.inputValue}") , ADFContext.getCurrent().getSecurityContext().getUserName()  , ADFUtils.getBoundAttributeValue("ContractStatus1") , ADFUtils.getBoundAttributeValue("CloseCode1") , ADFUtils.getBoundAttributeValue("CustomerNotes") });
              System.err.println("Function Executed");
            ADFUtils.findOperation("Rollback").execute();
            System.err.println("Rolled Back");
               ViewObject vo=ADFUtils.findIterator("NewCancelSalesContractsIterator").getViewObject();
            System.err.println("View Object Get");
               Row r=vo.getCurrentRow();
               System.err.println("Row ");
               vo.clearCache();
               System.err.println("Cache Cleared");
               vo.setQueryMode(ViewObject.QUERY_MODE_SCAN_DATABASE_TABLES);
               System.err.println("Set Query Mode");
               vo.executeQuery();
               System.err.println("Executed");
               vo.setCurrentRow(r);
          //  ADFUtils.findOperation("Rollback").execute();

            ViewObject master_v=ADFUtils.findIterator("DataTeamAgentContractReviewIterator").getViewObject();
            Row master_row=master_v.getCurrentRow();
            master_v.setQueryMode(ViewObject.QUERY_MODE_SCAN_DATABASE_TABLES);
            master_v.executeQuery();
            master_v.setCurrentRow(master_row);
        /*    System.err.println("Master View");
            ViewObject vo=ADFUtils.findIterator("NewCancelSalesContractsIterator").getViewObject();
                        System.err.println("View Object Get");
                           Row r=vo.getCurrentRow();
                           System.err.println("Row ");
                           vo.clearCache();
                           System.err.println("Cache Cleared");
                           vo.setQueryMode(ViewObject.QUERY_MODE_SCAN_DATABASE_TABLES);
                           System.err.println("Set Query Mode");
                           vo.executeQuery();
                           System.err.println("Executed");
                           vo.setCurrentRow(r);
               System.err.println("Set Current Row");
*/               
            AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("NewCancelSalesContractsTab"));
       


               JSFUtils.hidePopup("p11");
            JSFUtils.addFacesInformationMessage("Contract Submitted Successfully");



            
                
        /*    Connection Con = null;
            try {
                Con = getConnection();
                cst = Con.prepareCall("begin ? := SUBMIT_CONTRACTS_DATA_STATUS(?,?,?,?,?,?) ;end;");
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                Object Data_status=resolveExpression("#{bindings.DataStatus1.inputValue}");
                Object Contract_status=ADFUtils.getBoundAttributeValue("ContractStatus1");
                Object Close_code= ADFUtils.getBoundAttributeValue("CloseCode1");
                Object CustomerNotes=ADFUtils.getBoundAttributeValue("CustomerNotes");
                cst.registerOutParameter(1, Types.VARCHAR);
                System.err.println("Submitted Status : "+ resolveExpression("#{bindings.DataStatus1.inputValue}"));
                cst.setObject(2, resolveExpression("#{pageFlowScope.AssignmentId}"));
                cst.setObject(3, resolveExpression("#{bindings.DataStatus1.inputValue}"));
                cst.setObject(4, ADFContext.getCurrent().getSecurityContext().getUserName());
                cst.setObject(5, ADFUtils.getBoundAttributeValue("ContractStatus1"));
                cst.setObject(6, ADFUtils.getBoundAttributeValue("CloseCode1"));
                cst.setObject(7, ADFUtils.getBoundAttributeValue("CustomerNotes"));
                cst.executeUpdate();
                //Result = cst.getObject(1).toString();
                Con.commit();
                ADFUtils.findOperation("Commit").execute();
                
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (Con != null) {
                    try {
                        Con.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (cst != null) {
                    try {
                        cst.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }*/
        }
        
    }

    public void ChangeNotesDetailsAgent(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        ViewObject vo=ADFUtils.findIterator("ProductContractDDownloadVO2Iterator").getViewObject();
        vo.getCurrentRow().setAttribute("UpdatedBy", ADFContext.getCurrent().getSecurityContext().getUserName());
    }

    public void ConfirmSubmitEndOfDayChanges(DialogEvent dialogEvent) {
        // Add event code here...
        if(dialogEvent.getOutcome().equals(dialogEvent.getOutcome().yes))
        {
        ADFUtils.findOperation("Commit").execute();
        JSFUtils.addFacesInformationMessage("Data Submitted Successfully");
        }
    }

    public void NewFilterAssNotes(ActionEvent actionEvent) {
        // Add event code here...
        System.out.println("AssignmentId"+ADFUtils.getBoundAttributeValue("AssignmentId"));
        ADFUtils.findIterator("ProductContractDDownloadVO2Iterator").getViewObject().setNamedWhereClauseParam("P_ASSIGNMENT_ID", ADFUtils.getBoundAttributeValue("AssignmentId"));
        System.err.println("Here1");
        ADFUtils.findIterator("ProductContractDDownloadVO2Iterator").executeQuery();
        // System.out.println("CardNo"+ADFUtils.getBoundAttributeValue("CardNo"));
        ADFUtils.findIterator("ProductContractVO1Iterator").getViewObject().setNamedWhereClauseParam("P_CARDNO",ADFUtils.getBoundAttributeValue("CardNo"));
        ADFUtils.findIterator("ProductContractVO1Iterator").executeQuery();
        System.err.println("Here");
        JSFUtils.showPopup("p13");
    }

    public void MarkAgentNotesDoneNew(DialogEvent dialogEvent) {
        // Add event code here...
        if(dialogEvent.getOutcome().equals(dialogEvent.getOutcome().yes))
        {
      /*  System.err.println("1");
        System.err.println("AssignmentId"+ADFUtils.getBoundAttributeValue("AssignmentId"));
        System.err.println("2");
        String Status=ADFUtils.getBoundAttributeValue("Status2").toString();
        System.err.println(Status);
        String CloseCodeReason =ADFUtils.getBoundAttributeValue("CloseCodeReason2").toString();
        System.err.println(CloseCodeReason);
        String Actual_Paid=ADFUtils.getBoundAttributeValue("ActualPaid1").toString();
        System.err.println(Actual_Paid);
        String customer_notes=ADFUtils.getBoundAttributeValue("CustomerNotes1").toString();
        System.err.println(customer_notes);
            ViewObject vo=ADFUtils.findIterator("NewAssNotesReviewDetailsVO1Iterator").getViewObject();
            System.err.println("Get View Object");
                  Row r1=vo.getCurrentRow();
                  vo.clearCache();
                 // vo.reset();
            vo.setQueryMode(ViewObject.QUERY_MODE_SCAN_DATABASE_TABLES);
             System.err.println("Set Query Mode");                                                               
            vo.executeQuery();
            vo.setCurrentRow(r1);
        ADFUtils.callStoredFunction((AppModuleAMImpl)ADFUtils.getApplicationModuleForDataControl("AppModuleAMDataControl"),
                                                                       Types.NUMERIC, "MARK_NOTES_ASS_AS_DONE(?,?,?,?,?,?)",
                                                                        new Object[] {resolveExpression("#{bindings.AssignmentId.inputValue}") , ADFContext.getCurrent().getSecurityContext().getUserName() , Status ,CloseCodeReason , Actual_Paid, customer_notes});
        System.err.println("Function Submitted");
         //   ADFUtils.findOperation("Commit").execute();
            System.err.println("RolledBack");
            ADFUtils.findOperation("Commit").execute();

        
        System.err.println("Query Executed");
           System.err.println("Partial Target");
            //ADFUtils.findOperation("Commit").execute();
            System.err.println("Commited");
      /*     ViewObject vo2=ADFUtils.findIterator("DataTeamAgentNotesReviewIterator").getViewObject();
           System.err.println("Second View");
           Row r2=vo2.getCurrentRow();
           vo2.clearCache();
           vo2.setQueryMode(ViewObject.QUERY_MODE_SCAN_DATABASE_TABLES);
           System.err.println("Query Mode For Second View");
           // vo2.executeQuery();
          //  vo2.setCurrentRow(r2);
           System.err.println("Second View Executed");
       
       // AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("t3"));

        // AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("DataTeamAgentNotesReviewTab"));
*/
      ADFUtils.findOperation("Commit").execute();
            ViewObject vo=ADFUtils.findIterator("NewAssNotesReviewDetailsVO1Iterator").getViewObject();
            System.err.println("Get View Object");
                  Row r1=vo.getCurrentRow();
                  vo.clearCache();
            vo.setQueryMode(ViewObject.QUERY_MODE_SCAN_DATABASE_TABLES);
             System.err.println("Set Query Mode");                                                               
            vo.executeQuery();
            vo.setCurrentRow(r1);
             AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("t3"));

        JSFUtils.hidePopup("p13");
        System.err.println("popup closed");
        JSFUtils.addFacesInformationMessage("Assignment Marked As Done Successfully");
        System.err.println("Message Displayed");
        }
    }
}
