package com.example.nnInvestments.listings.domain;

import lombok.Data;

import java.util.Map;

@Data
public class ListingDetails {
    private PropInvestmentDetails propInvestMentDetails;
    private InvestmentDetails investmentDetails;
    private TenantDetails tenantDetails;
    private InvestmentOverview investmentOverview;
    private Reports reports;


    @Data
    private static class PropInvestmentDetails {
        Map<String, Details> financialDetails;
        private String minimumInvestment;
        private String maximumInvestment;

        @Data
        private static class Details {
            private String value;
            private String description;
        }
    }


    @Data
    private static class InvestmentDetails {
        private Integer totalInvestment;
        private Map<String, BreakUp> investmentBreakup;

        @Data
        private static class BreakUp {
            private Integer rentAmt;
            private Integer propMgmtFee;
            private String tax;
        }
    }


    @Data
    private static class TenantDetails {
        private String name;
        private String country;
        private String description;
        private TimePeriod timePeriod;
        private HighLights highLights;
        private PropertyPricing propertyPricing;
        private LocationHighlights locationHighlights;

        @Data
        private static class TimePeriod {
            private String leaseStartDate;
            private String lockInPeriod;
            private String leaseEndDate;
        }

        @Data
        private static class HighLights {
            private String totalArea;
            private String rentPerSF;
            private String leasePeriod;
            private String escalation;
        }

        @Data
        private static class PropertyPricing {
            private String purchasePrice;
            private String stampDuty;
            private String brokerage;
            private String legalFee;
            private String reserves;
        }

        @Data
        private static class LocationHighlights {
            private String description;
            private String mapImage;
        }
    }

    @Data
    private static class InvestmentOverview {
        private String description;
    }

    @Data
    private static class Reports {
        private String investmentNoteLink;
        private String legalReportLink;
    }
}
