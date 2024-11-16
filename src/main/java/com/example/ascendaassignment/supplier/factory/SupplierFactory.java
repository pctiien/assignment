package com.example.ascendaassignment.supplier.factory;

import com.example.ascendaassignment.supplier.BaseSupplier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SupplierFactory {
    private static final Map<String, BaseSupplier> suppliers = new HashMap<>();

    public static void registerSupplier(BaseSupplier supplier) {
        suppliers.put(supplier.getName(),supplier);
    }
    public static List<BaseSupplier> getAllSuppliers()
    {
        return new ArrayList<>(suppliers.values());
    }
    public static BaseSupplier getSuppliers(String supplierName){
        return suppliers.get(supplierName);
    }
}
