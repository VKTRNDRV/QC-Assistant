package com.example.qcassistant.unit.entityServices.app;

import com.example.qcassistant.domain.dto.app.AppAddDto;
import com.example.qcassistant.domain.dto.app.AppEditDto;
import com.example.qcassistant.domain.entity.app.MedableApp;
import com.example.qcassistant.domain.entity.app.MedidataApp;
import com.example.qcassistant.repository.app.MedableAppRepository;
import com.example.qcassistant.service.app.MedableAppService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MedableAppServiceTests {

    private MedableAppService appService;

    private MedableAppRepository appRepository;

    @Autowired
    public MedableAppServiceTests(MedableAppService appService,
                                  MedableAppRepository appRepository) {
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
                .setName(MedableAppService.CONTENT_APP_NAME);


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
        MedableApp content = this.appRepository
                .findFirstByName(MedableAppService.CONTENT_APP_NAME)
                .get();

        AppEditDto fromService = this.appService
                .getEditAppById(content.getId());

        Assertions.assertEquals(content.getName(),
                fromService.getName());
    }
}
