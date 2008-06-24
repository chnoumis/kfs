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
/*
 * Created on Jun 25, 2004
 *
 */
package org.kuali.kfs.pdp.service.paymentparser;

import java.io.InputStream;

import org.kuali.kfs.pdp.exception.FileReadException;


/**
 * @author jsissom
 */
public interface PaymentFileParser {
    public abstract void setFileHandler(PdpFileHandler fileHandler);

    public abstract void parse(String filename) throws FileReadException;

    public void parse(InputStream stream) throws FileReadException;
}
