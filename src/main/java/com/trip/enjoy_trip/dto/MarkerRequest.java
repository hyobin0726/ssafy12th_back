package com.trip.enjoy_trip.dto;

public class MarkerRequest {

    private Double latitude;
    private Double longitude;
    private Integer attractionId;
    private Integer gugunId;
    private Integer sidoId;

    // Getters and Setters
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Integer getAttractionId() {
        return attractionId;
    }

    public void setAttractionId(Integer attractionId) {
        this.attractionId = attractionId;
    }

    public Integer getGugunId() {
        return gugunId;
    }

    public void setGugunId(Integer gugunId) {
        this.gugunId = gugunId;
    }

    public Integer getSidoId() {
        return sidoId;
    }

    public void setSidoId(Integer sidoId) {
        this.sidoId = sidoId;
    }
}
