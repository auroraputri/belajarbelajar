package apap.tutorial.belajarbelajar.controller;

import apap.tutorial.belajarbelajar.model.CourseModel;
import apap.tutorial.belajarbelajar.model.PengajarModel;
import apap.tutorial.belajarbelajar.service.CourseService;
import apap.tutorial.belajarbelajar.service.PengajarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PengajarController {
    @Qualifier("pengajarServiceImpl")
    @Autowired
    private PengajarService pengajarService;

    @Qualifier("courseServiceImpl")
    @Autowired
    private CourseService courseService;

    @GetMapping("/pengajar/add/{code}")
    public String addPengajarFormPage(@PathVariable String code, Model model) {
        PengajarModel pengajar = new PengajarModel();
        CourseModel course = courseService.getCourseByCourseCode(code);
        pengajar.setCourse(course);
        model.addAttribute("pengajar", pengajar);
        return "form-add-pengajar";
    }

    @PostMapping("/pengajar/add")
    public String addPengajarSubmitPage(@ModelAttribute PengajarModel pengajar, Model model) {
        pengajarService.addPengajar(pengajar);
        model.addAttribute("noPengajar", pengajar.getNoPengajar());
        return "add-pengajar";
    }

    @GetMapping(value = "/course/pengajar/update/{noPengajar}")
    public String updateCoursePengajarFormPage(@PathVariable Long noPengajar, Model model) {

        PengajarModel pengajar = pengajarService.getCoursePengajarByNo(noPengajar);
        List<CourseModel> listCourse = courseService.getListCourse();
        model.addAttribute("pengajar", pengajar);
        model.addAttribute("listCourse", listCourse);

        return "form-update-pengajar";
    }

    @PostMapping(value = "/pengajar/update")
    public String updatePengajarSubmitPage(@ModelAttribute PengajarModel pengajar, Model model) {
        CourseModel course = pengajar.getCourse();
        if(course.getTanggalBerakhir().isBefore(LocalDateTime.now())){
            PengajarModel updatedPengajar = pengajarService.updatePengajar(pengajar);
            model.addAttribute("noPengajar", updatedPengajar.getNoPengajar());
            return "update-pengajar";
        }else{
            return "gabisa-update-pengajar";
        }

    }

    @PostMapping(value = "/pengajar/delete")
    public String deletePengajarSubmit(
            @ModelAttribute CourseModel course,
            Model model){
        if(courseService.isClosed(course.getTanggalDimulai(), course.getTanggalBerakhir())){
            for (PengajarModel pengajar : course.getListPengajar()){
                pengajarService.deletePengajar(pengajar);
            }
            model.addAttribute("code", course.getCode());
            return "delete-pengajar";
        }
        return "gabisa-delete-pengajar";
//
    }
}