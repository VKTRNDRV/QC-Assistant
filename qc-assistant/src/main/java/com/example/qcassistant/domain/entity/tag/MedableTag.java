package com.example.qcassistant.domain.entity.tag;

import com.example.qcassistant.domain.entity.destination.Destination;
import com.example.qcassistant.domain.entity.study.MedableStudy;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "medable_tags")
public class MedableTag extends BaseTag{

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "medable_tags_destinations",
            joinColumns = {@JoinColumn(name = "tag_id")},
            inverseJoinColumns = {@JoinColumn(name = "destination_id")}
    )
    private List<Destination> destinations;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "medable_tags_studies",
            joinColumns = {@JoinColumn(name = "tag_id")},
            inverseJoinColumns = {@JoinColumn(name = "study_id")}
    )
    private List<MedableStudy> studies;

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
    public List<MedableStudy> getStudies() {
        return studies;
    }
}
