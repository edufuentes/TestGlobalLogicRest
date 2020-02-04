package com.globallogic.test.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.*;

/**
 * User Entity Class.
 */
@Entity(name = "User")
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonPropertyOrder({ "id", "created", "last_login", "isActive" })
public class User {

  /**
   * Id.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   *  Name.
   */
  @JsonInclude(JsonInclude.Include.NON_NULL)
  @NotEmpty(message ="no puede estar vacio")
  private String name;

  /**
   * email.
   */
  @Column(nullable = true, unique = true)
  @Email(message="no es una dirección de correo bien formada")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String email;

  /**
   * password.
   */
  @Basic
  @Pattern(regexp = "^(?=(?:.*\\d){2})(?=(?:.*[A-Z]){1})(?=(?:.*[a-z]){2})\\S{1,}$")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String password;

  /**
   * isActive.
   */
  private Boolean isActive;

  @Column(insertable = true, updatable = false, nullable = true)
  @Temporal(TemporalType.DATE)
  private Calendar created;

  @Column(insertable = true, updatable = true, nullable = true)
  @Temporal(TemporalType.DATE)
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Calendar modified;

  @Column(insertable = true, updatable = true, nullable = true)
  @Temporal(TemporalType.DATE)
  private Calendar last_login;

  @PrePersist
  public void prePersist() {
    this.isActive = true;
  }
  @PreUpdate
  public void preUpdate () {
    this.modified = Calendar.getInstance();
  }


  /**
   * List Phones.
   */
  @JsonManagedReference
  @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<Phone> phone;


  public User() {

    phone = new ArrayList<Phone>();

  }


  /**
   * Get id.
   * @return Get Id.
   */
  public Long getId() {
    return id;
  }

  /**
   * Set id.
   * @param idA Id to Set
   */
  public void setId(final Long id) {
    this.id = id;
  }

  /**
   * Get name.
   * @return Name
   */
  public String getName() {
    return name;
  }

  /**
   * Set name.
   * @param nameA Name to Set
   */
  public void setName(final String name) {
    this.name = name;
  }


  public List<com.globallogic.test.entity.Phone> getPhone() {
    return phone;
  }

  public void setPhone(List<com.globallogic.test.entity.Phone> phone) {
    this.phone = phone;
  }

  @Override
  public String toString() {
    return "User{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", email='" + email + '\'' +
            ", password='" + password + '\'' +
            ", isActive='" + isActive + '\'' +
            '}';
  }


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Boolean getIsActive() {
    return isActive;
  }

  public void setIsActive(Boolean isActive) {
    this.isActive = isActive;
  }

  public Calendar getCreated() {
    return created;
  }

  public void setCreated(Calendar created) {
    this.created = created;
  }

  public Calendar getModified() {
    return modified;
  }

  public void setModified(Calendar modified) {
    this.modified = modified;
  }

  public Calendar getLast_login() {
    return last_login;
  }

  public void setLast_login(Calendar last_login) {
    this.last_login = last_login;
  }
}
