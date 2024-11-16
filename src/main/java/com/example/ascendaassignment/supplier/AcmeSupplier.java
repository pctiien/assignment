package com.example.ascendaassignment.supplier;

import com.example.ascendaassignment.helper.DataHelper;
import com.example.ascendaassignment.model.Hotel;
import com.example.ascendaassignment.supplier.factory.SupplierFactory;

import java.util.Map;

public class AcmeSupplier extends BaseSupplier {

    // Automatically register to SupplierFactory
    static {
        SupplierFactory.registerSupplier(new AcmeSupplier());
    }
    @Override
    public String endpoint() {
        return "https://5f2be0b4ffc88500167b85a0.mockapi.io/suppliers/acme";
    }
    @Override
    public String getName() {
        return "Acme";
    }
    @SuppressWarnings("unchecked")
    @Override
    public Hotel parse(Object source) {

        var sourceData = (Map<String,Object>) source;


        Hotel hotel = new Hotel();

        // Base info
        hotel.setId(DataHelper.getString(sourceData,"Id"));
        hotel.setDestination_id(Long.valueOf(sourceData.get("DestinationId").toString()));
        hotel.setName(sourceData.get("Name").toString());

        // Location
        Hotel.Location location = new Hotel.Location();
        location.setAddress(DataHelper.getString(sourceData,"Address"));
        location.setCountry(DataHelper.getString(sourceData,"Country"));
        location.setCity(DataHelper.getString(sourceData,"City"));
        location.setLat(DataHelper.getDouble(sourceData,"Latitude"));
        location.setLng(DataHelper.getDouble(sourceData,"Longitude"));

        hotel.setLocation(location);

        // Description
        hotel.setDescription(DataHelper.getString(sourceData,"Description"));

        // Amentities
        var amentitiesData = DataHelper.getList(sourceData,"Facilities");
        Hotel.Amenities amenities = new Hotel.Amenities();
        amenities.setGeneral(amentitiesData);

        hotel.setAmenities(amenities);


        return hotel;
    }
}
