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
package org.kuali.kfs.sys.context;

import java.util.Arrays;
import java.util.Properties;

import javax.xml.namespace.QName;

import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.framework.resourceloader.SpringResourceLoader;
import org.kuali.rice.core.impl.config.property.JAXBConfigImpl;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class KFSTestStartup {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(KFSTestStartup.class);

    private static ClassPathXmlApplicationContext context;

    public static void initializeKfsTestContext() {
        long startInit = System.currentTimeMillis();
        LOG.info("Initializing Kuali Rice Application...");

        String bootstrapSpringBeans = "kfs-startup-test.xml";

        Properties baseProps = new Properties();
        baseProps.putAll(System.getProperties());
        JAXBConfigImpl config = new JAXBConfigImpl(baseProps);
        ConfigContext.init(config);
        
        context = new ClassPathXmlApplicationContext(bootstrapSpringBeans);

//        try {
//            context.refresh();
//        } catch (RuntimeException e) {
//            LOG.error("problem during context.refresh()", e);
//
//            throw e;
//        }

        context.start();
        long endInit = System.currentTimeMillis();
        LOG.info("...Kuali Rice Application successfully initialized, startup took " + (endInit - startInit) + " ms.");
    
        SpringResourceLoader mainKfsSpringResourceLoader = (SpringResourceLoader)GlobalResourceLoader.getResourceLoader( new QName("KFS", "KFS_RICE_SPRING_RESOURCE_LOADER_NAME") ); 
        SpringContext.applicationContext = mainKfsSpringResourceLoader.getContext();
    }
}
