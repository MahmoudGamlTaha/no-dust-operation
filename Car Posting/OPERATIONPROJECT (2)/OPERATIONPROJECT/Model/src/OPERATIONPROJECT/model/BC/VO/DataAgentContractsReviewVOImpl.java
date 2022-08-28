package OPERATIONPROJECT.model.BC.VO;

import oracle.adf.share.ADFContext;

import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.server.ViewRowSetImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Jan 04 09:51:25 EET 2021
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class DataAgentContractsReviewVOImpl extends ViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public DataAgentContractsReviewVOImpl() {
    }

    /**
     * Returns the bind variable value for BAgent.
     * @return bind variable value for BAgent
     */
    public String getBAgent() {
        return (String) getNamedWhereClauseParam("BAgent");
    }

    /**
     * Sets <code>value</code> for bind variable BAgent.
     * @param value value to bind as BAgent
     */
    public void setBAgent(String value) {
        setNamedWhereClauseParam("BAgent", value);
    }
    @Override
    public void prepareRowSetForQuery(ViewRowSetImpl viewRowSetImpl) {
        viewRowSetImpl.ensureVariableManager().setVariableValue("BAgent",ADFContext.getCurrent().getSecurityContext().getUserName() );
        super.prepareRowSetForQuery(viewRowSetImpl);
    }
}
