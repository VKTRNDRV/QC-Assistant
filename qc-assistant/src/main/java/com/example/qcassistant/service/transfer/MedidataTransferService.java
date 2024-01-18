package com.example.qcassistant.service.transfer;

import com.example.qcassistant.domain.dto.app.AppTransferDTO;
import com.example.qcassistant.domain.dto.sponsor.SponsorTransferDTO;
import com.example.qcassistant.domain.dto.study.transfer.MedidataStudyTransferDTO;
import com.example.qcassistant.domain.dto.tag.TagTransferDTO;
import com.example.qcassistant.domain.dto.transfer.ClinicalEntitiesTransferDTO;
import com.example.qcassistant.domain.entity.app.BaseApp;
import com.example.qcassistant.domain.entity.app.MedidataApp;
import com.example.qcassistant.domain.entity.destination.Destination;
import com.example.qcassistant.domain.entity.sponsor.MedidataSponsor;
import com.example.qcassistant.domain.entity.study.MedidataStudy;
import com.example.qcassistant.domain.entity.study.environment.MedidataEnvironment;
import com.example.qcassistant.domain.entity.tag.MedidataTag;
import com.example.qcassistant.service.DestinationService;
import com.example.qcassistant.service.app.MedidataAppService;
import com.example.qcassistant.service.sponsor.MedidataSponsorService;
import com.example.qcassistant.service.study.MedidataStudyService;
import com.example.qcassistant.service.tag.MedidataTagService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MedidataTransferService extends BaseTransferService {

    private MedidataSponsorService sponsorService;

    private MedidataAppService appService;

    private MedidataTagService tagService;

    private MedidataStudyService studyService;

    @Autowired
    public MedidataTransferService(DestinationService destinationService,
                                   MedidataSponsorService sponsorService,
                                   MedidataAppService appService,
                                   MedidataTagService tagService,
                                   MedidataStudyService studyService,
                                   ModelMapper modelMapper,
                                   Gson gson) {
        super(destinationService, modelMapper, gson);
        this.sponsorService = sponsorService;
        this.appService = appService;
        this.tagService = tagService;
        this.studyService = studyService;
    }

    public ClinicalEntitiesTransferDTO exportEntities(){
        List<MedidataSponsor> sponsors = this.sponsorService.getEntities();
        List<MedidataApp> apps = this.appService.getEntities();
        List<MedidataTag> tags = this.tagService.getEntities();
        List<MedidataStudy> studies = this.studyService.getEntities();

        ClinicalEntitiesTransferDTO entities = new ClinicalEntitiesTransferDTO();
        entities.setSponsors(this.gson.toJson(super.mapSponsorsToTransferDTO(sponsors)))
                .setApps(this.gson.toJson(super.mapAppsToTransferDTO(apps)))
                .setTags(this.gson.toJson(super.mapTagsToTransferDTO(tags)))
                .setStudies(this.gson.toJson(this.mapStudiesToTransferDTO(studies)));

        return entities;
    }

    private List<MedidataStudyTransferDTO> mapStudiesToTransferDTO(Collection<MedidataStudy> studies) {
        List<MedidataStudyTransferDTO> transferDTOs = new ArrayList<>();
        for(MedidataStudy study : studies){
            MedidataStudyTransferDTO studyTransferDTO = this.modelMapper
                    .map(study, MedidataStudyTransferDTO.class);

            MedidataEnvironment env = study.getEnvironment();

            studyTransferDTO
                    .setSponsor(study.getSponsor().getName()).getEnvironment()
                    .setPatientApps(env.getPatientApps()
                            .stream().map(BaseApp::getName)
                            .collect(Collectors.toList()))
                    .setSiteApps(env.getSiteApps()
                            .stream().map(BaseApp::getName)
                            .collect(Collectors.toList()))
                    .setIsLegacy(env.getIsLegacy().name());

            transferDTOs.add(studyTransferDTO);
        }

        return transferDTOs;
    }

    @Override
    public void importEntities(ClinicalEntitiesTransferDTO entitiesJSON) {
        if(!entitiesJSON.getSponsors().trim().isEmpty()){
            this.importSponsors(entitiesJSON.getSponsors());
        }

        if(!entitiesJSON.getApps().trim().isEmpty()){
            this.importApps(entitiesJSON.getApps());
        }

        if(!entitiesJSON.getStudies().trim().isEmpty()){
            this.importStudies(entitiesJSON.getStudies());
        }

        if(!entitiesJSON.getTags().trim().isEmpty()){
            this.importTags(entitiesJSON.getTags());
        }
    }

    private void importTags(String json) {
        TagTransferDTO[] dtos = this.gson.fromJson(json, TagTransferDTO[].class);
        List<MedidataTag> tags = new ArrayList<>();
        List<Destination> destinations = this.destinationService.getEntities();
        List<MedidataStudy> studies = this.studyService.getEntities();
        for(TagTransferDTO tagDTO : dtos){
            MedidataTag tag = this.modelMapper.map(tagDTO, MedidataTag.class);

            List<Destination> tagDestinations = new ArrayList<>();
            for(String name : tagDTO.getDestinations()){
                for(Destination destination : destinations){
                    if(destination.getName().equals(name)){
                        tagDestinations.add(destination);
                        break;
                    }
                }
            }

            List<MedidataStudy> tagStudies = new ArrayList<>();
            for(String name : tagDTO.getStudies()){
                for(MedidataStudy study : studies){
                    if(study.getName().equals(name)){
                        tagStudies.add(study);
                        break;
                    }
                }
            }

            tag.setDestinations(destinations);
            tag.setStudies(studies);

            tags.add(tag);
        }

        this.tagService.saveAll(tags);
    }

    private void importStudies(String json) {
        MedidataStudyTransferDTO[] dtos = this.gson
                .fromJson(json, MedidataStudyTransferDTO[].class);
        List<MedidataStudy> studies = this.mapStudyDTOsToEntities(List.of(dtos));
        this.studyService.saveAll(studies);
    }
    public List<MedidataStudy> mapStudyDTOsToEntities(Iterable <MedidataStudyTransferDTO> dtos){
        MedidataSponsor unknownSponsor = this.sponsorService.getUnknownSponsor();
        List<MedidataApp> apps = this.appService.getEntities();
        List<MedidataStudy> studies = new ArrayList<>();

        for(MedidataStudyTransferDTO studyDTO : dtos){
            if(this.studyService.findFirstByName(
                    studyDTO.getName()).isPresent()) continue;

            MedidataStudy study = this.modelMapper.map(studyDTO, MedidataStudy.class);
            Optional<MedidataSponsor> sponsor = this.sponsorService
                    .findFirstByName(studyDTO.getSponsor());
            if(sponsor.isPresent()){
                study.setSponsor(sponsor.get());
            }else{
                study.setSponsor(unknownSponsor);
            }

            List<MedidataApp> siteApps = new ArrayList<>();
            List<MedidataApp> patientApps = new ArrayList<>();

            for(String appName : studyDTO.getEnvironment().getSiteApps()){
                for(MedidataApp app : apps){
                    if(app.getName().equals(appName)){
                        siteApps.add(app);
                        break;
                    }
                }
            }

            for(String appName : studyDTO.getEnvironment().getPatientApps()){
                for(MedidataApp app : apps){
                    if(app.getName().equals(appName)){
                        patientApps.add(app);
                        break;
                    }
                }
            }

            study.getEnvironment().setSiteApps(siteApps);
            study.getEnvironment().setPatientApps(patientApps);

            studies.add(study);
        }

        return studies;
    }



    private void importSponsors(String json) {
        SponsorTransferDTO[] dtos = this.gson
                .fromJson(json, SponsorTransferDTO[].class);
        List<MedidataSponsor> sponsors = new ArrayList<>();
        for(SponsorTransferDTO sponsorDTO : dtos){
            if(this.sponsorService.findFirstByName(
                    sponsorDTO.getName()).isPresent()) continue;
            MedidataSponsor sponsor = this.modelMapper
                    .map(sponsorDTO, MedidataSponsor.class);
            sponsors.add(sponsor);
        }

        this.sponsorService.saveAll(sponsors);
    }

    private void importApps(String json) {
        AppTransferDTO[] dtos = this.gson
                .fromJson(json, AppTransferDTO[].class);
        List<MedidataApp> apps = new ArrayList<>();
        for(AppTransferDTO appDTO : dtos){
            if(this.appService.findFirstByName(
                    appDTO.getName()).isPresent()) continue;
            MedidataApp app = this.modelMapper
                    .map(appDTO, MedidataApp.class);
            apps.add(app);
        }

        this.appService.saveAll(apps);
    }
}
