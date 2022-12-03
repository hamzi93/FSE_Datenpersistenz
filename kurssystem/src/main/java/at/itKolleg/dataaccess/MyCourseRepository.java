package at.itKolleg.dataaccess;

import at.itKolleg.domain.Course;
import at.itKolleg.domain.CourseTyp;
import java.sql.Date;
import java.util.List;

public interface MyCourseRepository extends BaseRepository<Course,Long>{
    // Spezifikation
    List<Course> findAllCoursesByName(String name);
    List<Course> finAllCoursesByDescription(String description);
    List<Course> findAllCoursesByNameOrDescription(String searchText);
    List<Course> findAllCoursesByCourseTyp(CourseTyp courseTyp);
    List<Course> findAllCoursesByStartDate(Date startDate);
    List<Course> findAllCoursesByEndDate(Date endDate);
    List<Course> findAllRunningCourses();
}
