package com.CraftDemo.CarPoolApplication.algorithms.interfaces;

import java.util.List;

public interface FilterService<K,V> {

    List<V> doFilter(K k);
}
