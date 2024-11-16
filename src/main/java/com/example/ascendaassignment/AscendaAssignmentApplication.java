package com.example.ascendaassignment;

import com.example.ascendaassignment.model.Hotel;
import com.example.ascendaassignment.service.HotelService;
import com.example.ascendaassignment.supplier.BaseSupplier;
import com.example.ascendaassignment.supplier.factory.SupplierFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.reflections.Reflections;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class AscendaAssignmentApplication {

    // Initialize suppliers for automatically register to factory ( executing static block )
    public static void initializeSuppliers(String packageName) {
        Reflections reflections = new Reflections(packageName);
        Set<Class<? extends BaseSupplier>> supplierClasses = reflections.getSubTypesOf(BaseSupplier.class);
        supplierClasses.forEach(clazz -> {
            try {
                Class.forName(clazz.getName());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static String fetchHotels(String[] hotelIds, Long[] destinationIds){
        List<BaseSupplier> suppliers = SupplierFactory.getAllSuppliers();

        // Fetch all standardized data
        List<Hotel> allHotelsData = new ArrayList<>();
        suppliers.forEach(supplier->{
            allHotelsData.addAll(supplier.fetch());
        });

        HotelService hotelService = new HotelService();
        hotelService.mergeAndSave(allHotelsData);

        List<Hotel> filteredHotels = hotelService.find(hotelIds,destinationIds);


        // Use for returning json value as String
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(filteredHotels);
        } catch (JsonProcessingException e) {
            LoggerFactory.getLogger(AscendaAssignmentApplication.class).error(e.getMessage());
        }
        return "";
    }

    public static void main(String[] args) {

        SpringApplication.run(AscendaAssignmentApplication.class, args);

        // Automatically register to Supplier Factory
        initializeSuppliers("com.example.ascendaassignment.supplier");

        String[] hotelIds = new String[]{"iJhz"};
        Long[] destinationIds = new Long[]{5432L};

        System.out.println(fetchHotels(hotelIds,destinationIds));


    }

}
