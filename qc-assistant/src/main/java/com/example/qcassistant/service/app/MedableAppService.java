package com.example.qcassistant.service.app;

import com.example.qcassistant.domain.dto.app.AppAddDto;
import com.example.qcassistant.domain.dto.app.AppEditDto;
import com.example.qcassistant.domain.entity.app.IqviaApp;
import com.example.qcassistant.domain.entity.app.MedableApp;
import com.example.qcassistant.domain.entity.app.MedidataApp;
import com.example.qcassistant.repository.app.MedableAppRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedableAppService extends BaseAppService{

    private MedableAppRepository appRepository;

    @Autowired
    public MedableAppService(ModelMapper modelMapper, MedableAppRepository appRepository) {
        super(modelMapper);
        this.appRepository = appRepository;
    }

    public void addApp(AppAddDto appAddDto) {
        validateNewApp(appAddDto);
        MedableApp app = super.modelMapper.map(appAddDto, MedableApp.class);
        this.appRepository.save(app);
    }

    private void validateNewApp(AppAddDto appAddDto) {
        appAddDto.trimStringFields();
        String name = appAddDto.getName();
        super.validateNameNotBlank(name);
        this.validateUniqueName(name);
    }

    private void validateEditApp(AppEditDto appEditDto){
        String name = appEditDto.getName();
        validateNameNotBlank(name);

        // if app name changed - validate unique
        if(!this.appRepository.findById(appEditDto.getId()).get()
                .getName().trim()
                .equals(name)){
            validateUniqueName(name);
        }
    }

    public void editApp(AppEditDto editDto) {
        validateEditApp(editDto);
        MedableApp editedApp = this.modelMapper
                .map(editDto, MedableApp.class);
        this.appRepository.save(editedApp);
    }

    @Override
    protected void validateUniqueName(String name){
        if(this.appRepository.findFirstByName(name).isPresent()){
            throw new RuntimeException("App '" + name + "' already present");
        }
    }
}
