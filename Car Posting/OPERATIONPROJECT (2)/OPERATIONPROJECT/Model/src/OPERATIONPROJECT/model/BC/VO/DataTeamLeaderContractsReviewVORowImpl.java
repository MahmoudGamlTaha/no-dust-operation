package OPERATIONPROJECT.model.BC.VO;

import java.math.BigDecimal;

import oracle.jbo.domain.Date;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Sun Feb 14 10:27:24 EET 2021
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class DataTeamLeaderContractsReviewVORowImpl extends ViewRowImpl {
    public static final int ENTITY_CONTRACTSREVIEWEO = 0;

    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    protected enum AttributesEnum {
        AreaId,
        AreaName,
        AssignDate,
        DataAgent,
        DeliveryType,
        DrivId,
        DriverName,
        PilotId,
        PilotName,
        TotalAssignemnt,
        ContractFlag,
        DataAgentVO1,
        DeliveryTypeROV1;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        protected int index() {
            return AttributesEnum.firstIndex() + ordinal();
        }

        protected static final int firstIndex() {
            return firstIndex;
        }

        protected static int count() {
            return AttributesEnum.firstIndex() + AttributesEnum.staticValues().length;
        }

        protected static final AttributesEnum[] staticValues() {
            if (vals == null) {
                vals = AttributesEnum.values();
            }
            return vals;
        }
    }
    public static final int AREAID = AttributesEnum.AreaId.index();
    public static final int AREANAME = AttributesEnum.AreaName.index();
    public static final int ASSIGNDATE = AttributesEnum.AssignDate.index();
    public static final int DATAAGENT = AttributesEnum.DataAgent.index();
    public static final int DELIVERYTYPE = AttributesEnum.DeliveryType.index();
    public static final int DRIVID = AttributesEnum.DrivId.index();
    public static final int DRIVERNAME = AttributesEnum.DriverName.index();
    public static final int PILOTID = AttributesEnum.PilotId.index();
    public static final int PILOTNAME = AttributesEnum.PilotName.index();
    public static final int TOTALASSIGNEMNT = AttributesEnum.TotalAssignemnt.index();
    public static final int CONTRACTFLAG = AttributesEnum.ContractFlag.index();
    public static final int DATAAGENTVO1 = AttributesEnum.DataAgentVO1.index();
    public static final int DELIVERYTYPEROV1 = AttributesEnum.DeliveryTypeROV1.index();

    /**
     * This is the default constructor (do not remove).
     */
    public DataTeamLeaderContractsReviewVORowImpl() {
    }
}

