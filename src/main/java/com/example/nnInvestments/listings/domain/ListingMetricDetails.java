package com.example.nnInvestments.listings.domain;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

@Data
public class ListingMetricDetails implements Comparable<ListingMetricDetails> {
    private String status;
    private int totalCredit;
    private int responseCount;
    private long lastUpdated;
    private long startDate;
    private long endDate;


    private static final Logger LOGGER = LoggerFactory.getLogger(ListingMetricDetails.class);

    public void captureResponse(){
        this.responseCount++;
        this.lastUpdated = Instant.now().toEpochMilli();
    }

    public void updateStatus(String status){
        this.status = status;
    }

    public BigDecimal getFulfillmentRatio() {

        int subscriptionDuration = getSubscriptionDuration(this.startDate, this.endDate);
        int subscriptionMonth = getSubscriptionMonth(this.startDate);
        long timeDuration = (Instant.now().toEpochMilli() - this.lastUpdated) / 1000000;
        long lastUpdatedDuration = timeDuration == 0 ? 1 : timeDuration;

        LOGGER.info("details {} , {} , {}" , subscriptionDuration, subscriptionMonth, lastUpdatedDuration);

        double moneyPerMonth = ((double) this.totalCredit / (subscriptionDuration * lastUpdatedDuration)) * subscriptionMonth;

        LOGGER.info("moneyPerMonth details {} " , moneyPerMonth);

        moneyPerMonth = moneyPerMonth == 0.0D ? 1.0D : moneyPerMonth;
        long remainingDaysInCurrentMonth = getRemainingDaysInEndMonth(this.endDate);

        LOGGER.info("remainingDaysInCurrentMonth details {} " , remainingDaysInCurrentMonth);
        double countMoneyRation = this.responseCount / moneyPerMonth;

        LOGGER.info("countMoneyRation details {} " , countMoneyRation);

        return new BigDecimal(countMoneyRation).multiply(BigDecimal.valueOf((double) remainingDaysInCurrentMonth / 30));
    }

    private int getSubscriptionDuration(Long startEpochDate, Long endEpochDate) {

        Instant startDate = Instant.ofEpochMilli(startEpochDate);
        Instant endDate = Instant.ofEpochMilli(endEpochDate);
        Duration duration = Duration.between(startDate, endDate);

        double days = (double) duration.toDays();
        return (int) Math.ceil(days / 30.0);

    }

    private int getSubscriptionMonth(Long startEpochDate) {
        Instant currentInstantDate = Instant.now();
        Instant currentDate = Instant.ofEpochMilli(currentInstantDate.toEpochMilli());
        Instant startDate = Instant.ofEpochMilli(startEpochDate);
        Duration duration = Duration.between(startDate, currentDate);
        double days = (double) duration.toDays();
        int months = (int) Math.ceil(days / 30.0);
        return months + 1;
    }

    private long getRemainingDaysInEndMonth(Long endEpochDate) {
        LocalDate endDate = Instant.ofEpochMilli(endEpochDate).atZone(ZoneId.systemDefault()).toLocalDate();

        Instant currentInstantDate = Instant.now();
        LocalDate currentDate = Instant.ofEpochMilli(currentInstantDate.toEpochMilli()).atZone(ZoneId.systemDefault()).toLocalDate();
        long daysDiff = ChronoUnit.DAYS.between(currentDate, endDate);

        return daysDiff > 30 ? 30 : daysDiff + 1;

    }

    @Override
    public int compareTo(ListingMetricDetails listingMetricDetails) {
        return this.getFulfillmentRatio().compareTo(listingMetricDetails.getFulfillmentRatio());
    }
}
