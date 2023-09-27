package com.example.qcassistant.service.app;

import com.example.qcassistant.domain.dto.app.AppAddDto;
import com.example.qcassistant.domain.dto.app.AppEditDto;
import com.example.qcassistant.domain.entity.app.MedidataApp;
import com.example.qcassistant.repository.app.MedidataAppRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedidataAppService extends BaseAppService{

    private MedidataAppRepository appRepository;

    @Autowired
    public MedidataAppService(MedidataAppRepository appRepository,
                              ModelMapper modelMapper) {
        super(modelMapper);
        this.appRepository = appRepository;
    }


    public void addApp(AppAddDto appAddDto) {
        validateNewApp(appAddDto);
        MedidataApp app = super.modelMapper.map(appAddDto, MedidataApp.class);
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

    public AppEditDto getEditAppById(Long id) {
        return this.modelMapper.map(
                this.appRepository.findById(id).get(),
                AppEditDto.class);
    }

    public List<AppEditDto> getAllEditApps() {
        return this.appRepository.findAll()
                .stream().map((a) -> this.modelMapper
                        .map(a, AppEditDto.class))
                .sorted((a1,a2) -> a1.getName().compareTo(a2.getName()))
                .collect(Collectors.toList());
    }

    public void editApp(AppEditDto editDto) {
        validateEditApp(editDto);
        MedidataApp editedApp = this.modelMapper
                .map(editDto, MedidataApp.class);
        this.appRepository.save(editedApp);
    }

    @Override
    protected void validateUniqueName(String name){
        if(this.appRepository.findFirstByName(name).isPresent()){
            throw new RuntimeException("App \"" + name + "\" already present");
        }
    }
}
