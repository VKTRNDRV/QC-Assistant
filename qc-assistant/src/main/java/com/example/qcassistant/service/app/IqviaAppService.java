package com.example.qcassistant.service.app;

import com.example.qcassistant.domain.dto.app.AppAddDto;
import com.example.qcassistant.domain.dto.app.AppEditDto;
import com.example.qcassistant.domain.entity.app.IqviaApp;
import com.example.qcassistant.domain.entity.app.MedidataApp;
import com.example.qcassistant.repository.app.IqviaAppRepository;
import com.example.qcassistant.repository.app.MedidataAppRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IqviaAppService extends BaseAppService{

    private IqviaAppRepository appRepository;

    @Autowired
    public IqviaAppService(ModelMapper modelMapper, IqviaAppRepository appRepository) {
        super(modelMapper);
        this.appRepository = appRepository;
    }

    @Override
    public void addApp(AppAddDto appAddDto) {
        validateNewApp(appAddDto);
        IqviaApp app = super.modelMapper.map(appAddDto, IqviaApp.class);
        this.appRepository.save(app);
    }

    @Override
    public void editApp(AppEditDto editDto) {
        validateEditApp(editDto);
        IqviaApp editedApp = this.modelMapper
                .map(editDto, IqviaApp.class);
        this.appRepository.save(editedApp);
    }

    @Override
    protected IqviaAppRepository getAppRepository(){
        return this.appRepository;
    }


    @Override
    protected List<IqviaApp> getEntities() {
        return getAppRepository().findAll();
    }

    @Override
    protected Optional<IqviaApp> findFirstByName(String name) {
        return getAppRepository().findFirstByName(name);
    }
}
