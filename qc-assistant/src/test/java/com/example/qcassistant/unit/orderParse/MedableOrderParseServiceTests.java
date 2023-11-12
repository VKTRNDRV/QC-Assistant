package com.example.qcassistant.unit.orderParse;

import com.example.qcassistant.domain.dto.raw.RawOrderInputDto;
import com.example.qcassistant.domain.enums.OrderType;
import com.example.qcassistant.domain.item.device.android.phone.MedableAndroidPhone;
import com.example.qcassistant.domain.item.device.ios.ipad.MedableIPad;
import com.example.qcassistant.domain.item.device.medical.MedableMedicalDevice;
import com.example.qcassistant.domain.order.DeviceRepository;
import com.example.qcassistant.domain.order.IqviaOrder;
import com.example.qcassistant.domain.order.MedableOrder;
import com.example.qcassistant.domain.order.SimRepository;
import com.example.qcassistant.service.orderParse.MedableOrderParseService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MedableOrderParseServiceTests {

    private MedableOrderParseService orderParseService;

    @Autowired
    public MedableOrderParseServiceTests(MedableOrderParseService orderParseService) {
        this.orderParseService = orderParseService;
    }

    @Test
    public void testWrongClientThrowsException(){
        RawOrderInputDto input = new RawOrderInputDto().setRawText(
                MedableTestOrderInput.WRONG_CLIENT_ORDER);

        Assertions.assertThrows(RuntimeException.class,
                () -> orderParseService.parseOrder(input));
    }

    @Test
    public void testDetectSIMs(){
        MedableOrder order = this.orderParseService.parseOrder(new RawOrderInputDto()
                .setRawText(MedableTestOrderInput.AFW_DEVICES_ORDER));

        SimRepository sims = order.getSimRepository();

        Assertions.assertEquals(sims.getSims().size(), 10);
    }

    @Test
    public void testDetectOrderType(){
        MedableOrder order = this.orderParseService.parseOrder(new RawOrderInputDto()
                .setRawText(MedableTestOrderInput.AFW_DEVICES_ORDER));

        Assertions.assertEquals(order.getOrderType(), OrderType.PROD);
    }

    @Test
    public void testDetectMedicalDevices(){
        MedableOrder order = this.orderParseService.parseOrder(new RawOrderInputDto()
                .setRawText(MedableTestOrderInput.EVERIONS_PLUS_ENGLISH_REQ_ORDER));

        DeviceRepository devices = order.getDeviceRepository();

        Assertions.assertTrue(devices.containsDevice(MedableMedicalDevice
                .EVERION_TRACKER.getShortName()));

        Assertions.assertEquals(3, devices.getDevices().size());
    }

    @Test
    public void testDetectAFWDevices(){
        MedableOrder order = this.orderParseService.parseOrder(new RawOrderInputDto()
                .setRawText(MedableTestOrderInput.AFW_DEVICES_ORDER));

        DeviceRepository devices = order.getDeviceRepository();

        Assertions.assertTrue(devices.containsDevice(
                MedableAndroidPhone.A_12.getShortName()));

        Assertions.assertEquals(10, devices.getDevices().size());
    }

    @Test
    public void testDetectIOSDevices(){
        MedableOrder order = this.orderParseService.parseOrder(new RawOrderInputDto()
                .setRawText(MedableTestOrderInput.IOS_DEVICES_ORDER));

        DeviceRepository devices = order.getDeviceRepository();

        Assertions.assertTrue(devices.containsDevice(
                MedableIPad.EIGHT_GEN.getShortName()));

        Assertions.assertEquals(1, devices.getDevices().size());
    }

    @Test
    public void testDetectLanguage(){
        MedableOrder order = this.orderParseService.parseOrder(new RawOrderInputDto()
                .setRawText(MedableTestOrderInput.EVERIONS_PLUS_ENGLISH_REQ_ORDER));

        Assertions.assertTrue(order.isEnglishRequested());
    }
}
