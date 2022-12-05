package at.itKolleg.dataaccess;

import at.itKolleg.domain.Course;
import at.itKolleg.domain.CourseTyp;
import com.mysql.cj.protocol.Resultset;
import util.Assert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MySqlCourseRepository implements MyCourseRepository {

    private Connection con;

    public MySqlCourseRepository() throws SQLException, ClassNotFoundException {
        this.con = MySqlDatabaseConnection.getConnection(
                "jdbc:mysql://localhost:3306/kurssystem", "root", "");
    }

    @Override
    public Optional<Course> insert(Course entity) {
        Assert.notNull(entity);

        try {
            String sql = "INSERT INTO `courses` (`name`, `description`, `hours`, `begindate`, `enddate`, `coursetype`) VALUES (?,?,?,?,?,?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); //generierten schlüssel separat zurück bekommen
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getDescription());
            preparedStatement.setInt(3, entity.getHours());
            preparedStatement.setDate(4, entity.getBeginDate());
            preparedStatement.setDate(5, entity.getEndDate());
            preparedStatement.setString(6, entity.getCourseTyp().toString());

            int affectedRows = preparedStatement.executeUpdate();//wie viele Spalten sind betroffen

            if (affectedRows == 0) {
                return Optional.empty();
            }

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys(); //keys zurückholen
            if (generatedKeys.next()) {
                return this.getById(generatedKeys.getLong(1));
            } else {
                return Optional.empty();
            }
        } catch (SQLException sqlException) {
            throw new DatabaseException(sqlException.getMessage());
        }
    }

    @Override
    public Optional<Course> getById(Long id) {
        Assert.notNull(id); //null check mit einer Utilityklasse
        if (countCoursesInDbWithId(id) == 0) {
            return Optional.empty();
        } else {
            try {
                String sql = "SELECT * FROM `courses` WHERE `id`=?";
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                preparedStatement.setLong(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();

                resultSet.next();
                Course course = new Course(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getInt("hours"),
                        resultSet.getDate("begindate"),
                        resultSet.getDate("enddate"),
                        CourseTyp.valueOf(resultSet.getString("coursetype"))
                );
                return Optional.of(course);
            } catch (SQLException sqlException) {
                throw new DatabaseException(sqlException.getMessage());
            }
        }
    }

    private int countCoursesInDbWithId(Long id) {
        String countSql = "SELECT COUNT(*) FROM `courses` WHERE `id`=?";

        try {
            PreparedStatement preparedStatementCount = con.prepareStatement(countSql);
            preparedStatementCount.setLong(1, id);
            ResultSet resultSetCount = preparedStatementCount.executeQuery();
            resultSetCount.next();
            int courseCount = resultSetCount.getInt(1);
            return courseCount;
        } catch (SQLException sqlException) {
            throw new DatabaseException(sqlException.getMessage());
        }
    }

    @Override
    public List<Course> getAll() {
        String sql = "SELECT * FROM `courses`";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Course> courseList = new ArrayList<>();
            while (resultSet.next()) {
                courseList.add(new Course( //hier findet objektrelationals Mapping statt
                                resultSet.getLong("id"),
                                resultSet.getString("name"),
                                resultSet.getString("description"),
                                resultSet.getInt("hours"),
                                resultSet.getDate("begindate"),
                                resultSet.getDate("enddate"),
                                CourseTyp.valueOf(resultSet.getString("coursetype"))
                        )
                );
            }
            return courseList;
        } catch (SQLException e) {
            throw new DatabaseException("Database error occured!");
        }
    }

    @Override
    public Optional<Course> update(Course entity) {
        Assert.notNull(entity);
        String sql = "UPDATE `courses` SET `name` = ?, `description` = ?, `hours` = ?, `begindate` = ?, `enddate` = ?, `coursetype` = ? WHERE `courses`.`id` = ? ";

        if (countCoursesInDbWithId(entity.getId()) == 0) {
            return Optional.empty();
        } else {
            try {
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, entity.getName());
                preparedStatement.setString(2, entity.getDescription());
                preparedStatement.setInt(3, entity.getHours());
                preparedStatement.setDate(4, entity.getBeginDate());
                preparedStatement.setDate(5, entity.getEndDate());
                preparedStatement.setString(6, entity.getCourseTyp().toString());
                preparedStatement.setLong(7, entity.getId());

                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows == 0) {
                    return Optional.empty();
                } else {
                    return this.getById(entity.getId());
                }
            } catch (SQLException sqlException) {
                throw new DatabaseException(sqlException.getMessage());
            }
        }
    }

    @Override
    public void deleteById(Long id) {
        Assert.notNull(id);
        String sql = "DELETE FROM `courses` WHERE `id` = ?";
        try {
            if (countCoursesInDbWithId(id) == 1){
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
            }

        }catch (SQLException sqlException){
            throw new DatabaseException(sqlException.getMessage());
        }
    }

    @Override
    public List<Course> findAllCoursesByName(String name) {
        return null;
    }

    @Override
    public List<Course> finAllCoursesByDescription(String description) {
        return null;
    }

    @Override
    public List<Course> findAllCoursesByNameOrDescription(String searchText) {
        return null;
    }

    @Override
    public List<Course> findAllCoursesByCourseTyp(CourseTyp courseTyp) {
        return null;
    }

    @Override
    public List<Course> findAllCoursesByStartDate(Date startDate) {
        return null;
    }

    @Override
    public List<Course> findAllCoursesByEndDate(Date endDate) {
        return null;
    }

    @Override
    public List<Course> findAllRunningCourses() {
        return null;
    }
}
