package su.myspringapps.Lords_and_planets.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Класс Lord
 * <p/>
 * Описывает повелителя. Один Повелитель может управлять несколькими Планетами.<br/>
 * Является Entity таблицы lords БД lords_and_planets_db
 * <p/>
 *
 * @author petr.mikhailin
 * created 21.07.2021 16:27
 */
@Entity
@Table(name = "lords")
public class Lord {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Имя
     */
    private String name;

    /**
     * Возраст
     */
    private Integer age;

    /**
     * Связь OneToMany с таблицей planets БД lords_and_planets_db
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lord")
    private Set<Planet> planets = new HashSet<>();

    public Lord() {
    }

    public Lord(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public Lord(Integer id, String name, Integer age, Set<Planet> planets) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.planets = planets;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Set<Planet> getPlanets() {
        return planets;
    }

    public void setPlanets(Set<Planet> planets) {
        this.planets = planets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lord lord = (Lord) o;
        return Objects.equals(name, lord.name) &&
                Objects.equals(age, lord.age);
    }

}