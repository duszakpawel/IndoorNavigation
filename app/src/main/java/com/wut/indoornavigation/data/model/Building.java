package com.wut.indoornavigation.data.model;

import java.util.List;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Building {
    List<Floor> floors;
}
