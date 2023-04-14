package taxes.ws.converter;
import taxes.bean.FactureGagne;
import taxes.bean.FacturePerte;
import taxes.ws.dto.FactureGagneDto;
import taxes.ws.dto.FacturePerteDto;
import org.springframework.stereotype.Component;

@Component
public class FacturePerteConverter extends  AbstractConverter<FacturePerte,FacturePerteDto> {

    @Override
    public FacturePerteDto toDto(FacturePerte item) {
        FacturePerteDto dto = null;
        if (item != null) {
            dto = new FacturePerteDto();
            dto.setId(item.getId());
            dto.setCode(item.getCode());
            dto.setMontantHT(item.getMontantHT());
            dto.setMontantHC(item.getMontantHC());
            dto.setMontantTTC(item.getMontantTTC());
            dto.setTva(item.getTva());
            dto.setDateFacture(item.getDateFacture());
            dto.setSociete(item.getSociete());
            dto.setTaxeIS(item.getTaxeIS());
        }
        return dto;
    }

    @Override
    public FacturePerte toItem(FacturePerteDto dto) {
        FacturePerte item = null ;
        if (dto!= null) {
             item = new FacturePerte();
            item.setId(dto.getId());
            item.setMontantHT(dto.getMontantHT());
            item.setMontantHC(dto.getMontantHC());
            item.setMontantTTC(dto.getMontantTTC());
            item.setTva(dto.getTva());
            item.setDateFacture(dto.getDateFacture());
            item.setSociete(dto.getSociete());
            item.setTaxeIS(dto.getTaxeIS());
        }
        return item;
    }

}
