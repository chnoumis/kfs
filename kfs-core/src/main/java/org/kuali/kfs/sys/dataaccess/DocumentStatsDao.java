package org.kuali.kfs.sys.dataaccess;

import java.util.List;
import java.util.Map;

public interface DocumentStatsDao {
    public List<Map<String, Integer>> reportNumInitiatedDocsByDocType(int limit, int days);

    Map<String, Map<String, Integer>> reportCompletedActionRequestsByPrincipal(int limit);

    Map<String, Map<String, Integer>> reportUncompletedActionRequestsByPrincipal(int limit);

    Map<String, Integer> reportUncompletedActionRequestsByType();

    Map<String, Integer> reportCompletedActionRequestsByType();

    public List<Map<String, Map<String, Integer>>> reportNumDocsByStatusByDocType(int limit, int days);


    public Map<String,Integer> reportNumDocsByStatus(int days);
}