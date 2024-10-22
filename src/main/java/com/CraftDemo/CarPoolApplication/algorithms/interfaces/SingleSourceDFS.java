package com.CraftDemo.CarPoolApplication.algorithms.interfaces;



import com.CraftDemo.CarPoolApplication.dto.Graph.Graph;

import java.util.List;
import java.util.Set;

public interface SingleSourceDFS<T,V> extends GraphSearch {
    Set<V> execute(T src , T destination , Graph<T> graph);
}
