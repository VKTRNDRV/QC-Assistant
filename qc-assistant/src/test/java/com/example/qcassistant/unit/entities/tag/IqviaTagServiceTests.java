package com.example.qcassistant.unit.entities.tag;

import com.example.qcassistant.domain.dto.tag.TagAddDto;
import com.example.qcassistant.domain.dto.tag.TagDisplayDto;
import com.example.qcassistant.domain.dto.tag.TagEditDto;
import com.example.qcassistant.domain.entity.tag.IqviaTag;
import com.example.qcassistant.domain.entity.tag.MedidataTag;
import com.example.qcassistant.domain.enums.OrderType;
import com.example.qcassistant.domain.enums.Severity;
import com.example.qcassistant.domain.enums.TagType;
import com.example.qcassistant.domain.enums.item.OperatingSystem;
import com.example.qcassistant.domain.enums.item.ShellType;
import com.example.qcassistant.repository.tag.IqviaTagRepository;
import com.example.qcassistant.repository.tag.MedidataTagRepository;
import com.example.qcassistant.service.tag.BaseTagService;
import com.example.qcassistant.service.tag.IqviaTagService;
import com.example.qcassistant.service.tag.MedidataTagService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class IqviaTagServiceTests {

    private IqviaTagService tagService;

    private IqviaTagRepository tagRepository;

    @Autowired
    public IqviaTagServiceTests(IqviaTagService tagService,
                                IqviaTagRepository tagRepository) {
        this.tagService = tagService;
        this.tagRepository = tagRepository;
    }

    @Test
    public <T> void getDisplayTags_ReturnsCorrectCount(){
        List<T> fromService = (List<T>) this
                .tagService.getDisplayTags();

        Assertions.assertEquals(fromService.size(),
                tagRepository.count());

        if(!fromService.isEmpty()){
            Assertions.assertEquals(fromService.get(0).getClass(),
                    TagDisplayDto.class);
        }
    }

    @Test
    public void addTag_BlankTextThrowsException(){
        TagAddDto blankNameTag = new TagAddDto();
        blankNameTag.setText("   ");

        Assertions.assertThrows(RuntimeException.class,
                () -> tagService.addTag(blankNameTag));
    }

    @Test
    public void addTag_TooLongTextThrowsException(){
        TagAddDto longTextTag = new TagAddDto();
        StringBuilder tagText = new StringBuilder();
        tagText.append("a".repeat(BaseTagService
                .MAX_NOTE_LENGTH + 1));
        longTextTag.setText(tagText.toString());

        Assertions.assertThrows(RuntimeException.class,
                () -> tagService.addTag(longTextTag));
    }

    @Test
    public void editTag_BlankTextThrowsException(){
        TagEditDto blankNameTag = new TagEditDto();
        blankNameTag.setText("   ");

        Assertions.assertThrows(RuntimeException.class,
                () -> tagService.editTag(blankNameTag));
    }

    @Test
    public void editTag_TooLongTextThrowsException(){
        TagEditDto longTextTag = new TagEditDto();
        StringBuilder tagText = new StringBuilder();
        tagText.append("a".repeat(BaseTagService
                .MAX_NOTE_LENGTH + 1));
        longTextTag.setText(tagText.toString());

        Assertions.assertThrows(RuntimeException.class,
                () -> tagService.editTag(longTextTag));
    }
}
