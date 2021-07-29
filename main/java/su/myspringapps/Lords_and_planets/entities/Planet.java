package su.myspringapps.Lords_and_planets.entities;

import javax.persistence.*;
import java.util.Objects;

/**
 * Класс Planet
 * <p/>
 * Описывает планету. Одной Планетой может править только один Повелитель.<br/>
 * Является Entity таблицы planets БД lords_and_planets_db
 * <p/>
 *
 * @author petr.mikhailin
 * created 21.07.2021 16:27
 */
@Entity
@Table(name = "planets")
public class Planet {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Название планеты
     */
    private String name;

    /**
     * Связь ManyToOne с таблицей lords БД lords_and_planets_db
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_lord", referencedColumnName = "id")
    private Lord lord;

    public Planet() {}

    public Planet(String name) {
        this.name = name;
    }

    public Planet(Integer id, String name, Lord lord) {
        this.id = id;
        this.name = name;
        this.lord = lord;
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

    public Lord getLord() {
        return lord;
    }

    public void setLord(Lord lord) {
        this.lord = lord;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Planet planet = (Planet) o;
        return Objects.equals(name, planet.name) &&
                Objects.equals(lord, planet.lord);
    }

}