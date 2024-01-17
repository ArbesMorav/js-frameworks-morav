package cz.eg.hr.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Entity
//@Indexed
@Validated
public class JavascriptFramework {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    //@Field
    // The following pattern allows letters and numbers along with "." and "-", which should be enough for names for now
    @Pattern(regexp = "[a-zA-Z0-9\\-.]+")
    @Length(min = 1, max = 30)
    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "framework", fetch = FetchType.LAZY)
    private List<FrameworkVersion> versions;

    @Range(min = 1, max = 5)
    @Column
    //@Field
    //@SortableField
    private Float rating;

    public JavascriptFramework() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Float getRating() {
        return rating;
    }
    public void setRating(Float rating) {
        this.rating = rating;
    }

    public List<FrameworkVersion> getVersions() {
        return versions;
    }
    public void setVersions(List<FrameworkVersion> versions) {
        this.versions = versions;
    }

    @Override
    public String toString() {
        return "JavaScriptFramework [id=" + id +
            ", name=" + name +
            ", rating=" + rating +
            ", versions=" + versions + "]";
    }

}
