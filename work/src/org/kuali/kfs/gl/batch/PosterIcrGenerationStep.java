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
package org.kuali.kfs.gl.batch;

import java.util.Date;

import org.kuali.kfs.sys.batch.AbstractStep;
import org.kuali.kfs.gl.batch.service.PosterService;

/**
 * A step to run the process that creates indirect cost recover transactions, that the poster can then post
 */
public class PosterIcrGenerationStep extends AbstractStep {
    private static org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(PosterIcrGenerationStep.class);
    private PosterService posterService;

    /**
     * Runs the process that generates Indirect Cost Recory transactions
     * 
     * @param jobName the name of the job this step is being run as part of
     * @param jobRunDate the time/date the job was started
     * @return true if the job completed successfully, false if otherwise
     * @see org.kuali.kfs.sys.batch.Step#execute(String, Date)
     */
    public boolean execute(String jobName, Date jobRunDate) {
        posterService.generateIcrTransactions();
        return true;
    }

    /**
     * Sets the posterService attribute, allowing the injection of an implementation of the service
     * 
     * @param ps the implementation of the posterService to set
     * @see org.kuali.kfs.gl.batch.service.PosterService
     */
    public void setPosterService(PosterService ps) {
        posterService = ps;
    }
}
