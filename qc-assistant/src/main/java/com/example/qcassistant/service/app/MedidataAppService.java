package com.example.qcassistant.service.app;

import com.example.qcassistant.domain.dto.app.AppAddDto;
import com.example.qcassistant.domain.dto.app.AppEditDto;
import com.example.qcassistant.domain.entity.app.MedidataApp;
import com.example.qcassistant.repository.app.MedidataAppRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedidataAppService extends BaseAppService{

    private MedidataAppRepository appRepository;

    @Autowired
    public MedidataAppService(MedidataAppRepository appRepository,
                              ModelMapper modelMapper) {
        super(modelMapper);
        this.appRepository = appRepository;
    }


    @Override
    public void addApp(AppAddDto appAddDto) {
        validateNewApp(appAddDto);
        MedidataApp app = super.modelMapper.map(appAddDto, MedidataApp.class);
        this.appRepository.save(app);
    }

    @Override
    public void editApp(AppEditDto editDto) {
        validateEditApp(editDto);
        MedidataApp editedApp = this.modelMapper
                .map(editDto, MedidataApp.class);
        this.appRepository.save(editedApp);
    }

    @Override
    protected MedidataAppRepository getAppRepository(){
        return this.appRepository;
    }


    @Override
    protected List<MedidataApp> getEntities() {
        return getAppRepository().findAll();
    }

    @Override
    protected Optional<MedidataApp> findFirstByName(String name) {
        return getAppRepository().findFirstByName(name);
    }
}
