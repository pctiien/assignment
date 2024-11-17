package com.example.ascendaassignment.supplier;

import com.example.ascendaassignment.helper.DataHelper;
import com.example.ascendaassignment.model.Hotel;
import com.example.ascendaassignment.supplier.factory.SupplierFactory;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

public class PaperfliesSupplier extends BaseSupplier{

    // Automatically register to SupplierFactory
    static {
        SupplierFactory.registerSupplier(new PaperfliesSupplier());
    }

    @Override
    public String endpoint() {
        return "https://5f2be0b4ffc88500167b85a0.mockapi.io/suppliers/paperflies";
    }
    @Override
    public String getName() {
        return "Paperflies";
    }
    @SuppressWarnings("unchecked")
    @Override
    public Hotel parse(Object source) {

        var sourceData = (Map<String,Object>) source;

        Hotel hotel = new Hotel();

        // Base info
        hotel.setId(DataHelper.getString(sourceData,"hotel_id"));
        hotel.setDestination_id(DataHelper.getLong(sourceData,"destination_id"));
        hotel.setName(DataHelper.getString(sourceData,"hotel_name"));

        // Location
        var locationData = DataHelper.getMap(sourceData,"location");
        Hotel.Location location = new Hotel.Location();
        if(locationData!=null){
            location.setAddress(DataHelper.getString(locationData,"address"));
            location.setCountry(DataHelper.getString(locationData,"country"));
        }
        hotel.setLocation(location);

        // Description
        hotel.setDescription(sourceData.get("details").toString());

        // Amentities
        var amentitiesData = DataHelper.getMap(sourceData,"amenities");
        Hotel.Amenities amenities = new Hotel.Amenities();
        if(amentitiesData!=null)
        {
            amenities.setGeneral(DataHelper.getList(amentitiesData,"general"));
            amenities.setRoom(DataHelper.getList(amentitiesData,"room"));
        }

        hotel.setAmenities(amenities);

        // Images
        var imagesData = DataHelper.getMap(sourceData,"images");
        Hotel.Images images = new Hotel.Images();

            // Images rooms
        ArrayList<Object> roomsImagesData = DataHelper.getList(imagesData,"rooms");
        images.setRooms(roomsImagesData.stream().map(image -> {
            Hotel.ImageItem imageItem = new Hotel.ImageItem();
            Map<String, Object> imageMap = (Map<String, Object>) image;
            imageItem.setLink(DataHelper.getString(imageMap, "link"));
            imageItem.setDescription(DataHelper.getString(imageMap, "caption"));
            return imageItem;
        }).collect(Collectors.toCollection(ArrayList::new)));

            // Images site
        ArrayList<Object> siteImagesData = DataHelper.getList(imagesData,"site");
        images.setSite(siteImagesData.stream().map(image -> {
            Hotel.ImageItem imageItem = new Hotel.ImageItem();
            Map<String, Object> imageMap = (Map<String, Object>) image;
            imageItem.setLink(DataHelper.getString(imageMap, "link"));
            imageItem.setDescription(DataHelper.getString(imageMap, "caption"));
            return imageItem;
        }).collect(Collectors.toCollection(ArrayList::new)));

        hotel.setImages(images);

        // Booking conditions
        var bookingConditionsData = DataHelper.getList(sourceData,"booking_conditions");

        hotel.setBooking_conditions(bookingConditionsData);

        return hotel;
    }


}
