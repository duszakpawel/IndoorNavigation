package com.wut.indoornavigation.logic.graph.models;


import com.wut.indoornavigation.data.model.Point;

import java.util.Objects;

public class Vertex {
    public final Integer id;
    public final Point position;

    public Vertex(Integer id, Point position){
        this.id = id;
        this.position = position;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
        {
            return true;
        }

        if (obj == null)
        {
            return false;
        }

        if (getClass() != obj.getClass())
        {
            return false;
        }

        Vertex other = (Vertex) obj;

        return Objects.equals(id, other.id);
    }
}
