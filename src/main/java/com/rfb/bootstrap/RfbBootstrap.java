package com.rfb.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.UUID;

// initialising (Test-) Data is possible with:
// 1.) Spring events
// 2.) Spring Boot CommandLineRunner
// 3.) JHipster "faker"-technology
@Component
public class RfbBootstrap implements CommandLineRunner {

    private final com.rfb.repository.RfbLocationRepository rfbLocationRepository;
    private final com.rfb.repository.RfbEventRepository rfbEventRepository;
    private final com.rfb.repository.RfbEventAttendanceRepository rfbEventAttendanceRepository;
    private final com.rfb.repository.RfbUserRepository rfbUserRepository;

    public RfbBootstrap(com.rfb.repository.RfbLocationRepository rfbLocationRepository, com.rfb.repository.RfbEventRepository rfbEventRepository,
                        com.rfb.repository.RfbEventAttendanceRepository rfbEventAttendanceRepository, com.rfb.repository.RfbUserRepository rfbUserRepository) {
        this.rfbLocationRepository = rfbLocationRepository;
        this.rfbEventRepository = rfbEventRepository;
        this.rfbEventAttendanceRepository = rfbEventAttendanceRepository;
        this.rfbUserRepository = rfbUserRepository;
    }
    @Transactional
    @Override
    public void run(String... strings) throws Exception {

        // init RFB Locations
        if(rfbLocationRepository.count() == 0){
            //only load data if no data loaded
            initData();
        }

    }

    private void initData() {
        com.rfb.domain.RfbUser rfbUser = new com.rfb.domain.RfbUser();
        rfbUser.setUsername("Johnny");
        rfbUserRepository.save(rfbUser);

        //load data
        com.rfb.domain.RfbLocation aleAndWitch = getRfbLocation("St Pete - Ale and the Witch", DayOfWeek.MONDAY.getValue());

        rfbUser.setHomeLocation(aleAndWitch);
        rfbUserRepository.save(rfbUser);

        com.rfb.domain.RfbEvent aleEvent = getRfbEvent(aleAndWitch);

        getRfbEventAttendance(rfbUser, aleEvent);

        com.rfb.domain.RfbLocation ratc = getRfbLocation("St Pete - Right Around The Corner", DayOfWeek.TUESDAY.getValue());

        com.rfb.domain.RfbEvent ratcEvent = getRfbEvent(ratc);

        getRfbEventAttendance(rfbUser, ratcEvent);

        com.rfb.domain.RfbLocation stPeteBrew = getRfbLocation("St Pete - St Pete Brewing", DayOfWeek.WEDNESDAY.getValue());

        com.rfb.domain.RfbEvent stPeteBrewEvent = getRfbEvent(stPeteBrew);

        getRfbEventAttendance(rfbUser, stPeteBrewEvent);

        com.rfb.domain.RfbLocation yardOfAle = getRfbLocation("St Pete - Yard of Ale", DayOfWeek.THURSDAY.getValue());

        com.rfb.domain.RfbEvent yardOfAleEvent = getRfbEvent(yardOfAle);

        getRfbEventAttendance(rfbUser, yardOfAleEvent);

    }


    private void getRfbEventAttendance(com.rfb.domain.RfbUser rfbUser, com.rfb.domain.RfbEvent rfbEvent) {
        com.rfb.domain.RfbEventAttendance rfbAttendance = new com.rfb.domain.RfbEventAttendance();
        rfbAttendance.setRfbEvent(rfbEvent);
        rfbAttendance.setRfbUser(rfbUser);
        rfbAttendance.setAttendanceDate(LocalDate.now());

        System.out.println(rfbAttendance.toString());

        rfbEventAttendanceRepository.save(rfbAttendance);
        rfbEventRepository.save(rfbEvent);
    }

    private com.rfb.domain.RfbEvent getRfbEvent(com.rfb.domain.RfbLocation rfbLocation) {
        com.rfb.domain.RfbEvent rfbEvent = new com.rfb.domain.RfbEvent();
        rfbEvent.setEventCode(UUID.randomUUID().toString());
        rfbEvent.setEventDate(LocalDate.now()); // will not be on assigned day...
        rfbLocation.addRvbEvent(rfbEvent);
        rfbLocationRepository.save(rfbLocation);
        rfbEventRepository.save(rfbEvent);
        return rfbEvent;
    }

    private com.rfb.domain.RfbLocation getRfbLocation(String locationName, int value) {
        com.rfb.domain.RfbLocation rfbLocation = new com.rfb.domain.RfbLocation();
        rfbLocation.setLocationName(locationName);
        rfbLocation.setRunDayOfWeek(value);
        rfbLocationRepository.save(rfbLocation);
        return rfbLocation;
    }
}
