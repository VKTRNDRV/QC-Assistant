package com.example.qcassistant.domain.entity.tag;

import com.example.qcassistant.domain.entity.destination.Destination;
import com.example.qcassistant.domain.entity.study.IqviaStudy;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "iqvia_tags")
public class IqviaTag extends BaseTag{

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "iqvia_tags_destinations",
            joinColumns = {@JoinColumn(name = "tag_id")},
            inverseJoinColumns = {@JoinColumn(name = "destination_id")}
    )
    private List<Destination> destinations;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "iqvia_tags_studies",
            joinColumns = {@JoinColumn(name = "tag_id")},
            inverseJoinColumns = {@JoinColumn(name = "study_id")}
    )
    private List<IqviaStudy> studies;

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
    public List<IqviaStudy> getStudies() {
        return studies;
    }
}
