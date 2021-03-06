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
package org.kuali.kfs.module.cab.fixture;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.kuali.kfs.module.purap.businessobject.RequisitionAccount;
import org.kuali.kfs.sys.context.SpringContext;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.rice.krad.service.BusinessObjectService;

public enum RequisitionAccountFixture {

    REC1 {
        @Override
        public RequisitionAccount newRecord() {
            RequisitionAccount obj = new RequisitionAccount();
            obj.setAccountIdentifier(211);
            obj.setItemIdentifier(111);
            obj.setChartOfAccountsCode("EA");
            obj.setAccountNumber("0366501");
            obj.setFinancialObjectCode("7015");
            obj.setAccountLinePercent(new BigDecimal(40));
            obj.setAmount(new KualiDecimal(7200));
            return obj;
        };
    },

    REC2 {
        @Override
        public RequisitionAccount newRecord() {
            RequisitionAccount obj = new RequisitionAccount();
            obj.setAccountIdentifier(212);
            obj.setItemIdentifier(111);
            obj.setChartOfAccountsCode("EA");
            obj.setAccountNumber("0366500");
            obj.setFinancialObjectCode("7015");
            obj.setAccountLinePercent(new BigDecimal(60));
            obj.setAmount(new KualiDecimal(10800));
            return obj;
        };
    },

    REC3 {
        @Override
        public RequisitionAccount newRecord() {
            RequisitionAccount obj = new RequisitionAccount();
            obj.setAccountIdentifier(213);
            obj.setItemIdentifier(112);
            obj.setChartOfAccountsCode("EA");
            obj.setAccountNumber("0366503");
            obj.setFinancialObjectCode("7000");
            obj.setAccountLinePercent(new BigDecimal(100));
            obj.setAmount(new KualiDecimal(14000));
            return obj;
        };
    },

    REC4 {
        @Override
        public RequisitionAccount newRecord() {
            RequisitionAccount obj = new RequisitionAccount();
            obj.setAccountIdentifier(214);
            obj.setItemIdentifier(113);
            obj.setChartOfAccountsCode("EA");
            obj.setAccountNumber("0366500");
            obj.setFinancialObjectCode("7015");
            obj.setAccountLinePercent(new BigDecimal(100));
            obj.setAmount(new KualiDecimal(750));
            return obj;
        };
    },

    REC5 {
        @Override
        public RequisitionAccount newRecord() {
            RequisitionAccount obj = new RequisitionAccount();
            obj.setAccountIdentifier(215);
            obj.setItemIdentifier(114);
            obj.setChartOfAccountsCode("EA");
            obj.setAccountNumber("0366500");
            obj.setFinancialObjectCode("7015");
            obj.setAccountLinePercent(new BigDecimal(100));
            obj.setAmount(new KualiDecimal(250));
            return obj;
        };
    },

    REC6 {
        @Override
        public RequisitionAccount newRecord() {
            RequisitionAccount obj = new RequisitionAccount();
            obj.setAccountIdentifier(221);
            obj.setItemIdentifier(121);
            obj.setChartOfAccountsCode("EA");
            obj.setAccountNumber("0366501");
            obj.setFinancialObjectCode("7015");
            obj.setAccountLinePercent(new BigDecimal(40));
            obj.setAmount(new KualiDecimal(7200));
            return obj;
        };
    },

    REC7 {
        @Override
        public RequisitionAccount newRecord() {
            RequisitionAccount obj = new RequisitionAccount();
            obj.setAccountIdentifier(222);
            obj.setItemIdentifier(121);
            obj.setChartOfAccountsCode("EA");
            obj.setAccountNumber("0366500");
            obj.setFinancialObjectCode("7015");
            obj.setAccountLinePercent(new BigDecimal(60));
            obj.setAmount(new KualiDecimal(10800));
            return obj;
        };
    },

    REC8 {
        @Override
        public RequisitionAccount newRecord() {
            RequisitionAccount obj = new RequisitionAccount();
            obj.setAccountIdentifier(223);
            obj.setItemIdentifier(122);
            obj.setChartOfAccountsCode("EA");
            obj.setAccountNumber("0366503");
            obj.setFinancialObjectCode("7000");
            obj.setAccountLinePercent(new BigDecimal(100));
            obj.setAmount(new KualiDecimal(14000));
            return obj;
        };
    },

    REC9 {
        @Override
        public RequisitionAccount newRecord() {
            RequisitionAccount obj = new RequisitionAccount();
            obj.setAccountIdentifier(224);
            obj.setItemIdentifier(123);
            obj.setChartOfAccountsCode("EA");
            obj.setAccountNumber("0366500");
            obj.setFinancialObjectCode("7015");
            obj.setAccountLinePercent(new BigDecimal(100));
            obj.setAmount(new KualiDecimal(750));
            return obj;
        };
    },

    REC10 {
        @Override
        public RequisitionAccount newRecord() {
            RequisitionAccount obj = new RequisitionAccount();
            obj.setAccountIdentifier(225);
            obj.setItemIdentifier(124);
            obj.setChartOfAccountsCode("EA");
            obj.setAccountNumber("0366500");
            obj.setFinancialObjectCode("7015");
            obj.setAccountLinePercent(new BigDecimal(100));
            obj.setAmount(new KualiDecimal(250));
            return obj;
        };
    },

    REC11 {
        @Override
        public RequisitionAccount newRecord() {
            RequisitionAccount obj = new RequisitionAccount();
            obj.setAccountIdentifier(231);
            obj.setItemIdentifier(131);
            obj.setChartOfAccountsCode("EA");
            obj.setAccountNumber("0366501");
            obj.setFinancialObjectCode("7015");
            obj.setAccountLinePercent(new BigDecimal(40));
            obj.setAmount(new KualiDecimal(7200));
            return obj;
        };
    },

    REC12 {
        @Override
        public RequisitionAccount newRecord() {
            RequisitionAccount obj = new RequisitionAccount();
            obj.setAccountIdentifier(232);
            obj.setItemIdentifier(131);
            obj.setChartOfAccountsCode("EA");
            obj.setAccountNumber("0366500");
            obj.setFinancialObjectCode("7015");
            obj.setAccountLinePercent(new BigDecimal(60));
            obj.setAmount(new KualiDecimal(10800));
            return obj;
        };
    },

    REC13 {
        @Override
        public RequisitionAccount newRecord() {
            RequisitionAccount obj = new RequisitionAccount();
            obj.setAccountIdentifier(233);
            obj.setItemIdentifier(132);
            obj.setChartOfAccountsCode("EA");
            obj.setAccountNumber("0366503");
            obj.setFinancialObjectCode("7000");
            obj.setAccountLinePercent(new BigDecimal(100));
            obj.setAmount(new KualiDecimal(14000));
            return obj;
        };
    },

    REC14 {
        @Override
        public RequisitionAccount newRecord() {
            RequisitionAccount obj = new RequisitionAccount();
            obj.setAccountIdentifier(234);
            obj.setItemIdentifier(133);
            obj.setChartOfAccountsCode("EA");
            obj.setAccountNumber("0366500");
            obj.setFinancialObjectCode("7015");
            obj.setAccountLinePercent(new BigDecimal(100));
            obj.setAmount(new KualiDecimal(750));
            return obj;
        };
    },

    REC15 {
        @Override
        public RequisitionAccount newRecord() {
            RequisitionAccount obj = new RequisitionAccount();
            obj.setAccountIdentifier(235);
            obj.setItemIdentifier(134);
            obj.setChartOfAccountsCode("EA");
            obj.setAccountNumber("0366500");
            obj.setFinancialObjectCode("7015");
            obj.setAccountLinePercent(new BigDecimal(100));
            obj.setAmount(new KualiDecimal(250));
            return obj;
        };
    };
    public abstract RequisitionAccount newRecord();

    public static void setUpData() {
        BusinessObjectService businessObjectService = SpringContext.getBean(BusinessObjectService.class);
        businessObjectService.save(getAll());
    }

    private static List<RequisitionAccount> getAll() {
        List<RequisitionAccount> recs = new ArrayList<RequisitionAccount>();
        recs.add(REC1.newRecord());
        recs.add(REC2.newRecord());
        recs.add(REC3.newRecord());
        recs.add(REC4.newRecord());
        recs.add(REC5.newRecord());
        recs.add(REC6.newRecord());
        recs.add(REC7.newRecord());
        recs.add(REC8.newRecord());
        recs.add(REC9.newRecord());
        recs.add(REC10.newRecord());
        recs.add(REC11.newRecord());
        recs.add(REC12.newRecord());
        recs.add(REC13.newRecord());
        recs.add(REC14.newRecord());
        recs.add(REC15.newRecord());
        return recs;
    }
}
