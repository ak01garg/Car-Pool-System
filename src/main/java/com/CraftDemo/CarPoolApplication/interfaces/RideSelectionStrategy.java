package com.CraftDemo.CarPoolApplication.interfaces;

import com.CraftDemo.CarPoolApplication.dto.request.RideSelectionRequest;
import com.CraftDemo.CarPoolApplication.models.Ride;

import java.util.List;
import java.util.Optional;

public interface RideSelectionStrategy {

    String generateCompositeKey(String source, String destination);
    List<Ride> selectRide(RideSelectionRequest rideSelectionRequest);
}
