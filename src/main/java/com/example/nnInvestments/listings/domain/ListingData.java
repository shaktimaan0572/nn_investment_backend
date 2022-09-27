package com.example.nnInvestments.listings.domain;

import com.example.nnInvestments.listings.entity.request.ListingRequest;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Document(collection = "listingData")
@Builder
@Getter
public class ListingData {

    @Id
    private String id;
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

    public static ListingData createListingData(ListingRequest listingRequest) {
        return ListingData
                .builder()
                .propertyName(listingRequest.getPropertyName())
                .address(listingRequest.getAddress())
                .area(listingRequest.getArea())
                .pricePerSqFt(listingRequest.getPricePerSqFt())
                .yield(listingRequest.getYield())
                .roi(listingRequest.getRoi())
                .propStatus(listingRequest.getPropStatus())
                .images(listingRequest.getImages())
                .listingDetails(listingRequest.getListingDetails())
                .listingMetricDetails(createListingMetricData(listingRequest.getListingMetricDetails()))
                .build();
    }

    private static ListingMetricDetails createListingMetricData(ListingMetricDetails listingMetricDetailsReq) {
        ListingMetricDetails listingMetricDetails = new ListingMetricDetails();
        listingMetricDetails.setEndDate(listingMetricDetailsReq.getEndDate());
        listingMetricDetails.setStartDate(listingMetricDetailsReq.getStartDate());
        listingMetricDetails.setTotalCredit(listingMetricDetailsReq.getTotalCredit());
        listingMetricDetails.setResponseCount(1);
        listingMetricDetails.setStatus("ACTIVE");
        return listingMetricDetails;
    }

}
