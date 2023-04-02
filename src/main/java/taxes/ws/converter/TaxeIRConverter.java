package taxes.ws.converter;

import taxes.bean.TaxeIR;
import taxes.ws.dto.TaxeIRDto;
import org.springframework.stereotype.Component;

@Component
public class TaxeIRConverter extends AbstractConverter<TaxeIR, TaxeIRDto> {

    public TaxeIRDto toDto(TaxeIR item) {
        TaxeIRDto dto = null;
        if (item != null) {
            dto = new TaxeIRDto();
            dto.setAnnee(item.getAnnee());
            dto.setMois(item.getMois());
            dto.setSalaireBrute(item.getSalaireBrute());
            dto.setId(item.getId());
            dto.setDateDeclaration(item.getDateDeclaration());
            dto.setSociete(item.getSociete());
            dto.setSalaireNet(item.getSalaireNet());
            dto.setMontantIR(item.getMontantIR());
//            dto.setTauxTaxeIR(item.getTauxTaxeIR());
//            dto.setTaxeIREmployes(item.getTaxeIREmployes());

        }
        return dto;
    }

    public TaxeIR toItem(TaxeIRDto dto) {
        TaxeIR item = null;
        if (dto != null) {
            item = new TaxeIR();
            item.setAnnee(dto.getAnnee());
            item.setSalaireBrute(dto.getSalaireBrute());
            item.setId(dto.getId());
            item.setDateDeclaration(dto.getDateDeclaration());
            item.setSociete(dto.getSociete());
            item.setSalaireNet(dto.getSalaireNet());
            item.setMontantIR(dto.getMontantIR());
//            item.setTauxTaxeIR(dto.getTauxTaxeIR());
//            item.setTaxeIREmployes(dto.getTaxeIREmployes());
        }
        return item;
    }


}
