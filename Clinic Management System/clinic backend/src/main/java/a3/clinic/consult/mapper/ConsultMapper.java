package a3.clinic.consult.mapper;

import a3.clinic.consult.dto.ConsultDescriptionDTO;
import a3.clinic.consult.model.Consult;
import a3.clinic.consult.dto.ConsultDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConsultMapper {
    ConsultDTO toDto(Consult consult);
    Consult fromDto(ConsultDTO consultDTO);

    ConsultDescriptionDTO toDescriptionDto(Consult consult);
}
