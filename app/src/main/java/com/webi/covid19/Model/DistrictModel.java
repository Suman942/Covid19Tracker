package com.webi.covid19.Model;

public class DistrictModel {

     public String district,confirmed,deceased,recovered,active;



    public DistrictModel(String district, String confirmed, String deceased, String recovered,String active) {
        this.district = district;
        this.confirmed = confirmed;
        this.deceased = deceased;
        this.recovered = recovered;
        this.active = active;
    }
}
