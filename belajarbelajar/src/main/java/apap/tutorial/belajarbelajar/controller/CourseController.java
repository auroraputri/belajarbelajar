package apap.tutorial.belajarbelajar.controller;

import apap.tutorial.belajarbelajar.model.PengajarModel;
import apap.tutorial.belajarbelajar.model.PenyelenggaraModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import apap.tutorial.belajarbelajar.model.CourseModel;
import apap.tutorial.belajarbelajar.service.CourseService;
import apap.tutorial.belajarbelajar.model.PenyelenggaraModel;
import apap.tutorial.belajarbelajar.service.PenyelenggaraService;
import apap.tutorial.belajarbelajar.model.PengajarModel;
import apap.tutorial.belajarbelajar.service.PengajarService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CourseController {
    @Qualifier("courseServiceImpl")
    @Autowired
    private CourseService courseService;
    @Autowired
    private PenyelenggaraService penyelenggaraService;
    @Autowired
    private PengajarService pengajarService;

    @GetMapping("course/add")
    public String addCourseFormPage(Model model) {
        CourseModel course= new CourseModel();
        List<PenyelenggaraModel> listPenyelenggara = penyelenggaraService.getListPenyelenggara();
        List<PenyelenggaraModel> listPenyelenggaraNew = new ArrayList<>();

        List<PengajarModel> listPengajarNew = new ArrayList<>();
        course.setListPengajar(listPengajarNew);
        course.getListPengajar().add((new PengajarModel()));

        course.setListPenyelenggara(listPenyelenggaraNew);
        course.getListPenyelenggara().add(new PenyelenggaraModel());

        model.addAttribute("course", course);
        model.addAttribute("listPenyelenggaraExisting", listPenyelenggara);
        return "form-add-course";
    }

    @PostMapping(value = "course/add", params = {"save"})
    public String addCourseSubmit(@ModelAttribute CourseModel course, Model model) {
        if(course.getListPenyelenggara() == null){
            course.setListPenyelenggara(new ArrayList<>());
        }

        if(course.getListPengajar() == null){
            course.setListPengajar(new ArrayList<>());
        }else {
            for (PengajarModel pengajar : course.getListPengajar()){
                pengajar.setCourse(course);
            }
        }

        courseService.addCourse(course);
        System.out.println(course.getCode());
        model.addAttribute("code", course.getCode());
        return "add-course";
    }

    @GetMapping("course/viewall")
    public String listCourse(Model model) {
        List<CourseModel> listCourse = courseService.getListCourse();
        model.addAttribute("listCourse", listCourse);
        return "viewall-course";
    }

    @GetMapping("course/view")
    public String viewDetailCoursePage(@RequestParam(value = "code") String code, Model model) {
        CourseModel course = courseService.getCourseByCourseCode(code);

        if (course != null){
            List<PengajarModel> listPengajar = course.getListPengajar();
            List<PenyelenggaraModel> listPenyelenggara = course.getListPenyelenggara();
            model.addAttribute("listPengajar", listPengajar);
            model.addAttribute("listPenyelenggara", listPenyelenggara);
            model.addAttribute("course", course);
            return "view-course";
        }else {
            model.addAttribute("code", code);
            return "gabisa-view-course";
        }

    }

    @GetMapping(value = "/course/view-query")
    public String viewDetailCoursePageQuery(@RequestParam(value = "code") String code, Model model) {
        CourseModel course = courseService.getCourseByCourseCodeQuery(code);

        if (course != null){
            List<PengajarModel> listPengajar = course.getListPengajar();
            List<PenyelenggaraModel> listPenyelenggara = course.getListPenyelenggara();
            model.addAttribute("listPengajar", listPengajar);
            model.addAttribute("listPenyelenggara", listPenyelenggara);
            model.addAttribute("course", course);;
            return "view-course";
        }else {
            model.addAttribute("code", code);
            return "gabisa-view-course";
        }
    }

    @GetMapping(value = "/course/update/{code}")
    public String updateCourseFormPage(@PathVariable String code, Model model) {

        CourseModel course = courseService.getCourseByCourseCode(code);
        if (course != null){
            model.addAttribute("course", course);
            return "form-update-course";
        }else {
            model.addAttribute("code", code);
            return "gabisa-view-course";
        }

    }

    @PostMapping(value = "/course/update")
    public String updateCourseSubmitPage(@ModelAttribute CourseModel course, Model model) {
        CourseModel updatedCourse = courseService.updateCourse(course);
        model.addAttribute("code", updatedCourse.getCode());

        return "update-course";
    }
    @GetMapping("course/viewallAsc")
    public String listCourseAsc(Model model) {
        List<CourseModel> listCourse = courseService.getListCourseAsc();
        model.addAttribute("listCourse", listCourse);
        return "viewall-course";
    }

    @GetMapping(value = "/course/delete/{code}")
    public String deleteCourseSubmitPage(@PathVariable String code, Model model) {
        CourseModel course = courseService.getCourseByCourseCode(code);

        if (course != null){
            if (course.getTanggalDimulai().isAfter(LocalDateTime.now()) || course.getTanggalBerakhir().isBefore(LocalDateTime.now()) && (course.getListPengajar().isEmpty())){
                CourseModel deleteCourse = courseService.deleteCourse(course);
                model.addAttribute("code",code);
                return "delete-course";
            }else{
                return "gabisa-delete-coursee";
            }
        }else {
            model.addAttribute("code", code);
            return "gabisa-view-course";
        }
    }

    @PostMapping(value = "/course/add", params = {"addRow"})
    private String addRowPenyelenggaraMultiple(
            @ModelAttribute CourseModel course,
            Model model
    ){
        if (course.getListPenyelenggara() == null || course.getListPenyelenggara().size() == 0){
            course.setListPenyelenggara(new ArrayList<>());
        }
        course.getListPenyelenggara().add(new PenyelenggaraModel());
        List<PenyelenggaraModel> listPenyelenggara = penyelenggaraService.getListPenyelenggara();

        model.addAttribute("course", course);
        model.addAttribute("listPenyelenggaraExisting", listPenyelenggara);
        return "form-add-course";
    }

    @PostMapping(value = "/course/add", params = {"deleteRow"})
    private String deleteRowPenyelenggaraMultiple(
            @ModelAttribute CourseModel course,
            @RequestParam("deleteRow") Integer row,
            Model model
    ){
        final Integer rowId = Integer.valueOf(row);
        course.getListPenyelenggara().remove(rowId.intValue());

        List<PenyelenggaraModel> listPenyelenggara = penyelenggaraService.getListPenyelenggara();

        model.addAttribute("course", course);
        model.addAttribute("listPenyelenggaraExisting", listPenyelenggara);
        return "form-add-course";
    }

    @PostMapping(value = "/course/add", params = {"addRowPengajar"})
    private String addRowPengajarMultiple(
            @ModelAttribute CourseModel course,
            Model model
    ){
        if (course.getListPengajar() == null || course.getListPengajar().size() == 0){
            course.setListPengajar(new ArrayList<>());
        }
        course.getListPengajar().add(new PengajarModel());
        List<PengajarModel> listPegajar = pengajarService.getListPengajar();
        List<PenyelenggaraModel> listPenyelenggara = penyelenggaraService.getListPenyelenggara();

        model.addAttribute("listPenyelenggaraExisting", listPenyelenggara);
        model.addAttribute("course", course);
        model.addAttribute("listPengajarExisting", listPegajar);
        return "form-add-course";
    }

    @PostMapping(value = "/course/add", params = {"deleteRowPengajar"})
    private String deleteRowPengajarMultiple(
            @ModelAttribute CourseModel course,
            @RequestParam("deleteRowPengajar") Integer row,
            Model model
    ){
        final Integer rowId = Integer.valueOf(row);
        course.getListPengajar().remove(rowId.intValue());

        List<PengajarModel> listPegajar = pengajarService.getListPengajar();
        List<PenyelenggaraModel> listPenyelenggara = penyelenggaraService.getListPenyelenggara();

        model.addAttribute("listPenyelenggaraExisting", listPenyelenggara);
        model.addAttribute("course", course);
        model.addAttribute("listPengajarExisting", listPegajar);
        return "form-add-course";
    }
}