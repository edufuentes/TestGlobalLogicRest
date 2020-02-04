package com.globallogic.test.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * Phone Entity Class.
 */
@Entity(name = "Phone")
@JsonIgnoreProperties(ignoreUnknown=true)
public class Phone {

  /**
   * Id.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   *  number.
   */
  private String number;

  /**
   * cityCode.
   */
  private String cityCode;

  /**
   * password.
   */
  private String countryCode;

  @JsonBackReference
  @ManyToOne
  @JoinColumn(name="ID_USER",  nullable = false)
  private User user;


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public String getCityCode() {
    return cityCode;
  }

  public void setCityCode(String cityCode) {
    this.cityCode = cityCode;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @Override
  public String toString() {
    return "Phone{" +
            "id=" + id +
            ", number='" + number + '\'' +
            ", cityCode='" + cityCode + '\'' +
            ", countryCode='" + countryCode + '\'' +
            ", user=" + user +
            '}';
  }
}

