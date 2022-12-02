package at.itKolleg.domain;


import java.sql.Date;

/**
 * Domaenenklasse enthält alle Kursinformationen
 * (Zeilen der Datenbank) als Instanzvariablen
 */
public class Course extends BaseEntity{

    //id könnte man in eine abstrakten Klasse auslagern
    private String name;
    private String description;
    private int hours;
    private Date beginDate; //java.sql.Date
    private Date endDate;
    private CourseTyp courseTyp; //enum

    public Course(Long id, String name, String description, int hours, Date beginDate, Date endDate, CourseTyp courseTyp) throws InvalidValueExeption {
        super(id);
        this.setName(name);
        this.setDescription(description);
        this.setHours(hours);
        this.setBeginDate(beginDate);
        this.setEndDate(endDate);
        this.setCourseTyp(courseTyp);
    }

    public Course(String name, String description, int hours, Date beginDate, Date endDate, CourseTyp courseTyp) throws InvalidValueExeption {
        super(null);
        this.setName(name);
        this.setDescription(description);
        this.setHours(hours);
        this.setBeginDate(beginDate);
        this.setEndDate(endDate);
        this.setCourseTyp(courseTyp);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws InvalidValueExeption { //obwohl uncheckd Exc geben wir es als info mit
        if (name != null && name.length() > 1) {
            this.name = name;
        } else {
            throw new InvalidValueExeption("Kursname muss mindestens 2 Zeichen lang sein!");
            // ist ab jetzt unser Exception Handling... man muss keine machen, aber wenn dann mit richtiger message
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws InvalidValueExeption {
        if (description != null && description.length() > 9) {
            this.description = description;
        } else {
            throw new InvalidValueExeption("Kursbeschreibung muss mindestens 10 Zeichen lang sein!");
        }
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) throws InvalidValueExeption {
        if (hours > 0 && hours < 10) {
            this.hours = hours;
        } else {
            throw new InvalidValueExeption("Anzahl der Kursstunden darf nur zwischen 1 und 10 Stunden betragen!");
        }
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) throws InvalidValueExeption {
        if (beginDate != null) {
            if (this.endDate != null) {
                if (beginDate.before(this.endDate)) {
                    this.beginDate = beginDate;
                } else {
                    throw new InvalidValueExeption("Kursbeginn muss vor Kursende sein!");
                }
            } else {
                this.beginDate = beginDate;
            }
        } else {
            throw new InvalidValueExeption("Beginndatum darf nicht leer sein!");
        }
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) throws InvalidValueExeption {
        if (endDate != null) {
            if (this.beginDate != null) {
                if (endDate.after(this.beginDate)) {
                    this.endDate = endDate;
                } else {
                    throw new InvalidValueExeption("Kursende muss nach Kursbegin sein!");
                }
            } else {
                this.endDate = endDate;
            }
        } else {
            throw new InvalidValueExeption("Enddatum darf nicht leer sein!");
        }
    }

    public CourseTyp getCourseTyp() {
        return courseTyp;
    }

    public void setCourseTyp(CourseTyp courseTyp) throws InvalidValueExeption {
        if (courseTyp != null) {
            this.courseTyp = courseTyp;
        } else {
            throw new InvalidValueExeption("Kurstyp darf nicht leer sein!");
        }
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + this.getId() +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", hours=" + hours +
                ", beginDate=" + beginDate +
                ", endDate=" + endDate +
                ", courseTyp=" + courseTyp +
                '}';
    }
}
