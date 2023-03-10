package apap.tutorial.belajarbelajar.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transaction;
import javax.transaction.Transactional;

import apap.tutorial.belajarbelajar.model.CourseModel;
import apap.tutorial.belajarbelajar.model.PengajarModel;
import apap.tutorial.belajarbelajar.repository.CourseDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CourseServiceImpl implements CourseService{
    @Autowired
    CourseDb courseDb;
    @Override
    public void addCourse(CourseModel course){
        courseDb.save(course);
    }
    @Override
    public List<CourseModel> getListCourse(){
        return courseDb.findAll();
    }

    @Override
    public CourseModel getCourseByCourseCode(String code) {
        Optional<CourseModel> course = courseDb.findByCode(code);
        if(course.isPresent()){
            return course.get();
        } else return null;
    }

    @Override
    public CourseModel getCourseByCourseCodeQuery(String code){
        Optional<CourseModel> course = courseDb.findByCodeUsingQuery(code);
        if(course.isPresent()){
            return course.get();
        } else return null;
    }

    @Override
    public CourseModel updateCourse(CourseModel course){
        courseDb.save(course);
        return course;
    }

    @Override
    public List<CourseModel> getListCourseAsc(){
        return courseDb.findCourseAsc();
    }

    @Override
    public CourseModel deleteCourse(CourseModel course){
        courseDb.delete(course);
        return null;
    }

    @Override
    public boolean isClosed(LocalDateTime tanggalDimulai, LocalDateTime tanggalBerakhir){
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(tanggalDimulai)||now.isAfter(tanggalBerakhir)){
            return true;
        }
        return false;
    }

}
