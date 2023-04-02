package taxes.ws.converter;

import taxes.bean.Employe;
import taxes.ws.dto.EmployeDto;
import org.springframework.stereotype.Component;
@Component
public class EmployeConverter extends AbstractConverter<Employe, EmployeDto> {
    @Override
    public EmployeDto toDto(Employe item){
        EmployeDto dto = null;
        if (item != null) {
            dto = new EmployeDto();
            dto.setId(item.getId());
            dto.setNom(item.getNom());
            dto.setCin(item.getCin());
            dto.setSalaire(item.getSalaire());
            dto.setSociete(item.getSociete());
            dto.setPrenom(item.getPrenom());
        }
        return dto;
    }
    @Override
public Employe toItem(EmployeDto dto){
        Employe item =null;
        if (dto != null) {
            item = new Employe();
            item.setId(dto.getId());
            item.setSociete(dto.getSociete());
            item.setCin(dto.getCin());
            item.setSalaire(dto.getSalaire());
            item.setNom(dto.getNom());
            item.setPrenom(dto.getPrenom());
        }
        return item;
    }
}
