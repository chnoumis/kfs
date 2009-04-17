/*
 * Copyright 2005-2007 The Kuali Foundation.
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
package org.kuali.kfs.gl.batch;

import org.kuali.kfs.gl.service.ScrubberService;
import org.kuali.kfs.sys.batch.AbstractBatchTransactionalCachingStep;
import org.kuali.kfs.sys.batch.service.BatchTransactionalCachingService.BatchTransactionExecutor;

/**
 * A step to run the scrubber process.
 */
public class ScrubberStep extends AbstractBatchTransactionalCachingStep {
    private ScrubberService scrubberService;

    @Override
    protected BatchTransactionExecutor getBatchTransactionExecutor() {
        return new BatchTransactionExecutor() {
            public void executeCustom() {
                scrubberService.scrubEntries();                
            }
        };
    }
    
    public void setScrubberService(ScrubberService scrubberService) {
        this.scrubberService = scrubberService;
    }
}
