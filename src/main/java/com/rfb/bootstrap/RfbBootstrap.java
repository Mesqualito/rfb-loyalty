package com.rfb.bootstrap;

import com.rfb.domain.User;
import com.rfb.repository.RfbEventAttendanceRepository;
import com.rfb.repository.RfbEventRepository;
import com.rfb.repository.RfbLocationRepository;
import com.rfb.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    private final RfbLocationRepository rfbLocationRepository;
    private final RfbEventRepository rfbEventRepository;
    private final RfbEventAttendanceRepository rfbEventAttendanceRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RfbBootstrap(RfbLocationRepository rfbLocationRepository, RfbEventRepository rfbEventRepository,
                        RfbEventAttendanceRepository rfbEventAttendanceRepository, UserRepository userRepository,
                        PasswordEncoder passwordEncoder) {
        this.rfbLocationRepository = rfbLocationRepository;
        this.rfbEventRepository = rfbEventRepository;
        this.rfbEventAttendanceRepository = rfbEventAttendanceRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
        User user = new User();
        user.setFirstName("Johnny");
        user.setLastName("English");
        user.setPassword(passwordEncoder.encode("johnny"));
        user.setLogin("johnny");
        user.setActivated(true);
        userRepository.save(user);

        //load data
        com.rfb.domain.RfbLocation aleAndWitch = getRfbLocation("St Pete - Ale and the Witch", DayOfWeek.MONDAY.getValue());

        user.setHomeLocation(aleAndWitch);
        userRepository.save(user);

        com.rfb.domain.RfbEvent aleEvent = getRfbEvent(aleAndWitch);

        getRfbEventAttendance(user, aleEvent);

        com.rfb.domain.RfbLocation ratc = getRfbLocation("St Pete - Right Around The Corner", DayOfWeek.TUESDAY.getValue());

        com.rfb.domain.RfbEvent ratcEvent = getRfbEvent(ratc);

        getRfbEventAttendance(user, ratcEvent);

        com.rfb.domain.RfbLocation stPeteBrew = getRfbLocation("St Pete - St Pete Brewing", DayOfWeek.WEDNESDAY.getValue());

        com.rfb.domain.RfbEvent stPeteBrewEvent = getRfbEvent(stPeteBrew);

        getRfbEventAttendance(user, stPeteBrewEvent);

        com.rfb.domain.RfbLocation yardOfAle = getRfbLocation("St Pete - Yard of Ale", DayOfWeek.THURSDAY.getValue());

        com.rfb.domain.RfbEvent yardOfAleEvent = getRfbEvent(yardOfAle);

        getRfbEventAttendance(user, yardOfAleEvent);

    }


    private void getRfbEventAttendance(User user, com.rfb.domain.RfbEvent rfbEvent) {
        com.rfb.domain.RfbEventAttendance rfbAttendance = new com.rfb.domain.RfbEventAttendance();
        rfbAttendance.setRfbEvent(rfbEvent);
        rfbAttendance.setUser(user);
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
