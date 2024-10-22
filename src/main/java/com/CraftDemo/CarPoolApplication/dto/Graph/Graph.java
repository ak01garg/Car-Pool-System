package com.CraftDemo.CarPoolApplication.dto.Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph<T> {
    private Map<T , List<T>> rideGraphMap;
    private Map<Object, T> nodeIdentifierMap;


    public Graph() {
        this.nodeIdentifierMap = new HashMap<>();
        this.rideGraphMap = new HashMap<>();
    }

    public void addNodes(T source , T destination){
        rideGraphMap.computeIfAbsent(source , ( k-> new ArrayList<>())).add(destination);
    }

    public void addNodeIdentifier(Object identifier, T node) {
        nodeIdentifierMap.put(identifier, node);
    }

    public void removeNodes(T source , T destination){
        List<T> nodes = rideGraphMap.get(source);
        nodes.remove(destination);
        rideGraphMap.put(source , nodes);
    }

    public void removeNodeIdentifier(Object identifier) {
        nodeIdentifierMap.remove(identifier);
    }

    public Map<T, List<T>> getRideGraphMap() {
        return rideGraphMap;
    }

    public Map<Object, T> getNodeIdentifierMap() {
        return nodeIdentifierMap;
    }
}
