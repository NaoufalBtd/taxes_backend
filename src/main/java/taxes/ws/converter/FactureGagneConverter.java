package taxes.ws.converter;
import taxes.bean.FactureGagne;
import taxes.ws.dto.FactureGagneDto;
import org.springframework.stereotype.Component;

@Component
public class FactureGagneConverter extends  AbstractConverter<FactureGagne ,FactureGagneDto> {
@Override
    public FactureGagneDto toDto(FactureGagne item) {
        FactureGagneDto dto = null;
        if (item!=null) {
            dto=new FactureGagneDto();
            dto.setId(item.getId());
            dto.setCode(item.getCode());
            dto.setMontantHT(item.getMontantHT());
            dto.setMontantTTC(item.getMontantTTC());
            dto.setTva(item.getTva());
            dto.setDateFacture(item.getDateFacture());
            dto.setSociete(item.getSociete());
            dto.setTaxeIS(item.getTaxeIS());
        }
        return dto;
    }


    @Override
    public FactureGagne toItem(FactureGagneDto dto) {
        FactureGagne item = null;
        if (dto != null) {
            item=new FactureGagne();
            item.setId(dto.getId());
            item.setCode(dto.getCode());
            item.setMontantHT(dto.getMontantHT());
            item.setMontantTTC(dto.getMontantTTC());
            item.setTva(dto.getTva());
            item.setDateFacture(dto.getDateFacture());
            item.setSociete(dto.getSociete());
            item.setTaxeIS(dto.getTaxeIS());
        }
        return item;
    }

}

