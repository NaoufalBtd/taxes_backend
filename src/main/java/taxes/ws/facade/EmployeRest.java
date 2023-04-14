package taxes.ws.facade;

import jakarta.websocket.server.PathParam;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import taxes.bean.Employe;
import taxes.bean.User;
import taxes.service.facade.EmployeFacade;
import taxes.ws.converter.EmployeConverter;
import taxes.ws.dto.EmployeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;


@RestController
@RequestMapping("/api/v1/employe")

public class EmployeRest {
    @Autowired
    private EmployeFacade employeFacade;
    @Autowired
    private EmployeConverter employeConverter;

    @GetMapping("/cin/{cin}")
    public EmployeDto findByCin(@PathVariable String cin) {
        Employe employes= employeFacade.findByCin(cin);
        EmployeDto employeDto = employeConverter.toDto(employes);
        return employeDto;
    }
    @GetMapping("/")
    public List<EmployeDto> findAll(@PathParam("page") Integer page, @PathParam("size") Integer size, @PathParam("name") String name, @AuthenticationPrincipal User user) {
        Integer currentPage = page != null ? page : 0;
        Integer pageSize = size != null ? size : 5;
        PageRequest pageRequest = PageRequest.of(currentPage, pageSize, Sort.by("id").descending());
        List<Employe> employes;
        if(name != null) {
            employes = employeFacade.findBySocieteAndName(user.getSociete().getIce(), name, pageRequest);
        } else {
            employes = employeFacade.findBySociete(user.getSociete().getIce(), pageRequest);
        }
        List<EmployeDto> employeDtos =employeConverter.toDto(employes);
        return employeDtos;
    }
    @GetMapping("/count")
    public int currentEmployeesCount(@AuthenticationPrincipal User user) {
        return employeFacade.currentEmployesCount(user.getSociete().getIce());
    }
    @DeleteMapping ("/cin/{cin}")
    public int deleteByCin(@PathVariable String cin) {
        return employeFacade.deleteByCin(cin);
    }
//    @GetMapping("/ice/{ice}")
//    public EmployeDto findBySocieteIce(@PathVariable String ice) {
//        Employe employe = employeFacade.findBySocieteIce(ice);
//        EmployeDto employeDto = employeConverter.toDto(employe);
//        return employeDto;
//    }
    @GetMapping("/salaire/{salaire}")
    public EmployeDto findBySalaire(@PathVariable double salaire) {
        Employe employe = employeFacade.findBySalaire(salaire);
        EmployeDto employeDto = employeConverter.toDto(employe);
        return employeDto;
    }

    @GetMapping("/undeclared/{ice}/{annee}/{mois}")
    public List<EmployeDto> findUndeclaredEmployes(@PathVariable String ice, @PathVariable int mois, @PathVariable int annee) {
        List<Employe> employes = employeFacade.findUndeclaredEmployes(ice, mois, annee);
        List<EmployeDto> employeDtos = employeConverter.toDto(employes);
            return employeDtos;
    }
    @PostMapping("/")
    public int save(@RequestBody EmployeDto employe, @AuthenticationPrincipal User user) {
        employe.setSociete(user.getSociete());
        Employe emp = employeConverter.toItem(employe);
        return employeFacade.save(emp);
    }

    @PutMapping("/")
    public int update(@RequestBody EmployeDto employe) {
        Employe emp = employeConverter.toItem(employe);
        return employeFacade.update(emp);
    }
}

