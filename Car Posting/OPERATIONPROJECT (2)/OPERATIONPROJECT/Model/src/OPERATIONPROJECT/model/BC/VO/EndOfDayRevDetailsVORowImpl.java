package OPERATIONPROJECT.model.BC.VO;

import java.math.BigDecimal;

import java.sql.Timestamp;

import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Feb 16 14:23:38 EET 2021
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class EndOfDayRevDetailsVORowImpl extends ViewRowImpl {


    public static final int ENTITY_RECONCILIATIONDRIVERPILOTEO = 0;

    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    protected enum AttributesEnum {
        ReturnDate,
        DriverId,
        PilotName,
        PilotId,
        AreaId,
        DataAgent,
        ExpectedMoney,
        ApprovedMoney,
        ActualMoney,
        Notes,
        Status,
        Id,
        UpdatedBy,
        DataTeamAgentEndOfDayReviewVo,
        DataStatusActionVO1;
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


    public static final int RETURNDATE = AttributesEnum.ReturnDate.index();
    public static final int DRIVERID = AttributesEnum.DriverId.index();
    public static final int PILOTNAME = AttributesEnum.PilotName.index();
    public static final int PILOTID = AttributesEnum.PilotId.index();
    public static final int AREAID = AttributesEnum.AreaId.index();
    public static final int DATAAGENT = AttributesEnum.DataAgent.index();
    public static final int EXPECTEDMONEY = AttributesEnum.ExpectedMoney.index();
    public static final int APPROVEDMONEY = AttributesEnum.ApprovedMoney.index();
    public static final int ACTUALMONEY = AttributesEnum.ActualMoney.index();
    public static final int NOTES = AttributesEnum.Notes.index();
    public static final int STATUS = AttributesEnum.Status.index();
    public static final int ID = AttributesEnum.Id.index();
    public static final int UPDATEDBY = AttributesEnum.UpdatedBy.index();
    public static final int DATATEAMAGENTENDOFDAYREVIEWVO = AttributesEnum.DataTeamAgentEndOfDayReviewVo.index();
    public static final int DATASTATUSACTIONVO1 = AttributesEnum.DataStatusActionVO1.index();

    /**
     * This is the default constructor (do not remove).
     */
    public EndOfDayRevDetailsVORowImpl() {
    }
}
