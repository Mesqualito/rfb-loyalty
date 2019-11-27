package com.service.mapper;

import com.domain.RfbLocation;
import com.service.dto.RfbLocationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link RfbLocation} and its DTO {@link RfbLocationDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RfbLocationMapper extends EntityMapper<RfbLocationDTO, RfbLocation> {


    @Mapping(target = "rvbEvents", ignore = true)
    @Mapping(target = "removeRvbEvent", ignore = true)
    RfbLocation toEntity(RfbLocationDTO rfbLocationDTO);

    default RfbLocation fromId(Long id) {
        if (id == null) {
            return null;
        }
        RfbLocation rfbLocation = new RfbLocation();
        rfbLocation.setId(id);
        return rfbLocation;
    }
}
