package at.itKolleg.dataaccess;

import at.itKolleg.domain.Course;
import at.itKolleg.domain.CourseTyp;
import at.itKolleg.domain.Student;
import at.itKolleg.util.Assert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MySqlStudentRepository implements MyStudentRepository {

    private Connection con;

    public MySqlStudentRepository() throws SQLException, ClassNotFoundException {
        this.con = MySqlDatabaseConnection.getConnection(
                "jdbc:mysql://localhost:3306/kurssystem", "root", "");
    }

    @Override
    public Optional<Student> insert(Student entity) {
        Assert.notNull(entity);
        try {
            String sql = "INSERT INTO `student` (`firstname`, `lastname`, `birthdate`) VALUES (?,?,?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); //generierten schlüssel separat zurück bekommen
            preparedStatement.setString(1, entity.getFirstName());
            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setDate(3, entity.getBirthDate());

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
    public Optional<Student> getById(Long id) {
        Assert.notNull(id); //null check mit einer Utilityklasse
        if (countStudentsInDbWithId(id) == 0) {
            return Optional.empty();
        } else {
        try {
            String sql = "SELECT * FROM `student` WHERE `id`=?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            Student student = new Student(
                    resultSet.getLong("id"),
                    resultSet.getString("firstname"),
                    resultSet.getString("lastname"),
                    resultSet.getDate("birthdate")
            );
            return Optional.of(student);
        } catch (SQLException sqlException) {
            throw new DatabaseException(sqlException.getMessage());
        }}
    }

    @Override
    public List<Student> getAll() {
        String sql = "SELECT * FROM `student`";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Student> studentsList = new ArrayList<>();
            while (resultSet.next()) {
                studentsList.add(new Student( //hier findet objektrelationals Mapping statt
                                resultSet.getLong("id"),
                                resultSet.getString("firstname"),
                                resultSet.getString("lastname"),
                                resultSet.getDate("birthdate")
                        )
                );
            }
            return studentsList;
        } catch (SQLException e) {
            throw new DatabaseException("Database error occured!");
        }
    }

    @Override
    public Optional<Student> update(Student entity) {
        Assert.notNull(entity);
        String sql = "UPDATE `student` SET `firstname` = ?, `lastname` = ?, `birthdate` = ? WHERE `student`.`id` = ? ";

        if (countStudentsInDbWithId(entity.getId()) == 0) {
            return Optional.empty();
        } else {
            try {
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, entity.getFirstName());
                preparedStatement.setString(2, entity.getLastName());
                preparedStatement.setDate(3, entity.getBirthDate());
                preparedStatement.setLong(4, entity.getId());

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
        String sql = "DELETE FROM `student` WHERE `id` = ?";
        try {
            if (countStudentsInDbWithId(id) == 1) {
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
            }

        } catch (SQLException sqlException) {
            throw new DatabaseException(sqlException.getMessage());
        }
    }

    @Override
    public List<Student> findAllStudentsByFirstOrLastName(String searchText) {
        try {
            String sql = "SELECT * FROM `student` WHERE LOWER(`firstname`) LIKE LOWER(?) OR LOWER(`lastname`) LIKE LOWER(?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, "%" + searchText + "%");
            preparedStatement.setString(2, "%" + searchText + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Student> studentsList = new ArrayList<>();
            while (resultSet.next()) {
                studentsList.add(new Student(
                                resultSet.getLong("id"),
                                resultSet.getString("firstname"),
                                resultSet.getString("lastname"),
                                resultSet.getDate("birthdate")
                        )
                );
            }
            return studentsList;
    }catch (SQLException sqlException){
            throw new DatabaseException(sqlException.getMessage());
        }
    }

    @Override
    public List<Student> findAllByBirthYear(Date birthYear) {
        return null;
    }

    @Override
    public List<Student> findAllBetweenBirthYears(Date fromYear, Date toYear) {
        return null;
    }

    private int countStudentsInDbWithId(Long id) {
        String countSql = "SELECT COUNT(*) FROM `student` WHERE `id`=?";

        try {
            PreparedStatement preparedStatementCount = con.prepareStatement(countSql);
            preparedStatementCount.setLong(1, id);
            ResultSet resultSetCount = preparedStatementCount.executeQuery();
            resultSetCount.next();
            int studentCount = resultSetCount.getInt(1);
            return studentCount;
        } catch (SQLException sqlException) {
            throw new DatabaseException(sqlException.getMessage());
        }
    }
}

