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
package org.kuali.kfs.coa.dataaccess.impl;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.kfs.coa.businessobject.Account;
import org.kuali.kfs.coa.businessobject.SubFundGroup;
import org.kuali.kfs.coa.dataaccess.SubFundGroupDao;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

/**
 * This class implements the {@link SubFundGroupDao} data access methods using Ojb
 */
public class SubFundGroupDaoOjb extends PlatformAwareDaoBaseOjb implements SubFundGroupDao {

    /**
     * @see org.kuali.kfs.coa.dataaccess.SubFundGroupDao#getByChartAndAccount(java.lang.String, java.lang.String)
     */
    public SubFundGroup getByChartAndAccount(String chartCode, String accountNumber) {
        Criteria criteria = new Criteria();
        criteria.addEqualTo("chartOfAccountsCode", chartCode);
        criteria.addEqualTo("accountNumber", accountNumber);

        Account account = (Account) getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(Account.class, criteria));
        criteria = new Criteria();
        criteria.addEqualTo("subFundGroupCode", account.getSubFundGroupCode());

        return (SubFundGroup) getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(SubFundGroup.class, criteria));
    }

}
