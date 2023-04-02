package taxes.ws.converter;

import taxes.bean.TaxeIREmployes;
import taxes.ws.dto.TaxeIREmployesDto;
import org.springframework.stereotype.Component;

@Component
public class TaxeIREmployesConverter  extends AbstractConverter<TaxeIREmployes, TaxeIREmployesDto> {
    public TaxeIREmployesDto toDto (TaxeIREmployes item) {
        TaxeIREmployesDto dto = null;
        if (item != null) {
            dto = new TaxeIREmployesDto();
            dto.setSalaireBrute(item.getSalaireBrute());
            dto.setId(item.getId());
            dto.setSalaireNet(item.getSalaireNet());
            dto.setMontantIR(item.getMontantIR());

        }
        return dto;
    }
    public TaxeIREmployes toItem (TaxeIREmployesDto dto) {
        TaxeIREmployes item = null;
        if (dto != null) {
            item =new TaxeIREmployes();

            item.setSalaireBrute(dto.getSalaireBrute());
            item.setId(dto.getId());
            item.setSalaireNet(dto.getSalaireNet());
            item.setMontantIR(dto.getMontantIR());



        }
        return item;
    }


}

