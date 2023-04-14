package taxes.ws.converter;

import taxes.bean.ISItem;
import taxes.ws.dto.ISItemDto;
import org.springframework.stereotype.Component;

@Component
public class ISItemConverter {
    public ISItemDto toDto(ISItem item){
        ISItemDto dto= null;
        if (item != null) {
            dto= new ISItemDto();
            dto.setTaxeIS(dto.getTaxeIS());
            dto.setFactureGagnes(dto.getFactureGagnes());
            dto.setFacturePertes(dto.getFacturePertes());

        }

        return dto;
    } public ISItem toItem(ISItemDto dto){
        ISItem item= null;
        if (dto != null) {
            item= new ISItem();
            item.setTaxeIS(dto.getTaxeIS());
            item.setFacturePertes(dto.getFacturePertes());
            item.setFactureGagnes(dto.getFactureGagnes());
        }
        return item;
    }
}
