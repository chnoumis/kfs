/*
 * Copyright 2006-2007 The Kuali Foundation.
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
package org.kuali.kfs.gl.batch.service;

import java.util.Map;

import org.kuali.kfs.gl.batch.BalanceForwardRuleHelper;
import org.kuali.kfs.gl.businessobject.OriginEntryGroup;


/**
 * An interface declaring the methods that steps running year end services would need to use to close out activity of an ending
 * fiscal year (though, note: OrganizationReversion, the other year end job, is not defined here).
 */
public interface YearEndService {


    /**
     * The method responsible for creating origin entries that will forward qualifying encumbrances into the next fiscal year.
     * 
     * @param originEntryGroup the origin entry group where generated origin entries should be saved
     * @param jobParameters the parameters necessary to run this job: the fiscal year to close and the university date the job was
     *        run
     * @param forwardEncumbrancesCounts the statistical counts generated by this job
     */
    public void forwardEncumbrances(OriginEntryGroup originEntryGroup, Map jobParameters, Map<String, Integer> forwardEncumbrancesCounts);

    /**
     * This method generates the report for the forward encumbrances job.
     * 
     * @param originEntryGroup the origin entry group that encumbrance forwarding origin entries were saved in
     * @param jobParameters the parameters needed to run the job in the first place
     * @param forwardEncumbrancesCounts the statistical counts generated by the forward encumbrances job
     */
    public void generateForwardEncumbrancesReports(OriginEntryGroup originEntryGroup, Map jobParameters, Map<String, Integer> forwardEncumbrancesCounts);

    /**
     * Implementations of this method are responsible for generating origin entries to forward the balances of qualifying balances
     * from the previous fiscal year
     * 
     * @param balanceForwardsUnclosedPriorYearAccountGroup the origin entry group to save balance forwarding origin entries with
     *        open accounts
     * @param balanceForwardsClosedPriorYearAccountGroup the origin entry group to save balance forwarding origin entries with
     *        closed accounts
     * @param balanceForwardRuleHelper the BalanceForwardRuleHelper which holds the important state - the job parameters and
     *        statistics - for the job to run
     */
    public void forwardBalances(OriginEntryGroup balanceForwardsUnclosedPriorYearAccountGroup, OriginEntryGroup balanceForwardsClosedPriorYearAccountGroup, BalanceForwardRuleHelper balanceForwardRuleHelper);

    /**
     * This method generates the report that summarizes the activity of a forward balances job
     * 
     * @param balanceForwardsUnclosedPriorYearAccountGroup the origin entry group where balance forwarding origin entries with open
     *        accounts are stored
     * @param balanceForwardsClosedPriorYearAccountGroup the origin entry group where balance forwarding origin entries with closed
     *        accounts are stored
     * @param balanceForwardRuleHelper the BalanceForwardRuleHelper that held the state of the balance forward job to report on
     */
    public void generateForwardBalanceReports(OriginEntryGroup balanceForwardsUnclosedPriorYearAccountGroup, OriginEntryGroup balanceForwardsClosedPriorYearAccountGroup, BalanceForwardRuleHelper balanceForwardRuleHelper);

    /**
     * This method is considered responsible for generating all of the origin entries that will close out nominal activity for a
     * given fiscal year.
     * 
     * @param nominalClosingOriginEntryGroup the origin entry group that nominal activity closing origin entries
     * @param nominalClosingJobParameters the parameters needed by the job to run correctly: the current university date and the
     *        fiscal year to close
     * @param nominalClosingCounts counts the Map of statistical counts generated by the process
     */
    public void closeNominalActivity(OriginEntryGroup nominalClosingOriginEntryGroup, Map nominalClosingJobParameters, Map<String, Integer> nominalClosingCounts);

    /**
     * This method generates the report for a nominal activity closing job
     * 
     * @param nominalClosingOriginEntryGroup the origin entry group that nominal activity closing origin entries
     * @param nominalClosingJobParameters the parameters needed by the job to run correctly: the current university date and the
     *        fiscal year to close
     * @param nominalClosingCounts counts the Map of statistical counts generated by the process
     */
    public void generateCloseNominalActivityReports(OriginEntryGroup nominalClosingOriginEntryGroup, Map nominalClosingJobParameters, Map<String, Integer> nominalClosingCounts);

    /**
     * Logs all of the missing prior year accounts that balances and encumbrances processed by year end jobs would attempt to call
     * on
     * 
     * @param balanceFiscalYear the fiscal year to find balances encumbrances for
     */
    public void logAllMissingPriorYearAccounts(Integer fiscalYear);

    /**
     * Logs all of the missing sub fund groups that balances and encumbrances processed by the year end job would attempt to call on
     * 
     * @param balanceFiscalYear the fiscal year to find balances and encumbrances for
     */
    public void logAllMissingSubFundGroups(Integer fiscalYear);
}
