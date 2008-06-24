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
package org.kuali.kfs.module.cg.document.service;

import java.util.List;

import org.kuali.kfs.module.cg.businessobject.ResearchRiskType;
import org.kuali.kfs.module.cg.document.RoutingFormDocument;

/**
 * This interface defines methods that a RoutingFormResearchRiskService must provide
 */
public interface RoutingFormResearchRiskService {

    /**
     * Setup research risks in the document.
     * 
     * @param routingFormDocument the doc to set up
     */
    public void setupResearchRisks(RoutingFormDocument routingFormDocument);

    /**
     * Get the list of active research risk types from the database.
     * 
     * @param exceptCodes the codes of research risk types to exclude from the results
     * @return List<ResearchRiskType>
     */
    public List<ResearchRiskType> getResearchRiskTypes(String[] exceptCodes);

    public List<String> getNotificationWorkgroups(String documentNumber);
}
