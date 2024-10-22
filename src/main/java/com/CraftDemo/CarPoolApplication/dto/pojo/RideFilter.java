package com.CraftDemo.CarPoolApplication.dto.pojo;

public class RideFilter {


    private String source;
    private String destination;
    private boolean filterTransitively;

    public RideFilter(String source, String destination, boolean filterTransitively) {
        this.source = source;
        this.destination = destination;
        this.filterTransitively = filterTransitively;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public boolean isFilterTransitively() {
        return filterTransitively;
    }

    public void setFilterTransitively(boolean filterTransitively) {
        this.filterTransitively = filterTransitively;
    }
}
