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
package org.kuali.kfs.module.purap.businessobject.options;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.kuali.core.lookup.keyvalues.KeyValuesBase;
import org.kuali.core.service.KeyValuesService;
import org.kuali.core.web.ui.KeyLabelPair;
import org.kuali.kfs.sys.businessobject.Options;
import org.kuali.kfs.sys.context.SpringContext;
import org.kuali.kfs.sys.businessobject.options.FiscalYearComparator;
import org.kuali.kfs.fp.businessobject.FiscalYearFunctionControl;
import org.kuali.kfs.fp.service.FiscalYearFunctionControlService;
import org.kuali.kfs.module.purap.document.service.PurapService;

/**
 * This class...
 */
public class PurapFiscalYearValuesFinder extends KeyValuesBase {

    /*
     * @see org.kuali.keyvalues.KeyValuesFinder#getKeyValues()
     */
    public List getKeyValues() {
        List returnControls = new ArrayList();
        List<Integer> years = SpringContext.getBean(PurapService.class).getAllowedFiscalYears();
        for (Integer year : years) {
            returnControls.add(new KeyLabelPair(year, year.toString()));
        }

        return returnControls;
    }

}
