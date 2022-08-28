package MTS.security.MTSClasses;


import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;


import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import java.util.Locale;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.controller.ControllerContext;
import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.fragment.RichPageTemplate;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.nav.RichCommandMenuItem;

import oracle.jbo.NavigationEvent;
import oracle.jbo.Row;
import oracle.jbo.ViewObject;
import oracle.jbo.client.Configuration;

import oracle.jheadstart.controller.jsf.bean.LocaleManager;
import oracle.jheadstart.controller.jsf.bean.LookAndFeelBean;
import oracle.jheadstart.controller.jsf.util.JsfUtils;

import org.apache.commons.lang.LocaleUtils;


public class MtsMenusLists {

    private RichPageTemplate pt1;

    public static void main(String[] args) {
        MtsMenusLists testClass = new MtsMenusLists();

    }

    public List getSubsystemsList() {

        MTSPermission p = MTSPermission.getInstance();
        if(p.getSubsystems().isEmpty())
            p = new MTSPermission();
        return p.getSubsystems();
    }

    public List getModulesList() {

        MTSPermission p = MTSPermission.getInstance();
        if(p.getModules().isEmpty())
            p = new MTSPermission();
        String Lang =
            LocaleManager.getInstance().getCurrentLocale().toString() == null ? "ar" :
            LocaleManager.getInstance().getCurrentLocale().toString();
        if (Lang.equalsIgnoreCase("en"))
            return p.getModules();
        else
            return p.getModules_ar();
    }

    public void goModule(ActionEvent actionEvent) {
        String moduleName = ((RichCommandMenuItem) actionEvent.getComponent()).getText();
        //  MTSPermission p = new MTSPermission();
        MTSPermission p = MTSPermission.getInstance();
        if(p.getModules().isEmpty())
            p = new MTSPermission();
        String url = p.getModulePath(moduleName);
        if (url != null) {
            FacesContext fCtx = FacesContext.getCurrentInstance();
            ExternalContext eCtx = fCtx.getExternalContext();
            try {
                eCtx.redirect(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else
            addMessage("Module not avaliable on server, please contact your administrator.",
                       FacesMessage.SEVERITY_ERROR);
    }

    private void addMessage(String message, FacesMessage.Severity severity) {
        FacesMessage facesMessage = new FacesMessage(severity, message, message);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    }

/*    public void goNotificationModule(ActionEvent actionEvent) {
        //  MTSPermission p = new MTSPermission();
        MTSPermission p = MTSPermission.getInstance();
        String url = p.getModulePath("Control Messages");
        FacesContext fCtx = FacesContext.getCurrentInstance();
        ExternalContext eCtx = fCtx.getExternalContext();
        try {
            eCtx.redirect(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/


    @SuppressWarnings("unchecked")
    public void fontSizeChangeLst(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        System.out.println("value : " + valueChangeEvent.getNewValue());
        ADFContext.getCurrent().getSessionScope().put("fontSize", valueChangeEvent.getNewValue());
    }


    /*    public void applyUserPreferencies(String UserName) {

        try {
            //   ADFContext.getCurrent().getSessionScope().put("isInitiated","True");
            String Template = "/common/pageTemplates/JhsPageTemplate.jspx";
            Object fontsize = "smallFont";
            String currentRegionTemp = "/common/pageTemplates/JhsRegionTemplate.jspx";
            String Language = "en";
            String Skin = "skyros_smallFont.desktop";

            //     String UserName =ADFContext.getCurrent().getSecurityContext().getUserName();
            SecurityAppModule am =
                (SecurityAppModule) Configuration.createRootApplicationModule("MTS.security.SecurityAppModule",
                                                                              "SecurityAppModuleLocal");
            ViewObject userPrefVo = am.findViewObject("ScUsersPreferencesView1");
            userPrefVo.executeQuery();
            if (userPrefVo.getFilteredRows("UserName", UserName).length > 0) {

                Row cur = userPrefVo.getFilteredRows("UserName", UserName)[0];
                Template = cur.getAttribute("PageTemplate").toString();
                fontsize = cur.getAttribute("FontSize") == null ? "SmallFont" : cur.getAttribute("FontSize");
                Language = cur.getAttribute("Language").toString();
                Skin = cur.getAttribute("SkinType").toString();

                //  LocaleUtils.toLocale(Language.toString());
                // System.out.println(locMan.getCurrentLocale());
                //System.out.println(skinValue.getValue());
                //System.out.println(templateValue.getValue());

                //                      Configuration.releaseRootApplicationModule(am, true);

            }
            //ADFContext.getCurrent().getSessionScope().put("isInitiated","True");
            Configuration.releaseRootApplicationModule(am, true);


            //     String currentPageTemplate = lkFe.getCurrentPageTemplate();
            //   System.out.println(currentPageTemplate);
            //    MTSLookAndFeelBean   lk = new MTSLookAndFeelBean();

            MTSLookAndFeelBean.getInstance().setCurrentPageTemplate(Template);
            MTSLookAndFeelBean.getInstance().setCurrentRegionTemplate(currentRegionTemp);
            //  lk.setCurrentPageTemplate(Template);
            //   templateValue.setValue(Template);
            // setCurrentPageTemplate(Template); //currentPageTemplate
            //  currentRegionTemplate =currentRegionTemp;
            //  setCurrentRegionTemplate(currentRegionTemplate);
            //  System.out.println(ADFContext.getCurrent().getSessionScope().get("fontSize"));
            //   System.out.println(lkFe.getCurrentSkin());
            MTSLookAndFeelBean.getInstance().setCurrentSkin(Skin);
            //           skinValue.setValue(Skin);
            //         fontSize.setValue(fontsize);

            //  setCurrentSkin(Skin);
            ADFContext.getCurrent().getSessionScope().put("fontSize", fontsize);
            //    System.out.println(locMan.getCurrentLocale());
            //  System.out.println(localValue.getValue());
            Language =
                (Language.equalsIgnoreCase("English") ? "en" : (Language.equalsIgnoreCase("Arabic") ? "ar" : Language));
            LocaleManager.getInstance().setCurrentLocale(LocaleUtils.toLocale(Language));
            ADFContext.getCurrent().getSessionScope().put("Language", Language);
            //  locMan.setCurrentLocale(LocaleUtils.toLocale(Language));
            //             localValue.setValue(Language);

            //  JsfUtils.redirectToSelf();
        } catch (Exception e) {
            e.printStackTrace();

        }


        // JsfUtils.redirectToSelf();
    }
*/
/*
    public void applyUserChanges(Object Template, Object fontSize, Object Language, Object Skin) {
        String UserName = ADFContext.getCurrent().getSecurityContext().getUserName();
        SecurityAppModule am =
            (SecurityAppModule) Configuration.createRootApplicationModule("MTS.security.SecurityAppModule",
                                                                          "SecurityAppModuleLocal");
        ViewObject userPrefVo = am.findViewObject("ScUsersPreferencesView1");
        userPrefVo.executeQuery();
        if (userPrefVo.getFilteredRows("UserName", UserName).length > 0) {

            Row cur = userPrefVo.getFilteredRows("UserName", UserName)[0];
            cur.setAttribute("PageTemplate", Template);
            cur.setAttribute("FontSize", fontSize);
            cur.setAttribute("Language", Language);
            cur.setAttribute("SkinType", Skin);
        } else {

            Row cur = userPrefVo.createRow();
            cur.setAttribute("UserName", UserName);
            cur.setAttribute("PageTemplate", Template);
            cur.setAttribute("FontSize", fontSize);
            cur.setAttribute("Language", Language);
            cur.setAttribute("SkinType", Skin);
            userPrefVo.insertRow(cur);

        }
        am.getTransaction().commit();
        Configuration.releaseRootApplicationModule(am, true);


    }
*/
    //    protected void refreshPage() {
    //
    //        FacesContext fctx = FacesContext.getCurrentInstance();
    //        String page = fctx.getViewRoot().getViewId();
    //        ViewHandler ViewH = fctx.getApplication().getViewHandler();
    //        UIViewRoot UIV = ViewH.createView(fctx, page);
    //        UIV.setViewId(page);
    //        fctx.setViewRoot(UIV);
    //
    //    }
    public void setPt1(RichPageTemplate pt1) {
        this.pt1 = pt1;
    }

    public RichPageTemplate getPt1() {
        return pt1;
    }
}
