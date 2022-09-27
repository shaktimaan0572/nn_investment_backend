package com.example.nnInvestments.listings.entity.request;

import com.example.nnInvestments.listings.domain.ListingDetails;
import com.example.nnInvestments.listings.domain.ListingMetricDetails;
import lombok.Data;

import java.util.List;

@Data
public class ListingRequest {
    private String propertyName;
    private String address;
    private String area;
    private String pricePerSqFt;
    private String yield;
    private String roi;
    private String propStatus;
    private List<String> images;
    private ListingDetails listingDetails;
    private ListingMetricDetails listingMetricDetails;
}
