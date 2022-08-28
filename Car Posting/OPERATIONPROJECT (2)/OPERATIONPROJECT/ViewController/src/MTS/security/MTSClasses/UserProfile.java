package MTS.security.MTSClasses;

import MTS.adf12c.Popups;
import MTS.adf12c.msg.BundleMessageMgr;

import MTS.security.MTSClasses.AdministrationBean;
import MTS.security.MTSClasses.WLSJmxInterface;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.event.PopupFetchEvent;

import oracle.jheadstart.controller.jsf.bean.LoginBean;

import java.util.ResourceBundle;

public class UserProfile {
    private RichInputText changePasswordOldpassword;
    private RichInputText changePasswordNewPassword1;
    private RichInputText changePasswordNewPassword2;

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
}
