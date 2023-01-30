package apap.tutorial.belajarbelajar.service;
import apap.tutorial.belajarbelajar.model.CourseModel;
import apap.tutorial.belajarbelajar.model.PengajarModel;

import java.time.LocalDateTime;
import java.util.List;

public interface CourseService {
    void addCourse(CourseModel course);

    List<CourseModel> getListCourse();

    CourseModel getCourseByCourseCode(String code);
    CourseModel getCourseByCourseCodeQuery(String code);
    CourseModel updateCourse(CourseModel course);
    List<CourseModel> getListCourseAsc();
    CourseModel deleteCourse(CourseModel course);
    boolean isClosed(LocalDateTime tanggalDimulai, LocalDateTime tanggalBerakhir);

}
