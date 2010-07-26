/*
 * Copyright 2010 The Kuali Foundation.
 * 
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl1.php
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.kfs.module.endow.document.validation.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.kfs.coa.businessobject.Chart;
import org.kuali.kfs.coa.businessobject.ObjectCode;
import org.kuali.kfs.coa.businessobject.ObjectLevel;
import org.kuali.kfs.coa.service.ChartService;
import org.kuali.kfs.coa.service.ObjectCodeService;
import org.kuali.kfs.coa.service.ObjectLevelService;
import org.kuali.kfs.module.endow.EndowConstants;
import org.kuali.kfs.module.endow.EndowKeyConstants;
import org.kuali.kfs.module.endow.EndowPropertyConstants;
import org.kuali.kfs.module.endow.businessobject.EndowmentAccountingLine;
import org.kuali.kfs.module.endow.businessobject.SourceEndowmentAccountingLine;
import org.kuali.kfs.module.endow.document.EndowmentAccountingLinesDocument;
import org.kuali.kfs.module.endow.document.EndowmentAccountingLinesDocumentBase;
import org.kuali.kfs.module.endow.document.validation.AddEndowmentAccountingLineRule;
import org.kuali.kfs.module.endow.document.validation.DeleteEndowmentAccountingLineRule;
import org.kuali.kfs.sys.context.SpringContext;
import org.kuali.rice.kns.document.Document;
import org.kuali.rice.kns.util.GlobalVariables;
import org.kuali.rice.kns.util.ObjectUtils;

public class EndowmentAccountingLinesDocumentBaseRules extends EndowmentTransactionLinesDocumentBaseRules implements AddEndowmentAccountingLineRule<EndowmentAccountingLinesDocument, EndowmentAccountingLine>, DeleteEndowmentAccountingLineRule<EndowmentAccountingLinesDocument, EndowmentAccountingLine> {

    /**
     * @see org.kuali.kfs.module.endow.document.validation.AddEndowmentAccountingLineRule#processAddEndowmentAccountingLineRules(org.kuali.kfs.module.endow.document.EndowmentAccountingLinesDocument,
     *      org.kuali.kfs.module.endow.businessobject.EndowmentAccountingLine)
     */
    public boolean processAddEndowmentAccountingLineRules(EndowmentAccountingLinesDocument endowmentAccountingLinesDocument, EndowmentAccountingLine endowmentAccountingLine) {
        boolean isValid = true;
        isValid &= validateAccountingLine(endowmentAccountingLinesDocument, endowmentAccountingLine, -1);
        return isValid;
    }

    /**
     * @see org.kuali.kfs.module.endow.document.validation.DeleteEndowmentAccountingLineRule#processDeleteAccountingLineRules(org.kuali.kfs.module.endow.document.EndowmentAccountingLinesDocument,
     *      org.kuali.kfs.module.endow.businessobject.EndowmentAccountingLine)
     */
    public boolean processDeleteAccountingLineRules(EndowmentAccountingLinesDocument EndowmentAccountingLinesDocument, EndowmentAccountingLine EndowmentAccountingLine) {
        // TODO Auto-generated method stub
        return true;
    }

    /**
     * Validates the given accounting Line.
     * 
     * @param accountingLinesDocument
     * @param accountingLine
     * @param index
     * @return true if valid, false otherwise
     */
    protected boolean validateAccountingLine(EndowmentAccountingLinesDocument accountingLinesDocument, EndowmentAccountingLine accountingLine, int index) {
        boolean isValid = true;

        // validate chart code
        if (isChartCodeEmpty(accountingLine, index)) {
            return false;
        }

        if (!validateChartCode(accountingLine, index)) {
            return false;
        }

        isValid &= validateChartCodeIsActive(accountingLine, index);

        // validate object code
        if (isObjectCodeEmpty(accountingLine, index)) {
            return false;
        }

        if (!validateObjectCode(accountingLine, index)) {
            return false;
        }

        isValid &= validateObjectCodeIsActive(accountingLine, index);
        isValid &= validateObjectCodeObjectConsolidation(accountingLine, index);
        isValid &= validateObjectCodeType(accountingLine, index);

        return isValid;
    }


    /**
     * Gets the prefix for an accounting line error.
     * 
     * @param line
     * @param index
     * @return the prefix
     */
    public String getAcctLineErrorPrefix(EndowmentAccountingLine line, int index) {
        String ERROR_PREFIX = null;
        if (line instanceof SourceEndowmentAccountingLine) {
            if (index == -1) {
                ERROR_PREFIX = EndowPropertyConstants.SOURCE_ACCT_LINE_PREFIX;
            }
            else {
                ERROR_PREFIX = EndowPropertyConstants.EXISTING_SOURCE_ACCT_LINE_PREFIX + "[" + index + "].";
            }
        }
        else {
            if (index == -1) {
                ERROR_PREFIX = EndowPropertyConstants.TARGET_ACCT_LINE_PREFIX;
            }
            else {
                ERROR_PREFIX = EndowPropertyConstants.EXISTING_TARGET_ACCT_LINE_PREFIX + "[" + index + "].";
            }
        }
        return ERROR_PREFIX;

    }

    /**
     * Checks if the chart code is empty.
     * 
     * @param line
     * @param index
     * @return true if valid, false otherwise
     */
    protected boolean isChartCodeEmpty(EndowmentAccountingLine line, int index) {
        if (StringUtils.isBlank(line.getFinancialObjectCode())) {
            putFieldError(getAcctLineErrorPrefix(line, index) + EndowPropertyConstants.ENDOWMENT_ACCOUNTING_LINE_CHART_CD, EndowKeyConstants.EndowmentTransactionDocumentConstants.ERROR_ACCT_LINE_CHART_CODE_REQUIRED);
            return true;
        }
        else
            return false;
    }

    /**
     * Validates that the chart code exists in the database.
     * 
     * @param line
     * @param index
     * @return true if valid, false otherwise
     */
    protected boolean validateChartCode(EndowmentAccountingLine line, int index) {
        boolean isValid = true;

        String chartOfAccountsCode = line.getChartOfAccountsCode();

        Chart chartCode = SpringContext.getBean(ChartService.class).getByPrimaryId(chartOfAccountsCode);

        if (ObjectUtils.isNull(chartCode)) {
            isValid = false;
            putFieldError(getAcctLineErrorPrefix(line, index) + EndowPropertyConstants.ENDOWMENT_ACCOUNTING_LINE_CHART_CD, EndowKeyConstants.EndowmentTransactionDocumentConstants.ERROR_ACCT_LINE_CHART_CODE_INVALID);
        }

        return isValid;
    }

    /**
     * Validates if the chart code is Active.
     * 
     * @param line
     * @param index
     * @return true of active, false otherwise
     */
    protected boolean validateChartCodeIsActive(EndowmentAccountingLine line, int index) {
        boolean isValid = true;

        String chartOfAccountsCode = line.getChartOfAccountsCode();

        Chart chartCode = SpringContext.getBean(ChartService.class).getByPrimaryId(chartOfAccountsCode);

        if (ObjectUtils.isNotNull(chartCode)) {
            if (!chartCode.isActive()) {
                isValid = false;
                putFieldError(getAcctLineErrorPrefix(line, index) + EndowPropertyConstants.ENDOWMENT_ACCOUNTING_LINE_CHART_CD, EndowKeyConstants.EndowmentTransactionDocumentConstants.ERROR_ACCT_LINE_CHART_CODE_INACTIVE);
            }
        }

        return isValid;
    }

    /**
     * Checks if the object code is empty.
     * 
     * @param line
     * @param index
     * @return true if empty, false otherwise
     */
    protected boolean isObjectCodeEmpty(EndowmentAccountingLine line, int index) {

        if (StringUtils.isBlank(line.getFinancialObjectCode())) {
            putFieldError(getAcctLineErrorPrefix(line, index) + EndowPropertyConstants.ENDOWMENT_ACCOUNTING_LINE_OBJECT_CD, EndowKeyConstants.EndowmentTransactionDocumentConstants.ERROR_ACCT_LINE_OBJECT_CODE_REQUIRED);
            return true;
        }
        else
            return false;
    }

    /**
     * Validates that the object code exists.
     * 
     * @param line
     * @param index
     * @return true if valid, false otherwise
     */
    protected boolean validateObjectCode(EndowmentAccountingLine line, int index) {
        boolean isValid = true;

        String financialObjectCode = line.getFinancialObjectCode();
        String chartOfAccountsCode = line.getChartOfAccountsCode();

        ObjectCode objectCode = SpringContext.getBean(ObjectCodeService.class).getByPrimaryIdForCurrentYear(chartOfAccountsCode, financialObjectCode);

        if (ObjectUtils.isNull(objectCode)) {
            isValid = false;
            putFieldError(getAcctLineErrorPrefix(line, index) + EndowPropertyConstants.ENDOWMENT_ACCOUNTING_LINE_OBJECT_CD, EndowKeyConstants.EndowmentTransactionDocumentConstants.ERROR_ACCT_LINE_OBJECT_CODE_INVALID);
        }

        return isValid;
    }

    /**
     * Validates that the object code is active.
     * 
     * @param line
     * @param index
     * @return true if active, false otherwise
     */
    protected boolean validateObjectCodeIsActive(EndowmentAccountingLine line, int index) {
        boolean isValid = true;

        String financialObjectCode = line.getFinancialObjectCode();
        String chartOfAccountsCode = line.getChartOfAccountsCode();

        ObjectCode objectCode = SpringContext.getBean(ObjectCodeService.class).getByPrimaryIdForCurrentYear(chartOfAccountsCode, financialObjectCode);

        if (ObjectUtils.isNotNull(objectCode)) {
            if (!objectCode.isActive()) {
                isValid = false;
                putFieldError(getAcctLineErrorPrefix(line, index) + EndowPropertyConstants.ENDOWMENT_ACCOUNTING_LINE_OBJECT_CD, EndowKeyConstants.EndowmentTransactionDocumentConstants.ERROR_ACCT_LINE_OBJECT_CODE_INACTIVE);
            }
        }

        return isValid;
    }

    /**
     * Validate that the object code does not represent assets, liabilities or funds balances (as indicated by the object code's
     * object consolidation).
     * 
     * @param line
     * @param index
     * @return true if valid, false otherwise
     */
    protected boolean validateObjectCodeObjectConsolidation(EndowmentAccountingLine line, int index) {
        boolean isValid = true;

        String financialObjectCode = line.getFinancialObjectCode();
        String chartOfAccountsCode = line.getChartOfAccountsCode();

        ObjectCode objectCode = SpringContext.getBean(ObjectCodeService.class).getByPrimaryIdForCurrentYear(chartOfAccountsCode, financialObjectCode);
        ObjectLevel objectLevel = SpringContext.getBean(ObjectLevelService.class).getByPrimaryId(chartOfAccountsCode, objectCode.getFinancialObjectLevelCode());

        if (ObjectUtils.isNotNull(objectLevel)) {
            String consolidatedObjectCode = objectCode.getFinancialObjectLevel().getFinancialConsolidationObjectCode();

            if (EndowConstants.ConsolidatedObjectCode.ASSETS.equalsIgnoreCase(consolidatedObjectCode) || EndowConstants.ConsolidatedObjectCode.LIABILITIES.equalsIgnoreCase(consolidatedObjectCode) || EndowConstants.ConsolidatedObjectCode.FUND_BALANCE.equalsIgnoreCase(consolidatedObjectCode)) {
                isValid &= false;
                putFieldError(getAcctLineErrorPrefix(line, index) + EndowPropertyConstants.ENDOWMENT_ACCOUNTING_LINE_OBJECT_CD, EndowKeyConstants.EndowmentTransactionDocumentConstants.ERROR_ACCT_LINE_OBJECT_CODE_NOT_ASSET_LIABILITY_OR_FUND_BAL);
            }
        }

        return isValid;
    }

    /**
     * Validates that Object codes belonging to the object types of Expense not Expenditure and Income not Cash may not be used on a
     * Transfer of Funds document.
     * 
     * @param line
     * @param index
     * @return true if valid, false otherwise
     */
    protected boolean validateObjectCodeType(EndowmentAccountingLine line, int index) {
        boolean isValid = true;

        String financialObjectCode = line.getFinancialObjectCode();
        String chartOfAccountsCode = line.getChartOfAccountsCode();

        ObjectCode objectCode = SpringContext.getBean(ObjectCodeService.class).getByPrimaryIdForCurrentYear(chartOfAccountsCode, financialObjectCode);
        String objectType = objectCode.getFinancialObjectTypeCode();

        if (StringUtils.isNotBlank(objectType)) {

            if (EndowConstants.ObjectTypeCode.EXPENSE_NOT_EXPENDITURE.equalsIgnoreCase(objectType) || EndowConstants.ObjectTypeCode.INCOME_NOT_CASH.equalsIgnoreCase(objectType)) {
                isValid &= false;
                putFieldError(getAcctLineErrorPrefix(line, index) + EndowPropertyConstants.ENDOWMENT_ACCOUNTING_LINE_OBJECT_CD, EndowKeyConstants.EndowmentTransactionDocumentConstants.ERROR_ACCT_LINE_OBJECT_TYPE_NOT_VALID);
            }
        }

        return isValid;
    }

    /**
     * Validates that the document has at least one accounting line.
     * 
     * @param document
     * @param isSource
     * @return true if valid, false otherwise
     */
    protected boolean validateAccountingLinesSizeGreaterThanZero(EndowmentAccountingLinesDocument document, boolean isSource) {
        boolean isValid = true;
        if (isSource) {
            isValid &= (document.getSourceAccountingLines().size() > 0);
        }
        else {
            isValid &= (document.getTargetAccountingLines().size() > 0);
        }

        if (!isValid) {
            putFieldError(EndowConstants.ACCOUNTING_LINE_ERRORS, EndowKeyConstants.EndowmentTransactionDocumentConstants.ERROR_ACCT_LINE_COUNT_INSUFFICIENT);
        }
        return isValid;
    }

    /**
     * @see org.kuali.kfs.module.endow.document.validation.impl.EndowmentTransactionLinesDocumentBaseRules#processCustomRouteDocumentBusinessRules(org.kuali.rice.kns.document.Document)
     */
    @Override
    protected boolean processCustomRouteDocumentBusinessRules(Document document) {
        boolean isValid = super.processCustomRouteDocumentBusinessRules(document);

        EndowmentAccountingLinesDocumentBase endowmentAccountingLinesDocumentBase = null;

        if (isValid) {
            endowmentAccountingLinesDocumentBase = (EndowmentAccountingLinesDocumentBase) document;

            // validate source Accounting lines
            if (endowmentAccountingLinesDocumentBase.getSourceAccountingLines() != null) {
                for (int i = 0; i < endowmentAccountingLinesDocumentBase.getSourceAccountingLines().size(); i++) {
                    EndowmentAccountingLine accountingLine = endowmentAccountingLinesDocumentBase.getSourceAccountingLines().get(i);
                    validateAccountingLine(endowmentAccountingLinesDocumentBase, accountingLine, i);
                }
            }

            // validate target Accounting lines
            if (endowmentAccountingLinesDocumentBase.getTargetAccountingLines() != null) {
                for (int i = 0; i < endowmentAccountingLinesDocumentBase.getTargetAccountingLines().size(); i++) {
                    EndowmentAccountingLine accountingLine = endowmentAccountingLinesDocumentBase.getTargetAccountingLines().get(i);
                    validateAccountingLine(endowmentAccountingLinesDocumentBase, accountingLine, i);
                }
            }

        }

        return GlobalVariables.getMessageMap().getErrorCount() == 0;
    }
}
