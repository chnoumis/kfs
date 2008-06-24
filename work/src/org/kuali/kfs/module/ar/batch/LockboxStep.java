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
package org.kuali.kfs.module.ar.batch;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.kuali.kfs.sys.batch.AbstractStep;
import org.kuali.kfs.module.ar.businessobject.Lockbox;
import org.kuali.kfs.module.ar.batch.service.LockboxService;

public class LockboxStep extends AbstractStep {

    private LockboxService lockboxService;
    private static org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(LockboxStep.class);
    
    public boolean execute(String jobName, Date jobRunDate) throws InterruptedException {
        boolean result = true;
        try {
            result = lockboxService.processLockbox();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void setLockboxService(LockboxService ls) {
        lockboxService = ls;
    }
    
}
