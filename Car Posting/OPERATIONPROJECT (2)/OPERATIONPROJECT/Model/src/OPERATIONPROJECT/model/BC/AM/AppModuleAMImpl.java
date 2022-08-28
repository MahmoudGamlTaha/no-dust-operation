package OPERATIONPROJECT.model.BC.AM;

import OPERATIONPROJECT.model.BC.VO.AssNotesReviewDetailsVOImpl;
import OPERATIONPROJECT.model.BC.VO.CarReconciliationVOImpl;

import OPERATIONPROJECT.model.BC.VO.DataAgentContractsReviewVOImpl;
import OPERATIONPROJECT.model.BC.VO.DataAgentVOImpl;

import OPERATIONPROJECT.model.BC.VO.DataTeamAgentCarReconciliationVOImpl;
import OPERATIONPROJECT.model.BC.VO.DataTeamAgentDashboardVOImpl;
import OPERATIONPROJECT.model.BC.VO.DataTeamAgentEndOfDayReviewVoImpl;
import OPERATIONPROJECT.model.BC.VO.DataTeamAgentNotesReviewVOImpl;
import OPERATIONPROJECT.model.BC.VO.DataTeamAgentPendingWorkVOImpl;
import OPERATIONPROJECT.model.BC.VO.DataTeamLeaderEndOfDayVOImpl;

import OPERATIONPROJECT.model.BC.VO.EligibleLostHeaderVOImpl;
import OPERATIONPROJECT.model.BC.VO.EligibleRLostHeaderVOImpl;
import OPERATIONPROJECT.model.BC.VO.EndOfDayRevDetailsVOImpl;

import OPERATIONPROJECT.model.BC.VO.Lookups.ResceduleDeliveryROVImpl;
import OPERATIONPROJECT.model.BC.VO.ProductContractVOImpl;
import OPERATIONPROJECT.model.BC.VO.ProductDetailsLostReconciliationVOImpl;
import OPERATIONPROJECT.model.BC.VO.ProductDetailsRLostReconciliationVOImpl;

import OPERATIONPROJECT.model.BC.VO.ProductLostRlostVOImpl;

import oracle.jbo.server.ViewLinkImpl;


import oracle.jbo.server.ViewObjectImpl;

import oracle.jheadstart.model.adfbc.v2.JhsApplicationModuleImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Oct 08 14:38:03 EET 2020
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class AppModuleAMImpl extends JhsApplicationModuleImpl implements OPERATIONPROJECT.model.BC.AM.common.AppModuleAM {
    /**
     * This is the default constructor (do not remove).
     */
    public AppModuleAMImpl() {
    }

    /**
     * Container's getter for Countries1.
     * @return Countries1
     */
    public ViewObjectImpl getCountries1() {
        return (ViewObjectImpl) findViewObject("Countries1");
    }

    /**
     * Container's getter for WeekDaysVO.
     * @return WeekDaysVO
     */
    public ViewObjectImpl getWeekDaysVO() {
        return (ViewObjectImpl) findViewObject("WeekDaysVO");
    }

    /**
     * Container's getter for TeamleaderDashboardVO.
     * @return TeamleaderDashboardVO
     */
    public ViewObjectImpl getTeamleaderDashboardVO() {
        return (ViewObjectImpl) findViewObject("TeamleaderDashboardVO");
    }

    /**
     * Container's getter for AreaSmsVO.
     * @return AreaSmsVO
     */
    public ViewObjectImpl getAreaSmsVO() {
        return (ViewObjectImpl) findViewObject("AreaSmsVO");
    }

    /**
     * Container's getter for OperationWorkVO.
     * @return OperationWorkVO
     */
    public ViewObjectImpl getOperationWorkVO() {
        return (ViewObjectImpl) findViewObject("OperationWorkVO");
    }

    /**
     * Container's getter for SchedulingTableIncludeVO.
     * @return SchedulingTableIncludeVO
     */
    public ViewObjectImpl getSchedulingTableIncludeVO() {
        return (ViewObjectImpl) findViewObject("SchedulingTableIncludeVO");
    }

    /**
     * Container's getter for SchedulingTableExcludeVO.
     * @return SchedulingTableExcludeVO
     */
    public ViewObjectImpl getSchedulingTableExcludeVO() {
        return (ViewObjectImpl) findViewObject("SchedulingTableExcludeVO");
    }

    /**
     * Container's getter for CarReconciliation1.
     * @return CarReconciliation1
     */
    public CarReconciliationVOImpl getCarReconciliation1() {
        return (CarReconciliationVOImpl) findViewObject("CarReconciliation1");
    }

    /**
     * Container's getter for DriverPaymnet1.
     * @return DriverPaymnet1
     */
    public ViewObjectImpl getDriverPaymnet1() {
        return (ViewObjectImpl) findViewObject("DriverPaymnet1");
    }

    /**
     * Container's getter for PilotPayment1.
     * @return PilotPayment1
     */
    public ViewObjectImpl getPilotPayment1() {
        return (ViewObjectImpl) findViewObject("PilotPayment1");
    }

    /**
     * Container's getter for CarReconciliation2.
     * @return CarReconciliation2
     */
    public CarReconciliationVOImpl getCarReconciliation2() {
        return (CarReconciliationVOImpl) findViewObject("CarReconciliation2");
    }

    /**
     * Container's getter for CarBalance1.
     * @return CarBalance1
     */
    public ViewObjectImpl getCarBalance1() {
        return (ViewObjectImpl) findViewObject("CarBalance1");
    }

    /**
     * Container's getter for CarSummary1.
     * @return CarSummary1
     */
    public ViewObjectImpl getCarSummary1() {
        return (ViewObjectImpl) findViewObject("CarSummary1");
    }

    /**
     * Container's getter for HubReconciliation1.
     * @return HubReconciliation1
     */
    public ViewObjectImpl getHubReconciliation1() {
        return (ViewObjectImpl) findViewObject("HubReconciliation1");
    }

    /**
     * Container's getter for PilotLostNew1.
     * @return PilotLostNew1
     */
    public ViewObjectImpl getPilotLostNew1() {
        return (ViewObjectImpl) findViewObject("PilotLostNew1");
    }

    /**
     * Container's getter for PilotReconciliationNotes1.
     * @return PilotReconciliationNotes1
     */
    public ViewObjectImpl getPilotReconciliationNotes1() {
        return (ViewObjectImpl) findViewObject("PilotReconciliationNotes1");
    }

    /**
     * Container's getter for PilotReconciliationSummary1.
     * @return PilotReconciliationSummary1
     */
    public ViewObjectImpl getPilotReconciliationSummary1() {
        return (ViewObjectImpl) findViewObject("PilotReconciliationSummary1");
    }

    /**
     * Container's getter for RelatedShipment1.
     * @return RelatedShipment1
     */
    public ViewObjectImpl getRelatedShipment1() {
        return (ViewObjectImpl) findViewObject("RelatedShipment1");
    }

    /**
     * Container's getter for ShipmentTotalLost_Rlost1.
     * @return ShipmentTotalLost_Rlost1
     */
    public ViewObjectImpl getShipmentTotalLost_Rlost1() {
        return (ViewObjectImpl) findViewObject("ShipmentTotalLost_Rlost1");
    }

    /**
     * Container's getter for TotalPilotReconciliationSummary1.
     * @return TotalPilotReconciliationSummary1
     */
    public ViewObjectImpl getTotalPilotReconciliationSummary1() {
        return (ViewObjectImpl) findViewObject("TotalPilotReconciliationSummary1");
    }

    /**
     * Container's getter for WeekDaysTeamleaderDashboardVL.
     * @return WeekDaysTeamleaderDashboardVL
     */
    public ViewLinkImpl getWeekDaysTeamleaderDashboardVL() {
        return (ViewLinkImpl) findViewLink("WeekDaysTeamleaderDashboardVL");
    }

    /**
     * Container's getter for WeekDaysAreaSmsVL.
     * @return WeekDaysAreaSmsVL
     */
    public ViewLinkImpl getWeekDaysAreaSmsVL() {
        return (ViewLinkImpl) findViewLink("WeekDaysAreaSmsVL");
    }

    /**
     * Container's getter for WeekDaysOperationWorkVL.
     * @return WeekDaysOperationWorkVL
     */
    public ViewLinkImpl getWeekDaysOperationWorkVL() {
        return (ViewLinkImpl) findViewLink("WeekDaysOperationWorkVL");
    }

    /**
     * Container's getter for WeekDaysSchedulingTableIncludeVL.
     * @return WeekDaysSchedulingTableIncludeVL
     */
    public ViewLinkImpl getWeekDaysSchedulingTableIncludeVL() {
        return (ViewLinkImpl) findViewLink("WeekDaysSchedulingTableIncludeVL");
    }

    /**
     * Container's getter for WeekDaysSchedulingTableExcludeVL.
     * @return WeekDaysSchedulingTableExcludeVL
     */
    public ViewLinkImpl getWeekDaysSchedulingTableExcludeVL() {
        return (ViewLinkImpl) findViewLink("WeekDaysSchedulingTableExcludeVL");
    }

    /**
     * Container's getter for CarBalanceVL1.
     * @return CarBalanceVL1
     */
    public ViewLinkImpl getCarBalanceVL1() {
        return (ViewLinkImpl) findViewLink("CarBalanceVL1");
    }

    /**
     * Container's getter for CarSummaryVL1.
     * @return CarSummaryVL1
     */
    public ViewLinkImpl getCarSummaryVL1() {
        return (ViewLinkImpl) findViewLink("CarSummaryVL1");
    }

    /**
     * Container's getter for HubReconciliationVL1.
     * @return HubReconciliationVL1
     */
    public ViewLinkImpl getHubReconciliationVL1() {
        return (ViewLinkImpl) findViewLink("HubReconciliationVL1");
    }

    /**
     * Container's getter for PilotLostNewVL1.
     * @return PilotLostNewVL1
     */
    public ViewLinkImpl getPilotLostNewVL1() {
        return (ViewLinkImpl) findViewLink("PilotLostNewVL1");
    }

    /**
     * Container's getter for PilotReconciliationVL1.
     * @return PilotReconciliationVL1
     */
    public ViewLinkImpl getPilotReconciliationVL1() {
        return (ViewLinkImpl) findViewLink("PilotReconciliationVL1");
    }

    /**
     * Container's getter for PilotReconciliationSummaryVL1.
     * @return PilotReconciliationSummaryVL1
     */
    public ViewLinkImpl getPilotReconciliationSummaryVL1() {
        return (ViewLinkImpl) findViewLink("PilotReconciliationSummaryVL1");
    }

    /**
     * Container's getter for Reconciliation_RelatedShipmentVL1.
     * @return Reconciliation_RelatedShipmentVL1
     */
    public ViewLinkImpl getReconciliation_RelatedShipmentVL1() {
        return (ViewLinkImpl) findViewLink("Reconciliation_RelatedShipmentVL1");
    }

    /**
     * Container's getter for ShipmentTotalLost_RlostVL1.
     * @return ShipmentTotalLost_RlostVL1
     */
    public ViewLinkImpl getShipmentTotalLost_RlostVL1() {
        return (ViewLinkImpl) findViewLink("ShipmentTotalLost_RlostVL1");
    }

    /**
     * Container's getter for TotalPilotReconciliationSummaryVL1.
     * @return TotalPilotReconciliationSummaryVL1
     */
    public ViewLinkImpl getTotalPilotReconciliationSummaryVL1() {
        return (ViewLinkImpl) findViewLink("TotalPilotReconciliationSummaryVL1");
    }

    /**
     * Container's getter for DataTeamLeaderDashboardVO1.
     * @return DataTeamLeaderDashboardVO1
     */
    public ViewObjectImpl getDataTeamLeaderDashboardVO1() {
        return (ViewObjectImpl) findViewObject("DataTeamLeaderDashboardVO1");
    }

    /**
     * Container's getter for DataTeamLeaderPendingWorkVO1.
     * @return DataTeamLeaderPendingWorkVO1
     */
    public ViewObjectImpl getDataTeamLeaderPendingWorkVO1() {
        return (ViewObjectImpl) findViewObject("DataTeamLeaderPendingWorkVO1");
    }

    /**
     * Container's getter for DataAgentVO1.
     * @return DataAgentVO1
     */
    public DataAgentVOImpl getDataAgentVO1() {
        return (DataAgentVOImpl) findViewObject("DataAgentVO1");
    }

    /**
     * Container's getter for DataTeamLeaderNotesReviewVO1.
     * @return DataTeamLeaderNotesReviewVO1
     */
    public ViewObjectImpl getDataTeamLeaderNotesReviewVO1() {
        return (ViewObjectImpl) findViewObject("DataTeamLeaderNotesReviewVO1");
    }

    /**
     * Container's getter for DataTeamLeaderEndOfDayVO1.
     * @return DataTeamLeaderEndOfDayVO1
     */
    public ViewObjectImpl getDataTeamLeaderEndOfDayVO1() {
        return (ViewObjectImpl) findViewObject("DataTeamLeaderEndOfDayVO1");
    }

    /**
     * Container's getter for DataTeamLeaderContractsReviewVO1.
     * @return DataTeamLeaderContractsReviewVO1
     */
    public ViewObjectImpl getDataTeamLeaderContractsReviewVO1() {
        return (ViewObjectImpl) findViewObject("DataTeamLeaderContractsReviewVO1");
    }

    /**
     * Container's getter for DataAgentContractsReviewVO1.
     * @return DataAgentContractsReviewVO1
     */
    public DataAgentContractsReviewVOImpl getDataAgentContractsReviewVO1() {
        return (DataAgentContractsReviewVOImpl) findViewObject("DataAgentContractsReviewVO1");
    }

    /**
     * Container's getter for NewCancelSalesContractsVO1.
     * @return NewCancelSalesContractsVO1
     */
    public ViewObjectImpl getNewCancelSalesContractsVO1() {
        return (ViewObjectImpl) findViewObject("NewCancelSalesContractsVO1");
    }

    /**
     * Container's getter for NewCancelSalesContractsVL1.
     * @return NewCancelSalesContractsVL1
     */
    public ViewLinkImpl getNewCancelSalesContractsVL1() {
        return (ViewLinkImpl) findViewLink("NewCancelSalesContractsVL1");
    }

    /**
     * Container's getter for DataTeamAgentPendingWorkVO1.
     * @return DataTeamAgentPendingWorkVO1
     */
    public DataTeamAgentPendingWorkVOImpl getDataTeamAgentPendingWorkVO1() {
        return (DataTeamAgentPendingWorkVOImpl) findViewObject("DataTeamAgentPendingWorkVO1");
    }

    /**
     * Container's getter for PendingWorkAssViewVO1.
     * @return PendingWorkAssViewVO1
     */
    public ViewObjectImpl getPendingWorkAssViewVO1() {
        return (ViewObjectImpl) findViewObject("PendingWorkAssViewVO1");
    }

    /**
     * Container's getter for PendingWorkAssVL1.
     * @return PendingWorkAssVL1
     */
    public ViewLinkImpl getPendingWorkAssVL1() {
        return (ViewLinkImpl) findViewLink("PendingWorkAssVL1");
    }


    /**
     * Container's getter for ProductContractDDownloadVO1.
     * @return ProductContractDDownloadVO1
     */
    public ViewObjectImpl getProductContractDDownloadVO1() {
        return (ViewObjectImpl) findViewObject("ProductContractDDownloadVO1");
    }

    /**
     * Container's getter for CloseCodesGroupsVO1.
     * @return CloseCodesGroupsVO1
     */
    public ViewObjectImpl getCloseCodesGroupsVO1() {
        return (ViewObjectImpl) findViewObject("CloseCodesGroupsVO1");
    }

    /**
     * Container's getter for CloseCodeReasonsVO1.
     * @return CloseCodeReasonsVO1
     */
    public ViewObjectImpl getCloseCodeReasonsVO1() {
        return (ViewObjectImpl) findViewObject("CloseCodeReasonsVO1");
    }

    /**
     * Container's getter for DataTeamAgentDashboardVO1.
     * @return DataTeamAgentDashboardVO1
     */
    public DataTeamAgentDashboardVOImpl getDataTeamAgentDashboardVO1() {
        return (DataTeamAgentDashboardVOImpl) findViewObject("DataTeamAgentDashboardVO1");
    }

    /**
     * Container's getter for DataTeamAgentNotesReviewVO1.
     * @return DataTeamAgentNotesReviewVO1
     */
    public DataTeamAgentNotesReviewVOImpl getDataTeamAgentNotesReviewVO1() {
        return (DataTeamAgentNotesReviewVOImpl) findViewObject("DataTeamAgentNotesReviewVO1");
    }

    /**
     * Container's getter for AssNotesReviewDetailsVO1.
     * @return AssNotesReviewDetailsVO1
     */
    public AssNotesReviewDetailsVOImpl getAssNotesReviewDetailsVO1() {
        return (AssNotesReviewDetailsVOImpl) findViewObject("AssNotesReviewDetailsVO1");
    }

    /**
     * Container's getter for AssNotesReviewVL1.
     * @return AssNotesReviewVL1
     */
    public ViewLinkImpl getAssNotesReviewVL1() {
        return (ViewLinkImpl) findViewLink("AssNotesReviewVL1");
    }

    /**
     * Container's getter for DataTeamAgentEndOfDayReviewVo1.
     * @return DataTeamAgentEndOfDayReviewVo1
     */
    public DataTeamAgentEndOfDayReviewVoImpl getDataTeamAgentEndOfDayReviewVo1() {
        return (DataTeamAgentEndOfDayReviewVoImpl) findViewObject("DataTeamAgentEndOfDayReviewVo1");
    }

    /**
     * Container's getter for EndOfDayRevDetailsVO1.
     * @return EndOfDayRevDetailsVO1
     */
    public EndOfDayRevDetailsVOImpl getEndOfDayRevDetailsVO1() {
        return (EndOfDayRevDetailsVOImpl) findViewObject("EndOfDayRevDetailsVO1");
    }

    /**
     * Container's getter for EndOfDayRevDetailsVL1.
     * @return EndOfDayRevDetailsVL1
     */
    public ViewLinkImpl getEndOfDayRevDetailsVL1() {
        return (ViewLinkImpl) findViewLink("EndOfDayRevDetailsVL1");
    }


    /**
     * Container's getter for NewCancelSalesContractsProducts1.
     * @return NewCancelSalesContractsProducts1
     */
    public ViewObjectImpl getNewCancelSalesContractsProducts1() {
        return (ViewObjectImpl) findViewObject("NewCancelSalesContractsProducts1");
    }

    /**
     * Container's getter for DataStatusActionVO1.
     * @return DataStatusActionVO1
     */
    public ViewObjectImpl getDataStatusActionVO1() {
        return (ViewObjectImpl) findViewObject("DataStatusActionVO1");
    }

    /**
     * Container's getter for DataTeamAgentCarReconciliationVO1.
     * @return DataTeamAgentCarReconciliationVO1
     */
    public DataTeamAgentCarReconciliationVOImpl getDataTeamAgentCarReconciliationVO1() {
        return (DataTeamAgentCarReconciliationVOImpl) findViewObject("DataTeamAgentCarReconciliationVO1");
    }

    /**
     * Container's getter for RelatedShipmentVO1.
     * @return RelatedShipmentVO1
     */
    public ViewObjectImpl getRelatedShipmentVO1() {
        return (ViewObjectImpl) findViewObject("RelatedShipmentVO1");
    }

    /**
     * Container's getter for DataTeamAgentCarReconVL1.
     * @return DataTeamAgentCarReconVL1
     */
    public ViewLinkImpl getDataTeamAgentCarReconVL1() {
        return (ViewLinkImpl) findViewLink("DataTeamAgentCarReconVL1");
    }

    /**
     * Container's getter for ContractsReviewVO1.
     * @return ContractsReviewVO1
     */
    public ViewObjectImpl getContractsReviewVO1() {
        return (ViewObjectImpl) findViewObject("ContractsReviewVO1");
    }

    /**
     * Container's getter for ContractsReviewVL1.
     * @return ContractsReviewVL1
     */
    public ViewLinkImpl getContractsReviewVL1() {
        return (ViewLinkImpl) findViewLink("ContractsReviewVL1");
    }

    /**
     * Container's getter for ReconciliationNotesVO1.
     * @return ReconciliationNotesVO1
     */
    public ViewObjectImpl getReconciliationNotesVO1() {
        return (ViewObjectImpl) findViewObject("ReconciliationNotesVO1");
    }

    /**
     * Container's getter for ReconciliationNotesVL1.
     * @return ReconciliationNotesVL1
     */
    public ViewLinkImpl getReconciliationNotesVL1() {
        return (ViewLinkImpl) findViewLink("ReconciliationNotesVL1");
    }

    /**
     * Container's getter for CarReconHubReconVO1.
     * @return CarReconHubReconVO1
     */
    public ViewObjectImpl getCarReconHubReconVO1() {
        return (ViewObjectImpl) findViewObject("CarReconHubReconVO1");
    }

    /**
     * Container's getter for CarReoncHubReconVL1.
     * @return CarReoncHubReconVL1
     */
    public ViewLinkImpl getCarReoncHubReconVL1() {
        return (ViewLinkImpl) findViewLink("CarReoncHubReconVL1");
    }

    /**
     * Container's getter for CarReconPilotsLostNewVO1.
     * @return CarReconPilotsLostNewVO1
     */
    public ViewObjectImpl getCarReconPilotsLostNewVO1() {
        return (ViewObjectImpl) findViewObject("CarReconPilotsLostNewVO1");
    }

    /**
     * Container's getter for CarReconPilotsLostNewVL1.
     * @return CarReconPilotsLostNewVL1
     */
    public ViewLinkImpl getCarReconPilotsLostNewVL1() {
        return (ViewLinkImpl) findViewLink("CarReconPilotsLostNewVL1");
    }

    /**
     * Container's getter for CarLostNewVO1.
     * @return CarLostNewVO1
     */
    public ViewObjectImpl getCarLostNewVO1() {
        return (ViewObjectImpl) findViewObject("CarLostNewVO1");
    }

    /**
     * Container's getter for CarReconCarLostNewVL1.
     * @return CarReconCarLostNewVL1
     */
    public ViewLinkImpl getCarReconCarLostNewVL1() {
        return (ViewLinkImpl) findViewLink("CarReconCarLostNewVL1");
    }

    /**
     * Container's getter for PilotReconciliationSummaryVO1.
     * @return PilotReconciliationSummaryVO1
     */
    public ViewObjectImpl getPilotReconciliationSummaryVO1() {
        return (ViewObjectImpl) findViewObject("PilotReconciliationSummaryVO1");
    }

    /**
     * Container's getter for PilotReconVL1.
     * @return PilotReconVL1
     */
    public ViewLinkImpl getPilotReconVL1() {
        return (ViewLinkImpl) findViewLink("PilotReconVL1");
    }

    /**
     * Container's getter for CarReconCarSummaryVO1.
     * @return CarReconCarSummaryVO1
     */
    public ViewObjectImpl getCarReconCarSummaryVO1() {
        return (ViewObjectImpl) findViewObject("CarReconCarSummaryVO1");
    }

    /**
     * Container's getter for CarReconCarSummaryVL1.
     * @return CarReconCarSummaryVL1
     */
    public ViewLinkImpl getCarReconCarSummaryVL1() {
        return (ViewLinkImpl) findViewLink("CarReconCarSummaryVL1");
    }

    /**
     * Container's getter for PilotCoverPilotCoverProductVO1.
     * @return PilotCoverPilotCoverProductVO1
     */
    public ViewObjectImpl getPilotCoverPilotCoverProductVO1() {
        return (ViewObjectImpl) findViewObject("PilotCoverPilotCoverProductVO1");
    }

    /**
     * Container's getter for PilotCoverReconDriverPilotVO1.
     * @return PilotCoverReconDriverPilotVO1
     */
    public ViewObjectImpl getPilotCoverReconDriverPilotVO1() {
        return (ViewObjectImpl) findViewObject("PilotCoverReconDriverPilotVO1");
    }

    /**
     * Container's getter for ProductDetailsHubReconciliationSummaryVO1.
     * @return ProductDetailsHubReconciliationSummaryVO1
     */
    public ViewObjectImpl getProductDetailsHubReconciliationSummaryVO1() {
        return (ViewObjectImpl) findViewObject("ProductDetailsHubReconciliationSummaryVO1");
    }

    /**
     * Container's getter for ProductDetailsProductDetailsSummaryVO1.
     * @return ProductDetailsProductDetailsSummaryVO1
     */
    public ViewObjectImpl getProductDetailsProductDetailsSummaryVO1() {
        return (ViewObjectImpl) findViewObject("ProductDetailsProductDetailsSummaryVO1");
    }

    /**
     * Container's getter for ProductDetailsTotalProductDetailsSummaryVO1.
     * @return ProductDetailsTotalProductDetailsSummaryVO1
     */
    public ViewObjectImpl getProductDetailsTotalProductDetailsSummaryVO1() {
        return (ViewObjectImpl) findViewObject("ProductDetailsTotalProductDetailsSummaryVO1");
    }

    /**
     * Container's getter for ProductDetailsRLostReconciliationVO1.
     * @return ProductDetailsRLostReconciliationVO1
     */
    public ProductDetailsRLostReconciliationVOImpl getProductDetailsRLostReconciliationVO1() {
        return (ProductDetailsRLostReconciliationVOImpl) findViewObject("ProductDetailsRLostReconciliationVO1");
    }

    /**
     * Container's getter for ProductDetailsLostReconciliationVO1.
     * @return ProductDetailsLostReconciliationVO1
     */
    public ProductDetailsLostReconciliationVOImpl getProductDetailsLostReconciliationVO1() {
        return (ProductDetailsLostReconciliationVOImpl) findViewObject("ProductDetailsLostReconciliationVO1");
    }

    /**
     * Container's getter for EligibleRLostHeaderVO1.
     * @return EligibleRLostHeaderVO1
     */
    public EligibleRLostHeaderVOImpl getEligibleRLostHeaderVO1() {
        return (EligibleRLostHeaderVOImpl) findViewObject("EligibleRLostHeaderVO1");
    }

    /**
     * Container's getter for EligibleLostHeaderVO1.
     * @return EligibleLostHeaderVO1
     */
    public EligibleLostHeaderVOImpl getEligibleLostHeaderVO1() {
        return (EligibleLostHeaderVOImpl) findViewObject("EligibleLostHeaderVO1");
    }

    /**
     * Container's getter for ProductLostRlostVO1.
     * @return ProductLostRlostVO1
     */
    public ProductLostRlostVOImpl getProductLostRlostVO1() {
        return (ProductLostRlostVOImpl) findViewObject("ProductLostRlostVO1");
    }

    /**
     * Container's getter for Shipment1VO1.
     * @return Shipment1VO1
     */
    public ViewObjectImpl getShipment1VO1() {
        return (ViewObjectImpl) findViewObject("Shipment1VO1");
    }

    /**
     * Container's getter for CoverProductVO1.
     * @return CoverProductVO1
     */
    public ViewObjectImpl getCoverProductVO1() {
        return (ViewObjectImpl) findViewObject("CoverProductVO1");
    }


    /**
     * Container's getter for RelatedAssVO1.
     * @return RelatedAssVO1
     */
    public ViewObjectImpl getRelatedAssVO1() {
        return (ViewObjectImpl) findViewObject("RelatedAssVO1");
    }

    /**
     * Container's getter for PilotReconciliationSumAssVL1.
     * @return PilotReconciliationSumAssVL1
     */
    public ViewLinkImpl getPilotReconciliationSumAssVL1() {
        return (ViewLinkImpl) findViewLink("PilotReconciliationSumAssVL1");
    }

    /**
     * Container's getter for RelatedCRVO1.
     * @return RelatedCRVO1
     */
    public ViewObjectImpl getRelatedCRVO1() {
        return (ViewObjectImpl) findViewObject("RelatedCRVO1");
    }

    /**
     * Container's getter for PilotReconciliationSummCRVL1.
     * @return PilotReconciliationSummCRVL1
     */
    public ViewLinkImpl getPilotReconciliationSummCRVL1() {
        return (ViewLinkImpl) findViewLink("PilotReconciliationSummCRVL1");
    }

    /**
     * Container's getter for CarIssueVO1.
     * @return CarIssueVO1
     */
    public ViewObjectImpl getCarIssueVO1() {
        return (ViewObjectImpl) findViewObject("CarIssueVO1");
    }

    /**
     * Container's getter for InternalIssueVO1.
     * @return InternalIssueVO1
     */
    public ViewObjectImpl getInternalIssueVO1() {
        return (ViewObjectImpl) findViewObject("InternalIssueVO1");
    }

    /**
     * Container's getter for DriverPilotIssueVO1.
     * @return DriverPilotIssueVO1
     */
    public ViewObjectImpl getDriverPilotIssueVO1() {
        return (ViewObjectImpl) findViewObject("DriverPilotIssueVO1");
    }

    /**
     * Container's getter for CarIssueDetailsVO1.
     * @return CarIssueDetailsVO1
     */
    public ViewObjectImpl getCarIssueDetailsVO1() {
        return (ViewObjectImpl) findViewObject("CarIssueDetailsVO1");
    }

    /**
     * Container's getter for CarIssueVL1.
     * @return CarIssueVL1
     */
    public ViewLinkImpl getCarIssueVL1() {
        return (ViewLinkImpl) findViewLink("CarIssueVL1");
    }

    /**
     * Container's getter for CarIssueViewVO1.
     * @return CarIssueViewVO1
     */
    public ViewObjectImpl getCarIssueViewVO1() {
        return (ViewObjectImpl) findViewObject("CarIssueViewVO1");
    }

    /**
     * Container's getter for CarIssueDetViewVO1.
     * @return CarIssueDetViewVO1
     */
    public ViewObjectImpl getCarIssueDetViewVO1() {
        return (ViewObjectImpl) findViewObject("CarIssueDetViewVO1");
    }

    /**
     * Container's getter for CarIssueViewVL1.
     * @return CarIssueViewVL1
     */
    public ViewLinkImpl getCarIssueViewVL1() {
        return (ViewLinkImpl) findViewLink("CarIssueViewVL1");
    }

    /**
     * Container's getter for ProductContractDDownloadVO2.
     * @return ProductContractDDownloadVO2
     */
    public ViewObjectImpl getProductContractDDownloadVO2() {
        return (ViewObjectImpl) findViewObject("ProductContractDDownloadVO2");
    }

    /**
     * Container's getter for NotesProductContractDVL1.
     * @return NotesProductContractDVL1
     */
    public ViewLinkImpl getNotesProductContractDVL1() {
        return (ViewLinkImpl) findViewLink("NotesProductContractDVL1");
    }

    /**
     * Container's getter for ProductContractVO1.
     * @return ProductContractVO1
     */
    public ProductContractVOImpl getProductContractVO1() {
        return (ProductContractVOImpl) findViewObject("ProductContractVO1");
    }

    /**
     * Container's getter for ProductContractNotesVL1.
     * @return ProductContractNotesVL1
     */
    public ViewLinkImpl getProductContractNotesVL1() {
        return (ViewLinkImpl) findViewLink("ProductContractNotesVL1");
    }

    /**
     * Container's getter for ProductContractVO2.
     * @return ProductContractVO2
     */
    public ProductContractVOImpl getProductContractVO2() {
        return (ProductContractVOImpl) findViewObject("ProductContractVO2");
    }

    /**
     * Container's getter for PendingAssViewVO1.
     * @return PendingAssViewVO1
     */
    public ViewObjectImpl getPendingAssViewVO1() {
        return (ViewObjectImpl) findViewObject("PendingAssViewVO1");
    }

    /**
     * Container's getter for ProductPackageVO1.
     * @return ProductPackageVO1
     */
    public ViewObjectImpl getProductPackageVO1() {
        return (ViewObjectImpl) findViewObject("ProductPackageVO1");
    }

    /**
     * Container's getter for ProductContractViewVO1.
     * @return ProductContractViewVO1
     */
    public ViewObjectImpl getProductContractViewVO1() {
        return (ViewObjectImpl) findViewObject("ProductContractViewVO1");
    }

    /**
     * Container's getter for AssNotesPcontractVL1.
     * @return AssNotesPcontractVL1
     */
    public ViewLinkImpl getAssNotesPcontractVL1() {
        return (ViewLinkImpl) findViewLink("AssNotesPcontractVL1");
    }

    /**
     * Container's getter for FilteredPendingWorkVO1.
     * @return FilteredPendingWorkVO1
     */
    public ViewObjectImpl getFilteredPendingWorkVO1() {
        return (ViewObjectImpl) findViewObject("FilteredPendingWorkVO1");
    }

    /**
     * Container's getter for NewAssNotesReviewDetailsVO1.
     * @return NewAssNotesReviewDetailsVO1
     */
    public ViewObjectImpl getNewAssNotesReviewDetailsVO1() {
        return (ViewObjectImpl) findViewObject("NewAssNotesReviewDetailsVO1");
    }

    /**
     * Container's getter for NewAssNotesDetVL1.
     * @return NewAssNotesDetVL1
     */
    public ViewLinkImpl getNewAssNotesDetVL1() {
        return (ViewLinkImpl) findViewLink("NewAssNotesDetVL1");
    }


    /**
     * Container's getter for NAssNotesDetVO3.
     * @return NAssNotesDetVO3
     */
    public ViewObjectImpl getNAssNotesDetVO3() {
        return (ViewObjectImpl) findViewObject("NAssNotesDetVO3");
    }

    /**
     * Container's getter for AssNotesVL3.
     * @return AssNotesVL3
     */
    public ViewLinkImpl getAssNotesVL3() {
        return (ViewLinkImpl) findViewLink("AssNotesVL3");
    }

    /**
     * Container's getter for ResceduleDeliveryROV1.
     * @return ResceduleDeliveryROV1
     */
    public ResceduleDeliveryROVImpl getResceduleDeliveryROV1() {
        return (ResceduleDeliveryROVImpl) findViewObject("ResceduleDeliveryROV1");
    }
}
