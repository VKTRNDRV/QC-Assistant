package com.example.qcassistant.service.app;

import com.example.qcassistant.domain.dto.app.MedidataAppAddDto;
import com.example.qcassistant.domain.dto.app.MedidataAppEditDto;
import com.example.qcassistant.domain.entity.app.MedidataApp;
import com.example.qcassistant.repository.app.MedidataAppRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedidataAppService {

    private MedidataAppRepository appRepository;
    private ModelMapper modelMapper;

    @Autowired
    public MedidataAppService(MedidataAppRepository appRepository,
                              ModelMapper modelMapper) {
        this.appRepository = appRepository;
        this.modelMapper = modelMapper;
    }


    public void addApp(MedidataAppAddDto appAddDto) {
        validateNewApp(appAddDto);
        MedidataApp app = this.modelMapper.map(appAddDto, MedidataApp.class);
        this.appRepository.save(app);
    }

    private void validateNewApp(MedidataAppAddDto appAddDto) {
        String name = appAddDto.getName();
        if(name == null || name.trim().isEmpty()){
            throw new RuntimeException("Name cannot be blank");
        }
        if(this.appRepository.findFirstByName(name).isPresent()){
            throw new RuntimeException("App \"" + name + "\" already present");
        }
    }

    private void validateEditApp(MedidataAppEditDto appEditDto){
        String name = appEditDto.getName();
        if(name == null || name.trim().isEmpty()){
            throw new RuntimeException("Name cannot be blank");
        }
    }

    public MedidataAppEditDto getEditAppById(Long id) {
        return this.modelMapper.map(
                this.appRepository.findById(id).get(),
                MedidataAppEditDto.class);
    }

    public List<MedidataAppEditDto> getAllEditApps() {
        return this.appRepository.findAll()
                .stream().map((a) -> this.modelMapper
                        .map(a, MedidataAppEditDto.class))
                .sorted((a1,a2) -> a1.getName().compareTo(a2.getName()))
                .collect(Collectors.toList());
    }

    public void editApp(MedidataAppEditDto editDto) {
        validateEditApp(editDto);
        MedidataApp editedApp = this.modelMapper
                .map(editDto, MedidataApp.class);
        this.appRepository.save(editedApp);
    }
}
