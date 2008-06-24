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
package org.kuali.kfs.module.ld.document.service;

import org.kuali.kfs.module.ld.businessobject.ExpenseTransferAccountingLine;
import org.kuali.kfs.module.ld.document.SalaryExpenseTransferDocument;

/**
 * Validates the transfers from an SET document do not conflict with any employee effort report generated by the Effort
 * Certification module.
 */
public interface SalaryTransferPeriodValidationService {

    /**
     * Checks the transfers that will be made by the expense lines do not impact an effort certification document.
     * 
     * @param document - salary expense document to validate
     * @return boolean indicating whether the transfer are valid (true) or not (false)
     */
    public boolean validateTransfers(SalaryExpenseTransferDocument document);

    /**
     * Disapproves the salary expense document due to effort validation errors.
     * 
     * @param document - document to cancel
     */
    public void disapproveSalaryExpenseDocument(SalaryExpenseTransferDocument document) throws Exception;

}
