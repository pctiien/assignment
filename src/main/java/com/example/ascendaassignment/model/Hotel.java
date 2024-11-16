package com.example.ascendaassignment.model;

import com.example.ascendaassignment.helper.StringHelper;
import lombok.Data;

import java.util.*;

@Data
public class Hotel implements Mergeable<Hotel>{
    private String id;
    private Long destination_id;
    private String name;
    private Location location;
    private String description;
    private Amenities amenities = new Amenities();
    private Images images = new Images();
    private ArrayList<String> booking_conditions = new ArrayList<>();

    @Override
    public void merge(Hotel other) {
        this.setId(other.getId()!=null ? other.getId() :this.getId());
        this.setDestination_id(other.getDestination_id()!=null ? other.getDestination_id() :this.getDestination_id());
        this.setName(other.getName()!=null ? other.getName() :this.getName());
        this.setDescription(other.getDescription()!=null ? other.getDescription() :this.getDescription());

        this.getAmenities().merge(other.getAmenities());
        this.getImages().merge(other.getImages());
        this.getAmenities().merge(other.getAmenities());
        this.getLocation().merge(other.getLocation());

        Set<String> bookingConditionsSet = new HashSet<>(this.getBooking_conditions());
        bookingConditionsSet.addAll(other.getBooking_conditions());
        this.setBooking_conditions(new ArrayList<>(bookingConditionsSet));
    }

    @Data
    public static class Amenities implements Mergeable<Amenities>{
        private ArrayList<String> general = new ArrayList<>();
        private ArrayList<String> room = new ArrayList<>();

        @Override
        public void merge(Amenities other) {
            Set<String> generalSet = new HashSet<>(StringHelper.toTrimmedLowerAndFormat(this.getGeneral()));
            generalSet.addAll(StringHelper.toTrimmedLowerAndFormat(other.getGeneral()));

            Set<String> roomSet = new HashSet<>(StringHelper.toTrimmedLowerAndFormat(this.getRoom()));
            roomSet.addAll(StringHelper.toTrimmedLowerAndFormat(other.getRoom()));

            this.setGeneral(new ArrayList<>(generalSet));
            this.setRoom(new ArrayList<>(roomSet));
        }
    }

    @Data
    public static class Images implements Mergeable<Images>{
        private ArrayList<ImageItem> rooms = new ArrayList<>();
        private ArrayList<ImageItem> site = new ArrayList<>();
        private ArrayList<ImageItem> amenities = new ArrayList<>();


        private Map<String, ImageItem> mergeImageItem(Set<ImageItem> set) {
            Map<String,ImageItem> map = new HashMap<>();
            set.forEach(room->{
                if(map.containsKey(room.getLink()))
                {
                    ImageItem existingItem = map.get(room.getLink());
                    existingItem.merge(room);
                    map.put(room.getLink(),existingItem);
                }else{
                    map.put(room.getLink(),room);
                }
            });
            return map;
        }

        @Override
        public void merge(Images other) {
            Set<ImageItem> roomsSet = new HashSet<>(this.getRooms());
            roomsSet.addAll(other.getRooms());
            Map<String,ImageItem> roomsMap = mergeImageItem(roomsSet);

            Set<ImageItem> siteSet = new HashSet<>(this.getSite());
            siteSet.addAll(other.getSite());
            Map<String,ImageItem> siteMap = mergeImageItem(siteSet);

            Set<ImageItem> amenitiesSet = new HashSet<>(this.getAmenities());
            amenitiesSet.addAll(other.getAmenities());
            Map<String,ImageItem> amenitiesMap = mergeImageItem(amenitiesSet);

            this.setRooms(new ArrayList<>(roomsMap.values()));
            this.setSite(new ArrayList<>(siteMap.values()));
            this.setAmenities(new ArrayList<>(amenitiesMap.values()));
        }
    }

    @Data
    public static class Location implements Mergeable<Location>{
        private Double lat;
        private Double lng;
        private String address;
        private String city;
        private String country;


        @Override
        public void merge(Location other) {
            this.setLat(other.getLat()!=null ? other.getLat(): this.getLat());
            this.setLng(other.getLng()!=null ? other.getLng(): this.getLng());
            this.setAddress(other.getAddress()!=null ? other.getAddress(): this.getAddress());
            this.setCity(other.getCity()!=null ? other.getCity(): this.getCity());
            this.setCountry(other.getCountry()!=null ? other.getCountry(): this.getCountry());
        }
    }

    @Data
    public static class ImageItem implements Mergeable<ImageItem>{
        private String link;
        private String description;

        @Override
        public void merge(ImageItem other) {
            this.setLink(other.getLink()!=null ? other.getLink(): this.getLink());
            this.setDescription(other.getDescription()!=null ? other.getDescription() : this.getDescription());

        }
    }
}