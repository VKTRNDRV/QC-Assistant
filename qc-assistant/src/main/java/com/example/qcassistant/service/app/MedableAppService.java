package com.example.qcassistant.service.app;

import com.example.qcassistant.domain.dto.app.AppAddDto;
import com.example.qcassistant.domain.dto.app.AppEditDto;
import com.example.qcassistant.domain.entity.app.IqviaApp;
import com.example.qcassistant.domain.entity.app.MedableApp;
import com.example.qcassistant.domain.entity.app.MedidataApp;
import com.example.qcassistant.repository.app.IqviaAppRepository;
import com.example.qcassistant.repository.app.MedableAppRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MedableAppService extends BaseAppService{

    private MedableAppRepository appRepository;

    @Autowired
    public MedableAppService(ModelMapper modelMapper, MedableAppRepository appRepository) {
        super(modelMapper);
        this.appRepository = appRepository;
    }

//    @Override
//    public List<AppEditDto> getAllEditApps() {
//        return this.getEntities().stream().map((a) -> this.modelMapper
//                        .map(a, AppEditDto.class))
//                .sorted((a1,a2) -> a1.getName().compareTo(a2.getName()))
//                .collect(Collectors.toList());
//    }

    @Override
    public void addApp(AppAddDto appAddDto) {
        validateNewApp(appAddDto);
        MedableApp app = super.modelMapper.map(appAddDto, MedableApp.class);
        this.appRepository.save(app);
    }

    @Override
    public void editApp(AppEditDto editDto) {
        validateEditApp(editDto);
        MedableApp editedApp = this.modelMapper
                .map(editDto, MedableApp.class);
        this.appRepository.save(editedApp);
    }

    @Override
    protected MedableAppRepository getAppRepository(){
        return this.appRepository;
    }


    @Override
    protected List<MedableApp> getEntities() {
        return getAppRepository().findAll();
    }

    @Override
    protected Optional<MedableApp> findFirstByName(String name) {
        return getAppRepository().findFirstByName(name);
    }
}
