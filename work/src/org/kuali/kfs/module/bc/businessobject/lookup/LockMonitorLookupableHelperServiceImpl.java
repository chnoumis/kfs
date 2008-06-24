/*
 * Copyright 2007 The Kuali Foundation.
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
package org.kuali.kfs.module.bc.businessobject.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.kuali.core.bo.BusinessObject;
import org.kuali.core.bo.user.UniversalUser;
import org.kuali.core.exceptions.UserNotFoundException;
import org.kuali.core.lookup.CollectionIncomplete;
import org.kuali.core.lookup.KualiLookupableHelperServiceImpl;
import org.kuali.core.service.KualiConfigurationService;
import org.kuali.core.service.UniversalUserService;
import org.kuali.core.util.GlobalVariables;
import org.kuali.core.util.UrlFactory;
import org.kuali.kfs.sys.KFSConstants;
import org.kuali.kfs.sys.context.SpringContext;
import org.kuali.kfs.module.bc.BCConstants;
import org.kuali.kfs.module.bc.BCKeyConstants;
import org.kuali.kfs.module.bc.BCPropertyConstants;
import org.kuali.kfs.module.bc.businessobject.BudgetConstructionFundingLock;
import org.kuali.kfs.module.bc.businessobject.BudgetConstructionHeader;
import org.kuali.kfs.module.bc.businessobject.BudgetConstructionLockSummary;
import org.kuali.kfs.module.bc.businessobject.BudgetConstructionPosition;
import org.kuali.kfs.module.bc.businessobject.PendingBudgetConstructionAppointmentFunding;
import org.kuali.kfs.module.bc.document.service.LockService;
import org.kuali.rice.kns.util.KNSConstants;

/**
 * Implements custom search routine to find the current budget locks and build up the result List. Set an unlock URL for each lock.
 */
public class LockMonitorLookupableHelperServiceImpl extends KualiLookupableHelperServiceImpl {
    private KualiConfigurationService kualiConfigurationService;
    private UniversalUserService universalUserService;

    /**
     * @see org.kuali.core.lookup.AbstractLookupableHelperServiceImpl#getSearchResults(java.util.Map)
     */
    @Override
    public List<? extends BusinessObject> getSearchResults(Map<String, String> fieldValues) {
        setBackLocation(fieldValues.get(KFSConstants.BACK_LOCATION));
        setDocFormKey(fieldValues.get(KFSConstants.DOC_FORM_KEY));
        setReferencesToRefresh(fieldValues.get(KFSConstants.REFERENCES_TO_REFRESH));

        List<BudgetConstructionLockSummary> results = new ArrayList<BudgetConstructionLockSummary>();
     
        // get universal identifier from network id
        String lockUserID = fieldValues.get(BCPropertyConstants.LOCK_USER_ID);
        String lockUnivId = getUniversalIdFromNetworkID(lockUserID);

        getAccountLocks(results, lockUnivId);
        getTransactionLocks(results, lockUnivId);
        getOrphanFundingLocks(results, lockUnivId);
        getPositionFundingLocks(results, lockUnivId);
        getOrphanPositionLocks(results, lockUnivId);

        return new CollectionIncomplete(results, new Long(0));
    }

    /**
     * Calls lock service to retrieve all current account locks and builds a lock summary object for each returned lock.
     * 
     * @param results - result list to add lock summaries
     */
    protected void getAccountLocks(List<BudgetConstructionLockSummary> results, String lockUnivId) {
        List<BudgetConstructionHeader> accountLocks = SpringContext.getBean(LockService.class).getAllAccountLocks(lockUnivId);
        for (BudgetConstructionHeader header : accountLocks) {
            BudgetConstructionLockSummary lockSummary = new BudgetConstructionLockSummary();
            lockSummary.setLockType(BCConstants.LockTypes.ACCOUNT_LOCK);
            lockSummary.setLockUserId(header.getBudgetLockUser().getPersonUserIdentifier());

            lockSummary.setDocumentNumber(header.getDocumentNumber());
            lockSummary.setUniversityFiscalYear(header.getUniversityFiscalYear());
            lockSummary.setChartOfAccountsCode(header.getChartOfAccountsCode());
            lockSummary.setAccountNumber(header.getAccountNumber());
            lockSummary.setSubAccountNumber(header.getSubAccountNumber());

            results.add(lockSummary);
        }
    }

    /**
     * Calls lock service to retrieve all current transaction locks and builds a lock summary object for each returned lock.
     * 
     * @param results - result list to add lock summaries
     */
    protected void getTransactionLocks(List<BudgetConstructionLockSummary> results, String lockUnivId) {
        List<BudgetConstructionHeader> transLocks = SpringContext.getBean(LockService.class).getAllTransactionLocks(lockUnivId);
        for (BudgetConstructionHeader header : transLocks) {
            BudgetConstructionLockSummary lockSummary = new BudgetConstructionLockSummary();
            lockSummary.setLockType(BCConstants.LockTypes.TRANSACTION_LOCK);
            lockSummary.setLockUserId(header.getBudgetTransactionLockUser().getPersonUserIdentifier());

            lockSummary.setDocumentNumber(header.getDocumentNumber());
            lockSummary.setUniversityFiscalYear(header.getUniversityFiscalYear());
            lockSummary.setChartOfAccountsCode(header.getChartOfAccountsCode());
            lockSummary.setAccountNumber(header.getAccountNumber());
            lockSummary.setSubAccountNumber(header.getSubAccountNumber());

            results.add(lockSummary);
        }
    }

    /**
     * Calls lock service to retrieve all funding locks that do not have a corresponding position locks and builds a lock summary
     * object for each returned lock.
     * 
     * @param results - result list to add lock summaries
     */
    protected void getOrphanFundingLocks(List<BudgetConstructionLockSummary> results, String lockUnivId) {
        List<BudgetConstructionFundingLock> fundingLocks = SpringContext.getBean(LockService.class).getOrphanedFundingLocks(lockUnivId);
        for (BudgetConstructionFundingLock fundingLock : fundingLocks) {
            BudgetConstructionLockSummary lockSummary = new BudgetConstructionLockSummary();
            lockSummary.setLockType(BCConstants.LockTypes.FUNDING_LOCK);
            lockSummary.setLockUserId(fundingLock.getAppointmentFundingLockUser().getPersonUserIdentifier());

            lockSummary.setUniversityFiscalYear(fundingLock.getUniversityFiscalYear());
            lockSummary.setChartOfAccountsCode(fundingLock.getChartOfAccountsCode());
            lockSummary.setAccountNumber(fundingLock.getAccountNumber());
            lockSummary.setSubAccountNumber(fundingLock.getSubAccountNumber());

            results.add(lockSummary);
        }
    }

    /**
     * Calls lock service to retrieve all current position/funding locks and builds a lock summary object for each returned lock.
     * 
     * @param results - result list to add lock summaries
     */
    protected void getPositionFundingLocks(List<BudgetConstructionLockSummary> results, String lockUnivId) {
        List<PendingBudgetConstructionAppointmentFunding> positionFundingLocks = SpringContext.getBean(LockService.class).getAllPositionFundingLocks(lockUnivId);
        for (PendingBudgetConstructionAppointmentFunding appointmentFunding : positionFundingLocks) {
            BudgetConstructionLockSummary lockSummary = new BudgetConstructionLockSummary();
            lockSummary.setLockType(BCConstants.LockTypes.POSITION_FUNDING_LOCK);
            lockSummary.setLockUserId(appointmentFunding.getBudgetConstructionPosition().getPositionLockUser().getPersonUserIdentifier());

            lockSummary.setUniversityFiscalYear(appointmentFunding.getUniversityFiscalYear());
            lockSummary.setChartOfAccountsCode(appointmentFunding.getChartOfAccountsCode());
            lockSummary.setAccountNumber(appointmentFunding.getAccountNumber());
            lockSummary.setSubAccountNumber(appointmentFunding.getSubAccountNumber());

            lockSummary.setPositionNumber(appointmentFunding.getBudgetConstructionPosition().getPositionNumber());
            lockSummary.setPositionDescription(appointmentFunding.getBudgetConstructionPosition().getPositionDescription());

            results.add(lockSummary);
        }
    }

    /**
     * Calls lock service to retrieve all current position locks without a corresponding funding lock and builds a lock summary
     * object for each returned lock.
     * 
     * @param results - result list to add lock summaries
     */
    protected void getOrphanPositionLocks(List<BudgetConstructionLockSummary> results, String lockUnivId) {
        List<BudgetConstructionPosition> positionLocks = SpringContext.getBean(LockService.class).getOrphanedPositionLocks(lockUnivId);
        for (BudgetConstructionPosition position : positionLocks) {
            BudgetConstructionLockSummary lockSummary = new BudgetConstructionLockSummary();
            lockSummary.setLockType(BCConstants.LockTypes.POSITION_LOCK);
            lockSummary.setLockUserId(position.getPositionLockUser().getPersonUserIdentifier());

            lockSummary.setUniversityFiscalYear(position.getUniversityFiscalYear());
            lockSummary.setPositionNumber(position.getPositionNumber());
            lockSummary.setPositionDescription(position.getPositionDescription());

            results.add(lockSummary);
        }
    }

    /**
     * Builds unlink action for each type of lock.
     * 
     * @see org.kuali.core.lookup.AbstractLookupableHelperServiceImpl#getActionUrls(org.kuali.core.bo.BusinessObject)
     */
    @Override
    public String getActionUrls(BusinessObject businessObject) {
        BudgetConstructionLockSummary lockSummary = (BudgetConstructionLockSummary) businessObject;

        String imageDirectory = kualiConfigurationService.getPropertyString(KFSConstants.EXTERNALIZABLE_IMAGES_URL_KEY);
        String lockFields = lockSummary.getUniversityFiscalYear() + "%" + lockSummary.getChartOfAccountsCode() + "%" + lockSummary.getAccountNumber() + "%" + lockSummary.getSubAccountNumber() + "%" + lockSummary.getPositionNumber() + "%";
        lockFields = StringUtils.replace(lockFields, "null", "");
        
        String buttonSubmit = "<input name=\"" + KFSConstants.DISPATCH_REQUEST_PARAMETER + "." + BCConstants.TEMP_LIST_UNLOCK_METHOD + ".";
        buttonSubmit += KFSConstants.METHOD_TO_CALL_PARM1_LEFT_DEL + StringUtils.replace(lockSummary.getLockType()," ","_") + KFSConstants.METHOD_TO_CALL_PARM1_RIGHT_DEL;
        buttonSubmit += KFSConstants.METHOD_TO_CALL_PARM2_LEFT_DEL + lockFields + KFSConstants.METHOD_TO_CALL_PARM2_RIGHT_DEL;
        buttonSubmit += KFSConstants.METHOD_TO_CALL_PARM3_LEFT_DEL + lockSummary.getLockUserId() + KFSConstants.METHOD_TO_CALL_PARM3_RIGHT_DEL + "\" ";
        buttonSubmit += "src=\"" + imageDirectory + BCConstants.UNLOCK_BUTTON_NAME + "\"  type=\"image\" styleClass=\"tinybutton\" alt=\"unlock\" title=\"unlock\" border=\"0\" />";

        return buttonSubmit;
    }
    
    /**
     * Uses UniversalUserService to retrieve user object associated with the given network id (if not blank) and then
     * returns universal id. Add error to GlobalVariables if the user was not found.
     * 
     * @param networkID - network id for the user to find
     * @return universal id for the user or null if not found or the network id was blank
     */
    protected String getUniversalIdFromNetworkID(String networkID) {
        String universalId = null;
        if (StringUtils.isNotBlank(networkID)) {
            try {
                UniversalUser user = universalUserService.getUniversalUserByAuthenticationUserId(networkID);
                universalId = user.getPersonUniversalIdentifier();
            }
            catch (UserNotFoundException e) {
                GlobalVariables.getErrorMap().putError(KFSConstants.GLOBAL_ERRORS, BCKeyConstants.ERROR_LOCK_INVALID_USER, networkID);
            }
        }
        
        return universalId;
    }

    /**
     * @see org.kuali.core.lookup.AbstractLookupableHelperServiceImpl#getReturnUrl(org.kuali.core.bo.BusinessObject, java.util.Map, java.lang.String)
     */
    @Override
    public String getReturnUrl(BusinessObject businessObject, Map fieldConversions, String lookupImpl) {
        return "";
    }

    /**
     * Overridden to prevent a validation exception from thrown when the search method is called to refresh the
     * results after an error is encountered.
     * 
     * @see org.kuali.core.lookup.AbstractLookupableHelperServiceImpl#validateSearchParameters(java.util.Map)
     */
    @Override
    public void validateSearchParameters(Map fieldValues) {
    }

    /**
     * Since this lookupable is called by the budget lookup action, the context will be KFS, not Rice. So the generated inquiries
     * will not have the Rice context (kr/) and be invalid. This override adds the Rice context to the inquiry Url to working
     * around the issue.
     * 
     * @see org.kuali.core.lookup.AbstractLookupableHelperServiceImpl#getInquiryUrl(org.kuali.core.bo.BusinessObject,
     *      java.lang.String)
     */
    @Override
    public String getInquiryUrl(BusinessObject bo, String propertyName) {
        String inquiryUrl = super.getInquiryUrl(bo, propertyName);
        inquiryUrl = StringUtils.replace(inquiryUrl, KNSConstants.INQUIRY_ACTION, KFSConstants.INQUIRY_ACTION);

        return inquiryUrl;
    }

    /**
     * Sets the kualiConfigurationService attribute value.
     * 
     * @param kualiConfigurationService The kualiConfigurationService to set.
     */
    public void setKualiConfigurationService(KualiConfigurationService kualiConfigurationService) {
        this.kualiConfigurationService = kualiConfigurationService;
    }

    /**
     * Sets the universalUserService attribute value.
     * 
     * @param universalUserService The universalUserService to set.
     */
    public void setUniversalUserService(UniversalUserService universalUserService) {
        this.universalUserService = universalUserService;
    }
    
}
