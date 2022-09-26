package com.example.nnInvestments.listings.domain;

import com.example.nnInvestments.listings.entity.request.ListingRequest;
import com.example.nnInvestments.listings.entity.response.PaginatedResponse;
import com.example.nnInvestments.listings.infra.listingDB.ListingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ListingService {

    @Autowired
    ListingRepository listingRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(ListingService.class);

    public void createListing(ListingRequest listingRequest) {

        ListingData listingData = ListingData.createListingData(listingRequest);
        ListingData listingSavedData = listingRepository.save(listingData);
        LOGGER.info("Listing data saved with : " + listingSavedData);
    }

    public void captureResponse(String listingId) {
        listingRepository
                .findById(listingId)
                .ifPresent(listingData -> {
                    listingData .getListingMetricDetails().captureResponse();
                    listingRepository.save(listingData);
                        }
                );
    }

    public void updateStatus(String listingId, final String status) {
        listingRepository
                .findById(listingId)
                .ifPresent(listing -> {
                    listing.getListingMetricDetails().setStatus(status);
                    listingRepository.save(listing);
                });

    }

    public PaginatedResponse getAllListing(String whereClause, int offset, int limit) {


        //Page<ListingData> listingDataPage = listingRepository.getPaginatedResults(whereClause, offset, limit);
        Page<ListingData> listingDataPage = listingRepository.findAll(PageRequest.of(offset, limit));
        List<ListingData> response = new ArrayList<>(listingDataPage.getContent());
        response.sort(Comparator.comparing(ListingData::getListingMetricDetails));

        return PaginatedResponse
                .builder()
                .responses(response)
                .currentPage(listingDataPage.getNumber())
                .totalItem(listingDataPage.getTotalElements())
                .totalPages(listingDataPage.getTotalPages())
                .build();

    }


    public ListingData getListingById(String listingId) {
        return listingRepository.findById(listingId).orElse(null);
    }
}
