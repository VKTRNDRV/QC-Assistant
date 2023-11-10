package com.example.qcassistant.unit.entities.app;

import com.example.qcassistant.domain.dto.app.AppAddDto;
import com.example.qcassistant.domain.dto.app.AppEditDto;
import com.example.qcassistant.domain.dto.sponsor.SponsorAddDto;
import com.example.qcassistant.domain.dto.sponsor.SponsorEditDto;
import com.example.qcassistant.domain.entity.BaseEntity;
import com.example.qcassistant.domain.entity.app.MedidataApp;
import com.example.qcassistant.domain.entity.destination.Language;
import com.example.qcassistant.repository.app.MedidataAppRepository;
import com.example.qcassistant.service.app.MedidataAppService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MedidataAppServiceTests {

    private MedidataAppService appService;

    private MedidataAppRepository appRepository;

    @Autowired
    public MedidataAppServiceTests(MedidataAppService appService,
                                   MedidataAppRepository appRepository) {
        this.appService = appService;
        this.appRepository = appRepository;
    }

    @Test
    public void testAddAppWithBlankName() {
        AppAddDto appAddDto = new AppAddDto()
                .setName("  ");

        Assertions.assertThrows(RuntimeException.class, () ->
                appService.addApp(appAddDto));
    }

    @Test
    public void testAddAppWithExistingName() {
        AppAddDto appAddDto = new AppAddDto()
                .setName(MedidataApp.PATIENT_CLOUD);


        Assertions.assertThrows(RuntimeException.class, () ->
                appService.addApp(appAddDto));
    }

    @Test
    public void testEditAppWithBlankName() {
        AppEditDto editDto = new AppEditDto()
                .setName("  ");

        Assertions.assertThrows(RuntimeException.class, () ->
                appService.editApp(editDto));
    }

    @Test
    public <T> void getAllEditApps_ReturnsCorrectCountAndType(){
        List<T> fromService = (List<T>) this
                .appService.getAllEditApps();

        Assertions.assertEquals(fromService.size(),
                appRepository.count());

        Assertions.assertEquals(fromService.get(0).getClass(),
                AppEditDto.class);
    }

    @Test
    public void getEditAppById_ReturnsCorrectApp(){
        MedidataApp patientCloud = this.appRepository
                .findFirstByName(MedidataApp.PATIENT_CLOUD)
                .get();

        AppEditDto fromService = this.appService
                .getEditAppById(patientCloud.getId());

        Assertions.assertEquals(patientCloud.getName(),
                fromService.getName());
    }
}
