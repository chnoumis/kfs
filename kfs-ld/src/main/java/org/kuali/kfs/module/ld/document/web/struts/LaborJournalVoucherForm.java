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
package org.kuali.kfs.module.ld.document.web.struts;

import org.apache.commons.lang.StringUtils;
import org.kuali.kfs.coa.businessobject.BalanceType;
import org.kuali.kfs.coa.service.BalanceTypeService;
import org.kuali.kfs.fp.document.JournalVoucherDocument;
import org.kuali.kfs.fp.document.web.struts.JournalVoucherForm;
import org.kuali.kfs.module.ld.LaborConstants.JournalVoucherOffsetType;
import org.kuali.kfs.module.ld.document.LaborJournalVoucherDocument;
import org.kuali.kfs.sys.KFSConstants;
import org.kuali.kfs.sys.businessobject.SourceAccountingLine;
import org.kuali.kfs.sys.context.SpringContext;

/**
 * Struts Action Form for the Labor Journal Voucher Document.
 */
public class LaborJournalVoucherForm extends JournalVoucherForm {

    protected String originalOffsetTypeCode = JournalVoucherOffsetType.NO_OFFSET.typeCode;

    /**
     * Constructs a JournalVoucherForm instance.
     */
    public LaborJournalVoucherForm() {
        super();
    }

    @Override
    protected String getDefaultDocumentTypeName() {
        return "LLJV";
    }

    /**
     * Populates source a accounting line encumbrance code (D, R or null) for a given source accounting line 
     * 
     * @see org.kuali.kfs.fp.document.web.struts.JournalVoucherForm#populateSourceAccountingLineEncumbranceCode(org.kuali.kfs.sys.businessobject.SourceAccountingLine)
     */
    @Override
    protected void populateSourceAccountingLineEncumbranceCode(SourceAccountingLine sourceLine) {
        BalanceType selectedBalanceType = getSelectedBalanceType();
        if (selectedBalanceType != null && StringUtils.isNotBlank(selectedBalanceType.getCode())) {
            sourceLine.setBalanceTyp(selectedBalanceType);
            sourceLine.setBalanceTypeCode(selectedBalanceType.getCode());

            // KFSMI-7163 remove the default encumbrance code
            // no more default encumbrance code
//            if (selectedBalanceType.isFinBalanceTypeEncumIndicator()) {
//                if (StringUtils.isBlank(sourceLine.getEncumbranceUpdateCode()) || !KFSConstants.ENCUMB_UPDT_DOCUMENT_CD.equals(sourceLine.getEncumbranceUpdateCode())) {
//                    sourceLine.setEncumbranceUpdateCode(KFSConstants.ENCUMB_UPDT_REFERENCE_DOCUMENT_CD);
//                }
//            }
//            else {
//                sourceLine.setEncumbranceUpdateCode(null);
//            }
        }
        else {
            // it's the first time in, the form will be empty the first time in set up default selection value
            selectedBalanceType = SpringContext.getBean(BalanceTypeService.class).getBalanceTypeByCode(KFSConstants.BALANCE_TYPE_ACTUAL);
            setSelectedBalanceType(selectedBalanceType);
            setOriginalBalanceType(selectedBalanceType.getCode());

            sourceLine.setEncumbranceUpdateCode(null);
        }
    }

    /**
     * Returns the journal voucher document associated with this form.
     * 
     * @return Returns the journalVoucherDocument.
     */
    public JournalVoucherDocument getJournalVoucherDocument() {
        return (LaborJournalVoucherDocument) getTransactionalDocument();
    }

    /**
     * Sets the journal voucher document associated with this form.
     * 
     * @param journalVoucherDocument The journalVoucherDocument to set.
     */
    public void setJournalVoucherDocument(JournalVoucherDocument journalVoucherDocument) {
        setDocument(journalVoucherDocument);
    }

    /**
     * Gets the originalOffsetTypeCode attribute.
     * 
     * @return Returns the originalOffsetTypeCode.
     */
    public String getOriginalOffsetTypeCode() {
        return originalOffsetTypeCode;
    }

    /**
     * Sets the originalOffsetTypeCode attribute value.
     * 
     * @param originalOffsetTypeCode The originalOffsetTypeCode to set.
     */
    public void setOriginalOffsetTypeCode(String originalOffsetTypeCode) {
        this.originalOffsetTypeCode = originalOffsetTypeCode;
    }

    /**
     * Configure map for optional accounting line quickfinders.
     * 
     * @see org.kuali.kfs.sys.web.struts.KualiAccountingDocumentFormBase#getForcedLookupOptionalFields()
     */
    
//    public Map getForcedLookupOptionalFields() {
//        Map retval = super.getForcedLookupOptionalFields();

//        String lookupField = KFSPropertyConstants.POSITION_NUMBER;
//        retval.put(KFSPropertyConstants.POSITION_NUMBER, lookupField + ";" + PositionData.class.getName());

//        lookupField = KFSPropertyConstants.PERSON_PAYROLL_IDENTIFIER;
//        retval.put(KFSPropertyConstants.EMPLID, lookupField + ";" + Person.class.getName());

//        return retval;
//    }
}

