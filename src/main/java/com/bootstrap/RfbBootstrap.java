package com.rfb.bootstrap;


import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.UUID;


@Component
public class RfbBootstrap implements CommandLineRunner {

    private final com.repository.RfbLocationRepository rfbLocationRepository;
    private final com.repository.RfbEventRepository rfbEventRepository;
    private final com.repository.RfbEventAttendanceRepository rfbEventAttendanceRepository;
    private final com.repository.RfbUserRepository rfbUserRepository;

    public RfbBootstrap(com.repository.RfbLocationRepository rfbLocationRepository, com.repository.RfbEventRepository rfbEventRepository,
                        com.repository.RfbEventAttendanceRepository rfbEventAttendanceRepository, com.repository.RfbUserRepository rfbUserRepository) {
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
        com.domain.RfbUser rfbUser = new com.domain.RfbUser();
        rfbUser.setUsername("Johnny");
        rfbUserRepository.save(rfbUser);

        //load data
        com.domain.RfbLocation aleAndWitch = getRfbLocation("St Pete - Ale and the Witch", DayOfWeek.MONDAY.getValue());

        rfbUser.setHomeLocation(aleAndWitch);
        rfbUserRepository.save(rfbUser);

        com.domain.RfbEvent aleEvent = getRfbEvent(aleAndWitch);

        getRfbEventAttendance(rfbUser, aleEvent);

        com.domain.RfbLocation ratc = getRfbLocation("St Pete - Right Around The Corner", DayOfWeek.TUESDAY.getValue());

        com.domain.RfbEvent ratcEvent = getRfbEvent(ratc);

        getRfbEventAttendance(rfbUser, ratcEvent);

        com.domain.RfbLocation stPeteBrew = getRfbLocation("St Pete - St Pete Brewing", DayOfWeek.WEDNESDAY.getValue());

        com.domain.RfbEvent stPeteBrewEvent = getRfbEvent(stPeteBrew);

        getRfbEventAttendance(rfbUser, stPeteBrewEvent);

        com.domain.RfbLocation yardOfAle = getRfbLocation("St Pete - Yard of Ale", DayOfWeek.THURSDAY.getValue());

        com.domain.RfbEvent yardOfAleEvent = getRfbEvent(yardOfAle);

        getRfbEventAttendance(rfbUser, yardOfAleEvent);

    }


    private void getRfbEventAttendance(com.domain.RfbUser rfbUser, com.domain.RfbEvent rfbEvent) {
        com.domain.RfbEventAttendance rfbAttendance = new com.domain.RfbEventAttendance();
        rfbAttendance.setRfbEvent(rfbEvent);
        rfbAttendance.setRfbUser(rfbUser);
        rfbAttendance.setAttendanceDate(LocalDate.now());

        System.out.println(rfbAttendance.toString());

        rfbEventAttendanceRepository.save(rfbAttendance);
        rfbEventRepository.save(rfbEvent);
    }

    private com.domain.RfbEvent getRfbEvent(com.domain.RfbLocation rfbLocation) {
        com.domain.RfbEvent rfbEvent = new com.domain.RfbEvent();
        rfbEvent.setEventCode(UUID.randomUUID().toString());
        rfbEvent.setEventDate(LocalDate.now()); // will not be on assigned day...
        rfbLocation.addRvbEvent(rfbEvent);
        rfbLocationRepository.save(rfbLocation);
        rfbEventRepository.save(rfbEvent);
        return rfbEvent;
    }

    private com.domain.RfbLocation getRfbLocation(String locationName, int value) {
        com.domain.RfbLocation rfbLocation = new com.domain.RfbLocation();
        rfbLocation.setLocationName(locationName);
        rfbLocation.setRunDayOfWeek(value);
        rfbLocationRepository.save(rfbLocation);
        return rfbLocation;
    }
}
