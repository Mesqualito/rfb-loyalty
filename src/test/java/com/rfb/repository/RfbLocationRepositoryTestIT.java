package com.rfb.repository;

import com.rfb.RfbloyaltyApp;
import com.rfb.bootstrap.RfbBootstrap;
import com.rfb.domain.RfbLocation;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.DayOfWeek;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = RfbloyaltyApp.class)
public class RfbLocationRepositoryTestIT extends AbstractRepositoryTest {

    @Before
    public void setUp() throws Exception {
        RfbBootstrap rfbBootstrap = new RfbBootstrap(rfbLocationRepository, rfbEventRepository,
            rfbEventAttendanceRepository, rfbUserRepository);
    }

    @Test
    public void findAllByRunDayOfWeek() throws Exception {
        List<RfbLocation> mondayLocations = rfbLocationRepository.findAllByRunDayOfWeek(DayOfWeek.MONDAY.getValue());
        List<RfbLocation> tuesdayLocations = rfbLocationRepository.findAllByRunDayOfWeek(DayOfWeek.TUESDAY.getValue());
        List<RfbLocation> wednesdayLocations = rfbLocationRepository.findAllByRunDayOfWeek(DayOfWeek.WEDNESDAY.getValue());
        List<RfbLocation> thursdayLocations = rfbLocationRepository.findAllByRunDayOfWeek(DayOfWeek.THURSDAY.getValue());

        assertEquals(1, mondayLocations.size());
        assertEquals(1, tuesdayLocations.size());
        assertEquals(1, wednesdayLocations.size());
        assertEquals(1, thursdayLocations.size());
    }
}
