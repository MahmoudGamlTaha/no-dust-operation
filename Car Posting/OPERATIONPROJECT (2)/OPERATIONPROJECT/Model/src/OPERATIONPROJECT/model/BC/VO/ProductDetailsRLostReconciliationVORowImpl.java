package OPERATIONPROJECT.model.BC.VO;

import java.math.BigDecimal;
import java.math.BigInteger;

import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Sat Feb 27 16:05:07 EET 2021
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class ProductDetailsRLostReconciliationVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    protected enum AttributesEnum {
        PilotName,
        PilotId,
        ShipmentId,
        Product,
        ProductId,
        DriverId,
        RlostEligible;
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


    public static final int PILOTNAME = AttributesEnum.PilotName.index();
    public static final int PILOTID = AttributesEnum.PilotId.index();
    public static final int SHIPMENTID = AttributesEnum.ShipmentId.index();
    public static final int PRODUCT = AttributesEnum.Product.index();
    public static final int PRODUCTID = AttributesEnum.ProductId.index();
    public static final int DRIVERID = AttributesEnum.DriverId.index();
    public static final int RLOSTELIGIBLE = AttributesEnum.RlostEligible.index();

    /**
     * This is the default constructor (do not remove).
     */
    public ProductDetailsRLostReconciliationVORowImpl() {
    }
}
