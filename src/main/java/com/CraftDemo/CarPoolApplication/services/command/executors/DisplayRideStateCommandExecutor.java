package com.CraftDemo.CarPoolApplication.services.command.executors;


import com.CraftDemo.CarPoolApplication.dto.pojo.UserStatistics;
import com.CraftDemo.CarPoolApplication.enums.CommandConfig;
import com.CraftDemo.CarPoolApplication.interfaces.CommandExecutor;
import com.CraftDemo.CarPoolApplication.models.Booking;
import com.CraftDemo.CarPoolApplication.models.Ride;
import com.CraftDemo.CarPoolApplication.models.User;
import com.CraftDemo.CarPoolApplication.services.BookingService;
import com.CraftDemo.CarPoolApplication.services.RideService;
import com.CraftDemo.CarPoolApplication.services.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.CraftDemo.CarPoolApplication.enums.RideStatus.EXPIRED;


public class DisplayRideStateCommandExecutor implements CommandExecutor {

    private final BookingService bookingService;
    private final UserService userService;
    private final RideService rideService;

    public DisplayRideStateCommandExecutor() {
        this.userService = new UserService();
        this.bookingService = new BookingService();
        this.rideService = new RideService();
    }

    @Override
    public boolean canExecute(String command) {
        return Objects.nonNull(command) && command.toLowerCase().startsWith(CommandConfig.DISPLAY_RIDE_STATS.getPrefix());
    }

    @Override
    public void execute(String command) {

        List<UserStatistics> userStatisticsList = new ArrayList<>();
        List<User> allUsers = userService.getAllUsers();
        for (User user : allUsers){
            List<Booking> bookings = bookingService.getBookings(user.getId());
            List<Ride> ridesOfferedByUser = rideService.getRidesOfferedByUser(user.getId());
            Integer ridesTaken = 0 ;
            Integer ridesOffered = 0 ;
            if(Objects.nonNull(bookings) && !bookings.isEmpty()){
                for (Booking booking : bookings) {
                    Ride rideById = rideService.getRideById(booking.getRideId());
                    ridesTaken += Objects.nonNull(rideById)
                            && Objects.equals(rideById.getRideStatus(), EXPIRED) ? 1 : 0;
                }
            }
            if(Objects.nonNull(ridesOfferedByUser) && !ridesOfferedByUser.isEmpty()){
                ridesOffered = Math.toIntExact(ridesOfferedByUser.stream().filter(ride -> Objects.equals(ride.getRideStatus(), EXPIRED)).count());
            }
            userStatisticsList.add(new UserStatistics(user.getName(),ridesOffered,ridesTaken));
        }

        userStatisticsList.forEach(userStatistics -> System.out.println(userStatistics.toString()));
    }
}
