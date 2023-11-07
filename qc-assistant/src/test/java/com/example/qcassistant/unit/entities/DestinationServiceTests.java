package com.example.qcassistant.unit.entities;

import com.example.qcassistant.domain.entity.BaseEntity;
import com.example.qcassistant.domain.entity.destination.Destination;
import com.example.qcassistant.service.DestinationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

@SpringBootTest
public class DestinationServiceTests {

    private DestinationService destinationService;

    @Autowired
    public DestinationServiceTests(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    @Test
    public void getEntities_DoesNotReturnUNKNOWN(){
        List<Destination> destinations = this
                .destinationService.getEntities();

        for(Destination destination : destinations){
            if(destination.getName().equals(BaseEntity.UNKNOWN)){
                Assertions.fail("UNKNOWN destination found");
            }
        }

        Assertions.assertTrue(true);
    }

//    @Test
//    public void testTest2(){
//        List<Destination> destinations = this
//                .destinationService.getEntities();
//
//        for(Destination destination : destinations){
//            if(destination.getName().equals(BaseEntity.UNKNOWN)){
//                Assertions.fail("UNKNOWN destination found");
//            }
//        }
//
//        Assertions.assertTrue(false);
//    }
}
