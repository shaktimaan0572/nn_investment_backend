package com.example.nnInvestments.listings.entity.request;

import com.example.nnInvestments.listings.domain.ListingDetails;
import com.example.nnInvestments.listings.domain.ListingMetricDetails;
import lombok.Data;

import java.util.List;

@Data
public class ListingRequest {
    private String propertyName;
    private String address;
    private double area;
    private int pricePerSqFt;
    private double yield;
    private double roi;
    private List<String> images;
    private ListingDetails listingDetails;
    private ListingMetricDetails listingMetricDetails;
}
