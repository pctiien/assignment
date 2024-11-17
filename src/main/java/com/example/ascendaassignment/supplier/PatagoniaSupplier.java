package com.example.ascendaassignment.supplier;

import com.example.ascendaassignment.helper.DataHelper;
import com.example.ascendaassignment.model.Hotel;
import com.example.ascendaassignment.supplier.factory.SupplierFactory;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

public class PatagoniaSupplier extends BaseSupplier{

    // Automatically register to SupplierFactory
    static {
        SupplierFactory.registerSupplier(new PatagoniaSupplier());
    }

    @Override
    public String endpoint() {
        return "https://5f2be0b4ffc88500167b85a0.mockapi.io/suppliers/patagonia";
    }
    @Override
    public String getName() {
        return "Patagonia";
    }
    @SuppressWarnings("unchecked")
    @Override
    public Hotel parse(Object source) {

        var sourceData = (Map<String,Object>) source;

        Hotel hotel = new Hotel();

        // Base info
        hotel.setId(DataHelper.getString(sourceData,"id"));
        hotel.setDestination_id(DataHelper.getLong(sourceData,"destination"));
        hotel.setName(DataHelper.getString(sourceData,"name"));

        // Location
        Hotel.Location location = new Hotel.Location();
        location.setAddress(DataHelper.getString(sourceData,"address"));
        location.setLat(DataHelper.getDouble(sourceData,"lat"));
        location.setLng(DataHelper.getDouble(sourceData,"lng"));

        hotel.setLocation(location);

        // Description
        hotel.setDescription(DataHelper.getString(sourceData,"info"));

        // Amentities
        Hotel.Amenities amenities = new Hotel.Amenities();
        amenities.setRoom(DataHelper.getList(sourceData,"amenities"));

        hotel.setAmenities(amenities);

        // Images
        var imagesData = DataHelper.getMap(sourceData,"images");

        Hotel.Images images = new Hotel.Images();
            // Images rooms
        ArrayList<Object> roomsImagesData = DataHelper.getList(imagesData,"rooms");
        images.setRooms(roomsImagesData.stream().map(image->{
            Hotel.ImageItem imageItem = new Hotel.ImageItem();
            Map<String,Object> imageMap = (Map<String,Object>)image ;
            imageItem.setLink(DataHelper.getString(imageMap,"url"));
            imageItem.setDescription(DataHelper.getString(imageMap,"description"));
            return imageItem;
        }).collect(Collectors.toCollection(ArrayList::new)));

            // Images amenities
        ArrayList<Object> amenitiesImagesData = DataHelper.getList(imagesData,"amenities");
        images.setAmenities(amenitiesImagesData.stream().map(image->{
            Hotel.ImageItem imageItem = new Hotel.ImageItem();
            Map<String,Object> imageMap = (Map<String,Object>)image ;
            imageItem.setLink(DataHelper.getString(imageMap,"url"));
            imageItem.setDescription(DataHelper.getString(imageMap,"description"));
            return imageItem;
        }).collect(Collectors.toCollection(ArrayList::new)));

        hotel.setImages(images);


        return hotel;
    }


}
