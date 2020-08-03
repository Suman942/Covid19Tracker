package com.webi.covid19.Model;

public class CountryMode {
    public String flag, country,cases,todayCases,deaths,todayDeaths,recovered,active,critical;

    public CountryMode(String flag, String country, String cases, String todayCases, String deaths, String todayDeaths, String recovered, String active, String critical) {
        this.flag = flag;
        this.country = country;
        this.cases = cases;
        this.todayCases = todayCases;
        this.deaths = deaths;
        this.todayDeaths = todayDeaths;
        this.recovered = recovered;
        this.active = active;
        this.critical = critical;
    }


    public CountryMode(String flagUrl, String countryName, String cases, String deaths, String todaydeaths, String recovered, String active, String critical) {
    }
}
