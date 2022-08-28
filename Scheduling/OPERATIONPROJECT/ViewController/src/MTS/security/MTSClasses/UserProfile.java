package MTS.security.MTSClasses;

import MTS.adf12c.Popups;
import MTS.adf12c.msg.BundleMessageMgr;

import MTS.security.MTSClasses.AdministrationBean;
import MTS.security.MTSClasses.WLSJmxInterface;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.event.PopupFetchEvent;

import oracle.jheadstart.controller.jsf.bean.LoginBean;

import java.util.ResourceBundle;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;

import oracle.adf.share.ADFContext;

public class UserProfile {
    private RichInputText changePasswordOldpassword;
    private RichInputText changePasswordNewPassword1;
    private RichInputText changePasswordNewPassword2;
    private RichInputText callUserName;
    private RichInputText callPassword;
    private RichInputText callExtensionNumber;
    private RichInputText callQueueNumber;

    public UserProfile() {
    }

    public void setChangePasswordOldpassword(RichInputText changePasswordOldpassword) {
        this.changePasswordOldpassword = changePasswordOldpassword;
    }

    public RichInputText getChangePasswordOldpassword() {
        return changePasswordOldpassword;
    }

    public void setChangePasswordNewPassword1(RichInputText changePasswordNewPassword1) {
        this.changePasswordNewPassword1 = changePasswordNewPassword1;
    }

    public RichInputText getChangePasswordNewPassword1() {
        return changePasswordNewPassword1;
    }

    public void setChangePasswordNewPassword2(RichInputText changePasswordNewPassword2) {
        this.changePasswordNewPassword2 = changePasswordNewPassword2;
    }

    public RichInputText getChangePasswordNewPassword2() {
        return changePasswordNewPassword2;
    }

    @SuppressWarnings("oracle.jdeveloper.java.semantic-warning")
    public String ChangePasswordCommit() {
        if (changePasswordNewPassword1.getValue().equals(changePasswordNewPassword2.getValue())) {
            WLSJmxInterface WLclient = new WLSJmxInterface();
            AdministrationBean ab = new AdministrationBean();
            if(WLclient.changeUserPassword(ab.getUser(), changePasswordOldpassword.getValue().toString(),
                                        changePasswordNewPassword1.getValue().toString())) 
            {
                BundleMessageMgr bmm = new BundleMessageMgr("MTS.security.ModelBundle");
                Popups.hidePopup("p1");
                bmm.addMessage("OPERATION_DONE");
            }
        } else {
            addErrorMessage("PASSWORD_MISMATCH");
        }
        return null;
    }

    public void changePasswordPopupFetch(PopupFetchEvent popupFetchEvent) {
        changePasswordOldpassword.setValue("");
        changePasswordNewPassword1.setValue("");
        changePasswordNewPassword2.setValue("");
    }
    private void addErrorMessage(String message) {
        ResourceBundle resourceBundle =
            ResourceBundle.getBundle("MTS.security.ModelBundle",
                                     FacesContext.getCurrentInstance().getViewRoot().getLocale());
        FacesMessage facesMessage =
            new FacesMessage(FacesMessage.SEVERITY_ERROR, resourceBundle.getString(message),
                             resourceBundle.getString(message));
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    }


    public void onPopupFetch(PopupFetchEvent popupFetchEvent) {
        changePasswordOldpassword.setValue(null);
        changePasswordNewPassword1.setValue(null);
        changePasswordNewPassword2.setValue(null);
    }

    public void setCallUserName(RichInputText callUserName) {
        this.callUserName = callUserName;
    }

    public RichInputText getCallUserName() {
        return callUserName;
    }

    public void setCallPassword(RichInputText callPassword) {
        this.callPassword = callPassword;
    }

    public RichInputText getCallPassword() {
        return callPassword;
    }

    public void setCallExtensionNumber(RichInputText callExtensionNumber) {
        this.callExtensionNumber = callExtensionNumber;
    }

    public RichInputText getCallExtensionNumber() {
        return callExtensionNumber;
    }

    public void setCallQueueNumber(RichInputText callQueueNumber) {
        this.callQueueNumber = callQueueNumber;
    }

    public RichInputText getCallQueueNumber() {
        return callQueueNumber;
    }

    public String SaveCallData() {
        // Add event code here...
        FacesContext faces_context=FacesContext.getCurrentInstance();
        UIViewRoot root = faces_context.getViewRoot();
        RichInputText username_txt=(RichInputText)root.findComponent("it3");
        System.err.println("Test Component"+username_txt);
        System.err.println("User Name : "+username_txt.getValue());
        RichInputText Password_txt=(RichInputText)root.findComponent("it4");
        System.err.println("Password : "+Password_txt.getValue());
        RichInputText ExtensionNumber_txt=(RichInputText)root.findComponent("it5");
        System.err.println("Extension Number"+ExtensionNumber_txt.getValue());
        RichInputText QueueNumber_txt=(RichInputText)root.findComponent("it6");
        System.err.println("Queue Number :"+QueueNumber_txt.getValue());
        return null;
    }

    @SuppressWarnings("unchecked")
    public void ChangeUserName(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        System.err.println("UserName : "+valueChangeEvent.getNewValue().toString());
   
        ADFContext.getCurrent().getSessionScope().put("CallUserName", valueChangeEvent.getNewValue().toString());
   System.err.println("Set Session Scope");
   System.err.println("UserName : "+ADFContext.getCurrent().getSessionScope().get("CallUserName"));
    System.err.println("Get Session Scope");
    }

    @SuppressWarnings("unchecked")
    public void ChangePassword(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        System.err.println("Password : "+valueChangeEvent.getNewValue().toString());
        ADFContext.getCurrent().getSessionScope().put("CallPass", valueChangeEvent.getNewValue().toString());
        
    }

    @SuppressWarnings("unchecked")
    public void ChangeExtensionNumber(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        System.err.println("Extension Number : "+valueChangeEvent.getNewValue().toString());
        ADFContext.getCurrent().getSessionScope().put("ExtensionNum", valueChangeEvent.getNewValue().toString());
    }

    @SuppressWarnings("unchecked")
    public void ChangeQueueNumber(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        System.err.println("Queue Number : "+valueChangeEvent.getNewValue().toString());
        ADFContext.getCurrent().getSessionScope().put("QueueNum", valueChangeEvent.getNewValue().toString());
    }
}
