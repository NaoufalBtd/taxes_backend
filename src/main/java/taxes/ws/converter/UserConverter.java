package taxes.ws.converter;

import taxes.bean.User;
import taxes.ws.dto.UserDto;

public class UserConverter extends AbstractConverter<User, UserDto> {

        @Override
        public UserDto toDto(User item) {
            UserDto dto = null;
            if (item != null) {
                dto = new UserDto();
                dto.setId(item.getId());
                dto.setUsername(item.getUsername());
                dto.setEmail(item.getEmail());
                dto.setPassword(item.getPassword());
                dto.setRole(String.valueOf(item.getRole()));
                dto.setSocieteIce(item.getSociete().getIce());
            }
            return dto;
        }

        @Override
        public User toItem(UserDto dto) {
            User item = null;
            if (dto != null) {
                item = new User();
                item.setId(dto.getId());
                item.setUsername(dto.getUsername());
                item.setEmail(dto.getEmail());
                item.setPassword(dto.getPassword());
//                item.setRole(dto.getRole());
                item.getSociete().setIce(dto.getSocieteIce());
            }
            return item;
        }
}
