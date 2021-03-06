/*
 * The Kuali Financial System, a comprehensive financial management system for higher education.
 * 
 * Copyright 2005-2014 The Kuali Foundation
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.kuali.kfs.module.purap.businessobject;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kuali.kfs.module.purap.document.AccountsPayableDocumentBase;
import org.kuali.kfs.module.purap.document.LineItemReceivingDocument;
import org.kuali.kfs.module.purap.document.PurchaseOrderDocument;
import org.kuali.kfs.module.purap.exception.PurError;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.rice.krad.util.ObjectUtils;

/**
 * @author Kuali Nervous System Team (kualidev@oncourse.iu.edu)
 */
public class LineItemReceivingItem extends ReceivingItemBase {

    private KualiDecimal itemOrderedQuantity;

    // not stored in db
    private KualiDecimal itemReceivedPriorQuantity;
    private KualiDecimal itemReceivedToBeQuantity;

    private LineItemReceivingDocument lineItemReceivingDocument;

    private static org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(AccountsPayableDocumentBase.class);

    /**
     * Default constructor.
     */
    public LineItemReceivingItem() {
    }

    public LineItemReceivingItem(LineItemReceivingDocument rld) {
        this.setDocumentNumber(rld.getDocumentNumber());
        this.setItemReceivedTotalQuantity(KualiDecimal.ZERO);
        this.setItemReturnedTotalQuantity(KualiDecimal.ZERO);
        this.setItemDamagedTotalQuantity(KualiDecimal.ZERO);
        this.setItemOriginalReceivedTotalQuantity(KualiDecimal.ZERO);
        this.setItemOriginalReturnedTotalQuantity(KualiDecimal.ZERO);
        this.setItemOriginalDamagedTotalQuantity(KualiDecimal.ZERO);
    }

    public LineItemReceivingItem(PurchaseOrderItem poi, LineItemReceivingDocument rld) {

        this.setDocumentNumber(rld.getDocumentNumber());
        this.setItemTypeCode(poi.getItemTypeCode());
        this.setPurchaseOrderIdentifier(rld.getPurchaseOrderIdentifier());

        this.setItemLineNumber(poi.getItemLineNumber());
        this.setItemCatalogNumber(poi.getItemCatalogNumber());
        this.setItemDescription(poi.getItemDescription());

        this.setItemOrderedQuantity(poi.getItemQuantity());
        this.setItemUnitOfMeasureCode(poi.getItemUnitOfMeasureCode());

        // TODO: Chris - look into this it appears this is null rather than zero on amendment, find out why!
        if (ObjectUtils.isNull(poi.getItemReceivedTotalQuantity())) {
            this.setItemReceivedPriorQuantity(KualiDecimal.ZERO);
        }
        else {
            this.setItemReceivedPriorQuantity(poi.getItemReceivedTotalQuantity());
        }

        this.setItemReceivedToBeQuantity(this.getItemOrderedQuantity().subtract(this.getItemReceivedPriorQuantity()));

        // should determine whether this is prefilled be based on the parameter that allows loading from po
        this.setItemReceivedTotalQuantity(KualiDecimal.ZERO);

        this.setItemReturnedTotalQuantity(KualiDecimal.ZERO);
        this.setItemDamagedTotalQuantity(KualiDecimal.ZERO);

        this.setItemOriginalReceivedTotalQuantity(KualiDecimal.ZERO);
        this.setItemOriginalReturnedTotalQuantity(KualiDecimal.ZERO);
        this.setItemOriginalDamagedTotalQuantity(KualiDecimal.ZERO);

        // not added
        this.setItemReasonAddedCode(null);
    }

    /**
     * Retreives a purchase order item by inspecting the item type to see if its above the line or below the line and returns the
     * appropriate type.
     * 
     * @return - purchase order item
     */
    public PurchaseOrderItem getPurchaseOrderItem() {
        if (ObjectUtils.isNotNull(this.getLineItemReceivingDocument())) {
            if (ObjectUtils.isNull(this.getLineItemReceivingDocument())) {
                this.refreshReferenceObject("lineItemReceivingDocument");
            }
        }
        // ideally we should do this a different way - maybe move it all into the service or save this info somehow (make sure and
        // update though)
        if (getLineItemReceivingDocument() != null) {
            PurchaseOrderDocument po = getLineItemReceivingDocument().getPurchaseOrderDocument();
            PurchaseOrderItem poi = null;
            if (this.getItemType().isLineItemIndicator()) {
                List<PurchaseOrderItem> items = po.getItems();
                poi = items.get(this.getItemLineNumber().intValue() - 1);

                // throw error if line numbers don't match
            }
            if (poi != null) {
                return poi;
            }
            else {
                // LOG.debug("getPurchaseOrderItem() Returning null because PurchaseOrderItem object for line number" +
                // getItemLineNumber() + "or itemType " + getItemTypeCode() + " is null");
                return null;
            }
        }
        else {
            LOG.error("getPurchaseOrderItem() Returning null because paymentRequest object is null");
            throw new PurError("Receiving Line Object in Purchase Order item line number " + getItemLineNumber() + "or itemType " + getItemTypeCode() + " is null");
        }
    }

    /**
     * Gets the itemOrderedQuantity attribute.
     * 
     * @return Returns the itemOrderedQuantity
     */
    public KualiDecimal getItemOrderedQuantity() {
        return itemOrderedQuantity;
    }

    /**
     * Sets the itemOrderedQuantity attribute.
     * 
     * @param itemOrderedQuantity The itemOrderedQuantity to set.
     */
    public void setItemOrderedQuantity(KualiDecimal itemOrderedQuantity) {
        this.itemOrderedQuantity = itemOrderedQuantity;
    }

    /**
     * Gets the LineItemReceivingDocument attribute.
     * 
     * @return Returns the LineItemReceivingDocument.
     */
    public LineItemReceivingDocument getLineItemReceivingDocument() {
        return lineItemReceivingDocument;
    }

    /**
     * Sets the LineItemReceivingDocument attribute value.
     * 
     * @param LineItemReceivingDocument The LineItemReceivingDocument to set.
     * @deprecated
     */
    public void setLineItemReceivingDocument(LineItemReceivingDocument lineItemReceivingDocument) {
        this.lineItemReceivingDocument = lineItemReceivingDocument;
    }

    public KualiDecimal getItemReceivedPriorQuantity() {
        if (ObjectUtils.isNull(itemReceivedPriorQuantity)) {
            setItemReceivedPriorQuantity(getPurchaseOrderItem().getItemReceivedTotalQuantity());
        }
        return itemReceivedPriorQuantity;
    }

    public void setItemReceivedPriorQuantity(KualiDecimal itemReceivedPriorQuantity) {

        this.itemReceivedPriorQuantity = itemReceivedPriorQuantity;
    }

    public KualiDecimal getItemReceivedToBeQuantity() {
        // lazy loaded
        KualiDecimal toBeQuantity = this.getItemOrderedQuantity().subtract(getItemReceivedPriorQuantity());
        if (toBeQuantity.isNegative()) {
            toBeQuantity = KualiDecimal.ZERO;
        }
        setItemReceivedToBeQuantity(toBeQuantity);

        return itemReceivedToBeQuantity;
    }

    public void setItemReceivedToBeQuantity(KualiDecimal itemReceivedToBeQuantity) {
        this.itemReceivedToBeQuantity = itemReceivedToBeQuantity;
    }

    public boolean isOrderedItem() {
        return StringUtils.isEmpty(getItemReasonAddedCode());
    }

}
