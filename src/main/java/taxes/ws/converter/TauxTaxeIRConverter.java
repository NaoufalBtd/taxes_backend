package taxes.ws.converter;

import taxes.bean.TauxTaxeIR;
import taxes.ws.dto.TauxTaxeIRDto;
import org.springframework.stereotype.Component;

@Component
public class TauxTaxeIRConverter  extends AbstractConverter<TauxTaxeIR, TauxTaxeIRDto> {

    public TauxTaxeIRDto toDto (TauxTaxeIR item) {
        TauxTaxeIRDto dto = null;
        if (item != null) {
            dto = new TauxTaxeIRDto();
            dto.setPourcentage(item.getPourcentage());
            dto.setSalaireMax(item.getSalaireMax());
            dto.setId(item.getId());
            dto.setDateApplicationFin(item.getDateApplicationFin());
            dto.setDateApplicationDebut(item.getDateApplicationDebut());
            dto.setSalaireMin(item.getSalaireMin());
        }
        return dto;
    }
    public TauxTaxeIR toItem (TauxTaxeIRDto dto) {
        TauxTaxeIR item = null;
        if (dto != null) {
          item =new TauxTaxeIR();
            item.setPourcentage(dto.getPourcentage());
            item.setSalaireMax(dto.getSalaireMax());
            item.setId(dto.getId());
            item.setDateApplicationFin(dto.getDateApplicationFin());
            item.setDateApplicationDebut(dto.getDateApplicationDebut());
            item.setSalaireMin(dto.getSalaireMin());
        }
        return item;
    }


}
