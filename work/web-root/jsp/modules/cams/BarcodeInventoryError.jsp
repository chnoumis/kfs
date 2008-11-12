<%--
 Copyright 2005-2007 The Kuali Foundation.
 
 Licensed under the Educational Community License, Version 1.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
 
 http://www.opensource.org/licenses/ecl1.php
 
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
--%>

<%@ include file="/jsp/kfs/kfsTldHeader.jsp"%>
<script language="JavaScript" type="text/javascript" src="scripts/cams/selectAllCheckbox.js"></script>
<kul:documentPage showDocumentInfo="true"  htmlFormAction="camsBarcodeInventoryError"  documentTypeName="BarcodeInventoryErrorDocument" 
renderMultipart="true"  showTabButtons="true">

	<!-- 
	<kfs:hiddenDocumentFields isTransactionalDocument="false" />
  	<html:hidden property="document.uploaderUniversalIdentifier"/>
  	 -->
 	<kfs:documentOverview editingMode="${KualiForm.editingMode}" />
 	
 	<cams:barcodeInventoryErrorDetails/>
 	
    <kul:notes />
    
    <kul:adHocRecipients />
    
    <kul:routeLog />
    
    <kul:panelFooter />
    
    <kfs:documentControls transactionalDocument="${documentEntry.transactionalDocument}" suppressRoutingControls="true"/>
    
</kul:documentPage>