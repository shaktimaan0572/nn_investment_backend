package com.example.nnInvestments.listings.apis;

import com.example.nnInvestments.listings.domain.ListingData;
import com.example.nnInvestments.listings.domain.ListingService;
import com.example.nnInvestments.listings.entity.request.ListingRequest;
import com.example.nnInvestments.listings.entity.response.PaginatedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/nnInvestment")
public class ListingController {

    @Autowired
    ListingService listingService;

    @PostMapping("/listing")
    public ResponseEntity<?> createListing(@RequestBody ListingRequest listingRequest){
        listingService.createListing(listingRequest);
        return ResponseEntity.ok().body(Boolean.TRUE);
    }

    @PostMapping("/listing/bulk")
    public ResponseEntity<?> createBulkListing(@RequestBody List<ListingRequest> listingRequests){
        listingService.createBulkListing(listingRequests);
        return ResponseEntity.ok().body(Boolean.TRUE);
    }

    @GetMapping("/listings")
    public ResponseEntity<?> getAllListing(
            @RequestParam(required = false) String whereClause,
            @RequestParam(required = false, defaultValue = "0") int offset,
            @RequestParam(required = false, defaultValue = "10") int limit){
        PaginatedResponse paginatedResponse = listingService.getAllListing(whereClause, offset , limit);
        return ResponseEntity.ok().body(paginatedResponse);
    }

    @GetMapping("/listing")
    public ResponseEntity<?> getListingById(@RequestParam String listingId){
        ListingData listingData = listingService.getListingById(listingId);
        return ResponseEntity.ok().body(listingData);
    }

    @PutMapping("/listing/response/{listingId}")
    public ResponseEntity<?> captureResponse(@PathVariable String listingId){
        listingService.captureResponse(listingId);
        return ResponseEntity.ok().body(Boolean.TRUE);
    }

    @PutMapping("/listing/status/{listingId}")
    public ResponseEntity<?> updateStatus(@PathVariable String listingId, @RequestParam String status){
        listingService.updateStatus(listingId,status);
        return ResponseEntity.ok().body(Boolean.TRUE);
    }

}
