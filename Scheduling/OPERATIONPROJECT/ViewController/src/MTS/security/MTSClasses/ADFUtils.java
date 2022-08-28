package MTS.security.MTSClasses;


import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import java.sql.CallableStatement;
import java.sql.SQLException;
import oracle.jbo.server.ApplicationModuleImpl;
import javax.faces.context.FacesContext;

import oracle.jbo.JboException;
import MTS.security.MTSClasses.JSFUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.model.SelectItem;

import oracle.adf.model.BindingContext;
import oracle.adf.model.DataControlFrame;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.model.binding.DCParameter;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.RichQuery;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.data.RichTree;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.event.QueryEvent;
import oracle.adf.view.rich.model.QueryDescriptor;
import oracle.adf.view.rich.model.QueryModel;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;
import oracle.binding.ControlBinding;
import oracle.binding.OperationBinding;

import oracle.jbo.ApplicationModule;
import oracle.jbo.AttributeDef;
import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.server.EntityReference;
import oracle.jbo.server.ViewDefImpl;
import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;
import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;
import oracle.jbo.uicli.binding.JUCtrlValueBinding;
import oracle.jbo.uicli.binding.JUEventBinding;

import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;
//import oracle.bpel.services.workflow.client.IWorkflowServiceClient;
//import oracle.bpel.services.workflow.client.WorkflowServiceClientFactory;
//import oracle.bpel.services.workflow.query.ITaskQueryService;
//import oracle.bpel.services.workflow.task.model.Task;
//
//import oracle.bpel.services.workflow.verification.IWorkflowContext;
//import oracle.bpel.services.workflow.WorkflowException;
//
//import oracle.bpel.services.workflow.metadata.ITaskMetadataService;
//import oracle.bpel.services.workflow.metadata.taskdefinition.model.TaskDefinition;


/**
 * A series of convenience functions for dealing with ADF Bindings.
 * Note: Updated for JDeveloper 11
 *
 * @author Duncan Mills
 * @author Steve Muench
 *
 */

public class ADFUtils {
    public static final ADFLogger LOGGER = ADFLogger.createADFLogger(ADFUtils.class);

    /**
     * Get application module for an application module data control by name.
     * @param name application module data control name
     * @return ApplicationModule
     */
    public static ApplicationModule getApplicationModuleForDataControl(String name) {
        return (ApplicationModule) JSFUtils.resolveExpression("#{data." + name + ".dataProvider}");
    }

    /**
     * A convenience method for getting the value of a bound attribute in the
     * current page context programatically.
     * @param attributeName of the bound value in the pageDef
     * @return value of the attribute
     */
    public static Object getBoundAttributeValue(String attributeName) {
        return findControlBinding(attributeName).getInputValue();
    }

    /**
     * A convenience method for setting the value of a bound attribute in the
     * context of the current page.
     * @param attributeName of the bound value in the pageDef
     * @param value to set
     */
    public static void setBoundAttributeValue(String attributeName, Object value) {
        findControlBinding(attributeName).setInputValue(value);
    }

    /**
     * Returns the evaluated value of a pageDef parameter.
     * @param pageDefName reference to the page definition file of the page with the parameter
     * @param parameterName name of the pagedef parameter
     * @return evaluated value of the parameter as a String
     */
    public static Object getPageDefParameterValue(String pageDefName, String parameterName) {
        BindingContainer bindings = findBindingContainer(pageDefName);
        DCParameter param = ((DCBindingContainer) bindings).findParameter(parameterName);
        return param.getValue();
    }

    /**
     * Convenience method to find a DCControlBinding as an AttributeBinding
     * to get able to then call getInputValue() or setInputValue() on it.
     * @param bindingContainer binding container
     * @param attributeName name of the attribute binding.
     * @return the control value binding with the name passed in.
     *
     */
    public static AttributeBinding findControlBinding(BindingContainer bindingContainer, String attributeName) {
        if (attributeName != null) {
            if (bindingContainer != null) {
                ControlBinding ctrlBinding = bindingContainer.getControlBinding(attributeName);
                if (ctrlBinding instanceof AttributeBinding) {
                    return (AttributeBinding) ctrlBinding;
                }
            }
        }
        return null;
    }

    /**
     * Convenience method to find a DCControlBinding as a JUCtrlValueBinding
     * to get able to then call getInputValue() or setInputValue() on it.
     * @param attributeName name of the attribute binding.
     * @return the control value binding with the name passed in.
     *
     */
    public static AttributeBinding findControlBinding(String attributeName) {
        return findControlBinding(getBindingContainer(), attributeName);
    }

    /**
     * Return the current page's binding container.
     * @return the current page's binding container
     */
    public static BindingContainer getBindingContainer() {
        return (BindingContainer) JSFUtils.resolveExpression("#{bindings}");
    }

    /**
     * Return the Binding Container as a DCBindingContainer.
     * @return current binding container as a DCBindingContainer
     */
    public static DCBindingContainer getDCBindingContainer() {
        return (DCBindingContainer) getBindingContainer();
    }

    /**
     * Get List of ADF Faces SelectItem for an iterator binding.
     *
     * Uses the value of the 'valueAttrName' attribute as the key for
     * the SelectItem key.
     *
     * @param iteratorName ADF iterator binding name
     * @param valueAttrName name of the value attribute to use
     * @param displayAttrName name of the attribute from iterator rows to display
     * @return ADF Faces SelectItem for an iterator binding
     */
    public static List<SelectItem> selectItemsForIterator(String iteratorName, String valueAttrName,
                                                          String displayAttrName) {
        return selectItemsForIterator(findIterator(iteratorName), valueAttrName, displayAttrName);
    }

    /**
     * Get List of ADF Faces SelectItem for an iterator binding with description.
     *
     * Uses the value of the 'valueAttrName' attribute as the key for
     * the SelectItem key.
     *
     * @param iteratorName ADF iterator binding name
     * @param valueAttrName name of the value attribute to use
     * @param displayAttrName name of the attribute from iterator rows to display
     * @param descriptionAttrName name of the attribute to use for description
     * @return ADF Faces SelectItem for an iterator binding with description
     */
    public static List<SelectItem> selectItemsForIterator(String iteratorName, String valueAttrName,
                                                          String displayAttrName, String descriptionAttrName) {
        return selectItemsForIterator(findIterator(iteratorName), valueAttrName, displayAttrName, descriptionAttrName);
    }

    /**
     * Get List of attribute values for an iterator.
     * @param iteratorName ADF iterator binding name
     * @param valueAttrName value attribute to use
     * @return List of attribute values for an iterator
     */
    public static List attributeListForIterator(String iteratorName, String valueAttrName) {
        return attributeListForIterator(findIterator(iteratorName), valueAttrName);
    }

    /**
     * Get List of Key objects for rows in an iterator.
     * @param iteratorName iterabot binding name
     * @return List of Key objects for rows
     */
    public static List<Key> keyListForIterator(String iteratorName) {
        return keyListForIterator(findIterator(iteratorName));
    }

    /**
     * Get List of Key objects for rows in an iterator.
     * @param iter iterator binding
     * @return List of Key objects for rows
     */
    public static List<Key> keyListForIterator(DCIteratorBinding iter) {
        List<Key> attributeList = new ArrayList<Key>();
        for (Row r : iter.getAllRowsInRange()) {
            attributeList.add(r.getKey());
        }
        return attributeList;
    }

    /**
     * Get List of Key objects for rows in an iterator using key attribute.
     * @param iteratorName iterator binding name
     * @param keyAttrName name of key attribute to use
     * @return List of Key objects for rows
     */
    public static List<Key> keyAttrListForIterator(String iteratorName, String keyAttrName) {
        return keyAttrListForIterator(findIterator(iteratorName), keyAttrName);
    }

    /**
     * Get List of Key objects for rows in an iterator using key attribute.
     *
     * @param iter iterator binding
     * @param keyAttrName name of key attribute to use
     * @return List of Key objects for rows
     */
    public static List<Key> keyAttrListForIterator(DCIteratorBinding iter, String keyAttrName) {
        List<Key> attributeList = new ArrayList<Key>();
        for (Row r : iter.getAllRowsInRange()) {
            attributeList.add(new Key(new Object[] { r.getAttribute(keyAttrName) }));
        }
        return attributeList;
    }

    /**
     * Get a List of attribute values for an iterator.
     *
     * @param iter iterator binding
     * @param valueAttrName name of value attribute to use
     * @return List of attribute values
     */
    public static List attributeListForIterator(DCIteratorBinding iter, String valueAttrName) {
        List attributeList = new ArrayList();
        for (Row r : iter.getAllRowsInRange()) {
            attributeList.add(r.getAttribute(valueAttrName));
        }
        return attributeList;
    }

    /**
     * Find an iterator binding in the current binding container by name.
     *
     * @param name iterator binding name
     * @return iterator binding
     */
    public static DCIteratorBinding findIterator(String name) {
        DCIteratorBinding iter = getDCBindingContainer().findIteratorBinding(name);
        if (iter == null) {
            throw new RuntimeException("Iterator '" + name + "' not found");
        }
        return iter;
    }

    public static DCIteratorBinding findIterator(String bindingContainer, String iterator) {
        DCBindingContainer bindings = (DCBindingContainer) JSFUtils.resolveExpression("#{" + bindingContainer + "}");
        if (bindings == null) {
            throw new RuntimeException("Binding container '" + bindingContainer + "' not found");
        }
        DCIteratorBinding iter = bindings.findIteratorBinding(iterator);
        if (iter == null) {
            throw new RuntimeException("Iterator '" + iterator + "' not found");
        }
        return iter;
    }

    public static JUCtrlValueBinding findCtrlBinding(String name) {
        JUCtrlValueBinding rowBinding = (JUCtrlValueBinding) getDCBindingContainer().findCtrlBinding(name);
        if (rowBinding == null) {
            throw new RuntimeException("CtrlBinding " + name + "' not found");
        }
        return rowBinding;
    }

    /**
     * Find an operation binding in the current binding container by name.
     *
     * @param name operation binding name
     * @return operation binding
     */
    public static OperationBinding findOperation(String name) {
        OperationBinding op = getDCBindingContainer().getOperationBinding(name);
        if (op == null) {
            throw new RuntimeException("Operation '" + name + "' not found");
        }
        return op;
    }

    /**
     * Find an operation binding in the current binding container by name.
     *
     * @param bindingContianer binding container name
     * @param opName operation binding name
     * @return operation binding
     */
    public static OperationBinding findOperation(String bindingContianer, String opName) {
        DCBindingContainer bindings = (DCBindingContainer) JSFUtils.resolveExpression("#{" + bindingContianer + "}");
        if (bindings == null) {
            throw new RuntimeException("Binding container '" + bindingContianer + "' not found");
        }
        OperationBinding op = bindings.getOperationBinding(opName);
        if (op == null) {
            throw new RuntimeException("Operation '" + opName + "' not found");
        }
        return op;
    }

    /**
     * Get List of ADF Faces SelectItem for an iterator binding with description.
     *
     * Uses the value of the 'valueAttrName' attribute as the key for
     * the SelectItem key.
     *
     * @param iter ADF iterator binding
     * @param valueAttrName name of value attribute to use for key
     * @param displayAttrName name of the attribute from iterator rows to display
     * @param descriptionAttrName name of the attribute for description
     * @return ADF Faces SelectItem for an iterator binding with description
     */
    public static List<SelectItem> selectItemsForIterator(DCIteratorBinding iter, String valueAttrName,
                                                          String displayAttrName, String descriptionAttrName) {
        List<SelectItem> selectItems = new ArrayList<SelectItem>();
        for (Row r : iter.getAllRowsInRange()) {
            selectItems.add(new SelectItem(r.getAttribute(valueAttrName), (String) r.getAttribute(displayAttrName),
                                           (String) r.getAttribute(descriptionAttrName)));
        }
        return selectItems;
    }

    /**
     * Get List of ADF Faces SelectItem for an iterator binding.
     *
     * Uses the value of the 'valueAttrName' attribute as the key for
     * the SelectItem key.
     *
     * @param iter ADF iterator binding
     * @param valueAttrName name of value attribute to use for key
     * @param displayAttrName name of the attribute from iterator rows to display
     * @return ADF Faces SelectItem for an iterator binding
     */
    public static List<SelectItem> selectItemsForIterator(DCIteratorBinding iter, String valueAttrName,
                                                          String displayAttrName) {
        List<SelectItem> selectItems = new ArrayList<SelectItem>();
        for (Row r : iter.getAllRowsInRange()) {
            selectItems.add(new SelectItem(r.getAttribute(valueAttrName), (String) r.getAttribute(displayAttrName)));
        }
        return selectItems;
    }

    /**
     * Get List of ADF Faces SelectItem for an iterator binding.
     *
     * Uses the rowKey of each row as the SelectItem key.
     *
     * @param iteratorName ADF iterator binding name
     * @param displayAttrName name of the attribute from iterator rows to display
     * @return ADF Faces SelectItem for an iterator binding
     */
    public static List<SelectItem> selectItemsByKeyForIterator(String iteratorName, String displayAttrName) {
        return selectItemsByKeyForIterator(findIterator(iteratorName), displayAttrName);
    }

    /**
     * Get List of ADF Faces SelectItem for an iterator binding with discription.
     *
     * Uses the rowKey of each row as the SelectItem key.
     *
     * @param iteratorName ADF iterator binding name
     * @param displayAttrName name of the attribute from iterator rows to display
     * @param descriptionAttrName name of the attribute for description
     * @return ADF Faces SelectItem for an iterator binding with discription
     */
    public static List<SelectItem> selectItemsByKeyForIterator(String iteratorName, String displayAttrName,
                                                               String descriptionAttrName) {
        return selectItemsByKeyForIterator(findIterator(iteratorName), displayAttrName, descriptionAttrName);
    }

    /**
     * Get List of ADF Faces SelectItem for an iterator binding with discription.
     *
     * Uses the rowKey of each row as the SelectItem key.
     *
     * @param iter ADF iterator binding
     * @param displayAttrName name of the attribute from iterator rows to display
     * @param descriptionAttrName name of the attribute for description
     * @return ADF Faces SelectItem for an iterator binding with discription
     */
    public static List<SelectItem> selectItemsByKeyForIterator(DCIteratorBinding iter, String displayAttrName,
                                                               String descriptionAttrName) {
        List<SelectItem> selectItems = new ArrayList<SelectItem>();
        for (Row r : iter.getAllRowsInRange()) {
            selectItems.add(new SelectItem(r.getKey(), (String) r.getAttribute(displayAttrName),
                                           (String) r.getAttribute(descriptionAttrName)));
        }
        return selectItems;
    }

    /**
     * Get List of ADF Faces SelectItem for an iterator binding.
     *
     * Uses the rowKey of each row as the SelectItem key.
     *
     * @param iter ADF iterator binding
     * @param displayAttrName name of the attribute from iterator rows to display
     * @return List of ADF Faces SelectItem for an iterator binding
     */
    public static List<SelectItem> selectItemsByKeyForIterator(DCIteratorBinding iter, String displayAttrName) {
        List<SelectItem> selectItems = new ArrayList<SelectItem>();
        for (Row r : iter.getAllRowsInRange()) {
            selectItems.add(new SelectItem(r.getKey(), (String) r.getAttribute(displayAttrName)));
        }
        return selectItems;
    }

    /**
     * Find the BindingContainer for a page definition by name.
     *
     * Typically used to refer eagerly to page definition parameters. It is
     * not best practice to reference or set bindings in binding containers
     * that are not the one for the current page.
     *
     * @param pageDefName name of the page defintion XML file to use
     * @return BindingContainer ref for the named definition
     */
    private static BindingContainer findBindingContainer(String pageDefName) {
        BindingContext bctx = getDCBindingContainer().getBindingContext();
        BindingContainer foundContainer = bctx.findBindingContainer(pageDefName);
        return foundContainer;
    }

    public static void printOperationBindingExceptions(List opList) {
        if (opList != null && !opList.isEmpty()) {
            for (Object error : opList) {
                LOGGER.severe(error.toString());
            }
        }
    }

    /**
     * When a bounded task flow manages a transaction (marked as requires-transaction,.
     * requires-new-transaction, or requires-existing-transaction), then the
     * task flow must issue any commits or rollbacks that are needed. This is
     * essentially to keep the state of the transaction that the task flow understands
     * in synch with the state of the transaction in the ADFbc layer.
     *
     * Use this method to issue a commit in the middle of a task flow while staying
     * in the task flow.
     */
    public static void saveAndContinue() {
        Map sessionMap = FacesContext.getCurrentInstance()
                                     .getExternalContext()
                                     .getSessionMap();
        BindingContext context = (BindingContext) sessionMap.get(BindingContext.CONTEXT_ID);
        String currentFrameName = context.getCurrentDataControlFrame();
        DataControlFrame dcFrame = context.findDataControlFrame(currentFrameName);

        dcFrame.commit();
        dcFrame.beginTransaction(null);
    }

    /**
     * Programmatic evaluation of EL.
     *
     * @param el EL to evaluate
     * @return Result of the evaluation
     */
    public static Object evaluateEL(String el) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELContext elContext = facesContext.getELContext();
        ExpressionFactory expressionFactory = facesContext.getApplication().getExpressionFactory();
        ValueExpression exp = expressionFactory.createValueExpression(elContext, el, Object.class);

        return exp.getValue(elContext);
    }

    /**
     * Programmatic invocation of a method that an EL evaluates to.
     * The method must not take any parameters.
     *
     * @param el EL of the method to invoke
     * @return Object that the method returns
     */
    public static Object invokeEL(String el) {
        return invokeEL(el, new Class[0], new Object[0]);
    }

    /**
     * Programmatic invocation of a method that an EL evaluates to.
     *
     * @param el EL of the method to invoke
     * @param paramTypes Array of Class defining the types of the parameters
     * @param params Array of Object defining the values of the parametrs
     * @return Object that the method returns
     */
    public static Object invokeEL(String el, Class[] paramTypes, Object[] params) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELContext elContext = facesContext.getELContext();
        ExpressionFactory expressionFactory = facesContext.getApplication().getExpressionFactory();
        MethodExpression exp = expressionFactory.createMethodExpression(elContext, el, Object.class, paramTypes);

        return exp.invoke(elContext, params);
    }

    /**
     * Sets a value into an EL object. Provides similar functionality to
     * the <af:setActionListener> tag, except the from is
     * not an EL. You can get similar behavior by using the following...

     * setEL(to, evaluateEL(from))
     *
     * @param el EL object to assign a value
     * @param val Value to assign
     */
    public static void setEL(String el, Object val) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELContext elContext = facesContext.getELContext();
        ExpressionFactory expressionFactory = facesContext.getApplication().getExpressionFactory();
        ValueExpression exp = expressionFactory.createValueExpression(elContext, el, Object.class);

        exp.setValue(elContext, val);
    }


    public static OperationBinding getOperationBindingForMethod(String pageDefMethod) {
        OperationBinding opBinding;

        opBinding = getBindingContainer().getOperationBinding(pageDefMethod);

        return opBinding;
    }

    /*   public static Task getWorkFlowTaskForRemoteClient(String bpmWorkListContextId, String bpmWorkListTaskId)
    {
        try {
            IWorkflowServiceClient wfServiceClient =
                WorkflowServiceClientFactory.getWorkflowServiceClient(WorkflowServiceClientFactory.REMOTE_CLIENT);

            ITaskQueryService taskQueryService =
                wfServiceClient.getTaskQueryService();

            IWorkflowContext wfContext = null;

            wfContext =
                    taskQueryService.getWorkflowContext(bpmWorkListContextId);

            Task humanTask = taskQueryService.getTaskDetailsById(wfContext, bpmWorkListTaskId);

            return humanTask;

        } catch (WorkflowException we) {
            // TODO: Add catch code
            we.printStackTrace();
            return null;
        }
    }

    public static TaskDefinition getWorkFlowTaskDefinitionForRemoteClient(String bpmWorkListContextId, String bpmWorkListTaskId)
    {
        try {
            IWorkflowServiceClient wfServiceClient =
                WorkflowServiceClientFactory.getWorkflowServiceClient(WorkflowServiceClientFactory.REMOTE_CLIENT);

            ITaskQueryService taskQueryService =
                wfServiceClient.getTaskQueryService();

            ITaskMetadataService taskMetadataService = wfServiceClient.getTaskMetadataService();
                ;

            IWorkflowContext wfContext = null;

            wfContext =
                    taskQueryService.getWorkflowContext(bpmWorkListContextId);

            Task humanTask = taskQueryService.getTaskDetailsById(wfContext, bpmWorkListTaskId);

            TaskDefinition humanTaskDef = taskMetadataService.getTaskDefinition(wfContext, humanTask);

            return humanTaskDef;

        } catch (WorkflowException we) {
            // TODO: Add catch code
            we.printStackTrace();
            return null;
        }
    }
 */

    public static void openURLInWindow(String url) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        System.out.println("########## Url :-" + url);
        url = url.replaceAll("\\\\", "\\\\\\\\");
        String javaScriptText = null;
        if (url.contains("http")) {
            javaScriptText = "window.open('" + url + "', 'popupWindow', 'dependent=yes, menubar=no, toolbar=no');";
        } else {
            JSFUtils.showFacesErrorMessage("Cannot open URL :" + url);
        }

        ExtendedRenderKitService service = Service.getRenderKitService(facesContext, ExtendedRenderKitService.class);
        service.addScript(facesContext, javaScriptText);
    }

    public static void executeClientJavascript(String script) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExtendedRenderKitService service = Service.getRenderKitService(facesContext, ExtendedRenderKitService.class);
        service.addScript(facesContext, script);
    }

    public static void invokePopup(String popupId) {
        invokePopup(popupId, null, null);
    }


    /**
     * Hides or shows the popup component depending on the value of the
     * boolean parameter.
     * @param popUpComponent rich component popup object
     * @param showPopup true = show , false = hide
     */
    public static void invokePopup(RichPopup popUpComponent, boolean showPopup) {
        String popupId = popUpComponent.getClientId(FacesContext.getCurrentInstance());
        if (showPopup)
            invokePopup(popupId);
        else
            hidePopup(popupId);

    }

    /**
     * Shows the specified popup and uses the specified hints to align the popup.
     * @param popupId is the clientId of the popup to be shown - clientId is derived from backing bean for the af:popup using getClientId method
     * @param align is a hint for the popup display. Check AdfRichPopup js javadoc for valid values. Supported value includes: "AdfRichPopup.ALIGN_START_AFTER", "AdfRichPopup.ALIGN_BEFORE_START" and "AdfRichPopup.ALIGN_END_BEFORE"
     * @param alignId is the clientId of the component the popup should align to - clientId is derived from backing bean for the component using getClientId method
     * align and alignId need to be specified together - specifying null for either of them will have no effect.
     */
    public static void invokePopup(String popupId, String align, String alignId) {
        if (popupId != null) {
            ExtendedRenderKitService service =
                Service.getRenderKitService(FacesContext.getCurrentInstance(), ExtendedRenderKitService.class);

            StringBuffer showPopup = new StringBuffer();
            showPopup.append("var hints = new Object();");
            //Add hints only if specified - see javadoc for AdfRichPopup js for details on valid values and behavior
            if (align != null && alignId != null) {
                showPopup.append("hints[AdfRichPopup.HINT_ALIGN] = " + align + ";");
                showPopup.append("hints[AdfRichPopup.HINT_ALIGN_ID] ='" + alignId + "';");
            }
            showPopup.append("var popupObj=AdfPage.PAGE.findComponent('" + popupId +
                             "'); if (popupObj) popupObj.show(hints);");
            service.addScript(FacesContext.getCurrentInstance(), showPopup.toString());
        }
    }

    /**
     * Hides the specified popup.
     * @param popupId is the clientId of the popup to be hidden
     * clientId is derived from backing bean for the af:popup using getClientId method
     */
    public static void hidePopup(String popupId) {
        if (popupId != null) {
            ExtendedRenderKitService service =
                Service.getRenderKitService(FacesContext.getCurrentInstance(), ExtendedRenderKitService.class);

            String hidePopup =
                "var popupObj=AdfPage.PAGE.findComponent('" + popupId + "'); if (popupObj) popupObj.hide();";
            service.addScript(FacesContext.getCurrentInstance(), hidePopup);
        }
    }

    public static String join(List<? extends CharSequence> s, String delimiter) {
        int capacity = 0;
        int delimLength = delimiter.length();
        Iterator<? extends CharSequence> iter = s.iterator();
        if (iter.hasNext()) {
            capacity += iter.next().length() + delimLength;
        }

        StringBuilder buffer = new StringBuilder(capacity);
        iter = s.iterator();
        if (iter.hasNext()) {
            buffer.append(iter.next());
            while (iter.hasNext()) {
                buffer.append(delimiter);
                buffer.append(iter.next());
            }
        }
        return buffer.toString();
    }

    public static void raiseContextualEvent(String eventBindingOpExpr, ActionEvent actionEvent,
                                            Map<String, Object> payload) {

        if (eventBindingOpExpr == null || actionEvent == null) {
            JSFUtils.showFacesErrorMessage("Event Binding Operation Expression is NULL. Action Event is NULL");
            return;
        } else {
            UIComponent pubUiComp = actionEvent.getComponent();
            Map<String, Object> attributes = pubUiComp.getAttributes();

            if (payload != null) {

                Set set = payload.entrySet();

                Iterator it = set.iterator();
                String key;
                Object value;

                while (it.hasNext()) {
                    Map.Entry m = (Map.Entry) it.next();
                    key = (String) m.getKey();
                    value = m.getValue();

                    attributes.put(key, value);

                }
            }

            JUEventBinding eventBinding = (JUEventBinding) getBindingContainer().get(eventBindingOpExpr);

            if (eventBinding == null) {
                JSFUtils.showFacesErrorMessage("Event Binding is Null");
                return;
            } else {
                ActionListener actionListener = (ActionListener) eventBinding.getListener();
                actionListener.processAction(actionEvent);
            }

        }
    }


    public static RowIterator getSelectedNodeRowIterator(RichTree tree) {
        if (tree != null && tree.getSelectedRowKeys() != null) {
            for (Object opaqueFacesKey : tree.getSelectedRowKeys()) {
                tree.setRowKey(opaqueFacesKey);
                return ((JUCtrlHierNodeBinding) tree.getRowData()).getRowIterator();
            }
        }
        return null;
    }

    public static boolean isSelectedNodeInRowIteratorFirst(RichTree tree) {
        RowIterator ri = getSelectedNodeRowIterator(tree);
        if (ri != null) {
            Key selectedNodeRowKey = getSelectedNodeRowKey(tree);
            if (selectedNodeRowKey != null) {
                return selectedNodeRowKey.equals(ri.first().getKey());
            }
        }
        return false;
    }

    public static boolean isSelectedNodeInRowIteratorLast(RichTree tree) {
        RowIterator ri = getSelectedNodeRowIterator(tree);
        if (ri != null) {
            Key selectedNodeRowKey = getSelectedNodeRowKey(tree);
            if (selectedNodeRowKey != null) {
                return selectedNodeRowKey.equals(ri.last().getKey());
            }
        }
        return false;
    }

    public static Key getSelectedNodeRowKey(RichTree tree) {
        if (tree != null && tree.getSelectedRowKeys() != null) {
            for (Object opaqueFacesKey : tree.getSelectedRowKeys()) {
                tree.setRowKey(opaqueFacesKey);
                return ((JUCtrlHierNodeBinding) tree.getRowData()).getRowKey();
            }
        }
        return null;
    }

    public static String getSelectedNodeViewDefFullName(RichTree tree) {
        if (tree != null && tree.getSelectedRowKeys() != null) {
            for (Object opaqueFacesKey : tree.getSelectedRowKeys()) {
                tree.setRowKey(opaqueFacesKey);
                return ((JUCtrlHierNodeBinding) tree.getRowData()).getViewObject().getDefFullName();
            }
        }
        return null;
    }

    public static void makeCurrentInTree(RichTree tree, String treeSeleEL, RowKeySet rowKeySet) {
        //To make a row current, we need to create a SelectionEvent which
        //expects the following arguments: component, unselected_keys,
        //selected_keys. In our example, we don't have unselected keys and
        //therefore create an empty RowSet for this
        SelectionEvent selectionEvent = new SelectionEvent(tree, new RowKeySetImpl(), rowKeySet);
        //Calling the helper method introduced for this chapter. The
        //"LocationsView1" reference is the tree binding definition
        //our example tree has in the ADF PagDef file.
        invokeEL(treeSeleEL, new Class[] { SelectionEvent.class }, new Object[] { selectionEvent });

    }

    /**
     * author ms
     * @param object
     * @return
     */
    public static String nullStringToSpace(Object object) {
        String spcStr = "";
        if (object == null) {
            return spcStr;
        } else {
            return object.toString();
        }
    }

    /**
     * author ms
     * @param queryComponent
     */
    public static void resetQueryPanel(RichQuery queryComponent) {
        QueryModel queryModel = queryComponent.getModel();
        QueryDescriptor queryDescriptor = queryComponent.getValue();
        queryModel.reset(queryDescriptor);
        queryComponent.refresh(FacesContext.getCurrentInstance());
    }

    /**
     * @param selectionEvent
     */
    public static void makeCurrentRowInTable(SelectionEvent selectionEvent) {
        RichTable table = (RichTable) selectionEvent.getSource();
        //CollectionModel tableModel = ModelUtils.toCollectionModel(table.getValue());
        //JUCtrlHierBinding adfTableBinding = (JUCtrlHierBinding) tableModel.getWrappedData();

        JUCtrlHierBinding adfTableBinding = null;
        adfTableBinding = (JUCtrlHierBinding) ((CollectionModel) table.getValue()).getWrappedData();
        DCIteratorBinding tableIteratorBinding = adfTableBinding.getDCIteratorBinding();
        Object selectedRowData = table.getSelectedRowData();
        JUCtrlHierNodeBinding nodeBinding = (JUCtrlHierNodeBinding) selectedRowData;
        Key rwKey = nodeBinding.getRowKey();
        tableIteratorBinding.setCurrentRowWithKey(rwKey.toStringFormat(true));
    }


    public static void createTableNewRow(String iterator) {
        DCIteratorBinding dciter = ADFUtils.findIterator(iterator);

        Row lastRow = dciter.getNavigatableRowIterator().last();
        Row newRow = dciter.getNavigatableRowIterator().createRow();

        newRow.setNewRowState(Row.STATUS_INITIALIZED);

        int lastRowIndex = dciter.getNavigatableRowIterator().getRangeIndexOf(lastRow);

        dciter.getNavigatableRowIterator().insertRowAtRangeIndex(lastRowIndex + 1, newRow);
        dciter.setCurrentRowWithKey(newRow.getKey().toStringFormat(true));
    }

    public static void deleteCurrentRow(String iter) {
        Row row = ADFUtils.findIterator(iter).getCurrentRow();
        row.remove();
    }

    public static void resetCriteria(String iter) {
        ViewObjectImpl voImp = (ViewObjectImpl) ADFUtils.findIterator(iter).getViewObject();
        voImp.executeEmptyRowSet();

    }

    public static ViewObject getChildViewObjectFromParent(ViewObject parentVO, String childAccessorName) {
        AttributeDef attrDef = parentVO.findAttributeDef(childAccessorName);
        if (attrDef == null) {
            throw new RuntimeException("Attribute Definition '" + childAccessorName +
                                       "' not found on parent view object");
        }
        return attrDef.getAccessorVO(parentVO);
    }

    public static void resetRowData(String iter) {
        Row currentRow = ADFUtils.findIterator(iter).getCurrentRow();
        currentRow.refresh(Row.REFRESH_UNDO_CHANGES | Row.REFRESH_WITH_DB_FORGET_CHANGES | Row.REFRESH_FORGET_NEW_ROWS |
                           Row.REFRESH_REMOVE_NEW_ROWS | Row.REFRESH_CONTAINEES);
    }

    public static void resetRowValues(UIComponent component) {
        List<UIComponent> children = component.getChildren();
        for (int i = 0; i < children.size(); i++) {
            UIComponent iComponent = children.get(i);
            List<UIComponent> children_2 = iComponent.getChildren();
            if (children_2 != null && !(children_2.isEmpty())) {
                resetRowValues((UIComponent) children_2);
            }
            System.out.println(iComponent.toString().substring(0, iComponent.toString().indexOf("[")));
            if (iComponent.toString()
                          .substring(0, iComponent.toString().indexOf("["))
                          .equals("RichInputText")) {
                RichInputText input = (RichInputText) iComponent;
                input.resetValue();
            }
            System.err.println(iComponent);
        }
    }

    public static void launchWindow(String taskflowURL, boolean inTab) {
        ExtendedRenderKitService erks =
            Service.getRenderKitService(FacesContext.getCurrentInstance(), ExtendedRenderKitService.class);

        StringBuilder script = new StringBuilder();
        if (inTab) {
            script.append("window.open(\"" + taskflowURL + "\");");
        } else {
            script.append("window.open(\"" + taskflowURL + "\"")
                .append(",\"Task Details\",\"location=0,status=0,scrollbars=1\");");
        }
        erks.addScript(FacesContext.getCurrentInstance(), script.toString());
    }

    public static void launchNewPage() {
        String newPage = "http://www.google.com";
        FacesContext ctx = FacesContext.getCurrentInstance();
        ExtendedRenderKitService erks = Service.getService(ctx.getRenderKit(), ExtendedRenderKitService.class);
        String url = "window.open('" + newPage + "','_self');";
        erks.addScript(FacesContext.getCurrentInstance(), url);
    }

    
    public static Object callStoredFunction(ApplicationModuleImpl appModule, int sqlReturnType, String stmt,
                                               Object[] bindVars) {
           CallableStatement cst = null;
           try {
               //Creating sql statement
               cst = appModule.getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
               //Register dataType for return value
               cst.registerOutParameter(1, sqlReturnType);
               //Pass input parameters value
               if (bindVars != null) {
                   for (int z = 0; z < bindVars.length; z++) {
                       cst.setObject(z + 2, bindVars[z]);
                   }
               }
               cst.executeUpdate();
               //Finally get returned value
               return cst.getObject(1);
           } catch (SQLException e) {
               throw new JboException(e.getMessage());
           } finally {
               if (cst != null) {
                   try {
                       cst.close();
                   } catch (SQLException e) {
                       e.printStackTrace();
                   }
               }
           }
       }
}
