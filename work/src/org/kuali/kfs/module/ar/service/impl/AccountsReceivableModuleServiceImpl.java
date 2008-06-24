/*
 * Copyright 2008 The Kuali Foundation.
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
package org.kuali.kfs.module.ar.service.impl;

import java.util.Map;

import org.kuali.kfs.sys.context.SpringContext;
import org.kuali.kfs.sys.service.ElectronicPaymentClaimingDocumentGenerationStrategy;
import org.kuali.kfs.integration.service.AccountsReceivableModuleService;

/**
 * The KFS AR module implementation of the AccountsReceivableModuleService
 */
public class AccountsReceivableModuleServiceImpl implements AccountsReceivableModuleService {
    private static final String CASH_CONTROL_ELECTRONIC_PAYMENT_CLAIMING_DOCUMENT_GENERATION_STRATEGY_BEAN_NAME = "cashControlElectronicPaymentClaimingDocumentHelper";

    /**
     * @see org.kuali.kfs.integration.service.AccountsReceivableModuleService#getAccountsReceivablePaymentClaimingStrategy()
     */
    public ElectronicPaymentClaimingDocumentGenerationStrategy getAccountsReceivablePaymentClaimingStrategy() {
        Map<String, ElectronicPaymentClaimingDocumentGenerationStrategy> eftClaimingDocumentGenerationStrategies = SpringContext.getBeansOfType(ElectronicPaymentClaimingDocumentGenerationStrategy.class);
        return eftClaimingDocumentGenerationStrategies.get(AccountsReceivableModuleServiceImpl.CASH_CONTROL_ELECTRONIC_PAYMENT_CLAIMING_DOCUMENT_GENERATION_STRATEGY_BEAN_NAME);
    }

}
