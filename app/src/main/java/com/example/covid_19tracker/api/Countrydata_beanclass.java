package com.example.covid_19tracker.api;

import java.util.Map;

public class Countrydata_beanclass {

    String country,updated;
    String cases,todayCases,active,recovered,todayRecovered,deaths,todayDeaths,tests;
    Map<String, String> countryInfo;
    String population;

    public Countrydata_beanclass(String country, String updated, String cases, String todayCases, String active, String recovered, String todayRecovered, String deaths, String todayDeaths, String tests, Map<String, String> countryInfo, String population) {
        this.country = country;
        this.updated = updated;
        this.cases = cases;
        this.todayCases = todayCases;
        this.active = active;
        this.recovered = recovered;
        this.todayRecovered = todayRecovered;
        this.deaths = deaths;
        this.todayDeaths = todayDeaths;
        this.tests = tests;
        this.countryInfo = countryInfo;
        this.population = population;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getUpdate() {
        return updated;
    }

    public void setUpdate(String updated) {
        this.updated = updated;
    }

    public String getCases() {
        return cases;
    }

    public void setCases(String cases) {
        this.cases = cases;
    }

    public String getTodayCases() {
        return todayCases;
    }

    public void setTodayCases(String todayCases) {
        this.todayCases = todayCases;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

    public String getTodayRecovered() {
        return todayRecovered;
    }

    public void setTodayRecovered(String todayRecovered) {
        this.todayRecovered = todayRecovered;
    }

    public String getDeaths() {
        return deaths;
    }

    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }

    public String getTodayDeaths() {
        return todayDeaths;
    }

    public void setTodayDeaths(String todayDeaths) {
        this.todayDeaths = todayDeaths;
    }

    public String getTests() {
        return tests;
    }

    public void setTests(String tests) {
        this.tests = tests;
    }

    public Map<String, String> getCountryInfo() {
        return countryInfo;
    }

    public void setCountryInfo(Map<String, String> countryInfo) {
        this.countryInfo = countryInfo;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }
}
