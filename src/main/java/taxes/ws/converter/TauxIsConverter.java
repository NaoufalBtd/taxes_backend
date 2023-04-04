package taxes.ws.converter;

import taxes.bean.TauxTaxeIS;
import taxes.ws.dto.TauxTaxeIsDto;
import org.springframework.stereotype.Component;

@Component
public class TauxIsConverter extends AbstractConverter<TauxTaxeIS, TauxTaxeIsDto> {
    @Override
    public TauxTaxeIsDto toDto(TauxTaxeIS item){
        TauxTaxeIsDto dto =null;
        if (item != null) {
            dto = new TauxTaxeIsDto();
            dto.setId(item.getId());
            dto.setDateApplicationDebut(item.getDateApplicationDebut());
            dto.setDateApplicationFin(item.getDateApplicationFin());
            dto.setPourcentage(item.getPourcentage());
            dto.setResultMax(item.getResultMax());
            dto.setResultatMin(item.getResultatMin());
        }
        return dto;
    }
    @Override
public TauxTaxeIS toItem(TauxTaxeIsDto dto){
        TauxTaxeIS item = null;
        if (dto != null) {
            item = new TauxTaxeIS();
            item.setId(dto.getId());
            item.setDateApplicationDebut(dto.getDateApplicationDebut());
            item.setDateApplicationFin(dto.getDateApplicationFin());
            item.setPourcentage(dto.getPourcentage());
            item.setResultMax(dto.getResultMax());
            item.setResultatMin(dto.getResultatMin());

        }
        return item;
    }
}
