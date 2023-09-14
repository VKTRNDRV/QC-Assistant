package com.example.qcassistant.domain.order;

import com.example.qcassistant.domain.item.sim.SerializedSIM;

import java.util.ArrayList;
import java.util.Collection;

public class SerializedSimRepository {

    private Collection<SerializedSIM> sims;

    public SerializedSimRepository(){
        this.sims = new ArrayList<>();
    }
}
