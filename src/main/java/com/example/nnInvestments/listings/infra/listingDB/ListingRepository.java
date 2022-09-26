package com.example.nnInvestments.listings.infra.listingDB;

import com.example.nnInvestments.listings.domain.ListingData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.stream.Collectors;

@Repository
public interface ListingRepository extends MongoRepository<ListingData, String>, PagingAndSortingRepository<ListingData,String> {

    @Query(value="{ 'listingMetricDetails.status' : ?0 }", fields="{listingDetails: 0}")
    Page<ListingData> findAllByStatus(String status ,  Pageable pageable);

    default Page<ListingData>getPaginatedResults(String whereClause, int offset, int limit){
        Criteria criteria = getQueryFromWhereClause(whereClause);
        Pageable pageable = PageRequest.of(offset, limit);

        return null;
    }

    private Criteria getQueryFromWhereClause(String whereClause) {
        Criteria criteria = new Criteria();
        String[] whereClauseArray = whereClause.split(";");
        for(String whereClauseKeyValue : whereClauseArray){
            if(whereClauseKeyValue.contains("==")){
                String[] whereQuery = whereClauseKeyValue.split("==");
                String key = whereQuery[0];
                String value = whereQuery[1];
            }else if(whereClause.contains("=in=")){
                String[] whereQuery = whereClauseKeyValue.split("=in=");
                String key = whereQuery[0];
                String[] value = whereQuery[1].split(",");
                criteria.and(key).in(Arrays.stream(value).collect(Collectors.toList()));
            }
        }
        return criteria;
    }
}
