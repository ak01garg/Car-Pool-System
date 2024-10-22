package com.CraftDemo.CarPoolApplication.dto.Graph;

import com.CraftDemo.CarPoolApplication.dto.Graph.GraphNode;

public class RideGraphNode extends GraphNode {

    private String location;

    public RideGraphNode(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
