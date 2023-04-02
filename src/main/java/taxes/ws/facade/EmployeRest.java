package taxes.ws.facade;

import jakarta.websocket.server.PathParam;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import taxes.bean.Employe;
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
    public List<EmployeDto> findAll(@PathParam("page") Integer page, @PathParam("size") Integer size) {
        System.out.println("request = -----------------------------------");

        Integer currentPage = page != null ? page : 0;
        Integer pageSize = size != null ? size : 5;
        PageRequest pageRequest = PageRequest.of(currentPage, pageSize, Sort.by("id").descending());

        List<Employe> employes = employeFacade.findAll(pageRequest);
        List<EmployeDto> employeDtos =employeConverter.toDto(employes);
        return employeDtos;
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
    @PostMapping("/")
    public int save(@RequestBody EmployeDto employe) {
        System.out.println("employe = " + employe);
        Employe emp = employeConverter.toItem(employe);
        return employeFacade.save(emp);
    }
}

