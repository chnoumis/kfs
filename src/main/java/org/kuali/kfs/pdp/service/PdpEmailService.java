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
package org.kuali.kfs.pdp.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.kuali.kfs.pdp.businessobject.Batch;
import org.kuali.kfs.pdp.businessobject.CustomerProfile;
import org.kuali.kfs.pdp.businessobject.PaymentDetail;
import org.kuali.kfs.pdp.businessobject.PaymentFileLoad;
import org.kuali.kfs.pdp.businessobject.PaymentGroup;
import org.kuali.kfs.sys.document.PaymentSource;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.krad.util.MessageMap;

/**
 * Defines methods for sending PDP emails.
 */
public interface PdpEmailService {

    /**
     * Sends email for a payment load has failed. Errors encountered will be printed out in message
     *
     * @param paymentFile parsed payment file object (might not be populated completely due to errors)
     * @param errors <code>MessageMap</code> containing <code>ErrorMessage</code> entries
     */
    public void sendErrorEmail(PaymentFileLoad paymentFile, MessageMap errors);

    /**
     * Sends email for a successful payment load. Warnings encountered will be printed out in message
     *
     * @param paymentFile parsed payment file object
     * @param warnings <code>List</code> of <code>String</code> messages
     */
    public void sendLoadEmail(PaymentFileLoad paymentFile, List<String> warnings);

    /**
     * Sends email for a payment load that was held due to tax reasons
     *
     * @param paymentFile parsed payment file object
     */
    public void sendTaxEmail(PaymentFileLoad paymentFile);

    /**
     * Sends email for a load done internally
     *
     * @param batch <code>Batch</code> created by load
     */
    public void sendLoadEmail(Batch batch);

    /**
     * Sends email for a purap bundle that exceeds the maximum number of notes allowed
     *
     * @param creditMemos list of credit memo documents in bundle
     * @param paymentRequests list of payment request documents in bundle
     * @param lineTotal total number of lines for bundle
     * @param maxNoteLines maximum number of lines allowed
     */
    public void sendExceedsMaxNotesWarningEmail(List<String> creditMemos, List<String> paymentRequests, int lineTotal, int maxNoteLines);

    /**
     * Sends summary email for an ACH extract
     *
     * @param unitCounts Map containing payment counts for each unit
     * @param unitTotals Map containing total payment amount for each unit
     * @param extractDate date of ACH extraction
     */
    public void sendAchSummaryEmail(Map<String, Integer> unitCounts, Map<String, KualiDecimal> unitTotals, Date extractDate);

    /**
     * Sends advice notification email to the payee receiving an ACH payment
     *
     * @param paymentGroup ACH payment group to send notification for
     * @param paymentDetail Payment Detail containing payment amounts
     * @param customer Pdp Customer profile for payment
     */
    public void sendAchAdviceEmail(PaymentGroup paymentGroup, PaymentDetail paymentDetail, CustomerProfile customer);

    /**
     * Sends Payment Cancellation Email
     *
     * @param paymentGroup
     * @param note
     * @param user
     */
    public void sendCancelEmail(PaymentGroup paymentGroup, String note, Person user);

    /**
     * Reads system parameter indicating whether to status emails should be sent
     *
     * @return true if email should be sent, false otherwise
     */
    public boolean isPaymentEmailEnabled() ;

    /**
     * Sends notification e-mail that an immediate extract Disbursement Voucher has been extracted
     * @param paymentSource the payment source being immediately extracted
     * @param paymentSourceToExtractService the service used to extract the payment, which reports the e-mail addresses to extract
     * @param user the current extracting user
     */
    public void sendPaymentSourceImmediateExtractEmail(PaymentSource paymentSource, String fromAddress, Collection<String> toAddresses);
}