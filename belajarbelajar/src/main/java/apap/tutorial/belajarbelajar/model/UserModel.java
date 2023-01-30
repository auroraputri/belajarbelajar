package apap.tutorial.belajarbelajar.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class UserModel implements Serializable {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @NotNull
    @Size(max=50)
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @NotNull
    @Column(name = "is_Sso", nullable = false)
    private Boolean isSso;

    @NotNull
    @Size(max=50)
    @Column(name = "nama", nullable = false, unique = true)
    private String nama;

    @NotNull
    @Size(max=50)
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotNull
    @Lob
    @Column(name = "password", nullable = false, unique = true)
    private String password;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_role", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private RoleModel role;
}
