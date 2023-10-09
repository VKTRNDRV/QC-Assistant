package com.example.qcassistant.domain.entity.tag;

import com.example.qcassistant.domain.entity.destination.Destination;
import com.example.qcassistant.domain.entity.study.MedidataStudy;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "medidata_tags")
public class MedidataTag extends BaseTag{

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "medidata_tags_destinations",
            joinColumns = {@JoinColumn(name = "tag_id")},
            inverseJoinColumns = {@JoinColumn(name = "destination_id")}
    )
    private List<Destination> destinations;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "medidata_tags_studies",
            joinColumns = {@JoinColumn(name = "tag_id")},
            inverseJoinColumns = {@JoinColumn(name = "study_id")}
    )
    private List<MedidataStudy> studies;


    @Override
    public boolean hasDestinationPrecondition() {
        if(this.destinations == null || this
                .destinations.size() == 0){
            return false;
        }

        return true;
    }

    @Override
    public List<Destination> getDestinations() {
        return destinations;
    }

    @Override
    public boolean hasStudyPrecondition() {
        if(this.studies == null || this
                .studies.size() == 0){
            return false;
        }

        return true;
    }

    @Override
    public List<MedidataStudy> getStudies() {
        return studies;
    }
}
