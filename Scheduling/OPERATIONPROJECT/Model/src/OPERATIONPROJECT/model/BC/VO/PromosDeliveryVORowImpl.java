package OPERATIONPROJECT.model.BC.VO;

import OPERATIONPROJECT.model.BC.EO.PromosDeliveryEOImpl;

import OPERATIONPROJECT.model.BC.VO.common.PromosDeliveryVORow;

import java.sql.Timestamp;

import oracle.jbo.RowIterator;
import oracle.jbo.RowSet;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.domain.DBSequence;
import oracle.jbo.domain.Date;
import oracle.jbo.server.ViewRowImpl;
import oracle.jbo.ViewObject;
import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.server.ApplicationModuleImpl;
import oracle.jbo.ApplicationModule;
import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
 import OPERATIONPROJECT.model.BC.AM.AppModuleAMImpl;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.jbo.client.Configuration;



// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Nov 17 07:26:01 EET 2020
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class PromosDeliveryVORowImpl extends ViewRowImpl implements PromosDeliveryVORow {


    public static final int ENTITY_PROMOSDELIVERYEO = 0;

    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    protected enum AttributesEnum {
        Id,
        ProductName,
        ProductSku,
        AssignDate,
        CardNo,
        AreaId,
        Quantity,
        assignPromos,
        DeliveryFlag,
        NextDeliveryDate,
        PromoPrice,
        PromosPrice,
        PromosDeliveryVO_AreaContractVOLookup,
        PromosLookupROV1;
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


    public static final int ID = AttributesEnum.Id.index();
    public static final int PRODUCTNAME = AttributesEnum.ProductName.index();
    public static final int PRODUCTSKU = AttributesEnum.ProductSku.index();
    public static final int ASSIGNDATE = AttributesEnum.AssignDate.index();
    public static final int CARDNO = AttributesEnum.CardNo.index();
    public static final int AREAID = AttributesEnum.AreaId.index();
    public static final int QUANTITY = AttributesEnum.Quantity.index();
    public static final int ASSIGNPROMOS = AttributesEnum.assignPromos.index();
    public static final int DELIVERYFLAG = AttributesEnum.DeliveryFlag.index();
    public static final int NEXTDELIVERYDATE = AttributesEnum.NextDeliveryDate.index();
    public static final int PROMOPRICE = AttributesEnum.PromoPrice.index();
    public static final int PROMOSPRICE = AttributesEnum.PromosPrice.index();
    public static final int PROMOSDELIVERYVO_AREACONTRACTVOLOOKUP =
        AttributesEnum.PromosDeliveryVO_AreaContractVOLookup.index();
    public static final int PROMOSLOOKUPROV1 = AttributesEnum.PromosLookupROV1.index();

    /**
     * This is the default constructor (do not remove).
     */
    public PromosDeliveryVORowImpl() {
    }

    /**
     * Gets PromosDeliveryEO entity object.
     * @return the PromosDeliveryEO
     */
    public PromosDeliveryEOImpl getPromosDeliveryEO() {
        return (PromosDeliveryEOImpl) getEntity(ENTITY_PROMOSDELIVERYEO);
    }

    /**
     * Gets the attribute value for ID using the alias name Id.
     * @return the ID
     */
    public DBSequence getId() {
        return (DBSequence) getAttributeInternal(ID);
    }

    /**
     * Sets <code>value</code> as attribute value for ID using the alias name Id.
     * @param value value to set the ID
     */
    public void setId(DBSequence value) {
        setAttributeInternal(ID, value);
    }

    /**
     * Gets the attribute value for PRODUCT_NAME using the alias name ProductName.
     * @return the PRODUCT_NAME
     */
    public String getProductName() {
        return (String) getAttributeInternal(PRODUCTNAME);
    }

    /**
     * Sets <code>value</code> as attribute value for PRODUCT_NAME using the alias name ProductName.
     * @param value value to set the PRODUCT_NAME
     */
    public void setProductName(String value) {
        setAttributeInternal(PRODUCTNAME, value);
    }

    /**
     * Gets the attribute value for PRODUCT_SKU using the alias name ProductSku.
     * @return the PRODUCT_SKU
     */
    public String getProductSku() {
        return (String) getAttributeInternal(PRODUCTSKU);
    }

    /**
     * Sets <code>value</code> as attribute value for PRODUCT_SKU using the alias name ProductSku.
     * @param value value to set the PRODUCT_SKU
     */
    public void UpdatePromoPrice(String PromoSku) {
        ViewObjectImpl promosLookup = (ViewObjectImpl) getApplicationModule().findViewObject("PromosLookupVO");
        // promosLookup.setRangeSize(-1);
         Row[] AllRows = promosLookup.getAllRowsInRange();
         System.out.println("Rows Length : "+AllRows.length);
         
          //    RowSetIterator iterator=promosLookup.createRowSetIterator(null);
         for (Row r : AllRows){
             if((String)r.getAttribute("ProductId")==PromoSku) {
                 setAttributeInternal(PROMOPRICE, r.getAttribute("SellingPrice"));
                 System.out.println("Selling : "+r.getAttribute("SellingPrice"));
                 break;
             }
         }
        
    }
    public void setProductSku(String value) {
//       
      
             
        setAttributeInternal(PRODUCTSKU, value);
       // UpdatePromoPrice(value);
    }

    /**
     * Gets the attribute value for ASSIGN_DATE using the alias name AssignDate.
     * @return the ASSIGN_DATE
     */
    public Date getAssignDate() {
        return (Date) getAttributeInternal(ASSIGNDATE);
    }

    /**
     * Sets <code>value</code> as attribute value for ASSIGN_DATE using the alias name AssignDate.
     * @param value value to set the ASSIGN_DATE
     */
    public void setAssignDate(Date value) {
        setAttributeInternal(ASSIGNDATE, value);
    }

    /**
     * Gets the attribute value for CARD_NO using the alias name CardNo.
     * @return the CARD_NO
     */
    public Long getCardNo() {
        return (Long) getAttributeInternal(CARDNO);
    }

    /**
     * Sets <code>value</code> as attribute value for CARD_NO using the alias name CardNo.
     * @param value value to set the CARD_NO
     */
    public void setCardNo(Long value) {
        setAttributeInternal(CARDNO, value);
    }

    /**
     * Gets the attribute value for AREA_ID using the alias name AreaId.
     * @return the AREA_ID
     */
    public Long getAreaId() {
        return (Long) getAttributeInternal(AREAID);
    }

    /**
     * Sets <code>value</code> as attribute value for AREA_ID using the alias name AreaId.
     * @param value value to set the AREA_ID
     */
    public void setAreaId(Long value) {
        setAttributeInternal(AREAID, value);
    }

    /**
     * Gets the attribute value for QUANTITY using the alias name Quantity.
     * @return the QUANTITY
     */
    public Long getQuantity() {
        return (Long) getAttributeInternal(QUANTITY);
    }

    /**
     * Sets <code>value</code> as attribute value for QUANTITY using the alias name Quantity.
     * @param value value to set the QUANTITY
     */
    public void setQuantity(Long value) {
        setAttributeInternal(QUANTITY, value);
    }

    /**
     * Gets the attribute value for the calculated attribute assignPromos.
     * @return the assignPromos
     */
    public Boolean getassignPromos() {
        return (Boolean) getAttributeInternal(ASSIGNPROMOS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute assignPromos.
     * @param value value to set the  assignPromos
     */
    public void setassignPromos(Boolean value) {
        setAttributeInternal(ASSIGNPROMOS, value);
    }

    /**
     * Gets the attribute value for DELIVERY_FLAG using the alias name DeliveryFlag.
     * @return the DELIVERY_FLAG
     */
    public Long getDeliveryFlag() {
        return (Long) getAttributeInternal(DELIVERYFLAG);
    }

    /**
     * Sets <code>value</code> as attribute value for DELIVERY_FLAG using the alias name DeliveryFlag.
     * @param value value to set the DELIVERY_FLAG
     */
    public void setDeliveryFlag(Long value) {
        setAttributeInternal(DELIVERYFLAG, value);
    }

    /**
     * Gets the attribute value for NEXT_DELIVERY_DATE using the alias name NextDeliveryDate.
     * @return the NEXT_DELIVERY_DATE
     */
    public Timestamp getNextDeliveryDate() {
        return (Timestamp) getAttributeInternal(NEXTDELIVERYDATE);
    }

    /**
     * Sets <code>value</code> as attribute value for NEXT_DELIVERY_DATE using the alias name NextDeliveryDate.
     * @param value value to set the NEXT_DELIVERY_DATE
     */
    public void setNextDeliveryDate(Timestamp value) {
        setAttributeInternal(NEXTDELIVERYDATE, value);
    }

    /**
     * Gets the attribute value for PRICE using the alias name PromoPrice.
     * @return the PRICE
     */
    public Long getPromoPrice() {
        return (Long) getAttributeInternal(PROMOPRICE);
    }

    /**
     * Sets <code>value</code> as attribute value for PRICE using the alias name PromoPrice.
     * @param value value to set the PRICE
     */
    public void setPromoPrice(Long value) {
        setAttributeInternal(PROMOPRICE, value);
    }

    /**
     * Gets the associated <code>RowIterator</code> using master-detail link PromosPrice.
     */
    public RowIterator getPromosPrice() {
        return (RowIterator) getAttributeInternal(PROMOSPRICE);
    }

    /**
     * Gets the view accessor <code>RowSet</code> PromosDeliveryVO_AreaContractVOLookup.
     */
    public RowSet getPromosDeliveryVO_AreaContractVOLookup() {
        return (RowSet) getAttributeInternal(PROMOSDELIVERYVO_AREACONTRACTVOLOOKUP);
    }

    /**
     * Gets the view accessor <code>RowSet</code> PromosLookupROV1.
     */
    public RowSet getPromosLookupROV1() {
        return (RowSet) getAttributeInternal(PROMOSLOOKUPROV1);
    }
}
