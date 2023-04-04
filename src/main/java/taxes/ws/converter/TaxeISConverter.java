package taxes.ws.converter;

import taxes.bean.TaxeIS;
import taxes.ws.dto.TaxeISDto;
import org.springframework.stereotype.Component;

@Component
public class TaxeISConverter extends AbstractConverter<TaxeIS,TaxeISDto> {
    public TaxeISDto toDto(TaxeIS item){
        TaxeISDto dto = null;
        if (item != null) {
            dto= new TaxeISDto();
            dto.setId(item.getId());
            dto.setAnnee(item.getAnnee());
            dto.setTrimestre(item.getTrimestre());
            dto.setCharge(item.getCharge());
            dto.setSociete(item.getSociete());
            dto.setChiffreAffaire(item.getChiffreAffaire());
            dto.setMontantIs(item.getMontantIs());
            dto.setResultatAvantImpot(item.getResultatAvantImpot());
            dto.setResultatApresImpot(item.getResultatApresImpot());
            dto.setTauxTaxeIS(item.getTauxTaxeIS());
        }
        return dto;
    }
    public TaxeIS toItem(TaxeISDto dto){
    TaxeIS item= null;
        if (dto != null) {
            item= new TaxeIS();
            item.setId(item.getId());
            item.setAnnee(item.getAnnee());
            item.setTrimestre(item.getTrimestre());
            item.setCharge(item.getCharge());
            item.setSociete(item.getSociete());
            item.setChiffreAffaire(item.getChiffreAffaire());
            item.setMontantIs(item.getMontantIs());
            item.setResultatAvantImpot(item.getResultatAvantImpot());
            item.setResultatApresImpot(item.getResultatApresImpot());
            item.setTauxTaxeIS(item.getTauxTaxeIS());


        }
        return item;
    }


}
