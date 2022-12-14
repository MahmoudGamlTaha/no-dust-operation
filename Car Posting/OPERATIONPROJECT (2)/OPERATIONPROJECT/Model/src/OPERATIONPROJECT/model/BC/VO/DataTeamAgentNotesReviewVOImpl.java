package OPERATIONPROJECT.model.BC.VO;

import oracle.adf.share.ADFContext;

import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.server.ViewRowSetImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Feb 16 13:19:56 EET 2021
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class DataTeamAgentNotesReviewVOImpl extends ViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public DataTeamAgentNotesReviewVOImpl() {
    }

    @Override
    public void prepareRowSetForQuery(ViewRowSetImpl viewRowSetImpl) {
        // TODO Implement this method
        viewRowSetImpl.ensureVariableManager().setVariableValue("P_CURRENT_USER", ADFContext.getCurrent().getSecurityContext().getUserName());
        super.prepareRowSetForQuery(viewRowSetImpl);
    }

    /**
     * Returns the bind variable value for P_CURRENT_USER.
     * @return bind variable value for P_CURRENT_USER
     */
    public String getP_CURRENT_USER() {
        return (String) getNamedWhereClauseParam("P_CURRENT_USER");
    }

    /**
     * Sets <code>value</code> for bind variable P_CURRENT_USER.
     * @param value value to bind as P_CURRENT_USER
     */
    public void setP_CURRENT_USER(String value) {
        setNamedWhereClauseParam("P_CURRENT_USER", value);
    }
}

