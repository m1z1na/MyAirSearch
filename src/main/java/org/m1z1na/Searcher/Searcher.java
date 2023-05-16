package org.m1z1na.Searcher;

import java.util.List;

public interface Searcher {

    List<String> searchData(String searchTerm);

    List<String> searchDataByName(List<String> list, String searchTerm);

    long getTimeSpent();

}
