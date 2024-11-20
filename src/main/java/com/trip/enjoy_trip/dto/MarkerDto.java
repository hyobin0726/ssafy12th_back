package com.trip.enjoy_trip.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarkerDto {
    private Integer markerId;

    @NotNull(message = "latitude(위도) 가 없습니다.")
    private Double latitude;

    @NotNull(message = "longitude(경도) 가 없습니다.")
    private Double longitude;

    @NotNull(message = "attractionId 가 없습니다.")
    private Integer attractionId;

    @NotNull(message = "gugunId 가 없습니다.")
    private Integer gugunId;

    @NotNull(message = "sidoId 가 없습니다.")
    private Integer sidoId;
}
