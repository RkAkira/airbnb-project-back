package fr.perpetualmotion.airbnbback.user.mapper;

import fr.perpetualmotion.airbnbback.user.application.dto.ReadUserDTO;
import fr.perpetualmotion.airbnbback.user.domain.Authority;
import fr.perpetualmotion.airbnbback.user.domain.User;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper {

    ReadUserDTO readUserDTOToUser(User user);

    default String mapAuthoritiesToString(Authority authority){
        return authority.getName();
    }
}
