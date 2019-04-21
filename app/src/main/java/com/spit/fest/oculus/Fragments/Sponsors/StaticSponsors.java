package com.spit.fest.oculus.Fragments.Sponsors;

import java.util.HashMap;
import java.util.Map;

public class StaticSponsors {

    private static boolean isDataSet = false;
    private static Map<String,Map<String,String>> sponsors = new HashMap<>();
    private static Map<Integer,Map<String,String>> displayOrder = new HashMap<>();


    public static void setData(Map<String,Map<String,String>> sponsorsReceived,Map<Integer,Map<String,String>> displayOrderReceived){
        isDataSet = true;
        displayOrder=displayOrderReceived;
        sponsors = sponsorsReceived;

    }

    public static boolean isIsDataSet() {
        return isDataSet;
    }

    public static Map<String, Map<String,String>> getSponsors() {
        return sponsors;
    }

    public static Map<Integer, Map<String, String>> getDisplayOrder() {
        return displayOrder;
    }
}
