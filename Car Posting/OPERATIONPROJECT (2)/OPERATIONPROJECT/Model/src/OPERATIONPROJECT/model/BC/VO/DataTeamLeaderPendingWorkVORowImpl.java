package OPERATIONPROJECT.model.BC.VO;

import java.math.BigDecimal;

import java.sql.Timestamp;

import oracle.jbo.domain.Date;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Sun Feb 14 10:30:16 EET 2021
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class DataTeamLeaderPendingWorkVORowImpl extends ViewRowImpl {


    public static final int ENTITY_DATATEAMLEADERPENDINGWORKEO = 0;

    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    protected enum AttributesEnum {
        AreaId,
        AreaName,
        AssignDate,
        DrivId,
        DriverName,
        PilotId,
        PilotName,
        TotalAssignemnt,
        PendingWorkFlag,
        RescheduleDelivery,
        NewDeliveryDate,
        DataAgent,
        Dispatchable,
        IssueBover,
        LostContracts,
        NeedCall,
        Perfect,
        Priority10,
        Priority8,
        Priority9,
        DataAgentVO1,
        ResceduleDeliveryROV1;
        private static AttributesEnum[] vals = null;
        ;
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
    public static final int DRIVID = AttributesEnum.DrivId.index();
    public static final int DRIVERNAME = AttributesEnum.DriverName.index();
    public static final int PILOTID = AttributesEnum.PilotId.index();
    public static final int PILOTNAME = AttributesEnum.PilotName.index();
    public static final int TOTALASSIGNEMNT = AttributesEnum.TotalAssignemnt.index();
    public static final int PENDINGWORKFLAG = AttributesEnum.PendingWorkFlag.index();
    public static final int RESCHEDULEDELIVERY = AttributesEnum.RescheduleDelivery.index();
    public static final int NEWDELIVERYDATE = AttributesEnum.NewDeliveryDate.index();
    public static final int DATAAGENT = AttributesEnum.DataAgent.index();
    public static final int DISPATCHABLE = AttributesEnum.Dispatchable.index();
    public static final int ISSUEBOVER = AttributesEnum.IssueBover.index();
    public static final int LOSTCONTRACTS = AttributesEnum.LostContracts.index();
    public static final int NEEDCALL = AttributesEnum.NeedCall.index();
    public static final int PERFECT = AttributesEnum.Perfect.index();
    public static final int PRIORITY10 = AttributesEnum.Priority10.index();
    public static final int PRIORITY8 = AttributesEnum.Priority8.index();
    public static final int PRIORITY9 = AttributesEnum.Priority9.index();
    public static final int DATAAGENTVO1 = AttributesEnum.DataAgentVO1.index();
    public static final int RESCEDULEDELIVERYROV1 = AttributesEnum.ResceduleDeliveryROV1.index();

    /**
     * This is the default constructor (do not remove).
     */
    public DataTeamLeaderPendingWorkVORowImpl() {
    }
}

