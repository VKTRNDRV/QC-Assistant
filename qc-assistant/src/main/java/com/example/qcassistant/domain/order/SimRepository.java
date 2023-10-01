package com.example.qcassistant.domain.order;

import com.example.qcassistant.domain.item.sim.SerializedSIM;

import java.util.ArrayList;
import java.util.Collection;

public class SimRepository {

    private Collection<SerializedSIM> sims;

    public SimRepository(){
        this.sims = new ArrayList<>();
    }

    public void addSim(SerializedSIM serializedSIM) {
        sims.add(serializedSIM);
    }
}
