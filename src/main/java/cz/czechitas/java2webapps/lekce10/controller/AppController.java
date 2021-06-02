package cz.czechitas.java2webapps.lekce10.controller;

import cz.czechitas.java2webapps.lekce10.entity.Student;
import cz.czechitas.java2webapps.lekce10.entity.Trida;
import cz.czechitas.java2webapps.lekce10.service.RodicService;
import cz.czechitas.java2webapps.lekce10.service.StudentService;
import cz.czechitas.java2webapps.lekce10.service.TridaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AppController {
    @Autowired
    private final TridaService tridaService;
    @Autowired
    private final StudentService studentService;
    @Autowired
    private final RodicService rodicService;

    public AppController(TridaService tridaService, StudentService studentService, RodicService rodicService) {
        this.tridaService = tridaService;
        this.studentService = studentService;
        this.rodicService = rodicService;
    }

    @GetMapping("/")
    public ModelAndView seznam() {
        return new ModelAndView("tridy")
                .addObject("tridy", tridaService.seznamTrid());
    }

    @GetMapping("/trida/{tridaNazev}")
    public ModelAndView detailTridy(@PathVariable(value = "tridaNazev") String tridaNazev) {
        Trida trida = tridaService.detailTridy(tridaNazev);
        return new ModelAndView("detailTridy")
                .addObject("trida", trida)
                .addObject("studenti", studentService.seznamStudentuTridy(trida));
    }

    @GetMapping("/trida/{tridaNazev}/{student}")
    public ModelAndView detailStudenta(@PathVariable(value = "tridaNazev") String tridaNazev, @PathVariable(value = "student") Integer id) {
        Trida trida = tridaService.detailTridy(tridaNazev);
        Student student = studentService.detailStudenta(id, trida);
        return new ModelAndView("detailStudenta")
                .addObject("trida", trida)
                .addObject("student", student)
                .addObject("rodice", rodicService.seznamRodicu(student));
    }
}
