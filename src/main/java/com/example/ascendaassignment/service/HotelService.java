package com.example.ascendaassignment.service;

import com.example.ascendaassignment.model.Hotel;

import java.util.*;

public class HotelService {

    private List<Hotel> hotels=new ArrayList<>();

    public void mergeAndSave(List<Hotel> datas)
    {
        // Merge when 2 hotels have the same ID
        Map<String,Hotel> mergedData = new HashMap<>();

        datas.forEach(hotel->{
            if(mergedData.containsKey(hotel.getId()))
            {
                // If any fields of new hotel != null , set it to existing hotel
                // If data type is array , merge 2 arrays of existing and new hotel and set it to existing hotel
                Hotel existingHotel = mergedData.get(hotel.getId());
                existingHotel.merge(hotel);
                mergedData.put(hotel.getId(), existingHotel);

            }else{
                mergedData.put(hotel.getId(),hotel);
            }
        });
        hotels.clear();
        hotels.addAll(mergedData.values());
    }
    public List<Hotel> find(String[] hotelIds,Long[] destinationIds)
    {
        List<Hotel> filteredHotels = new ArrayList<>();
        hotels.forEach(hotel->{
            boolean isHotelIdMatch = hotelIds == null || Arrays.stream(hotelIds).anyMatch(id -> id.equals(hotel.getId()));
            boolean isDestinationIdMatch = destinationIds == null || Arrays.stream(destinationIds).anyMatch(id -> id.equals(hotel.getDestination_id()));

            if (isHotelIdMatch && isDestinationIdMatch) {
                filteredHotels.add(hotel);
            }
        });
        return filteredHotels;
    }

}
