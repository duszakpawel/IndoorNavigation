package com.wut.indoornavigation.data.model;

import java.util.List;

import lombok.Builder;
import lombok.Value;

/**
 * Class representing building. Contains all floors
 */
@Value
@Builder
public class Building {
    List<Floor> floors;
}
