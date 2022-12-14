package OPERATIONPROJECT.model.BC.VO;

import OPERATIONPROJECT.model.BC.EO.common.BinaryDouble;

import OPERATIONPROJECT.model.BC.EO.common.BinaryDouble1;

import java.math.BigDecimal;
import java.math.BigInteger;

import oracle.jbo.domain.DBSequence;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.NClobDomain;
import oracle.jbo.domain.Number;
import oracle.jbo.server.ViewRowImpl;

import oracle.ord.im.OrdAudioDomain;
import oracle.ord.im.OrdDocDomain;
import oracle.ord.im.OrdImageDomain;
import oracle.ord.im.OrdImageSignatureDomain;
import oracle.ord.im.OrdVideoDomain;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Sun Jun 20 09:34:40 EET 2021
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class ProductContractVORowImpl extends ViewRowImpl {


    public static final int ENTITY_PRODUCTCONTRACTEO = 0;

    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    protected enum AttributesEnum {
        CardNo,
        DeliveryFlag,
        ExchangeDuration,
        Financial,
        New11,
        PackageId,
        PackageNo,
        PackageVersion,
        PartialSubmit,
        Price,
        ProductConId,
        ProductId,
        ProductName,
        ProductSku,
        Quantity,
        TreatmentId,
        TreatmentName,
        Id,
        Active,
        UpdatedBy,
        NewPackFlag,
        ProductsAndPackagesROV1,
        TreatmentTypesROV1;
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


    public static final int CARDNO = AttributesEnum.CardNo.index();
    public static final int DELIVERYFLAG = AttributesEnum.DeliveryFlag.index();
    public static final int EXCHANGEDURATION = AttributesEnum.ExchangeDuration.index();
    public static final int FINANCIAL = AttributesEnum.Financial.index();
    public static final int NEW11 = AttributesEnum.New11.index();
    public static final int PACKAGEID = AttributesEnum.PackageId.index();
    public static final int PACKAGENO = AttributesEnum.PackageNo.index();
    public static final int PACKAGEVERSION = AttributesEnum.PackageVersion.index();
    public static final int PARTIALSUBMIT = AttributesEnum.PartialSubmit.index();
    public static final int PRICE = AttributesEnum.Price.index();
    public static final int PRODUCTCONID = AttributesEnum.ProductConId.index();
    public static final int PRODUCTID = AttributesEnum.ProductId.index();
    public static final int PRODUCTNAME = AttributesEnum.ProductName.index();
    public static final int PRODUCTSKU = AttributesEnum.ProductSku.index();
    public static final int QUANTITY = AttributesEnum.Quantity.index();
    public static final int TREATMENTID = AttributesEnum.TreatmentId.index();
    public static final int TREATMENTNAME = AttributesEnum.TreatmentName.index();
    public static final int ID = AttributesEnum.Id.index();
    public static final int ACTIVE = AttributesEnum.Active.index();
    public static final int UPDATEDBY = AttributesEnum.UpdatedBy.index();
    public static final int NEWPACKFLAG = AttributesEnum.NewPackFlag.index();
    public static final int PRODUCTSANDPACKAGESROV1 = AttributesEnum.ProductsAndPackagesROV1.index();
    public static final int TREATMENTTYPESROV1 = AttributesEnum.TreatmentTypesROV1.index();

    /**
     * This is the default constructor (do not remove).
     */
    public ProductContractVORowImpl() {
    
    }
}

