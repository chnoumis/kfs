ALTER TABLE krns_sesn_doc_t ADD (OBJ_ID VARCHAR(36));
ALTER TABLE krns_sesn_doc_t ADD (VER_NBR DECIMAL(8) DEFAULT 0);
ALTER TABLE KRSB_MSG_QUE_T CHANGE SVC_NMSPC APPL_ID VARCHAR(255);
ALTER TABLE KRNS_DOC_HDR_T MODIFY FDOC_DESC varchar(255);