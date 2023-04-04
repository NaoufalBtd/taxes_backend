package taxes.bootstrap;

import taxes.bean.*;
import taxes.dao.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import taxes.service.impl.TaxeIRService;
import taxes.service.impl.UserService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import static java.lang.Double.POSITIVE_INFINITY;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private SocieteDao societeDao;

    @Autowired
    private EmployeDao employeDao;

    @Autowired
    private TaxeIRService taxeIRService;

    @Autowired
    private TauxTaxeIRDao tauxTaxeIRDao;
    @Autowired
    private UserService userService;



    @PostConstruct
    public void checkContextInitialization() {
        System.out.println("Application context has been initialized successfully!");
    }
    @Override
    public void run(String... args) throws Exception {
        System.out.println("Loading initial data...");

        // Create a new Societe and save it
        Societe societe = new Societe();
        societe.setIce("ICE123");
        societe.setLibelle("Example Company");
        societe.setDescription("This is an example company");
        societeDao.save(societe);

        // Create Platform User
        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin");
        user.setEmail("admin@test.com");
        user.setSociete(societe);
        userService.save(user);


        // Create some Employees and save them
        Employe employe1 = new Employe();
        employe1.setNom("John");
        employe1.setPrenom("Doe");
        employe1.setCin("123456789");
        employe1.setSalaire(2000);
        employe1.setSociete(societe);
        employeDao.save(employe1);

        Employe employe2 = new Employe();
        employe2.setNom("Jane");
        employe2.setPrenom("Doe");
        employe2.setCin("987654321");
        employe2.setSalaire(1500);
        employe2.setSociete(societe);
        employeDao.save(employe2);

        Employe employe3 = new Employe();
        employe3.setNom("Mark");
        employe3.setPrenom("Smith");
        employe3.setCin("456789123");
        employe3.setSalaire(2500);
        employe3.setSociete(societe);
        employeDao.save(employe3);

        Employe employe4 = new Employe();
        employe4.setNom("Linda");
        employe4.setPrenom("Johnson");
        employe4.setCin("789123456");
        employe4.setSalaire(1800);
        employe4.setSociete(societe);
        employeDao.save(employe4);

        Employe employe5 = new Employe();
        employe5.setNom("William");
        employe5.setPrenom("Jones");
        employe5.setCin("321654987");
        employe5.setSalaire(3000);
        employe5.setSociete(societe);
        employeDao.save(employe5);

        Employe employe6 = new Employe();
        employe6.setNom("Mary");
        employe6.setPrenom("Brown");
        employe6.setCin("654987321");
        employe6.setSalaire(1700);
        employe6.setSociete(societe);
        employeDao.save(employe6);

        Employe employe7 = new Employe();
        employe7.setNom("David");
        employe7.setPrenom("Taylor");
        employe7.setCin("987321654");
        employe7.setSalaire(2200);
        employe7.setSociete(societe);
        employeDao.save(employe7);

        Employe employe8 = new Employe();
        employe8.setNom("Sarah");
        employe8.setPrenom("Clark");
        employe8.setCin("321789654");
        employe8.setSalaire(1900);
        employe8.setSociete(societe);
        employeDao.save(employe8);

        Employe employe9 = new Employe();
        employe9.setNom("Michael");
        employe9.setPrenom("Anderson");
        employe9.setCin("789654321");
        employe9.setSalaire(2800);
        employe9.setSociete(societe);
        employeDao.save(employe9);

        Employe employe11 = new Employe();
        employe11.setNom("Daniel");
        employe11.setPrenom("Martinez");
        employe11.setCin("987456321");
        employe11.setSalaire(2100);
        employe11.setSociete(societe);
        employeDao.save(employe11);

        Employe employe12 = new Employe();
        employe12.setNom("Sophia");
        employe12.setPrenom("Lopez");
        employe12.setCin("456789321");
        employe12.setSalaire(2400);
        employe12.setSociete(societe);
        employeDao.save(employe12);

        Employe employe13 = new Employe();
        employe13.setNom("Jacob");
        employe13.setPrenom("Hernandez");
        employe13.setCin("789654123");
        employe13.setSalaire(2900);
        employe13.setSociete(societe);
        employeDao.save(employe13);

        Employe employe14 = new Employe();
        employe14.setNom("Isabella");
        employe14.setPrenom("Gonzalez");
        employe14.setCin("321456987");
        employe14.setSalaire(1750);
        employe14.setSociete(societe);
        employeDao.save(employe14);

        Employe employe15 = new Employe();
        employe15.setNom("Ethan");
        employe15.setPrenom("Rodriguez");
        employe15.setCin("789321456");
        employe15.setSalaire(2600);
        employe15.setSociete(societe);
        employeDao.save(employe15);

        Employe employe16 = new Employe();
        employe16.setNom("Mia");
        employe16.setPrenom("Wilson");
        employe16.setCin("456321789");
        employe16.setSalaire(2000);
        employe16.setSociete(societe);
        employeDao.save(employe16);

        Employe employe17 = new Employe();
        employe17.setNom("Alexander");
        employe17.setPrenom("Moore");
        employe17.setCin("321987654");
        employe17.setSalaire(2700);
        employe17.setSociete(societe);
        employeDao.save(employe17);

        Employe employe18 = new Employe();
        employe18.setNom("Abigail");
        employe18.setPrenom("Jackson");
        employe18.setCin("654123987");
        employe18.setSalaire(1850);
        employe18.setSociete(societe);
        employeDao.save(employe18);

        Employe employe19 = new Employe();
        employe19.setNom("Ryan");
        employe19.setPrenom("Lee");
        employe19.setCin("321654789");
        employe19.setSalaire(3100);
        employe19.setSociete(societe);
        employeDao.save(employe19);

        Employe employe20 = new Employe();
        employe20.setNom("Emily");
        employe20.setPrenom("Davis");
        employe20.setCin("987654321");
        employe20.setSalaire(2300);
        employe20.setSociete(societe);
        employeDao.save(employe20);

        // Save TauxTaxeIr

        TauxTaxeIR tauxTaxeIr1 = new TauxTaxeIR();
        tauxTaxeIr1.setPourcentage(0);
        tauxTaxeIr1.setDateApplicationDebut(Timestamp.valueOf(LocalDate.parse("2019-01-01").atStartOfDay()));
        tauxTaxeIr1.setDateApplicationFin(Timestamp.valueOf(LocalDate.parse("2030-12-31").atStartOfDay()));
        tauxTaxeIr1.setSalaireMin(0);
        tauxTaxeIr1.setSalaireMax(3000.00);
        tauxTaxeIRDao.save(tauxTaxeIr1);

        TauxTaxeIR tauxTaxeIr2 = new TauxTaxeIR();
        tauxTaxeIr2.setPourcentage(10);
        tauxTaxeIr2.setDateApplicationDebut(Timestamp.valueOf(LocalDate.parse("2019-01-01").atStartOfDay()));
        tauxTaxeIr2.setDateApplicationFin(Timestamp.valueOf(LocalDate.parse("2030-12-31").atStartOfDay()));
        tauxTaxeIr2.setSalaireMin(3000.01);
        tauxTaxeIr2.setSalaireMax(7000.00);
        tauxTaxeIRDao.save(tauxTaxeIr2);

        TauxTaxeIR tauxTaxeIr3 = new TauxTaxeIR();
        tauxTaxeIr3.setPourcentage(20);
        tauxTaxeIr3.setDateApplicationDebut(Timestamp.valueOf(LocalDate.parse("2019-01-01").atStartOfDay()));
        tauxTaxeIr3.setDateApplicationFin(Timestamp.valueOf(LocalDate.parse("2030-12-31").atStartOfDay()));
        tauxTaxeIr3.setSalaireMin(7000.01);
        tauxTaxeIr3.setSalaireMax(10000.00);
        tauxTaxeIRDao.save(tauxTaxeIr3);

        TauxTaxeIR tauxTaxeIr4 = new TauxTaxeIR();
        tauxTaxeIr4.setPourcentage(30);
        tauxTaxeIr4.setDateApplicationDebut(Timestamp.valueOf(LocalDate.parse("2019-01-01").atStartOfDay()));
        tauxTaxeIr4.setDateApplicationFin(Timestamp.valueOf(LocalDate.parse("2030-12-31").atStartOfDay()));
        tauxTaxeIr4.setSalaireMin(10000.01);
        tauxTaxeIr4.setSalaireMax(100000000.00);
        tauxTaxeIRDao.save(tauxTaxeIr4);

        // Save TaxeIr

        //save taxeIr for year 2022 and all months
        String[] years = {"2020","2021", "2022"};
            for (String year : years) {
                for (int i = 1; i <= 12; i++) {
                    TaxeIR taxeIR = new TaxeIR();
                    taxeIR.setAnnee(Integer.parseInt(year));
                    taxeIR.setMois(i);
                    taxeIR.setDateDeclaration(Timestamp.valueOf(LocalDate.of(Integer.parseInt(year), i, 1).atStartOfDay()));
                    taxeIR.setSociete(societe);
                    taxeIRService.declareTaxeIR(taxeIR);
                }
            }
        }
}
