package at.itKolleg.dataaccess;

import at.itKolleg.domain.Student;
import java.sql.Date;
import java.util.List;

public interface MyStudentRepository extends BaseRepository<Student,Long>{

    List<Student> findAllStudentsByFirstOrLastName(String searchText);
    List<Student> findAllByBirthYear(Date birthYear);
    List<Student> findAllBetweenBirthYears(Date fromYear, Date toYear);
}
